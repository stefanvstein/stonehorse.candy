package stonehorse.candy;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static java.lang.Math.toIntExact;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;
import static stonehorse.candy.Choices.*;
import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.fail;


public class ChoicesTest {
    @Test
    public void someCond() {
        assertEquals(1, toIntExact(
                cond(() -> 1 == 2, () -> 7,
                        () -> 1)));
        assertEquals(1, toIntExact(
                cond(() -> null, () -> 7,
                        () -> 1)));
        assertEquals("A", cond(()->"A"));
        assertEquals(1, toIntExact(
                cond(() -> 1 == 2, () -> 7,
                        () -> 1 == 3, () -> 4,
                        () -> 1)));
        assertEquals(4, toIntExact(
                cond(() -> 1 == 2, () -> 7,
                        () -> 1 == 1, () -> 4,
                        () -> 1)));
        assertEquals(7, toIntExact(
                cond(() -> 1 == 1, () -> 7,
                        () -> 1 == 1, () -> 4,
                        () -> 1)));

    }


    @Test
    public void testIfelse() {
        assertEquals("Yes", ifelse(3 == 3, () -> "Yes", () -> "No"));
        assertEquals("No", ifelse(3 == 2, () -> "Yes", () -> "No"));
        assertNull(ifelse(3 == 2, () -> "Yes", () -> null));
        assertNull(ifelse(3 == 3, () -> null, () -> "No"));
        try {
            ifelse(true, null, () -> "true");
            fail();
        } catch (NullPointerException e) {
        }
        try {
            ifelse(true, () -> "Hoo", null);
            fail();
        } catch (NullPointerException e) {
        }
        try {
            ifelse(false, () -> "Hoo", null);
            fail();
        } catch (NullPointerException e) {
        }
    }

    @Test
    public void testWhen() {
        assertEquals("Yes", when(3 == 3, () -> "Yes"));
        assertNull(when(2 == 3, () -> "No"));
        try {
            when(false, null);
            fail();
        } catch (NullPointerException e) {
        }

    }

    @Test
    public void testIfNot() {
        assertEquals("Yes", ifNot(3 == 2, () -> "Yes", () -> "No"));
        assertEquals("No", ifNot(3 == 3, () -> "Yes", () -> "No"));
        assertNull(ifNot(3 == 3, () -> "Yes", () -> null));
        assertNull(ifNot(3 == 2, () -> null, () -> "No"));
        try {
            ifNot(true, null, () -> "true");
            fail();
        } catch (NullPointerException e) {
        }
        try {
            ifNot(true, () -> "Hoo", null);
            fail();
        } catch (NullPointerException e) {
        }
        try {
            ifNot(false, () -> "Hoo", null);
            fail();
        } catch (NullPointerException e) {
        }
    }

    @Test
    public void testWhenNot() {
      //  Supplier<String> s = ()->"Yes";
        assertEquals("Yes", whenNot(3 == 2, () -> "Yes"));
        assertNull(when(2 == 3, () -> "No"));
        try {
            whenNot(false, null);
            fail();
        } catch (NullPointerException e) {
        }
    }

    @Test
    public void testunless() {
        assertEquals("Yes", unless(() -> "Yes", false, () -> "No"));
        assertEquals("No", unless(() -> "Yes", true, () -> "No"));
        try {
            unless(null, true, () -> "No");
            fail();
        } catch (NullPointerException e) {
        }
        try {
            unless(() -> "No", true, null);
            fail();
        } catch (NullPointerException e) {
        }
    }

    @Test
    public void testunlessnull() {
        assertEquals("Value", unlessNull("Value", () -> "Other"));
        assertEquals("Other", unlessNull(null, () -> "Other"));
        try {
            unlessNull(null, null);
            fail();
        } catch (NullPointerException e) {
        }
        try {
            unlessNull("NotNull", null);
            fail();
        } catch (NullPointerException e) {
        }
    }

    @Test
    public void testMapOr() {
        assertEquals(Integer.valueOf(5), mapOr("Value", String::length,()-> 32));
        String nil =null;
        assertEquals(Integer.valueOf(32), mapOr(nil, String::length,()-> 32));
        try{
            mapOr("Value", null, ()->42);
            fail();
        }catch(NullPointerException e){}
        try{
            mapOr("Value", String::length, null);
            fail();
        }catch(NullPointerException e){}
        try{
            mapOr(nil, String::length, null);
            fail();
        }catch(NullPointerException e){}
        try{
            mapOr(nil, null, ()->42);
            fail();
        }catch(NullPointerException e){}
    }

    @Test
    public void testDo(){
        AtomicBoolean hit=new AtomicBoolean(false);
        Choices.doIfElse(false, ()-> hit.set(false), ()->hit.set(true));
        assertTrue(hit.get());
        Choices.doIfElse(true, ()-> hit.set(false), ()->hit.set(true));
        assertFalse(hit.get());

        Choices.doIfNot(true, ()->hit.set(false),()->hit.set(true));
        assertTrue(hit.get());
        Choices.doIfNot(false,()->hit.set(false), ()->hit.set(true));
        assertFalse(hit.get());

        Choices.doWhen(true, ()->hit.set(true));
        assertTrue(hit.get());
        Choices.doWhen(false, ()->hit.set(true));
        assertTrue(hit.get());

        Choices.doWhenNot(true, ()->hit.set(false));
        assertTrue(hit.get());
        Choices.doWhenNot(false, ()->hit.set(false));
        assertFalse(hit.get());
    }
}
