package com.airport.service.impl;

import com.airport.entity.Passengers;
import com.airport.repository.springdata.CountriesRepository;
import com.airport.repository.springdata.PassengerRepository;
import com.airport.repository.springdata.PassportsRepository;
import com.airport.service.PassengersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.sql.Timestamp;

@Service
@RequiredArgsConstructor
public class PassengersServiceImpl implements PassengersService {

	private final PassengerRepository passengerRepository;
	private final CountriesRepository countriesRepository;
	private final PassportsRepository passportsRepository;

	private final EntityManager entityManager;
	@Override
	@Transactional
	public Passengers save (Passengers passenger) {
		//passenger.setCountries (countriesRepository.findCountriesByName (passenger.getCountries ().getName ()));
		passenger.setCreated (new Timestamp (System.currentTimeMillis ()));
		//passengers.setTickets (ticketsDao.findByIds (entity.getTickets ()));

		/*Set<Passports> passportsSet = passenger.getPassports ();
		passenger.setPassports (null);
		Passengers save = passengerRepository.saveAndFlush (passenger);

		for (Passports p : passportsSet){
			p.setPassengersId (save);
			passportsSet.add (passportsRepository.saveAndFlush (p));
		}
		save.setPassports (passportsSet);
		return save;*/
		return null;
	}

	@Override
	@Transactional
	public Passengers update (Passengers passenger) {
		entityManager.joinTransaction ();

		passenger.setChanged (new Timestamp (System.currentTimeMillis ()));
		Passengers update = passengerRepository.saveAndFlush (passenger);

		/*for (Passports p : passportsSet){
			p.setPassengersId (save);
			passportsSet.add (passportsRepository.saveAndFlush (p));
		}
		save.setPassports (passportsSet);*/
		return update;

	}







}
