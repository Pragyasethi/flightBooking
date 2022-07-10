//package com.flightapp.flightservice.model;
//
//import java.time.LocalTime;
//
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.Table;
//
//import org.hibernate.annotations.OnDelete;
//import org.hibernate.annotations.OnDeleteAction;
//
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//@Entity
//@Table(name = "f_flight_schedule")
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
//public class FlightSchedule {
//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
//	private Long id;
//	@ManyToOne
//	@JoinColumn(name = "source", nullable = false)
//	@OnDelete(action = OnDeleteAction.CASCADE)
//	private Airport source;
//	@ManyToOne
//	@JoinColumn(name = "destination", nullable = false)
//	@OnDelete(action = OnDeleteAction.CASCADE)
//	private Airport destination;
//	private LocalTime deptTime;
//	private LocalTime arrTime;
//	private String scheduledfor;
//
//}
