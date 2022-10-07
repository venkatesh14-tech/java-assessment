package com.example.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Battery;
import com.example.repository.BatteryRepository;

@Service
public class BatteryService {

	@Autowired
	public BatteryRepository batteryRepository;

	public void save(Battery battery) {
		
		batteryRepository.save(battery);
	}

	@SuppressWarnings("unchecked")
	public JSONArray getDetailsByPostCode(Integer postCode) {
		JSONArray batteryDetails = new JSONArray();
		try {
			List<Battery> batteryList = batteryRepository.findAllByPostCode(postCode);
			batteryList = batteryList.stream().sorted(Comparator.comparing(Battery::getName))
					.collect(Collectors.toList());
			if (batteryList != null && !batteryList.isEmpty()) {
				for (Battery battery : batteryList) {
					JSONObject obj = new JSONObject();
					obj.put("name", battery.getName());
					Integer total = batteryList.stream().collect(Collectors.summingInt(Battery::getCapacity));
					double average = batteryList.stream().collect(Collectors.averagingDouble(Battery::getCapacity));
					obj.put("total", total);
					obj.put("average", average);
					batteryDetails.add(obj);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return batteryDetails;
	}
}
