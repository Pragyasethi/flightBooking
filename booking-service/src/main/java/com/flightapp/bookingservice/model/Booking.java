package com.flightapp.bookingservice.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "f_booking")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Booking {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(nullable = false,unique = true)
	private String pnrNumber;
	@Column(nullable = false)
	private String email;
	@Column(nullable = false)
	private Long flightId;
	private LocalDateTime bookingDate;
	private Integer noOfSeats;
	@OneToMany(cascade = CascadeType.ALL)
	private List<PassengerDetails> passengerDetails;
	@Column(columnDefinition = "int default 1")
	@Builder.Default
	private Integer status=1;

}
