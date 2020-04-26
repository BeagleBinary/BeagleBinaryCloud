package com.serverless.SensorFiles;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.serverless.ApiGatewayResponse;
import com.serverless.Response;
import com.serverless.dal.Sensor;
import org.apache.log4j.Logger;

import java.util.Map;

public class GetSensorByExternalAddressHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

    private final Logger logger = Logger.getLogger(this.getClass());

    @Override
    public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {

        try {
            // get the 'pathParameters' from input
            Map<String,String> pathParameters =  (Map<String,String>)input.get("pathParameters");
            String externalAddress = pathParameters.get("address");

            Sensor sensor = new Sensor().getByAddress(externalAddress);

            if (sensor != null) {

                return ApiGatewayResponse.builder()
                        .setStatusCode(200)
                        .setObjectBody(sensor)
                        .setCORSHeaders()
                        .build();
            } else {
                return ApiGatewayResponse.builder()
                        .setStatusCode(404)
                        .setObjectBody("Sensor with external address of: '" + externalAddress + "' not found.")
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
