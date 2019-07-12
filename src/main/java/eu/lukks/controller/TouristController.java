package eu.lukks.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eu.lukks.domain.Flight;
import eu.lukks.domain.Tourist;
import eu.lukks.domain.TouristDto;
import eu.lukks.service.ICountryService;
import eu.lukks.service.IFlightService;
import eu.lukks.service.ITouristService;

@RestController
@RequestMapping("/tourist")
public class TouristController {

	private ICountryService iCountryService;
	private ITouristService iTouristService;
	private IFlightService iFlightService;

	@Autowired
	public TouristController(ICountryService iCountryService, ITouristService iTouristService,
			IFlightService iFlightService) {
		super();
		this.iCountryService = iCountryService;
		this.iTouristService = iTouristService;
		this.iFlightService = iFlightService;
	}

	@GetMapping("/list")
	public List<TouristDto> getTouristDtoList() {
		List<Tourist> tourists = iTouristService.getTouristList();
		List<TouristDto> touristsDtos = new ArrayList<TouristDto>();

		for (Tourist tourist : tourists) {
			TouristDto touristDto = new TouristDto();
			touristDto.setId(tourist.getId());
			touristDto.setName(tourist.getName());
			touristDto.setSurname(tourist.getSurname());
			touristDto.setGender(tourist.getGender().getName());
			if (tourist.getCountry() != null)
				touristDto.setCountry(tourist.getCountry().getName());
			if (tourist.getNotes() != null)
				touristDto.setNotes(tourist.getNotes());
			touristDto.setDateBirth(tourist.getDateBirth());
			if (tourist.getFlights() == null)
				touristDto.setNumberOfFlights(0);
			else
				touristDto.setNumberOfFlights(tourist.getFlights().size());
			touristsDtos.add(touristDto);
		}
		return touristsDtos;

	}

	@PostMapping("/create")
	public void addNewTourist(@ModelAttribute Tourist tourist,
			@RequestParam(value = "countryId", required = false) Long countryId,
			@RequestParam(value = "flight", required = false) List<Long> flightIds) {

		if (countryId != null)
			tourist.setCountry(iCountryService.getCountryById(countryId));

		iTouristService.saveTourist(tourist);

		if (flightIds != null) {
			for (Long id : flightIds) {
				Flight flight = iFlightService.getFlightById(id);
				if (flight != null) {
					Set<Tourist> tourists = flight.getTourists();
					tourists.add(tourist);
					flight.setTourists(tourists);
					iFlightService.saveFlight(flight);
				}

			}
		}

	}

	@GetMapping("/delete/{id}")
	public void deleteTourist(@PathVariable("id") Long id) {
		Tourist tourist = iTouristService.getTouristById(id);

		if (tourist != null) {
			iTouristService.deleteTourist(tourist);
		}
	}

	@GetMapping("/delete/flight/{touristId}/{flightId}")
	public void deleteFlightForTourist(@PathVariable("touristId") Long touristId,
			@PathVariable("flightId") Long flightId) {
		Tourist tourist = iTouristService.getTouristById(touristId);
		Flight flight = iFlightService.getFlightById(flightId);
		iTouristService.deleteTouristFromFlight(tourist, flight);
	}

	@GetMapping("/add/flight/{touristId}/{flightId}")
	public void addFlightToTourist(@PathVariable("touristId") Long touristId, @PathVariable("flightId") Long flightId) {
		Tourist tourist = iTouristService.getTouristById(touristId);
		Flight flight = iFlightService.getFlightById(flightId);
		if (iTouristService.checkAvalibleSeatForFlight(flight)) {
			flight.getTourists().add(tourist);
			iFlightService.saveFlight(flight);
		}
	}

	@ExceptionHandler(Exception.class)
	public void handleException(final Exception e) {
	}

}
