package com.excel.main;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

import com.excel.service.ExcelUtils;

public class App {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File file = new File("E:/志愿填报测试计分表.xlsx");
		Map<String, Object> questions = new LinkedHashMap<String, Object>();
		questions.put("question1", "D");
		questions.put("question2", "B");
		questions.put("question3", "A");
		questions.put("question4", "E");
		questions.put("question5", "C");
		questions.put("question6", "C");
		questions.put("question7", "C");
		questions.put("question8", "A");
		questions.put("question9", "E");
		questions.put("question10", "A");
		questions.put("question11", "B");
		questions.put("question12", "A");
		questions.put("question13", "D");
		questions.put("question14", "D");
		questions.put("question15", "C");
		questions.put("question16", "A");
		questions.put("question17", "D");
		questions.put("question18", "D");
		questions.put("question19", "A");
		questions.put("question20", "B");
		questions.put("question21", "D");
		questions.put("question22", "A");
		questions.put("question23", "A");
		questions.put("question24", "C");
		questions.put("question25", "E");
		questions.put("question26", "B");
		questions.put("question27", "B");
		questions.put("question28", "B");
		questions.put("question29", "C");
		questions.put("question30", "D");
		questions.put("question31", "B");
		questions.put("question32", "B");
		Map<String, Object> resultMap = ExcelUtils.executeExcelFormula(file, 0,
				questions);
		for (String key : resultMap.keySet()) {
			System.out.println("key2:" + key);
			System.out.println("value2:" + resultMap.get(key));
		}
	}

}
