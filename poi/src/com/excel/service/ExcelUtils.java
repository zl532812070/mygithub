package com.excel.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {
	// 默认单元格格式化日期字符串
	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
			"yyyyMMddHHmmss");
	public static String path = "E:\\";

	/**
	 * 问卷结果写入excel
	 */
	public static Map<String, Object> executeExcelFormula(File file,
			int sheetAt, Map<String, Object> questions) {
		if (file == null) {
			return null;
		}
		if (file.getName().endsWith("xlsx")) {
			// 处理ecxel2007
			return executeExcelFormula2007(file, sheetAt, questions);
		} else if (file.getName().endsWith("xls")) {
			// 处理ecxel2003
			return executeExcelFormula2003(file, sheetAt, questions);
		} else {
			return null;
		}
	}

	private static Map<String, Object> executeExcelFormula2003(File file,
			int sheetAt, Map<String, Object> questions) {
		HSSFWorkbook workbook = null;
		HSSFSheet sheet = null;
		try {
			workbook = new HSSFWorkbook(new FileInputStream(file));
			sheet = workbook.getSheetAt(sheetAt);
			if (questions != null && questions.size() > 0) {
				int rowCount = 0;
				for (String key : questions.keySet()) {
					HSSFRow row = sheet.getRow(rowCount);
					if (row == null || checkRowNull2003(row)) {
						Object value = questions.get(key);
						row = sheet.createRow(rowCount);
						HSSFCell cell_0 = row.getCell(0);// 题目数
						if (cell_0 == null) {
							cell_0 = row.createCell(0);
						}
						cell_0.setCellValue(key);
						HSSFCell cell_1 = row.getCell(1);// 答案
						if (cell_1 == null) {
							cell_1 = row.createCell(1);
						}
						if (value instanceof String) {
							cell_1.setCellValue(value.toString());
						} else if (value instanceof Integer) {
							cell_1.setCellValue(Integer.valueOf(value
									.toString()));
						}
					}
					rowCount++;
				}
			}
			Map<String, Object> resultMap = resultFromExcel(workbook);
			workbook.setForceFormulaRecalculation(true);
			String prefixName = file.getName().substring(0,
					file.getName().lastIndexOf("."));
			String postfixName = file.getName().substring(
					file.getName().lastIndexOf("."), file.getName().length());
			StringBuilder targetFileName = new StringBuilder();
			targetFileName.append(prefixName)
					.append(DATE_FORMAT.format(new Date())).append(postfixName);
			File targetFile = new File(path + targetFileName.toString());
			FileOutputStream fos = new FileOutputStream(targetFile);
			workbook.write(fos);
			workbook.close();
			fos.close();
			return resultMap;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static Map<String, Object> executeExcelFormula2007(File file,
			int sheetAt, Map<String, Object> questions) {
		// TODO Auto-generated method stub
		XSSFWorkbook workbook = null;
		XSSFSheet sheet = null;
		try {
			workbook = new XSSFWorkbook(new FileInputStream(file));
			sheet = workbook.getSheetAt(sheetAt);
			if (questions != null && questions.size() > 0) {
				int rowCount = 0;
				for (String key : questions.keySet()) {
					XSSFRow row = sheet.getRow(rowCount);
					if (row == null || checkRowNull2007(row)) {
						Object value = questions.get(key);
						row = sheet.createRow(rowCount);
						XSSFCell cell_0 = row.getCell(0);// 题目数
						if (cell_0 == null) {
							cell_0 = row.createCell(0);
						}
						cell_0.setCellValue(key);
						XSSFCell cell_1 = row.getCell(1);// 答案
						if (cell_1 == null) {
							cell_1 = row.createCell(1);
						}
						if (value instanceof String) {
							cell_1.setCellValue(value.toString());
						} else if (value instanceof Integer) {
							cell_1.setCellValue(Integer.valueOf(value
									.toString()));
						}
					}
					rowCount++;
				}
			}
			workbook.setForceFormulaRecalculation(true);
			Map<String, Object> resultMap = resultFromExcel(workbook);
			String prefixName = file.getName().substring(0,
					file.getName().lastIndexOf("."));
			String postfixName = file.getName().substring(
					file.getName().lastIndexOf("."), file.getName().length());
			StringBuilder targetFileName = new StringBuilder();
			targetFileName.append(prefixName)
					.append(DATE_FORMAT.format(new Date())).append(postfixName);
			File targetFile = new File(path + targetFileName.toString());
			FileOutputStream fos = new FileOutputStream(targetFile);
			workbook.write(fos);
			workbook.close();
			fos.close();
			return resultMap;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取excel中需要在word中显示的结果数据
	 * 
	 */
	public static Map<String, Object> resultFromExcel(Workbook workbookObj) {
		if (workbookObj instanceof XSSFWorkbook) {// 2007
			XSSFWorkbook workbook = (XSSFWorkbook) workbookObj;
			return resultFromExcel2007(workbook);
		} else if (workbookObj instanceof HSSFWorkbook) {// 2003
			HSSFWorkbook workbook = (HSSFWorkbook) workbookObj;
			return resultFromExcel2003(workbook);
		} else {
			return null;
		}
	}

	public static Map<String, Object> resultFromExcel2003(HSSFWorkbook workbook) {
		HSSFSheet sheet = null;
		Object value = null;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		FormulaEvaluator evaluator = workbook.getCreationHelper()
				.createFormulaEvaluator();
		sheet = workbook.getSheetAt(workbook.getNumberOfSheets() - 1);
		for (int i = sheet.getFirstRowNum(); i < sheet
				.getPhysicalNumberOfRows(); i++) {
			HSSFRow row = sheet.getRow(i);
			if (row == null || checkRowNull2003(row)) {
				continue;
			}
			HSSFCell cell_0 = row.getCell(0);
			HSSFCell cell_1 = row.getCell(1);
			value = getCellValue(evaluator, cell_1);
			resultMap.put(cell_0.getStringCellValue(), value);
		}
		return resultMap;
	}

	public static Map<String, Object> resultFromExcel2007(XSSFWorkbook workbook) {
		XSSFSheet sheet = null;
		Object value = null;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		FormulaEvaluator evaluator = workbook.getCreationHelper()
				.createFormulaEvaluator();
		sheet = workbook.getSheetAt(workbook.getNumberOfSheets() - 1);
		for (int i = sheet.getFirstRowNum(); i < sheet
				.getPhysicalNumberOfRows(); i++) {
			XSSFRow row = sheet.getRow(i);
			if (row == null || checkRowNull2007(row)) {
				continue;
			}
			XSSFCell cell_0 = row.getCell(0);
			XSSFCell cell_1 = row.getCell(1);
			value = getCellValue(evaluator, cell_1);
			resultMap.put(cell_0.getStringCellValue(), value);
		}
		return resultMap;
	}

	private static String getCellValue(FormulaEvaluator evaluator, Cell cell) {
		if (cell == null) {
			return "isNull";
		}
		System.out.println("rowIdx:" + cell.getRowIndex() + ",colIdx:"
				+ cell.getColumnIndex());
		String cellValue = null;
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			cellValue = cell.getStringCellValue();
			break;

		case Cell.CELL_TYPE_NUMERIC:
			cellValue = String.valueOf(cell.getNumericCellValue());
			break;

		case Cell.CELL_TYPE_FORMULA:
			cellValue = getCellValue(evaluator.evaluate(cell));
			break;
		default:
			break;
		}

		return cellValue;
	}

	private static String getCellValue(CellValue cell) {
		String cellValue = null;
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			cellValue = cell.getStringValue();
			break;

		case Cell.CELL_TYPE_NUMERIC:
			cellValue = String.valueOf(cell.getNumberValue());
			break;
		case Cell.CELL_TYPE_FORMULA:
			break;
		default:
			break;
		}

		return cellValue;
	}

	/**
	 * 判断行为空(xls)
	 * 
	 * @param row
	 * @return
	 */
	private static boolean checkRowNull2003(HSSFRow row) {
		for (int i = row.getFirstCellNum(); i < row.getPhysicalNumberOfCells(); i++) {
			HSSFCell cell = row.getCell(i);
			if (cell != null && cell.getCellType() != HSSFCell.CELL_TYPE_BLANK) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断行为空(xlsx)
	 * 
	 * @param row
	 * @return
	 */
	private static boolean checkRowNull2007(XSSFRow row) {
		for (int i = row.getFirstCellNum(); i < row.getPhysicalNumberOfCells(); i++) {
			XSSFCell cell = row.getCell(i);
			if (cell != null && cell.getCellType() != XSSFCell.CELL_TYPE_BLANK) {
				return false;
			}
		}
		return true;
	}
}