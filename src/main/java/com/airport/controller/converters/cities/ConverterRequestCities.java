package com.airport.controller.converters.cities;

import com.airport.controller.converters.EntityConverter;
import com.airport.controller.request.change.CitiesUpdateRequest;
import com.airport.controller.request.create.CitiesSaveRequest;
import com.airport.entity.Cities;
import com.airport.entity.Countries;
import com.airport.exceptions.ArgumentOfMethodNotValidException;
import com.airport.exceptions.ConversionException;
import com.airport.exceptions.EntityAlreadyExistException;
import com.airport.exceptions.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import javax.persistence.NoResultException;

@RequiredArgsConstructor
// TODO add
public abstract class ConverterRequestCities<S, T> extends EntityConverter<S, T> {

	protected Cities doConvert (Cities cities, CitiesSaveRequest entity) {
		cities.setName (entity.getName ());
		return cities;
	}

	protected Cities doConvert (Cities cities, CitiesUpdateRequest entity) {
		if (entity.getName () != null) cities.setName (entity.getName ());
		return cities;
	}

	Countries findCountries (Class<?> sClass, String name) {
		Countries countries;
		try {
			countries = entityManager.createQuery ("select c from Countries c where c.name=:name", Countries.class)
			                         .setParameter ("name", name)
			                         .getSingleResult ();
		} catch (NumberFormatException e) {
			throw new ConversionException (sClass, Cities.class, name, new ArgumentOfMethodNotValidException (Countries.class, name));
		} catch (NoResultException e) {
			throw new ConversionException (sClass, Cities.class, name, new EntityNotFoundException (" name = " + name, Countries.class));
		}
		return countries;
	}

	void uniqueCitiesName (Class<?> sClass, String name) {
		try {
			entityManager.createQuery ("select c from Cities c where c.name =:name ", Cities.class)
			             .setParameter ("name", name)
			             .getSingleResult ();

		} catch (NumberFormatException e) {
			throw new ConversionException (sClass, Cities.class, name, new ArgumentOfMethodNotValidException (Cities.class, name));
		} catch (NoResultException e) {
			return;
		}
		throw new ConversionException (sClass, Cities.class, name, new EntityAlreadyExistException (" name = " + name));
	}
}
