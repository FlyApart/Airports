package com.airport.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"id", "passports", "ticket", "cities", "role"})
@ToString(exclude = {"cities", "passports", "ticket", "role"})
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "passenger")
public class Passenger {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	Long id;

	@Column(nullable = false, length = 50)
	String name;

	@Column(name = "surname", nullable = false, length = 50)
	String secondName;

	@Column(name = "login", unique = true, nullable = false, length = 50)
	String login;

	@Column(nullable = false, length = 50)
	String password;

	@Column
	Date created;

	@Column
	Date changed;

	@Column(name = "date_birth")
	Date birthDate;

	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false, length = 50)
	Status status = Status.NOT_ACTIVE;

	@OneToOne
	@JoinColumn(name = "cities_id")
	Cities cities;

	@JsonManagedReference
	@OneToMany(fetch = FetchType.EAGER, targetEntity = Passports.class, mappedBy = "passengerId", cascade = CascadeType.ALL)
	Set<Passports> passports = Collections.emptySet ();

	@JsonManagedReference
	@OneToMany(fetch = FetchType.EAGER, targetEntity = Tickets.class, mappedBy = "passengerId", cascade = CascadeType.ALL)
	Set<Tickets> ticket = Collections.emptySet ();

	@JsonManagedReference
	@ManyToMany(fetch = FetchType.EAGER,cascade = {CascadeType.MERGE, CascadeType.REFRESH})
	@JoinTable(name = "passengers_roles",
			joinColumns = {@JoinColumn(name = "passenger_id", referencedColumnName = "id")},
			inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
	Set<Role> role= Collections.emptySet ();

	@JsonIgnore
	@Column (name = "code_activation")
	String activationCode;

}
