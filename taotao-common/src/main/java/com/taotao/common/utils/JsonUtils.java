package com.taotao.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {
	
	public static String objectToJson(Object object) {
		ObjectMapper objectMapper = new ObjectMapper();
		String result = null;
		try {
			result = objectMapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return result;
	}
}
