import org.testng.Assert;
import org.testng.annotations.Test;

import files.PayLoad;
import io.restassured.path.json.JsonPath;

public class SumValidation {

	@Test
	public void sumOfCourses() {
		int sum=0;
		JsonPath js = new JsonPath(PayLoad.CoursePrice());
		// print no of courses returned by array
		// *note" size only can be applied on array
		int coursesSize = js.getInt("courses.size()");
		System.out.println(coursesSize);
		for(int i=0;i<coursesSize;i++) {
			
			int price=js.getInt("courses["+i+"].price");
			int copies=js.getInt("courses["+i+"].copies");
			int totalPrice=price*copies;
			System.out.println(totalPrice);
			sum=sum+totalPrice;			
		}
		System.out.println("total price of all the courses and its copies is "+sum);
		
		int purchaseAmount=js.getInt("dashboard.purchaseAmount");
		Assert.assertEquals(sum, purchaseAmount,"values are matching");
	}

}
