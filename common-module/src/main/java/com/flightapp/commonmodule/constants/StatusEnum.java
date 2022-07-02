package com.flightapp.commonmodule.constants;

import java.util.HashMap;
import java.util.Map;

public enum StatusEnum {
	INACTIVE("Inactive", 0), ACTIVE("Active", 1), BLOCKED("Blocked", 2), CANCELLED("Cancelled", 3);

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

	public static StatusEnum fromStatus(int status) {
		StatusEnum statusEnum = STATUS_MAP.get(status);
		if (statusEnum == null) {
			throw new IllegalArgumentException("No operator found with symbol: " + status);
		}
		return statusEnum;
	}

}
