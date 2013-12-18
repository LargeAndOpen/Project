/*
�ت�:�ھڿ��J���a�Ͽ��X�۹���������
���J:�a��
���X:
1.�����o�Ӧa�Ϫ��������T
2.�����o�Ӧa�Ϫ����I���T
3.�����o�Ӧa�Ϫ����J���T
��:�|���X�e10�����ơA����10���h���X�����Ƶ�������
Pattern:
public String getFirstFoodinfofromCity(String City)
=>get first element from food infomation for selected City
public String getFirstSiteinfofromCity(String City)
=>get first element from site infomation for selected City
public String getFirstLiveinfofromCity(String City)
=>get first element from live infomation for selected City
*/
import java.io.*;
import org.json.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.net.*;


public class TEST1{
	
	
	public class Foodinfo
	{
		
		/*
		���������a�Ϫ��������T
		���o�Ĥ@��Element���W��
		*/
		public String getFirstFoodinfofromCity(String City)throws JSONException, IOException
		{
			//����Open data url
			String foodurl="http://data.coa.gov.tw:8080/od/data/api/eir01/?$format=json";
			//�n���e�X������
			 String top="&$top=";
			 //�n���L�X������
			 String skip="&$skip=";
			 //�n�H���򰵱Ƨ�
			 String Order="&$orderby=";
			 //�z���Y�a��
			 String filter="&$filter=";
			  //�����r��UTF-8�榡
			 City = java.net.URLEncoder.encode(City,"UTF-8");
			 
			 //�z�������]�w��City
			 filter+="address+like+";
			 filter+=City;
			 foodurl = foodurl+top+skip+Order+filter;
		
			//�N�������T�s�bfood.txt
			String foodfile = "food.txt";
			getFilefromUrl(foodurl,foodfile);
			
			
			File f = new File(foodfile);
			
			FileInputStream file= new FileInputStream(f);
			InputStreamReader input =new InputStreamReader(file,"UTF-8");
			
			JSONTokener jt = new JSONTokener(input);
			
			JSONArray jsonRealPrice = new JSONArray(jt);
			
			String title=jsonRealPrice.getJSONObject(0).getString("title");
			 
			 return title;
		}
		public void printtop10fromCity(String City)throws JSONException, IOException
		{
			//����Open data url
			String foodurl="http://data.coa.gov.tw:8080/od/data/api/eir01/?$format=json";
			//�n���e�X������
			 String top="&$top=";
			 //�n���L�X������
			 String skip="&$skip=";
			 //�n�H���򰵱Ƨ�
			 String Order="&$orderby=";
			 //�z���Y�a��
			 String filter="&$filter=";
			  //�����r��UTF-8�榡
			 City = java.net.URLEncoder.encode(City,"UTF-8");
			 
			 //�z�������]�w��City
			 filter+="address+like+";
			 filter+=City;
			 foodurl = foodurl+top+skip+Order+filter;
		
			//�N�������T�s�bfood.txt
			String foodfile = "food.txt";
			getFilefromUrl(foodurl,foodfile);
			File f = new File(foodfile);
			
			FileInputStream file= new FileInputStream(f);
			InputStreamReader input =new InputStreamReader(file,"UTF-8");
			
			JSONTokener jt = new JSONTokener(input);
			
			JSONArray jsonRealPrice = new JSONArray(jt);
			System.out.println("�������T");
			for(int i = 0;i<jsonRealPrice.length()&&i<10;i++)
			{
				String title=jsonRealPrice.getJSONObject(i).getString("title");
				System.out.println(title);
				
			}
		}
	}
	public  Foodinfo getFoodinfo()
	{
		Foodinfo foodinfo = new Foodinfo();
		return foodinfo;
	}
	public class Siteinfo
	{
		/*
		���������a�Ϫ����I���T
		���o�Ĥ@��Element���W��
		*/
		public String getFirstSiteinfofromCity(String City)throws JSONException, IOException
		{
			//���IOpen data url
			 String siteurl = "http://data.coa.gov.tw:8080/od/data/api/eir04/?$format=json";
			 //�n���e�X������
			 String top="&$top=";
			 //�n���L�X������
			 String skip="&$skip=";
			 //�n�H���򰵱Ƨ�
			 String Order="&$orderby=";
			 //�z���Y�a��
			 String filter="&$filter=";
			  //�����r��UTF-8�榡
			 City = java.net.URLEncoder.encode(City,"UTF-8");
			 
			 //�z�������]�w��City
			 filter+="address+like+";
			 filter+=City;
			 
			 siteurl = siteurl+top+skip+Order+filter;
			
			//�N���I���T�s�bsite.txt
			String sitefile = "site.txt";
			getFilefromUrl(siteurl,sitefile);
			
			File f1 = new File(sitefile);
			
			FileInputStream file1= new FileInputStream(f1);
			InputStreamReader input1 =new InputStreamReader(file1,"UTF-8");
			
			JSONTokener jt1 = new JSONTokener(input1);
			
			JSONArray jsonRealPrice1 = new JSONArray(jt1);
			String title=jsonRealPrice1.getJSONObject(0).getString("title");
			return title;
		}
		
		public void printtop10fromCity(String City)throws JSONException, IOException
		{
			//���IOpen data url
			 String siteurl = "http://data.coa.gov.tw:8080/od/data/api/eir04/?$format=json";
			 //�n���e�X������
			 String top="&$top=";
			 //�n���L�X������
			 String skip="&$skip=";
			 //�n�H���򰵱Ƨ�
			 String Order="&$orderby=";
			 //�z���Y�a��
			 String filter="&$filter=";
			  //�����r��UTF-8�榡
			 City = java.net.URLEncoder.encode(City,"UTF-8");
			 
			 //�z�������]�w��City
			 filter+="address+like+";
			 filter+=City;
			 
			 siteurl = siteurl+top+skip+Order+filter;
			
			//�N���I���T�s�bsite.txt
			String sitefile = "site.txt";
			getFilefromUrl(siteurl,sitefile);
			
			File f1 = new File(sitefile);
			
			FileInputStream file1= new FileInputStream(f1);
			InputStreamReader input1 =new InputStreamReader(file1,"UTF-8");
			
			JSONTokener jt1 = new JSONTokener(input1);
			
			JSONArray jsonRealPrice1 = new JSONArray(jt1);
			
			System.out.println("���I���T");
			
			for(int i = 0;i<jsonRealPrice1.length()&&i<10;i++)
			{
				String title=jsonRealPrice1.getJSONObject(i).getString("title");
				System.out.println(title);
				
			}
			
		}
	}
	public  Siteinfo getSiteinfo()
	{
		 Siteinfo siteinfo= new Siteinfo();
		return siteinfo;
	}
	public class Liveinfo
	{
		/*
		���������a�Ϫ����J���T
		���o�Ĥ@��Element���W��
		*/
		public String getFirstLiveinfofromCity(String City)throws JSONException, IOException
		{
			 //���JOpen data url
			 String liveurl= "http://data.coa.gov.tw:8080/od/data/api/eir02/?$format=json";
			 //�n���e�X������
			 String top="&$top=";
			 //�n���L�X������
			 String skip="&$skip=";
			 //�n�H���򰵱Ƨ�
			 String Order="&$orderby=";
			 //�z���Y�a��
			 String filter="&$filter=";
			  //�����r��UTF-8�榡
			 City = java.net.URLEncoder.encode(City,"UTF-8");
			 
			 //�z�������]�w��City
			 filter+="address+like+";
			 filter+=City;
			 liveurl = liveurl+top+skip+Order+filter;
			
			//�N���J���T�s�blive.txt
			String livefile = "live.txt";
			getFilefromUrl(liveurl,livefile);
			
			File f2 = new File(livefile);
			
			FileInputStream file2= new FileInputStream(f2);
			InputStreamReader input2 =new InputStreamReader(file2,"UTF-8");
			
			JSONTokener jt2 = new JSONTokener(input2);
			
			JSONArray jsonRealPrice2 = new JSONArray(jt2);
			 String title=jsonRealPrice2.getJSONObject(0).getString("title");
			return title;
		}
		public void printtop10fromCity(String City)throws JSONException, IOException
		{
			//���JOpen data url
			 String liveurl= "http://data.coa.gov.tw:8080/od/data/api/eir02/?$format=json";
			 //�n���e�X������
			 String top="&$top=";
			 //�n���L�X������
			 String skip="&$skip=";
			 //�n�H���򰵱Ƨ�
			 String Order="&$orderby=";
			 //�z���Y�a��
			 String filter="&$filter=";
			  //�����r��UTF-8�榡
			 City = java.net.URLEncoder.encode(City,"UTF-8");
			 
			 //�z�������]�w��City
			 filter+="address+like+";
			 filter+=City;
			 liveurl = liveurl+top+skip+Order+filter;
			
			//�N���J���T�s�blive.txt
			String livefile = "live.txt";
			getFilefromUrl(liveurl,livefile);
			
			File f2 = new File(livefile);
			
			FileInputStream file2= new FileInputStream(f2);
			InputStreamReader input2 =new InputStreamReader(file2,"UTF-8");
			
			JSONTokener jt2 = new JSONTokener(input2);
			
			JSONArray jsonRealPrice2 = new JSONArray(jt2);
			System.out.println("���J���T");
			
			for(int i = 0;i<jsonRealPrice2.length()&&i<10;i++)
			{
				String title=jsonRealPrice2.getJSONObject(i).getString("title");
				System.out.println(title);
				
			}
		}
	}
	public  Liveinfo getLiveinfo()
	{
		Liveinfo liveinfo= new Liveinfo();
		return liveinfo;
	}
	public static void main(String[] args)throws JSONException, IOException
	{
		 //���J
		 BufferedReader buf = new BufferedReader(
            new InputStreamReader(System.in)); 

		 System.out.print("�п��J����:");
		 
		 //�ϥΪ̿��J�n�j�M������
		 String City="������";
		 //String City=buf.readLine();
		
		 TEST1 test = new TEST1();
		 Foodinfo fi = test.getFoodinfo();
		 
		 fi.printtop10fromCity(City);

		Siteinfo si = test.getSiteinfo();
		si.printtop10fromCity(City);
	
		Liveinfo li = test.getLiveinfo();
		li.printtop10fromCity(City);
	}
	
	/*
	urlstring => �n�U�������}
	objfile =>�s�����ɮ�
	*/
	public static void getFilefromUrl(String urlstring,String objfile)throws JSONException, IOException
	{
		 File desFile =new File(objfile);
		//�p�G�ɮפw�s�b �R�����Ӫ��ɮ� 
		if (desFile.exists())
			desFile.delete();
		try{
		URL url = new URL( urlstring );
		URLConnection connection = url.openConnection();
		//buffer
		byte[] data = new byte[1]; 
		
      //�]�w�������Ƭy�ӷ� ,�N�O�n�U�������}
      BufferedInputStream bufferedInputStream = new BufferedInputStream( connection.getInputStream() );
      //�]�w�@�x�s �n�U���ɮת����m. 
      BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(desFile));
      while( bufferedInputStream.read(data) != -1) {
              bufferedOutputStream.write(data);
      }   
	  // �N�w�İϤ������ƥ����g�X 
      bufferedOutputStream.flush();
	  // �������Ƭy
      bufferedInputStream.close(); 
      bufferedOutputStream.close(); 
		}catch(Exception e)
		{
			System.out.println("Error! No Internet!");
			System.exit(1);
		}
	}
	
	
	
	
	
	
}

