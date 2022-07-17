package com.flightapp.bookingservice.utility;

import java.util.List;

import com.flightapp.bookingservice.constants.QueryOperatorEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SearchCriteria {
	private String field;
	private QueryOperatorEnum operator;
	private String value;
	private List<String> values;// Used in case of IN operator

}
