package com.meep.challenge.jobs;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.meep.challenge.exceptions.VehicleManagerException;
import com.meep.challenge.service.impl.VehicleManagerServiceImpl;

@Component
@DisallowConcurrentExecution
public class VehiclePollingJob implements Job {
	
	Logger logger = LoggerFactory.getLogger(VehiclePollingJob.class);
	
    @Autowired
    VehicleManagerServiceImpl memberClassService;

    @Override
    public void execute(JobExecutionContext context) {
    	try {
    		logger.info("--> Begin job Execution");
    		memberClassService.syncVehicles();
    		logger.info("--> End job Execution");
    	} catch(VehicleManagerException vme) {
    		logger.error("Error in VehiclePollingJob job execution",vme);
    	}
    }
}
