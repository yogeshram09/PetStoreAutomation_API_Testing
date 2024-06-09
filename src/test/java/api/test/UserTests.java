package api.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;

import api.endpoints.UserEndPoints;
import api.payload_POJO.User;
import io.restassured.response.Response;

public class UserTests {

	Faker faker;
	User userPayload;
	String data;
	
	org.apache.logging.log4j.Logger logger;

	@BeforeClass
	public void setUpData() throws JsonProcessingException {

		faker = new Faker();
		userPayload = new User();

		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().emailAddress());
		userPayload.setPassword(faker.internet().password(7, 12));
		userPayload.setPhone(faker.phoneNumber().cellPhone());

		// Read data from POJO to JSON

		ObjectMapper mapper = new ObjectMapper();
		data = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(userPayload);

		// System.out.println(data);
		
		logger= LogManager.getLogger(this.getClass());
		
		logger.info(" ******************* Set Up the Data ******************* ");

	}

	@Test(priority = 1)
	public void testPostUser() {

		logger.info(" ******************* Creating User ******************* ");
		
		Response res = UserEndPoints.createUser(userPayload);

		res.then().log().all();

		Assert.assertEquals(res.getStatusCode(), 200);
		
		logger.info(" ******************* User Created Successfully..!! ******************* ");

	}

	@Test(priority = 2)
	public void tesGetUserByName() {
		
		logger.info(" ******************* Reading User ******************* ");

		Response res = UserEndPoints.readUser(this.userPayload.getUsername());

		res.then().log().all();

		Assert.assertEquals(res.getStatusCode(), 200);
		
		logger.info(" ******************* User Read Successfully..!! ******************* ");
	}

	@Test(priority = 3)
	public void testUpdateUserByName() {

		// update user following details
		
		logger.info(" ******************* Updating User ******************* ");

		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().emailAddress());

		Response res = UserEndPoints.updateUser(this.userPayload.getUsername(), userPayload);
		res.then().log().all();

		Assert.assertEquals(res.getStatusCode(), 200);
		
		logger.info(" ******************* User Updated Successfully..!! ******************* ");

		// Checking data after update
		
		logger.info(" ******************* Reading Updating User ******************* ");

		Response res1 = UserEndPoints.readUser(this.userPayload.getUsername());

		res1.then().log().all();

		Assert.assertEquals(res1.getStatusCode(), 200);
		
		logger.info(" ******************* Updated User Read Successfully..!! ******************* ");

	}
	
	@Test(priority = 4)
	public void testDeleteUserByName() {
		
		logger.info(" ******************* Deleting User ******************* ");
		
		Response res = UserEndPoints.deleteUser(this.userPayload.getUsername());
		res.then().log().all();
		
		Assert.assertEquals(res.getStatusCode(), 200);
		
		logger.info(" ******************* User Deleted Successfully..!! ******************* ");
		
	}

}
