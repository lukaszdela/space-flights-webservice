package eu.lukks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eu.lukks.domain.Flight;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long>{

}
