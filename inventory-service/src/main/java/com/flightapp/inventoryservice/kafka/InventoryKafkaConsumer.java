package com.flightapp.inventoryservice.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.flightapp.commonmodule.utility.JsonUtil;
import com.flightapp.inventoryservice.dto.InventoryRequest;
import com.flightapp.inventoryservice.service.InventoryService;

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
	@KafkaListener(topics = "inventory-service", groupId = "group_id")
	public void consume(String recordConsumed) {
			InventoryRequest request = JsonUtil.toObject(recordConsumed, InventoryRequest.class);
			inventorySerivce.saveInventoryDetails(request);
			System.out.println("message = " + recordConsumed);
	}

}
