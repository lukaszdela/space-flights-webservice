package eu.lukks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eu.lukks.domain.Tourist;

@Repository
public interface TouristRepository extends JpaRepository<Tourist, Long>{

}
