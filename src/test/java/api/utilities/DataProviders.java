package api.utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviders {

	@DataProvider(name = "Data")
	public String[][] getAllData() throws IOException {

		String path = System.getProperty("user.dir") + "\\testdata\\UserData.xlsx";

		ExcelUtility excel = new ExcelUtility(path);

		int row = excel.getNumbersOfRows("data1");
		int column = excel.getNumbersOfColumns("data1", 1);

		String userData[][] = new String[row][column];

		for (int i = 1; i <= row; i++) {

			for (int j = 0; j < column; j++) {

				userData[i - 1][j] = excel.getCellData("data1", i, j);

			}

		}

		return userData;

	}

	@DataProvider(name = "UserNames")
	public String[] getUserNames() throws IOException {

		String path = System.getProperty("user.dir") + "\\testdata\\UserData.xlsx";

		ExcelUtility excel = new ExcelUtility(path);

		int row = excel.getNumbersOfRows("data1");

		String userName[] = new String[row];

		for (int i = 1; i <= row; i++) {

			userName[i - 1] = excel.getCellData("data1", i, 1);

		}

		return userName;
	}

}
