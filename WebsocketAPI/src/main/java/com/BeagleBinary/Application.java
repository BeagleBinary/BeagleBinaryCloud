package com.BeagleBinary;


import com.BeagleBinary.Models.SensorDataModel;
import com.BeagleBinary.Models.SensorModel;
import com.BeagleBinary.Models.UserModel;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {

	private DynamoDBMapper dynamoDBMapper;

	@Autowired
	private AmazonDynamoDB amazonDynamoDB;

	public static void main(String[] args)
	{
		try
		{
			SpringApplication.run(Application.class, args);
		}catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	@Override
	public void run(String... args) throws Exception
	{
		//SpringApplication.run(Application.class, args);
		//Create Sensor Data table
		dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);

		CreateTableRequest sensorDataTableRequest = dynamoDBMapper
				.generateCreateTableRequest(SensorDataModel.class);
		sensorDataTableRequest.setProvisionedThroughput(
				new ProvisionedThroughput(1L, 1L));
		TableUtils.createTableIfNotExists(amazonDynamoDB, sensorDataTableRequest);

		//Create User table
		CreateTableRequest userTableRequest = dynamoDBMapper
				.generateCreateTableRequest(UserModel.class);
		userTableRequest.setProvisionedThroughput(
				new ProvisionedThroughput(1L, 1L));
		TableUtils.createTableIfNotExists(amazonDynamoDB, userTableRequest);

		//Create Sensor table
		CreateTableRequest sensorTableRequest = dynamoDBMapper
				.generateCreateTableRequest(SensorModel.class);
		sensorTableRequest.setProvisionedThroughput(
				new ProvisionedThroughput(1L, 1L));
		TableUtils.createTableIfNotExists(amazonDynamoDB, sensorTableRequest);
	}

}
