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
import com.jingtum.model.Wallet;
import com.jingtum.model.BalanceCollection;
import com.jingtum.model.Balance;
import com.jingtum.model.FinGate;
import com.jingtum.model.Amount;
import com.jingtum.model.Currency;
import com.jingtum.model.Payment;
import com.jingtum.model.PaymentCollection;
import com.jingtum.model.RequestResult;

/**
 * Wallet
 *
 */
public class WalletExample {
	public static void main(String[] args) throws AuthenticationException, InvalidRequestException, APIConnectionException, APIException, ChannelException, NoSuchAlgorithmException, InvalidParameterException, FailedException {
		
		Currency usd = new Currency(Jingtum.getCurrencyUSD(),"jBciDE8Q3uJjf111VeiUNM775AMKHEbBLS");
		
		System.out.println("---------创建 Wallet---------");
		Wallet wallet = FinGate.getInstance().createWallet(); //创建新钱包，静态方法
		System.out.println(wallet.getAddress()); //钱包地址
		System.out.println(wallet.getSecret()); //钱包密钥
		
		System.out.println("---------激活 Wallet---------");
		FinGate.getInstance().setFinGate("js4UaG1pjyCEi9f867QHJbWwD3eo6C5xsa", "snqFcHzRe22JTM8j7iZVpQYzxEEbW"); //激活钱包的发送SWT方
		boolean isActivated = FinGate.getInstance().activateWallet(wallet.getAddress());
		System.out.println(isActivated);
		
		System.out.println("---------获取 Wallet balance---------");
		Wallet wallet2 = new Wallet("jfg27BowdBZv6NKnMY94NSpFrzkEWRb1yy","sporReJYeHrxA5pHC8ShpLhfXu6sL");
		BalanceCollection bc = wallet2.getBalance();
		Balance bl;
		Iterator<Balance> it = bc.getData().iterator();
		Integer i = 0;
		while(it.hasNext())
		{			
			i++;
		    bl = (Balance)it.next();
		    System.out.println("---------balance #" + i);
			System.out.println(bl.getValue()); //金额
			System.out.println(bl.getCurrency()); //货币单位
			System.out.println(bl.getCounterparty()); //发行方
			System.out.println(bl.getFreezed()); //冻结金额
		}
		
		System.out.println("---------获得支付路径并支付---------");
		Wallet wallet3 = new Wallet("js4UaG1pjyCEi9f867QHJbWwD3eo6C5xsa","snqFcHzRe22JTM8j7iZVpQYzxEEbW"); 
		Amount jtc = new Amount(); //构建支付的货币
		jtc.setValue(100); //金额
		//jtc.setCounterparty("jBciDE8Q3uJjf111VeiUNM775AMKHEbBLS");
		//jtc.setCurrency(Jingtum.getCurrencyUSD());
		jtc.setJingtumCurrency(usd); //或者采用上面注掉的方法
		PaymentCollection pc = wallet3.getPathList("jHb9CJAWyB4jr91VRWn96DkukG4bwdtyTh",jtc); //获得支付路径
		Payment pay = null;
		Iterator<Payment> it_2 = pc.getData().iterator();
		Integer j = 0;
		while(it_2.hasNext())
		{			
			j++;
		    pay = (Payment)it_2.next();
		    System.out.println("---------payment path#" + j);
		    System.out.println(pay.getSourceAccount());
			System.out.println(pay.getSourceAmount().getValue());
			System.out.println(pay.getSourceAmount().getCurrency());
			System.out.println(pay.getSourceAmount().getIssuer());
			System.out.println(pay.getSourceSlippage());
		    System.out.println(pay.getDestinationAccount());
			System.out.println(pay.getDestinationAmount().getValue());
			System.out.println(pay.getDestinationAmount().getCurrency());
			System.out.println(pay.getDestinationAmount().getIssuer());
			System.out.println(pay.getPaths());
		
		}
		RequestResult payment = wallet3.submitPayment(pay, true, FinGate.getInstance().getNextUUID() ); //支付，参数为：获取方地址，货币，是否等待支付结果，和资源号（选填）
		System.out.println(payment.getHash()); //交易hash值
		System.out.println(payment.getClient_resource_id()); //交易资源号
		System.out.println(payment.getSuccess()); //交易是否成功
		System.out.println(payment.getState()); //交易状态
		System.out.println(payment.getResult()); //支付服务器结果
		System.out.println(payment.getDate()); //支付时间，UNIXTIME时间
		System.out.println(payment.getFee()); //支付费用
		
		System.out.println("---------支付---------");
		Wallet wallet5 = new Wallet("js4UaG1pjyCEi9f867QHJbWwD3eo6C5xsa","snqFcHzRe22JTM8j7iZVpQYzxEEbW");
		Amount jtc5 = new Amount(); //构建支付的货币
		jtc5.setValue(100); //金额
		//jtc5.setCounterparty("jBciDE8Q3uJjf111VeiUNM775AMKHEbBLS");
		//jtc5.setCurrency(Jingtum.getCurrencyUSD()); 
		jtc5.setJingtumCurrency(usd);  //或者采取上面注掉的方法直接设定
		RequestResult rr = wallet5.submitPayment("jHb9CJAWyB4jr91VRWn96DkukG4bwdtyTh",jtc5, true, FinGate.getInstance().getNextUUID() ); //支付，参数为：获取方地址，货币，是否等待支付结果，和资源号（选填）
		System.out.println(rr.getHash()); //交易hash值
		System.out.println(rr.getClient_resource_id()); //交易资源号
		System.out.println(rr.getSuccess()); //交易是否成功
		System.out.println(rr.getState()); //交易状态
		System.out.println(rr.getResult()); //支付服务器结果
		System.out.println(rr.getDate()); //支付时间，UNIXTIME时间
		System.out.println(rr.getFee()); //支付费用
		
		System.out.println("---------根据hash值或者资源号获取 Payment 信息---------");
		Wallet wallet4 = new Wallet("js4UaG1pjyCEi9f867QHJbWwD3eo6C5xsa","snqFcHzRe22JTM8j7iZVpQYzxEEbW");
		
		//Wallet wallet4 = new Wallet("js4UaG1pjyCEi9f867QHJbWwD3eo6C5xsa");
		Payment payment2 = wallet4.getPayment("616FEF2ED71147476D657E5B54F120AFB5F4A4F9FEC721BB37B405540A1A77EB"); 
		System.out.println(payment2.getHash());
		System.out.println(payment2.getClient_resource_id());
		System.out.println(payment2.getSuccess());
		System.out.println(payment2.getState());
		System.out.println(payment2.getResult());
		System.out.println(payment2.getDate());
		System.out.println(payment2.getFee());
		System.out.println(payment2.getType());
		System.out.println(payment2.getCounterparty());
		System.out.println(payment2.getAmount().getIssuer());
		System.out.println(payment2.getAmount().getCurrency());
		System.out.println(payment2.getAmount().getValue());
		
		System.out.println("---------获取全部 Payments 信息---------");
		Wallet wallet6 = new Wallet("js4UaG1pjyCEi9f867QHJbWwD3eo6C5xsa","snqFcHzRe22JTM8j7iZVpQYzxEEbW");
		
		PaymentCollection pc2 = wallet6.getPaymentList(null,null,false,Payment.Direction.all,2,3);
		Payment pay2;
		Iterator<Payment> it_3 = pc2.getData().iterator();
		Integer x = 0;
		while(it_3.hasNext())
		{			
			x++;
		    pay2 = (Payment)it_3.next();
		    System.out.println("---------payment #" + x);
			System.out.println(pay2.getDate());
			System.out.println(pay2.getHash());
			System.out.println(pay2.getType());
			System.out.println(pay2.getFee());
			System.out.println(pay2.getResult());
			System.out.println(pay2.getClient_resource_id());
			System.out.println(pay2.getCounterparty());
			System.out.println(pay2.getAmount().getCurrency());
			System.out.println(pay2.getAmount().getIssuer());
			System.out.println(pay2.getAmount().getValue());
		}
		
		System.out.println("---------end---------");

	}
}
