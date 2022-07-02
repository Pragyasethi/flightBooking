package com.flightapp.flightservice.model;

import javax.persistence.Column;
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
@Table(name = "f_airline")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Airline {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(nullable = false,unique = true)
	private String airlineName;
	private String airlineLogo;
	@Column(columnDefinition = "int default 1")
	@Builder.Default
	private Integer status=1;
}
