package stonehorse.candy;


import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertTrue;
import static stonehorse.candy.Atomic.atomic;

public class AtomicTest {
    @Test public void testAtomic(){
        AtomicInteger i= Atomic.atomic(32);

        AtomicReference<Integer> ir =Atomic.atomic(Integer.valueOf(32));
        AtomicLong l= Atomic.atomic(32L);
        AtomicLong g = Atomic.atomic(longer(32));
        AtomicBoolean b = Atomic.atomic(false);
        assertNotNull(i);
        assertNotNull(ir);
        assertNotNull(l);
        assertNotNull(g);
        assertNotNull(b);
        Atomic.swap(b, x->!x);
        assertTrue(Atomic.value(b));
        Atomic.swap(l, x->++x);
        assertEquals(33, Atomic.value(l));

    }



    private static long longer(int i){
        return i;
    }
}
