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

import com.jingtum.exception.APIConnectionException;
import com.jingtum.exception.APIException;
import com.jingtum.exception.AuthenticationException;
import com.jingtum.exception.ChannelException;
import com.jingtum.exception.FailedException;
import com.jingtum.exception.InvalidParameterException;
import com.jingtum.exception.InvalidRequestException;

import com.jingtum.model.FinGate;

/**
 * Utility
 *
 */
public class UtilityExample {
	public static void main(String[] args) throws AuthenticationException, InvalidRequestException, APIConnectionException, APIException, ChannelException, FailedException, InvalidParameterException {

		
		System.out.println("---------获取 server连接信息---------");
		boolean isConnected = FinGate.getInstance().getStatus();  //为 true 代表 API服务器可以连接;反之，连接失败
		System.out.println(isConnected);   
		
		System.out.println("---------获取 uuid---------"); 
		String uuid = FinGate.getInstance().getNextUUID(); //生成uuid，用于提交支付是的资源号
		System.out.println(uuid);
		
		System.out.println("---------end---------");
	}
	
}
