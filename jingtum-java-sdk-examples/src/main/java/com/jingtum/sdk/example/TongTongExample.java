/*
 * Copyright www.jingtum.com Inc. 
 * 
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * 
 */
package com.jingtum.sdk.example;

import java.util.Iterator;

import com.jingtum.exception.APIConnectionException;
import com.jingtum.exception.APIException;
import com.jingtum.exception.AuthenticationException;
import com.jingtum.exception.ChannelException;
import com.jingtum.exception.FailedException;
import com.jingtum.exception.InvalidParameterException;
import com.jingtum.exception.InvalidRequestException;
import com.jingtum.model.Balance;
import com.jingtum.model.BalanceCollection;
import com.jingtum.model.FinGate;
import com.jingtum.model.TongTong;
import com.jingtum.model.Wallet;

//http://developer.jingtum.com/tongtong-start.html

public class TongTongExample {
	public static void main(String[] args) throws AuthenticationException, InvalidRequestException, APIConnectionException, ChannelException, APIException, FailedException, InvalidParameterException{
		FinGate.getInstance().setTest(true);
		FinGate.getInstance().setFinGate("js4UaG1pjyCEi9f867QHJbWwD3eo6C5xsa", "snqFcHzRe22JTM8j7iZVpQYzxEEbW"); //FinGate地址密码
		FinGate.getInstance().setCustom("JT00000027");  //设置用户编号
		FinGate.getInstance().setCustomSecret("353631eaa0ac5d564f51074e3890a210d745958b732d6d055f83919f2b608c0c"); //用户密码
		String orderNumber = FinGate.getInstance().getNextUUID(); //获得唯一订单号
		
		System.out.println("================issue custom tum================");
		Wallet wallet = FinGate.getInstance().createWallet();
		System.out.println(wallet.getAddress());
		System.out.println(wallet.getSecret());
		FinGate.getInstance().activateWallet(wallet.getAddress());
		
		TongTong tt = FinGate.getInstance().issueCustomTum(orderNumber, "8300000027000020160415201704150120000003", 5, wallet.getAddress());
		System.out.println(tt.getCmd());
		System.out.println(tt.getSystemCode());
		System.out.println(tt.getCustomerCode());
		System.out.println(tt.getErrorMsg());
		System.out.println(tt.getHmac());
		
		System.out.println("================wallet balance================");
		
		try {
			Thread.sleep(10000);  //等待发行通成功
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BalanceCollection bc = wallet.getBalance();
		
		Balance bl;
		Iterator<Balance> it = bc.getData().iterator();
		while(it.hasNext())
		{	
		    bl = (Balance)it.next();
		    System.out.println(bl.getCurrency());
		    System.out.println(bl.getCounterparty());
		    System.out.println(bl.getValue());
		}
		
		System.out.println("================query custom tum issue================");
		try {
			Thread.sleep(5000);  //等待发行通成功
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TongTong tt2 = FinGate.getInstance().queryIssue(orderNumber);
		System.out.println(tt2.getCmd());
		System.out.println(tt2.getSystemCode());
		System.out.println(tt2.getCustomerCode());
		System.out.println(tt2.getOrderNumber());
		System.out.println(tt2.getCustomTum());
		System.out.println(tt2.getCirculation());
		System.out.println(tt2.getCustomTumAccount());
		System.out.println(tt2.getIssueDate());
		System.out.println(tt2.getTransactionHash());
		System.out.println(tt2.isIssueStatus());
		System.out.println(tt2.getHmac());
		
		System.out.println("================query custom tum status================");
		TongTong tt3 = FinGate.getInstance().getCustomTumStatus("8300000027000020160415201704150120000003");
		System.out.println(tt3.getCmd());
		System.out.println(tt3.getSystemCode());
		System.out.println(tt3.getCustomerCode());
		System.out.println(tt3.getCustomTum());
		System.out.println(tt3.getCustomTumName());
		System.out.println(tt3.getCirculation());
		System.out.println(tt3.getCustomTumStatus());
		System.out.println(tt3.getStartDate());
		System.out.println(tt3.getEndDate());
		System.out.println(tt3.getDescription());
		System.out.println(tt3.getBaseUnit());
		System.out.println(tt3.getCredit());
		System.out.println(tt3.getType());
		System.out.println(tt3.isSubdividable());
		System.out.println(tt3.isForbidden());
		System.out.println(tt3.isExchangeable());
		System.out.println(tt3.isExchangeableWithSWT());
		System.out.println(tt3.getPicURL());
		System.out.println(tt3.getHmac());
	}
}
