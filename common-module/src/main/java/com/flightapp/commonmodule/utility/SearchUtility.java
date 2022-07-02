package com.flightapp.commonmodule.utility;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.flightapp.commonmodule.constants.QueryOperatorEnum;
import com.flightapp.commonmodule.model.SearchCriteria;

public class SearchUtility {
	
	private SearchUtility() {}
	
	public static final Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
	
	public static List<SearchCriteria> searchFilter(String search) {
		List<SearchCriteria> criteriaFilterList = new ArrayList<>();
		Matcher matcher = pattern.matcher(search + ",");
		while (matcher.find()) {
			criteriaFilterList.add(SearchCriteria.builder().field(matcher.group(1))
					.operator(QueryOperatorEnum.fromSymbol(matcher.group(2))).value(matcher.group(3)).build());
		}
		return criteriaFilterList;

	}

}
