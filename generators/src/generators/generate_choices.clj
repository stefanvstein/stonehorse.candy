(ns generators.generate-choices
(:require [generators.common :refer [nline]]))

(defn- supplier-expressions [to]
  (apply str 
         (map #(str "      Supplier<Boolean> t" % ", Supplier<? extends T> v" %  "," (nline))
              (range 0 to))))

(defn- arg-list [from to]
  (str (apply str 
              (map #(str "t" % ", v" %  ", " )
                   (range from to)))
        "v" to))

(defn- final-supplier [n]
  (str  "      Supplier<? extends T> v" n))

(defn gen-cond2 [n]
  (str
   "  /**
   * Evaluates each test t in order until true, in which case value of corresponding v is returned. 
   * When all values are false, or null, returns the value of last v. 
   * Evaluations short circuits on truth. There are overloads with arity on test cases.   
   */
"
   "  public static <T> T cond("
        (nline)
        (supplier-expressions n)
        (final-supplier n) 
        "){"
        (nline)
        "    requireAllNonNull("
        (arg-list 0 n) 
        ");"
        (nline)
        "    return trampoline(condRec("
        (arg-list 0 n) 
        "));"
        (nline)
        "  }"))

(defn gen-cond-rec2 [n]
  (str "  private static <T> Supplier<RecursiveVal<T>> condRec("
       (nline)
       (supplier-expressions n)
       (final-supplier n)
       "){"
       (nline)
       "    return ()->ifelse(truth(t0.get()),
                      ()->done(v0.get()),
                      ()->recur(condRec("
       (arg-list 1 n)

       ")));" 
       (nline)
       "   }"))

(defn generate [n package]
  (println (str "package " package ";
import java.util.function.Supplier;
import static java.util.Objects.requireNonNull;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import java.util.function.Function;
import static " package ".Trampoline.trampoline;
import static " package ".Trampoline.done;
import static " package ".Trampoline.recur;
import " package ".Trampoline.RecursiveVal;
import static " package ".NullableBooleans.truth;

/**
 * Expression replacements for the if-statements<p>
 *
 * {@link #cond(Supplier, Supplier, Supplier)} is similar to switch-case-default as expression, and exists overloaded with arity on cases<br>
 * {@link #ifelse(boolean, Supplier, Supplier)} is if-then-else as expression. That is ternary operator with prefix syntax<br>
 * {@link #ifNot(boolean, Supplier, Supplier)} is like if-else-then as expression<br>
 * {@link #when(boolean, Supplier)} is if-then without else. Delivers null on else<br>
 * {@link #whenNot(boolean, Supplier)} is if-else without then. Delivers null on then<br>
 * {@link #unless(Supplier, boolean, Supplier)} is then-if-else as expression<br>
 * {@link #unlessNull(Object, Supplier)} Supplier value instead if T happen to be null. T if it is an object<br>
 * {@link #whenOr(Object, Function, Supplier)} return application of T to Function, unless null when Supplier value returns<br>
 * <br>
 * {@link #doIfElse(boolean, Runnable, Runnable)} ifelse for side effects<br>
 *{@link #doWhen(boolean, Runnable)} when for side effects<br>
 * {@link #doWhenNot(boolean, Runnable)} whenNot for side effects<br>
 * <br>
 * {@link #requireAllNonNull(Object...)} throws NPE if any is null <br>
 */
//This class is generated

public class Choices{
  private Choices(){}
  /**
   * Always returns value of v0.
   * Evaluates each test t in order until true, in which case value of corresponding v is returned. 
   * When all values are false, or null, returns the value of last v. Evaluations short circuits on truth. 
   * There are overloads with arity on test cases.   
   */
  public static <T> T cond(
      Supplier<? extends T> v0){
    requireNonNull(v0);
    return v0.get();
  }

  /**
   * Evaluates each test t in order until true, in which case value of corresponding v is returned.
   * When all values are false, or null, returns the value of last v. Evaluations short circuits on truth. 
   * There are overloads with arity on test cases.   
   */

  public static <T> T cond(
      Supplier<Boolean> t0, Supplier<? extends T> v0,
      Supplier<? extends T> v1){
  requireAllNonNull(t0,v0,v1);
  return ifelse(truth(t0.get()),()->v0.get(),()->v1.get());
  }"))


           (dotimes [i (dec  n)]
             (println  (gen-cond2 (+ 2 i)))
             (newline)
             (println (gen-cond-rec2 (+ 2 i)))
             (newline))
  
  (println 
"   private static <T> Supplier<RecursiveVal<T>> condRec(
            Supplier<Boolean> t0, Supplier<? extends T> v0,
            Supplier<? extends T> v1) {
        requireAllNonNull(t0,v0,v1);
        return ()->
                ifelse(truth(t0.get()),
                        ()->done(v0.get()),
                        ()->done(v1.get()));
  }

  /**
   * Evaluates then and returns its value unless test is false, in which case otherwise is evaluated and its value returned. 
   * Behaves like if-then-else but is a expression. That is ternary operator with prefix syntax. 
   */
  public static <T> T ifelse(boolean test, Supplier<T> then,
                             Supplier<T> otherwise) {
    requireNonNull(then);
    requireNonNull(otherwise);
    return test ? then.get() : otherwise.get();
  }

  /**
   * Then is evaluated and its value returned if test is true. Otherwise null is returned
   */
  public static <T> T when(boolean test, Supplier<T> then){
    return ifelse(test, then, ()->null);
  }

  /**
   * Evaluates then and returns its value unless test is true, in which case otherwise is evaluated and its value returned. 
   */
  public static <T> T ifNot(boolean test, Supplier<T> then, Supplier<T> otherwise){
    return ifelse(test, otherwise, then);
  }

  /**
   * Then is evaluated and its value returned if test is false. Otherwise null is returned   
   */
  public static <T> T whenNot(boolean test, Supplier<T> then){
    return ifelse(test, ()->null, then);
  }

  /**
   * Returns evaluation of then, unless test happen to be true, in which case the evaluation of otherwise is returned
   */
  public static <T> T unless(Supplier<T> then, boolean test, Supplier<T> otherwise) {
    requireNonNull(then);
    requireNonNull(otherwise);
    if (test)
      return otherwise.get();
    return then.get();
  }

  /**
   * t is delivered unless it is null, in which case the evaluation of then is returned
   */
  public static <T> T unlessNull( T t, Supplier<? extends T> then){
    requireNonNull(then);
    if(isNull(t))
      return then.get();
    return t;
  }

  /**
   * Return application of t to f, unless t is null, in which case evaluation of supplier s returns
   */
  public static <T, V> V whenOr(T t, Function<T, V> f, Supplier<V> s) {
    requireNonNull(f);
    requireNonNull(s);
    if (nonNull(t))
      return f.apply(t);
    return s.get();
  }

  /**
   * Like when without any return value, to express side effect
   */
  public static void doWhen (boolean test, Runnable then){
    if(test)
      then.run();
  }

  /**
   * Like whenNo without any return value, to express side effect
   */
  public static void doWhenNot (boolean test, Runnable then){
    if(!test)
      then.run();
  }

  /**
   * Like ifelse without any return value, to express side effect
   */
  public static void doIfElse(boolean test, Runnable then, Runnable otherwise) {
    requireNonNull(then);
    requireNonNull(otherwise);
    if (test)
      then.run();
    else otherwise.run();
  }

  /**
   * Like ifNot without any return value, to express side effect
   */
  public static void doIfNot(boolean test, Runnable then, Runnable otherwise){
    doIfElse(test, otherwise, then);
  }

  /**
   * Throws nullpointer exception if any is null
   */
  public static <T> void requireAllNonNull(Object... nonNulls) {
        for (Object o: nonNulls) {
          requireNonNull(o);
        }
    }
}"))
