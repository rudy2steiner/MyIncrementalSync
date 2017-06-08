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

/**
 * 
 * 数组的帮助类
 * 
 */
public class ArrayUtil {

	/**
	 * 把两个Object[] 转成一个
	 * 
	 * @param param1
	 * @param param2
	 * @return Object[]
	 */
	public static Object[] groupArray(Object[] param1, Object[] param2) {
		Object[] param = new Object[param1.length + param2.length];
		System.arraycopy(param1, 0, param, 0, param1.length);
		System.arraycopy(param2, 0, param, param1.length, param2.length);
		return param;
	}
}
