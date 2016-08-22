package stonehorse.candy;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;


public class IdentityTest {
    @Test
    public void testIdentity(){
        assertEquals(Integer.valueOf(4),
                Identity.identity("Value")
                .map(String::length)
                .map(t->t-1)
        .get());
        assertEquals(Integer.valueOf(4),
                Identity.get(Identity.identity("Value")
                        .flatmap(t->Identity.identity(t.length()))
                        .flatmap(t->Identity.identity(t-1))));
        assertEquals("Identity[val]", Identity.identity("val").toString());
        assertEquals(Identity.identity("val"), Identity.identity("val"));
        assertEquals(Identity.identity("val").hashCode(), Identity.identity("val").hashCode());
    }
}
