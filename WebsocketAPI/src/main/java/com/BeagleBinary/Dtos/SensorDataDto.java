package com.BeagleBinary.Dtos;

import com.BeagleBinary.Models.SensorDataModel;

public class SensorDataDto {

    public String id;
    public String timeStamp;
    public String sensorId;
    public String externalAddress;
    public int dataType;
    public double data;

    public SensorDataDto(){}

    public SensorDataDto(SensorDataModel dataModel)
    {
        this.id = dataModel.getId();
        this.timeStamp = dataModel.getTimeStamp();
        this.data = dataModel.getData();
        this.sensorId = dataModel.getSensorId();
        this.externalAddress = dataModel.getExternalAddress();

        this.dataType = dataModel.getDataType();
    }
}
