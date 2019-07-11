package eu.lukks.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.lukks.domain.Country;
import eu.lukks.repository.CountryRepository;

@Service
public class CountryService implements ICountryService {

	private CountryRepository countryRepository;

	@Autowired
	public CountryService(CountryRepository countryRepository) {
		super();
		this.countryRepository = countryRepository;
	}

	@Override
	public List<Country> getCountryList() {
		return countryRepository.findAll();
	}

	@Override
	public Country getCountryById(Long id) {
		return countryRepository.findById(id).orElse(null);
	}

}
