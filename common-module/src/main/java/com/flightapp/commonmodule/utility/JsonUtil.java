package com.flightapp.commonmodule.utility;

import com.google.gson.Gson;

public class JsonUtil {
	private JsonUtil() {}
	
	/**
	 * To convert Object to json string.
	 * @param aClass
	 * @return
	 */
	public static String toJson(Object aClass) {
		return new Gson().toJson(aClass);	
	}
	
	/**
	 * To convert json string to class object.
	 * @param jsonString
	 * @param aClass
	 * @return 
	 * @return
	 */
	public static <T> T toObject(String jsonString,Class<?> aClass) {
		return (T) new Gson().fromJson(jsonString, aClass);
		
	}

}
