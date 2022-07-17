package com.flightapp.inventoryservice.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.flightapp.inventoryservice.dto.InventoryRequest;
import com.flightapp.inventoryservice.service.InventoryService;
import com.flightapp.inventoryservice.utility.JsonUtil;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InventoryKafkaConsumer {
	
	private final InventoryService inventorySerivce;

//	public void consume(List<ConsumerRecord<String,String>> records) {
//		for(ConsumerRecord<String,String> record:records) {
//			InventoryRequest request = JsonUtil.toObject(record.value(), InventoryRequest.class);
//			inventorySerivce.saveInventoryDetails(request);
//			//System.out.println("message = " + message);
//		}
//	}
//	@KafkaListener(topics = "FlightToInventory", groupId = "group_id")
//	public void consumePostRequest(String recordConsumed) {
//			InventoryRequest request = JsonUtil.toObject(recordConsumed, InventoryRequest.class);
//			inventorySerivce.saveInventoryDetails(request);
//			System.out.println("message = " + recordConsumed);
//	}
	
	@KafkaListener(topics = "BookingToInventoryBook", groupId = "group_id")
	public void consumePutRequestForBooking(String recordConsumed) {
		System.out.println("message = " + recordConsumed);
			InventoryRequest request = JsonUtil.toObject(recordConsumed, InventoryRequest.class);
			inventorySerivce.updateSeatsAfterBooking(request);
	}
	
	@KafkaListener(topics = "BookingToInventoryCancel", groupId = "group_id")
	public void consumePutRequestForCancellation(String recordConsumed) {
		System.out.println("message = " + recordConsumed);
			InventoryRequest request = JsonUtil.toObject(recordConsumed, InventoryRequest.class);
			inventorySerivce.updateSeatsForCancellation(request);
	}

}
