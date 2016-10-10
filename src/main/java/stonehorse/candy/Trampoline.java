package stonehorse.candy;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Supplier;

/**
 * Functions for tail call
 */
public class Trampoline {
	private Trampoline(){}

	/**
	 * Tail call return value
	 */
	public static class Continuation<V> {
		public final java.util.function.Supplier<Continuation<V>> fun;

		public final V value;
		public final boolean hasValue;

		private Continuation(Supplier<Continuation< V>> fun, V value, boolean hasValue) {
			this.fun = fun;
			this.value = value;
			this.hasValue = hasValue;
		}

		private static <V> Continuation<V> nil() {
			return new Continuation<>(null, null, false);
		}
	}

	/**
	 * return recur to recur withLast f and a
	 */
	public static < V> Continuation<V> recur(Supplier<Continuation<V>> f) {
		return new Continuation<>(f, null, false);
	}
/**
 * return done to when last value is found
 */
	public static <V> Continuation<V> done(V v) {
		return new Continuation<>(null, v, true);
	}

	/**
	 * Return stop when done without a value. Null if on trampoline
	 */
	public static <V> Continuation< V> stop() {
		return new Continuation<>(null, null, false);
	}

	/**
	 * return seq to yield lazy value v, and continue withLast f a
	 * f as null is same as done or stop, depending on v
	 * Will behave like recur on a trampoline
	 */
	public static < V> Continuation< V> seq(Supplier<Continuation<V>> f, V v) {
		return new Continuation<>(f, v, true);
	}

	/**
	 * Will recur on fun until done(value)
	 */
	public static <V> V trampoline(Supplier<Continuation<V>> fun) {
		boolean value = false;
		Continuation< V> ret = Continuation.nil();
		while (fun != null && !value) {
			ret = fun.get();

			fun = ret.fun;
			value = ret.hasValue;
		}
		return ret.value;
	}

	/**
	 * Emits a lazy Iterable recuring until stop, done, null or lack of f 
	 */
	public static <A, V> Iterable<V> lazy(final Supplier<Continuation< V>> fun) {
		return ()-> new LazyIterator<>(fun);
	}

	private static class LazyIterator< V> implements Iterator<V> {

		private Supplier<Continuation<V>> fun;
		private V next;
		private boolean hasNext = false;

		public LazyIterator(Supplier<Continuation<V>> fun) {
			this.fun = fun;
		}

		@Override public boolean hasNext() {
			if (hasNext)
				return true;
			if (null == fun)
				return false;
			while (true) {
				Continuation<V> ret = fun.get();
				if(null==ret)
					return false;
				fun = ret.fun;
				next = ret.value;
				hasNext = ret.hasValue;
				if (hasNext)
					return true;
				if (null == fun)
					return false;
			}
		}

		@Override
		public V next() {
			if (hasNext())
				try {
					hasNext = false;
					return next;
				} finally {
					next = null;
				}
			throw new NoSuchElementException();

		}

	}
}
