/*
 * Copyright 2015-2017 GenerallyCloud.com
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */ 
package com.generallycloud.baseio.component;

import com.generallycloud.baseio.common.Logger;
import com.generallycloud.baseio.common.LoggerFactory;

public class LoggerSocketSEListener implements SocketSessionEventListener{

	private Logger logger = LoggerFactory.getLogger(LoggerSocketSEListener.class);
	
	@Override
	public void sessionOpened(SocketSession session) {
		logger.info("session opened:{}",session);
	}

	@Override
	public void sessionClosed(SocketSession session) {
		logger.info("session closed:{}",session);
	}
	
}
