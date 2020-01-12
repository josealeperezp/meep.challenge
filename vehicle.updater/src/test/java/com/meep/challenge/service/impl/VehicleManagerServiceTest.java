package com.meep.challenge.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.meep.challenge.Application;
import com.meep.challenge.dto.VehicleDTO;
import com.meep.challenge.dto.VehicleEntity;
import com.meep.challenge.repository.VehicleRepository;
import com.meep.challenge.service.VehicleManagerService;
import com.meep.challenge.service.VehicleWrapperService;

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest(classes = Application.class)
@TestPropertySource(properties = "vehicle.job.autostart=false")
public class VehicleManagerServiceTest {
	
	@Autowired
	private VehicleManagerService vehicleManagerService;
	
	@Autowired
	private VehicleRepository vehicleRepository;
	
	@MockBean
	private VehicleWrapperService vehicleWrapperService;
	
	@Before
	public void before() {
		vehicleRepository.deleteAll();
		VehicleEntity v1 = new VehicleEntity();
		v1.setVehicleId("v1");
		v1.setLicencePlate("p1");
		
		VehicleEntity v2 = new VehicleEntity();
		v2.setVehicleId("v2");
		v2.setLicencePlate("p2");
		
		VehicleEntity v3 = new VehicleEntity();
		v3.setVehicleId("v3");
		v3.setLicencePlate("p3");
		
		vehicleRepository.saveAndFlush(v1);
		vehicleRepository.saveAndFlush(v2);
		vehicleRepository.saveAndFlush(v3);
	}
	
	@After
	public void after() {
		vehicleRepository.deleteAll();
	}
	
	@Test
	public void syncVehiclesTest() throws Exception {
		List<VehicleDTO> vehicles = new ArrayList<>();
		
		VehicleDTO v1 = new VehicleDTO();
		v1.setId("v1");
		v1.setLicencePlate("p1");
		
		VehicleDTO v2 = new VehicleDTO();
		v2.setId("v2");
		v2.setLicencePlate("p2");
		
		VehicleDTO v4 = new VehicleDTO();
		v4.setId("v4");
		v4.setLicencePlate("p4");
		
		vehicles.add(v1);
		vehicles.add(v2);
		vehicles.add(v4);
		
		doReturn(vehicles).when(vehicleWrapperService).requestVehicles();

		//Initial state: v1, v2, v3
		vehicleManagerService.syncVehicles();
		
		//Expected final state: v1, v2, v4
		List<VehicleEntity> finalVehicles = vehicleRepository.findAll();
		assertEquals("Unexpected size", 3, finalVehicles.size());
		
		boolean hasV1 = false;
		boolean hasV2 = false;
		boolean hasV4 = false;
		for(VehicleEntity ve : finalVehicles) {
			if(v1.getId().equals(ve.getVehicleId()))
				hasV1 = true;
			
			if(v2.getId().equals(ve.getVehicleId()))
				hasV2 = true;
			
			if(v4.getId().equals(ve.getVehicleId()))
				hasV4 = true;
		}
		assertTrue("Expected vehicle is not present", hasV1);
		assertTrue("Expected vehicle is not present", hasV2);
		assertTrue("Expected vehicle is not present", hasV4);
	}

}
