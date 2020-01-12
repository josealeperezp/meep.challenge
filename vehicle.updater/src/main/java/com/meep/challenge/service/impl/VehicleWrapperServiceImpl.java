package com.meep.challenge.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.meep.challenge.dto.VehicleDTO;
import com.meep.challenge.exceptions.VehicleManagerException;
import com.meep.challenge.service.VehicleWrapperService;

@Service
public class VehicleWrapperServiceImpl implements VehicleWrapperService {
	@Value("${vehicle.url}")
	private String URL;
    
	@Value("${vehicle.lowerleftlatlon}")
	private String lowerLeftLatLon;
	
	@Value("${vehicle.upperrightlatlon}")
	private String upperRightLatLon;
	
	@Value("${vehicle.companyzoneids}")
	private String companyZoneIds;
	
	@Autowired
	private RestTemplate restTemplate;
    
    @Override
    public List<VehicleDTO> requestVehicles() throws VehicleManagerException {
    	try {
	    	ResponseEntity<List<VehicleDTO>> rateResponse =restTemplate.exchange(
	    		buildUrl().toUriString(),
	    	    HttpMethod.GET, 
	    	    null, 
	    	    new ParameterizedTypeReference<List<VehicleDTO>>() {});
	    	return rateResponse.getBody();
    	} catch(Exception e) {
    		throw new VehicleManagerException("Error retrieving info from Meep Vehicle API",e);
    	}
    }
    
    private UriComponentsBuilder buildUrl() {
    	UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL);
    	builder.queryParam("lowerLeftLatLon", lowerLeftLatLon);
    	builder.queryParam("upperRightLatLon", upperRightLatLon);
    	builder.queryParam("companyZoneIds", companyZoneIds);
    	return builder;
    }
}
