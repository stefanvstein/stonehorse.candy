package stonehorse.candy;

import static stonehorse.candy.Functions.identity;
import static java.util.Objects.requireNonNull;
import java.util.Objects;
import java.util.function.Supplier;
import static stonehorse.candy.Choices.mapOr;
import static stonehorse.candy.Choices.ifelse;
import stonehorse.candy.Trampoline.RecursiveVal;
import static stonehorse.candy.Trampoline.done;
import static stonehorse.candy.Trampoline.recur;
import static stonehorse.candy.Trampoline.trampoline;
/**
 * Functions for basic logical operations on Suppliers. Functions do not chain automatically, as they are expected to receive lambda expressions. There are overload with different arity on Suppliers and they short circuit when the answer is found.
 * @see #and(Supplier)
 * @see #or(Supplier)
 * @see #anyEquals(Object, Supplier)
 * @see #allEquals(Object, Supplier)
 * @see Functions
 * @see NullableBooleans
 */
public class SupplierLogic{
  private SupplierLogic(){}
  /**
   * Are all suppliers returning true. There are overloads with different numbers of suppliers. The test will short circuit when there is a false value.
   * @see #or(Supplier)
   * @see #anyEquals(Object,Supplier)
   * @see #allEquals(Object,Supplier)
   */
  public static boolean and(Supplier<Boolean> b0){
    requireNonNull(b0);
    return mapOr(b0.get(), identity(), ()->false);
  }

  /**
   * Are any suppliers returning true. There are overloads with different numbers of suppliers. The test will short circuit when there is a true value.
   * @see #and(Supplier)
   * @see #anyEquals(Object,Supplier)
   * @see #allEquals(Object,Supplier)
   */
  public static boolean or(Supplier<Boolean> b0){
    requireNonNull(b0);
    return mapOr(b0.get(), identity(), ()->false);
  }

  /**
   * Do the values of all suppliers equal o. Short circuits when not. There are overrides with different arity 
   * @see #or(Supplier)
   * @see #and(Supplier)
   * @see #allEquals(Object,Supplier)
   */
  public static boolean anyEquals(Object o, Supplier<Object> s0){
    requireNonNull(s0);
    return Objects.equals(o, s0.get());
  }

  private static Supplier<RecursiveVal<Boolean>> anyEqualsRec(Object o, Supplier<Object> s0){
    return ()->done(Objects.equals(o, s0.get()));
  }

  /**
   * Do the values of all suppliers equal o. Short circuits when not. There are overrides with different arity 
   * @see #or(Supplier)
   * @see #and(Supplier)
   * @see #anyEquals(Object,Supplier)
   */
  public static boolean allEquals(Object o, Supplier<Object> s0){
    requireNonNull(s0);
    return Objects.equals(o, s0.get());
  }

  private static Supplier<RecursiveVal<Boolean>> allEqualsRec(Object o, Supplier<Object> s0){
    return ()->done(Objects.equals(o, s0.get()));
  }

  /**
   * Are all suppliers returning true. There are overloads with different numbers of suppliers. The test will short circuit when there is a false value.
   * @see #or(Supplier)
   * @see #anyEquals(Object,Supplier)
   * @see #allEquals(Object,Supplier)
   */
  public static boolean and(Supplier<Boolean> b0,
      Supplier<Boolean> b1){
    requireNonNull(b0);
    requireNonNull(b1);
    return mapOr(b0.get(), identity(), ()->false) &&
     mapOr(b1.get(), identity(), ()->false);
  }

  /**
   * Are any suppliers returning true. There are overloads with different numbers of suppliers. The test will short circuit when there is a true value.
   * @see #and(Supplier)
   * @see #anyEquals(Object,Supplier)
   * @see #allEquals(Object,Supplier)
   */
  public static boolean or(Supplier<Boolean> b0,
      Supplier<Boolean> b1){
    requireNonNull(b0);
    requireNonNull(b1);
    return mapOr(b0.get(), identity(), ()->false) ||
     mapOr(b1.get(), identity(), ()->false);
  }

  /**
   * Do the values of all suppliers equal o. Short circuits when not. There are overrides with different arity 
   * @see #or(Supplier)
   * @see #and(Supplier)
   * @see #allEquals(Object,Supplier)
   */
  public static boolean anyEquals(Object o,
       Supplier<Object> s0,
       Supplier<Object> s1){
    requireNonNull(s0);
    requireNonNull(s1);
    return trampoline(anyEqualsRec(o,s0,s1));
  }

  private static Supplier<RecursiveVal<Boolean>> anyEqualsRec(Object o,
       Supplier<Object> s0,
       Supplier<Object> s1){
    return ()->ifelse(Objects.equals(o, s0.get()),
               ()->done(true),
               ()->recur(anyEqualsRec(o,s1)));
  }

  /**
   * Do the values of all suppliers equal o. Short circuits when not. There are overrides with different arity 
   * @see #or(Supplier)
   * @see #and(Supplier)
   * @see #anyEquals(Object,Supplier)
   */
  public static boolean allEquals(Object o,
       Supplier<Object> s0,
       Supplier<Object> s1){
    requireNonNull(s0);
    requireNonNull(s1);
    return trampoline(allEqualsRec(o,s0,s1));
  }

  private static Supplier<RecursiveVal<Boolean>> allEqualsRec(Object o,
       Supplier<Object> s0,
       Supplier<Object> s1){
    return ()->ifelse(Objects.equals(o, s0.get()),
               ()->recur(allEqualsRec(o,s1)),
               ()->done(false));
  }

  /**
   * Are all suppliers returning true. There are overloads with different numbers of suppliers. The test will short circuit when there is a false value.
   * @see #or(Supplier)
   * @see #anyEquals(Object,Supplier)
   * @see #allEquals(Object,Supplier)
   */
  public static boolean and(Supplier<Boolean> b0,
      Supplier<Boolean> b1,
      Supplier<Boolean> b2){
    requireNonNull(b0);
    requireNonNull(b1);
    requireNonNull(b2);
    return mapOr(b0.get(), identity(), ()->false) &&
     mapOr(b1.get(), identity(), ()->false) &&
     mapOr(b2.get(), identity(), ()->false);
  }

  /**
   * Are any suppliers returning true. There are overloads with different numbers of suppliers. The test will short circuit when there is a true value.
   * @see #and(Supplier)
   * @see #anyEquals(Object,Supplier)
   * @see #allEquals(Object,Supplier)
   */
  public static boolean or(Supplier<Boolean> b0,
      Supplier<Boolean> b1,
      Supplier<Boolean> b2){
    requireNonNull(b0);
    requireNonNull(b1);
    requireNonNull(b2);
    return mapOr(b0.get(), identity(), ()->false) ||
     mapOr(b1.get(), identity(), ()->false) ||
     mapOr(b2.get(), identity(), ()->false);
  }

  /**
   * Do the values of all suppliers equal o. Short circuits when not. There are overrides with different arity 
   * @see #or(Supplier)
   * @see #and(Supplier)
   * @see #allEquals(Object,Supplier)
   */
  public static boolean anyEquals(Object o,
       Supplier<Object> s0,
       Supplier<Object> s1,
       Supplier<Object> s2){
    requireNonNull(s0);
    requireNonNull(s1);
    requireNonNull(s2);
    return trampoline(anyEqualsRec(o,s0,s1,s2));
  }

  private static Supplier<RecursiveVal<Boolean>> anyEqualsRec(Object o,
       Supplier<Object> s0,
       Supplier<Object> s1,
       Supplier<Object> s2){
    return ()->ifelse(Objects.equals(o, s0.get()),
               ()->done(true),
               ()->recur(anyEqualsRec(o,s1,s2)));
  }

  /**
   * Do the values of all suppliers equal o. Short circuits when not. There are overrides with different arity 
   * @see #or(Supplier)
   * @see #and(Supplier)
   * @see #anyEquals(Object,Supplier)
   */
  public static boolean allEquals(Object o,
       Supplier<Object> s0,
       Supplier<Object> s1,
       Supplier<Object> s2){
    requireNonNull(s0);
    requireNonNull(s1);
    requireNonNull(s2);
    return trampoline(allEqualsRec(o,s0,s1,s2));
  }

  private static Supplier<RecursiveVal<Boolean>> allEqualsRec(Object o,
       Supplier<Object> s0,
       Supplier<Object> s1,
       Supplier<Object> s2){
    return ()->ifelse(Objects.equals(o, s0.get()),
               ()->recur(allEqualsRec(o,s1,s2)),
               ()->done(false));
  }

  /**
   * Are all suppliers returning true. There are overloads with different numbers of suppliers. The test will short circuit when there is a false value.
   * @see #or(Supplier)
   * @see #anyEquals(Object,Supplier)
   * @see #allEquals(Object,Supplier)
   */
  public static boolean and(Supplier<Boolean> b0,
      Supplier<Boolean> b1,
      Supplier<Boolean> b2,
      Supplier<Boolean> b3){
    requireNonNull(b0);
    requireNonNull(b1);
    requireNonNull(b2);
    requireNonNull(b3);
    return mapOr(b0.get(), identity(), ()->false) &&
     mapOr(b1.get(), identity(), ()->false) &&
     mapOr(b2.get(), identity(), ()->false) &&
     mapOr(b3.get(), identity(), ()->false);
  }

  /**
   * Are any suppliers returning true. There are overloads with different numbers of suppliers. The test will short circuit when there is a true value.
   * @see #and(Supplier)
   * @see #anyEquals(Object,Supplier)
   * @see #allEquals(Object,Supplier)
   */
  public static boolean or(Supplier<Boolean> b0,
      Supplier<Boolean> b1,
      Supplier<Boolean> b2,
      Supplier<Boolean> b3){
    requireNonNull(b0);
    requireNonNull(b1);
    requireNonNull(b2);
    requireNonNull(b3);
    return mapOr(b0.get(), identity(), ()->false) ||
     mapOr(b1.get(), identity(), ()->false) ||
     mapOr(b2.get(), identity(), ()->false) ||
     mapOr(b3.get(), identity(), ()->false);
  }

  /**
   * Do the values of all suppliers equal o. Short circuits when not. There are overrides with different arity 
   * @see #or(Supplier)
   * @see #and(Supplier)
   * @see #allEquals(Object,Supplier)
   */
  public static boolean anyEquals(Object o,
       Supplier<Object> s0,
       Supplier<Object> s1,
       Supplier<Object> s2,
       Supplier<Object> s3){
    requireNonNull(s0);
    requireNonNull(s1);
    requireNonNull(s2);
    requireNonNull(s3);
    return trampoline(anyEqualsRec(o,s0,s1,s2,s3));
  }

  private static Supplier<RecursiveVal<Boolean>> anyEqualsRec(Object o,
       Supplier<Object> s0,
       Supplier<Object> s1,
       Supplier<Object> s2,
       Supplier<Object> s3){
    return ()->ifelse(Objects.equals(o, s0.get()),
               ()->done(true),
               ()->recur(anyEqualsRec(o,s1,s2,s3)));
  }

  /**
   * Do the values of all suppliers equal o. Short circuits when not. There are overrides with different arity 
   * @see #or(Supplier)
   * @see #and(Supplier)
   * @see #anyEquals(Object,Supplier)
   */
  public static boolean allEquals(Object o,
       Supplier<Object> s0,
       Supplier<Object> s1,
       Supplier<Object> s2,
       Supplier<Object> s3){
    requireNonNull(s0);
    requireNonNull(s1);
    requireNonNull(s2);
    requireNonNull(s3);
    return trampoline(allEqualsRec(o,s0,s1,s2,s3));
  }

  private static Supplier<RecursiveVal<Boolean>> allEqualsRec(Object o,
       Supplier<Object> s0,
       Supplier<Object> s1,
       Supplier<Object> s2,
       Supplier<Object> s3){
    return ()->ifelse(Objects.equals(o, s0.get()),
               ()->recur(allEqualsRec(o,s1,s2,s3)),
               ()->done(false));
  }

  /**
   * Are all suppliers returning true. There are overloads with different numbers of suppliers. The test will short circuit when there is a false value.
   * @see #or(Supplier)
   * @see #anyEquals(Object,Supplier)
   * @see #allEquals(Object,Supplier)
   */
  public static boolean and(Supplier<Boolean> b0,
      Supplier<Boolean> b1,
      Supplier<Boolean> b2,
      Supplier<Boolean> b3,
      Supplier<Boolean> b4){
    requireNonNull(b0);
    requireNonNull(b1);
    requireNonNull(b2);
    requireNonNull(b3);
    requireNonNull(b4);
    return mapOr(b0.get(), identity(), ()->false) &&
     mapOr(b1.get(), identity(), ()->false) &&
     mapOr(b2.get(), identity(), ()->false) &&
     mapOr(b3.get(), identity(), ()->false) &&
     mapOr(b4.get(), identity(), ()->false);
  }

  /**
   * Are any suppliers returning true. There are overloads with different numbers of suppliers. The test will short circuit when there is a true value.
   * @see #and(Supplier)
   * @see #anyEquals(Object,Supplier)
   * @see #allEquals(Object,Supplier)
   */
  public static boolean or(Supplier<Boolean> b0,
      Supplier<Boolean> b1,
      Supplier<Boolean> b2,
      Supplier<Boolean> b3,
      Supplier<Boolean> b4){
    requireNonNull(b0);
    requireNonNull(b1);
    requireNonNull(b2);
    requireNonNull(b3);
    requireNonNull(b4);
    return mapOr(b0.get(), identity(), ()->false) ||
     mapOr(b1.get(), identity(), ()->false) ||
     mapOr(b2.get(), identity(), ()->false) ||
     mapOr(b3.get(), identity(), ()->false) ||
     mapOr(b4.get(), identity(), ()->false);
  }

  /**
   * Do the values of all suppliers equal o. Short circuits when not. There are overrides with different arity 
   * @see #or(Supplier)
   * @see #and(Supplier)
   * @see #allEquals(Object,Supplier)
   */
  public static boolean anyEquals(Object o,
       Supplier<Object> s0,
       Supplier<Object> s1,
       Supplier<Object> s2,
       Supplier<Object> s3,
       Supplier<Object> s4){
    requireNonNull(s0);
    requireNonNull(s1);
    requireNonNull(s2);
    requireNonNull(s3);
    requireNonNull(s4);
    return trampoline(anyEqualsRec(o,s0,s1,s2,s3,s4));
  }

  private static Supplier<RecursiveVal<Boolean>> anyEqualsRec(Object o,
       Supplier<Object> s0,
       Supplier<Object> s1,
       Supplier<Object> s2,
       Supplier<Object> s3,
       Supplier<Object> s4){
    return ()->ifelse(Objects.equals(o, s0.get()),
               ()->done(true),
               ()->recur(anyEqualsRec(o,s1,s2,s3,s4)));
  }

  /**
   * Do the values of all suppliers equal o. Short circuits when not. There are overrides with different arity 
   * @see #or(Supplier)
   * @see #and(Supplier)
   * @see #anyEquals(Object,Supplier)
   */
  public static boolean allEquals(Object o,
       Supplier<Object> s0,
       Supplier<Object> s1,
       Supplier<Object> s2,
       Supplier<Object> s3,
       Supplier<Object> s4){
    requireNonNull(s0);
    requireNonNull(s1);
    requireNonNull(s2);
    requireNonNull(s3);
    requireNonNull(s4);
    return trampoline(allEqualsRec(o,s0,s1,s2,s3,s4));
  }

  private static Supplier<RecursiveVal<Boolean>> allEqualsRec(Object o,
       Supplier<Object> s0,
       Supplier<Object> s1,
       Supplier<Object> s2,
       Supplier<Object> s3,
       Supplier<Object> s4){
    return ()->ifelse(Objects.equals(o, s0.get()),
               ()->recur(allEqualsRec(o,s1,s2,s3,s4)),
               ()->done(false));
  }

  /**
   * Are all suppliers returning true. There are overloads with different numbers of suppliers. The test will short circuit when there is a false value.
   * @see #or(Supplier)
   * @see #anyEquals(Object,Supplier)
   * @see #allEquals(Object,Supplier)
   */
  public static boolean and(Supplier<Boolean> b0,
      Supplier<Boolean> b1,
      Supplier<Boolean> b2,
      Supplier<Boolean> b3,
      Supplier<Boolean> b4,
      Supplier<Boolean> b5){
    requireNonNull(b0);
    requireNonNull(b1);
    requireNonNull(b2);
    requireNonNull(b3);
    requireNonNull(b4);
    requireNonNull(b5);
    return mapOr(b0.get(), identity(), ()->false) &&
     mapOr(b1.get(), identity(), ()->false) &&
     mapOr(b2.get(), identity(), ()->false) &&
     mapOr(b3.get(), identity(), ()->false) &&
     mapOr(b4.get(), identity(), ()->false) &&
     mapOr(b5.get(), identity(), ()->false);
  }

  /**
   * Are any suppliers returning true. There are overloads with different numbers of suppliers. The test will short circuit when there is a true value.
   * @see #and(Supplier)
   * @see #anyEquals(Object,Supplier)
   * @see #allEquals(Object,Supplier)
   */
  public static boolean or(Supplier<Boolean> b0,
      Supplier<Boolean> b1,
      Supplier<Boolean> b2,
      Supplier<Boolean> b3,
      Supplier<Boolean> b4,
      Supplier<Boolean> b5){
    requireNonNull(b0);
    requireNonNull(b1);
    requireNonNull(b2);
    requireNonNull(b3);
    requireNonNull(b4);
    requireNonNull(b5);
    return mapOr(b0.get(), identity(), ()->false) ||
     mapOr(b1.get(), identity(), ()->false) ||
     mapOr(b2.get(), identity(), ()->false) ||
     mapOr(b3.get(), identity(), ()->false) ||
     mapOr(b4.get(), identity(), ()->false) ||
     mapOr(b5.get(), identity(), ()->false);
  }

  /**
   * Do the values of all suppliers equal o. Short circuits when not. There are overrides with different arity 
   * @see #or(Supplier)
   * @see #and(Supplier)
   * @see #allEquals(Object,Supplier)
   */
  public static boolean anyEquals(Object o,
       Supplier<Object> s0,
       Supplier<Object> s1,
       Supplier<Object> s2,
       Supplier<Object> s3,
       Supplier<Object> s4,
       Supplier<Object> s5){
    requireNonNull(s0);
    requireNonNull(s1);
    requireNonNull(s2);
    requireNonNull(s3);
    requireNonNull(s4);
    requireNonNull(s5);
    return trampoline(anyEqualsRec(o,s0,s1,s2,s3,s4,s5));
  }

  private static Supplier<RecursiveVal<Boolean>> anyEqualsRec(Object o,
       Supplier<Object> s0,
       Supplier<Object> s1,
       Supplier<Object> s2,
       Supplier<Object> s3,
       Supplier<Object> s4,
       Supplier<Object> s5){
    return ()->ifelse(Objects.equals(o, s0.get()),
               ()->done(true),
               ()->recur(anyEqualsRec(o,s1,s2,s3,s4,s5)));
  }

  /**
   * Do the values of all suppliers equal o. Short circuits when not. There are overrides with different arity 
   * @see #or(Supplier)
   * @see #and(Supplier)
   * @see #anyEquals(Object,Supplier)
   */
  public static boolean allEquals(Object o,
       Supplier<Object> s0,
       Supplier<Object> s1,
       Supplier<Object> s2,
       Supplier<Object> s3,
       Supplier<Object> s4,
       Supplier<Object> s5){
    requireNonNull(s0);
    requireNonNull(s1);
    requireNonNull(s2);
    requireNonNull(s3);
    requireNonNull(s4);
    requireNonNull(s5);
    return trampoline(allEqualsRec(o,s0,s1,s2,s3,s4,s5));
  }

  private static Supplier<RecursiveVal<Boolean>> allEqualsRec(Object o,
       Supplier<Object> s0,
       Supplier<Object> s1,
       Supplier<Object> s2,
       Supplier<Object> s3,
       Supplier<Object> s4,
       Supplier<Object> s5){
    return ()->ifelse(Objects.equals(o, s0.get()),
               ()->recur(allEqualsRec(o,s1,s2,s3,s4,s5)),
               ()->done(false));
  }

  /**
   * Are all suppliers returning true. There are overloads with different numbers of suppliers. The test will short circuit when there is a false value.
   * @see #or(Supplier)
   * @see #anyEquals(Object,Supplier)
   * @see #allEquals(Object,Supplier)
   */
  public static boolean and(Supplier<Boolean> b0,
      Supplier<Boolean> b1,
      Supplier<Boolean> b2,
      Supplier<Boolean> b3,
      Supplier<Boolean> b4,
      Supplier<Boolean> b5,
      Supplier<Boolean> b6){
    requireNonNull(b0);
    requireNonNull(b1);
    requireNonNull(b2);
    requireNonNull(b3);
    requireNonNull(b4);
    requireNonNull(b5);
    requireNonNull(b6);
    return mapOr(b0.get(), identity(), ()->false) &&
     mapOr(b1.get(), identity(), ()->false) &&
     mapOr(b2.get(), identity(), ()->false) &&
     mapOr(b3.get(), identity(), ()->false) &&
     mapOr(b4.get(), identity(), ()->false) &&
     mapOr(b5.get(), identity(), ()->false) &&
     mapOr(b6.get(), identity(), ()->false);
  }

  /**
   * Are any suppliers returning true. There are overloads with different numbers of suppliers. The test will short circuit when there is a true value.
   * @see #and(Supplier)
   * @see #anyEquals(Object,Supplier)
   * @see #allEquals(Object,Supplier)
   */
  public static boolean or(Supplier<Boolean> b0,
      Supplier<Boolean> b1,
      Supplier<Boolean> b2,
      Supplier<Boolean> b3,
      Supplier<Boolean> b4,
      Supplier<Boolean> b5,
      Supplier<Boolean> b6){
    requireNonNull(b0);
    requireNonNull(b1);
    requireNonNull(b2);
    requireNonNull(b3);
    requireNonNull(b4);
    requireNonNull(b5);
    requireNonNull(b6);
    return mapOr(b0.get(), identity(), ()->false) ||
     mapOr(b1.get(), identity(), ()->false) ||
     mapOr(b2.get(), identity(), ()->false) ||
     mapOr(b3.get(), identity(), ()->false) ||
     mapOr(b4.get(), identity(), ()->false) ||
     mapOr(b5.get(), identity(), ()->false) ||
     mapOr(b6.get(), identity(), ()->false);
  }

  /**
   * Do the values of all suppliers equal o. Short circuits when not. There are overrides with different arity 
   * @see #or(Supplier)
   * @see #and(Supplier)
   * @see #allEquals(Object,Supplier)
   */
  public static boolean anyEquals(Object o,
       Supplier<Object> s0,
       Supplier<Object> s1,
       Supplier<Object> s2,
       Supplier<Object> s3,
       Supplier<Object> s4,
       Supplier<Object> s5,
       Supplier<Object> s6){
    requireNonNull(s0);
    requireNonNull(s1);
    requireNonNull(s2);
    requireNonNull(s3);
    requireNonNull(s4);
    requireNonNull(s5);
    requireNonNull(s6);
    return trampoline(anyEqualsRec(o,s0,s1,s2,s3,s4,s5,s6));
  }

  private static Supplier<RecursiveVal<Boolean>> anyEqualsRec(Object o,
       Supplier<Object> s0,
       Supplier<Object> s1,
       Supplier<Object> s2,
       Supplier<Object> s3,
       Supplier<Object> s4,
       Supplier<Object> s5,
       Supplier<Object> s6){
    return ()->ifelse(Objects.equals(o, s0.get()),
               ()->done(true),
               ()->recur(anyEqualsRec(o,s1,s2,s3,s4,s5,s6)));
  }

  /**
   * Do the values of all suppliers equal o. Short circuits when not. There are overrides with different arity 
   * @see #or(Supplier)
   * @see #and(Supplier)
   * @see #anyEquals(Object,Supplier)
   */
  public static boolean allEquals(Object o,
       Supplier<Object> s0,
       Supplier<Object> s1,
       Supplier<Object> s2,
       Supplier<Object> s3,
       Supplier<Object> s4,
       Supplier<Object> s5,
       Supplier<Object> s6){
    requireNonNull(s0);
    requireNonNull(s1);
    requireNonNull(s2);
    requireNonNull(s3);
    requireNonNull(s4);
    requireNonNull(s5);
    requireNonNull(s6);
    return trampoline(allEqualsRec(o,s0,s1,s2,s3,s4,s5,s6));
  }

  private static Supplier<RecursiveVal<Boolean>> allEqualsRec(Object o,
       Supplier<Object> s0,
       Supplier<Object> s1,
       Supplier<Object> s2,
       Supplier<Object> s3,
       Supplier<Object> s4,
       Supplier<Object> s5,
       Supplier<Object> s6){
    return ()->ifelse(Objects.equals(o, s0.get()),
               ()->recur(allEqualsRec(o,s1,s2,s3,s4,s5,s6)),
               ()->done(false));
  }

  /**
   * Are all suppliers returning true. There are overloads with different numbers of suppliers. The test will short circuit when there is a false value.
   * @see #or(Supplier)
   * @see #anyEquals(Object,Supplier)
   * @see #allEquals(Object,Supplier)
   */
  public static boolean and(Supplier<Boolean> b0,
      Supplier<Boolean> b1,
      Supplier<Boolean> b2,
      Supplier<Boolean> b3,
      Supplier<Boolean> b4,
      Supplier<Boolean> b5,
      Supplier<Boolean> b6,
      Supplier<Boolean> b7){
    requireNonNull(b0);
    requireNonNull(b1);
    requireNonNull(b2);
    requireNonNull(b3);
    requireNonNull(b4);
    requireNonNull(b5);
    requireNonNull(b6);
    requireNonNull(b7);
    return mapOr(b0.get(), identity(), ()->false) &&
     mapOr(b1.get(), identity(), ()->false) &&
     mapOr(b2.get(), identity(), ()->false) &&
     mapOr(b3.get(), identity(), ()->false) &&
     mapOr(b4.get(), identity(), ()->false) &&
     mapOr(b5.get(), identity(), ()->false) &&
     mapOr(b6.get(), identity(), ()->false) &&
     mapOr(b7.get(), identity(), ()->false);
  }

  /**
   * Are any suppliers returning true. There are overloads with different numbers of suppliers. The test will short circuit when there is a true value.
   * @see #and(Supplier)
   * @see #anyEquals(Object,Supplier)
   * @see #allEquals(Object,Supplier)
   */
  public static boolean or(Supplier<Boolean> b0,
      Supplier<Boolean> b1,
      Supplier<Boolean> b2,
      Supplier<Boolean> b3,
      Supplier<Boolean> b4,
      Supplier<Boolean> b5,
      Supplier<Boolean> b6,
      Supplier<Boolean> b7){
    requireNonNull(b0);
    requireNonNull(b1);
    requireNonNull(b2);
    requireNonNull(b3);
    requireNonNull(b4);
    requireNonNull(b5);
    requireNonNull(b6);
    requireNonNull(b7);
    return mapOr(b0.get(), identity(), ()->false) ||
     mapOr(b1.get(), identity(), ()->false) ||
     mapOr(b2.get(), identity(), ()->false) ||
     mapOr(b3.get(), identity(), ()->false) ||
     mapOr(b4.get(), identity(), ()->false) ||
     mapOr(b5.get(), identity(), ()->false) ||
     mapOr(b6.get(), identity(), ()->false) ||
     mapOr(b7.get(), identity(), ()->false);
  }

  /**
   * Do the values of all suppliers equal o. Short circuits when not. There are overrides with different arity 
   * @see #or(Supplier)
   * @see #and(Supplier)
   * @see #allEquals(Object,Supplier)
   */
  public static boolean anyEquals(Object o,
       Supplier<Object> s0,
       Supplier<Object> s1,
       Supplier<Object> s2,
       Supplier<Object> s3,
       Supplier<Object> s4,
       Supplier<Object> s5,
       Supplier<Object> s6,
       Supplier<Object> s7){
    requireNonNull(s0);
    requireNonNull(s1);
    requireNonNull(s2);
    requireNonNull(s3);
    requireNonNull(s4);
    requireNonNull(s5);
    requireNonNull(s6);
    requireNonNull(s7);
    return trampoline(anyEqualsRec(o,s0,s1,s2,s3,s4,s5,s6,s7));
  }

  private static Supplier<RecursiveVal<Boolean>> anyEqualsRec(Object o,
       Supplier<Object> s0,
       Supplier<Object> s1,
       Supplier<Object> s2,
       Supplier<Object> s3,
       Supplier<Object> s4,
       Supplier<Object> s5,
       Supplier<Object> s6,
       Supplier<Object> s7){
    return ()->ifelse(Objects.equals(o, s0.get()),
               ()->done(true),
               ()->recur(anyEqualsRec(o,s1,s2,s3,s4,s5,s6,s7)));
  }

  /**
   * Do the values of all suppliers equal o. Short circuits when not. There are overrides with different arity 
   * @see #or(Supplier)
   * @see #and(Supplier)
   * @see #anyEquals(Object,Supplier)
   */
  public static boolean allEquals(Object o,
       Supplier<Object> s0,
       Supplier<Object> s1,
       Supplier<Object> s2,
       Supplier<Object> s3,
       Supplier<Object> s4,
       Supplier<Object> s5,
       Supplier<Object> s6,
       Supplier<Object> s7){
    requireNonNull(s0);
    requireNonNull(s1);
    requireNonNull(s2);
    requireNonNull(s3);
    requireNonNull(s4);
    requireNonNull(s5);
    requireNonNull(s6);
    requireNonNull(s7);
    return trampoline(allEqualsRec(o,s0,s1,s2,s3,s4,s5,s6,s7));
  }

  private static Supplier<RecursiveVal<Boolean>> allEqualsRec(Object o,
       Supplier<Object> s0,
       Supplier<Object> s1,
       Supplier<Object> s2,
       Supplier<Object> s3,
       Supplier<Object> s4,
       Supplier<Object> s5,
       Supplier<Object> s6,
       Supplier<Object> s7){
    return ()->ifelse(Objects.equals(o, s0.get()),
               ()->recur(allEqualsRec(o,s1,s2,s3,s4,s5,s6,s7)),
               ()->done(false));
  }

  /**
   * Are all suppliers returning true. There are overloads with different numbers of suppliers. The test will short circuit when there is a false value.
   * @see #or(Supplier)
   * @see #anyEquals(Object,Supplier)
   * @see #allEquals(Object,Supplier)
   */
  public static boolean and(Supplier<Boolean> b0,
      Supplier<Boolean> b1,
      Supplier<Boolean> b2,
      Supplier<Boolean> b3,
      Supplier<Boolean> b4,
      Supplier<Boolean> b5,
      Supplier<Boolean> b6,
      Supplier<Boolean> b7,
      Supplier<Boolean> b8){
    requireNonNull(b0);
    requireNonNull(b1);
    requireNonNull(b2);
    requireNonNull(b3);
    requireNonNull(b4);
    requireNonNull(b5);
    requireNonNull(b6);
    requireNonNull(b7);
    requireNonNull(b8);
    return mapOr(b0.get(), identity(), ()->false) &&
     mapOr(b1.get(), identity(), ()->false) &&
     mapOr(b2.get(), identity(), ()->false) &&
     mapOr(b3.get(), identity(), ()->false) &&
     mapOr(b4.get(), identity(), ()->false) &&
     mapOr(b5.get(), identity(), ()->false) &&
     mapOr(b6.get(), identity(), ()->false) &&
     mapOr(b7.get(), identity(), ()->false) &&
     mapOr(b8.get(), identity(), ()->false);
  }

  /**
   * Are any suppliers returning true. There are overloads with different numbers of suppliers. The test will short circuit when there is a true value.
   * @see #and(Supplier)
   * @see #anyEquals(Object,Supplier)
   * @see #allEquals(Object,Supplier)
   */
  public static boolean or(Supplier<Boolean> b0,
      Supplier<Boolean> b1,
      Supplier<Boolean> b2,
      Supplier<Boolean> b3,
      Supplier<Boolean> b4,
      Supplier<Boolean> b5,
      Supplier<Boolean> b6,
      Supplier<Boolean> b7,
      Supplier<Boolean> b8){
    requireNonNull(b0);
    requireNonNull(b1);
    requireNonNull(b2);
    requireNonNull(b3);
    requireNonNull(b4);
    requireNonNull(b5);
    requireNonNull(b6);
    requireNonNull(b7);
    requireNonNull(b8);
    return mapOr(b0.get(), identity(), ()->false) ||
     mapOr(b1.get(), identity(), ()->false) ||
     mapOr(b2.get(), identity(), ()->false) ||
     mapOr(b3.get(), identity(), ()->false) ||
     mapOr(b4.get(), identity(), ()->false) ||
     mapOr(b5.get(), identity(), ()->false) ||
     mapOr(b6.get(), identity(), ()->false) ||
     mapOr(b7.get(), identity(), ()->false) ||
     mapOr(b8.get(), identity(), ()->false);
  }

  /**
   * Do the values of all suppliers equal o. Short circuits when not. There are overrides with different arity 
   * @see #or(Supplier)
   * @see #and(Supplier)
   * @see #allEquals(Object,Supplier)
   */
  public static boolean anyEquals(Object o,
       Supplier<Object> s0,
       Supplier<Object> s1,
       Supplier<Object> s2,
       Supplier<Object> s3,
       Supplier<Object> s4,
       Supplier<Object> s5,
       Supplier<Object> s6,
       Supplier<Object> s7,
       Supplier<Object> s8){
    requireNonNull(s0);
    requireNonNull(s1);
    requireNonNull(s2);
    requireNonNull(s3);
    requireNonNull(s4);
    requireNonNull(s5);
    requireNonNull(s6);
    requireNonNull(s7);
    requireNonNull(s8);
    return trampoline(anyEqualsRec(o,s0,s1,s2,s3,s4,s5,s6,s7,s8));
  }

  private static Supplier<RecursiveVal<Boolean>> anyEqualsRec(Object o,
       Supplier<Object> s0,
       Supplier<Object> s1,
       Supplier<Object> s2,
       Supplier<Object> s3,
       Supplier<Object> s4,
       Supplier<Object> s5,
       Supplier<Object> s6,
       Supplier<Object> s7,
       Supplier<Object> s8){
    return ()->ifelse(Objects.equals(o, s0.get()),
               ()->done(true),
               ()->recur(anyEqualsRec(o,s1,s2,s3,s4,s5,s6,s7,s8)));
  }

  /**
   * Do the values of all suppliers equal o. Short circuits when not. There are overrides with different arity 
   * @see #or(Supplier)
   * @see #and(Supplier)
   * @see #anyEquals(Object,Supplier)
   */
  public static boolean allEquals(Object o,
       Supplier<Object> s0,
       Supplier<Object> s1,
       Supplier<Object> s2,
       Supplier<Object> s3,
       Supplier<Object> s4,
       Supplier<Object> s5,
       Supplier<Object> s6,
       Supplier<Object> s7,
       Supplier<Object> s8){
    requireNonNull(s0);
    requireNonNull(s1);
    requireNonNull(s2);
    requireNonNull(s3);
    requireNonNull(s4);
    requireNonNull(s5);
    requireNonNull(s6);
    requireNonNull(s7);
    requireNonNull(s8);
    return trampoline(allEqualsRec(o,s0,s1,s2,s3,s4,s5,s6,s7,s8));
  }

  private static Supplier<RecursiveVal<Boolean>> allEqualsRec(Object o,
       Supplier<Object> s0,
       Supplier<Object> s1,
       Supplier<Object> s2,
       Supplier<Object> s3,
       Supplier<Object> s4,
       Supplier<Object> s5,
       Supplier<Object> s6,
       Supplier<Object> s7,
       Supplier<Object> s8){
    return ()->ifelse(Objects.equals(o, s0.get()),
               ()->recur(allEqualsRec(o,s1,s2,s3,s4,s5,s6,s7,s8)),
               ()->done(false));
  }

}
