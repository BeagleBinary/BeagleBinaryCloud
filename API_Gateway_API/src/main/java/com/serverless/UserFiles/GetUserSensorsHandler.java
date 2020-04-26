package com.serverless.UserFiles;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.serverless.ApiGatewayResponse;
import com.serverless.Response;
import com.serverless.dal.Sensor;
import com.serverless.dal.User;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;

public class GetUserSensorsHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

    private final Logger logger = Logger.getLogger(this.getClass());

    @Override
    public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {

        try {
            // get the 'pathParameters' from input
            Map<String,String> pathParameters =  (Map<String,String>)input.get("pathParameters");
            String userId = pathParameters.get("id");

            User user = new User().get(userId);

            if (user != null) {

                List<Sensor> sensors= new Sensor().getByUserId(userId);

                return ApiGatewayResponse.builder()
                        .setStatusCode(200)
                        .setObjectBody(sensors)
                        .setCORSHeaders()
                        .build();
            } else {
                return ApiGatewayResponse.builder()
                        .setStatusCode(404)
                        .setObjectBody("User with id: '" + userId + "' not found.")
                        .setCORSHeaders()
                        .build();
            }
        } catch (Exception ex) {
            logger.error("Error in retrieving user: " + ex);

            Response responseBody = new Response("Error in retrieving user:  " + ex, input);
            return ApiGatewayResponse.builder()
                    .setStatusCode(500)
                    .setObjectBody(responseBody)
                    .setCORSHeaders()
                    .build();
        }
    }
}
