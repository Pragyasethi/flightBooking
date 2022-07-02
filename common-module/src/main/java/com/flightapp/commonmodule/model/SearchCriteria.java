package com.flightapp.commonmodule.model;

import java.util.List;

import com.flightapp.commonmodule.constants.QueryOperatorEnum;

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
