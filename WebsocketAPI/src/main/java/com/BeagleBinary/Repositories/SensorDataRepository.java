package com.BeagleBinary.Repositories;

import com.BeagleBinary.Dtos.SensorDataDto;
import com.BeagleBinary.Models.SensorDataModel;
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
public class SensorDataRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(SensorDataRepository.class);

    @Autowired
    private DynamoDBMapper mapper;


    @Autowired
    private SensorRepository sensorRepository;


    public void insertIntoDynamoDB(SensorDataModel data)
    {
        try{
            SensorModel sensor = sensorRepository.getSensor(data.getSensorId());

            if(sensor != null)
            {
                mapper.save(sensor);
            }

            mapper.save(data);
        }catch (DynamoDBMappingException e){

        }

    }


    public SensorDataModel getSensorData(String sensorDataId)
    {
        return mapper.load(SensorDataModel.class, sensorDataId);
    }


    public List<SensorDataDto> getSensorDatas()
    {
        List<SensorDataDto> toReturn = new ArrayList<SensorDataDto>();
        PaginatedScanList<SensorDataModel> allUsers = mapper.scan(SensorDataModel.class, new DynamoDBScanExpression());

        for(SensorDataModel SensorDataModel: allUsers)
        {
            toReturn.add(new SensorDataDto(SensorDataModel));
        }
        return toReturn;
    }


    public void updateSensorData(SensorDataModel sensor)
    {
        try
        {
            mapper.save(sensor, buildDynamoDBSaveExpression(sensor));
        } catch (ConditionalCheckFailedException exception)
        {
            LOGGER.error("invalid data - " + exception.getMessage());
        }
    }

    public void deleteSensorData(SensorDataModel sensor)
    {
        mapper.delete(sensor);
    }


    public void deleteSensorData(String dataId)
    {
        SensorDataModel toDelete = mapper.load(SensorDataModel.class, dataId);
        mapper.delete(toDelete);
    }

    public DynamoDBSaveExpression buildDynamoDBSaveExpression(SensorDataModel sensor)
    {
        DynamoDBSaveExpression saveExpression = new DynamoDBSaveExpression();
        Map<String, ExpectedAttributeValue> expected = new HashMap<>();

        expected.put("id", new ExpectedAttributeValue(new AttributeValue(sensor.getId()))
                .withComparisonOperator(ComparisonOperator.EQ));
        saveExpression.setExpected(expected);

        return saveExpression;
    }
}
