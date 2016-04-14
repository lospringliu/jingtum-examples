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
import com.jingtum.model.RequestResult;
import com.jingtum.model.TrustLine;
import com.jingtum.model.TrustLineCollection;

/**
 * TrustLines
 *
 */
public class TrustLinesxExample {
	public static void main(String[] args) throws AuthenticationException, InvalidRequestException, APIConnectionException, APIException, ChannelException, NoSuchAlgorithmException, InvalidParameterException, FailedException {

		System.out.println("---------获取信任---------");
		Wallet wallet1 = new Wallet("js4UaG1pjyCEi9f867QHJbWwD3eo6C5xsa","snqFcHzRe22JTM8j7iZVpQYzxEEbW"); //根据地址和密钥生成钱包，如只为获取全部授信，密钥可为空
		TrustLineCollection tlc = wallet1.getTrustLineList("CNY",null); //信任通，信任发行者，信任额度
		//tlc = wallet1.getTrustLine(); //不带参数
		TrustLine tl1;
		
		Iterator<TrustLine> it1 = tlc.getData().iterator();
		Integer i = 0;
		while(it1.hasNext()){		
			i++;
			tl1 = (TrustLine)it1.next();
			System.out.println("---------Trust Line #" + i);
			System.out.println(tl1.getAccount()); //当前账号地址
			System.out.println(tl1.getCounterparty()); //授信方
			System.out.println(tl1.getCurrency()); //货币单位
			System.out.println(tl1.getLimit()); //授信额度
	
		}
		
		System.out.println("---------增加信任---------");
		Wallet wallet2 = new Wallet("js4UaG1pjyCEi9f867QHJbWwD3eo6C5xsa","snqFcHzRe22JTM8j7iZVpQYzxEEbW"); //增加授信密钥为必需值，否则提交会失败
		TrustLine trustline = new TrustLine(); //构建信任实例
		trustline.setCounterparty("jMhLAPaNFo288PNo5HMC37kg6ULjJg8vPf");
		trustline.setCurrency(Jingtum.getCurrencyUSD());
		trustline.setLimit(200);
		RequestResult pr = wallet2.addTrustLine(trustline, true);
		System.out.println(pr.getSuccess());
		System.out.println(pr.getHash());
		System.out.println(pr.getState());
		
		System.out.println("---------删除信任---------");
		Wallet wallet3 = new Wallet("js4UaG1pjyCEi9f867QHJbWwD3eo6C5xsa","snqFcHzRe22JTM8j7iZVpQYzxEEbW"); 
		TrustLine trustline2 = new TrustLine();
		trustline2.setCounterparty("jMhLAPaNFo288PNo5HMC37kg6ULjJg8vPf");
		trustline2.setCurrency(Jingtum.getCurrencyUSD());
		RequestResult pr2 = wallet3.removeTrustLine(trustline2, true);
		System.out.println(pr2.getSuccess());
		System.out.println(pr2.getHash());
		System.out.println(pr2.getState());
		
		System.out.println("---------end---------");
	}
}
