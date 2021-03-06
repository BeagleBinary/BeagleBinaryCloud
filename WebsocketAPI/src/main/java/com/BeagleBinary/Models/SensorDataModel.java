package com.BeagleBinary.Models;


import com.amazonaws.services.dynamodbv2.datamodeling.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@DynamoDBDocument
@DynamoDBTable(tableName = "SensorData")
public class SensorDataModel {

    private String id;
    private String timeStamp;
    private double data;
    private String sensorId;
    private int dataType;
    private String externalAddress;

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");


    public SensorDataModel()
    {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        timeStamp = sdf.format(timestamp);
        data = 0;
    }


    @DynamoDBHashKey
    @DynamoDBAutoGeneratedKey
    public String getId() {
        return this.id;
    }


    @DynamoDBAttribute
    public String getTimeStamp() {
        return this.timeStamp;
    }


    @DynamoDBAttribute
    public double getData() {
        return this.data;
    }


    @DynamoDBAttribute
    public String getSensorId() {
        return this.sensorId;
    }


    @DynamoDBAttribute
    public String getExternalAddress() {
        return this.externalAddress;
    }

    @DynamoDBAttribute
    public int getDataType() {
        return this.dataType;
    }

    public void setId(String id){
        this.id = id;
    }


    public void setTimeStamp(String timeStamp){
        this.timeStamp = timeStamp;
    }


    public void setDataType(int dataType){
        this.dataType = dataType;
    }


    public void setData(double data){
        this.data = data;
    }


    public void setSensorId(String sensorId){
        this.sensorId = sensorId;
    }

    public void setExternalAddress(String address){
        this.externalAddress = address;
    }
}
