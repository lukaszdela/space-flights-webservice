package eu.lukks.service;

import java.util.List;

import eu.lukks.domain.Country;

public interface ICountryService {

	List<Country> getCountryList();

	Country getCountryById(Long id);

}
