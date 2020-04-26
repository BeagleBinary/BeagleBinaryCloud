package com.BeagleBinary.Controllers;


import com.BeagleBinary.Dtos.Greeting;
import com.BeagleBinary.Dtos.SensorDataDto;
import com.BeagleBinary.Models.SensorDataModel;
import com.BeagleBinary.Models.SensorModel;
import com.BeagleBinary.Repositories.SensorDataRepository;
import com.BeagleBinary.Repositories.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.util.HtmlUtils;

@Controller
@CrossOrigin(origins = "*")
public class GreetingController {

	@Autowired
	private SensorDataRepository dataRepository;

	@Autowired
	private SensorRepository sensorRepository;


	@MessageMapping("/API/data")
	@SendTo("/topic/collect")
	public Greeting greeting(String stringData) throws Exception
	{

		/*
		///Thread.sleep(500); // simulated delay
		System.out.println("Sensor id of: " + data.sensorId );
		System.out.println("Sensor data id of: " + data.data );
		SensorModel sensor = sensorRepository.getSensor(data.sensorId);

		if(sensor == null)
		{

			return new Greeting("Error adding data sensor not found!");
		}

		SensorDataModel toAdd = new SensorDataModel();

		toAdd.setSensorId(data.sensorId);
		toAdd.setData(data.data);

		dataRepository.insertIntoDynamoDB(toAdd);
		return new Greeting("Wrote data value, " + data.data + " for sensor: " + HtmlUtils.htmlEscape(data.sensorId));

		 */
		stringData = formatDataString(stringData);




		String sensorId = stringData.split(",")[0];


		Double sensorData = Double.parseDouble(stringData.split(",")[1]);
		System.out.println("String data is " + stringData);
		int dataType  = Integer.parseInt(stringData.split(",")[2]);
		System.out.println("Sensor id of: " + sensorId );
		System.out.println("Sensor data id of: " +sensorData );
		System.out.println("Sensor data type of: " + dataType );
		SensorModel sensor = sensorRepository.getSensor(sensorId);


		if(sensor == null)
		{

			return new Greeting("Error adding data sensor not found!");
		}

		SensorDataModel toAdd = new SensorDataModel();

		toAdd.setSensorId(sensorId);
		toAdd.setData(sensorData);
		toAdd.setDataType(dataType);

		dataRepository.insertIntoDynamoDB(toAdd);
		return new Greeting("Wrote data value, " + sensorData + " for sensor: " + HtmlUtils.htmlEscape(sensorId));


	}


	private String formatDataString(String stringData)
	{
		if(stringData == null)
		{
			return "";
		}

		stringData  = stringData.replace("'sensorId'", "");
		stringData  = stringData.replace("'data'", "");
		stringData  = stringData.replace("'dataType'","");
		stringData = stringData.replace("{","");
		stringData = stringData.replace("}","");
		stringData = stringData.replace(" ","");
		stringData = stringData.replace(":","");
		stringData = stringData.replace("'","");
		stringData = stringData.replace("'","");

		return stringData;
	}
}
