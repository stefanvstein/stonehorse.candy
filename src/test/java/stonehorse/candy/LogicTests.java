package stonehorse.candy;

import org.junit.Test;



import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class LogicTests {

	@Test
	public void testAnd() {
		assertTrue(SupplierLogic.and(()->true, ()->true));
		assertFalse(SupplierLogic.and(()->true, ()->false));
		assertFalse(SupplierLogic.and(()->false, ()->true));
		assertFalse(SupplierLogic.and(()->false, ()->{throw new IllegalStateException();}));
		assertFalse(SupplierLogic.and(()->false, ()->false));
		assertFalse(SupplierLogic.and(()->false));
		assertTrue(SupplierLogic.and(()->true));
		try{SupplierLogic.and(null);fail();}catch(NullPointerException e){}
		try{SupplierLogic.and(null, ()->false);fail();}catch(NullPointerException e){}
		try{SupplierLogic.and( ()->false, null);fail();}catch(NullPointerException e){}
		assertFalse(SupplierLogic.and(()->null, ()->false));
		assertFalse(SupplierLogic.and(()->null, ()->true));
		assertFalse(SupplierLogic.and(()->null));
		assertTrue(SupplierLogic.and(()->true));
		
	}

	@Test
	public void testOr() {
		assertTrue(SupplierLogic.or(()->true, ()->true));
		assertTrue(SupplierLogic.or(()->true, ()->false));
		assertTrue(SupplierLogic.or(()->true, ()->{throw new IllegalStateException();}));
		assertTrue(SupplierLogic.or(()->false, ()->true));
		assertFalse(SupplierLogic.or(()->false, ()->false));
		assertFalse(SupplierLogic.or(()->false));
		assertTrue(SupplierLogic.or(()->true));
		try{SupplierLogic.or(null);fail();}catch(NullPointerException e){}
		try{SupplierLogic.or(null, ()->false);fail();}catch(NullPointerException e){}
		try{SupplierLogic.or( ()->false, null);fail();}catch(NullPointerException e){}
		assertFalse(SupplierLogic.or(()->null, ()->false));
		assertTrue(SupplierLogic.or(()->null, ()->true));
		assertFalse(SupplierLogic.or(()->null));
		assertTrue(SupplierLogic.or(()->true));
	}

	@Test
	public void testAllEq() {
		assertTrue(SupplierLogic.allEquals("Value", ()->"V"+"alue"));
		assertFalse(SupplierLogic.allEquals("Value", ()->"No"));
		assertFalse(SupplierLogic.allEquals("Value", ()->null));
		try{
			SupplierLogic.allEquals("No", null);
			fail();}
		catch( NullPointerException e){}
		assertTrue(SupplierLogic.allEquals("Value", ()->"V"+"alue", ()->"Value"));
		assertFalse(SupplierLogic.allEquals("Value", ()->"No", ()->"Value"));
        assertFalse(SupplierLogic.allEquals("Value", ()->"No", ()->{throw new IllegalStateException();}));
		assertFalse(SupplierLogic.allEquals("Value", ()->null, ()->"Value"));
		assertFalse(SupplierLogic.allEquals("Value", ()->"Value", ()->null));
		assertTrue(SupplierLogic.allEquals(null, ()->null, ()->null));
		try{
			SupplierLogic.allEquals("No", null,
					()->"Value"); 
			fail();}
		catch( NullPointerException e){}
		try{
			SupplierLogic.allEquals("No", ()->"Value",null);
			fail();}
		catch( NullPointerException e){}
	
	}

	@Test
	public void testAnyEq() {
        assertFalse(SupplierLogic.anyEquals("This", ()->"No"));
        assertTrue(SupplierLogic.anyEquals("This", ()->"This"));
        assertTrue(SupplierLogic.anyEquals("This", ()->"No", ()->"This"));
        assertTrue(SupplierLogic.anyEquals("This", ()->"No", ()->"This", ()->{throw new IllegalStateException();}));
        assertFalse(SupplierLogic.anyEquals("This", ()->"No", ()->"No"));
        assertTrue(SupplierLogic.anyEquals(null, ()->"No", ()->null));
        try{
            SupplierLogic.anyEquals("This", ()->"This", null);
            fail();
            }catch(NullPointerException e){}



	}
}
