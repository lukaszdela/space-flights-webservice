package eu.lukks.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
import eu.lukks.domain.FlightDto;
import eu.lukks.domain.Tourist;
import eu.lukks.service.IFlightService;
import eu.lukks.service.ITouristService;

@RestController
@RequestMapping("/flight")
public class FlightController {

	private ITouristService iTouristService;
	private IFlightService iFlightService;

	@Autowired
	public FlightController(ITouristService iTouristService, IFlightService iFlightService) {
		super();
		this.iTouristService = iTouristService;
		this.iFlightService = iFlightService;
	}

	@GetMapping("/list")
	public List<FlightDto> getFlightDtoList() {
		List<Flight> flights = iFlightService.getFlightList();

		return flights.stream()
				.map(f -> new FlightDto(f.getId(), f.getOutlet(), f.getArrival(), f.getSeats(), f.getTicketPrice()))
				.collect(Collectors.toList());

	}

	@PostMapping("/create")
	public void addNewFlight(@ModelAttribute Flight flight,
			@RequestParam(value = "tourist", required = false) List<Long> touristsIds) {
		Set<Tourist> tourists = new HashSet<Tourist>();

		if (touristsIds != null) {
			for (Long id : touristsIds) {
				tourists.add(iTouristService.getTouristById(id));
			}
		}
		flight.setTourists(tourists);
		iFlightService.saveFlight(flight);
	}

	@GetMapping("/delete/{id}")
	public void deleteFlight(@PathVariable("id") Long id) {
		Flight flight = iFlightService.getFlightById(id);

		if (flight != null) {
			iFlightService.deleteFlight(flight);

		}

	}

	@GetMapping("/delete/tourist/{flightId}/{touristId}")
	public void deleteTouristFromFlight(@PathVariable("touristId") Long touristId,
			@PathVariable("flightId") Long flightId) {
		Tourist tourist = iTouristService.getTouristById(touristId);
		Flight flight = iFlightService.getFlightById(flightId);
		iTouristService.deleteTouristFromFlight(tourist, flight);
	}

	@GetMapping("/add/tourist/{flightId}/{touristId}")
	public void addTouristToFlight(@PathVariable("touristId") Long touristId, @PathVariable("flightId") Long flightId) {
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
