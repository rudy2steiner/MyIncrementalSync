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

import java.util.Map;
import java.util.concurrent.RejectedExecutionException;

import com.generallycloud.baseio.component.AbstractSocketSessionManager.SocketSessionManagerEvent;

/**
 * @author wangkai
 *
 */
public interface SocketSessionManager extends SessionManager {

	public abstract SocketSession getSession(Integer sessionID);

	public abstract void offerSessionMEvent(SocketSessionManagerEvent event);
	
	/**
	 * 推荐使用 {@link SocketSessionManager.offerSessionMEvent}
	 * @return
	 */
	public abstract Map<Integer,SocketSession> getManagedSessions();

	public abstract void putSession(SocketSession session) throws RejectedExecutionException;

	public abstract void removeSession(SocketSession session);

}
