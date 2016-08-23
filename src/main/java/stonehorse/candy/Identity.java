package stonehorse.candy;

import java.util.Objects;
import java.util.function.Function;

/**
 * The identity
 */
public class Identity<T> {
    final private T t;
    private Identity(T t){
        this.t=t;
    }
    public static <T> Identity<T> identity(T t){
        return new Identity<>(t);
    }
    public T get(){
        return t;
    }
    public static <T> T get(Identity<? extends T> t){
        return t.get();
    }
    public <V> Identity<V> map(Function<? super T,? extends V> f){
        return identity(f.apply(t));
    }
    public <V> Identity<V> flatmap(Function<? super T,Identity<V>> f){
        return f.apply(t);
    }

    @Override
    public String toString() {
        return "Identity["+t+"]";
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(t);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj==this) return true;
        if(!(obj instanceof Identity))
            return false;
        return Objects.equals(t,((Identity)obj).t);
    }
    public static <T,V> Identity<T> map(Function<? super V,? extends T> f, Identity<V> v){
        return v.map(f);
    }
    public static <T,V> Identity<T> flatmap(Function<? super V,Identity< T>> f, Identity<V> v){
        return v.flatmap(f);
    }
}
