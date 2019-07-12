package eu.lukks.service;

import java.util.List;
import java.util.Set;

import eu.lukks.domain.Flight;
import eu.lukks.domain.Tourist;

public interface IFlightService {

	List<Flight> getFlightList();

	Flight getFlightById(Long id);

	void deleteFlight(Flight flight);

	void saveFlight(Flight flight);

	Set<Tourist> getFlightTouristListByFlightId(Long id);

}
