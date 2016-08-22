package stonehorse.candy;


import java.util.Objects;


import static java.util.Arrays.asList;


public class Tuples {
    private Tuples(){}
    public interface T1<V> {

        V _1();

        T1<V> _1(V v);

        /**
         * new Type of Tuple
         */
        <X> T1<X> with_1(X x);

        /**
         * new Type of Tuple
         */
        <X> T2<V, X> with_2(X x);
    }

    public interface T2<V1, V2> extends T1<V1> {
        V2 _2();

        T2<V1, V2> _2(V2 v);

        /**
         * Skip first
         */
        T1<V2> skip();

        <X> T3<V1, V2, X> with_3(X x);

        /**
         * new Type of Tuple
         */
        @Override
        <X> T2<V1, X> with_2(X x);

        /**
         * new Type of Tuple
         */
        @Override
        <X> T2<X, V2> with_1(X x);

        @Override
        T2<V1, V2> _1(V1 v);
    }

    public interface T3<V1, V2, V3> extends T2<V1, V2> {
        V3 _3();

        @Override
        T3<V1, V2, V3> _2(V2 v);

        @Override
        T3<V1, V2, V3> _1(V1 v);

        T3<V1, V2, V3> _3(V3 v);

        /**
         * Skip first
         */
        @Override
        T2<V2, V3> skip();

        /**
         * new Type of Tuple
         */
        @Override
        <X> T3<X, V2, V3> with_1(X x);

        /**
         * new Type of Tuple
         */
        @Override
        <X> T3<V1, X, V3> with_2(X x);

        /**
         * new Type of Tuple
         */
        @Override
        <X> T3<V1, V2, X> with_3(X x);

        <X> T4<V1, V2, V3, X> with_4(X x);
    }

    public interface T4<V1, V2, V3, V4> extends T3<V1, V2, V3> {
        V4 _4();

          @Override
        T4<V1, V2, V3, V4> _1(V1 v);

        @Override
        T4<V1, V2, V3, V4> _2(V2 v);

        @Override
        T4<V1, V2, V3, V4> _3(V3 v);

        T4<V1, V2, V3, V4> _4(V4 v);

        /**
         * Skip first
         */
        @Override
        T3<V2, V3, V4> skip();

        /**
         * new Type of Tuple
         */
        @Override
        <X> T4<X, V2, V3, V4> with_1(X x);

        /**
         * new Type of Tuple
         */
        @Override
        <X> T4<V1, X, V3, V4> with_2(X x);

        /**
         * new Type of Tuple
         */
        @Override
        <X> T4<V1, V2, X, V4> with_3(X x);

        /**
         * new Type of Tuple
         */
        <X> T4<V1, V2, V3, X> with_4(X x);

        /**
         * new Type of Tuple
         */
        <X> T5<V1, V2, V3, V4, X> with_5(X x);
    }

    public interface T5<V1, V2, V3, V4, V5> extends T4<V1, V2, V3, V4> {
        V5 _5();

        @Override
        T5<V1, V2, V3, V4, V5> _1(V1 v);

        @Override
        T5<V1, V2, V3, V4, V5> _2(V2 v);

        @Override
        T5<V1, V2, V3, V4, V5> _3(V3 v);

        @Override
        T5<V1, V2, V3, V4, V5> _4(V4 v);

        T5<V1, V2, V3, V4, V5> _5(V5 v);

        /**
         * Skip first
         */
        @Override
        T4<V2, V3, V4, V5> skip();

        /**
         * new Type of Tuple
         */
        @Override
        <X> T5<X, V2, V3, V4, V5> with_1(X x);

        /**
         * new Type of Tuple
         */
        @Override
        <X> T5<V1, X, V3, V4, V5> with_2(X x);

        /**
         * new Type of Tuple
         */
        @Override
        <X> T5<V1, V2, X, V4, V5> with_3(X x);

        /**
         * new Type of Tuple
         */
        @Override
        <X> T5<V1, V2, V3, X, V5> with_4(X x);

        /**
         * new Type of Tuple
         */
        <X> T5<V1, V2, V3, V4, X> with_5(X x);

    }


    public static <T> T1<T> of(T t) {
        return new Box<>(t);
    }

    public static <F, S> T2<F, S> of(F first, S second) {
        return new Pair<>(first, second);
    }

    public static <F, S, T> T3<F, S, T> of(F first, S second, T third) {
        return new Triplet<>(first, second, third);
    }

    public static <V1, V2, V3, V4> T4<V1, V2, V3, V4> of(V1 first, V2 second, V3 third, V4 fourth) {
        return new Quartet<>(first, second, third, fourth);
    }
    public static <V1, V2, V3, V4, V5> T5<V1, V2, V3, V4, V5> of(V1 first, V2 second, V3 third, V4 fourth, V5 fifth) {
        return new Quintet<>(first, second, third, fourth, fifth);
    }

    public static <T> T first(T1<T> t) {
        return t._1();
    }
    public static <T> T second(T2<?,T> t) {
        return t._2();
    }
    public static <T> T third(T3<?,?,T> t) {
        return t._3();
    }
    public static <T> T fourth(T4<?,?,?,T> t) {
        return t._4();
    }
    public static <T> T fifth(T5<?,?,?,?,T> t) {
        return t._5();
    }

    private static class Box<T> implements T1<T> {
        private final T t;

        private Box(T t) {
            this.t = t;
        }


        @Override
        public String toString() {
            return "[" + t + "]";
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof Box && Objects.equals(t, ((Box) obj).t);
        }

        @Override
        public int hashCode() {
            return Objects.hash(t);
        }

        @Override
        public T _1() {
            return t;
        }

        @Override
        public T1<T> _1(T t) {
            return new Box<>(t);
        }

        @Override
        public <X> Box<X> with_1(X x) {
            return new Box<>(x);
        }

        @Override
        public <X> T2<T, X> with_2(X x) {
            return new Pair<>(t, x);
        }
    }

    private static class Pair<F, S> implements T2<F, S> {
        private final F first;
        private final S second;

        private Pair(F f, S s) {
            first = f;
            second = s;
        }

        private static boolean not(boolean b) {
            return !b;
        }

        @Override
        public int hashCode() {
            return Objects.hash(first, second);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (not(obj instanceof Pair))
                return false;
            Pair<?, ?> other = (Pair<?, ?>) obj;
            return asList(first, second).equals(asList(other.first, other.second));
        }

        @Override
        public String toString() {
            return "[" + first + ", " + second + "]";
        }

        @Override
        public S _2() {
            return second;
        }

        @Override
        public T2<F, S> _2(S s) {
            return new Pair<>(first, s);
        }

        @Override
        public T1<S> skip() {
            return new Box<>(second);
        }

        @Override
        public <X> T3<F, S, X> with_3(X x) {
            return new Triplet<>(first, second, x);
        }

        @Override
        public <X> Pair<F, X> with_2(X x) {
            return new Pair<>(first, x);
        }

        @Override
        public <X> Pair<X, S> with_1(X x) {
            return new Pair<>(x, second);
        }

        @Override
        public T2<F, S> _1(F f) {
            return new Pair<>(f, second);
        }


        @Override
        public F _1() {
            return first;
        }


    }

    private static class Triplet<F, S, T> implements T3<F, S, T> {
        private final F first;
        private final S second;
        private final T third;



        private Triplet(F f, S s, T t) {
            first = f;
            second = s;
            third = t;
        }

        @Override
        public int hashCode() {
            return Objects.hash(first, second, third);
        }

        private static boolean not(boolean b) {
            return !b;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (not(obj instanceof Triplet))
                return false;
            Triplet<?, ?, ?> other = (Triplet<?, ?, ?>) obj;
            return asList(first, second, third).equals(
                    asList(other.first, other.second, other.third));
        }

        @Override
        public String toString() {
            return "[" + first + ", " + second + ", " + third + "]";
        }

        @Override
        public T _3() {
            return third;
        }


        @Override
        public T3<F, S, T> _2(S s) {
            return new Triplet<>(first, s, third);
        }

        @Override
        public T3<F, S, T> _1(F f) {
            return new Triplet<>(f, second, third);
        }

        @Override
        public T3<F, S, T> _3(T t) {
            return new Triplet<>(first, second, t);
        }

        @Override
        public T2<S, T> skip() {
            return new Pair<>(second, third);
        }

        @Override
        public <X> Triplet<F, X, T> with_2(X x) {
            return new Triplet<>(first, x, third);
        }

        @Override
        public <X> Triplet<X, S, T> with_1(X x) {
            return new Triplet<>(x, second, third);
        }

        @Override
        public <X> Triplet<F, S, X> with_3(X x) {
            return new Triplet<>(first, second, x);
        }


        @Override
        public S _2() {
            return second;
        }



        @Override
        public F _1() {
            return first;
        }

        @Override
        public <X> T4<F, S, T, X> with_4(X x) {
            return new Quartet<>(first, second, third, x);
        }

    }

    private static class Quartet<V1, V2, V3, V4> implements T4<V1, V2, V3, V4> {

        private final V1 first;
        private final V2 second;
        private final V3 third;
        private final V4 fourth;

        public Quartet(V1 first, V2 second, V3 third, V4 fourth) {
            this.first = first;
            this.second = second;
            this.third = third;
            this.fourth = fourth;
        }

        @Override
        public V4 _4() {
            return fourth;
        }

        @Override
        public V1 _1() {
            return first;
        }


        @Override
        public T4<V1, V2, V3, V4> _1(V1 v) {
            return new Quartet<>(v, second, third, fourth);
        }


        @Override
        public V3 _3() {
            return third;
        }

        @Override
        public V2 _2() {
            return second;
        }


        @Override
        public T4<V1, V2, V3, V4> _2(V2 v) {
            return new Quartet<>(first, v, third, fourth);
        }

        @Override
        public T4<V1, V2, V3, V4> _3(V3 v) {
            return new Quartet<>(first, second, v, fourth);
        }

        @Override
        public T4<V1, V2, V3, V4> _4(V4 v) {
            return new Quartet<>(first, second, third, v);
        }

        /**
         * Skip first
         */
        @Override
        public T3<V2, V3, V4> skip() {
            return new Triplet<>(second, third, fourth);
        }

        /**
         * new Type of Tuple
         */
        @Override
        public <X> T4<X, V2, V3, V4> with_1(X x) {
            return new Quartet<>(x, second, third, fourth);
        }

        /**
         * new Type of Tuple
         */
        @Override
        public <X> T4<V1, X, V3, V4> with_2(X x) {
            return new Quartet<>(first, x, third, fourth);
        }


        /**
         * new Type of Tuple
         */
        @Override
        public <X> T4<V1, V2, X, V4> with_3(X x) {
            return new Quartet<>(first, second, x, fourth);
        }

        /**
         * new Type of Tuple
         */
        @Override
        public <X> T4<V1, V2, V3, X> with_4(X x) {
            return new Quartet<>(first, second, third, x);
        }

        /**
         * new Type of Tuple
         */
        @Override
        public <X> T5<V1, V2, V3, V4, X> with_5(X x) {
            return new Quintet<>(first, second, third, fourth, x);
        }

        @Override
        public int hashCode() {
            return Objects.hash(first, second, third, fourth);
        }

        private static boolean not(boolean b) {
            return !b;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (not(obj instanceof Quartet))
                return false;
            Quartet<?, ?, ?, ?> other = (Quartet<?, ?, ?, ?>) obj;
            return asList(first, second, third, fourth).equals(
                    asList(other.first, other.second, other.third, other.fourth));
        }

        @Override
        public String toString() {
            return "[" + first + ", " + second + ", " + third + ", " + fourth + "]";
        }
    }

    private static class Quintet<V1, V2, V3, V4, V5> implements T5<V1, V2, V3, V4, V5> {

        private final V1 first;
        private final V2 second;
        private final V3 third;
        private final V4 fourth;
        private final V5 fifth;

        Quintet(V1 first, V2 second, V3 third, V4 fourth, V5 fifth) {
            this.first = first;
            this.second = second;
            this.third = third;
            this.fourth = fourth;
            this.fifth = fifth;
        }

        @Override
        public int hashCode() {
            return Objects.hash(first, second, third, fourth, fifth);
        }

        private static boolean not(boolean b) {
            return !b;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (not(obj instanceof Quintet))
                return false;
            Quintet<?, ?, ?, ?, ?> other = (Quintet<?, ?, ?, ?, ?>) obj;
            return asList(first, second, third, fourth, fifth).equals(
                    asList(other.first, other.second, other.third, other.fourth, other.fifth));
        }

        @Override
        public String toString() {
            return "[" + first + ", " + second + ", " + third + ", " + fourth + ", " + fifth + "]";
        }

        @Override
        public V5 _5() {
            return fifth;
        }

        @Override
        public V4 _4() {
            return fourth;
        }

        @Override
        public V1 _1() {
            return first;
        }


        @Override
        public T5<V1, V2, V3, V4, V5> _1(V1 v) {
            return new Quintet<>(v, second, third, fourth, fifth);
        }

        @Override
        public V3 _3() {
            return third;
        }


        @Override
        public V2 _2() {
            return second;
        }


        @Override
        public T5<V1, V2, V3, V4, V5> _2(V2 v) {
            return new Quintet<>(first, v, third, fourth, fifth);
        }

        @Override
        public T5<V1, V2, V3, V4, V5> _3(V3 v) {
            return new Quintet<>(first, second, v, fourth, fifth);
        }

        @Override
        public T5<V1, V2, V3, V4, V5> _4(V4 v) {
            return new Quintet<>(first, second, third, v, fifth);
        }

        @Override
        public T5<V1, V2, V3, V4, V5> _5(V5 v) {
            return new Quintet<>(first, second, third, fourth, v);
        }

        /**
         * Skip first
         */
        @Override
        public T4<V2, V3, V4, V5> skip() {
            return new Quartet<>(second, third, fourth, fifth);
        }

        /**
         * new Type of Tuple
         */
        @Override
        public <X> T5<X, V2, V3, V4, V5> with_1(X x) {
            return new Quintet<>(x, second, third, fourth, fifth);
        }

        /**
         * new Type of Tuple
         */
        @Override
        public <X> T5<V1, X, V3, V4, V5> with_2(X x) {
            return new Quintet<>(first, x, third, fourth, fifth);
        }


        /**
         * new Type of Tuple
         */
        @Override
        public <X> T5<V1, V2, X, V4, V5> with_3(X x) {
            return new Quintet<>(first, second, x, fourth, fifth);
        }

        /**
         * new Type of Tuple
         */
        @Override
        public <X> T5<V1, V2, V3, X, V5> with_4(X x) {
            return new Quintet<>(first, second, third, x, fifth);
        }

        /**
         * new Type of Tuple
         */
        @Override
        public <X> T5<V1, V2, V3, V4, X> with_5(X x) {
            return new Quintet<>(first, second, third, fourth, x);
        }

    }
}
