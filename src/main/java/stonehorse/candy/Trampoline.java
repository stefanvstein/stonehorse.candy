package stonehorse.candy;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Supplier;

public class Trampoline {
	private Trampoline(){}
	public static class RecursiveVal<V> {
		public final java.util.function.Supplier<RecursiveVal<V>> fun;

		public final V value;
		public final boolean hasValue;

		private RecursiveVal(Supplier< RecursiveVal< V>> fun, V value, boolean hasValue) {
			this.fun = fun;
			this.value = value;
			this.hasValue = hasValue;
		}

		private static <V> RecursiveVal<V> nil() {
			return new RecursiveVal<>(null, null, false);
		}
	}

	/**
	 * return recur to recur withLast f and a
	 */
	public static < V> RecursiveVal<V> recur(Supplier<RecursiveVal<V>> f) {
		return new RecursiveVal<>(f, null, false);
	}
/**
 * return done to when last value is found
 */
	public static <V> RecursiveVal<V> done(V v) {
		return new RecursiveVal<>(null, v, true);
	}

	/**
	 * Return stop when done without a value. Null if on trampoline
	 */
	public static <V> RecursiveVal< V> stop() {
		return new RecursiveVal<>(null, null, false);
	}

	/**
	 * return seq to yield lazy value v, and continue withLast f a
	 * f as null is same as done or stop, depending on v
	 * Will behave like recur on a trampoline
	 */
	public static < V> RecursiveVal< V> seq(Supplier<RecursiveVal<V>> f, V v) {
		return new RecursiveVal<>(f, v, true);
	}

	public static <V> V trampoline(Supplier<RecursiveVal<V>> fun) {
		boolean value = false;
		RecursiveVal< V> ret = RecursiveVal.nil();
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
	public static <A, V> Iterable<V> lazy(final Supplier< RecursiveVal< V>> fun) {
		return ()-> new LazyIterator<>(fun);
	}

	private static class LazyIterator< V> implements Iterator<V> {

		private Supplier<RecursiveVal<V>> fun;
		private V next;
		private boolean hasNext = false;

		public LazyIterator(Supplier< RecursiveVal<V>> fun) {
			this.fun = fun;
		}

		@Override public boolean hasNext() {
			if (hasNext)
				return true;
			if (null == fun)
				return false;
			while (true) {
				RecursiveVal<V> ret = fun.get();
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
