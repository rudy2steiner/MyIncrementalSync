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
package com.alibaba.middleware.race.sync.io;

import java.io.IOException;

import com.generallycloud.baseio.buffer.ByteBuf;
import com.generallycloud.baseio.common.ReleaseUtil;
import com.generallycloud.baseio.component.Session;
import com.generallycloud.baseio.component.SocketChannelContext;
import com.generallycloud.baseio.component.SocketSession;
import com.generallycloud.baseio.protocol.AbstractChannelReadFuture;
import com.generallycloud.baseio.protocol.ProtocolException;

public class FixedLengthReadFutureImpl extends AbstractChannelReadFuture implements FixedLengthReadFuture {

	private ByteBuf	buf;

	private boolean	header_complete;

	private boolean	body_complete;

	private int		limit;

	public FixedLengthReadFutureImpl(SocketSession session, ByteBuf buf,int limit) {
		super(session.getContext());
		this.buf = buf;
		this.limit = limit;
	}

	public FixedLengthReadFutureImpl(SocketChannelContext context) {
		super(context);
	}

	private void doHeaderComplete(Session session, ByteBuf buf) {

		int length = buf.getInt();

		if (length < 1) {
			body_complete = true;
			if (length == FixedLengthProtocolDecoder.PROTOCOL_PING) {
				setPING();
			} else if (length == FixedLengthProtocolDecoder.PROTOCOL_PONG) {
				setPONG();
			}else{
				throw new ProtocolException("illegal length:" + length);
			}
			return;
		} 
		
		buf.reallocate(length, limit);
	}
	
	@Override
	public boolean read(SocketSession session, ByteBuf buffer) throws IOException {

		ByteBuf buf = this.buf;

		if (!header_complete) {

			buf.read(buffer);

			if (buf.hasRemaining()) {
				return false;
			}
			
			header_complete = true;
			
			doHeaderComplete(session, buf.flip());
		}

		if (!body_complete) {

			buf.read(buffer);

			if (buf.hasRemaining()) {
				return false;
			}
			
			body_complete = true;
			
			buf.flip();
		}

		return true;
	}

	@Override
	public void release() {
		ReleaseUtil.release(buf);
	}
	
	@Override
	public ByteBuf getBuf() {
		return buf;
	}
	
	@Override
	public boolean isReleased() {
		return buf.isReleased();
	}
	
}
