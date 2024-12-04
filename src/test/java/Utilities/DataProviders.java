package Utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviders {
	
	// DataProvider 1
	@DataProvider(name = "LoginData")
	public String[][] getData() throws IOException {
		
		String path = "./testData/LoginData.xlsx";// identifying source excel file
		ExeclUtility xlutility = new ExeclUtility(path);// creating object of ExcelUtility class
		int totalrows = xlutility.getRowCount("sheet1");
		int totalcols = xlutility.getCellCount("sheet1", 1);
		
        // Create two dimension array which can store data
		String loginData[][] = new String[totalrows][totalcols];// 
		for (int i = 1; i <= totalrows; i++) {
			for (int j = 0; j < totalcols; j++) {
				loginData[i-1 ][j] = xlutility.getCellData("Sheet1", i, j);
			}
		}
		return loginData;
	}
	
	// DataProvider 2

	// DataProvider 3
	
	// DataProvider 4

}
