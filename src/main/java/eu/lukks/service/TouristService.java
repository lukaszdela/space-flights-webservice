package eu.lukks.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.lukks.domain.Country;
import eu.lukks.domain.Flight;
import eu.lukks.domain.Gender;
import eu.lukks.domain.Tourist;
import eu.lukks.repository.CountryRepository;
import eu.lukks.repository.FlightRepository;
import eu.lukks.repository.TouristRepository;

@Service
public class TouristService implements ITouristService {

	private CountryRepository countryRepository;
	private TouristRepository touristRepository;
	private FlightRepository flightRepository;

	@Autowired
	public TouristService(CountryRepository countryRepository, TouristRepository touristRepository,
			FlightRepository flightRepository) {
		super();
		this.countryRepository = countryRepository;
		this.touristRepository = touristRepository;
		this.flightRepository = flightRepository;
	}

	@Override
	public List<Tourist> getTouristList() {
		return touristRepository.findAll();
	}

	@Override
	public Tourist getTouristById(Long id) {
		return touristRepository.findById(id).orElse(null);
	}

	@Override
	public void addNewTourist(String name, String surname, Gender gender, Long countryId, String notes,
			LocalDate dateBirth, Set<Flight> flights) {
		Country country = countryRepository.findById(countryId).orElse(null);
		Tourist tourist = new Tourist();
		tourist.setName(name);
		tourist.setSurname(surname);
		tourist.setGender(gender);
		tourist.setCountry(country);
		tourist.setNotes(notes);
		tourist.setDateBirth(dateBirth);
		tourist.setFlights(flights);
		touristRepository.save(tourist);
	}

	@Override
	public void deleteTourist(Tourist tourist) {
		Set<Flight> flights = tourist.getFlights();
		for (Flight flight : flights) {
			flight.getTourists().remove(tourist);
			flightRepository.save(flight);
		}
		tourist.getFlights().removeAll(flights);
		touristRepository.save(tourist);
		touristRepository.delete(tourist);

	}

	@Override
	public void deleteTouristFromFlight(Tourist tourist, Flight flight) {
		tourist.getFlights().remove(flight);
		touristRepository.save(tourist);
		flight.getTourists().remove(tourist);
		flightRepository.save(flight);

	}

	@Override
	public void saveTourist(Tourist tourist) {
		touristRepository.save(tourist);
	}

	@Override
	public Boolean checkAvalibleSeatForFlight(Flight flight) {
		if (flight.getTourists().size() < flight.getSeats())
			return true;
		else
			return false;
	}

}
