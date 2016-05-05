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
import com.jingtum.model.IssueRecord;
import com.jingtum.model.TumInfo;
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
		
		boolean isSuccessful = FinGate.getInstance().issueCustomTum(orderNumber, "8300000027000020160415201704150120000003", 5, wallet.getAddress());
		System.out.println(isSuccessful);

		
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
		IssueRecord ir = FinGate.getInstance().issueRecord(orderNumber);
		System.out.println(ir.getAccount());
		System.out.println(ir.getAmount());
		System.out.println(ir.getCurrency());
		System.out.println(ir.getDate());
		System.out.println(ir.getOrder());
		System.out.println(ir.getStatus());
		System.out.println(ir.getTxHash());
		
		System.out.println("================query custom tum status================");
		TumInfo ti = FinGate.getInstance().tumInfo("8300000027000020160415201704150120000003");
		System.out.println(ti.getCurrency());
		System.out.println(ti.getName());
		System.out.println(ti.getCirculation());
		System.out.println(ti.getStatus());
		System.out.println(ti.getStartDate());
		System.out.println(ti.getEndDate());
		System.out.println(ti.getDescription());
		System.out.println(ti.getValue());
		System.out.println(ti.getCredit());
		System.out.println(ti.getType());
		System.out.println(ti.getFlags());
		System.out.println(ti.getLogoUrl());
	}
}
