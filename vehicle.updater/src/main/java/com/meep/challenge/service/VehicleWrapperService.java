package com.meep.challenge.service;

import java.util.List;

import com.meep.challenge.dto.VehicleDTO;
import com.meep.challenge.exceptions.VehicleManagerException;

public interface VehicleWrapperService {

	List<VehicleDTO> requestVehicles() throws VehicleManagerException;
}
