package com.airport.repository.springdata;


import com.airport.entity.Airplanes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface AirplanesRepository extends CrudRepository<Airplanes, Long>, JpaRepository<Airplanes, Long> {
	Optional<Airplanes> findByModel (String model);

	@Modifying
	@Query("delete  from Airplanes a where a = :airplane")
	void deleteAirplane (Airplanes airplane);

}