package stonehorse.candy;

import java.util.function.Function;
import static stonehorse.candy.Trampoline.done;
import stonehorse.candy.Trampoline.RecursiveVal;
import static stonehorse.candy.Trampoline.trampoline;
import static stonehorse.candy.Trampoline.recur;

/**
 * Functions for function composition in reversed order, in order to visually serialize nested funktion calls.<p>
 * 
 * {@link #thread(Object, Function, Function)} The result of applying the first argument to the first function is applied to the second function. There are overloads with arity on functions<br>
 * {@link #threadMaybe(Object, Function, Function)} Like thread except that the call chain terminates if a function return null.
 */

public class Threading{
  private Threading(){}
/**
 * t is returned
 */
  public static <T> T thread( T t){
    return t;
  }
/**
 * t is applied to f
 */
  public static <T0, T1> T1 thread( T0 t, Function<T0, T1> f){
    return f.apply(t);
  }

  private static <T0,T1> RecursiveVal<T1> threadRec(T0 t0, Function<T0,T1> f){
    return done(f.apply(t0));
  }

/**
 * t is returned
 */
  public static <T> T threadMaybe( T t){
    return t;
  }
/**
 * t is applied to f unless it is null
 */
  public static <T0, T1> T1 threadMaybe( T0 t, Function<T0, T1> f){
    if(t!=null)
      return f.apply(t);
    return null;
  }

  private static <T0,T1> RecursiveVal<T1> threadMaybeRec(T0 t0, Function<T0,T1> f){
    if(t0!=null)
      return done(f.apply(t0));
    else return done(null);
  }
  /**
  * The result of applying the first argument t0 to the first function f0 is applied to the second function f1 and so on
  */
   public static <T0,T1,T2> T2 thread(T0 t0,
    Function<T0, T1> f0,
    Function<T1, T2> f1){
    return trampoline(()->threadRec(t0, f0,f1));
  }

  /**
  * The result of applying the first argument t0 to the first function f0 is applied to the second function f1 unless its null and so on
  */
  public static <T0,T1,T2> T2 threadMaybe(T0 t0, 
    Function<T0, T1> f0,
    Function<T1, T2> f1){
    return trampoline(()->threadMaybeRec(t0,f0,f1));
  }

  private static <T0,T1,T2> RecursiveVal<T2> threadRec(T0 t0,
    Function<T0, T1> f0,
    Function<T1, T2> f1){
    return recur(()->threadRec(f0.apply(t0),f1));
  }

  private static <T0,T1,T2> RecursiveVal<T2> threadMaybeRec(T0 t0,
    Function<T0, T1> f0,
    Function<T1, T2> f1){
    T1 v = f0.apply(t0);
    if(v==null)
      return done(null);
    return recur(()->threadMaybeRec(v,f1));
  }

  /**
  * The result of applying the first argument t0 to the first function f0 is applied to the second function f1 and so on
  */
   public static <T0,T1,T2,T3> T3 thread(T0 t0,
    Function<T0, T1> f0,
    Function<T1, T2> f1,
    Function<T2, T3> f2){
    return trampoline(()->threadRec(t0, f0,f1,f2));
  }

  /**
  * The result of applying the first argument t0 to the first function f0 is applied to the second function f1 unless its null and so on
  */
  public static <T0,T1,T2,T3> T3 threadMaybe(T0 t0, 
    Function<T0, T1> f0,
    Function<T1, T2> f1,
    Function<T2, T3> f2){
    return trampoline(()->threadMaybeRec(t0,f0,f1,f2));
  }

  private static <T0,T1,T2,T3> RecursiveVal<T3> threadRec(T0 t0,
    Function<T0, T1> f0,
    Function<T1, T2> f1,
    Function<T2, T3> f2){
    return recur(()->threadRec(f0.apply(t0),f1,f2));
  }

  private static <T0,T1,T2,T3> RecursiveVal<T3> threadMaybeRec(T0 t0,
    Function<T0, T1> f0,
    Function<T1, T2> f1,
    Function<T2, T3> f2){
    T1 v = f0.apply(t0);
    if(v==null)
      return done(null);
    return recur(()->threadMaybeRec(v,f1,f2));
  }

  /**
  * The result of applying the first argument t0 to the first function f0 is applied to the second function f1 and so on
  */
   public static <T0,T1,T2,T3,T4> T4 thread(T0 t0,
    Function<T0, T1> f0,
    Function<T1, T2> f1,
    Function<T2, T3> f2,
    Function<T3, T4> f3){
    return trampoline(()->threadRec(t0, f0,f1,f2,f3));
  }

  /**
  * The result of applying the first argument t0 to the first function f0 is applied to the second function f1 unless its null and so on
  */
  public static <T0,T1,T2,T3,T4> T4 threadMaybe(T0 t0, 
    Function<T0, T1> f0,
    Function<T1, T2> f1,
    Function<T2, T3> f2,
    Function<T3, T4> f3){
    return trampoline(()->threadMaybeRec(t0,f0,f1,f2,f3));
  }

  private static <T0,T1,T2,T3,T4> RecursiveVal<T4> threadRec(T0 t0,
    Function<T0, T1> f0,
    Function<T1, T2> f1,
    Function<T2, T3> f2,
    Function<T3, T4> f3){
    return recur(()->threadRec(f0.apply(t0),f1,f2,f3));
  }

  private static <T0,T1,T2,T3,T4> RecursiveVal<T4> threadMaybeRec(T0 t0,
    Function<T0, T1> f0,
    Function<T1, T2> f1,
    Function<T2, T3> f2,
    Function<T3, T4> f3){
    T1 v = f0.apply(t0);
    if(v==null)
      return done(null);
    return recur(()->threadMaybeRec(v,f1,f2,f3));
  }

  /**
  * The result of applying the first argument t0 to the first function f0 is applied to the second function f1 and so on
  */
   public static <T0,T1,T2,T3,T4,T5> T5 thread(T0 t0,
    Function<T0, T1> f0,
    Function<T1, T2> f1,
    Function<T2, T3> f2,
    Function<T3, T4> f3,
    Function<T4, T5> f4){
    return trampoline(()->threadRec(t0, f0,f1,f2,f3,f4));
  }

  /**
  * The result of applying the first argument t0 to the first function f0 is applied to the second function f1 unless its null and so on
  */
  public static <T0,T1,T2,T3,T4,T5> T5 threadMaybe(T0 t0, 
    Function<T0, T1> f0,
    Function<T1, T2> f1,
    Function<T2, T3> f2,
    Function<T3, T4> f3,
    Function<T4, T5> f4){
    return trampoline(()->threadMaybeRec(t0,f0,f1,f2,f3,f4));
  }

  private static <T0,T1,T2,T3,T4,T5> RecursiveVal<T5> threadRec(T0 t0,
    Function<T0, T1> f0,
    Function<T1, T2> f1,
    Function<T2, T3> f2,
    Function<T3, T4> f3,
    Function<T4, T5> f4){
    return recur(()->threadRec(f0.apply(t0),f1,f2,f3,f4));
  }

  private static <T0,T1,T2,T3,T4,T5> RecursiveVal<T5> threadMaybeRec(T0 t0,
    Function<T0, T1> f0,
    Function<T1, T2> f1,
    Function<T2, T3> f2,
    Function<T3, T4> f3,
    Function<T4, T5> f4){
    T1 v = f0.apply(t0);
    if(v==null)
      return done(null);
    return recur(()->threadMaybeRec(v,f1,f2,f3,f4));
  }

  /**
  * The result of applying the first argument t0 to the first function f0 is applied to the second function f1 and so on
  */
   public static <T0,T1,T2,T3,T4,T5,T6> T6 thread(T0 t0,
    Function<T0, T1> f0,
    Function<T1, T2> f1,
    Function<T2, T3> f2,
    Function<T3, T4> f3,
    Function<T4, T5> f4,
    Function<T5, T6> f5){
    return trampoline(()->threadRec(t0, f0,f1,f2,f3,f4,f5));
  }

  /**
  * The result of applying the first argument t0 to the first function f0 is applied to the second function f1 unless its null and so on
  */
  public static <T0,T1,T2,T3,T4,T5,T6> T6 threadMaybe(T0 t0, 
    Function<T0, T1> f0,
    Function<T1, T2> f1,
    Function<T2, T3> f2,
    Function<T3, T4> f3,
    Function<T4, T5> f4,
    Function<T5, T6> f5){
    return trampoline(()->threadMaybeRec(t0,f0,f1,f2,f3,f4,f5));
  }

  private static <T0,T1,T2,T3,T4,T5,T6> RecursiveVal<T6> threadRec(T0 t0,
    Function<T0, T1> f0,
    Function<T1, T2> f1,
    Function<T2, T3> f2,
    Function<T3, T4> f3,
    Function<T4, T5> f4,
    Function<T5, T6> f5){
    return recur(()->threadRec(f0.apply(t0),f1,f2,f3,f4,f5));
  }

  private static <T0,T1,T2,T3,T4,T5,T6> RecursiveVal<T6> threadMaybeRec(T0 t0,
    Function<T0, T1> f0,
    Function<T1, T2> f1,
    Function<T2, T3> f2,
    Function<T3, T4> f3,
    Function<T4, T5> f4,
    Function<T5, T6> f5){
    T1 v = f0.apply(t0);
    if(v==null)
      return done(null);
    return recur(()->threadMaybeRec(v,f1,f2,f3,f4,f5));
  }

  /**
  * The result of applying the first argument t0 to the first function f0 is applied to the second function f1 and so on
  */
   public static <T0,T1,T2,T3,T4,T5,T6,T7> T7 thread(T0 t0,
    Function<T0, T1> f0,
    Function<T1, T2> f1,
    Function<T2, T3> f2,
    Function<T3, T4> f3,
    Function<T4, T5> f4,
    Function<T5, T6> f5,
    Function<T6, T7> f6){
    return trampoline(()->threadRec(t0, f0,f1,f2,f3,f4,f5,f6));
  }

  /**
  * The result of applying the first argument t0 to the first function f0 is applied to the second function f1 unless its null and so on
  */
  public static <T0,T1,T2,T3,T4,T5,T6,T7> T7 threadMaybe(T0 t0, 
    Function<T0, T1> f0,
    Function<T1, T2> f1,
    Function<T2, T3> f2,
    Function<T3, T4> f3,
    Function<T4, T5> f4,
    Function<T5, T6> f5,
    Function<T6, T7> f6){
    return trampoline(()->threadMaybeRec(t0,f0,f1,f2,f3,f4,f5,f6));
  }

  private static <T0,T1,T2,T3,T4,T5,T6,T7> RecursiveVal<T7> threadRec(T0 t0,
    Function<T0, T1> f0,
    Function<T1, T2> f1,
    Function<T2, T3> f2,
    Function<T3, T4> f3,
    Function<T4, T5> f4,
    Function<T5, T6> f5,
    Function<T6, T7> f6){
    return recur(()->threadRec(f0.apply(t0),f1,f2,f3,f4,f5,f6));
  }

  private static <T0,T1,T2,T3,T4,T5,T6,T7> RecursiveVal<T7> threadMaybeRec(T0 t0,
    Function<T0, T1> f0,
    Function<T1, T2> f1,
    Function<T2, T3> f2,
    Function<T3, T4> f3,
    Function<T4, T5> f4,
    Function<T5, T6> f5,
    Function<T6, T7> f6){
    T1 v = f0.apply(t0);
    if(v==null)
      return done(null);
    return recur(()->threadMaybeRec(v,f1,f2,f3,f4,f5,f6));
  }

  /**
  * The result of applying the first argument t0 to the first function f0 is applied to the second function f1 and so on
  */
   public static <T0,T1,T2,T3,T4,T5,T6,T7,T8> T8 thread(T0 t0,
    Function<T0, T1> f0,
    Function<T1, T2> f1,
    Function<T2, T3> f2,
    Function<T3, T4> f3,
    Function<T4, T5> f4,
    Function<T5, T6> f5,
    Function<T6, T7> f6,
    Function<T7, T8> f7){
    return trampoline(()->threadRec(t0, f0,f1,f2,f3,f4,f5,f6,f7));
  }

  /**
  * The result of applying the first argument t0 to the first function f0 is applied to the second function f1 unless its null and so on
  */
  public static <T0,T1,T2,T3,T4,T5,T6,T7,T8> T8 threadMaybe(T0 t0, 
    Function<T0, T1> f0,
    Function<T1, T2> f1,
    Function<T2, T3> f2,
    Function<T3, T4> f3,
    Function<T4, T5> f4,
    Function<T5, T6> f5,
    Function<T6, T7> f6,
    Function<T7, T8> f7){
    return trampoline(()->threadMaybeRec(t0,f0,f1,f2,f3,f4,f5,f6,f7));
  }

  private static <T0,T1,T2,T3,T4,T5,T6,T7,T8> RecursiveVal<T8> threadRec(T0 t0,
    Function<T0, T1> f0,
    Function<T1, T2> f1,
    Function<T2, T3> f2,
    Function<T3, T4> f3,
    Function<T4, T5> f4,
    Function<T5, T6> f5,
    Function<T6, T7> f6,
    Function<T7, T8> f7){
    return recur(()->threadRec(f0.apply(t0),f1,f2,f3,f4,f5,f6,f7));
  }

  private static <T0,T1,T2,T3,T4,T5,T6,T7,T8> RecursiveVal<T8> threadMaybeRec(T0 t0,
    Function<T0, T1> f0,
    Function<T1, T2> f1,
    Function<T2, T3> f2,
    Function<T3, T4> f3,
    Function<T4, T5> f4,
    Function<T5, T6> f5,
    Function<T6, T7> f6,
    Function<T7, T8> f7){
    T1 v = f0.apply(t0);
    if(v==null)
      return done(null);
    return recur(()->threadMaybeRec(v,f1,f2,f3,f4,f5,f6,f7));
  }

  /**
  * The result of applying the first argument t0 to the first function f0 is applied to the second function f1 and so on
  */
   public static <T0,T1,T2,T3,T4,T5,T6,T7,T8,T9> T9 thread(T0 t0,
    Function<T0, T1> f0,
    Function<T1, T2> f1,
    Function<T2, T3> f2,
    Function<T3, T4> f3,
    Function<T4, T5> f4,
    Function<T5, T6> f5,
    Function<T6, T7> f6,
    Function<T7, T8> f7,
    Function<T8, T9> f8){
    return trampoline(()->threadRec(t0, f0,f1,f2,f3,f4,f5,f6,f7,f8));
  }

  /**
  * The result of applying the first argument t0 to the first function f0 is applied to the second function f1 unless its null and so on
  */
  public static <T0,T1,T2,T3,T4,T5,T6,T7,T8,T9> T9 threadMaybe(T0 t0, 
    Function<T0, T1> f0,
    Function<T1, T2> f1,
    Function<T2, T3> f2,
    Function<T3, T4> f3,
    Function<T4, T5> f4,
    Function<T5, T6> f5,
    Function<T6, T7> f6,
    Function<T7, T8> f7,
    Function<T8, T9> f8){
    return trampoline(()->threadMaybeRec(t0,f0,f1,f2,f3,f4,f5,f6,f7,f8));
  }

  private static <T0,T1,T2,T3,T4,T5,T6,T7,T8,T9> RecursiveVal<T9> threadRec(T0 t0,
    Function<T0, T1> f0,
    Function<T1, T2> f1,
    Function<T2, T3> f2,
    Function<T3, T4> f3,
    Function<T4, T5> f4,
    Function<T5, T6> f5,
    Function<T6, T7> f6,
    Function<T7, T8> f7,
    Function<T8, T9> f8){
    return recur(()->threadRec(f0.apply(t0),f1,f2,f3,f4,f5,f6,f7,f8));
  }

  private static <T0,T1,T2,T3,T4,T5,T6,T7,T8,T9> RecursiveVal<T9> threadMaybeRec(T0 t0,
    Function<T0, T1> f0,
    Function<T1, T2> f1,
    Function<T2, T3> f2,
    Function<T3, T4> f3,
    Function<T4, T5> f4,
    Function<T5, T6> f5,
    Function<T6, T7> f6,
    Function<T7, T8> f7,
    Function<T8, T9> f8){
    T1 v = f0.apply(t0);
    if(v==null)
      return done(null);
    return recur(()->threadMaybeRec(v,f1,f2,f3,f4,f5,f6,f7,f8));
  }

  /**
  * The result of applying the first argument t0 to the first function f0 is applied to the second function f1 and so on
  */
   public static <T0,T1,T2,T3,T4,T5,T6,T7,T8,T9,T10> T10 thread(T0 t0,
    Function<T0, T1> f0,
    Function<T1, T2> f1,
    Function<T2, T3> f2,
    Function<T3, T4> f3,
    Function<T4, T5> f4,
    Function<T5, T6> f5,
    Function<T6, T7> f6,
    Function<T7, T8> f7,
    Function<T8, T9> f8,
    Function<T9, T10> f9){
    return trampoline(()->threadRec(t0, f0,f1,f2,f3,f4,f5,f6,f7,f8,f9));
  }

  /**
  * The result of applying the first argument t0 to the first function f0 is applied to the second function f1 unless its null and so on
  */
  public static <T0,T1,T2,T3,T4,T5,T6,T7,T8,T9,T10> T10 threadMaybe(T0 t0, 
    Function<T0, T1> f0,
    Function<T1, T2> f1,
    Function<T2, T3> f2,
    Function<T3, T4> f3,
    Function<T4, T5> f4,
    Function<T5, T6> f5,
    Function<T6, T7> f6,
    Function<T7, T8> f7,
    Function<T8, T9> f8,
    Function<T9, T10> f9){
    return trampoline(()->threadMaybeRec(t0,f0,f1,f2,f3,f4,f5,f6,f7,f8,f9));
  }

  private static <T0,T1,T2,T3,T4,T5,T6,T7,T8,T9,T10> RecursiveVal<T10> threadRec(T0 t0,
    Function<T0, T1> f0,
    Function<T1, T2> f1,
    Function<T2, T3> f2,
    Function<T3, T4> f3,
    Function<T4, T5> f4,
    Function<T5, T6> f5,
    Function<T6, T7> f6,
    Function<T7, T8> f7,
    Function<T8, T9> f8,
    Function<T9, T10> f9){
    return recur(()->threadRec(f0.apply(t0),f1,f2,f3,f4,f5,f6,f7,f8,f9));
  }

  private static <T0,T1,T2,T3,T4,T5,T6,T7,T8,T9,T10> RecursiveVal<T10> threadMaybeRec(T0 t0,
    Function<T0, T1> f0,
    Function<T1, T2> f1,
    Function<T2, T3> f2,
    Function<T3, T4> f3,
    Function<T4, T5> f4,
    Function<T5, T6> f5,
    Function<T6, T7> f6,
    Function<T7, T8> f7,
    Function<T8, T9> f8,
    Function<T9, T10> f9){
    T1 v = f0.apply(t0);
    if(v==null)
      return done(null);
    return recur(()->threadMaybeRec(v,f1,f2,f3,f4,f5,f6,f7,f8,f9));
  }

  /**
  * The result of applying the first argument t0 to the first function f0 is applied to the second function f1 and so on
  */
   public static <T0,T1,T2,T3,T4,T5,T6,T7,T8,T9,T10,T11> T11 thread(T0 t0,
    Function<T0, T1> f0,
    Function<T1, T2> f1,
    Function<T2, T3> f2,
    Function<T3, T4> f3,
    Function<T4, T5> f4,
    Function<T5, T6> f5,
    Function<T6, T7> f6,
    Function<T7, T8> f7,
    Function<T8, T9> f8,
    Function<T9, T10> f9,
    Function<T10, T11> f10){
    return trampoline(()->threadRec(t0, f0,f1,f2,f3,f4,f5,f6,f7,f8,f9,f10));
  }

  /**
  * The result of applying the first argument t0 to the first function f0 is applied to the second function f1 unless its null and so on
  */
  public static <T0,T1,T2,T3,T4,T5,T6,T7,T8,T9,T10,T11> T11 threadMaybe(T0 t0, 
    Function<T0, T1> f0,
    Function<T1, T2> f1,
    Function<T2, T3> f2,
    Function<T3, T4> f3,
    Function<T4, T5> f4,
    Function<T5, T6> f5,
    Function<T6, T7> f6,
    Function<T7, T8> f7,
    Function<T8, T9> f8,
    Function<T9, T10> f9,
    Function<T10, T11> f10){
    return trampoline(()->threadMaybeRec(t0,f0,f1,f2,f3,f4,f5,f6,f7,f8,f9,f10));
  }

  private static <T0,T1,T2,T3,T4,T5,T6,T7,T8,T9,T10,T11> RecursiveVal<T11> threadRec(T0 t0,
    Function<T0, T1> f0,
    Function<T1, T2> f1,
    Function<T2, T3> f2,
    Function<T3, T4> f3,
    Function<T4, T5> f4,
    Function<T5, T6> f5,
    Function<T6, T7> f6,
    Function<T7, T8> f7,
    Function<T8, T9> f8,
    Function<T9, T10> f9,
    Function<T10, T11> f10){
    return recur(()->threadRec(f0.apply(t0),f1,f2,f3,f4,f5,f6,f7,f8,f9,f10));
  }

  private static <T0,T1,T2,T3,T4,T5,T6,T7,T8,T9,T10,T11> RecursiveVal<T11> threadMaybeRec(T0 t0,
    Function<T0, T1> f0,
    Function<T1, T2> f1,
    Function<T2, T3> f2,
    Function<T3, T4> f3,
    Function<T4, T5> f4,
    Function<T5, T6> f5,
    Function<T6, T7> f6,
    Function<T7, T8> f7,
    Function<T8, T9> f8,
    Function<T9, T10> f9,
    Function<T10, T11> f10){
    T1 v = f0.apply(t0);
    if(v==null)
      return done(null);
    return recur(()->threadMaybeRec(v,f1,f2,f3,f4,f5,f6,f7,f8,f9,f10));
  }

}
