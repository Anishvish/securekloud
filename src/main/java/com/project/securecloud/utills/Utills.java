package com.project.securecloud.utills;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class Utills {

	// private constructor
	Utills() {

	}

	@SuppressWarnings("unchecked")
	public static List<Map<String, Object>> fetchDataFromXML(byte[] byteArray) throws IOException {

		return new XmlMapper().readValue(byteArray, List.class);

	}
}
