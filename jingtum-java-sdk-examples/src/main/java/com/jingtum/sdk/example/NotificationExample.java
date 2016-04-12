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
 *  */
package com.jingtum.sdk.example;

import java.security.NoSuchAlgorithmException;

import com.jingtum.exception.APIConnectionException;
import com.jingtum.exception.APIException;
import com.jingtum.exception.AuthenticationException;
import com.jingtum.exception.ChannelException;
import com.jingtum.exception.FailedException;
import com.jingtum.exception.InvalidParameterException;
import com.jingtum.exception.InvalidRequestException;
import com.jingtum.model.Notification;
import com.jingtum.model.Wallet;

/**
 * Notification
 *
 */
public class NotificationExample {
	public static void main(String[] args) throws AuthenticationException, InvalidRequestException, APIConnectionException, APIException, ChannelException, NoSuchAlgorithmException, InvalidParameterException, FailedException {
		Wallet wallet = new Wallet("js4UaG1pjyCEi9f867QHJbWwD3eo6C5xsa","snqFcHzRe22JTM8j7iZVpQYzxEEbW"); //根据井通地址密钥生成钱包
		Notification noti = wallet.getNotification("9937AA23F6B4B11674A9696F16BE62C947DAFA92D464DDDF26B26B9BE0CA178B"); //根据hash值获取notification实例
		System.out.println("---------获取 Notification---------");
		
		System.out.println(noti.getAccount()); //通知相关账号
		System.out.println(noti.getType()); //通知类型
		System.out.println(noti.getDirection()); //交易的方向，incoming 或者 outgoing
		System.out.println(noti.getState()); //交易的状态
		System.out.println(noti.getResult()); //交易结果
		System.out.println(noti.getHash()); //交易hash值
		System.out.println(noti.getDate()); //交易时间，UNIXTIME
		System.out.println(noti.getPreviousHash()); //前一个交易的URL
		System.out.println(noti.getNextHash()); //后一个交易的URL
		
		System.out.println("---------end---------");
	}
}
