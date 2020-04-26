package com.BeagleBinary.Repositories;

import com.BeagleBinary.Dtos.UserDto;
import com.BeagleBinary.Models.UserModel;
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
public class UserRepository {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserRepository.class);

	@Autowired
	private DynamoDBMapper mapper;

	public void insertIntoDynamoDB(UserModel user)
	{
		try
		{
			mapper.save(user);

		}catch (DynamoDBMappingException e)
		{
			System.out.println("An exception has occured when uploading a user ");
			e.printStackTrace();
		}

	}

	public UserModel getUser(String userId)
	{
		return mapper.load(UserModel.class, userId);
	}

	public List<UserDto> getUsers()
	{
		List<UserDto> toReturn = new ArrayList<UserDto>();
		PaginatedScanList<UserModel> allUsers = mapper.scan(UserModel.class, new DynamoDBScanExpression());

		for(UserModel userModel: allUsers)
		{
			toReturn.add(new UserDto(userModel));
		}
		return toReturn;
	}
	public void updateUser(UserModel user) {
		try
		{
			mapper.save(user, buildDynamoDBSaveExpression(user));
		} catch (ConditionalCheckFailedException exception)
		{
			LOGGER.error("invalid data - " + exception.getMessage());
		}
	}

	public void deleteUser(UserModel user)
	{
		mapper.delete(user);
	}

	public DynamoDBSaveExpression buildDynamoDBSaveExpression(UserModel user)
	{
		DynamoDBSaveExpression saveExpression = new DynamoDBSaveExpression();
		Map<String, ExpectedAttributeValue> expected = new HashMap<>();

		expected.put("id", new ExpectedAttributeValue(new AttributeValue(user.getId()))
				.withComparisonOperator(ComparisonOperator.EQ));

		saveExpression.setExpected(expected);

		return saveExpression;
	}
}