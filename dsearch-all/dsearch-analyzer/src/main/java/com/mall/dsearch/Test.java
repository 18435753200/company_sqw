package com.mall.dsearch;

import java.io.File;

public class Test {
	public static void main(String[] args) {
		String resource = "D:\\soft\\solr\\solr\\solrslave1\\solr\\dic\\ext.dic";
		File f0 = new File(resource), f = f0;
		System.err.println(f0.getAbsolutePath());
		System.err.println(f.getAbsolutePath());
		if (!f.isAbsolute()) {
			System.err.println("f.isAbsolute()=false");
		} else {
			System.err.println("f.isAbsolute()=true");
		}
		boolean found = f.isFile() && f.canRead();
		if (!found) { // no success with $CWD/$configDir/$resource
			f = f0.getAbsoluteFile();
			found = f.isFile() && f.canRead();
		}
		System.err.println("found:"+found);
	}
}
