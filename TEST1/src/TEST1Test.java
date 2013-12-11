/*
 測試 getFirstFoodinfofromCity(String City)的結果有沒有正確
 測試getFirstFoodinfofromCity(String City)的結果有沒有正確
 測試getFirstSiteinfofromCity(String City)的結果有沒有正確
 */

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class TEST1Test {
	private TEST1   u;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		 u = new TEST1();
	}
	
	@Test
	public void testGetFirstFoodinfofromCity() {
		
		//測試台北美食資訊
		try{
		String expected = "一日均衡手抓飯便當";
        String result = u.getFirstFoodinfofromCity("台北");
        assertEquals(expected, result);
		}
		catch(Exception e)
		{
			
		}
	}

	@Test
	public void testGetFirstSiteinfofromCity() {
		
		//測試台中景點資訊
		try{
		String expected = "荔園休閒農園";
        String result = u.getFirstSiteinfofromCity("台中");
        assertEquals(expected, result);
		}
		catch(Exception e)
		{
			
		}
	}

	@Test
	public void testGetFirstLiveinfofromCity() {
		
		//測試台南住宿資訊
		try{
			 String expected = "長奕雅築";
		      String result = u.getFirstLiveinfofromCity("台南");
		      assertEquals(expected, result);
		}
		catch(Exception e)
		{
			
		}
	}

}
