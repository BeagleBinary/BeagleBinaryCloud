package com.serverless.SensorDataFiles;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.serverless.ApiGatewayResponse;
import com.serverless.Response;
import com.serverless.dal.SensorData;
import org.apache.log4j.Logger;

import java.util.Collections;
import java.util.Map;

public class DeleteSensorDataHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

	private final Logger logger = Logger.getLogger(this.getClass());

	@Override
	public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {

    try {
        // get the 'pathParameters' from input
        Map<String,String> pathParameters =  (Map<String,String>)input.get("pathParameters");
        String sensorDataId = pathParameters.get("id");

        // get the Product by id
        Boolean success = new SensorData().delete(sensorDataId);

        // send the response back
        if (success) {
          return ApiGatewayResponse.builder()
      				.setStatusCode(204)
      				.setCORSHeaders()
      				.build();
        } else {
          return ApiGatewayResponse.builder()
      				.setStatusCode(404)
      				.setObjectBody("Sensor data with id: '" + sensorDataId + "' not found.")
      				.setCORSHeaders()
      				.build();
        }
    } catch (Exception ex) {
        logger.error("Error in deleting sensor data: " + ex);

        // send the error response back
  			Response responseBody = new Response("Error in deleting sensor data: ", input);
  			return ApiGatewayResponse.builder()
  					.setStatusCode(500)
  					.setObjectBody(responseBody)
  					.setCORSHeaders()
  					.build();
    }
	}
}
