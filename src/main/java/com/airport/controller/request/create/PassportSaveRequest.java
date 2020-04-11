package com.airport.controller.request.create;

import com.airport.entity.PassportsTypes;
import com.airport.util.validation.FieldValid;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PassportSaveRequest {

	@FieldValid(max = 18)
	@Pattern(regexp = "^[\\d]+$", message = "example : 654676")
	String series;

	@FieldValid(max = 18)
	@Pattern(regexp = "^[\\d]+$", message = "example : 20212223")
	String number;

	@NotNull
	PassportsTypes types;

	@FieldValid (min = 1,max = 18)
	@Pattern(regexp = "^[\\d]+$", message = "example : 123")
	String passengerId;


}
