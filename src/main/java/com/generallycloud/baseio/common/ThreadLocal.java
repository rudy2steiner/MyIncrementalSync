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
package com.generallycloud.baseio.common;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wangkai
 *
 */
public class ThreadLocal {

	private static Map<Thread, ThreadLocal> threadLocals = new HashMap<>();
	
	public static ThreadLocal get() {
		Thread thread = Thread.currentThread();
		ThreadLocal local = threadLocals.get(thread);
		if (local == null) {
			synchronized (threadLocals) {
				local = threadLocals.get(thread);
				if (local != null) {
					return local;
				}
				local = new ThreadLocal();
				threadLocals.put(thread, local);
				return local;
			}
		}
		return local;
	}
	
	public static ThreadLocal remove(){
		synchronized (threadLocals) {
			return threadLocals.remove(Thread.currentThread());
		}
	}

	private Map<Object, Object> cache = new HashMap<>();

	public void putCache(Object key, Object value) {
		cache.put(key, value);
	}

	public Object getCache(Object key) {
		return cache.get(key);
	}

	public Object removeCache(Object key) {
		return cache.remove(key);
	}

}
