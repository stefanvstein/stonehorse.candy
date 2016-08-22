package stonehorse.candy;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Functional interface for Optional
 */
public class Maybe {
	private Maybe(){}
	public static <T> Optional<T> maybe(T t){
		return Optional.ofNullable(t);
	}
	public static <T> Optional<T> nothing(){
		return Optional.empty();
	}

	public static <T> Optional<T> just(T t){
		return Optional.of(t);
	}

	public static boolean has(Optional<?> o) {
		return o != null && o.isPresent();
	}

	public static <T> T get(Optional<T> m, T t){
		if(m==null)
			return t;
		return m.orElse(t);
	}
	public static <T> T otherwise(Optional<T> m, Supplier<T> s){
		Objects.requireNonNull(s);
		if(m==null)
			return s.get();
		return m.orElseGet(s);
	}
	public static <T,V> Optional<V> flatmap(Function<? super T, Optional<V>> f, Optional<T> v){
		Objects.requireNonNull(f);
		if(v==null)
			return nothing();
		return v.flatMap(f);
	}
	public static <T,V> Optional<V> map(Function<? super T,? extends V> f, Optional<T> v){
		Objects.requireNonNull(f);
		if(v==null)
			return nothing();
		return v.map(f);
	}

	public static <T> void ifPresent(Optional<T> o, Consumer<? super T> c) {
		Objects.requireNonNull(c);
		if(o!=null)
		o.ifPresent(c);
	}
	public static <T> Optional<T> filter(Optional<T> o, Predicate<? super T> predicate) {
		Objects.requireNonNull(predicate);
		if(o==null)
			return nothing();
		return o.filter(predicate);
	}
}
