package stonehorse.candy;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 *
 */
public class Functions {
	private Functions(){}
	/**
	 * produces a supplier by applying the argument to a functions, without calling the function. The function is called when the supplier is evaluated.
	 */
	public static <T, V> Supplier<V> partial(final Function<? super T, ? extends V> f, final T t) {
		Objects.requireNonNull(f);
		return () -> f.apply(t);
	}
	public static <T> Supplier<Boolean> partialP(final Predicate<? super T> f, final T t) {
	Objects.requireNonNull(f);
		return () -> f.test(t);
	}

	/**
	 * Produces a Function of a BiFunction by supplying the first argument
	 */
	public static <T, U, V> Function<U, V> partial(final java.util.function.BiFunction<? super T, ? super U, ? extends V> f,
												   final T t) {
		Objects.requireNonNull(f);
		return a -> f.apply(t, a);
	}

	/**
	 * Returns a function that return false if supplied returns true, and true is supplied returns false
	 */

	public static <T> Function<T, Boolean> complementF(
			final Function<? super T, Boolean> f) {
		Objects.requireNonNull(f);
		return a -> NullableBooleans.not(f.apply(a));
	}



	public static <T> Function<T, Boolean> complement(
			final Predicate<? super T> f) {
		Objects.requireNonNull(f);
		return a -> NullableBooleans.not(f.test(a));
	}

	/**
	 * returns the identity function, which returns the argument applied
	 */
	public static <V, U extends V> Function<U, V> identity() {
		return t -> t;
	}

	/**
	 * Returns a composed function that first applies f1 to its input, and then f2 to the result..
	 */
	public static <A, B, V> Function<A, V> compose(
			final Function<? super A, ? extends B> f1,
			final Function<? super B, ? extends V> f2) {
		Objects.requireNonNull(f1);
		Objects.requireNonNull(f2);
		return a -> f2.apply(f1.apply(a));
	}
}
