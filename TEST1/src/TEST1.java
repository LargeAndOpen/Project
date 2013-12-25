
/*
�ت�:�ھڿ�J���a�Ͽ�X�۹��������
��J:�a��
��X:
1.�����o�Ӧa�Ϫ�������T
2.�����o�Ӧa�Ϫ����I��T
3.�����o�Ӧa�Ϫ���J��T
��:�|��X�e10����ơA����10���h��X���Ƶ�������
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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.net.*;


public class TEST1{
	
	// �s�u
	private Connection con;
	
	// �إ߸�Ʈw
	public class Create_database{
		
		// Column Strings
		private String[] cols = {"id","title","catalogs_id","address","phone","business_hrs","price","Description","route","tianmama","ImageUrl","sc_id","post_id","link","x","y","create_date","modify_date"};
		private String[] cols2 = {"id","title","catalogs_id","address","phone","business_hrs","price","Description","route","ImageUrl","sc_id","post_id","link","x","y","create_date","modify_date"};
		
		// Urls
		private String[] urls = {"http://data.coa.gov.tw:8080/od/data/api/eir01/?$format=json","http://data.coa.gov.tw:8080/od/data/api/eir04/?$format=json","http://data.coa.gov.tw:8080/od/data/api/eir02/?$format=json"};
		
		// Filename
		private String[] files = {"food","site","live"};
		
		// �إ߳s�u
		private void start_link(){
			
			// ����
			try{
				// �s��instance
				//Class.forName("org.postgresql.Driver").newInstance();
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				
				// ��Ʈw��m�Ϋإ߳s�u
				//String url="jdbc:postgresql://210.61.10.89:9999/Team8";
				String url="jdbc:mysql://localhost/large?characterEncoding=utf-8";
				//con = DriverManager.getConnection(url,"Team8","LAO2013");
				con = DriverManager.getConnection(url,"large","large");
			}
			// �ҥ~�B�z
			catch(Exception ee){
				System.out.print(ee.getMessage());
			}
		}
		
		// �����s�u
		private void end_link(){
			
			// ����
			try{
				// �s�u����				
				con.close();
				
			// �ҥ~�B�z
			}catch(Exception ee){
				System.out.print(ee.getMessage());
			}
		}
		
		// �R���¦���Table
		private void delete_tables(){
			
			// ����
			try{
				// �ԭz�l
				Statement st = con.createStatement();
				
				// �����B���I�B��J
				String sql;
				sql = "DROP TABLE food;";
				st.execute(sql);
				sql = "DROP TABLE site;";
				st.execute(sql);
				sql = "DROP TABLE live;";
				st.execute(sql);
				st.close();
				
			// �ҥ~�B�z
			}catch(Exception ee){
				System.out.print(ee.getMessage());
			}
		}
		
		// �إ߷s��Table
		private void create_tables(){
			
			// ����
			try{
				// �ԭz�l
				Statement st = con.createStatement();
				
				// �����B���I�B��J
				String sql;
				sql = "CREATE TABLE food(" +
				      "id varchar(50) NOT NULL," +
				      "title varchar(50) NOT NULL," +
					  "catalogs_id varchar(30) NOT NULL," +
					  "address varchar(100) NOT NULL," +
					  "phone varchar(100) NOT NULL," +
					  "business_hrs varchar(120) NOT NULL," +
					  "price varchar(400) NOT NULL," +
					  "Description varchar(1200) NOT NULL," +
					  "route varchar(300) NOT NULL," +
					  "tianmama varchar(10) NOT NULL," +
					  "ImageUrl varchar(150) NOT NULL," +
					  "sc_id varchar(2) NOT NULL," +
					  "post_id varchar(5) NOT NULL," +
					  "link varchar(150) NOT NULL," +
					  "x varchar(60) NOT NULL," +
					  "y varchar(60) NOT NULL," +
					  "create_date varchar(30) NOT NULL," +
					  "modify_date varchar(30) NOT NULL);";
				st.execute(sql);
				sql = "CREATE TABLE site(" +
				      "id varchar(50) NOT NULL," +
				      "title varchar(50) NOT NULL," +
					  "catalogs_id varchar(30) NOT NULL," +
					  "address varchar(100) NOT NULL," +
					  "phone varchar(100) NOT NULL," +
					  "business_hrs varchar(120) NOT NULL," +
					  "price varchar(400) NOT NULL," +
					  "Description varchar(1200) NOT NULL," +
					  "route varchar(300) NOT NULL," +
					  "tianmama varchar(10) NOT NULL," +
					  "ImageUrl varchar(150) NOT NULL," +
					  "sc_id varchar(2) NOT NULL," +
					  "post_id varchar(5) NOT NULL," +
					  "link varchar(150) NOT NULL," +
					  "x varchar(60) NOT NULL," +
					  "y varchar(60) NOT NULL," +
					  "create_date varchar(30) NOT NULL," +
					  "modify_date varchar(30) NOT NULL);";
				st.execute(sql);
				sql = "CREATE TABLE live(" +
				      "id varchar(50) NOT NULL," +
				      "title varchar(50) NOT NULL," +
					  "catalogs_id varchar(30) NOT NULL," +
					  "address varchar(100) NOT NULL," +
					  "phone varchar(100) NOT NULL," +
					  "business_hrs varchar(120) NOT NULL," +
					  "price varchar(400) NOT NULL," +
					  "Description varchar(1200) NOT NULL," +
					  "route varchar(300) NOT NULL," +
					  "ImageUrl varchar(150) NOT NULL," +
					  "sc_id varchar(2) NOT NULL," +
					  "post_id varchar(5) NOT NULL," +
					  "link varchar(150) NOT NULL," +
					  "x varchar(60) NOT NULL," +
					  "y varchar(60) NOT NULL," +
					  "create_date varchar(30) NOT NULL," +
					  "modify_date varchar(30) NOT NULL);";
				st.execute(sql);
				st.close();
				
			// �ҥ~�B�z
			}catch(Exception ee){
				System.out.print(ee.getMessage());
			}
		}
	
		// Parse��ƨæs�i��Ʈw
		private void update(){
			
			int i=0;
			
			// ����
			try{
			
				// �����B���I�B��J
				for(int seq=0;seq<3;seq++)
				{
					// �N��Ƽg�Jtxt��
					getFilefromUrl(urls[seq],files[seq]+".txt");
					File f = new File(files[seq]+".txt");
					FileInputStream file= new FileInputStream(f);
					InputStreamReader input =new InputStreamReader(file,"UTF-8");
					
					// SQL
					String sql = "", tmp;
					Statement st = con.createStatement();
					
					// JSON Parser
					JSONTokener jt = new JSONTokener(input);
					JSONArray item = new JSONArray(jt);
					for(i = 0;i<item.length();i++)
					{
						// �Ҧ����
						sql="INSERT INTO "+files[seq]+" VALUES(";
						
						// �����B���I
						if(seq<2)
						{
							for(int j=0;j<cols.length;j++)
							{
								// ���o����쪺���
								tmp=item.getJSONObject(i).getString(cols[j]);
								tmp=tmp.replaceAll("\"", "\'");
								sql+="\""+tmp+"\"";
								if(j!=cols.length-1) sql+=",";
							}
						}
						// ��J
						else
						{
							for(int j=0;j<cols2.length;j++)
							{
								// ���o����쪺���
								tmp=item.getJSONObject(i).getString(cols2[j]);
								tmp=tmp.replaceAll("\"", "\'");
								sql+="\""+tmp+"\"";
								if(j!=cols2.length-1) sql+=",";
							}
						}
						sql+=");";
						st.execute(sql);
					}
					st.close();
				}
				
			// �ҥ~�B�z
			}catch(Exception ee){
				System.out.println("i="+i);
				System.out.println(ee.getMessage());
			}
		}
	}
	public Create_database getDatabase(){
		Create_database db = new Create_database();
		return db;
	}
 	
	// ������T
	public class Foodinfo
	{
		/*
		��������a�Ϫ�������T
		���o�Ĥ@��Element���W��
		*/
		public String getFirstFoodinfofromCity(String City)throws JSONException, IOException
		{
			//����Open data url
			String foodurl="http://data.coa.gov.tw:8080/od/data/api/eir01/?$format=json";
			//�n���e�X�����
			 String top="&$top=";
			 //�n���L�X�����
			 String skip="&$skip=";
			 //�n�H���򰵱Ƨ�
			 String Order="&$orderby=";
			 //�z��Y�a��
			 String filter="&$filter=";
			  //����r��UTF-8�榡
			 City = java.net.URLEncoder.encode(City,"UTF-8");
			 
			 //�z�����]�w��City
			 filter+="address+like+";
			 filter+=City;
			 foodurl = foodurl+top+skip+Order+filter;
		
			//�N������T�s�bfood.txt
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
			try{
				// �ԭz�l
				Statement st = con.createStatement();
				
				// �ŦX�ӿ���������
				String sql = "SELECT * FROM food WHERE address LIKE '%"+City+"%'";
				
				// ���oResponse
				ResultSet rs = st.executeQuery(sql);
				
				// �C�X�Ҧ��ŦX����(�̦h�e10��)
				int i=0;
				while (rs.next()&&i<10) {
					
					// �L�X
					System.out.println(rs.getString("title"));
					
					// ���W
					i++;
				}
				st.close();
				
			}catch(Exception ee){
				System.out.println(ee.getMessage());
			}
		}
	}
	public  Foodinfo getFoodinfo()
	{
		Foodinfo foodinfo = new Foodinfo();
		return foodinfo;
	}
	
	// ���I��T
	public class Siteinfo
	{
		/*
		��������a�Ϫ����I��T
		���o�Ĥ@��Element���W��
		*/
		public String getFirstSiteinfofromCity(String City)throws JSONException, IOException
		{
			//���IOpen data url
			 String siteurl = "http://data.coa.gov.tw:8080/od/data/api/eir04/?$format=json";
			 //�n���e�X�����
			 String top="&$top=";
			 //�n���L�X�����
			 String skip="&$skip=";
			 //�n�H���򰵱Ƨ�
			 String Order="&$orderby=";
			 //�z��Y�a��
			 String filter="&$filter=";
			  //����r��UTF-8�榡
			 City = java.net.URLEncoder.encode(City,"UTF-8");
			 
			 //�z�����]�w��City
			 filter+="address+like+";
			 filter+=City;
			 
			 siteurl = siteurl+top+skip+Order+filter;
			
			//�N���I��T�s�bsite.txt
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
			try{
				// �ԭz�l
				Statement st = con.createStatement();
				
				// �ŦX�ӿ���������
				String sql = "SELECT * FROM site WHERE address LIKE '%"+City+"%'";
				
				// ���oResponse
				ResultSet rs = st.executeQuery(sql);
				
				// �C�X�Ҧ��ŦX����(�̦h�e10��)
				int i=0;
				while (rs.next()&&i<10) {
					
					// �L�X
					System.out.println(rs.getString("title"));
					
					// ���W
					i++;
				}
				st.close();
				
			}catch(Exception ee){
				System.out.println(ee.getMessage());
			}
		}
	}
	public  Siteinfo getSiteinfo()
	{
		 Siteinfo siteinfo= new Siteinfo();
		return siteinfo;
	}
	
	// ��J��T
	public class Liveinfo
	{
		/*
		��������a�Ϫ���J��T
		���o�Ĥ@��Element���W��
		*/
		public String getFirstLiveinfofromCity(String City)throws JSONException, IOException
		{
			 //��JOpen data url
			 String liveurl= "http://data.coa.gov.tw:8080/od/data/api/eir02/?$format=json";
			 //�n���e�X�����
			 String top="&$top=";
			 //�n���L�X�����
			 String skip="&$skip=";
			 //�n�H���򰵱Ƨ�
			 String Order="&$orderby=";
			 //�z��Y�a��
			 String filter="&$filter=";
			  //����r��UTF-8�榡
			 City = java.net.URLEncoder.encode(City,"UTF-8");
			 
			 //�z�����]�w��City
			 filter+="address+like+";
			 filter+=City;
			 liveurl = liveurl+top+skip+Order+filter;
			
			//�N��J��T�s�blive.txt
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
			try{
				// �ԭz�l
				Statement st = con.createStatement();
				
				// �ŦX�ӿ���������
				String sql = "SELECT * FROM live WHERE address LIKE '%"+City+"%'";
				
				// ���oResponse
				ResultSet rs = st.executeQuery(sql);
				
				// �C�X�Ҧ��ŦX����(�̦h�e10��)
				int i=0;
				while (rs.next()&&i<10) {
					
					// �L�X
					System.out.println(rs.getString("title"));
					
					// ���W
					i++;
				}
				st.close();
				
			}catch(Exception ee){
				System.out.println(ee.getMessage());
			}
		}
	}
	public  Liveinfo getLiveinfo()
	{
		Liveinfo liveinfo= new Liveinfo();
		return liveinfo;
	}
	
	// �D�禡
	public static void main(String[] args)throws JSONException, IOException
	{
		// ��l��class
		TEST1 test = new TEST1();
		
		// ��Ʈw
		Create_database db=test.getDatabase();
		
		// �_�l�s�u
		db.start_link();
		
		/* �u����s�ɤ~�n��������
		// �R���¦�Table�í��s�إ�Table
		db.delete_tables();
		db.create_tables();
		
		// Parse��ƨæs�i��Ʈw
		db.update();
		System.out.println("Finished all Tables");
		/**/
		
		// Query
		BufferedReader buf = new BufferedReader(new InputStreamReader(System.in)); 

		System.out.print("�п�J����:");
		 
		//�ϥΪ̿�J�n�j�M������
		//String City="������";
		String City=buf.readLine();
		
		// ����
		Foodinfo fi = test.getFoodinfo();
		fi.printtop10fromCity(City);
		
		// ���I
		Siteinfo si = test.getSiteinfo();
		si.printtop10fromCity(City);
		
		// ��J
		Liveinfo li = test.getLiveinfo();
		li.printtop10fromCity(City);
		
		// �����s�u
		db.end_link();
	}
	
	/* urlstring => �n�U�������}, objfile =>�s���ɮ�*/
	public static void getFilefromUrl(String urlstring,String objfile)throws JSONException, IOException
	{
		 File desFile =new File(objfile);
		//�p�G�ɮפw�s�b �R����Ӫ��ɮ� 
		if (desFile.exists())
			desFile.delete();
		try{
		URL url = new URL( urlstring );
		URLConnection connection = url.openConnection();
		
		
		//buffer
		byte[] data = new byte[1]; 
		
      //�]�w������Ƭy�ӷ� ,�N�O�n�U�������}
      BufferedInputStream bufferedInputStream = new BufferedInputStream( connection.getInputStream() );
      //�]�w�@�x�s �n�U���ɮת���m. 
      BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(desFile));
      while( bufferedInputStream.read(data) != -1) {
              bufferedOutputStream.write(data);
      }   
	  // �N�w�İϤ�����ƥ����g�X 
      bufferedOutputStream.flush();
	  // ������Ƭy
      bufferedInputStream.close(); 
      bufferedOutputStream.close(); 
		}catch(Exception e)
		{
			System.out.println("Error! No Internet!");
			System.exit(1);
		}
	}
}

