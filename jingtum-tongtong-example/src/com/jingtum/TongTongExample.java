package com.jingtum;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonNode;

import com.utils.DigestUtil;
import com.utils.HttpUtil;

import play.libs.Json;

public class TongTongExample {
	
	//通通测试环境
	public static final String URL = "http://test.tongall.com";
	//用户通发行
	public static final String issuePath = "/currency/issue";
	//用户通发行查询
	public static final String queryIssuePath = "/currency/queryIssue";
	//用户通状态查询
	public static final String statusPath = "/currency/status";
	
	// 通通的发通接口
	public static boolean issueCustomTum(String order_no, String currency, Double amount, String account) {

		//商户编号
		String p1_custom = "JT0000002A";
		//商户密钥
		String sign = "3af37696924d52bbb710e996b3cfcc5d6c8fbf493828786ca40bbf2393d1b79c";	
		
		String paymentPath = URL + issuePath;
		System.out.println("paymentPath---:"+paymentPath);
		DecimalFormat format = new DecimalFormat("0.00");
		String p4_amount = format.format(amount);
		String hmacStr = "IssueCurrency" + p1_custom + order_no + currency + p4_amount
				+ account;
		System.out.println("hmacStr---:"+hmacStr);
		String paramHmac = DigestUtil.hmacSign(hmacStr, sign);
		System.out.println("paramHmac---:"+paramHmac);
		Map<String, String> params = new HashMap<String, String>();
		params.put("p0_cmd", "IssueCurrency");
		params.put("p1_custom", p1_custom);
		params.put("p2_order", order_no);
		params.put("p3_currency", currency);
		params.put("p4_amount", p4_amount);
		params.put("p5_account", account);
		params.put("hmac", paramHmac);
		String paramsStr = params.toString();
		System.out.println("payment params: " + paramsStr);
		List<String> result = null;
		try {
			result = HttpUtil.URLPost(paymentPath, params);
		} catch (IOException e) {
			System.out.println("tongtong currency transfer failed, message is:" + e.getMessage());
			e.printStackTrace();
		}
		System.out.println("tongtong currency result1: " + result);
		if (result != null) {
			JsonNode retNode = Json.parse(result.get(0));
			if (retNode.get("r1_code").asText().equals("1")) {
				return true;
			}
		}
		return false;
	}

	// 查询用户通发行状态
	public static boolean queryIssue(String orderNumber) {

		//商户编号
		String p1_custom = "JT0000002A";
		//商户密钥
		String sign = "3af37696924d52bbb710e996b3cfcc5d6c8fbf493828786ca40bbf2393d1b79c";	
		
		String paymentPath = URL + queryIssuePath;
		System.out.println("paymentPath---:"+paymentPath);
		String hmacStr = "QueryIssue" + p1_custom;
		System.out.println("hmacStr---:"+hmacStr);
		String paramHmac = DigestUtil.hmacSign(hmacStr, sign);
		System.out.println("paramHmac---:"+paramHmac);
		Map<String, String> params = new HashMap<String, String>();
		params.put("p0_cmd", "QueryIssue");
		params.put("p1_custom", p1_custom);
		params.put("p2_order", orderNumber);
		params.put("hmac", paramHmac);
		String paramsStr = params.toString();
		System.out.println("payment params: " + paramsStr);
		List<String> result = null;
		try {
			result = HttpUtil.URLGet(paymentPath, params);
		} catch (IOException e) {
			System.out.println("queryIssue failed, message is:" + e.getMessage());
			e.printStackTrace();
		}
		System.out.println("queryIssue result2: " + result);
		if (result != null) {
			JsonNode retNode = Json.parse(result.get(0));
			if (retNode.get("r1_code").asText().equals("1")) {
				return true;
			}
		}
		return false;
	}	

	// 查询用户通发行状态
	public static boolean queryCustomTum(String currency) {

		//商户编号
		String p1_custom = "JT0000002A";
		//商户密钥
		String sign = "3af37696924d52bbb710e996b3cfcc5d6c8fbf493828786ca40bbf2393d1b79c";	
		
		String paymentPath = URL + statusPath;
		System.out.println("paymentPath---:"+paymentPath); 
		
		String unix = String.valueOf(System.currentTimeMillis()/1000L);
		
		String hmacStr = "statusPath" + p1_custom + currency + unix ;
		System.out.println("hmacStr---:"+hmacStr);
		
		String paramHmac = DigestUtil.hmacSign(hmacStr, sign);
		System.out.println("paramHmac---:"+paramHmac);
		Map<String, String> params = new HashMap<String, String>();
		params.put("p0_cmd", "CurrencyStatus");
		params.put("p1_custom", p1_custom);
		params.put("p2_currency", currency);
		params.put("p3_date", unix);
		params.put("hmac", paramHmac);
		String paramsStr = params.toString();
		System.out.println("payment params3: " + paramsStr);
		List<String> result = null;
		try {
			result = HttpUtil.URLGet(paymentPath, params);
		} catch (IOException e) {
			System.out.println("queryCustomTum failed, message is:" + e.getMessage());
			e.printStackTrace();
		}
		System.out.println("queryCustomTum result3: " + result);
		if (result != null) {
			JsonNode retNode = Json.parse(result.get(0));
			if (retNode.get("r1_code").asText().equals("1")) {
				return true;
			}
		}
		return false;
	}	
	
	
	public static void main(String[] args) {

		//通通发行
		System.out.println("---------------issueCustomTums  begin------------");

		Boolean isSuccessful = TongTongExample.issueCustomTum("d9c2077a-edca-4d56-908c-ea870955321b", "8300000027000020160415201704150120000003", 5.0, "jhMrfpJgx1azAgfbcyzN7jaU4KBGRrVd64");
		System.out.println("isSuccessful-----:"+isSuccessful);
		
		System.out.println("---------------issueCustomTums  end---------------");
		
		//查询用户通发行状态
		System.out.println("---------------queryIssue  begin------------");		

		Boolean queryIssueStatus = TongTongExample.queryIssue("d9c2077a-edca-4d56-908c-ea870955321b");
		System.out.println("queryIssueStatus-----:"+queryIssueStatus);

		System.out.println("---------------queryIssue  end---------------");	

		//用户通状态查询
		System.out.println("---------------queryCustomTum  begin------------");		

		Boolean customTumStatus = TongTongExample.queryCustomTum("8300000027000020160415201704150120000003");
		System.out.println("customTumStatus-----:"+customTumStatus);
		
		System.out.println("---------------queryCustomTum  end---------------");		
	}
}
