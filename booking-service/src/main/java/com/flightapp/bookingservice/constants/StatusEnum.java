package com.flightapp.bookingservice.constants;

import java.util.HashMap;
import java.util.Map;

public enum StatusEnum {
	INACTIVE("Inactive", 0), ACTIVE("Active", 1), BLOCKED("Blocked", 2), CANCELLED("Cancelled", 3), DELETED("Deleted",4);

	private final String statusName;
	private final int status;

	private StatusEnum(String statusName, int status) {
		this.statusName = statusName;
		this.status = status;
	}

	public String getStatusName() {
		return statusName;
	}

	public int getStatus() {
		return status;
	}

	private static final Map<Integer, StatusEnum> STATUS_MAP = new HashMap<>();
	static {
		for (StatusEnum type : values()) {
			STATUS_MAP.put(type.status, type);
		}
	}

	private static final Map<String, StatusEnum> STATUS_NAME_MAP = new HashMap<>();
	static {
		for (StatusEnum type : values()) {
			STATUS_NAME_MAP.put(type.name(), type);
		}
	}

	public static StatusEnum fromStatus(int status) {
		StatusEnum statusEnum = STATUS_MAP.get(status);
		if (statusEnum == null) {
			throw new IllegalArgumentException("No status found with symbol: " + status);
		}
		return statusEnum;
	}

	public static StatusEnum fromStatus(String statusName) {
		StatusEnum statusEnum = STATUS_NAME_MAP.get(statusName);
		if (statusEnum == null) {
			throw new IllegalArgumentException("No status found with name: " + statusName);
		}
		return statusEnum;
	}
}
