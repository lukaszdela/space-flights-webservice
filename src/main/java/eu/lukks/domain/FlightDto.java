package eu.lukks.domain;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FlightDto {

	private Long id;
	private LocalDateTime outlet;
	private LocalDateTime arrival;
	private Integer seats;
	private Double ticketPrice;

}
