package stonehorse.candy;


import org.junit.Test;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static stonehorse.candy.Maybe.*;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class MaybeTest {
    @Test public void testMaybeJustAndNothing(){
        assertTrue(maybe("String").isPresent());
        assertTrue(just("String").isPresent());
        assertFalse(maybe(null).isPresent());
        assertFalse(nothing().isPresent());
        try{
            just(null);
            fail();
        }catch(NullPointerException e){}

    }
    @Test public void testHas() {
        assertFalse(has(nothing()));
        assertFalse(has(null));
        assertTrue(has(just("value")));
    }
    @Test public void testGet(){
        assertEquals("String", get(just("String"), "Nothing"));
        assertEquals("Nothing", get(nothing(), "Nothing"));
        assertEquals("Nothing", get(null, "Nothing"));
    }
    @Test public void testOtherwise(){
        assertEquals("no value", otherwise(nothing(), ()->"no value"));
        assertEquals("something", otherwise(just("something"), ()->"no value"));
        assertEquals("no value", otherwise(null, ()->"no value"));
        try {
            otherwise(null, null);
            fail();
        }catch(NullPointerException e){}

    }
    @Test public void testMap(){
        assertEquals(nothing(), map(String::toUpperCase,Maybe.<String>nothing()));
        Optional<String> nil=null;
        assertEquals(nothing(), map(String::toUpperCase,nil));
        assertEquals(just("VALUE"), map(String::toUpperCase, just("Value")));
        try{
            map(null, nothing());
            fail();
        }catch(NullPointerException e){}
    }
    @Test public void testFlatMap(){
        assertEquals(nothing(), flatmap(s-> map(String::toUpperCase, maybe(s)),Maybe.<String>nothing()));
        assertEquals(just("VALUE"), flatmap(s-> map(String::toUpperCase, maybe(s)),just("value")));
        Optional<String> nil=null;
        assertEquals(nothing(), flatmap(s-> map(String::toUpperCase, maybe(s)),nil));
        try{
            flatmap(null, just("value"));
            fail();
        }catch (NullPointerException e){}
    }
    @Test public void testIfPresent(){
        AtomicReference<String> hit=new AtomicReference<>();
        Maybe.ifPresent(nothing(), hit::set);
        assertNull(hit.get());
        Maybe.ifPresent(just("value"), hit::set);
        assertEquals("value",hit.get());
        try{
        Maybe.ifPresent(nothing(), null);
            fail();}catch(NullPointerException e){}
    }
    @Test public void testFilter(){
        assertEquals(Maybe.just("value"),Maybe.filter(just("value"), v->v.startsWith("v")));
        assertEquals(Maybe.nothing(),Maybe.filter(just("value"), v->v.startsWith("b")));
        Optional<String> nada = nothing();
        assertEquals(Maybe.nothing(),Maybe.filter(nada, v->v.startsWith("b")));
        Optional<String> nil=null;
        assertEquals(Maybe.nothing(),Maybe.filter(nil, v->v.startsWith("b")));
        try{
            Maybe.filter(nothing(), null);
            fail();
        }catch (NullPointerException e){}
    }
}
