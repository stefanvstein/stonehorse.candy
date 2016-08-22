package stonehorse.candy;

import org.junit.Test;

import java.util.function.Function;

import static stonehorse.candy.Functions.compose;
import static stonehorse.candy.Functions.function;
import static stonehorse.candy.Threading.thread;
import static stonehorse.candy.Threading.threadMaybe;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;

public class TestThreading {
    public static class Ex {
        public static int len(String s) {
            return s.length();
        }

        public static int inc(int x) {
            return x + 1;
        }

        public static int plus(int x, int y) {
            return x + y;
        }

        public static Integer toNill(Object o) {
            return null;
        }
    }
    @Test
    public void testThreading() {
        thread("Text",
                Ex::len,
                Ex::inc,
                function(Ex::plus, 4));

        assertEquals(9, (Ex.plus(4, Ex.inc(Ex.len("Text")))));
        assertNull(threadMaybe(null, Ex::len));
        Function<String, Integer> f =
                compose(Ex::len,
                        compose(Ex::inc,
                                function(Ex::plus, 4)));

        assertEquals(Integer.valueOf(9), f.apply("Text"));

        assertNull(threadMaybe("Text",
                Ex::len,
                Ex::toNill,
                Ex::inc));
    }

}
