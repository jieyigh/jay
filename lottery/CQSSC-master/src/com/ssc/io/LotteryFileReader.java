package com.ssc.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.ssc.bean.DataBean;
import com.ssc.tools.DataLineTools;

public class LotteryFileReader {

	public List<DataBean> readfile(String filepath) throws Exception {
		List<DataBean> dataList = new ArrayList<DataBean>();
		File file = new File(filepath);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = reader.readLine()) != null) {
				String[] dataArray = DataLineTools.splitDataLine(line);
				DataBean databean = new DataBean(dataArray[0], dataArray[1], dataArray[2]);
				dataList.add(databean);
			}
		} catch (Exception e) {
			throw new Exception("读取文件异常");
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		return dataList;
	}
}
