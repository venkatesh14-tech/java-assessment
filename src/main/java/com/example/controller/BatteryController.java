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
