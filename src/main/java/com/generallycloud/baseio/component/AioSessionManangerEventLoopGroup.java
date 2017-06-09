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

import com.generallycloud.baseio.concurrent.AbstractExecutorEventLoopGroup;
import com.generallycloud.baseio.concurrent.ExecutorEventLoop;

public class AioSessionManangerEventLoopGroup extends AbstractExecutorEventLoopGroup {

	private AioSocketSessionManager sessionManager;

	public AioSessionManangerEventLoopGroup(String eventLoopName, int eventQueueSize,
			int eventLoopSize, AioSocketSessionManager sessionManager) {
		super(eventLoopName, eventQueueSize, eventLoopSize);
		this.sessionManager = sessionManager;
	}

	@Override
	protected ExecutorEventLoop newEventLoop(int coreIndex, int eventQueueSize) {
		
		ExecutorEventLoop eventLoop = new AioSessionManagerEventLoop(this, eventQueueSize, sessionManager);
		
		sessionManager.initSocketSelectorEventLoop(eventLoop);
		
		return eventLoop;
	}

}
