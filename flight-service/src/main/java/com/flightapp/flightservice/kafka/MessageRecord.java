package com.flightapp.flightservice.kafka;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageRecord implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String topic;
	private String key;
	private String message;

}
