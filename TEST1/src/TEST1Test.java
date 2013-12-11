/*
 ���� getFirstFoodinfofromCity(String City)�����G���S�����T
 ����getFirstFoodinfofromCity(String City)�����G���S�����T
 ����getFirstSiteinfofromCity(String City)�����G���S�����T
 */

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;





public class TEST1Test {
	public TEST1   u;
	public TEST1.Foodinfo u1;
	public TEST1.Siteinfo u2;
	public TEST1.Liveinfo u3;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		 u = new TEST1();
		 u1 = u.getFoodinfo();
		 u2 = u.getSiteinfo();
		 u3 = u.getLiveinfo();
	}
	
	@Test
	public void testGetFirstFoodinfofromCity() {
		
		//���եx�_������T
		try{
		String expected = "�@�駡�Ť�춺�K��";
		
		String result = u1.getFirstFoodinfofromCity("�x�_");
        assertEquals(expected, result);
		}
		catch(Exception e)
		{
			
		}
	}

	@Test
	public void testGetFirstSiteinfofromCity() {
		
		//���եx�����I��T
		try{
		String expected = "���𶢹A��";
        String result = u2.getFirstSiteinfofromCity("�x��");
        assertEquals(expected, result);
		}
		catch(Exception e)
		{
			
		}
	}

	@Test
	public void testGetFirstLiveinfofromCity() {
		
		//���եx�n��J��T
		try{
			 String expected = "�������v";
		      String result = u3.getFirstLiveinfofromCity("�x�n");
		      assertEquals(expected, result);
		}
		catch(Exception e)
		{
			
		}
	}

}
