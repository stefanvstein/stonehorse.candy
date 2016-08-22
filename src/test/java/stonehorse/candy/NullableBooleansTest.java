package stonehorse.candy;

import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

public class NullableBooleansTest {
    @Test public void testand(){
        assertFalse(NullableBooleans.and(null,Boolean.TRUE));
        assertFalse(NullableBooleans.and(Boolean.TRUE, null));
        assertFalse(NullableBooleans.and(null,null));
        assertFalse(NullableBooleans.and(Boolean.FALSE,Boolean.TRUE));
        assertFalse(NullableBooleans.and(Boolean.TRUE,Boolean.FALSE));
        assertFalse(NullableBooleans.and(Boolean.FALSE,Boolean.FALSE));
        assertTrue(NullableBooleans.and(Boolean.TRUE,Boolean.TRUE));
    }

    @Test public void testnot(){
        assertTrue(NullableBooleans.not(null));
        assertTrue(NullableBooleans.not(Boolean.FALSE));
        assertFalse(NullableBooleans.not(Boolean.TRUE));
    }

    @Test public void testor(){
        assertTrue(NullableBooleans.or(null,Boolean.TRUE));
        assertTrue(NullableBooleans.or(Boolean.TRUE, null));
        assertFalse(NullableBooleans.or(null,null));
        assertTrue(NullableBooleans.or(Boolean.FALSE,Boolean.TRUE));
        assertTrue(NullableBooleans.or(Boolean.TRUE,Boolean.FALSE));
        assertFalse(NullableBooleans.or(Boolean.FALSE,Boolean.FALSE));
        assertTrue(NullableBooleans.or(Boolean.TRUE,Boolean.TRUE));
    }

    @Test public void testtruth(){
        assertTrue(NullableBooleans.truth(Boolean.TRUE));
        assertFalse(NullableBooleans.truth(Boolean.FALSE));
        assertFalse(NullableBooleans.truth(null));
    }


}
