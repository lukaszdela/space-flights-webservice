package eu.lukks.domain;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TouristDto {

	private Long id;
	private String name;
	private String surname;
	private String gender;
	private String country;
	private String notes;
	private LocalDate dateBirth;
	private Integer numberOfFlights;

}
