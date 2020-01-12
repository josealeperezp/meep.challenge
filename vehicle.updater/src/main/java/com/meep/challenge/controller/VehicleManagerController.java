package com.meep.challenge.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.meep.challenge.dto.VehicleDTO;
import com.meep.challenge.exceptions.VehicleManagerException;
import com.meep.challenge.service.VehicleManagerService;

@Controller
@RequestMapping(value = "/vehicles", produces = MediaType.APPLICATION_JSON_VALUE)
public class VehicleManagerController {
	Logger logger = LoggerFactory.getLogger(VehicleManagerController.class);

	@Autowired
	private VehicleManagerService vehicleManager;
	
    @ResponseBody
    @GetMapping
    public ResponseEntity<List<VehicleDTO>> getVehicles() {
    	try {
	    	List<VehicleDTO> vehicles = vehicleManager.getAllVehicles();
	        return new ResponseEntity<>(vehicles, HttpStatus.OK);
    	} catch (VehicleManagerException vme) {
    		logger.error("Error resolving vehicles", vme);
    		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    	}
    }
}
