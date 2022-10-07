package com.example.controller;

import java.util.List;

import org.json.simple.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Battery;
import com.example.service.BatteryService;

@RestController
@RequestMapping(value="/api")
public class BatteryController {
	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	public BatteryService batteryService;
	
	/**
	This api used to save all the battery details in h2 database
	@param in request body we will send list of batteries details like name,postcode and watt capacity
	@return this api return success and sends message as "Battery creation was successfull" or error code 404
	*/
	@PostMapping("/batteries")
	public ResponseEntity<?> saveBattery(@RequestBody List<Battery> batteries) {

		try {
			if (batteries != null && !batteries.isEmpty()) {
				batteries.forEach(battery -> {
					batteryService.save(battery);
				});
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.error("Exception occured at saving battery details" + e);
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body("Battery creation was successfull");
	}
	
	/**
	This api used to get list of names of batteries in sorting order and also total and average watt capacity 
	@param postcode will send as a param
	@return this api return list of names of batteries in sorting order and also total and average watt capacity
	*/
	@GetMapping("/batteries/{postcode}")
	public JSONArray getDetailsByPostCode(@PathVariable(value = "postcode") String postcode){
		try {
			Integer pc = postcode !=null && !postcode.equals("") ? Integer.parseInt(postcode) : 0;
			return batteryService.getDetailsByPostCode(pc);
		}catch(Exception e) {
			e.printStackTrace();
			log.error("Exception occured at getting details by passcode" + e);
			return null;
		}
		
	}

}
