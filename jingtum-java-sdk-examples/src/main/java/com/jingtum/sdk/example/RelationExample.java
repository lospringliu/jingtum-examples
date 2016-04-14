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
import com.jingtum.model.RequestResult;
import com.jingtum.model.Relation;
import com.jingtum.model.Relation.RelationType;
import com.jingtum.model.RelationAmount;
import com.jingtum.model.RelationCollection;
import com.jingtum.model.Wallet;

/**
 * Relation
 *
 */
public class RelationExample {
	public static void main(String[] args) throws AuthenticationException, InvalidRequestException, APIConnectionException, APIException, ChannelException, NoSuchAlgorithmException, InvalidParameterException, FailedException {

		Wallet wallet = new Wallet("js4UaG1pjyCEi9f867QHJbWwD3eo6C5xsa","snqFcHzRe22JTM8j7iZVpQYzxEEbW");; //根据井通地址生成钱包
		System.out.println("---------获取relationship---------");
		RelationCollection rc = wallet.getRelationList(RelationType.authorize,null,null,null);//参数均为可选参数
		//RelationCollection rc = wallet.getRelations(); //或者不加过滤条件
		Iterator<Relation> it = rc.getData().iterator();
		Integer i = 0;
		Relation re;
		while(it.hasNext())
		{
			i++;
			re = (Relation)it.next();
			System.out.println("---------Relation #" + i);
			System.out.println(re.getAccount()); //账号
			System.out.println(re.getType()); //关系类型
			System.out.println(re.getCounterparty()); //关系对家
			System.out.println(re.getAmount().getCurrency()); //货币单位
			System.out.println(re.getAmount().getIssuer()); //货币发行方
			System.out.println(re.getAmount().getLimit()); //金额
		}	
		
		
		System.out.println("---------增加relationship---------");
		RelationAmount amount = new RelationAmount();
		amount.setIssuer("janxMdrWE2SUzTqRUtfycH4UGewMMeHa9f"); //Currency issuer
		amount.setCurrency(Jingtum.getCurrencyCNY());
		amount.setLimit(200); //使用limit，而不是value
		RequestResult pr = wallet.addRelation(RelationType.authorize, "jsTo3VKjQrgDKdm5iQdMjc9YqDfrWJn1hw", amount, true);
		System.out.println(pr.getFee());
		System.out.println(pr.getHash());
		System.out.println(pr.getSuccess());
		System.out.println(pr.getState());
		
		System.out.println("---------移除relationship---------");
		RelationAmount currency = new RelationAmount();
		currency.setIssuer("janxMdrWE2SUzTqRUtfycH4UGewMMeHa9f"); //Currency issuer
		currency.setCurrency("CNY");
		RequestResult pr2 = wallet.removeRelation(RelationType.authorize, "jsTo3VKjQrgDKdm5iQdMjc9YqDfrWJn1hw", currency, true);
		System.out.println(pr2.getFee());
		System.out.println(pr2.getHash());
		System.out.println(pr2.getSuccess());
		System.out.println(pr2.getState());
		

		
		System.out.println("---------获取counterparty relationship---------");
		Wallet wallet2 = new Wallet("jsTo3VKjQrgDKdm5iQdMjc9YqDfrWJn1hw","sseBFE4ZydZCaVEYctv6UedGnbnwn");
		RelationCollection rc2 = wallet2.getCoRelationList(Relation.RelationType.all,null,null,null); //关系类型，地址，关系数，marker
		//RelationCollection rc2 = wallet2.getCounterpartyRelations(); 不带参数获取所有
		Iterator<Relation> it2 = rc2.getData().iterator();
		Integer j = 0;
		Relation re2;
		while(it2.hasNext())
		{
			j++;
			re2 = (Relation)it2.next();
			System.out.println("---------Relation #" + j);
			System.out.println(re2.getAccount()); //关系主动方的井通地址
			System.out.println(re2.getType()); //关系类型
			System.out.println(re2.getCounterparty()); //关系被动方的井通地址
			System.out.println(re2.getAmount().getCurrency()); //货币单位
			System.out.println(re2.getAmount().getIssuer()); //货币发行方
			System.out.println(re2.getAmount().getLimit()); //金额
		}
		
		System.out.println("---------end---------");
	}

}
