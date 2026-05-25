package Utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviders {

	// DataProvider 1

	@DataProvider(name = "LoginData")
	public String[][] getData() throws IOException {

		String path = ".\\testData\\Opencart_Logindata.xlsx"; // Taking xl file from testData

		ExcelUtility xlutil = new ExcelUtility(path); // Creating an Object for Utility

		int totalrows = xlutil.getRowCount("Sheet1");
		int totalcols = xlutil.getCellCount("Sheet1", 1);

		String logindata[][] = new String[totalrows][totalcols]; // Created for 2 dimensionol array which can store

		for (int i = 1; i <= totalrows; i++) // 1
		{
			for (int j = 0; j < totalcols; j++) // 0 i is row j is col
			{
				logindata[i - 1][j] = xlutil.getCellData("Sheet1", i, j); // 1,0
			}
		}
		return logindata; // returning 2 dimensional array
	}

	// dataProvider2

	// DataProvider3

	// DataProvider4
}
