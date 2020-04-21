package com.w2a.APITestingFramework.utilities;

import java.lang.reflect.Method;
import java.util.Hashtable;

import org.testng.annotations.DataProvider;

import com.w2a.APITestingFramework.setUp.BaseTest;

// when we have different sheets in the excel file
/*public class DataUtil extends BaseTest {

	@DataProvider(name = "data")
	public Object[][] getData(Method m) {

		System.out.println("SheetName " + m.getName());
		String sheetName = m.getName();
		int rows = excel.getRowCount(sheetName);
		int cols = excel.getColumnCount(sheetName);

		System.out.println("Total rows are : " + rows + ", Total cols are : " + cols);

		Object[][] data = new Object[rows - 1][cols];

		System.out.println(excel.getCellData(sheetName, 0, 3));

		for (int rowNum = rows - 1; rowNum <= rows; rowNum++) {
			for (int colNum = 0; colNum < cols; colNum++) {
				data[rowNum - (rows - 1)][colNum] = excel.getCellData(sheetName, colNum, rowNum);
			}
		}

		return data;
	}
}*/

// when all the data in one sheet, using HashMap, we will read the data
public class DataUtil extends BaseTest {

	@DataProvider(name = "data")
	public Object[][] getData(Method m) {
		int rows = excel.getRowCount(config.getProperty("testDataSheetName"));
		System.out.println("Total rows are: " + rows);

		String testName = m.getName();
		System.out.println("Test name is: " + testName);

		// find the test case start now
		int testCaseRowNum = 1;
		for (testCaseRowNum = 1; testCaseRowNum <= rows; testCaseRowNum++) {
			String testCaseName = excel.getCellData(config.getProperty("testDataSheetName"), 0, testCaseRowNum);

			if (testCaseName.equalsIgnoreCase(testName))
				break;
		}

		System.out.println("Test case starts from row num: " + testCaseRowNum);

		// checking total rows in test case
		int dataStartRowNum = testCaseRowNum + 2;

		int testRows = 0;
		while (!excel.getCellData(config.getProperty("testDataSheetName"), 0, dataStartRowNum + testRows).equals("")) {
			testRows++;
		}

		System.out.println("Total rows of data are: " + testRows);

		// checking total columns in test case

		int colStartColNum = testCaseRowNum + 1;
		int testCols = 0;
		while (!excel.getCellData(config.getProperty("testDataSheetName"), testCols, colStartColNum).equals("")) {
			testCols++;
		}

		System.out.println("Total cols are: " + testCols);

		// Printing data

		Object[][] data = new Object[testRows][1];

		int i = 0;
		for (int rNum = dataStartRowNum; rNum < (dataStartRowNum + testRows); rNum++) {
			Hashtable<String, String> table = new Hashtable<String, String>();
			for (int cNum = 0; cNum < testCols; cNum++) {
				String testData = excel.getCellData(config.getProperty("testDataSheetName"), cNum, rNum);
				String colName = excel.getCellData(config.getProperty("testDataSheetName"), cNum, colStartColNum);

				table.put(colName, testData);
			}
			data[i][0] = table;
			i++;
		}
		return data;
	}

}
