package com.bookmanagement.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class BookSearchCriteria {
	private String field;
	private QueryOperatorEnum operator;
	private String value;
	private List<String> values;// Used in case of IN operator

}
