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

import com.jingtum.Jingtum;
import com.jingtum.exception.APIConnectionException;
import com.jingtum.exception.APIException;
import com.jingtum.exception.AuthenticationException;
import com.jingtum.exception.ChannelException;
import com.jingtum.exception.FailedException;
import com.jingtum.exception.InvalidParameterException;
import com.jingtum.exception.InvalidRequestException;
import com.jingtum.model.JingtumAmount;
import com.jingtum.model.Order;
import com.jingtum.model.RequestResult;
import com.jingtum.model.Wallet;
import com.jingtum.util.Utility;

public class SubscribeExample {
	@SuppressWarnings("unused")
	public static void main(String[] args) throws AuthenticationException, InvalidRequestException, APIConnectionException, APIException, ChannelException, InvalidParameterException, InterruptedException, FailedException{
		
		Wallet wallet = new Wallet("js4UaG1pjyCEi9f867QHJbWwD3eo6C5xsa","snqFcHzRe22JTM8j7iZVpQYzxEEbW");	
		SubscribeEventHandlerExample sme = new SubscribeEventHandlerExample(); //先实例化EventHandler
		System.out.println("---------建立连接---------");
		boolean isConnected = Utility.openConnection(sme);
		System.out.println("---------连接状态：" + isConnected);
		System.out.println("---------订阅---------");
		boolean isSubscribed = wallet.subscribe();
		System.out.println("---------订阅状态：" + isSubscribed);
		System.out.println("---------异步支付---------");
		//异步支付
		JingtumAmount jtc = new JingtumAmount(); //构建支付的货币
		jtc.setCounterparty(""); //货币发行方
		jtc.setCurrency(Jingtum.getCurrencySWT()); //货币单位
		jtc.setValue(1000); //金额
		RequestResult payment = wallet.pay("jsTo3VKjQrgDKdm5iQdMjc9YqDfrWJn1hw",jtc, false, Utility.getUID());
		
		System.out.println("---------异步挂单---------");
		//异步挂单
		JingtumAmount pay = new JingtumAmount(); //构建JingtumCurrency 实例
		pay.setCounterparty(""); //Currency counterparty
		pay.setCurrency(Jingtum.getCurrencySWT()); //单位
		pay.setValue(1); //数量
		JingtumAmount get = new JingtumAmount();
		get.setCounterparty("janxMdrWE2SUzTqRUtfycH4UGewMMeHa9f");
		get.setCurrency(Jingtum.getCurrencyCNY());
		get.setValue(2);
		RequestResult od = wallet.putOrder(Order.OrderType.sell, pay, get, false);
		
		System.out.println("---------取消订阅---------");
		Thread.sleep(15000); //等待之前两个操作返回的消息，之后收钱的操作应该收不到消息了
		boolean isUnsubscribed = wallet.unsubscribe();
		System.out.println("---------取消订阅状态：" + isUnsubscribed);
		
		System.out.println("---------收钱---------");
		//收钱
		Wallet wallet2 = new Wallet("jsTo3VKjQrgDKdm5iQdMjc9YqDfrWJn1hw","sseBFE4ZydZCaVEYctv6UedGnbnwn");
		JingtumAmount jtc2 = new JingtumAmount(); //构建支付的货币
		jtc2.setCounterparty(""); //货币发行方
		jtc2.setCurrency(Jingtum.getCurrencySWT()); //货币单位
		jtc2.setValue(10); //金额
		RequestResult payment2 = wallet2.pay("js4UaG1pjyCEi9f867QHJbWwD3eo6C5xsa",jtc2, false, Utility.getUID() );
		
		Thread.sleep(15000); //等待能否收到收钱的消息
		System.out.println("---------断开连接---------");
		boolean isClosed = Utility.closeConnection();
		System.out.println("---------断开连接状态：" + isClosed);
		
		System.out.println("---------end---------");
	}
}
