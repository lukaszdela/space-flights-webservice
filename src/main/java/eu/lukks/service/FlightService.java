package eu.lukks.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.lukks.domain.Flight;
import eu.lukks.domain.Tourist;
import eu.lukks.repository.FlightRepository;

@Service
public class FlightService implements IFlightService {

	private FlightRepository flightRepository;

	@Autowired
	public FlightService(FlightRepository flightRepository) {
		super();
		this.flightRepository = flightRepository;
	}

	@Override
	public List<Flight> getFlightList() {
		return flightRepository.findAll();
	}

	@Override
	public Flight getFlightById(Long id) {
		return flightRepository.findById(id).orElse(null);
	}

	@Override
	public void saveFlight(Flight flight) {
		flightRepository.save(flight);
	}

	@Override
	public void deleteFlight(Flight flight) {
		Set<Tourist> tourists = flight.getTourists();
		tourists.removeAll(tourists);
		flightRepository.save(flight);
		flightRepository.delete(flight);
	}

}
