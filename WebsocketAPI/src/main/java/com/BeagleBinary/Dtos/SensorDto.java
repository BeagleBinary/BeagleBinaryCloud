package com.BeagleBinary.Dtos;

import com.BeagleBinary.Models.SensorModel;

public class SensorDto {
    public String id;
    public String userId;
    public String externalAddress;

    public SensorDto(){}

    public SensorDto(SensorModel sensorModel)
    {
        this.id = sensorModel.getId();
        this.userId = sensorModel.getUserId();
        this.externalAddress = sensorModel.getExternalAddress();
    }
}
