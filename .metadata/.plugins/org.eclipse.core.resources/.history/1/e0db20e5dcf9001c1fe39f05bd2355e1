package com.flightapp.flightservice.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "f_flight")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Flight {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(nullable = false,unique = true)
	private String flightNumber;
	@Column(nullable = false)
	private BigDecimal price;
	@Column(columnDefinition = "int default 1")
	@Builder.Default
	private Integer status=1;
	@ManyToOne
	@JoinColumn(name="airline_id",nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Airline airline;

}
