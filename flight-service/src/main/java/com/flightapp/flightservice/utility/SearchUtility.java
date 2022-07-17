package com.flightapp.flightservice.utility;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.flightapp.flightservice.constants.QueryOperatorEnum;

public class SearchUtility {
	
	private SearchUtility() {}
	
	public static final Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
	
	//public static final Pattern emailPattern =Pattern.compile("(\\w+?)(:|<|>)([a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$)");
	//public static final Pattern otherattern =Pattern.compile("(\\w+?)(:|<|>)([a-zA-Z0-9+_-])");
	public static final Pattern matchPattern =Pattern.compile("(\\w+?)(:|<|>|!|~)([a-zA-Z0-9+_.@-]*),?");
	//public static final Pattern matchPattern =Pattern.compile("(\\w+?)(:|<|>|!)([a-zA-Z0-9+_.-@]+),?");



	public static List<SearchCriteria> searchFilter(String search) {
		List<SearchCriteria> criteriaFilterList = new ArrayList<>();
	//	Matcher matcher = pattern.matcher(search + ",");
		Matcher matcher = matchPattern.matcher(search + ",");
		while (matcher.find()) {
			criteriaFilterList.add(SearchCriteria.builder().field(matcher.group(1))
					.operator(QueryOperatorEnum.fromSymbol(matcher.group(2))).value(matcher.group(3)).build());
		} 
 		return criteriaFilterList;

	}
	
	
	
//	public static void main(String[] args) {
//		String search="source:3,destination:2,scheduledfor#monday";
//		Matcher matcher = matchPattern.matcher(search+",");
//		while (matcher.find()) {
//			System.out.println(matcher.group(1));
//			System.out.println(matcher.group(2));
//			System.out.println(matcher.group(3));
//		}
//		System.out.println(matcher.find());
//
//	}

}
