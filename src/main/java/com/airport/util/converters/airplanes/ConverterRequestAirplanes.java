package com.airport.util.converters.airplanes;

import com.airport.controller.exceptions.ArgumentOfMethodNotValidException;
import com.airport.controller.exceptions.ConversionException;
import com.airport.controller.exceptions.EntityAlreadyExistException;
import com.airport.controller.exceptions.EntityNotFoundException;
import com.airport.controller.request.change.AirplanesUpdateRequest;
import com.airport.controller.request.create.AirplanesSaveRequest;
import com.airport.entity.Airplanes;
import com.airport.entity.Countries;
import com.airport.util.converters.EntityConverter;

import javax.persistence.NoResultException;

public abstract class ConverterRequestAirplanes<S, T> extends EntityConverter<S, T> {

	protected Airplanes doConvert (Airplanes airplanes, AirplanesSaveRequest entity) {
		airplanes.setBuilt(entity.getBuilt());
		airplanes.setFlightDuration(Long.valueOf (entity.getFlightDuration ()));
		airplanes.setModel(entity.getModel());
		airplanes.setSeats(Integer.valueOf (entity.getSeats ()));
		airplanes.setRow (Integer.valueOf (entity.getRow ()));
		airplanes.setComfortSeats (Integer.valueOf (entity.getComfortSeats ()));
		airplanes.setComfortRow (Integer.valueOf (entity.getComfortRow ()));
		airplanes.setBusinessRow (Integer.valueOf (entity.getBusinessSeats ()));
		airplanes.setBusinessRow (Integer.valueOf (entity.getBusinessRow ()));
		return airplanes;
	}

	protected Airplanes doConvert (Airplanes airplanes, AirplanesUpdateRequest entity) {
		if (entity.getBuilt () != null) airplanes.setBuilt (entity.getBuilt ());
		if (entity.getFlightDuration () != null) airplanes.setFlightDuration (Long.valueOf (entity.getFlightDuration ()));
		if (entity.getModel () != null) airplanes.setModel (entity.getModel ());
		if (entity.getSeats () != null) airplanes.setSeats (Integer.valueOf(entity.getSeats()));
		if (entity.getRow()!=null)airplanes.setRow (Integer.valueOf (entity.getRow ()));
		if (entity.getComfortSeats()!=null)airplanes.setComfortSeats (Integer.valueOf (entity.getComfortSeats ()));
		if (entity.getComfortRow()!=null)airplanes.setComfortRow (Integer.valueOf (entity.getComfortRow ()));
		if (entity.getBusinessSeats()!=null)airplanes.setBusinessRow (Integer.valueOf (entity.getBusinessSeats ()));
		if (entity.getBusinessRow()!=null)airplanes.setBusinessRow (Integer.valueOf (entity.getBusinessRow ()));
		return airplanes;
	}

	Countries findCountries (Class<?> sClass, String country) {
		Countries countries;
		try {
			countries = entityManager.createQuery ("select c from Countries c where c.name=:name", Countries.class)
			                         .setParameter ("name", country)
			                         .getSingleResult ();
		} catch (NumberFormatException e) {
			throw new ConversionException (sClass, Airplanes.class, country, new ArgumentOfMethodNotValidException (Countries.class,country));
		} catch (NoResultException e) {
			throw new ConversionException (sClass, Airplanes.class, country,
                    new EntityNotFoundException (" name = " + country, Countries.class));
		}
		return countries;
	}


	void isUniqueModelAirplanes(Class<?> sClass, String model){
		try {
			entityManager.createQuery ("select c from Airplanes c where c.model =:model", Airplanes.class)
			             .setParameter ("model", model)
			             .getSingleResult ();

		} catch (NumberFormatException e) {
			throw new ConversionException(sClass, Airplanes.class, model,
					new ArgumentOfMethodNotValidException ("model = " + model));
		} catch (NoResultException e) {
		   return;
		}
		throw new ConversionException(sClass, Airplanes.class, model,
				new EntityAlreadyExistException (Airplanes.class,"model = "+model));

	}
}
