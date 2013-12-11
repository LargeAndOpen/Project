
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.net.*;


public class TEST1{
	
	
	
	
	public static void main(String[] args)throws JSONException, IOException
	{
	
		
		
		
		//Open data存放的資料網址
		//美食Open data url
		String foodurl="http://data.coa.gov.tw/od/data/api/eir01/?$format=json";
		//景點Open data url
		 String siteurl = "http://data.coa.gov.tw/od/data/api/eir04/?$format=json";
		 //住宿Open data url
		 String liveurl= "http://data.coa.gov.tw/od/data/api/eir02/?$format=json";
		 
		 
		 //要取前幾筆資料
		 String top="&$top=";
		 //要跳過幾筆資料
		 String skip="&$skip=";
		 //要以什麼做排序
		 String Order="&$orderby=";
		 //篩選某地區
		 String filter="&$filter=";
		 //輸入
		 BufferedReader buf = new BufferedReader(
            new InputStreamReader(System.in)); 
			
			
		 System.out.print("請輸入城市:");
		 
		 //使用者輸入要搜尋的城市
		 String City=buf.readLine();
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
		
		
		System.out.println("美食資訊");
		for(int i = 0;i<jsonRealPrice.length()&&i<10;i++)
		{
			String title=jsonRealPrice.getJSONObject(i).getString("title");
			System.out.println(title);
			
		}
		
		siteurl = siteurl+top+skip+Order+filter;
		
		//將景點資訊存在site.txt
		String sitefile = "site.txt";
		getFilefromUrl(siteurl,sitefile);
		
		File f1 = new File(sitefile);
		
		FileInputStream file1= new FileInputStream(f1);
		InputStreamReader input1 =new InputStreamReader(file1,"UTF-8");
		
		JSONTokener jt1 = new JSONTokener(input1);
		
		JSONArray jsonRealPrice1 = new JSONArray(jt1);
		System.out.println("景點資訊");
		
		for(int i = 0;i<jsonRealPrice1.length()&&i<10;i++)
		{
			String title=jsonRealPrice1.getJSONObject(i).getString("title");
			System.out.println(title);
			
		}
		
		siteurl = siteurl+top+skip+Order+filter;
		
		//將住宿資訊存在live.txt
		String livefile = "live.txt";
		getFilefromUrl(liveurl,livefile);
		
		File f2 = new File(livefile);
		
		FileInputStream file2= new FileInputStream(f2);
		InputStreamReader input2 =new InputStreamReader(file2,"UTF-8");
		
		JSONTokener jt2 = new JSONTokener(input2);
		
		JSONArray jsonRealPrice2 = new JSONArray(jt2);
		System.out.println("住宿資訊");
		
		for(int i = 0;i<jsonRealPrice2.length()&&i<10;i++)
		{
			String title=jsonRealPrice2.getJSONObject(i).getString("title");
			System.out.println(title);
			
		}
		
	}
	
	/*
	urlstring => 要下載的網址
	objfile =>存放的檔案
	*/
	public static void getFilefromUrl(String urlstring,String objfile)throws JSONException, IOException
	{
		 File desFile =new File(objfile);
		//如果檔案已存在 刪除原來的檔案 
		if (desFile.exists())
			desFile.delete();
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
		
	}
	
	/*
	獲取相關地區的美食資訊
	取得第一個Element的名稱
	*/
	public String getFirstFoodinfofromCity(String City)throws JSONException, IOException
	{
		//美食Open data url
		String foodurl="http://data.coa.gov.tw/od/data/api/eir01/?$format=json";
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
	/*
	獲取相關地區的景點資訊
	取得第一個Element的名稱
	*/
	public String getFirstSiteinfofromCity(String City)throws JSONException, IOException
	{
		//景點Open data url
		 String siteurl = "http://data.coa.gov.tw/od/data/api/eir04/?$format=json";
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
	/*
	獲取相關地區的住宿資訊
	取得第一個Element的名稱
	*/
	public String getFirstLiveinfofromCity(String City)throws JSONException, IOException
	{
		 //住宿Open data url
		 String liveurl= "http://data.coa.gov.tw/od/data/api/eir02/?$format=json";
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
	
}

