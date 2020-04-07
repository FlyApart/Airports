package com.airport.controller.request.create;

import com.airport.controller.request.change.FlightUpdateRequest;
import com.airport.util.validation.FieldValid;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.DynamicUpdate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode (exclude = {"discount"})
@ToString (exclude = {"discount"})
@FieldDefaults(level = AccessLevel.PRIVATE)
@DynamicUpdate
public class FlightSaveRequest extends FlightUpdateRequest {

	@FieldValid
	String fightsNumber;



}