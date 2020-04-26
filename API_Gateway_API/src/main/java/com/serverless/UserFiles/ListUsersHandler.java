package com.serverless.UserFiles;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.serverless.ApiGatewayResponse;
import com.serverless.Response;
import com.serverless.dal.Sensor;
import com.serverless.dal.User;
import org.apache.log4j.Logger;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ListUsersHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

	private final Logger logger = Logger.getLogger(this.getClass());

	@Override
	public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {
    try {
        // get all products
        List<User> users = new User().list();

        // send the response back
        return ApiGatewayResponse.builder()
    				.setStatusCode(200)
    				.setObjectBody(users)
    				.setCORSHeaders()
    				.build();
    } catch (Exception ex) {
        logger.error("Error in listing users: " + ex);
        // send the error response back
  			Response responseBody = new Response("Error in listing users: ", input);
  			return ApiGatewayResponse.builder()
  					.setStatusCode(500)
  					.setObjectBody(responseBody)
  					.setCORSHeaders()
  					.build();
    }
	}
}
