
/*
目的:根據輸入的地區輸出相對應的資料
輸入:地區
輸出:
1.有關這個地區的美食資訊
2.有關這個地區的景點資訊
3.有關這個地區的住宿資訊
註:會輸出前10筆資料，不足10筆則輸出到資料結尾為止
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
	
	// 連線
	private Connection con;
	
	// 建立資料庫
	public class Create_database{
		
		// Column Strings
		private String[] cols = {"id","title","catalogs_id","address","phone","business_hrs","price","Description","route","tianmama","ImageUrl","sc_id","post_id","link","x","y","create_date","modify_date"};
		private String[] cols2 = {"id","title","catalogs_id","address","phone","business_hrs","price","Description","route","ImageUrl","sc_id","post_id","link","x","y","create_date","modify_date"};
		
		// Urls
		private String[] urls = {"http://data.coa.gov.tw:8080/od/data/api/eir01/?$format=json","http://data.coa.gov.tw:8080/od/data/api/eir04/?$format=json","http://data.coa.gov.tw:8080/od/data/api/eir02/?$format=json"};
		
		// Filename
		private String[] files = {"food","site","live"};
		
		// 建立連線
		private void start_link(){
			
			// 嘗試
			try{
				// 新的instance
				//Class.forName("org.postgresql.Driver").newInstance();
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				
				// 資料庫位置及建立連線
				//String url="jdbc:postgresql://210.61.10.89:9999/Team8";
				String url="jdbc:mysql://localhost/large?characterEncoding=utf-8";
				//con = DriverManager.getConnection(url,"Team8","LAO2013");
				con = DriverManager.getConnection(url,"large","large");
			}
			// 例外處理
			catch(Exception ee){
				System.out.print(ee.getMessage());
			}
		}
		
		// 結束連線
		private void end_link(){
			
			// 嘗試
			try{
				// 連線結束				
				con.close();
				
			// 例外處理
			}catch(Exception ee){
				System.out.print(ee.getMessage());
			}
		}
		
		// 刪除舊有的Table
		private void delete_tables(){
			
			// 嘗試
			try{
				// 敘述子
				Statement st = con.createStatement();
				
				// 美食、景點、住宿
				String sql;
				sql = "DROP TABLE food;";
				st.execute(sql);
				sql = "DROP TABLE site;";
				st.execute(sql);
				sql = "DROP TABLE live;";
				st.execute(sql);
				st.close();
				
			// 例外處理
			}catch(Exception ee){
				System.out.print(ee.getMessage());
			}
		}
		
		// 建立新的Table
		private void create_tables(){
			
			// 嘗試
			try{
				// 敘述子
				Statement st = con.createStatement();
				
				// 美食、景點、住宿
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
				
			// 例外處理
			}catch(Exception ee){
				System.out.print(ee.getMessage());
			}
		}
	
		// Parse資料並存進資料庫
		private void update(){
			
			int i=0;
			
			// 嘗試
			try{
			
				// 美食、景點、住宿
				for(int seq=0;seq<3;seq++)
				{
					// 將資料寫入txt檔
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
						// 所有欄位
						sql="INSERT INTO "+files[seq]+" VALUES(";
						
						// 美食、景點
						if(seq<2)
						{
							for(int j=0;j<cols.length;j++)
							{
								// 取得該欄位的資料
								tmp=item.getJSONObject(i).getString(cols[j]);
								tmp=tmp.replaceAll("\"", "\'");
								sql+="\""+tmp+"\"";
								if(j!=cols.length-1) sql+=",";
							}
						}
						// 住宿
						else
						{
							for(int j=0;j<cols2.length;j++)
							{
								// 取得該欄位的資料
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
				
			// 例外處理
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
 	
	// 美食資訊
	public class Foodinfo
	{
		/*
		獲取相關地區的美食資訊
		取得第一個Element的名稱
		*/
		public String getFirstFoodinfofromCity(String City)throws JSONException, IOException
		{
			//美食Open data url
			String foodurl="http://data.coa.gov.tw:8080/od/data/api/eir01/?$format=json";
			//要取前幾筆資料
			 String top="&$top=";
			 //要跳過幾筆資料
			 String skip="&$skip=";
			 //要以什麼做排序
			 String Order="&$orderby=";
			 //篩選某地區
			 String filter="&$filter=";
			  //中文字轉UTF-8格式
			 City = java.net.URLEncoder.encode(City,"UTF-8");
			 
			 //篩選條件設定為City
			 filter+="address+like+";
			 filter+=City;
			 foodurl = foodurl+top+skip+Order+filter;
		
			//將美食資訊存在food.txt
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
				// 敘述子
				Statement st = con.createStatement();
				
				// 符合該縣市的項目
				String sql = "SELECT * FROM food WHERE address LIKE '%"+City+"%'";
				
				// 取得Response
				ResultSet rs = st.executeQuery(sql);
				
				// 列出所有符合項目(最多前10個)
				int i=0;
				while (rs.next()&&i<10) {
					
					// 印出
					System.out.println(rs.getString("title"));
					
					// 遞增
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
	
	// 景點資訊
	public class Siteinfo
	{
		/*
		獲取相關地區的景點資訊
		取得第一個Element的名稱
		*/
		public String getFirstSiteinfofromCity(String City)throws JSONException, IOException
		{
			//景點Open data url
			 String siteurl = "http://data.coa.gov.tw:8080/od/data/api/eir04/?$format=json";
			 //要取前幾筆資料
			 String top="&$top=";
			 //要跳過幾筆資料
			 String skip="&$skip=";
			 //要以什麼做排序
			 String Order="&$orderby=";
			 //篩選某地區
			 String filter="&$filter=";
			  //中文字轉UTF-8格式
			 City = java.net.URLEncoder.encode(City,"UTF-8");
			 
			 //篩選條件設定為City
			 filter+="address+like+";
			 filter+=City;
			 
			 siteurl = siteurl+top+skip+Order+filter;
			
			//將景點資訊存在site.txt
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
				// 敘述子
				Statement st = con.createStatement();
				
				// 符合該縣市的項目
				String sql = "SELECT * FROM site WHERE address LIKE '%"+City+"%'";
				
				// 取得Response
				ResultSet rs = st.executeQuery(sql);
				
				// 列出所有符合項目(最多前10個)
				int i=0;
				while (rs.next()&&i<10) {
					
					// 印出
					System.out.println(rs.getString("title"));
					
					// 遞增
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
	
	// 住宿資訊
	public class Liveinfo
	{
		/*
		獲取相關地區的住宿資訊
		取得第一個Element的名稱
		*/
		public String getFirstLiveinfofromCity(String City)throws JSONException, IOException
		{
			 //住宿Open data url
			 String liveurl= "http://data.coa.gov.tw:8080/od/data/api/eir02/?$format=json";
			 //要取前幾筆資料
			 String top="&$top=";
			 //要跳過幾筆資料
			 String skip="&$skip=";
			 //要以什麼做排序
			 String Order="&$orderby=";
			 //篩選某地區
			 String filter="&$filter=";
			  //中文字轉UTF-8格式
			 City = java.net.URLEncoder.encode(City,"UTF-8");
			 
			 //篩選條件設定為City
			 filter+="address+like+";
			 filter+=City;
			 liveurl = liveurl+top+skip+Order+filter;
			
			//將住宿資訊存在live.txt
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
				// 敘述子
				Statement st = con.createStatement();
				
				// 符合該縣市的項目
				String sql = "SELECT * FROM live WHERE address LIKE '%"+City+"%'";
				
				// 取得Response
				ResultSet rs = st.executeQuery(sql);
				
				// 列出所有符合項目(最多前10個)
				int i=0;
				while (rs.next()&&i<10) {
					
					// 印出
					System.out.println(rs.getString("title"));
					
					// 遞增
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
	
	// 主函式
	public static void main(String[] args)throws JSONException, IOException
	{
		// 初始化class
		TEST1 test = new TEST1();
		
		// 資料庫
		Create_database db=test.getDatabase();
		
		// 起始連線
		db.start_link();
		
		/* 只有更新時才要全部重建
		// 刪除舊有Table並重新建立Table
		db.delete_tables();
		db.create_tables();
		
		// Parse資料並存進資料庫
		db.update();
		System.out.println("Finished all Tables");
		/**/
		
		// Query
		BufferedReader buf = new BufferedReader(new InputStreamReader(System.in)); 

		System.out.print("請輸入城市:");
		 
		//使用者輸入要搜尋的城市
		String City="高雄市";
		//String City=buf.readLine();
		
		// 美食
		Foodinfo fi = test.getFoodinfo();
		fi.printtop10fromCity(City);
		
		// 景點
		Siteinfo si = test.getSiteinfo();
		si.printtop10fromCity(City);
		
		// 住宿
		Liveinfo li = test.getLiveinfo();
		li.printtop10fromCity(City);
		
		// 結束連線
		db.end_link();
	}
	
	/* urlstring => 要下載的網址, objfile =>存放的檔案*/
	public static void getFilefromUrl(String urlstring,String objfile)throws JSONException, IOException
	{
		 File desFile =new File(objfile);
		//如果檔案已存在 刪除原來的檔案 
		if (desFile.exists())
			desFile.delete();
		try{
		URL url = new URL( urlstring );
		URLConnection connection = url.openConnection();
		
		
		//buffer
		byte[] data = new byte[1]; 
		
      //設定接收資料流來源 ,就是要下載的網址
      BufferedInputStream bufferedInputStream = new BufferedInputStream( connection.getInputStream() );
      //設定　儲存 要下載檔案的位置. 
      BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(desFile));
      while( bufferedInputStream.read(data) != -1) {
              bufferedOutputStream.write(data);
      }   
	  // 將緩衝區中的資料全部寫出 
      bufferedOutputStream.flush();
	  // 關閉資料流
      bufferedInputStream.close(); 
      bufferedOutputStream.close(); 
		}catch(Exception e)
		{
			System.out.println("Error! No Internet!");
			System.exit(1);
		}
	}
}

