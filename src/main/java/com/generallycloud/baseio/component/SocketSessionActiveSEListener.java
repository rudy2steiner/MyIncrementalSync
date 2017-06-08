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

import com.generallycloud.baseio.common.CloseUtil;
import com.generallycloud.baseio.common.Logger;
import com.generallycloud.baseio.common.LoggerFactory;
import com.generallycloud.baseio.protocol.ReadFuture;

public class SocketSessionActiveSEListener implements SocketSessionIdleEventListener {

	private Logger		logger	= LoggerFactory.getLogger(SocketSessionActiveSEListener.class);

	@Override
	public void sessionIdled(SocketSession session, long lastIdleTime, long currentTime) {

		if (session.isClosed()) {
			logger.info("closed session");
			return;
		}
		
		if (session.getLastAccessTime() < lastIdleTime) {

			logger.info("Did not detect heartbeat messages in heartbeat cycle, prepare to disconnect {}",session);
			CloseUtil.close(session); 

		} else {

			SocketChannelContext context = session.getContext();

			BeatFutureFactory factory = context.getBeatFutureFactory();

			if (factory == null) {

				RuntimeException e = new RuntimeException("none factory of BeatFuture");

				CloseUtil.close(session);

				logger.error(e.getMessage(), e);

				return;
			}

			ReadFuture future = factory.createPINGPacket(session);
			
			if (future == null) {
				// 该session无需心跳,比如HTTP协议
				return;
			}

			session.flush(future);
		}
	}
}
