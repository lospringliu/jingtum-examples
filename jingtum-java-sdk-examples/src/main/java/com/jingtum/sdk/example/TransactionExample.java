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

import com.jingtum.exception.APIConnectionException;
import com.jingtum.exception.APIException;
import com.jingtum.exception.AuthenticationException;
import com.jingtum.exception.ChannelException;
import com.jingtum.exception.FailedException;
import com.jingtum.exception.InvalidParameterException;
import com.jingtum.exception.InvalidRequestException;
import com.jingtum.model.Transaction;
import com.jingtum.model.TransactionCollection;
import com.jingtum.model.Wallet;

/**
 * Transaction
 *
 */
public class TransactionExample {
	public static void main(String[] args) throws AuthenticationException, InvalidRequestException, APIConnectionException, APIException, ChannelException, NoSuchAlgorithmException, InvalidParameterException, FailedException {

		Wallet wallet = new Wallet("js4UaG1pjyCEi9f867QHJbWwD3eo6C5xsa","snqFcHzRe22JTM8j7iZVpQYzxEEbW");
		Transaction tran;
		
		System.out.println("---------获取所有transaction---------");
		TransactionCollection tc = wallet.getTransactionList(null,false,Transaction.DirectionType.all,1,2); //参数为：支付交易的接收方地址，是否移除失败的交易历史，支付交易的方向，incoming或outgoing
		//TransactionCollection tc = wallet.getTransactions(); 或者不加过滤条件，获取所有
		Iterator<Transaction> it1 = tc.getData().iterator();
		Integer i = 0;
		while(it1.hasNext())
		{
			i++;
			tran = (Transaction)it1.next();
			System.out.println("---------Transaction #" + i);
			System.out.println(tran.getType()); //交易类型
			System.out.println(tran.getClient_resource_id()); //交易资源号
			System.out.println(tran.getCounterparty()); //交易对家
			System.out.println(tran.getDate()); //交易时间，UNIXTIME
			System.out.println(tran.getFee()); //交易费用，井通计价
			System.out.println(tran.getHash()); //交易hash
			System.out.println(tran.getResult()); //交易结果
			if(tran.getAmount() != null){
				System.out.println(tran.getAmount().getCurrency()); //货币单位
				System.out.println(tran.getAmount().getIssuer()); //货币发行方
				System.out.println(tran.getAmount().getValue()); //金额
			}
			if(tran.getGets() != null){
				System.out.println(tran.getGets().getCurrency()); //货币单位
				System.out.println(tran.getGets().getIssuer()); //货币发行方
				System.out.println(tran.getGets().getValue()); //金额
			}
			if(tran.getPays() != null){
				System.out.println(tran.getPays().getCurrency()); //货币单位
				System.out.println(tran.getPays().getIssuer()); //货币发行方
				System.out.println(tran.getPays().getValue()); //金额
			}
		}
		
		System.out.println("---------根据hash获取transaction---------");
		tran = wallet.getTransaction("9937AA23F6B4B11674A9696F16BE62C947DAFA92D464DDDF26B26B9BE0CA178B"); //参数：hash值
		System.out.println(tran.getType());
		System.out.println(tran.getClient_resource_id());
		System.out.println(tran.getCounterparty());
		System.out.println(tran.getDate());
		System.out.println(tran.getFee());
		System.out.println(tran.getHash());
		System.out.println(tran.getResult());
		if(tran.getAmount() != null){
			System.out.println(tran.getAmount().getCurrency()); //货币单位
			System.out.println(tran.getAmount().getIssuer()); //货币发行方
			System.out.println(tran.getAmount().getValue()); //金额
		}
		if(tran.getGets() != null){
			System.out.println(tran.getGets().getCurrency()); //货币单位
			System.out.println(tran.getGets().getIssuer()); //货币发行方
			System.out.println(tran.getGets().getValue()); //金额
		}
		if(tran.getPays() != null){
			System.out.println(tran.getPays().getCurrency()); //货币单位
			System.out.println(tran.getPays().getIssuer()); //货币发行方
			System.out.println(tran.getPays().getValue()); //金额
		}
		
		System.out.println("---------end---------");
	}

}
