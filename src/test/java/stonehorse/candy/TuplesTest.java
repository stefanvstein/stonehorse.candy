package stonehorse.candy;

import org.junit.Test;

import static stonehorse.candy.Tuples.*;
import static junit.framework.TestCase.assertEquals;

public class TuplesTest {
    T1<Integer> inc(T1<Integer> f){
        return f._1(f._1());
    }
    @Test
    public void firstTest(){
        T1<String> t = of("Olle");
        assertEquals("[Olle]", t.toString());
        assertEquals(of("Olle"), t);
        assertEquals(of("Olle").hashCode(), t.hashCode());
        t=t._1("Nisse");
        assertEquals("Nisse",t._1());
        assertEquals("Nisse",Tuples.first(t));


        T1<Integer> y = t.with_1(23);
        T2<Integer, String> u = y.with_2("Olle");
        y=u;
        y.with_2(3);
        assertEquals("[23, Olle]", u.toString());
        assertEquals(of(23,"Olle"), u);
        assertEquals(of(23, "Olle").hashCode(), u.hashCode());
        assertEquals(Integer.valueOf(23), u._1());
        assertEquals("Olle", u._2());
        assertEquals(of(24,"Olle"), u._1(24));
        assertEquals(of(23,"Nisse"), u._2("Nisse"));
        assertEquals(of("Olle"), u.skip());
        assertEquals(of("Nisse","Olle"), u.with_1("Nisse"));
        assertEquals(Integer.valueOf(23),Tuples.first(u));
        assertEquals("Olle",Tuples.second(u));


        T3<Integer, String, Double> t3 = u.with_3(2.3);
        assertEquals("[23, Olle, 2.3]", t3.toString());
        assertEquals(of(23,"Olle", 2.3), t3);
        assertEquals(of(23,"Olle", 2.3).hashCode(), t3.hashCode());
        assertEquals(Integer.valueOf(23), t3._1());
        assertEquals("Olle", t3._2());
        assertEquals(2.3, t3._3());
        assertEquals(of(24,"Olle", 2.3), t3._1(24));
        assertEquals(of("Some","Olle", 2.3), t3.with_1("Some"));
        assertEquals(of(23,"Nisse", 2.3), t3._2("Nisse"));
        assertEquals(of(23,15, 2.3), t3.with_2(15));
        assertEquals(of(23,"Olle", 2.4), t3._3(2.4));
        assertEquals(of(23,"Olle", "Float"), t3.with_3("Float"));
        assertEquals(of("Olle", 2.3), t3.skip());
        assertEquals(Integer.valueOf(23),Tuples.first(t3));
        assertEquals("Olle",Tuples.second(t3));
        assertEquals(Double.valueOf(2.3),Tuples.third(t3));


        T4<Integer, String, Double, Integer> t4 = t3.with_4(1);
        assertEquals("[23, Olle, 2.3, 1]", t4.toString());
        assertEquals(of(23,"Olle", 2.3, 1), t4);
        assertEquals(of(23,"Olle", 2.3, 1).hashCode(), t4.hashCode());
        assertEquals(Integer.valueOf(23), t4._1());
        assertEquals("Olle", t4._2());
        assertEquals(Double.valueOf(2.3), t4._3());
        assertEquals(Integer.valueOf(1), t4._4());
        assertEquals(of(24,"Olle", 2.3,1), t4._1(24));
        assertEquals(of("Some","Olle", 2.3, 1), t4.with_1("Some"));
        assertEquals(of(23,"Nisse", 2.3, 1), t4._2("Nisse"));
        assertEquals(of(23,15, 2.3, 1), t4.with_2(15));
        assertEquals(of(23,"Olle", 2.4, 1), t4._3(2.4));
        assertEquals(of(23,"Olle", "Float", 1), t4.with_3("Float"));
        assertEquals(of(23,"Olle", 2.3, 2), t4._4(2));
        assertEquals(of(23,"Olle", 2.3, "Stol"), t4.with_4("Stol"));
        assertEquals(of("Olle", 2.3,1), t4.skip());
        assertEquals(Integer.valueOf(23),Tuples.first(t4));
        assertEquals("Olle",Tuples.second(t4));
        assertEquals(Double.valueOf(2.3),Tuples.third(t4));
        assertEquals(Integer.valueOf(1),Tuples.fourth(t4));


        T5<Integer, String, Double, Integer, String> t5 = t4.with_5("Five");
        assertEquals("[23, Olle, 2.3, 1, Five]", t5.toString());
        assertEquals(of(23,"Olle", 2.3, 1, "Five"), t5);
        assertEquals(of(23,"Olle", 2.3, 1,"Five").hashCode(), t5.hashCode());
        assertEquals(Integer.valueOf(23), t5._1());
        assertEquals("Olle", t5._2());
        assertEquals(Double.valueOf(2.3), t5._3());
        assertEquals(Integer.valueOf(1), t5._4());
        assertEquals(of(24,"Olle", 2.3,1, "Five"), t5._1(24));
        assertEquals(of("Some","Olle", 2.3, 1, "Five"), t5.with_1("Some"));
        assertEquals(of(23,"Nisse", 2.3, 1,"Five"), t5._2("Nisse"));
        assertEquals(of(23,15, 2.3, 1, "Five"), t5.with_2(15));
        assertEquals(of(23,"Olle", 2.4, 1,"Five"), t5._3(2.4));
        assertEquals(of(23,"Olle", "Float", 1,"Five"), t5.with_3("Float"));
        assertEquals(of(23,"Olle", 2.3, 2,"Five"), t5._4(2));
        assertEquals(of(23,"Olle", 2.3, "Stol","Five"), t5.with_4("Stol"));
        assertEquals(of(23,"Olle", 2.3, 1,"FIVE"), t5._5("FIVE"));
        assertEquals(of(23,"Olle", 2.3, 1,3), t5.with_5(3));
        assertEquals("Five", t5._5());
        assertEquals(of("Olle", 2.3,1,"Five"), t5.skip());
        assertEquals(Integer.valueOf(23),Tuples.first(t5));
        assertEquals("Olle",Tuples.second(t5));
        assertEquals(Double.valueOf(2.3),Tuples.third(t5));
        assertEquals(Integer.valueOf(1),Tuples.fourth(t5));
        assertEquals("Five",Tuples.fifth(t5));
    }


}
