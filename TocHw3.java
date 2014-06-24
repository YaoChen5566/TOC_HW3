/************************************************************

資訊104乙 F74006242 郭曜禎

執行範例:java -jar TocHW3.jar http://www.datagarage.io/api/5365dee31bc6e9d9463a0057 大安區 復興南路 103

輸出範例:22546571

程式敘述:利用inputstreamReader和URL將資料擷取下來，將輸入的argumentlist轉變為Pattern做Regular Expression
	       和JSON格式裡的內容作比較，當有全部相符的資料時則將售價加入Sum變數，Count+1，並把Find變成true，最後再將平均售價印出
	       
************************************************************/
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TocHw3 {

	public static void main(String[] args) {
		
		if(args.length!=4)
		{
			System.out.println("Arguments error!");
			return;
		}
		
		String WebAddress = args[0];
		String City = args[1];
		String Road = args[2];
		int Date = Integer.valueOf(args[3]);
		
		int Sum = 0;
		int Count = 0;
		
		int DataDate = 0;
		int DataMoney = 0;
		boolean Find = false;
		
		Pattern TargetCity = Pattern.compile(City);
		Pattern TargetRoad = Pattern.compile(".*"+Road+".*");
		Matcher matcher;
		try 
		{
			InputStreamReader input = new InputStreamReader(new URL(WebAddress).openStream(),"UTF-8");
			
			JSONArray ParseData = new JSONArray(new JSONTokener(input));
			
			for(int i = 0 ; i < ParseData.length() ; i++)
			{
				JSONObject BigData = ParseData.getJSONObject(i);
				
				matcher = TargetCity.matcher(BigData.get("鄉鎮市區").toString());
				
				if(matcher.find())
				{
					matcher = TargetRoad.matcher(BigData.get("土地區段位置或建物區門牌").toString());
					if(matcher.find())
					{
						
						DataDate = Integer.parseInt(BigData.get("交易年月").toString());
						if((DataDate/100)>=Date)
						{
							DataMoney = Integer.valueOf(BigData.get("總價元").toString());
							Sum += DataMoney;
							Count++;
							Find = true;
						}
					}
				}
				
			}
			if(Find)
			{
				System.out.printf("%d\n",Sum/Count);
			}
			
		}
		catch (IOException e) 
		{
			System.out.println("Exception");
		}
		catch(JSONException e)
		{
			System.out.println("Exception");
		}

	}

}
