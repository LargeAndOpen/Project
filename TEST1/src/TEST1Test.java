/*
 ���� getFirstFoodinfofromCity(String City)�����G���S�����T
 ����getFirstFoodinfofromCity(String City)�����G���S�����T
 ����getFirstSiteinfofromCity(String City)�����G���S�����T
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
		
		//���եx�_������T
		try{
		String expected = "�@�駡�Ť�춺�K��";
        String result = u.getFirstFoodinfofromCity("�x�_");
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
        String result = u.getFirstSiteinfofromCity("�x��");
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
		      String result = u.getFirstLiveinfofromCity("�x�n");
		      assertEquals(expected, result);
		}
		catch(Exception e)
		{
			
		}
	}

}
