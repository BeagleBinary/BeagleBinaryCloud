package com.serverless.SensorDataFiles;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.serverless.ApiGatewayResponse;
import com.serverless.Response;
import com.serverless.dal.Sensor;
import com.serverless.dal.SensorData;
import org.apache.log4j.Logger;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class GetSensorDataBySensorHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

	private final Logger logger = Logger.getLogger(this.getClass());

	@Override
	public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {

    try {
        // get the 'pathParameters' from input
        Map<String,String> pathParameters =  (Map<String,String>)input.get("pathParameters");
        String sensorId = pathParameters.get("sensorId");

        Sensor sensor = new Sensor().get(sensorId);



        if (sensor != null) {
        	List<SensorData> sensorDataList = new SensorData().getSensorData(sensorId);
          return ApiGatewayResponse.builder()
      				.setStatusCode(200)
      				.setObjectBody(sensorDataList)
      				.setCORSHeaders()
      				.build();
        } else {
          return ApiGatewayResponse.builder()
      				.setStatusCode(404)
              .setObjectBody("Sensor with id: '" + sensorId + "' not found.")
      				.setCORSHeaders()
      				.build();
        }
    } catch (Exception ex) {
        logger.error("Error in retrieving sensor: " + ex);

  			Response responseBody = new Response("Error in retrieving sensor:  " + ex, input);
  			return ApiGatewayResponse.builder()
  					.setStatusCode(500)
  					.setObjectBody(responseBody)
  					.setCORSHeaders()
  					.build();
    	}
	}
}
