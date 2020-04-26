package com.BeagleBinary.Repositories;

import com.BeagleBinary.Dtos.SensorDto;
import com.BeagleBinary.Models.SensorModel;
import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class SensorRepository {

	private static final Logger LOGGER = LoggerFactory.getLogger(SensorRepository.class);

	@Autowired
	private DynamoDBMapper mapper;

	public void insertIntoDynamoDB(SensorModel sensor)
	{
		try{
			mapper.save(sensor);

		}catch (DynamoDBMappingException e){

		}

	}

	public SensorModel getByExternal(String address){

		PaginatedScanList<SensorModel> allSensors = mapper.scan(SensorModel.class, new DynamoDBScanExpression());

		for(SensorModel sensorModel: allSensors)
		{
			if(sensorModel.getExternalAddress().equals(address)){
				return sensorModel;
			}
		}

		return null;
	}

	public SensorModel getSensor(String sensorId)
	{
		return mapper.load(SensorModel.class, sensorId);
	}

	public List<SensorDto> getSensors()
	{
		List<SensorDto> toReturn = new ArrayList<SensorDto>();
		PaginatedScanList<SensorModel> allUsers = mapper.scan(SensorModel.class, new DynamoDBScanExpression());

		for(SensorModel sensorModel: allUsers)
		{
			toReturn.add(new SensorDto(sensorModel));
		}
		return toReturn;
	}
	public void updateSensor(SensorModel sensor) {
		try
		{
			mapper.save(sensor, buildDynamoDBSaveExpression(sensor));

		} catch (ConditionalCheckFailedException exception)
		{
			LOGGER.error("invalid data - " + exception.getMessage());
		}
	}

	public void deleteSensor(SensorModel sensor)
	{
		mapper.delete(sensor);
	}

	public DynamoDBSaveExpression buildDynamoDBSaveExpression(SensorModel user)
	{
		DynamoDBSaveExpression saveExpression = new DynamoDBSaveExpression();
		Map<String, ExpectedAttributeValue> expected = new HashMap<>();

		expected.put("id", new ExpectedAttributeValue(new AttributeValue(user.getId()))
				.withComparisonOperator(ComparisonOperator.EQ));

		saveExpression.setExpected(expected);

		return saveExpression;
	}
}