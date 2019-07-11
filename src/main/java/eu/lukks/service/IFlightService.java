package eu.lukks.service;

import java.util.List;

import eu.lukks.domain.Flight;

public interface IFlightService {

	List<Flight> getFlightList();

	Flight getFlightById(Long id);

	void deleteFlight(Flight flight);

	void saveFlight(Flight flight);

}
