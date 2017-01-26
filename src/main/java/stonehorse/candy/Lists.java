package stonehorse.candy;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Creating lists from variable number of args without the varargs hazards
 */
public class Lists{
    private Lists(){}
    public static <T> List<T> asList(){
        return Collections.emptyList();
    }
    public static <T> List<T> arrayList(){
        return new ArrayList<>();
    }


  public static <T> List<T> asList(
    T t0){
    List<T> l = arrayList(
      t0
    );
    return Collections.unmodifiableList(l);
  }

  public static <T> List<T> arrayList(
    T t0){
    List<T> l = new ArrayList<>(1);
    l.add(t0);
    return l;
  }

  public static <T> List<T> asList(
    T t0,
    T t1){
    List<T> l = arrayList(
      t0,
      t1
    );
    return Collections.unmodifiableList(l);
  }

  public static <T> List<T> arrayList(
    T t0,
    T t1){
    List<T> l = new ArrayList<>(2);
    l.add(t0);
    l.add(t1);
    return l;
  }

  public static <T> List<T> asList(
    T t0,
    T t1,
    T t2){
    List<T> l = arrayList(
      t0,
      t1,
      t2
    );
    return Collections.unmodifiableList(l);
  }

  public static <T> List<T> arrayList(
    T t0,
    T t1,
    T t2){
    List<T> l = new ArrayList<>(3);
    l.add(t0);
    l.add(t1);
    l.add(t2);
    return l;
  }

  public static <T> List<T> asList(
    T t0,
    T t1,
    T t2,
    T t3){
    List<T> l = arrayList(
      t0,
      t1,
      t2,
      t3
    );
    return Collections.unmodifiableList(l);
  }

  public static <T> List<T> arrayList(
    T t0,
    T t1,
    T t2,
    T t3){
    List<T> l = new ArrayList<>(4);
    l.add(t0);
    l.add(t1);
    l.add(t2);
    l.add(t3);
    return l;
  }

  public static <T> List<T> asList(
    T t0,
    T t1,
    T t2,
    T t3,
    T t4){
    List<T> l = arrayList(
      t0,
      t1,
      t2,
      t3,
      t4
    );
    return Collections.unmodifiableList(l);
  }

  public static <T> List<T> arrayList(
    T t0,
    T t1,
    T t2,
    T t3,
    T t4){
    List<T> l = new ArrayList<>(5);
    l.add(t0);
    l.add(t1);
    l.add(t2);
    l.add(t3);
    l.add(t4);
    return l;
  }

  public static <T> List<T> asList(
    T t0,
    T t1,
    T t2,
    T t3,
    T t4,
    T t5){
    List<T> l = arrayList(
      t0,
      t1,
      t2,
      t3,
      t4,
      t5
    );
    return Collections.unmodifiableList(l);
  }

  public static <T> List<T> arrayList(
    T t0,
    T t1,
    T t2,
    T t3,
    T t4,
    T t5){
    List<T> l = new ArrayList<>(6);
    l.add(t0);
    l.add(t1);
    l.add(t2);
    l.add(t3);
    l.add(t4);
    l.add(t5);
    return l;
  }

  public static <T> List<T> asList(
    T t0,
    T t1,
    T t2,
    T t3,
    T t4,
    T t5,
    T t6){
    List<T> l = arrayList(
      t0,
      t1,
      t2,
      t3,
      t4,
      t5,
      t6
    );
    return Collections.unmodifiableList(l);
  }

  public static <T> List<T> arrayList(
    T t0,
    T t1,
    T t2,
    T t3,
    T t4,
    T t5,
    T t6){
    List<T> l = new ArrayList<>(7);
    l.add(t0);
    l.add(t1);
    l.add(t2);
    l.add(t3);
    l.add(t4);
    l.add(t5);
    l.add(t6);
    return l;
  }

  public static <T> List<T> asList(
    T t0,
    T t1,
    T t2,
    T t3,
    T t4,
    T t5,
    T t6,
    T t7){
    List<T> l = arrayList(
      t0,
      t1,
      t2,
      t3,
      t4,
      t5,
      t6,
      t7
    );
    return Collections.unmodifiableList(l);
  }

  public static <T> List<T> arrayList(
    T t0,
    T t1,
    T t2,
    T t3,
    T t4,
    T t5,
    T t6,
    T t7){
    List<T> l = new ArrayList<>(8);
    l.add(t0);
    l.add(t1);
    l.add(t2);
    l.add(t3);
    l.add(t4);
    l.add(t5);
    l.add(t6);
    l.add(t7);
    return l;
  }

  public static <T> List<T> asList(
    T t0,
    T t1,
    T t2,
    T t3,
    T t4,
    T t5,
    T t6,
    T t7,
    T t8){
    List<T> l = arrayList(
      t0,
      t1,
      t2,
      t3,
      t4,
      t5,
      t6,
      t7,
      t8
    );
    return Collections.unmodifiableList(l);
  }

  public static <T> List<T> arrayList(
    T t0,
    T t1,
    T t2,
    T t3,
    T t4,
    T t5,
    T t6,
    T t7,
    T t8){
    List<T> l = new ArrayList<>(9);
    l.add(t0);
    l.add(t1);
    l.add(t2);
    l.add(t3);
    l.add(t4);
    l.add(t5);
    l.add(t6);
    l.add(t7);
    l.add(t8);
    return l;
  }

  public static <T> List<T> asList(
    T t0,
    T t1,
    T t2,
    T t3,
    T t4,
    T t5,
    T t6,
    T t7,
    T t8,
    T t9){
    List<T> l = arrayList(
      t0,
      t1,
      t2,
      t3,
      t4,
      t5,
      t6,
      t7,
      t8,
      t9
    );
    return Collections.unmodifiableList(l);
  }

  public static <T> List<T> arrayList(
    T t0,
    T t1,
    T t2,
    T t3,
    T t4,
    T t5,
    T t6,
    T t7,
    T t8,
    T t9){
    List<T> l = new ArrayList<>(10);
    l.add(t0);
    l.add(t1);
    l.add(t2);
    l.add(t3);
    l.add(t4);
    l.add(t5);
    l.add(t6);
    l.add(t7);
    l.add(t8);
    l.add(t9);
    return l;
  }

  public static <T> List<T> asList(
    T t0,
    T t1,
    T t2,
    T t3,
    T t4,
    T t5,
    T t6,
    T t7,
    T t8,
    T t9,
    T t10){
    List<T> l = arrayList(
      t0,
      t1,
      t2,
      t3,
      t4,
      t5,
      t6,
      t7,
      t8,
      t9,
      t10
    );
    return Collections.unmodifiableList(l);
  }

  public static <T> List<T> arrayList(
    T t0,
    T t1,
    T t2,
    T t3,
    T t4,
    T t5,
    T t6,
    T t7,
    T t8,
    T t9,
    T t10){
    List<T> l = new ArrayList<>(11);
    l.add(t0);
    l.add(t1);
    l.add(t2);
    l.add(t3);
    l.add(t4);
    l.add(t5);
    l.add(t6);
    l.add(t7);
    l.add(t8);
    l.add(t9);
    l.add(t10);
    return l;
  }

  public static <T> List<T> asList(
    T t0,
    T t1,
    T t2,
    T t3,
    T t4,
    T t5,
    T t6,
    T t7,
    T t8,
    T t9,
    T t10,
    T t11){
    List<T> l = arrayList(
      t0,
      t1,
      t2,
      t3,
      t4,
      t5,
      t6,
      t7,
      t8,
      t9,
      t10,
      t11
    );
    return Collections.unmodifiableList(l);
  }

  public static <T> List<T> arrayList(
    T t0,
    T t1,
    T t2,
    T t3,
    T t4,
    T t5,
    T t6,
    T t7,
    T t8,
    T t9,
    T t10,
    T t11){
    List<T> l = new ArrayList<>(12);
    l.add(t0);
    l.add(t1);
    l.add(t2);
    l.add(t3);
    l.add(t4);
    l.add(t5);
    l.add(t6);
    l.add(t7);
    l.add(t8);
    l.add(t9);
    l.add(t10);
    l.add(t11);
    return l;
  }

  public static <T> List<T> asList(
    T t0,
    T t1,
    T t2,
    T t3,
    T t4,
    T t5,
    T t6,
    T t7,
    T t8,
    T t9,
    T t10,
    T t11,
    T t12){
    List<T> l = arrayList(
      t0,
      t1,
      t2,
      t3,
      t4,
      t5,
      t6,
      t7,
      t8,
      t9,
      t10,
      t11,
      t12
    );
    return Collections.unmodifiableList(l);
  }

  public static <T> List<T> arrayList(
    T t0,
    T t1,
    T t2,
    T t3,
    T t4,
    T t5,
    T t6,
    T t7,
    T t8,
    T t9,
    T t10,
    T t11,
    T t12){
    List<T> l = new ArrayList<>(13);
    l.add(t0);
    l.add(t1);
    l.add(t2);
    l.add(t3);
    l.add(t4);
    l.add(t5);
    l.add(t6);
    l.add(t7);
    l.add(t8);
    l.add(t9);
    l.add(t10);
    l.add(t11);
    l.add(t12);
    return l;
  }

  public static <T> List<T> asList(
    T t0,
    T t1,
    T t2,
    T t3,
    T t4,
    T t5,
    T t6,
    T t7,
    T t8,
    T t9,
    T t10,
    T t11,
    T t12,
    T t13){
    List<T> l = arrayList(
      t0,
      t1,
      t2,
      t3,
      t4,
      t5,
      t6,
      t7,
      t8,
      t9,
      t10,
      t11,
      t12,
      t13
    );
    return Collections.unmodifiableList(l);
  }

  public static <T> List<T> arrayList(
    T t0,
    T t1,
    T t2,
    T t3,
    T t4,
    T t5,
    T t6,
    T t7,
    T t8,
    T t9,
    T t10,
    T t11,
    T t12,
    T t13){
    List<T> l = new ArrayList<>(14);
    l.add(t0);
    l.add(t1);
    l.add(t2);
    l.add(t3);
    l.add(t4);
    l.add(t5);
    l.add(t6);
    l.add(t7);
    l.add(t8);
    l.add(t9);
    l.add(t10);
    l.add(t11);
    l.add(t12);
    l.add(t13);
    return l;
  }

  public static <T> List<T> asList(
    T t0,
    T t1,
    T t2,
    T t3,
    T t4,
    T t5,
    T t6,
    T t7,
    T t8,
    T t9,
    T t10,
    T t11,
    T t12,
    T t13,
    T t14){
    List<T> l = arrayList(
      t0,
      t1,
      t2,
      t3,
      t4,
      t5,
      t6,
      t7,
      t8,
      t9,
      t10,
      t11,
      t12,
      t13,
      t14
    );
    return Collections.unmodifiableList(l);
  }

  public static <T> List<T> arrayList(
    T t0,
    T t1,
    T t2,
    T t3,
    T t4,
    T t5,
    T t6,
    T t7,
    T t8,
    T t9,
    T t10,
    T t11,
    T t12,
    T t13,
    T t14){
    List<T> l = new ArrayList<>(15);
    l.add(t0);
    l.add(t1);
    l.add(t2);
    l.add(t3);
    l.add(t4);
    l.add(t5);
    l.add(t6);
    l.add(t7);
    l.add(t8);
    l.add(t9);
    l.add(t10);
    l.add(t11);
    l.add(t12);
    l.add(t13);
    l.add(t14);
    return l;
  }

  public static <T> List<T> asList(
    T t0,
    T t1,
    T t2,
    T t3,
    T t4,
    T t5,
    T t6,
    T t7,
    T t8,
    T t9,
    T t10,
    T t11,
    T t12,
    T t13,
    T t14,
    T t15){
    List<T> l = arrayList(
      t0,
      t1,
      t2,
      t3,
      t4,
      t5,
      t6,
      t7,
      t8,
      t9,
      t10,
      t11,
      t12,
      t13,
      t14,
      t15
    );
    return Collections.unmodifiableList(l);
  }

  public static <T> List<T> arrayList(
    T t0,
    T t1,
    T t2,
    T t3,
    T t4,
    T t5,
    T t6,
    T t7,
    T t8,
    T t9,
    T t10,
    T t11,
    T t12,
    T t13,
    T t14,
    T t15){
    List<T> l = new ArrayList<>(16);
    l.add(t0);
    l.add(t1);
    l.add(t2);
    l.add(t3);
    l.add(t4);
    l.add(t5);
    l.add(t6);
    l.add(t7);
    l.add(t8);
    l.add(t9);
    l.add(t10);
    l.add(t11);
    l.add(t12);
    l.add(t13);
    l.add(t14);
    l.add(t15);
    return l;
  }

  public static <T> List<T> asList(
    T t0,
    T t1,
    T t2,
    T t3,
    T t4,
    T t5,
    T t6,
    T t7,
    T t8,
    T t9,
    T t10,
    T t11,
    T t12,
    T t13,
    T t14,
    T t15,
    T t16){
    List<T> l = arrayList(
      t0,
      t1,
      t2,
      t3,
      t4,
      t5,
      t6,
      t7,
      t8,
      t9,
      t10,
      t11,
      t12,
      t13,
      t14,
      t15,
      t16
    );
    return Collections.unmodifiableList(l);
  }

  public static <T> List<T> arrayList(
    T t0,
    T t1,
    T t2,
    T t3,
    T t4,
    T t5,
    T t6,
    T t7,
    T t8,
    T t9,
    T t10,
    T t11,
    T t12,
    T t13,
    T t14,
    T t15,
    T t16){
    List<T> l = new ArrayList<>(17);
    l.add(t0);
    l.add(t1);
    l.add(t2);
    l.add(t3);
    l.add(t4);
    l.add(t5);
    l.add(t6);
    l.add(t7);
    l.add(t8);
    l.add(t9);
    l.add(t10);
    l.add(t11);
    l.add(t12);
    l.add(t13);
    l.add(t14);
    l.add(t15);
    l.add(t16);
    return l;
  }

  public static <T> List<T> asList(
    T t0,
    T t1,
    T t2,
    T t3,
    T t4,
    T t5,
    T t6,
    T t7,
    T t8,
    T t9,
    T t10,
    T t11,
    T t12,
    T t13,
    T t14,
    T t15,
    T t16,
    T t17){
    List<T> l = arrayList(
      t0,
      t1,
      t2,
      t3,
      t4,
      t5,
      t6,
      t7,
      t8,
      t9,
      t10,
      t11,
      t12,
      t13,
      t14,
      t15,
      t16,
      t17
    );
    return Collections.unmodifiableList(l);
  }

  public static <T> List<T> arrayList(
    T t0,
    T t1,
    T t2,
    T t3,
    T t4,
    T t5,
    T t6,
    T t7,
    T t8,
    T t9,
    T t10,
    T t11,
    T t12,
    T t13,
    T t14,
    T t15,
    T t16,
    T t17){
    List<T> l = new ArrayList<>(18);
    l.add(t0);
    l.add(t1);
    l.add(t2);
    l.add(t3);
    l.add(t4);
    l.add(t5);
    l.add(t6);
    l.add(t7);
    l.add(t8);
    l.add(t9);
    l.add(t10);
    l.add(t11);
    l.add(t12);
    l.add(t13);
    l.add(t14);
    l.add(t15);
    l.add(t16);
    l.add(t17);
    return l;
  }

  public static <T> List<T> asList(
    T t0,
    T t1,
    T t2,
    T t3,
    T t4,
    T t5,
    T t6,
    T t7,
    T t8,
    T t9,
    T t10,
    T t11,
    T t12,
    T t13,
    T t14,
    T t15,
    T t16,
    T t17,
    T t18){
    List<T> l = arrayList(
      t0,
      t1,
      t2,
      t3,
      t4,
      t5,
      t6,
      t7,
      t8,
      t9,
      t10,
      t11,
      t12,
      t13,
      t14,
      t15,
      t16,
      t17,
      t18
    );
    return Collections.unmodifiableList(l);
  }

  public static <T> List<T> arrayList(
    T t0,
    T t1,
    T t2,
    T t3,
    T t4,
    T t5,
    T t6,
    T t7,
    T t8,
    T t9,
    T t10,
    T t11,
    T t12,
    T t13,
    T t14,
    T t15,
    T t16,
    T t17,
    T t18){
    List<T> l = new ArrayList<>(19);
    l.add(t0);
    l.add(t1);
    l.add(t2);
    l.add(t3);
    l.add(t4);
    l.add(t5);
    l.add(t6);
    l.add(t7);
    l.add(t8);
    l.add(t9);
    l.add(t10);
    l.add(t11);
    l.add(t12);
    l.add(t13);
    l.add(t14);
    l.add(t15);
    l.add(t16);
    l.add(t17);
    l.add(t18);
    return l;
  }

  public static <T> List<T> asList(
    T t0,
    T t1,
    T t2,
    T t3,
    T t4,
    T t5,
    T t6,
    T t7,
    T t8,
    T t9,
    T t10,
    T t11,
    T t12,
    T t13,
    T t14,
    T t15,
    T t16,
    T t17,
    T t18,
    T t19){
    List<T> l = arrayList(
      t0,
      t1,
      t2,
      t3,
      t4,
      t5,
      t6,
      t7,
      t8,
      t9,
      t10,
      t11,
      t12,
      t13,
      t14,
      t15,
      t16,
      t17,
      t18,
      t19
    );
    return Collections.unmodifiableList(l);
  }

  public static <T> List<T> arrayList(
    T t0,
    T t1,
    T t2,
    T t3,
    T t4,
    T t5,
    T t6,
    T t7,
    T t8,
    T t9,
    T t10,
    T t11,
    T t12,
    T t13,
    T t14,
    T t15,
    T t16,
    T t17,
    T t18,
    T t19){
    List<T> l = new ArrayList<>(20);
    l.add(t0);
    l.add(t1);
    l.add(t2);
    l.add(t3);
    l.add(t4);
    l.add(t5);
    l.add(t6);
    l.add(t7);
    l.add(t8);
    l.add(t9);
    l.add(t10);
    l.add(t11);
    l.add(t12);
    l.add(t13);
    l.add(t14);
    l.add(t15);
    l.add(t16);
    l.add(t17);
    l.add(t18);
    l.add(t19);
    return l;
  }
}
