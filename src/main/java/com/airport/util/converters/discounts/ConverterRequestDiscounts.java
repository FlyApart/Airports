package com.airport.util.converters.discounts;

import com.airport.controller.exceptions.ArgumentOfMethodNotValidException;
import com.airport.controller.exceptions.ConversionException;
import com.airport.controller.exceptions.EntityAlreadyExistException;
import com.airport.controller.exceptions.EntityNotFoundException;
import com.airport.controller.request.change.DiscountsUpdateRequest;
import com.airport.controller.request.create.DiscountsSaveRequest;
import com.airport.entity.Discounts;
import com.airport.entity.Flights;
import com.airport.util.converters.EntityConverter;

import javax.persistence.NoResultException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class ConverterRequestDiscounts<S, T> extends EntityConverter<S, T> {

	protected Discounts doConvert (Discounts discounts, DiscountsSaveRequest entity) {
		discounts.setCost (Double.valueOf (entity.getCost ()));
		discounts.setTitle (entity.getTitle ());
		return discounts;
	}

	protected Discounts doConvert (Discounts discounts, DiscountsUpdateRequest entity) {
		if (entity.getCost () != null) discounts.setCost (Double.valueOf (entity.getCost ()));
		if (entity.getTitle () != null) discounts.setTitle (entity.getTitle ());
		return discounts;
	}

	Set<Flights> findFlights (Class<?> sClass, Set<Long> setFlights) {

		List<Flights> flightsList = entityManager.createQuery ("from Flights f where f.id in (:id)", Flights.class)
		                                .setParameter ("id", setFlights)
		                                .getResultList ();
		if (setFlights.size ()!=flightsList.size ()){
			throw new ConversionException (sClass, Discounts.class, setFlights,
					new EntityNotFoundException (Discounts.class, setFlights));
		}
		return new HashSet<> (flightsList);

	}
	void uniqueDiscountsName (Class<?> sClass, String title) {
		try {
			entityManager.createQuery ("select c from Discounts c where c.title =:title ", Discounts.class)
			             .setParameter ("title", title)
			             .getSingleResult ();

		} catch (NumberFormatException e) {
			throw new ConversionException (sClass, Discounts.class, title, new ArgumentOfMethodNotValidException (Discounts.class, title));
		} catch (NoResultException e) {
			return;
		}
		throw new ConversionException (sClass, Discounts.class, title, new EntityAlreadyExistException (Discounts.class, " title = " + title));
	}
}
