package stonehorse.candy;

import org.junit.Test;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;


public class FunctionsTest {
    @Test
    public void testPartial() {
        assertEquals("lue", Functions.function((BiFunction<String, Integer, String>) String::substring, "Value").apply(2));

        try {
            BiFunction<String, String, String> nil = null;
            Functions.function(nil, "Value");
            fail();
        } catch (NullPointerException e) {
        }

        assertEquals("VALUE", Functions.supplier((Function<String, String>) String::toUpperCase, "value").get());
        try {
            Function<String, String> nil = null;
            Functions.supplier(nil, "Value");
            fail();
        } catch (NullPointerException e) {
        }

    }

    @Test
    public void testComplement() {

        assertFalse(Functions.complement(v -> true).apply("Value"));
        assertTrue(Functions.complement(v -> false).apply("Value"));
        try {
            Predicate<String> nil = null;
            Functions.complement(nil).apply("Value");
            fail();
        } catch (NullPointerException e) {
        }

    }

    @Test
    public void testCompose() {
        Function<String,String> trim=String::trim;
        Function<String,Integer> len=String::length;
        Function<String, String> nil = null;
assertEquals(Integer.valueOf(13), Functions.compose( trim, len).apply(" heading Space"));
        try{
            Functions.compose(nil, trim);
            fail();
        }catch(NullPointerException e){}
        try{
            Functions.compose( trim, nil);
            fail();
        }catch(NullPointerException e){}
    }

    @Test
    public void testIdentity() {
        Object o =new Object();
        assertSame(o, Functions.identity().apply(o));

    }
}
