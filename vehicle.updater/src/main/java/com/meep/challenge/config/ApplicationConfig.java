package com.meep.challenge.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.lang3.ArrayUtils;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;
import org.springframework.web.client.RestTemplate;

import com.meep.challenge.jobs.VehiclePollingJob;

@Configuration
public class ApplicationConfig {
    private ApplicationContext applicationContext;
    private DataSource dataSource;
    
    @Value("${vehicle.cron}")
    private String CRON_EXPRESSION;
    
    @Value("${vehicle.job.autostart}")
    private Boolean AUTO_START;

    public ApplicationConfig(ApplicationContext applicationContext, DataSource dataSource) {
        this.applicationContext = applicationContext;
        this.dataSource = dataSource;
    }
    
    @Bean
    public RestTemplate getRestTemplate() {
    	return new RestTemplate();
    }
    
    @Bean(name = "vehicleMapper")
    public Mapper mapper() {
    	List<String> mappingFiles = new ArrayList<>();
    	mappingFiles.add("dozer/vehicle-mapping.xml");
    	return new DozerBeanMapper(mappingFiles);
    }

    @Bean
    public SpringBeanJobFactory springBeanJobFactory() {
        CustomSpringBeanJobFactory jobFactory = new CustomSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

    @Bean
    public SchedulerFactoryBean scheduler(Trigger... triggers) {
        SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();

        if (ArrayUtils.isNotEmpty(triggers))
        	schedulerFactory.setTriggers(triggers);

        schedulerFactory.setOverwriteExistingJobs(true);
        schedulerFactory.setAutoStartup(AUTO_START);
        schedulerFactory.setQuartzProperties(new Properties());
        schedulerFactory.setDataSource(dataSource);
        schedulerFactory.setJobFactory(springBeanJobFactory());
        schedulerFactory.setWaitForJobsToCompleteOnShutdown(true);
        return schedulerFactory;
    }
    
    @Bean(name = "jobClass")
    public JobDetailFactoryBean jobClass() {
        return QuartzFactory.createJobDetail(VehiclePollingJob.class, "Class Statistics Job");
    }

    @Bean
    public CronTriggerFactoryBean triggerClass(@Qualifier("jobClass") JobDetail jobDetail) {
        return QuartzFactory.createCronTrigger(jobDetail, CRON_EXPRESSION, "Class Statistics Trigger");
    }
}
