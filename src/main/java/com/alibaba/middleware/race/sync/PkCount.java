package com.alibaba.middleware.race.sync;

import com.alibaba.middleware.race.sync.model.RecordLog;

public class PkCount {

//	private Logger			logger	= LoggerUtil.get();

	private static PkCount	pkCount	= new PkCount();

	public static PkCount get() {
		return pkCount;
	}

	private int all,in2in, in2out, out2out, out2in;

	public void count(RecordLog r, int startId, int endId) {
		all++;
		if (r.isPkUpdate()) {
			if (inRange(r.getBeforePk(), startId, endId)) {
				if (inRange(r.getPk(), startId, endId)) {
					in2in++;
				} else {
					in2out++;
				}
			} else {
				if (inRange(r.getPk(), startId, endId)) {
					out2in++;
				} else {
					out2out++;
				}
			}
		}
	}

	private boolean inRange(int pk, int startId, int endId) {
		return pk > startId && pk < endId;
	}

	public int getIn2in() {
		return in2in;
	}

	public int getIn2out() {
		return in2out;
	}

	public int getOut2out() {
		return out2out;
	}

	public int getOut2in() {
		return out2in;
	}

	public void setIn2in(int in2in) {
		this.in2in = in2in;
	}

	public void setIn2out(int in2out) {
		this.in2out = in2out;
	}

	public void setOut2out(int out2out) {
		this.out2out = out2out;
	}

	public void setOut2in(int out2in) {
		this.out2in = out2in;
	}
	
	public int getAll() {
		return all;
	}

	public void setAll(int all) {
		this.all = all;
	}

	public void printResult() {
//		logger.info("all:{}, in2in:{}, in2out:{}, out2out:{}, out2in:{}",all, in2in, in2out, out2out, out2in);
	}

}