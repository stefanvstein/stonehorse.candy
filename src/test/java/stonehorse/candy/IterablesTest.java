package stonehorse.candy;

import org.junit.Test;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

import static stonehorse.candy.A.B.b;
import static stonehorse.candy.A.a;
import static stonehorse.candy.Iterables.*;
import static stonehorse.candy.Lists.asList;
import static org.junit.Assert.*;


public class IterablesTest {
    @Test
    public void testReduce() {
        assertEquals(6, (long) fold((a, v) -> a + v, 0, asList(1, 2, 3)));
        assertEquals(Integer.valueOf(0), fold((a, v) -> a + v, 0, Collections.<Integer>emptyList()));
        assertEquals(Integer.valueOf(0), fold((a, v) -> a + v, 0, (List<Integer>)null));
        assertEquals(6, (long) reduce((a, v) -> a + v, asList(1, 2, 3)));
        assertNull(reduce((a, v) -> a + v, (List<Integer>)null));
        assertNull(reduce((a, v) -> a + v, Collections.<Integer>emptyList()));
    }
    @Test
    public void testFilter() {
        assertEquals(asList(2, 4), list(filter(v -> v % 2 == 0, asList(1, 2, 3, 4))));
    }

    @Test
    public void testMap() {
        assertEquals(asList(2, 3, 4, 5), list(map(v -> v + 1, asList(1, 2, 3, 4))));
        assertEquals(asList(null, null, null, null), list(map(v -> null, asList(1, 2, 3, 4))));
        Function<A.B,A> f = a->a;
        Iterable<A> as=map(f,asList(b(),b()));
        list(map(v->v, asList()));
    }
    @Test
    public void testBiMap() {
        assertEquals(asList(5, 7, 9, 11), list(map((a, b) -> a + b, asList(1, 2, 3, 4), asList(4, 5, 6, 7))));
        assertEquals(asList(5, 7), list(map((a, b) -> a + b, asList(1, 2 ), asList(4, 5, 6, 7))));
        assertEquals(asList(5, 7), list(map((a, b) -> a + b, asList(1, 2,3,4 ), asList(4, 5 ))));
        assertNull( map((a, b) -> a + b, asList(1, 2,3,4 ), (List<Integer>)null));
        Iterable<A> r=map((a,v)->a, asList(b()), asList(b()));
    }

    @Test
    public void testReductions() {
        assertEquals(asList(1, 3, 6, 10), list(reductions((a, v) -> a + v, 0, asList(1, 2, 3, 4))));
        assertEquals(asList(), list(reductions((a, v) -> a + v, 0, Lists.<Integer>asList())));
        assertEquals(asList(1), list(reductions((a, v) -> a + v, 0, Lists.asList(1))));
        assertNull(reductions((a, v) -> a + v, 0, (Iterable<Integer>)null));
        assertNull(reductions(null, 0, asList(1, 2, 3, 4)));
    }

    @Test
    public void testDrop() {
        assertEquals(asList(1, 2, 3), list(drop(0, asList(1, 2, 3))));
        assertEquals(asList(2, 3), list(drop(1, asList(1, 2, 3))));
        assertEquals(asList(3), list(drop(2, asList(1, 2, 3))));
        assertEquals(asList(), list(drop(3, asList(1, 2, 3))));
        assertEquals(asList(), list(drop(4, asList(1, 2, 3))));
        assertEquals(asList(), list(drop(1, asList(1))));
        assertEquals(asList(), list(drop(1, asList())));
        assertEquals(asList(), list(drop(0, asList())));
        assertEquals(null, list(drop(0, null)));

    }

    @Test
    public void testFirst() {
        assertEquals(Integer.valueOf(1), first(asList(1, 2)));
        assertEquals(Integer.valueOf(1), first(asList(1)));
        assertNull(first(asList()));
        assertNull(first(null));
    }

    @Test
    public void testSecond() {
        assertEquals(Integer.valueOf(2), second(asList(1, 2, 3)));
        assertEquals(Integer.valueOf(2), second(asList(1, 2)));
        assertNull(second(asList(1)));
        assertNull(second(asList()));
        assertNull(second(null));
    }

    @Test
    public void testNth() {
        assertEquals(Integer.valueOf(2), nth(1, asList(1, 2, 3)));
        assertEquals(Integer.valueOf(1), nth(0, asList(1, 2)));
        assertEquals(Integer.valueOf(2), nth(1, asList(1, 2)));
        assertNull(nth(2, asList(1, 2)));
        assertNull(nth(2, asList()));
        assertNull(nth(0, asList()));
        assertNull(nth(0, null));
        assertNull(nth(1, null));
        assertNull(nth(-1, null));
        assertNull(nth(-2, asList(1, 2)));
    }


    @Test
    public void testTake() {
        assertEquals(asList(1, 2), list(take(2, asList(1, 2, 3, 4))));
        assertEquals(asList(1, 2, 3), list(take(3, asList(1, 2, 3, 4))));
        assertEquals(asList(), list(take(-1, asList(1, 2, 3, 4))));
        assertFalse(take(0, asList(1, 2, 3)).iterator().hasNext());
        assertFalse(take(4, asList()).iterator().hasNext());
        A a= take(1,asList(b(), b())).iterator().next();
        assertNotNull(a);
        a= first(take(2,asList(b(), b())));
        assertNotNull(a);
    }

    @Test
    public void testTakeWhile() {
        assertEquals(asList(1, 2, 3), list(takeWhile(v -> v < 4, asList(1, 2, 3, 4, 5))));
        assertEquals(asList(), list(takeWhile(v -> false, asList(1, 2, 3, 4, 5))));
        assertFalse(takeWhile(v -> false, asList(1, 2, 3, 4, 5)).iterator().hasNext());
        Iterator<Integer> i = takeWhile(v -> v < 2, asList(1, 2, 3, 4, 5)).iterator();
        i.next();
        assertFalse(i.hasNext());
        Iterable<A> r = takeWhile((Object v)->true, asList(b()));
    }

    @Test
    public void testLast() {
        assertEquals(Integer.valueOf(3), last(asList(1, 2, 3)));
        assertEquals(Integer.valueOf(2), last(asList(1, 2)));
        assertNull(last(asList()));
        assertNull(last(null));
    }

    @Test
    public void testTakeNth() {
        assertEquals(asList(1, 4, 7), list(takeNth(3, asList(1, 2, 3, 4, 5, 6, 7))));
        assertEquals(asList(1), list(takeNth(3, asList(1, 2, 3))));
        assertEquals(asList(1), list(takeNth(3, asList(1, 2))));
        assertNull(takeNth(3, null));
        Iterable<A> as= takeNth(23, asList(b(),b()));
    }

    @Test
    public void testFlatMap() {
        assertEquals(asList("1", "1", "2", "2"),
                list(flatMap((v) -> asList(v.toString(), v.toString()), asList(1, 2))));
        assertEquals(asList("1", "2"),
                list(flatMap((v) -> asList(v.toString()),
                        asList(1, 2))));
        assertEquals(asList(),
                list(flatMap((v) -> asList(v.toString()),
                        asList())));
        assertEquals(asList(),
                list(flatMap((v) -> null,
                        asList())));
        assertEquals(null,
                list(flatMap((v) -> asList(v.toString()),
                        null)));
       
    }


    @Test
    public void testNext() {
        assertEquals(asList(2, 3), list(next(asList(1, 2, 3))));
        assertEquals(asList(2), list(next(asList(1, 2))));
        assertNull(list(next(asList(1))));
        assertNull(list(next(asList())));
        assertNull(list(next(null)));
        assertNull(next(null));
    }

    @Test
    public void testRest() {
        assertEquals(asList(2, 3), list(rest(asList(1, 2, 3))));
        assertEquals(asList(2), list(rest(asList(1, 2))));
        assertEquals(asList(), list(rest(asList(1))));
        assertEquals(asList(), list(rest(asList())));
        assertEquals(asList(), list(rest(null)));

    }

    @Test
    public void testDropLast() {
        assertEquals(asList(1, 2), list(dropLast(0, asList(1, 2))));
        assertEquals(asList(1, 2), list(dropLast(-7, asList(1, 2))));
        assertEquals(asList(1, 2), list(dropLast(1, asList(1, 2, 3))));
        assertEquals(asList(), list(dropLast(3, asList(1, 2, 3))));
        assertEquals(asList(), list(dropLast(4, asList(1, 2, 3))));
        assertNull(list(dropLast(3, null)));
    }

    @Test
    public void testDropWhile() {
        assertEquals(asList(2, 3), list(dropWhile(c -> c < 2, asList(1, 2, 3))));
        assertEquals(asList(2, 3, 4), list(dropWhile(c -> c < 2, asList(1, 2, 3, 4))));
        assertEquals(asList(), list(dropWhile(c -> c < 2, asList(1))));
        assertEquals(asList(), list(dropWhile(c -> c < 2, Lists.<Integer>asList())));
        assertNull(list(dropWhile(c -> c < 2, (List<Integer>) null)));
    }


    @Test
    public void testPartition() {
        assertEquals(asList(asList(1, 2, 3), asList(4, 5, 6)),
                list(partition(3, asList(1, 2, 3, 4, 5, 6, 7, 8))));
        assertEquals(asList(asList(1, 2, 3), asList(4, 5, 6)),
                list(partition(3, asList(1, 2, 3, 4, 5, 6, 7))));
        assertEquals(asList(asList(1, 2, 3), asList(4, 5, 6)),
                list(partition(3, asList(1, 2, 3, 4, 5, 6))));

        assertEquals(asList(),
                list(partition(3, asList(1, 2))));
        assertEquals(asList(),
                list(partition(3, asList())));
        assertNull(
                list(partition(3, null)));
        Iterable<Iterable<A>> as = partition(3, asList(b()));

        Iterable<Iterable<Integer>> p = partition(2, asList(1, 2, 3, 4, 5));
        Iterable<Integer> f = first(p);

        Iterable<Integer> s = second(p);

        Iterator<Integer> fi = f.iterator();
        Iterator<Integer> si = s.iterator();
    }


    @Test
    public void testPartitionBy() {
        assertEquals(asList(asList(1, 7), asList(4, 6), asList(3), asList(8)),
                list(map(Iterables::list,
                        partitionBy(v -> v % 2 == 0, asList(1, 7, 4, 6, 3, 8)))));
        assertEquals(asList(asList(1, 7)),
                list(map(Iterables::list,
                        partitionBy(v -> v % 2 == 0, asList(1, 7)))));
        assertEquals(asList(asList(1)),
                list(map(Iterables::list, partitionBy(v -> v % 2 == 0, asList(1)))));
        assertEquals(asList(asList(1), asList(4)),
                list(map(Iterables::list, partitionBy(v -> v % 2 == 0, asList(1, 4)))));
        assertEquals(asList(),
                list(partitionBy(v -> v % 2 == 0, Lists.<Integer>asList())));
        assertNull(
                list(partitionBy(v -> v % 2 == 0, (List<Integer>) null)));
        assertNull(
                list(partitionBy(null, Lists.<Integer>asList())));
        Iterable<Iterable<A>> as=partitionBy((Object v)->b(),asList(b()) );
    }

    @Test
    public void testSplitAt() throws Exception {
        assertEquals(asList(asList(1,2),asList(3,4)),
                list(map (Iterables::list, splitAt(2, asList(1,2,3,4)))));
        assertEquals(asList(asList(),asList(1,2,3,4)),
                list(map (Iterables::list, splitAt(0, asList(1,2,3,4)))));
        assertEquals(asList(asList(1,2,3,4),asList()),
                list(map (Iterables::list,splitAt(7, asList(1,2,3,4)))));
        assertEquals(asList(asList(1),asList()),
                list(map (Iterables::list,splitAt(1, asList(1)))));
        assertNull(splitAt(1, null));
    }

    @Test
    public void testSplitWith() throws Exception {
        assertEquals(asList(asList(4,2),asList(3,4)),
                list(map(Iterables::list, splitWith(v->v%2==0, asList(4,2,3,4)))));

    }

    @Test
    public void testConcat() {
        assertEquals(asList(1, 2, 3, 4), list(concat(asList(1, 2), asList(3, 4))));
        assertEquals(asList(1, 2), list(concat(asList(1, 2), null)));
        assertEquals(asList(), list(concat(asList(), null)));
        assertEquals(asList(1, 2), list(concat(asList(), asList(1, 2))));
        assertEquals(asList(), list(concat(null, asList())));
        assertEquals(asList(1, 2), list(concat(null, asList(1, 2))));
        assertNull(concat(null, null));
        Iterable<A> as = concat(asList(b()), asList(b()));
    }

    @Test public void testWith(){
        assertEquals(asList(1,2,3), list(withLast(asList(1,2), 3)));
        assertEquals(asList(3), list(withLast(asList(), 3)));
        assertNull(withLast(null, 3));
    }

    @Test
    public void testCons() {
        assertEquals(asList(1, 2, 3), list(with(1, asList(2, 3))));
        assertEquals(asList(1), list(with(1, null)));
        assertEquals(asList(2, 1), list(with(2, with(1, null))));
        assertEquals(asList(null), list(with(null, null)));
        a();
        Iterable<A> as = with(b(), asList(a()));
        as= with(a(), asList(b()));
    }

    @Test
    public void testIterate() {
        assertEquals(asList(5, 6, 7, 8), list(take(4, iterate(v -> v + 1, 5))));
        assertEquals(asList(5), list(take(4, iterate(null, 5))));
        assertEquals(asList(5, null,null, null), list(take(4, iterate(v->null, 5))));
    }

    @Test
    public void testStream() {
        assertTrue(stream(asList(1, 2, 3)).allMatch(v -> v < 4));
    }

    @Test
    public void testIterable() {
        assertEquals(asList(2, 3, 4), list(iterable(asList(1, 2, 3).stream().map(v -> v + 1))));
    }

    @Test
    public void testRepeatedly() {
        assertEquals(asList(2, 2, 2), list(take(3, repeatedly(() -> 2))));
        Iterable<A> as =repeatedly(A.B::b);
    }

    @Test
    public void testRepeat() {
        assertEquals(asList(1, 1), list(take(2, repeat(1))));
        assertEquals(asList(null), list(take(1, repeat(null))));
        assertEquals(asList(null, null), list(take(2, repeat(null))));
        assertEquals(asList(null, null, null), list(take(3, repeat(null))));
    }
    @Test
    public void testRange() {
        assertEquals(asList(0,1,2),list(take(3,range())));
        assertEquals(asList(0,1,2),list(range(3)));
        assertEquals(asList(3,4,5), list(range(3,6)));
        assertEquals(asList(3,5), list(range(3,6,2)));
    }


    @Test
    public void testCycle() throws Exception {
        assertEquals(asList(1,2,3,1,2,3), list(take(6,cycle(asList(1,2,3)))));
        assertEquals(asList(null, null, null,null), list(take(4,cycle(asList(null)))));
    }


}
