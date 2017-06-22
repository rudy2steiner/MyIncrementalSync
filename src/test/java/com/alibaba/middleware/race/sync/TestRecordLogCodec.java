package com.alibaba.middleware.race.sync;

import java.io.File;
import java.io.RandomAccessFile;

import com.alibaba.middleware.race.sync.channel.RAFInputStream;
import com.alibaba.middleware.race.sync.channel.ReadChannel;
import com.alibaba.middleware.race.sync.channel.SimpleReadChannel;
import com.alibaba.middleware.race.sync.model.RecordLog;
import com.alibaba.middleware.race.sync.model.Table;

/**
 * @author wangkai
 */
public class TestRecordLogCodec {

	public static void main(String[] args) throws Exception {

		File file = new File(Constants.DATA_HOME+"/1.txt");
//		File file = new File(Constants.TESTER_HOME+"/canal.txt");
//		File file = new File(Constants.TESTER_HOME+"/123.txt");
		
		RandomAccessFile raf = new RandomAccessFile(file,"r");

		RAFInputStream inputStream = new RAFInputStream(raf);

		ReadChannel channel = new SimpleReadChannel(inputStream, 1024 * 1024 * 1);

		ChannelReader2 reader = ChannelReader2.get();

		byte[] cs = "middleware3|student".getBytes();

		int all = 0;
		
		Table table = Table.newOffline();
		
		long old = System.currentTimeMillis();
		
		RecordLog r = new RecordLog();
		r.newColumns(8);
		
		for (; channel.hasBufRemaining();) {
			reader.read(table, channel, cs, r);
			if (r == null) {
//				System.out.println("------------------");
				continue;
			}
			r.reset();
			all++;
//			System.out.println(JSONObject.toJSONString(r));
		}
		
		System.out.println("time:"+(System.currentTimeMillis() - old));
		
		System.out.println(all);
	}
}