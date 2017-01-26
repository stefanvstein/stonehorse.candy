package stonehorse.candy;


import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.IntUnaryOperator;
import java.util.function.LongUnaryOperator;
import java.util.function.UnaryOperator;

/**
 * Utility functions for java.util.concurrent.atomic with function first interface
 */
public class Atomic {

    public static AtomicBoolean atomic(boolean b){
        return new AtomicBoolean(b);
    }
    public static AtomicInteger atomic(int i){
        return new AtomicInteger(i);
    }
    public static AtomicLong atomic(long i){
        return new AtomicLong(i);
    }
    public static <T> AtomicReference<T> atomic(T o){
        return new AtomicReference<>(o);
    }

    /**
     * @see java.util.concurrent.atomic.AtomicReference#updateAndGet(UnaryOperator)
     */
    public static <T> T swap(AtomicReference<T> ref, UnaryOperator<T> f ){
        Objects.requireNonNull(ref);
        Objects.requireNonNull(f);
        return ref.updateAndGet(f);
    }
    /**
     * @see java.util.concurrent.atomic.AtomicInteger#updateAndGet(IntUnaryOperator)
     */
    public static int swap(AtomicInteger ref, IntUnaryOperator f ){
        Objects.requireNonNull(ref);
        Objects.requireNonNull(f);
        return ref.updateAndGet(f);
    }
    /**
     * @see java.util.concurrent.atomic.AtomicLong#updateAndGet(LongUnaryOperator)
     */
    public static long swap(AtomicLong ref, LongUnaryOperator f ){
        Objects.requireNonNull(ref);
        Objects.requireNonNull(f);
        return ref.updateAndGet(f);
    }

    /**
     * Like updateAndGate for AtomicBoolean
     */
    public static boolean swap(AtomicBoolean ref, BooleanOperator f ){
        Objects.requireNonNull(ref);
        Objects.requireNonNull(f);
        boolean prev, next;
        do {
            prev = ref.get();
            next = f.apply(prev);
        } while (!ref.compareAndSet(prev, next));
        return next;
    }

    public static int value(AtomicInteger a){
        Objects.requireNonNull(a);
        return a.get();
    }
    public static long value(AtomicLong a){
        Objects.requireNonNull(a);
        return a.get();
    }
    public static boolean value(AtomicBoolean a){
        Objects.requireNonNull(a);
        return a.get();
    }
    public static <T> T value(AtomicReference<T> a){
        Objects.requireNonNull(a);
        return a.get();
    }
}
