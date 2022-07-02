package com.flightapp.bookingservice.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "f_passenger_details")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PassengerDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String passengerName;
	private String gender;
	private Integer age;
	private String idProofNumber;

}
