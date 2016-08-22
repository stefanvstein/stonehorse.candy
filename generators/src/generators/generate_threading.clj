(ns generators.generate-threading
(:require [generators.common :refer :all]))


(defn transformation-funs [from to]
  (apply str 
         (interpose "," 
                    (map #(str 
                           (nline) 
                           "    Function<T" % ", T" (inc %) "> f" %) 
                         (range from to))))
  )

(defn thread-rec [n]
   (str "  private static <"
        (increasing-args "T" 0 (inc n))
        (str "> RecursiveVal<T" n "> threadRec(T0 t0,")
        (transformation-funs 0 n)
        "){"
        (nline)
        "    return recur(()->threadRec(f0.apply(t0),"
        (increasing-args "f" 1 n)
        "));"  
        (nline)
        "  }"))

(defn thread-maybe [n]
  (str "  /**
  * The result of applying the first argument t0 to the first function f0 is applied to the second function f1 unless its null and so on
  */
  public static <"
       (increasing-args "T" 0 (inc n))
       "> T" n " threadMaybe(T0 t0, " 
       (transformation-funs 0 n)
       "){"
       (nline)
       "    return trampoline(()->threadMaybeRec(t0,"
       (increasing-args "f" 0 n)
       "));"
       (nline)
       "  }"))

(defn thread-maybe-rec [n]
  (str "  private static <"
       (increasing-args "T" 0 (inc n))
       "> RecursiveVal<T" n "> threadMaybeRec(T0 t0,"
       (transformation-funs 0 n)
       "){
    T1 v = f0.apply(t0);
    if(v==null)
      return done(null);
    return recur(()->threadMaybeRec(v,"
       (increasing-args "f" 1 n)
       "));
  }"))

(defn thread [n]
  (str
   "  /**
  * The result of applying the first argument t0 to the first function f0 is applied to the second function f1 and so on
  */
   public static <"
       (increasing-args "T" 0 (inc n))
       "> T" n " thread(T0 t0,"
       (transformation-funs 0 n)
       "){
    return trampoline(()->threadRec(t0, "
       (increasing-args "f" 0 n)
       "));
  }"))

(defn generate [n package]
  (println (str "package " package ";

import java.util.function.Function;
import static " package ".Trampoline.done;
import " package ".Trampoline.RecursiveVal;
import static " package ".Trampoline.trampoline;
import static " package ".Trampoline.recur;

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
  }"))
  (dotimes [i n]
    (println (thread (inc (inc i))))
    (newline)
    (println (thread-maybe (inc (inc i))))
    (newline)
    (println (thread-rec (inc (inc i))))
    (newline)
    (println (thread-maybe-rec (inc (inc i))))
    (newline))

  (println "}"))
