package com.airport.controller;



import com.airport.controller.request.change.PassportUpdateRequest;
import com.airport.controller.request.create.PassportSaveRequest;
import com.airport.entity.Passenger;
import com.airport.entity.Passports;
import com.airport.exceptions.EntityNotFoundException;
import com.airport.repository.springdata.PassengersRepository;
import com.airport.repository.springdata.PassportsRepository;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.Objects;
import java.util.Set;

@CrossOrigin
@RestController
@RequestMapping(value = "/rest/passports")
@RequiredArgsConstructor
public class PassportsController {

	private final PassportsRepository passportsRepository;
	private final PassengersRepository passengersRepository;
	private final ConversionService conversionService;

	@ApiImplicitParams({
			@ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", value = "Results page you want to retrieve (0..N)"),
			@ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", value = "Number of records per page."),
			@ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
					value = "Sorting criteria in the format: property(, " + "\"asc or desc\"). " + "Default sort order is ascending. " + "Multiple sort criteria are supported.")})
	@GetMapping
	public ResponseEntity<Page<Passports>> findAllPassports (@ApiIgnore Pageable pageable) {
		return new ResponseEntity<> (passportsRepository.findAll (pageable), HttpStatus.OK);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Passports> findPassportById (@PathVariable("id") String id) {
		Passports passports = passportsRepository.findById (Long.valueOf (id))
		                                         .orElseThrow (() -> new EntityNotFoundException (Passports.class, id));
		return new ResponseEntity<> (passports, HttpStatus.OK);
	}

	@GetMapping(value = "/passenger/{passengerId}")
	public ResponseEntity<Set<Passports>> findPassportsByPassengersId (@PathVariable("passengerId") String passengerId) {
		Passenger passenger = passengersRepository.findById (Long.valueOf (passengerId))
		                                          .orElseThrow (() -> new EntityNotFoundException (Passenger.class, passengerId));
		return new ResponseEntity<> (passenger.getPassports (), HttpStatus.OK);
	}

	@Transactional
	@DeleteMapping(value = "/{id}")
	public String deletePassport (@PathVariable("id") String id) {
		Passports passports = passportsRepository.findById (Long.valueOf (id))
		                                         .orElseThrow (() -> new EntityNotFoundException (Passports.class, id));
		passportsRepository.delete (passports);
		return id;
	}

	@Transactional
	@DeleteMapping(value = "/passenger/{passengerId}")
	public String deletePassportByPassengersId (@PathVariable("passengerId") String passengerId) {
		passportsRepository.deletePassportsByPassportsId (Long.valueOf (passengerId));
		return passengerId;
	}

	@PostMapping(value = "/passenger/{passengerId}")
	@Transactional
	public ResponseEntity<Passports> createPassport (@PathVariable String passengerId,
	                                                 @RequestBody @Valid PassportSaveRequest passportSaveRequest) {
		passportSaveRequest.setPassengerId (Long.valueOf (passengerId));
		return new ResponseEntity<> (passportsRepository.saveAndFlush (Objects.requireNonNull (
				conversionService.convert (passportSaveRequest, Passports.class))), HttpStatus.CREATED);
	}

	@PutMapping(value = "/{id}")
	@Transactional
	public ResponseEntity<Passports> updatePassport (@PathVariable String id, @RequestBody @Valid PassportUpdateRequest passportUpdateRequest) {
		passportUpdateRequest.setId (id);
		return new ResponseEntity<> (passportsRepository.saveAndFlush (Objects.requireNonNull (
				conversionService.convert (passportUpdateRequest, Passports.class))), HttpStatus.CREATED);
	}
}
