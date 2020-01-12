package com.meep.challenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.meep.challenge.dto.VehicleEntity;

import java.util.List;
import java.util.Set;

@SuppressWarnings("rawtypes")
public interface VehicleRepository extends JpaRepository<VehicleEntity, Long>, JpaSpecificationExecutor {
    
	@Query("select vehicleId from VehicleEntity where vehicleId in (?1)")
	List<String> findVehicleIdIn(Set<String> ids);
	
	@Query("select count(0) from VehicleEntity where vehicleId not in (?1)")
	Integer countVehicleNotIdIn(Set<String> ids);
    
	@Modifying
	@Query("delete from VehicleEntity where vehicleId not in (?1)")
	void deleteNotIn(Set<String> ids);
}
