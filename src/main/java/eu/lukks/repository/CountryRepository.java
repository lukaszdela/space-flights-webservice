package eu.lukks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eu.lukks.domain.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long>{

}
