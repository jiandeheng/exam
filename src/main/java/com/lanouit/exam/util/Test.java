package com.lanouit.exam.util;

import java.util.HashMap;
import java.util.Map;

public class Test {
	
	public static void main(String[] args) throws Exception {
	
		Map<String, Map<String, String>> bigMap = new HashMap<String, Map<String,String>>();
		Map<String, String> smallMap = new HashMap<String, String>();
		bigMap.put("index", smallMap);
		smallMap.put("A", "A");
		smallMap.put("B", "B");
		smallMap.put("C", "C");
		System.out.println(bigMap.get("index").size());
	}
	
}
