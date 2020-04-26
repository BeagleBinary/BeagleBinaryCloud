package com.serverless.UserFiles;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.serverless.ApiGatewayResponse;
import com.serverless.Response;
import com.serverless.dal.Sensor;
import com.serverless.dal.User;
import org.apache.log4j.Logger;

import javax.jws.soap.SOAPBinding;
import java.util.Collections;
import java.util.Map;

public class CreateUserHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

	private final Logger logger = Logger.getLogger(this.getClass());

	@Override
	public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {


      try {
          // get the 'body' from input
          JsonNode body = new ObjectMapper().readTree((String) input.get("body"));

          // create the Product object for post
		  String userName = body.get("userName").asText();
		  String firstName = body.get("firstName").asText();
		  String lastName = body.get("lastName").asText();
		  String emailAddress = body.get("emailAddress").asText();
		  String phoneNumber = body.get("phoneNumber").asText();
		  String password = body.get("password").asText();




		  if(userName == null || firstName == null || lastName == null || emailAddress == null || phoneNumber == null ||password == null){
			  Response responseBody = new Response("User input fields are incorrect",input);
			  return ApiGatewayResponse.builder()
					  .setStatusCode(500)
					  .setObjectBody(responseBody)
					  .setCORSHeaders()
					  .build();
		  }

		  User createdUser = new User(userName, firstName ,lastName,
				  emailAddress, phoneNumber, password);

		  createdUser.save(createdUser);


          return ApiGatewayResponse.builder()
      				.setStatusCode(200)
      				.setObjectBody(createdUser)
      				.setCORSHeaders()
      				.build();

      } catch (Exception ex) {
          logger.error("Error in saving sensor: " + ex);

          // send the error response back
    			Response responseBody = new Response("Error in saving sensor: " + ex, input);
    			return ApiGatewayResponse.builder()
    					.setStatusCode(500)
    					.setObjectBody(ex)
    					.setCORSHeaders()
    					.build();
      }
	}
}
