package com.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class HttpUtil {
	private static final String URL_PARAM_CONNECT_FLAG = "&";
	private static final int SIZE = 1024 * 1024;

	private HttpUtil() {
	}

	/**
	 * GET请求
	 * 
	 * @param strUrl
	 *            String
	 * @param map
	 *            Map
	 * @throws IOException
	 * @return List
	 */
	public static List<String> URLGet(String strUrl, Map<String, String> map) throws IOException {
		String strtTotalURL = "";
		List<String> result = new ArrayList<String>();
		if (strtTotalURL.indexOf("?") == -1) {
			strtTotalURL = strUrl + "?" + getUrl(map);
		} else {
			strtTotalURL = strUrl + "&" + getUrl(map);
		}
		URL url = new URL(strtTotalURL);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setUseCaches(false);
        
        // 根据ResponseCode判断连接是否成功  
        int responseCode = con.getResponseCode();  
        if (responseCode != 200) {
            System.out.println(" Error，responseCode==" + responseCode);  
        } else {
        	System.out.println("Get Success!"); 
    		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()), SIZE);
    		while (true) {
    			String line = in.readLine();
    			if (line == null) {
    				break;
    			} else {
    				result.add(line);
    			}
    		}
    		in.close();
    		return (result);
        }
		return null;
	}

	/**
	 * POST请求
	 * 
	 * @param strUrl
	 *            String
	 * @param content
	 *            Map
	 * @throws IOException
	 * @return List
	 */
	public static List<String> URLPost(String strUrl, Map<String, String> map) throws IOException {

		String content =  getUrl(map);
		System.out.println("content----:"+content);
		URL url = new URL(strUrl);
		System.out.println("url---:"+url);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setAllowUserInteraction(false);
		con.setUseCaches(false);
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=GBK");
		// 获取con对象对应的输出流
		BufferedWriter bout = new BufferedWriter(new OutputStreamWriter(con.getOutputStream()));
		bout.write(content);
		bout.flush();
		bout.close();
		
        // 根据ResponseCode判断连接是否成功 
        int responseCode = con.getResponseCode();
        if (responseCode != 200) {
            System.out.println(" Error，responseCode==" + responseCode);
        } else {
        	System.out.println("Post Success!");        	
    		// 定义BufferedReader输入流来读取URL的ResponseData
    		BufferedReader bin = new BufferedReader(new InputStreamReader(con.getInputStream()), SIZE);
    		List<String> result = new ArrayList<String>();
    		while (true) {
    			String line = bin.readLine();
    			if (line == null) {
    				break;
    			} else {
    				result.add(line);
    			}
    		}
    		return (result);
        }
		return null;
	}

	/**
	 * getURL
	 * 
	 * @param map
	 *            Map
	 * @return String
	 */
	private static String getUrl(Map<String, String> map) {
		if (null == map || map.keySet().size() == 0) {
			return ("");
		}
		StringBuffer url = new StringBuffer();
		Set<String> keys = map.keySet();
		for (Iterator<String> i = keys.iterator(); i.hasNext();) {
			String key = i.next();
			if (map.containsKey(key)) {
				Object val = map.get(key);
				String str = val != null ? val.toString() : "";
				try {
					str = URLEncoder.encode(str, "GBK");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				url.append(key).append("=").append(str)
						.append(URL_PARAM_CONNECT_FLAG);
			}
		}
		String strURL = "";
		strURL = url.toString();
		if (URL_PARAM_CONNECT_FLAG.equals("" + strURL.charAt(strURL.length() - 1))) {
			strURL = strURL.substring(0, strURL.length() - 1);
		}
		return strURL;
	}
}
