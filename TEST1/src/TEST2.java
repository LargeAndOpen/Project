
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


public class TEST2{
	
	// 連線
	private Connection con;
	
	// 最大搜尋數量
	public static final int MAX_QUERY = 10;
	
	// 建立資料庫
	public class Create_database{
		
		// 建立連線
		private void start_link(){
			
			// 嘗試
			try{
				// 新的instance
				Class.forName("org.postgresql.Driver").newInstance();
				//Class.forName("com.mysql.jdbc.Driver").newInstance();
				
				// 資料庫位置及建立連線
				String url="jdbc:postgresql://210.61.10.89:9999/Team8";
				//String url="jdbc:mysql://localhost/large?characterEncoding=utf-8";
				con = DriverManager.getConnection(url,"Team8","LAO2013");
				//con = DriverManager.getConnection(url,"large","large");
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
		public void printResult(String City){

			try{
			
				// 敘述子
				Statement st = con.createStatement();
				
				// 符合該縣市的項目
				String sql = "SELECT * FROM food WHERE address LIKE '%"+City+"%'";
				
				// 紀錄偏好
				String prefer="";
				
				// Reading buffer
				BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));
				
				// 不斷讀入偏好
				System.out.println("美食搜尋: 請輸入偏好，一行一個，輸入'.'離開");
				while(true)
				{
					// 偏好
					prefer = buf.readLine();
					
					// 停止讀入
					if(prefer.equals(".")) break;
					
					// 增加偏好搜尋
					sql += " and Description LIKE '%"+prefer+"%'";
				}
				sql += ";";
				
				// 取得Response
				ResultSet rs = st.executeQuery(sql);
				
				// 列出所有符合項目(最多前10個)
				int i=0;
				System.out.println("<美食資訊>");
				while (rs.next()&&i<10) {
					
					// 印出名稱及地址
					System.out.println("("+(i+1)+")--------------");
					System.out.println("名稱: "+rs.getString("title"));
					System.out.println("地址: "+rs.getString("address"));
					
					// 遞增
					i++;
				}
				System.out.println("");
				st.close();
				
			}catch(Exception ee){
				
				// 錯誤訊息
				System.out.println("哇哩勒");
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
		public void printResult(String City){

			try{
			
				// 敘述子
				Statement st = con.createStatement();
				
				// 符合該縣市的項目
				String sql = "SELECT * FROM site WHERE address LIKE '%"+City+"%'";
				
				// 紀錄偏好
				String prefer="";
				
				// Reading buffer
				BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));
				
				// 不斷讀入偏好
				System.out.println("景點搜尋: 請輸入偏好，一行一個，輸入'.'離開");
				while(true)
				{
					// 偏好
					prefer = buf.readLine();
					
					// 停止讀入
					if(prefer.equals(".")) break;
					
					// 增加偏好搜尋
					sql += " and Description LIKE '%"+prefer+"%'";
				}
				sql += ";";
				
				// 取得Response
				ResultSet rs = st.executeQuery(sql);
				
				// 列出所有符合項目(最多前10個)
				int i=0;
				System.out.println("<景點資訊>");
				while (rs.next()&&i<10) {
					
					// 印出名稱及地址
					System.out.println("("+(i+1)+")--------------");
					System.out.println("名稱: "+rs.getString("title"));
					System.out.println("地址: "+rs.getString("address"));
					
					// 遞增
					i++;
				}
				System.out.println("");
				st.close();
				
			}catch(Exception ee){
				
				// 錯誤訊息
				System.out.println("哇哩勒");
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
		public void printResult(String City){

			try{
			
				// 敘述子
				Statement st = con.createStatement();
				
				// 符合該縣市的項目
				String sql = "SELECT * FROM live WHERE address LIKE '%"+City+"%'";
				
				// 紀錄偏好
				String prefer="";
				
				// Reading buffer
				BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));
				
				// 不斷讀入偏好
				System.out.println("住宿搜尋: 請輸入偏好，一行一個，輸入'.'離開");
				while(true)
				{
					// 偏好
					prefer = buf.readLine();
					
					// 停止讀入
					if(prefer.equals(".")) break;
					
					// 增加偏好搜尋
					sql += " and Description LIKE '%"+prefer+"%'";
				}
				sql += ";";
				
				// 取得Response
				ResultSet rs = st.executeQuery(sql);
				
				// 列出所有符合項目(最多前10個)
				int i=0;
				System.out.println("<住宿資訊>");
				while (rs.next()&&i<10) {
					
					// 印出名稱及地址
					System.out.println("("+(i+1)+")--------------");
					System.out.println("名稱: "+rs.getString("title"));
					System.out.println("地址: "+rs.getString("address"));
					
					// 遞增
					i++;
				}
				System.out.println("");
				st.close();
				
			}catch(Exception ee){
				
				// 錯誤訊息
				System.out.println("哇哩勒");
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
		TEST2 test = new TEST2();
		
		// 資料庫
		Create_database db=test.getDatabase();
		
		// 起始連線
		db.start_link();
		
		// Query
		BufferedReader buf = new BufferedReader(new InputStreamReader(System.in)); 
                                
		System.out.print("請輸入城市:");
		 
		//使用者輸入要搜尋的城市
		String City=buf.readLine();
		
		// 美食
		Foodinfo fi = test.getFoodinfo();
		fi.printResult(City);
		
		// 景點
		Siteinfo si = test.getSiteinfo();
		si.printResult(City);
		
		// 住宿
		Liveinfo li = test.getLiveinfo();
		li.printResult(City);

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

