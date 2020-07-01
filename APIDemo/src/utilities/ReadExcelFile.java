package utilities;
import static io.restassured.RestAssured.given;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
/**
 * Created by arjun 
 */

public class ReadExcelFile {
	
	private static final String FILE_PATH = "C:\\Users\\home\\Downloads\\Projects\\APIDemo\\Book2.xlsx";
	
	public static void main(String args[]) throws FileNotFoundException {
		
		List studentList = getAPIDetails();

		System.out.println(studentList);
	}

	private static List getAPIDetails() throws FileNotFoundException {
		FileInputStream file=new FileInputStream(new File("C:\\Users\\home\\git\\EclipseProjects\\APIDemo\\Files\\testdata.json"));
		List apiList = new ArrayList();
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(FILE_PATH);

			// Using XSSF for xlsx format, for xls use HSSF
			Workbook workbook = new XSSFWorkbook(fis);
			int numberOfSheets = workbook.getNumberOfSheets();

			// looping over each workbook sheet
			for (int i = 0; i < numberOfSheets; i++) {
				Sheet sheet = workbook.getSheetAt(i);
				Iterator rowIterator = sheet.iterator();
				int temp=1;
				int lastRowNumber=sheet.getLastRowNum();
				// iterating over each row
				while (rowIterator.hasNext()) {
					temp=temp+1;
					TestData data = new TestData();
					Row row = (Row) rowIterator.next();
					Iterator cellIterator = row.cellIterator();
					if(row.getRowNum()==0){
					       continue; //just skip the rows if row number is 0 or 1
					      }						
					// Iterating over each cell (column wise) in a particular row.
					while (cellIterator.hasNext()) {
						Cell cell = (Cell) cellIterator.next();
						// The Cell Containing String will is name.
						if (CellType.STRING == cell.getCellType()) {

							// Cell with index 1 contains baseURI in Maths
							if (cell.getColumnIndex() == 0) {
								data.setBaseURI(cell.getStringCellValue());	
							}
							// Cell with index 2 contains Resource in Science
							else if (cell.getColumnIndex() == 1) {
								data.setResource(cell.getStringCellValue());	
							}
							// Cell with index 3 contains request Method in English
							else if (cell.getColumnIndex() == 2) {
								data.setRequestMethod(cell.getStringCellValue());	
							}
							
							// The Cell Containing numeric value will contain marks
						
						}
					}
					// end iterating a row, add all the elements of a row in list
					apiList.add(data);
					
					RestAssured.baseURI=data.getBaseURI();
					SessionFilter sf=new SessionFilter();
					String response=given().log().all().header("Content-Type","application/json").body(IOUtils.toString(file,"UTF-8")).filter(sf)
					.when().post("/rest/auth/1/session")
					
					.then().log().all().assertThat().statusCode(200).extract().response().asString();
					
					//there are two ways doing of using session
					
					/*//1. we can do using below format. we can set the the sessionValue in further API's
					JsonPath js=new JsonPath(response);
					String sessionValue=js.getString("session.value");*/
					
					//2. create reference for Session filter class use that in "given()" method
					
					//update comment
					if(data.getRequestMethod().equalsIgnoreCase("post")) {
					given().pathParam("key", "10300").log().all().header("Content-Type","application/json").body(PayLoad.body()).filter(sf)
					.when().post(data.getResource())
					.then().log().all().assertThat().statusCode(201).extract().response().asString();
					}
					
					if(data.getRequestMethod().equalsIgnoreCase("get")) {
						String issueDetails= given().log().all().filter(sf).pathParam("key", "10300").queryParam("fields", "comment")
								.when().get(data.getResource())
								.then().log().all().assertThat().statusCode(200).extract().response().asString();
								
								System.out.println(issueDetails);
						}
					
				}
			}

			fis.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return apiList;
	}

}