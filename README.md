# Beagle Binary Cloud Solution 
- [Cloud Architecture](#cloud-architecture)

  * [Registration API](#registration-api)
    + [How It Works](#how-it-works)
  * [Data Collection API](#data-collection-api)
    + [How It Works](#how-it-works-1)
    
- [How to Use](#how-to-use)


<!-- > This is a fixture to test heading levels -->


## Cloud Architecture

![Overall Architecture](https://github.gatech.edu/amirshafii3/BeagleBinary/blob/master/Cloud/Images/Cloud%20Architecture.png)

The cloud solution consists of two components: The Registration API, and the Data Collection API.

The the cloud stores data for three types: Users, Sensors, and Sensor Data. Users have sensors and sensors have data.



### Registration API

![Registration API](https://github.gatech.edu/amirshafii3/BeagleBinary/blob/master/Cloud/Images/Registration%20API.png)

The registration API handles CRUD (Create, read, update and delete) operations for Users and Sensors, as well as allowing for the querying of sensor data for any given sensor. This API is hosted using the Serverless framework along with AWS API Gateway and operates under the HTTP protocol. 


#### How It Works:
The Serverless framework parses the code and divides it into Lambda Functions for each of the CRUD operations. Lambda functions in the context of AWS are sections of code that handle requests to a particular path. These functions and paths are combined using AWS API gateway which assigns lambda functions to a particular path. 

The beauty in this approach is that the individual functions can be updated rather than re uploading the entire code. Structurally, each function can be written in a different programming language and pieced together by AWS API Gateway.

This is where the idea of Serverless comes from. There is not one monolith application hosted on a single server. Instead the application is split into several parts that is hosted on different servers pieced together by AWS.





### Data Collection API
![Data Collection API](https://github.gatech.edu/amirshafii3/BeagleBinary/blob/master/Cloud/Images/Data%20Creation%20API.png)

The Data Collection API serves to collect data from any given sensor. It can only collect numerical data and it is not able to do any CRUD operations. 

#### How It Works:
The Data Collection API utilizes the Springboot framework and is Written using Java. The application is hosted on AWS using Elastic Beanstalk, which is a service that allows a monolith application to be run on AWS. A Linux instance is created and is used to host the application which is uploaded as a.jar file. 




## How to Use
The process is as follows:
1. Create a user by making a post request to the Users endpoint. 
2. Create a sensor by making a post request to the Sensors endpoint. Use the User Id created in the previous step for the userId parameter.
3. Using the Websocket endpoint, enter a valid sensorId(perhaps the one from the previous step) and data.
4. You can query the data for any given sensor by providing the Sensor Data endpoint followed by the sensor's sensorId.

## Endpoints
### Data Collection API
The the data collection API can be found at:
http://beaglebinaryapi-env.pizmphu9qv.us-east-1.elasticbeanstalk.com/

The website is just for testing purposes. In order to use it.
1. Press connect to establish a connection to the API.
2. Once a connection is established, provide a valid sensor ID and data.
3. Press send to send the data. If the operation was sucessful a message such as:

```
Wrote data value, 1.0 for sensor: 58ef016b-9889-42f9-b6e2-78db9a86ac97
```

### Registration API

The endpoints for the Registration API are as follows:
  #### Sensors
  POST https://55k9v8ozca.execute-api.us-east-1.amazonaws.com/dev/sensors
  
     This is the endpoint for creating a sensor.
     
  DELETE https://55k9v8ozca.execute-api.us-east-1.amazonaws.com/dev/sensors/{id}
  
     This is the endpoint for deleing a sensor.
     
  GET https://55k9v8ozca.execute-api.us-east-1.amazonaws.com/dev/sensors/{id}
  
     This is the endpoint for getting a specific sensor.
     
  GET https://55k9v8ozca.execute-api.us-east-1.amazonaws.com/dev/sensors
  
     This is the endpoint for getting all sensors.
  
  #### Sensor Data Querying
  DELETE https://55k9v8ozca.execute-api.us-east-1.amazonaws.com/dev/data/{id}
  
     This is this is the endpoint to delete a specific sensor data. This is only for testing and should never be used.
     
  GET https://55k9v8ozca.execute-api.us-east-1.amazonaws.com/dev/data/{id}
  
     This is the endpoint for retrieving a specific sensor data object.
     
  GET https://55k9v8ozca.execute-api.us-east-1.amazonaws.com/dev/data/bySensor/{sensorId}
  
     This is the endpoint for getting data for querying all sensor data for a specific sensor.
  
  #### User
  POST https://55k9v8ozca.execute-api.us-east-1.amazonaws.com/dev/users
  
     This is the endpoint for creating a user.
     
  DELETE https://55k9v8ozca.execute-api.us-east-1.amazonaws.com/dev/users/{id}
  
     This is the endpoint for deleting a user.
     
  GET https://55k9v8ozca.execute-api.us-east-1.amazonaws.com/dev/users/{id}
  
     This is the endpoint for getting a user.
     
  GET https://55k9v8ozca.execute-api.us-east-1.amazonaws.com/dev/users
  
     This is the endpoint for getting all users.

