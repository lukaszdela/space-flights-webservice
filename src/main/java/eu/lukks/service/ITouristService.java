package eu.lukks.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import eu.lukks.domain.Flight;
import eu.lukks.domain.Gender;
import eu.lukks.domain.Tourist;

public interface ITouristService {

	List<Tourist> getTouristList();

	Tourist getTouristById(Long id);

	void deleteTourist(Tourist tourist);

	void saveTourist(Tourist tourist);

	void addNewTourist(String name, String surname, Gender gender, Long countryId, String notes, LocalDate dateBirth,
			Set<Flight> flights);

	Boolean checkAvalibleSeatForFlight(Flight flight);

	void deleteTouristFromFlight(Tourist tourist, Flight flight);

}
