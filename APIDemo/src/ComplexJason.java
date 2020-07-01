import files.PayLoad;
import io.restassured.path.json.JsonPath;

public class ComplexJason {
	
	
	public static void main(String[] args) {
		
		JsonPath js=new JsonPath(PayLoad.CoursePrice());
		
		//print no of courses returned by array
		//*note" size only can be applied on array
		int coursesSize=js.getInt("courses.size()");
		System.out.println(coursesSize);
		
		// print purchase amount
		int purchaseamount=js.getInt("dashboard.purchaseAmount");
		System.out.println(purchaseamount);
		
		//print title of first course
		
		String title=js.get("courses[0].title");
		System.out.println(title);
		
		//print all the courses and prices of courses
		for(int i=0;i<coursesSize;i++) {
			
			String courseTitles=js.get("courses["+i+"].title");
			System.out.println(courseTitles);
			int coursePrices=js.getInt("courses["+i+"].price");
			System.out.println(coursePrices);
		}
		//print price of specific course
		String courseName="RPA";
		for(int i=0;i<coursesSize;i++) {
			
			String titleOfCourse=js.get("courses["+i+"].title");
			if(titleOfCourse.contains(courseName)) {				
				
				System.out.println(courseName+"copies count "+js.get("courses["+i+"].copies").toString());
				break;				
			}
		}
		
		
	}
	
	
	
	
	
	
}
