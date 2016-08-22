package stonehorse.candy;
import java.util.function.Supplier;
import static java.util.Objects.requireNonNull;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import java.util.function.Function;
import static stonehorse.candy.Trampoline.trampoline;
import static stonehorse.candy.Trampoline.done;
import static stonehorse.candy.Trampoline.recur;
import stonehorse.candy.Trampoline.RecursiveVal;
import static stonehorse.candy.NullableBooleans.truth;

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
 * {@link #mapOr(Object, Function, Supplier)} return application of T to Function, unless null when Supplier value returns<br>
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
   * <p>Example:
   * <pre>{@code
   * cond(() -> false, () -> "No",
   *      () -> true, () -> "Yes",
   *      () -> true, () -> "Already caught" ,
   *      () -> "catch all")  => "Yes"
   * }</pre>  
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
   * <p>Example:
   * <pre>{@code
   * cond(() -> false, () -> "No",
   *      () -> true, () -> "Yes",
   *      () -> true, () -> "Already caught" ,
   *      () -> "catch all")  => "Yes"
   * }</pre>  
   */

  public static <T> T cond(
      Supplier<Boolean> t0, Supplier<? extends T> v0,
      Supplier<? extends T> v1){
  requireAllNonNull(t0,v0,v1);
  return ifelse(truth(t0.get()), v0::get, v1::get);
  }
  /**
   * Evaluates each test t in order until true, in which case value of corresponding v is returned. 
   * When all values are false, or null, returns the value of last v. 
   * Evaluations short circuits on truth. There are overloads with arity on test cases.   
   * <p>Example:
   * <pre>{@code
   * cond(() -> false, () -> "No",
   *      () -> true, () -> "Yes",
   *      () -> true, () -> "Already caught" ,
   *      () -> "catch all")  => "Yes"
   * }</pre>  
   */
  public static <T> T cond(
      Supplier<Boolean> t0, Supplier<? extends T> v0,
      Supplier<Boolean> t1, Supplier<? extends T> v1,
      Supplier<? extends T> v2){
    requireAllNonNull(t0, v0, t1, v1, v2);
    return trampoline(condRec(t0, v0, t1, v1, v2));
  }

  private static <T> Supplier<RecursiveVal<T>> condRec(
      Supplier<Boolean> t0, Supplier<? extends T> v0,
      Supplier<Boolean> t1, Supplier<? extends T> v1,
      Supplier<? extends T> v2){
    return ()->ifelse(truth(t0.get()),
                      ()->done(v0.get()),
                      ()->recur(condRec(t1, v1, v2)));
   }

  /**
   * Evaluates each test t in order until true, in which case value of corresponding v is returned. 
   * When all values are false, or null, returns the value of last v. 
   * Evaluations short circuits on truth. There are overloads with arity on test cases.   
   * <p>Example:
   * <pre>{@code
   * cond(() -> false, () -> "No",
   *      () -> true, () -> "Yes",
   *      () -> true, () -> "Already caught" ,
   *      () -> "catch all")  => "Yes"
   * }</pre>  
   */
  public static <T> T cond(
      Supplier<Boolean> t0, Supplier<? extends T> v0,
      Supplier<Boolean> t1, Supplier<? extends T> v1,
      Supplier<Boolean> t2, Supplier<? extends T> v2,
      Supplier<? extends T> v3){
    requireAllNonNull(t0, v0, t1, v1, t2, v2, v3);
    return trampoline(condRec(t0, v0, t1, v1, t2, v2, v3));
  }

  private static <T> Supplier<RecursiveVal<T>> condRec(
      Supplier<Boolean> t0, Supplier<? extends T> v0,
      Supplier<Boolean> t1, Supplier<? extends T> v1,
      Supplier<Boolean> t2, Supplier<? extends T> v2,
      Supplier<? extends T> v3){
    return ()->ifelse(truth(t0.get()),
                      ()->done(v0.get()),
                      ()->recur(condRec(t1, v1, t2, v2, v3)));
   }

  /**
   * Evaluates each test t in order until true, in which case value of corresponding v is returned. 
   * When all values are false, or null, returns the value of last v. 
   * Evaluations short circuits on truth. There are overloads with arity on test cases.   
   * <p>Example:
   * <pre>{@code
   * cond(() -> false, () -> "No",
   *      () -> true, () -> "Yes",
   *      () -> true, () -> "Already caught" ,
   *      () -> "catch all")  => "Yes"
   * }</pre>  
   */
  public static <T> T cond(
      Supplier<Boolean> t0, Supplier<? extends T> v0,
      Supplier<Boolean> t1, Supplier<? extends T> v1,
      Supplier<Boolean> t2, Supplier<? extends T> v2,
      Supplier<Boolean> t3, Supplier<? extends T> v3,
      Supplier<? extends T> v4){
    requireAllNonNull(t0, v0, t1, v1, t2, v2, t3, v3, v4);
    return trampoline(condRec(t0, v0, t1, v1, t2, v2, t3, v3, v4));
  }

  private static <T> Supplier<RecursiveVal<T>> condRec(
      Supplier<Boolean> t0, Supplier<? extends T> v0,
      Supplier<Boolean> t1, Supplier<? extends T> v1,
      Supplier<Boolean> t2, Supplier<? extends T> v2,
      Supplier<Boolean> t3, Supplier<? extends T> v3,
      Supplier<? extends T> v4){
    return ()->ifelse(truth(t0.get()),
                      ()->done(v0.get()),
                      ()->recur(condRec(t1, v1, t2, v2, t3, v3, v4)));
   }

  /**
   * Evaluates each test t in order until true, in which case value of corresponding v is returned. 
   * When all values are false, or null, returns the value of last v. 
   * Evaluations short circuits on truth. There are overloads with arity on test cases.   
   * <p>Example:
   * <pre>{@code
   * cond(() -> false, () -> "No",
   *      () -> true, () -> "Yes",
   *      () -> true, () -> "Already caught" ,
   *      () -> "catch all")  => "Yes"
   * }</pre>  
   */
  public static <T> T cond(
      Supplier<Boolean> t0, Supplier<? extends T> v0,
      Supplier<Boolean> t1, Supplier<? extends T> v1,
      Supplier<Boolean> t2, Supplier<? extends T> v2,
      Supplier<Boolean> t3, Supplier<? extends T> v3,
      Supplier<Boolean> t4, Supplier<? extends T> v4,
      Supplier<? extends T> v5){
    requireAllNonNull(t0, v0, t1, v1, t2, v2, t3, v3, t4, v4, v5);
    return trampoline(condRec(t0, v0, t1, v1, t2, v2, t3, v3, t4, v4, v5));
  }

  private static <T> Supplier<RecursiveVal<T>> condRec(
      Supplier<Boolean> t0, Supplier<? extends T> v0,
      Supplier<Boolean> t1, Supplier<? extends T> v1,
      Supplier<Boolean> t2, Supplier<? extends T> v2,
      Supplier<Boolean> t3, Supplier<? extends T> v3,
      Supplier<Boolean> t4, Supplier<? extends T> v4,
      Supplier<? extends T> v5){
    return ()->ifelse(truth(t0.get()),
                      ()->done(v0.get()),
                      ()->recur(condRec(t1, v1, t2, v2, t3, v3, t4, v4, v5)));
   }

  /**
   * Evaluates each test t in order until true, in which case value of corresponding v is returned. 
   * When all values are false, or null, returns the value of last v. 
   * Evaluations short circuits on truth. There are overloads with arity on test cases.   
   * <p>Example:
   * <pre>{@code
   * cond(() -> false, () -> "No",
   *      () -> true, () -> "Yes",
   *      () -> true, () -> "Already caught" ,
   *      () -> "catch all")  => "Yes"
   * }</pre>  
   */
  public static <T> T cond(
      Supplier<Boolean> t0, Supplier<? extends T> v0,
      Supplier<Boolean> t1, Supplier<? extends T> v1,
      Supplier<Boolean> t2, Supplier<? extends T> v2,
      Supplier<Boolean> t3, Supplier<? extends T> v3,
      Supplier<Boolean> t4, Supplier<? extends T> v4,
      Supplier<Boolean> t5, Supplier<? extends T> v5,
      Supplier<? extends T> v6){
    requireAllNonNull(t0, v0, t1, v1, t2, v2, t3, v3, t4, v4, t5, v5, v6);
    return trampoline(condRec(t0, v0, t1, v1, t2, v2, t3, v3, t4, v4, t5, v5, v6));
  }

  private static <T> Supplier<RecursiveVal<T>> condRec(
      Supplier<Boolean> t0, Supplier<? extends T> v0,
      Supplier<Boolean> t1, Supplier<? extends T> v1,
      Supplier<Boolean> t2, Supplier<? extends T> v2,
      Supplier<Boolean> t3, Supplier<? extends T> v3,
      Supplier<Boolean> t4, Supplier<? extends T> v4,
      Supplier<Boolean> t5, Supplier<? extends T> v5,
      Supplier<? extends T> v6){
    return ()->ifelse(truth(t0.get()),
                      ()->done(v0.get()),
                      ()->recur(condRec(t1, v1, t2, v2, t3, v3, t4, v4, t5, v5, v6)));
   }

  /**
   * Evaluates each test t in order until true, in which case value of corresponding v is returned. 
   * When all values are false, or null, returns the value of last v. 
   * Evaluations short circuits on truth. There are overloads with arity on test cases.   
   * <p>Example:
   * <pre>{@code
   * cond(() -> false, () -> "No",
   *      () -> true, () -> "Yes",
   *      () -> true, () -> "Already caught" ,
   *      () -> "catch all")  => "Yes"
   * }</pre>  
   */
  public static <T> T cond(
      Supplier<Boolean> t0, Supplier<? extends T> v0,
      Supplier<Boolean> t1, Supplier<? extends T> v1,
      Supplier<Boolean> t2, Supplier<? extends T> v2,
      Supplier<Boolean> t3, Supplier<? extends T> v3,
      Supplier<Boolean> t4, Supplier<? extends T> v4,
      Supplier<Boolean> t5, Supplier<? extends T> v5,
      Supplier<Boolean> t6, Supplier<? extends T> v6,
      Supplier<? extends T> v7){
    requireAllNonNull(t0, v0, t1, v1, t2, v2, t3, v3, t4, v4, t5, v5, t6, v6, v7);
    return trampoline(condRec(t0, v0, t1, v1, t2, v2, t3, v3, t4, v4, t5, v5, t6, v6, v7));
  }

  private static <T> Supplier<RecursiveVal<T>> condRec(
      Supplier<Boolean> t0, Supplier<? extends T> v0,
      Supplier<Boolean> t1, Supplier<? extends T> v1,
      Supplier<Boolean> t2, Supplier<? extends T> v2,
      Supplier<Boolean> t3, Supplier<? extends T> v3,
      Supplier<Boolean> t4, Supplier<? extends T> v4,
      Supplier<Boolean> t5, Supplier<? extends T> v5,
      Supplier<Boolean> t6, Supplier<? extends T> v6,
      Supplier<? extends T> v7){
    return ()->ifelse(truth(t0.get()),
                      ()->done(v0.get()),
                      ()->recur(condRec(t1, v1, t2, v2, t3, v3, t4, v4, t5, v5, t6, v6, v7)));
   }

  /**
   * Evaluates each test t in order until true, in which case value of corresponding v is returned. 
   * When all values are false, or null, returns the value of last v. 
   * Evaluations short circuits on truth. There are overloads with arity on test cases.   
   * <p>Example:
   * <pre>{@code
   * cond(() -> false, () -> "No",
   *      () -> true, () -> "Yes",
   *      () -> true, () -> "Already caught" ,
   *      () -> "catch all")  => "Yes"
   * }</pre>  
   */
  public static <T> T cond(
      Supplier<Boolean> t0, Supplier<? extends T> v0,
      Supplier<Boolean> t1, Supplier<? extends T> v1,
      Supplier<Boolean> t2, Supplier<? extends T> v2,
      Supplier<Boolean> t3, Supplier<? extends T> v3,
      Supplier<Boolean> t4, Supplier<? extends T> v4,
      Supplier<Boolean> t5, Supplier<? extends T> v5,
      Supplier<Boolean> t6, Supplier<? extends T> v6,
      Supplier<Boolean> t7, Supplier<? extends T> v7,
      Supplier<? extends T> v8){
    requireAllNonNull(t0, v0, t1, v1, t2, v2, t3, v3, t4, v4, t5, v5, t6, v6, t7, v7, v8);
    return trampoline(condRec(t0, v0, t1, v1, t2, v2, t3, v3, t4, v4, t5, v5, t6, v6, t7, v7, v8));
  }

  private static <T> Supplier<RecursiveVal<T>> condRec(
      Supplier<Boolean> t0, Supplier<? extends T> v0,
      Supplier<Boolean> t1, Supplier<? extends T> v1,
      Supplier<Boolean> t2, Supplier<? extends T> v2,
      Supplier<Boolean> t3, Supplier<? extends T> v3,
      Supplier<Boolean> t4, Supplier<? extends T> v4,
      Supplier<Boolean> t5, Supplier<? extends T> v5,
      Supplier<Boolean> t6, Supplier<? extends T> v6,
      Supplier<Boolean> t7, Supplier<? extends T> v7,
      Supplier<? extends T> v8){
    return ()->ifelse(truth(t0.get()),
                      ()->done(v0.get()),
                      ()->recur(condRec(t1, v1, t2, v2, t3, v3, t4, v4, t5, v5, t6, v6, t7, v7, v8)));
   }

  /**
   * Evaluates each test t in order until true, in which case value of corresponding v is returned. 
   * When all values are false, or null, returns the value of last v. 
   * Evaluations short circuits on truth. There are overloads with arity on test cases.   
   * <p>Example:
   * <pre>{@code
   * cond(() -> false, () -> "No",
   *      () -> true, () -> "Yes",
   *      () -> true, () -> "Already caught" ,
   *      () -> "catch all")  => "Yes"
   * }</pre>  
   */
  public static <T> T cond(
      Supplier<Boolean> t0, Supplier<? extends T> v0,
      Supplier<Boolean> t1, Supplier<? extends T> v1,
      Supplier<Boolean> t2, Supplier<? extends T> v2,
      Supplier<Boolean> t3, Supplier<? extends T> v3,
      Supplier<Boolean> t4, Supplier<? extends T> v4,
      Supplier<Boolean> t5, Supplier<? extends T> v5,
      Supplier<Boolean> t6, Supplier<? extends T> v6,
      Supplier<Boolean> t7, Supplier<? extends T> v7,
      Supplier<Boolean> t8, Supplier<? extends T> v8,
      Supplier<? extends T> v9){
    requireAllNonNull(t0, v0, t1, v1, t2, v2, t3, v3, t4, v4, t5, v5, t6, v6, t7, v7, t8, v8, v9);
    return trampoline(condRec(t0, v0, t1, v1, t2, v2, t3, v3, t4, v4, t5, v5, t6, v6, t7, v7, t8, v8, v9));
  }

  private static <T> Supplier<RecursiveVal<T>> condRec(
      Supplier<Boolean> t0, Supplier<? extends T> v0,
      Supplier<Boolean> t1, Supplier<? extends T> v1,
      Supplier<Boolean> t2, Supplier<? extends T> v2,
      Supplier<Boolean> t3, Supplier<? extends T> v3,
      Supplier<Boolean> t4, Supplier<? extends T> v4,
      Supplier<Boolean> t5, Supplier<? extends T> v5,
      Supplier<Boolean> t6, Supplier<? extends T> v6,
      Supplier<Boolean> t7, Supplier<? extends T> v7,
      Supplier<Boolean> t8, Supplier<? extends T> v8,
      Supplier<? extends T> v9){
    return ()->ifelse(truth(t0.get()),
                      ()->done(v0.get()),
                      ()->recur(condRec(t1, v1, t2, v2, t3, v3, t4, v4, t5, v5, t6, v6, t7, v7, t8, v8, v9)));
   }

  /**
   * Evaluates each test t in order until true, in which case value of corresponding v is returned. 
   * When all values are false, or null, returns the value of last v. 
   * Evaluations short circuits on truth. There are overloads with arity on test cases.   
   * <p>Example:
   * <pre>{@code
   * cond(() -> false, () -> "No",
   *      () -> true, () -> "Yes",
   *      () -> true, () -> "Already caught" ,
   *      () -> "catch all")  => "Yes"
   * }</pre>  
   */
  public static <T> T cond(
      Supplier<Boolean> t0, Supplier<? extends T> v0,
      Supplier<Boolean> t1, Supplier<? extends T> v1,
      Supplier<Boolean> t2, Supplier<? extends T> v2,
      Supplier<Boolean> t3, Supplier<? extends T> v3,
      Supplier<Boolean> t4, Supplier<? extends T> v4,
      Supplier<Boolean> t5, Supplier<? extends T> v5,
      Supplier<Boolean> t6, Supplier<? extends T> v6,
      Supplier<Boolean> t7, Supplier<? extends T> v7,
      Supplier<Boolean> t8, Supplier<? extends T> v8,
      Supplier<Boolean> t9, Supplier<? extends T> v9,
      Supplier<? extends T> v10){
    requireAllNonNull(t0, v0, t1, v1, t2, v2, t3, v3, t4, v4, t5, v5, t6, v6, t7, v7, t8, v8, t9, v9, v10);
    return trampoline(condRec(t0, v0, t1, v1, t2, v2, t3, v3, t4, v4, t5, v5, t6, v6, t7, v7, t8, v8, t9, v9, v10));
  }

  private static <T> Supplier<RecursiveVal<T>> condRec(
      Supplier<Boolean> t0, Supplier<? extends T> v0,
      Supplier<Boolean> t1, Supplier<? extends T> v1,
      Supplier<Boolean> t2, Supplier<? extends T> v2,
      Supplier<Boolean> t3, Supplier<? extends T> v3,
      Supplier<Boolean> t4, Supplier<? extends T> v4,
      Supplier<Boolean> t5, Supplier<? extends T> v5,
      Supplier<Boolean> t6, Supplier<? extends T> v6,
      Supplier<Boolean> t7, Supplier<? extends T> v7,
      Supplier<Boolean> t8, Supplier<? extends T> v8,
      Supplier<Boolean> t9, Supplier<? extends T> v9,
      Supplier<? extends T> v10){
    return ()->ifelse(truth(t0.get()),
                      ()->done(v0.get()),
                      ()->recur(condRec(t1, v1, t2, v2, t3, v3, t4, v4, t5, v5, t6, v6, t7, v7, t8, v8, t9, v9, v10)));
   }

   private static <T> Supplier<RecursiveVal<T>> condRec(
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
   * <p>Example:
   * <pre>{@code
   * ifelse(true, () -> "Yes", () -> "No") => "Yes"
   * ifelse(false, () -> "Yes", () -> "No") => "No"
   * }</pre>  
   */
  public static <T> T ifelse(boolean test, Supplier<T> then,
                             Supplier<T> otherwise) {
    requireNonNull(then);
    requireNonNull(otherwise);
    return test ? then.get() : otherwise.get();
  }

  /**
   * Then is evaluated and its value returned if test is true. Otherwise null is returned
   * <p>Example:
   * <pre>{@code
   * when(true, () -> "Yes") => "Yes"
   * when(false, () -> "Yes") => null
   * }</pre>
   */
  public static <T> T when(boolean test, Supplier<T> then){
    return ifelse(test, then, ()->null);
  }

  /**
   * Evaluates then and returns its value unless test is true, in which case otherwise is evaluated and its value returned. 
   <p>Example:
   * <pre>{@code
   * ifNot(true, () -> "Yes", () -> "No") => "No"
   * ifNot(false, () -> "Yes", () -> "No") => "Yes"
   * }</pre>
   */
  public static <T> T ifNot(boolean test, Supplier<T> then, Supplier<T> otherwise){
    return ifelse(test, otherwise, then);
  }

  /**
   * Then is evaluated and its value returned if test is false. Otherwise null is returned   
   * <p>Example:
   * <pre>{@code
   * whenNot(true, () -> "Yes") => null
   * whenNot(false, () -> "Yes") => "Yes"
   * }</pre>
   */
  public static <T> T whenNot(boolean test, Supplier<T> then){
    return ifelse(test, ()->null, then);
  }

  /**
   * Returns evaluation of then, unless test happen to be true, in which case the evaluation of otherwise is returned
   * <p>Example:
   * <pre>{@code
   * unless(() -> "Yes", true, () -> "No") => "Yes"
   * unless(() -> "Yes", false, () -> "No") => "No"
   * }</pre>
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
   * <p>Example:
   * <pre>{@code
   * unlessNull("Yes", ()->"No") => "Yes"
   * unlessNull(null, ()->"No") => "No"
   * }</pre>
   */
  public static <T> T unlessNull( T t, Supplier<? extends T> then){
    requireNonNull(then);
    if(isNull(t))
      return then.get();
    return t;
  }

  /**
   * Return application of t to f, unless t is null, in which case evaluation of supplier s returns
   * <p>Example:
   * <pre>{@code
   * mapOr("Yes", String::toUpperCase,()-> "No") => "YES"
   * mapOr(((String)null), String::toUpperCase,()-> "No") => "No"
   * }</pre>
   */
  public static <T, V> V mapOr(T t, Function<T, V> f, Supplier<V> s) {
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
   * Throws null pointer exception if any is null
   */
  public static <T> void requireAllNonNull(Object... nonNulls) {
        for (Object o: nonNulls) {
          requireNonNull(o);
        }
    }
}
