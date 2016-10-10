package stonehorse.candy;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Supplier;

import static stonehorse.candy.Trampoline.*;
import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


public class TrampolineTest {
    static Supplier<Continuation<Boolean>> isEven(int a) {
        return () -> a == 0 ? done(true) : recur(isOdd(a-1));
    }

    static Supplier<Continuation< Boolean>> isOdd(int a) {
        return () -> a == 0 ? done(false) : recur(isEven(a - 1));
    }

    @Test
    public void testMutalRecursion() {
        assertTrue(trampoline(isEven(200000)));
    }

    static Supplier<Continuation<Integer>> onlyEven(int a){
        return () -> {
            if(a<=0) return done(0);
            if(a%2==0) return seq(onlyEven(a-1),a);
            else return recur(onlyEven(a-1));
        };
    }
    @Test public void testLazy(){
        assertEquals(Arrays.asList(4,2,0), Iterables.forceList(lazy(onlyEven(5))));
        Iterator<Integer> i = lazy(onlyEven(5)).iterator();
        assertEquals(Integer.valueOf(4), i.next());
        assertEquals(Integer.valueOf(2), i.next());
        assertTrue(i.hasNext());
        assertEquals(Integer.valueOf(0), i.next());
        //noinspection EmptyCatchBlock
        try{
            i.next();
            fail();
        }catch(NoSuchElementException e){}
    }

    static Supplier<Continuation< Integer>> nothing(){
        return Trampoline::stop;
    }

    static Supplier<Continuation< Integer>> nil(){
        return () -> null;
    }


    @Test public void testLazyNothing(){
        assertEquals(Collections.emptyList(), Iterables.forceList(lazy(nothing())));
        assertEquals(Collections.emptyList(),Iterables.list(lazy(nil())));
    }

}
