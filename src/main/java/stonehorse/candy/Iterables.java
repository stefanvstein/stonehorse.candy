package stonehorse.candy;

import stonehorse.candy.Tuples.T2;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static stonehorse.candy.Lists.arrayList;
import static stonehorse.candy.Lists.asList;
import static stonehorse.candy.NullableBooleans.not;
import static stonehorse.candy.Maybe.maybe;
import static stonehorse.candy.Trampoline.*;
import static java.util.Collections.emptyIterator;
import static java.util.Collections.emptyList;
import static java.util.Objects.isNull;
import static java.util.Optional.ofNullable;
/**
 * Functions of iterables creating lazy iterators. As they are lazy they can be composed without memory exhaustion.
 * It's all functions which you easily create new ones of to extend the functionality.
 */
public class Iterables {
    private Iterables(){}
    /**
     * A new list with elements found in the next iterator of iterable
     */
    public static <A> List<A> forceList(Iterable<A> iterable) {
        List<A> list = new ArrayList<>();
        if (isNull(iterable))
            return list;
        for (A e : iterable)
            list.add(e);
        return list;
    }

    /**
     * A list with elements found in the next iterator of iterable. The iterable itself if it happen to be a List
     */
    public static <A> List<A> list(Iterable<A> iterable) {
        if (isNull(iterable))
            return null;
        if (iterable instanceof List)
            return (List<A>) iterable;
        return forceList(iterable);
    }

    /**
     * A new set with elements found in the next iterator of iterable
     */
    public static <A> Set<A> forceSet(Iterable<A> iterable) {
        Set<A> set = new HashSet<>();
        if (isNull(iterable))
            return set;
        for (A e : iterable)
            set.add(e);
        return set;
    }

    /**
     * A set with elements found in the next iterator of iterable. The iterable itself if it happen to be a Set
     * * <p>Example:
     * <pre>{@code
     *
     * }</pre>
     */
    public static <A> Set<A> set(Iterable<A> iterable) {
        if (isNull(iterable))
            return null;
        if (iterable instanceof Set)
            return (Set<A>) iterable;
        return forceSet(iterable);
    }

    /**
     * Combines elements of the first iterator into a single result by repeated application of a combining operation f where the initial result is accumulator
     * * <p>Example:
     * <pre>{@code
     * reduce((a,v)->a+v, 0, Arrays.asList(1,2,3,4)) => 10
     * }</pre>
     */
    public static <A, V, AP extends A> A reduce(BiFunction<AP, ? super V, AP> f, AP accumulator, Iterable<? extends V> elements) {
        if (isNull(elements) || isNull(f))
            return accumulator;
        for (V v : elements)
            accumulator = f.apply(accumulator, v);
        return accumulator;
    }

    /**
     * Iterable of lazy iterators filtering another Iterable using a predicate
     * <p>Example:
     * <pre>{@code
     * list(filter( e -> 0==e%2, Arrays.asList(1, 2, 3, 4))) => [2, 4]
     * }</pre>
     */
    public static <T> Iterable<T> filter(final Predicate<? super T> f, Iterable<T> data) {
        if (isNull(data) || isNull(f))
            return null;
        return () -> {
            Iterator<T> i = data.iterator();
            if (i.hasNext())
                return lazy(filterI(f, i)).iterator();
            return emptyIterator();
        };
    }

    private static <T> Supplier<Trampoline.RecursiveVal<T>> filterI(Predicate<? super T> f, Iterator<T> elements) {
        return () -> {
            if (!elements.hasNext())
                return stop();
            T t = elements.next();
            if (f.test(t))
                return seq(filterI(f, elements), t);
            else
                return recur(filterI(f, elements));
        };
    }

    /**
     * Iterable of lazy iterators applying a function to all element induced by a Iterable
     * <p>Example:
     * <pre>{@code
     * list(map( e -> e+1, Arrays.asList(1, 2, 3, 4))) => [2, 3, 4, 5]
     * }</pre>
     */
    public static <A, V> Iterable<V> map(final Function<? super A, V> f, final Iterable<A> data) {
        if (isNull(data) || isNull(f))
            return null;
        return () -> {
            Iterator<A> i = data.iterator();
            if (!i.hasNext())
                return emptyIterator();
            return lazy(mapI(f, i)).iterator();
        };
    }

    private static <A, V> Supplier<RecursiveVal<V>> mapI(Function<? super A, ? extends V> f, final Iterator<A> elements) {
        return () -> {
            V v = f.apply(elements.next());
            if (elements.hasNext())
                return seq(mapI(f, elements), v);
            return done(v);
        };
    }
    /**
     * Iterable of lazy iterators applying a combining function to every element induced by two Iterables, until one of the iterators are empty.
     * <p>Example:
     * <pre>{@code
     * list(map( (a,b) -> a+b, Arrays.asList(1, 2, 3, 4), Arrays.asList(1, 2, 3))) => [2, 4, 6]
     * }</pre>
     */
    public static <A, B, V> Iterable<V> map(BiFunction<? super A, ? super B, V> f, final Iterable<? extends A> i1, final Iterable<? extends B> i2) {
        if (isNull(i1) || isNull(i2) || isNull(f))
            return null;
        return () ->
                lazy(mapI(f, i1.iterator(), i2.iterator())).iterator();

    }

    private static <A, B, V> Supplier<RecursiveVal<V>> mapI(BiFunction<? super A, ? super B, ? extends V> f, final Iterator<? extends A> i1, final Iterator<? extends B> i2) {
        return () -> {
            if (!i1.hasNext())
                return stop();
            if (!i2.hasNext())
                return stop();
            V v = f.apply(i1.next(), i2.next());
            if (i1.hasNext() && i2.hasNext())
                return seq(mapI(f, i1, i2), v);
            return done(v);
        };
    }

    /**
     * Iterable of lazy iterators producing intermediate values of the reduction of elements by f, starting with accumulator
     * <p>Example:
     * <pre>{@code
     * list( reductions( (a,b) -> a+b, 0, Arrays.asList(1, 2, 3, 4))) => [1, 3, 6, 10]
     * }</pre>
     */
    public static <A, V, VP extends V> Iterable<V> reductions(final BiFunction<? super V, ? super A, VP> f, final V accumulator, final Iterable<? extends A> elements) {
        if (isNull(elements) || isNull(f))
            return null;
        return () -> lazy(reductionsI(f, accumulator, elements.iterator())).iterator();
    }

    private static <V, A, VP extends V> Supplier<RecursiveVal<V>> reductionsI(BiFunction<? super V, ? super A, VP> f, V a, final Iterator<? extends A> elements) {
        return () -> {
            if (!elements.hasNext())
                return stop();
            V acc = f.apply(a, elements.next());
            if (elements.hasNext())
                return seq(reductionsI(f, acc, elements), acc);
            return done(acc);
        };
    }

    /**
     * Iterable of lazy iterators but the num first elements of iterable
     * <p>Example:
     * <pre>{@code
     * list( drop( 2, Arrays.asList(1, 2, 3, 4))) => [3, 4]
     * }</pre>   */
    public static <T> Iterable<T> drop(int num, Iterable<T> iterable) {
        if (iterable == null)
            return null;
        return () -> {
            if (num < 1)
                return iterable.iterator();
            return lazy(dropI(num, iterable.iterator())).iterator();
        };
    }

    private static <T> Supplier<RecursiveVal<T>> dropI(int num, Iterator<? extends T> i) {
        return () -> {
            if (0 < num) {
                if (i.hasNext()) {
                    i.next();
                    return recur(dropI(num - 1, i));
                }
                return stop();
            }
            if (i.hasNext()) {
                T v = i.next();
                return seq(dropI(0, i), v);
            }
            return stop();
        };
    }

    /**
     * The first element of the next iterator of iterable or null
     * <p>Example:
     * <pre>{@code
     * first(  Arrays.asList(1, 2, 3, 4)) => 1
     * }</pre>
     */
    public static <T> T first(Iterable<T> iterable) {
        if (isNull(iterable))
            return null;
        Iterator<T> iterator = iterable.iterator();
        if (iterator.hasNext())
            return iterator.next();
        return null;
    }

    /**
     * The second element of the next iterator of iterable or null
     * <p>Example:
     * <pre>{@code
     * second( Arrays.asList(1, 2, 3, 4)) => 2
     * }</pre>
     */
    public static <T> T second(Iterable<T> iterable) {
        if (isNull(iterable))
            return null;
        return first(drop(1, iterable));
    }

    /**
     * The nth element of the next iterator of iterable or null
     * <p>Example:
     * <pre>{@code
     * nth(2, Arrays.asList(1, 2, 3, 4)) => 3
     * }</pre>
     */

    public static <T> T nth(int i, Iterable<T> iterable) {
        if (isNull(iterable))
            return null;
        if (i < 0)
            return null;
        return first(drop(i, iterable));
    }

    /**
     * Iterable of lazy iterators having num first values of iterable
     * <p>Example:
     * <pre>{@code
     * list( take(2, Arrays.asList(1, 2, 3, 4))) => [1, 2]
     * }</pre>
     */

    public static <T> Iterable<T> take(final int num, final Iterable<T> iterable) {
        if (isNull(iterable))
            return null;
        return () -> {
            if (num < 1)
                return emptyIterator();
            return lazy(takeI(num, iterable.iterator())).iterator();
        };
    }

    private static <T> Supplier<RecursiveVal<T>> takeI(int num, Iterator<? extends T> iterator) {
        return () -> {
            if (!iterator.hasNext())
                return stop();
            if (num > 0)
                return seq(takeI(num - 1, iterator), iterator.next());
            return stop();
        };
    }

    /**
     * Iterable of lazy iterators having the first elements matching predicate
     * <p>Example:
     * <pre>{@code
     * list( takeWhile(x -> x<3, Arrays.asList(1, 2, 3, 4))) => [1, 2]
     * }</pre>
     */
    public static <T> Iterable<T> takeWhile(Predicate<? super T> pred, Iterable<T> iterable) {
        return () -> {
            if (isNull(iterable) || isNull(pred))
                return null;
            return lazy(takeWhileI(pred, iterable.iterator())).iterator();
        };
    }

    private static <T> Supplier<RecursiveVal<T>> takeWhileI(Predicate<? super T> pred, Iterator<? extends T> iterator) {
        return () -> {
            if (!iterator.hasNext())
                return stop();
            T t = iterator.next();
            if (pred.test(t))
                return seq(takeWhileI(pred, iterator), t);
            return stop();
        };
    }

    /**
     * The last element of the next iterator of iterable or null
     * <p>Example:
     * <pre>{@code
     * last(  Arrays.asList(1, 2, 3, 4)) => 4
     * }</pre>
     */

    public static <T> T last(Iterable<T> i) {
        return reduce((a, v) -> v, null, i);
    }

    /**
     * Iterable of lazy iterators with every nth element of iterables iterator
     * <p>Example:
     * <pre>{@code
     * list( takeNth(2, Arrays.asList(1, 2, 3, 4))) => [1, 3]
     * }</pre>
     */
    public static <T> Iterable<T> takeNth(int nth, Iterable< T> iterable) {
        if (isNull( iterable))
            return null;
        return () -> lazy(takeNthI(iterable.iterator(), nth, 0)).iterator();
    }

    private static <T> Supplier< RecursiveVal<T>> takeNthI(Iterator<? extends T> iterator, int nth, int cnt) {
        return () -> {
            if (not(iterator.hasNext()))
                return stop();
            T v = iterator.next();
            if (cnt == 0)
                return seq(takeNthI(iterator, nth, nth-1), v);
            return recur(takeNthI(iterator, nth, cnt-1));
        };
    }

    /**
     * Iterable of lazy iterators which concatenate the result of applying map to every element in iterators of data.
     * <p>Example:
     * <pre>{@code
     * list( flatMap((v) -> asList(v, v), asList(1, 2))) => [1, 1, 2, 2]
     * }</pre>
     */
    public static <V, A> Iterable<A> flatMap(Function<? super V, Iterable< A>> f, Iterable<? extends V> data) {
        if (isNull(data) || isNull(f))
            return null;
        return lazy(flatMapI(f, null, data.iterator()));

    }

    private static <V, A> Supplier<RecursiveVal<A>> flatMapI(Function<? super V, Iterable<A>> f, Iterator<A> current, Iterator<? extends V> data) {
        return () -> {
            if (not(isNull(current))  && current.hasNext()) {
                A a = current.next();
                return seq(flatMapI(f, current, data), a);
            }
            if (data != null && data.hasNext()) {
                Iterable<A> c = f.apply(data.next());
                if (null != c)
                    return recur(flatMapI(f, c.iterator(), data));
                return recur(flatMapI(f, null, data));
            }
            return stop();
        };
    }

    /**
     * Iterable of lazy iterators emitting all elements but first from iterable or null if empty
     * <p>Example:
     * <pre>{@code
     * list( next( asList(1, 2, 3))) => [2, 2]
     * list( next( asList(1))) => null
     * }</pre>
     */
    public static <T> Iterable<T> next(Iterable<T> iterable) {
        if(iterable==null)
            return null;
        Iterator<?> it = iterable.iterator();
        if(it==null || !it.hasNext())
            return null;
        it.next();
        if(!it.hasNext())
            return null;

        return ()->{

            Iterator<T> i = iterable.iterator();
            if(i==null || !i.hasNext())
                return null;
            i.next();
            if(i.hasNext())
                return i;
            return null;
        };
    }

    /**
     * Iterable of lazy iterators emitting all elements but first from iterable
     * <p>Example:
     * <pre>{@code
     * list( rest( asList(1, 2, 3))) => [2, 2]
     * list( rest( asList(1))) => []
     * }</pre>
     */
    public static <T> Iterable<T> rest(Iterable<T> iterable) {
        if(iterable==null)
            return emptyList();

        return ()->{
            Iterator<T> i = iterable.iterator();
            if(i==null || !i.hasNext())
                return Collections.emptyIterator();
            i.next();
            if(i.hasNext())
                return i;
            return Collections.emptyIterator();
        };
    }
    /**
     * Iterable of lazy iterators emitting all elements except last num elements
     * <p>Example:
     * <pre>{@code
     * list( dropLast( 2, asList(1, 2, 3))) => [1]
     * }</pre>
     */

    public static <T> Iterable<T> dropLast(int num, Iterable<T> iterable) {
        if(iterable==null)
            return null;
        return ()->{
            if(num<1)
                return iterable.iterator();
           return maybe(map((a,b)-> a, iterable, drop(num, iterable)))
                    .map(Iterable::iterator)
                    .orElse(null);
        };
    }

    /**
     * Iterable of lazy iterators emitting all elements but except the first matching predicate
     * <p>Example:
     * <pre>{@code
     * list( dropWhile( e -> e<3, asList(1, 2, 3))) => [3]
     * }</pre>
     */

    public static <T> Iterable<T> dropWhile(Predicate<? super T> predicate, Iterable<T> iterable) {
        if(iterable==null || predicate == null)
            return null;
        return ()->lazy(dropWhileI(predicate, true, iterable.iterator())).iterator();
    }

    private static <T> Supplier<RecursiveVal<T>> dropWhileI(Predicate<? super T> predicate, boolean dropping, Iterator<T> i){
        return ()->{
            if(i.hasNext()){
                T v=i.next();
                if(dropping && predicate.test(v))
                    return recur(dropWhileI(predicate, true, i));
                return seq(dropWhileI(predicate, false, i), v);
            }
            return stop();
        };
    }


    /**
     * Iterable of lazy iterators partitioning elements into num long iterables
     * <p>Example:
     * <pre>{@code
     * list(map( Iterables::list,  list( partition( 3, asList(1, 2, 3, 4, 5, 6, 7))))) => [[1, 2, 3], [4, 5, 6]]
     * }</pre>
     */
    public static <T> Iterable<Iterable<T>> partition(int num, Iterable<T> iterable) {
        if (num < 1)
            throw new IllegalArgumentException();
        if (iterable == null)
            return null;
        return () -> {
            Iterator<T> iterator = iterable.iterator();
            if (iterator == null)
                return null;
            return lazy(partitionI(num, arrayList(), iterator)).iterator();
        };
    }

    private static <T> Supplier< RecursiveVal< Iterable<T>>> partitionI(int num, List<T> acc, Iterator< T> iterator) {
        return () -> {
            if (iterator.hasNext()) {
                acc.add(iterator.next());
                if (acc.size() >= num)
                    return seq(partitionI(num, arrayList(), iterator), acc);
                return recur(partitionI(num, acc, iterator));
            }
            return stop();
        };
    }


    /**
     * Iterable of lazy iterators partitioning elements by application to function
     * * <p>Example:
     * <pre>{@code
     * list( map( Iterables::list,  partitionBy( v -> v%2, asList(1, 3, 4, 5, 7, 6)))) => [[1, 3], [4], [5, 7], [6]]
     * }</pre>
     */
    public static <T> Iterable<Iterable<T>> partitionBy(Function<? super T, Object> function, Iterable<T> iterable) {
        if (function == null)
            return null;
        if (iterable == null)
            return null;
        return () -> {
            Iterator<T> iterator = iterable.iterator();
            if (iterator == null)
                return null;
            return lazy(partitionByI(function, Tuples.of(null, null), iterator)).iterator();
        };
    }

    private static <T> Supplier< RecursiveVal< Iterable<T>>> partitionByI(Function<? super T, Object> function, T2<Object, List<T>> acc, Iterator<T> iterator) {
        return () -> {
            if (iterator.hasNext()) {
                T v = iterator.next();
                Object o = function.apply(v);
                if (Objects.equals(acc._1(), o)) {
                    if (acc._2() == null)
                        return recur(partitionByI(function,  Tuples.of(o, arrayList(v)), iterator));
                    acc._2().add(v);
                    return recur(partitionByI(function, Tuples.of(o, acc._2()), iterator));
                }
                if (acc._2() == null)
                    return recur(partitionByI(function, Tuples.of(o, arrayList(v)), iterator));
                return seq(partitionByI(function, Tuples.of(o, arrayList(v)), iterator), acc._2());
            }
            if (acc._2() == null)
                return stop();
            return done(acc._2());
        };
    }

    /**
     * Tuple of Iterables of lazy iterators by spliting iterables iterators at i
     * * <p>Example:
     * <pre>{@code
     *  list(map( Iterables::list, splitAt( 3, asList(1, 2, 3, 4, 5, 6, 7))))) => [[1, 2, 3], [4, 5, 6, 7]]
     * }</pre>
     */
    public static <T> Iterable<Iterable<T>> splitAt(int i, Iterable< T> iterable){
        if(iterable==null || i<0)
            return null;
        return asList(take(i, iterable), drop(i, iterable));

    }

    /**
     * Tuple of Iterables of lazy iterators by spliting iterables iterators by predicate
     * * <p>Example:
     * <pre>{@code
     *  list(map( Iterables::list, splitWith(v->v%2==0, asList(4,2,3,4)))) => [[4, 2], [3, 4]]
     * }</pre>
     */
    public static <T> Iterable<Iterable<T>> splitWith(Predicate<T> p, Iterable< T> iterable){
        if(iterable==null || p==null)
            return null;
        return asList(takeWhile(p, iterable), dropWhile(p, iterable));
    }

    /**
     * Iterable of lazy iterators concatenating iterators from a and b
     * * <p>Example:
     * <pre>{@code
     *  list(concat( asList(1,2), asList(3,4))) => [1, 2, 3, 4]
     * }</pre>
     */
    public static <T> Iterable<T> concat(Iterable< T> a, Iterable< T> b) {
        if (null == a && null == b)
            return null;
        return () -> {
            if (a == null)
                return b.iterator();
            if (b == null)
                return a.iterator();
            return lazy(concatI(a.iterator(), asList(b).iterator())).iterator();
        };
    }

    private static <T> Supplier<RecursiveVal<T>> concatI(Iterator<? extends T> current, Iterator<? extends Iterable<? extends T>> following) {
        return () -> {
            if (current == null && following == null)
                return stop();
            if (null != current && current.hasNext()) {
                T t = current.next();
                return seq(concatI(current, following), t);
            }
            if (following.hasNext()) {
                Iterable<? extends T> iterable = following.next();
                if (iterable == null)
                    return recur(concatI(null, following));
                return recur(concatI(iterable.iterator(), following));
            }
            return stop();
        };
    }
    /**
     * Iterable of lazy iterators appending b to each iterator of iterable
     * * <p>Example:
     * <pre>{@code
     *  list( withLast( asList(1,2), 3 )) => [1, 2, 3]
     * }</pre>
     */
    public static <T> Iterable<T> withLast(Iterable<T> iterable, T b){
        if(iterable==null)
            return null;
        return ()-> {
            Iterator<T> i = iterable.iterator();
            if(i==null || not(i.hasNext()))
                return Lists.asList(b).iterator();
            return lazy(withLastI(i, b)).iterator();
        };
    }

    private static <T> Supplier<RecursiveVal<T>> withLastI(Iterator<T> a, T b){
        return ()->{
            if(a.hasNext()){
                T n = a.next();
                return seq(withLastI(a,b), n);
            }return Trampoline.done(b);
        };
    }

    /**
     * Iterable of lazy iterators prepending t to each iterator of iterable
     * <p>Example:
     * <pre>{@code
     *  list( with( 1, asList(2,3))) => [1, 2, 3]
     * }</pre>
     */
    public static <T> Iterable<T> with(T t, Iterable<? extends T> iterable) {
        return () -> {
            if (null == iterable)
                return asList(t).iterator();
            return lazy(withI(t, iterable.iterator())).iterator();
        };
    }

    private static <T> Supplier<RecursiveVal<T>> withI(T a, Iterator<? extends T> i) {
        return () -> {
            if (i.hasNext())
                return seq(withI(i.next(), i), a);
            return done(a);
        };
    }

    /**
     * Iterable of lazy infinite iterators iterating over function with initial value. That is initial, function(initial), function(function(initial)) and so on
     * <p>Example:
     * <pre>{@code
     *  list(take(4, iterate(v -> v + 1, 5))) => [5, 6, 7, 8]
     * }</pre>
     */
    public static <T> Iterable<T> iterate(Function<? super T, ? extends T> function, T initial) {
        return () -> {
            if (isNull(function))
                return asList(initial).iterator();
            return with(initial, lazy(iterateI(function, initial))).iterator();
        };
    }

    private static <T> Supplier<RecursiveVal<T>> iterateI(Function<? super T, ? extends T> function, T val) {
        return () -> {
            T n = function.apply(val);
            return seq(iterateI(function,n), n);
        };
    }

    /**
     * Stream of iterable
     */
    public static <T> Stream<T> stream(Iterable<T> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false);
    }

    /**
     * Iterable of stream
     *
     */
    public static <T> Iterable<T> iterable(Stream<T> stream) {
        return stream::iterator;
    }


    /**
     * Iterable of lazy infinite iterators with invokes of supplier
     * <p>Example:
     * <pre>{@code
     *  list(take(3, repeatedly(() -> 4))) => [4, 4, 4]
     * }</pre>
     */
    public static <T> Iterable<T> repeatedly(Supplier<T> supplier) {
        if (isNull(supplier))
            return null;
        return () -> lazy(repeatedlyI(supplier)).iterator();
    }

    private static <T> Supplier<RecursiveVal<T>> repeatedlyI(Supplier<? extends T> supplier) {
        return () -> seq(repeatedlyI(supplier), supplier.get());
    }
    /**
     * Iterable of lazy infinite iterators of x
     * <p>Example:
     * <pre>{@code
     *  list(take(3, repeat( 4))) => [4, 4, 4]
     * }</pre>
     */
    public static <T> Iterable<T> repeat(T x){
        return ()->lazy(repeatI(x, null)).iterator();
    }

    public static <T> Iterable<T> repeat(int lim, T t){
        if(lim<1)
            return emptyList();
        return ()->lazy(repeatI(t,lim)).iterator();
    }

    private static <T> Supplier<RecursiveVal<T>> repeatI(T t, Integer lim){
        return ()->{
            if(lim==null)
                return seq(repeatI(t,null), t);
            if(lim>0)
                return seq(repeatI(t, lim-1), t);
            if(lim==0)
                return done(t);
            return stop();
        };
    }

    /**
     * Iterable of lazy iterators iterating from 0 to Integer.MAX_VALUE
     * <p>Example:
     * <pre>{@code
     *  list(take(3, range())) => [0, 1, 2]
     * }</pre>
     */
    public static Iterable<Integer> range(){
        return ()->lazy(rangeI(Integer.MAX_VALUE,1, 0)).iterator();
    }

    /**
     * Iterable of lazy iterators iterating from 0 to n
     * <p>Example:
     * <pre>{@code
     *  list(range(3)) => [0, 1, 2]
     * }</pre>
     */
    public static Iterable<Integer> range(int n){
        if(n<0) return emptyList();
        return ()->lazy(rangeI(n,1, 0)).iterator();
    }
    /**
     * Iterable of lazy iterators iterating from from to to
     * <p>Example:
     * <pre>{@code
     *  list(range(1,3)) => [1, 2]
     * }</pre>
     */
    public static Iterable<Integer> range(int from, int to){
        if(from==to) return emptyList();
        return ()->{
            if(to<from) return lazy(rangeI( from, -1, to)).iterator();
            return lazy(rangeI(to,1, from)).iterator();
        };
    }
    /**
     * Iterable of lazy iterators iterating from from to to in steps of step
     * <p>Example:
     * <pre>{@code
     *  list(range(1,5,3)) => [1, 4]
     * }</pre>
     */
    public static Iterable<Integer> range(int from, int to, int step){
        return ()->lazy(rangeI(to,step, from)).iterator();
    }

    private static Supplier< RecursiveVal<Integer>> rangeI( int to, int step, int n){
        return () -> {
            if(step>0 && n >= to )
                return stop();
            if(step<0 && n<=to)
                return stop();
            return seq(rangeI(to, step, n+step), n);
        };
    }

    /**
     * Iterable of lazy infinite iterators elements of in. A new iterator is created for each lap.
     * <p>Example:
     * <pre>{@code
     *  list(take(5, cycle(asList(1,2,3)))) => [1, 2, 3, 1, 2]
     * }</pre>
     */
    public static <T> Iterable<T> cycle(Iterable< T> in){
        if(!ofNullable(in)
                .map(Iterable::iterator)
                .map(Iterator::hasNext)
                .orElse(false))
            return emptyList();
        return ()->lazy(cycleI(in, in.iterator())).iterator();
    }

    private static <T> Supplier<RecursiveVal<T>> cycleI(Iterable< T> in, Iterator<T> iterator){
        return ()->{
            Iterator<T> i = iterator;
            if(!i.hasNext())
                i=in.iterator();
            T t=i.next();
            return seq(cycleI(in,i), t);
        };
    }
}
