package api.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import api.endpoints.UserEndPoints;
import api.payload_POJO.User;
import api.utilities.DataProviders;
import io.restassured.response.Response;

public class DataDrivenTest {

	@Test(priority = 1, dataProvider = "Data", dataProviderClass = DataProviders.class)
	public void testCreateUser(String userID, String userName, String fname, String lname, String email, String pass,
			String phone) throws JsonProcessingException {

		// System.out.println(userName);

		User payload = new User();

		payload.setId(Integer.parseInt(userID));
		payload.setUsername(userName);
		payload.setFirstName(fname);
		payload.setLastName(lname);
		payload.setEmail(email);
		payload.setPassword(pass);
		payload.setPhone(phone);

//		ObjectMapper mapper = new ObjectMapper();
//		String data = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(payload);
//		System.out.println(data);

		Response res = UserEndPoints.createUser(payload);
		res.then().log().all();
		Assert.assertEquals(res.getStatusCode(), 200);

	}

	@Test(priority = 2, dataProvider = "UserNames", dataProviderClass = DataProviders.class)
	public void testReadUserByName(String userName) {

		// System.out.println(userName);

		Response res = UserEndPoints.readUser(userName);
		res.then().log().all();
		Assert.assertEquals(res.getStatusCode(), 200);

	}

	@Test(priority = 3, dataProvider = "UserNames", dataProviderClass = DataProviders.class)
	public void testDeleteUserByName(String userName) {

		// System.out.println(userName);

		Response res = UserEndPoints.deleteUser(userName);
		res.then().log().all();
		Assert.assertEquals(res.getStatusCode(), 200);

	}

}
