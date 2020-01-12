package com.meep.challenge.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.meep.challenge.dto.VehicleDTO;
import com.meep.challenge.dto.VehicleEntity;
import com.meep.challenge.exceptions.VehicleManagerException;
import com.meep.challenge.repository.VehicleRepository;
import com.meep.challenge.service.VehicleManagerService;
import com.meep.challenge.service.VehicleWrapperService;


@Service
public class VehicleManagerServiceImpl implements VehicleManagerService {
	
	Logger logger = LoggerFactory.getLogger(VehicleManagerServiceImpl.class);
	
	@Autowired
	private VehicleWrapperService vehicleWrapperService;
	
	@Autowired
	private VehicleRepository vehicleRepository;
	
	@Autowired
	@Qualifier("vehicleMapper")
	private Mapper mapper;

    @Transactional(propagation=Propagation.REQUIRED, readOnly=false)
    public void syncVehicles() throws VehicleManagerException {
    	try {
	    	List<VehicleDTO> actualVehicles = vehicleWrapperService.requestVehicles();
	    	Map<String, VehicleDTO> vehicleMap = toMap(actualVehicles);
	    	Set<String> actualVehicleIds = vehicleMap.keySet();
	    	Integer countVehiclesToDelete = vehicleRepository.countVehicleNotIdIn(actualVehicleIds);
	    	if(countVehiclesToDelete > 0)
	    		vehicleRepository.deleteNotIn(actualVehicleIds);
	    	
	    	logger.info("--> Vehicles deleted: "+countVehiclesToDelete);
	    	logger.debug("Vehicle IDs: "+actualVehicleIds);
	    	
	    	List<String> difference = vehicleRepository.findVehicleIdIn(actualVehicleIds);
	    	actualVehicleIds.removeAll(difference);
	    	if(!actualVehicleIds.isEmpty()) {
		    	List<VehicleEntity> vehiclesToSync = new ArrayList<>();
		    	for(String vehicleId : actualVehicleIds)
		    		vehiclesToSync.add(mapper.map(vehicleMap.get(vehicleId), VehicleEntity.class, "dto_to_entity"));
		    	
		    	vehicleRepository.saveAll(vehiclesToSync);
	    	}
	    	
	    	logger.info("--> Vehicles added: "+actualVehicleIds.size());
	    	logger.debug("Vehicle IDs: "+actualVehicleIds);
	    	
    	} catch(VehicleManagerException vme) {
    		throw vme;
    	} catch(Exception e) {
    		logger.error("Error updating vehicles.", e);
    		throw new VehicleManagerException();
    	}
    }
    
    @Override
    @Transactional(propagation=Propagation.REQUIRED, readOnly=true)
    public List<VehicleDTO> getAllVehicles() throws VehicleManagerException {
    	try {
	    	List<VehicleEntity> vehicleEntities = vehicleRepository.findAll();
	    	List<VehicleDTO> vehicleDTOs = new ArrayList<>();
	    	for(VehicleEntity vehicleEntity: vehicleEntities)
	    		vehicleDTOs.add(mapper.map(vehicleEntity, VehicleDTO.class, "entity_to_dto"));
	    	
	    	return vehicleDTOs;
    	} catch(Exception e) {
    		logger.error("Error retrieving vehicles.", e);
    		throw new VehicleManagerException();
    	}
    }
    
    private Map<String, VehicleDTO> toMap(List<VehicleDTO> vehicles) {
    	Map<String, VehicleDTO> vehicleMap = new HashMap<>();
    	for(VehicleDTO vehicle : vehicles)
    		vehicleMap.put(vehicle.getId(), vehicle);
    	
    	return vehicleMap;
    }
    
}
