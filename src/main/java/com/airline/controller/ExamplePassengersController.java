package com.airline.controller;

import com.airline.controller.request.PassengerRequestExample;
import com.airline.entity.Passengers;
import com.airline.repository.PassengersDao;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/rest/passengersExample")
public class ExamplePassengersController {
	@Autowired
	private PassengersDao passengersDao;

	@GetMapping
	public ResponseEntity<List<Passengers>> getPassengers() {
		return new ResponseEntity<>(passengersDao.findAll(), HttpStatus.OK);
	}

	@ApiOperation(value = "Get user from server by id")
	@ApiResponses({
			@ApiResponse(code = 200, message = "Successful getting user"),
			@ApiResponse(code = 400, message = "Invalid User ID supplied"),
			@ApiResponse(code = 401, message = "Lol kek"),
			@ApiResponse(code = 404, message = "User was not found"),
			@ApiResponse(code = 500, message = "Server error, something wrong"),
	})
	@GetMapping(value = "/{id}")
	public ResponseEntity<Passengers> getPassengersById(@ApiParam("User Path Id") @PathVariable Long id) {
		Passengers passengers = passengersDao.findById(id);
		return new ResponseEntity<>(passengers, HttpStatus.OK);
	}

	@PostMapping
	@Transactional
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Passengers> createPassenger(@RequestBody @Valid PassengerRequestExample request) {
		Passengers passengers = new Passengers();
		//userID is empty - will be generated by DB
		passengers.setName (request.getName ());
		passengers.setSecondName (request.getSecondName());
		passengers.setBirthDate(request.getBirthDate());
		passengers.setCreated (	 new Timestamp (System.currentTimeMillis ()));
		passengers.setCountry (request.getCountry ());
		passengers.setLogin(request.getLogin());
		passengers.setPassword(request.getPassword());

		Passengers save = passengersDao.save(passengers);
		//        roleDao.save(new Role(savedUser.getUserId(), "ROLE_USER"));
		return new ResponseEntity<>(save, HttpStatus.OK);
	}

	@PutMapping(value = "/{id}")
	@Transactional
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Passengers> updatePassenger(@PathVariable("id") Long id, @RequestBody @Valid PassengerRequestExample request) {

		Passengers passengers = passengersDao.findById (id);

		passengers.setName (request.getName());
		passengers.setSecondName (request.getSecondName());
		passengers.setBirthDate(request.getBirthDate());
		passengers.setChanged (new Timestamp (System.currentTimeMillis ()));
		passengers.setCountry (request.getCountry ());
		passengers.setLogin(request.getLogin());
		passengers.setPassword(request.getPassword());

		Passengers update = passengersDao.update(passengers);
		//        roleDao.save(new Role(savedUser.getUserId(), "ROLE_USER"));
		return new ResponseEntity<>(update, HttpStatus.OK);
	}

	@DeleteMapping (value = "/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Long> deletePassenger(@PathVariable("id") Long id) {
		passengersDao.delete (id);
		return new ResponseEntity<>(id, HttpStatus.OK);
	}
	}
