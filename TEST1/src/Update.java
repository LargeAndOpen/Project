import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

public class Update {

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
				System.out.println("Connect error!");
				System.out.println(ee.getMessage());
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
				System.out.println("Error!!");
				System.out.println(ee.getMessage());
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
				System.out.println("Error!!");
				System.out.println(ee.getMessage());
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
								tmp=tmp.replaceAll("\'", "\"");
								sql+="\'"+tmp+"\'";
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
								tmp=tmp.replaceAll("\'", "\"");
								sql+="\'"+tmp+"\'";
								if(j!=cols2.length-1) sql+=",";
							}
						}
						sql+=");";
						st.execute(sql);
					}
					st.close();
					
					// 提示完成訊息
					System.out.println("完成"+files[seq]+"建立");
				}
				
			// 例外處理
			}catch(Exception ee){
				System.out.println("Error!!");
				System.out.println(ee.getMessage());
			}
		}
	}
	public Create_database getDatabase(){
		Create_database db = new Create_database();
		return db;
	}
	
	// 主函式
	public static void main(String[] args)throws JSONException, IOException
	{
		// 初始化class
		Update update = new Update();
		
		// 資料庫
		Create_database db=update.getDatabase();
		
		// 起始連線
		db.start_link();
		
		// 刪除舊有Table並重新建立Table
		db.delete_tables();
		db.create_tables();
		
		// Parse資料並存進資料庫
		db.update();
		System.out.println("Finished all Tables");

		// 結束連線
		db.end_link();
	}
	
	/* urlstring => 要下載的網址, objfile =>存放的檔案*/
	public static void getFilefromUrl(String urlstring,String objfile)throws JSONException, IOException
	{
		// 新檔案
		File desFile =new File(objfile);
		 
		//如果檔案已存在 刪除原來的檔案 
		if (desFile.exists()) desFile.delete();
		
		// 嘗試
		try{
			
			// 取得URL
			URL url = new URL( urlstring );
			URLConnection connection = url.openConnection();
			
			//buffer
			byte[] data = new byte[1];
		
			// 設定接收資料流來源 ,就是要下載的網址
			BufferedInputStream bufferedInputStream = new BufferedInputStream( connection.getInputStream() );
			
			// 設定　儲存 要下載檔案的位置. 
			BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(desFile));
			while( bufferedInputStream.read(data) != -1) {
				bufferedOutputStream.write(data);
			}   
			
			// 將緩衝區中的資料全部寫出 
			bufferedOutputStream.flush();
			
			// 關閉資料流
			bufferedInputStream.close(); 
			bufferedOutputStream.close(); 
			}catch(Exception e){
				
				// 沒有網路
				System.out.println("Error! No Internet!");
				System.exit(1);
		}
	}
}
