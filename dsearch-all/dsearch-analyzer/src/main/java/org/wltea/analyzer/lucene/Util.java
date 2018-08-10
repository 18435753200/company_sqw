package org.wltea.analyzer.lucene;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Util {
	/*
	 * 切分多条文件路径，以逗号分隔
	 */
	public static List<String> SplitFileNames(String fileNames) {
		if (fileNames == null)
			return Collections.<String> emptyList();

		List<String> result = new ArrayList<String>();
		/*for (String file : fileNames.split("(?<!\\\\),")) {
			result.add(file.replaceAll("\\\\(?=,)", ""));
		}*/
		
		File dir =new File(fileNames);
		listDicFile(dir, result);
		return result;
	}
	
	private static void listDicFile(File dir, List<String> result){
		if(dir.exists()){
			File[] files = dir.listFiles();
			for(File file : files){
				if(file.isDirectory()){
					listDicFile(file, result);
				} else {
					result.add(file.getAbsolutePath());
				}
			}
		}
	}
}
