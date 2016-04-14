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

import java.security.NoSuchAlgorithmException;
import java.util.Iterator;

import com.jingtum.Jingtum;
import com.jingtum.exception.APIConnectionException;
import com.jingtum.exception.APIException;
import com.jingtum.exception.AuthenticationException;
import com.jingtum.exception.ChannelException;
import com.jingtum.exception.FailedException;
import com.jingtum.exception.InvalidParameterException;
import com.jingtum.exception.InvalidRequestException;
import com.jingtum.model.FinGate;
import com.jingtum.model.Wallet;
import com.jingtum.model.Amount;
import com.jingtum.model.OrderBook;
import com.jingtum.model.OrderBookCollection;
import com.jingtum.model.OrderBookResult;
import com.jingtum.model.Order;
import com.jingtum.model.OrderCollection;
import com.jingtum.model.RequestResult;

/**
 * Order
 *
 */
public class OrderExample {
	public static void main(String[] args) throws AuthenticationException, InvalidRequestException, APIConnectionException, APIException, ChannelException, NoSuchAlgorithmException, InvalidParameterException, FailedException {

		System.out.println("---------挂单---------");
		Wallet wallet = new Wallet("js4UaG1pjyCEi9f867QHJbWwD3eo6C5xsa","snqFcHzRe22JTM8j7iZVpQYzxEEbW"); //根据钱包地址和密钥生成钱包实例
		Amount pay = new Amount(); //构建JingtumCurrency 实例/支付的通
		pay.setCounterparty(""); //Currency counter party
		pay.setCurrency(Jingtum.getCurrencySWT()); //单位
		pay.setValue(1); //数量
		Amount get = new Amount(); //挂单得到的通
		get.setCounterparty("janxMdrWE2SUzTqRUtfycH4UGewMMeHa9f"); //counterparty
		get.setCurrency(Jingtum.getCurrencyCNY()); //currency
		get.setValue(2);
		RequestResult od = wallet.createOrder(Order.OrderType.sell, pay, get, true); //挂单，参数为：挂单类型，支付的currency，获得的currency，是否等待交易结果返回
		//wallet.asyncPutOrder(Order.OrderType.sell, pay, get); 或者直接调用异步同步方法
		//wallet.syncPutOrder(Order.OrderType.sell, pay, get);
		System.out.println(od.getFee()); //交易的手续费
		System.out.println(od.getHash()); //交易的hash值
		System.out.println(od.getSequence()); //交易序列号
		System.out.println(od.getState()); //交易状态
		System.out.println(od.getSuccess()); //请求的结果，是否成功
		
		System.out.println("---------取消挂单---------");
		long sequence = od.getSequence();
		RequestResult od2 = wallet.cancelOrder(sequence, true); //取消挂单，参数为交易序列号和是否等待交易结果
		System.out.println(od2.getFee()); //以下几个输出通挂单示例
		System.out.println(od2.getHash());
		System.out.println(od2.getSequence());
		System.out.println(od2.getState());
		System.out.println(od2.getSuccess());
		
		System.out.println("---------获取全部挂单---------");
		Wallet wallet2 = new Wallet("js4UaG1pjyCEi9f867QHJbWwD3eo6C5xsa","snqFcHzRe22JTM8j7iZVpQYzxEEbW"); 
		OrderCollection oc = wallet2.getOrderList(); //获取所有挂单
		Order od_3;
		Iterator<Order> it_3 = oc.getData().iterator();
		Integer k = 0;
		while(it_3.hasNext())
		{			
			k++;
		    od_3 = (Order)it_3.next();
		    System.out.println("---------Order #" + k);
			System.out.println(od_3.getType()); //挂单类型，sell或者buy
			System.out.println(od_3.getSequence()); //挂单序列号
			System.out.println(od_3.getPassive()); //是否被动交易
			System.out.println(od_3.getReceive().getCurrency()); //获得的货币单位
			System.out.println(od_3.getReceive().getValue()); //获得的金额
			System.out.println(od_3.getReceive().getCounterparty()); //获得货币的发行方
			System.out.println(od_3.getPay().getCurrency()); //支付的货币单位
			System.out.println(od_3.getPay().getValue()); //支付的金额
			System.out.println(od_3.getPay().getCounterparty()); //支付货币的发行方
		}
		
		System.out.println("---------根据hash值获取挂单---------");
		Wallet wallet3 = new Wallet("js4UaG1pjyCEi9f867QHJbWwD3eo6C5xsa","snqFcHzRe22JTM8j7iZVpQYzxEEbW");
		
		Order od_5 = wallet3.getOrder("C05613A5A956FF98DFAB8EFD12A0BC91D862C735E9C8FFF8B500811EDA1DB2BB"); //根据hash值获得挂单
		System.out.println(od_5.getSuccess()); //请求结果
		System.out.println(od_5.getHash()); //交易hash
		System.out.println(od_5.getValidated()); //交易服务器状态
		System.out.println(od_5.getFee()); //交易手续费，SWT计价
		System.out.println(od_5.getAction()); //交易动作类型
		System.out.println(od_5.getDirection()); //交易方向，incoming或outgoing
		System.out.println(od_5.getAccount()); //交易帐号
		System.out.println(od_5.getPay().getCounterparty());
		System.out.println(od_5.getPay().getCurrency());
		System.out.println(od_5.getPay().getValue());
		System.out.println(od_5.getReceive().getCounterparty());
		System.out.println(od_5.getReceive().getCurrency());
		System.out.println(od_5.getReceive().getValue());
		System.out.println(od_5.getPassive()); //交易是否是被动交易
		System.out.println(od_5.getType()); //交易类型，sell或buy
		System.out.println(od_5.getSequence());	//交易序列号
		
		System.out.println("---------获得货币对的挂单列表(市场深度)---------");
		Amount base = new Amount(); //基准货币（currency+counterparty）
		base.setCurrency(Jingtum.getCurrencyCNY());
		base.setCounterparty("jBciDE8Q3uJjf111VeiUNM775AMKHEbBLS");
		
		Amount counter = new Amount(); //目标货币（currency+counterparty）
		counter.setCurrency(Jingtum.getCurrencyUSD());
		counter.setCounterparty("jBciDE8Q3uJjf111VeiUNM775AMKHEbBLS");
		
		OrderBookResult oBR = FinGate.getInstance().getOrderBook(base, counter);
		
		System.out.println(oBR.getOrderbook()); //挂单货币对
		System.out.println(oBR.getSuccess());
		System.out.println(oBR.getValidated());
		OrderBookCollection asks = oBR.getAsks();
		Iterator<OrderBook> it_4 = asks.getData().iterator();
		Integer i = 0;
		OrderBook ob;
		while(it_4.hasNext())
		{
			i++;
			ob = (OrderBook)it_4.next();
			System.out.println("---------ask #" + i);
			System.out.println(ob.getOrder_maker());
			System.out.println(ob.getSequence());
			System.out.println(ob.getPassive());
			System.out.println(ob.getSell());
			System.out.println(ob.getPrice().getCurrency());
			System.out.println(ob.getPrice().getCounterparty());
			System.out.println(ob.getPrice().getValue());
			System.out.println(ob.getTaker_gets_funded().getCurrency());
			System.out.println(ob.getTaker_gets_funded().getCounterparty());
			System.out.println(ob.getTaker_gets_funded().getValue());
			System.out.println(ob.getTaker_gets_total().getCurrency());
			System.out.println(ob.getTaker_gets_total().getCounterparty());
			System.out.println(ob.getTaker_gets_total().getValue());
			System.out.println(ob.getTaker_pays_funded().getCurrency());
			System.out.println(ob.getTaker_pays_funded().getCounterparty());
			System.out.println(ob.getTaker_pays_funded().getValue());
			System.out.println(ob.getTaker_pays_total().getCurrency());
			System.out.println(ob.getTaker_pays_total().getCounterparty());
			System.out.println(ob.getTaker_pays_total().getValue());
		}
		OrderBookCollection bids = oBR.getBids();
		it_4 = bids.getData().iterator();
		i = 0;
		while(it_4.hasNext())
		{
			i++;
			ob = (OrderBook)it_4.next();
			System.out.println("---------bid #" + i);
			System.out.println(ob.getOrder_maker());
			System.out.println(ob.getSequence());
			System.out.println(ob.getPassive());
			System.out.println(ob.getSell());
			System.out.println(ob.getPrice().getCurrency());
			System.out.println(ob.getPrice().getCounterparty());
			System.out.println(ob.getPrice().getValue());
			System.out.println(ob.getTaker_gets_funded().getCurrency());
			System.out.println(ob.getTaker_gets_funded().getCounterparty());
			System.out.println(ob.getTaker_gets_funded().getValue());
			System.out.println(ob.getTaker_gets_total().getCurrency());
			System.out.println(ob.getTaker_gets_total().getCounterparty());
			System.out.println(ob.getTaker_gets_total().getValue());
			System.out.println(ob.getTaker_pays_funded().getCurrency());
			System.out.println(ob.getTaker_pays_funded().getCounterparty());
			System.out.println(ob.getTaker_pays_funded().getValue());
			System.out.println(ob.getTaker_pays_total().getCurrency());
			System.out.println(ob.getTaker_pays_total().getCounterparty());
			System.out.println(ob.getTaker_pays_total().getValue());
		}
		
		System.out.println("---------end---------");
		
	}
}
