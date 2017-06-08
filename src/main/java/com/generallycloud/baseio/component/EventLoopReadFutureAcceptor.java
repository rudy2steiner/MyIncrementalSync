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

import com.generallycloud.baseio.component.IoEventHandle.IoEventState;
import com.generallycloud.baseio.concurrent.ExecutorEventLoop;
import com.generallycloud.baseio.protocol.ChannelReadFuture;

public class EventLoopReadFutureAcceptor extends AbstractReadFutureAcceptor{

	@Override
	protected void accept(final IoEventHandle eventHandle,final SocketSession session,final ChannelReadFuture future) {
		
		ExecutorEventLoop eventLoop = session.getExecutorEventLoop();

		eventLoop.dispatch(new Runnable() {

			@Override
			public void run() {

				try {

					eventHandle.accept(session, future);

				} catch (Exception e) {

					eventHandle.exceptionCaught(session, future, e, IoEventState.HANDLE);
				}
			}
		});
	}
	
	
}
