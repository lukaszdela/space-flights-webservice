package eu.lukks.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import eu.lukks.domain.Country;
import eu.lukks.domain.CountryDto;
import eu.lukks.service.ICountryService;

@RestController
@RequestMapping("/country")
public class CountryController {

	private ICountryService iCountryService;

	@Autowired
	public CountryController(ICountryService iCountryService) {
		super();
		this.iCountryService = iCountryService;
	}

	@GetMapping("/list")
	public List<CountryDto> getCountryList() {
		List<Country> countries = iCountryService.getCountryList();

		return countries.stream().map(c -> new CountryDto(c.getId(), c.getName())).collect(Collectors.toList());
	}

	@ExceptionHandler(Exception.class)
	public void handleException(final Exception e) {
	}

}
