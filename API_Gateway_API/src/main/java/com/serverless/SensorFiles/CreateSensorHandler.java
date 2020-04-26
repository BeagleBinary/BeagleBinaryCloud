package com.serverless.SensorFiles;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.serverless.ApiGatewayResponse;
import com.serverless.Response;
import com.serverless.dal.Sensor;
import com.serverless.dal.User;
import org.apache.log4j.Logger;

import java.util.Collections;
import java.util.Map;

public class CreateSensorHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

	private final Logger logger = Logger.getLogger(this.getClass());

	@Override
	public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {


      try {
          // get the 'body' from input
          JsonNode body = new ObjectMapper().readTree((String) input.get("body"));

          // create the Product object for post
		  String userId = body.get("userId").asText();
		  String externalAddress = body.get("externalAddress").asText();

		  if(userId == null){
			  Response responseBody = new Response("Please provide a valid user id for the sensor",input);
			  return ApiGatewayResponse.builder()
					  .setStatusCode(500)
					  .setObjectBody(responseBody)
					  .setCORSHeaders()
					  .build();
		  }

		  User user = new User().get(userId);

		  if(user == null){
			  Response responseBody = new Response("User Not Found",input);
			  return ApiGatewayResponse.builder()
					  .setStatusCode(500)
					  .setObjectBody(responseBody)
					  .setCORSHeaders()
					  .build();
		  }


		  Sensor sensor = new Sensor();
		  sensor.setUserId(userId);
		  sensor.setExternalAddress(externalAddress);

          sensor.save(sensor);

          return ApiGatewayResponse.builder()
      				.setStatusCode(200)
      				.setObjectBody(sensor)
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
