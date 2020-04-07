package com.airport.util.converters.parent;

import com.airport.controller.request.change.CountriesUpdateRequest;
import com.airport.controller.request.create.CountriesSaveRequest;
import com.airport.controller.request.create.PassengerSaveRequest;
import com.airport.entity.Countries;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
// TODO add
public abstract class ConverterRequestCountries<S, T> extends EntityConverter<S, T> {

  protected Countries doConvert(Countries countries, CountriesSaveRequest entity) {
    countries.setName(entity.getName());
    countries.setLocation(entity.getLocation());
    countries.setPopulation(Long.valueOf(entity.getPopulation()));
    return countries;
  }

  protected Countries doConvert(Countries countries, CountriesUpdateRequest entity) {
      if (entity.getName()!=null) countries.setName(entity.getName());
      if (entity.getLocation()!=null) countries.setLocation(entity.getLocation());
      if (entity.getPopulation()!=null) countries.setPopulation(Long.valueOf(entity.getPopulation()));

    return countries;
  }
}
