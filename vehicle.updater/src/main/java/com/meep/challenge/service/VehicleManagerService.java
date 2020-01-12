package com.meep.challenge.service;

import java.util.List;

import com.meep.challenge.dto.VehicleDTO;
import com.meep.challenge.exceptions.VehicleManagerException;

public interface VehicleManagerService {

	 void syncVehicles() throws VehicleManagerException;
	 List<VehicleDTO> getAllVehicles() throws VehicleManagerException;
}
