package com.airline.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;


import java.sql.Timestamp;
/*@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)*/

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@EqualsAndHashCode
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Passengers {
	Long id;
	String name;
	String secondName;
	String login;
	String password;
	Long country;
	Timestamp created;
	Timestamp changed;
	Timestamp birthDate;
 // void test() {Passengers passengers = Passengers.builder ().id(1L).name("sad").build (); }
}