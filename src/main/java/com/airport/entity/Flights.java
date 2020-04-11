package com.airport.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(exclude = {"discount", "id", "airplane", "departureAirport", "arriveAirport", "airlines"})
@ToString(exclude = {"discount", "airplane", "departureAirport", "arriveAirport", "airlines"})
@Entity
@Table(name = "flights")
public class Flights {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@Column(name = "flights_number", nullable = false, length = 50, unique = true)
	String fightsNumber;

	@Column(name = "departure_date", nullable = false)
	Date departureDate;

	@Column(name = "arrive_date", nullable = false)
	Date arriveDate;

	@Column(nullable = false)
	Double price;

	@Column
	Date changed;

	@OneToOne
	@JoinColumn(name = "airplane_id", nullable = false)
	Airplanes airplane;

	@OneToOne
	@JoinColumn(name = "departure_airport_id", nullable = false)
	Airports departureAirport;

	@OneToOne
	@JoinColumn(name = "arrive_airport_id", nullable = false)
	Airports arriveAirport;

	@OneToOne
	@JoinColumn(name = "airline_id", nullable = false)
	Airlines airlines;

	@JsonManagedReference
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinTable(name = "flights_discounts", joinColumns = @JoinColumn(name = "flights_id"), inverseJoinColumns = @JoinColumn(name = "discounts_id"))
	Set<Discounts> discount = Collections.emptySet ();
}
