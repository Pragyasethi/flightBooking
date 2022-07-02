package com.bookmanagement.model;

import java.util.HashMap;
import java.util.Map;

public enum QueryOperatorEnum {

	EQUAL(":"),
	NOT_EQUALS("!"),
	GREATER_THAN(">"),
	LESS_THAN("<"),
	LIKE("%"),
	IN("IN");

	private String symbol =null;

	private QueryOperatorEnum(String symbol) {
		this.symbol = symbol;
	}

	private static final Map<String, QueryOperatorEnum> SYMBOL_MAP = new HashMap<>();
	static {
		for (QueryOperatorEnum type : values()) {
			SYMBOL_MAP.put(type.symbol, type);
		}
	}

	public String getSymbol() {
		return symbol;
	}
	
	public static QueryOperatorEnum fromSymbol(String symbol) {
		QueryOperatorEnum operator = SYMBOL_MAP.get(symbol);
		if(operator == null) {
			throw new IllegalArgumentException("No operator found with symbol: " +symbol);
		}
		return operator;
	}

}
