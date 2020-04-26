package com.serverless.UserFiles;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.serverless.ApiGatewayResponse;
import com.serverless.Response;
import com.serverless.dal.CookieModel;
import com.serverless.dal.User;
import com.serverless.dal.UserLoginModel;
import org.apache.log4j.Logger;

import java.util.Collections;
import java.util.Map;

public class UserLoginHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

    private final Logger logger = Logger.getLogger(this.getClass());

    @Override
    public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {
        try {
            // get the 'body' from input
            JsonNode body = new ObjectMapper().readTree((String) input.get("body"));

            String userName = body.get("userName").asText();
            String password = body.get("password").asText();

            User user = new User().getUserByPassword(userName, password);

            if(user == null)
            {
                logger.error("User was not found!");

                // send the error response back
                Response responseBody = new Response("User was not found!",input);

                return ApiGatewayResponse.builder()
                        .setStatusCode(404)
                        .setObjectBody(responseBody)
                        .setCORSHeaders()
                        .build();
            }


            user.setAccessKey("");
            user.save(user);

            CookieModel cookieModel = new CookieModel(user.getId(), user.getAccessKey(), user.getKeyExpirationTime());


            return ApiGatewayResponse.builder()
                    .setStatusCode(200)
                    .setObjectBody(cookieModel)
                    .setCORSHeaders()
                    .build();

        }catch (Exception exception)
        {
            logger.error("Error in logging in user : " + exception);

            // send the error response back
            Response responseBody = new Response("Error in logging in user: " + exception, input);
            return ApiGatewayResponse.builder()
                    .setStatusCode(500)
                    .setObjectBody(responseBody)
                    .setCORSHeaders()
                    .build();

        }
    }
}
