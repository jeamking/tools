package com.demo.tools;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ho.yaml.Yaml;

public class Yaml2Properties {

	public static void main(String[] args) {
        try {
            ClassLoader cl = Yaml2Properties.class.getClassLoader();
			File yamlFile = new File(cl.getResource("").getPath().toString()
					+ "application.yaml");
			Map yamlMap = Yaml.loadType(yamlFile, HashMap.class);
			List<String> propertiesList = yamlMap2PropertiesList(yamlMap);
			for (String item: propertiesList) {
				System.out.println(item);
			}
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	private static List<String> yamlMap2PropertiesList(Map yamlMap){
		List<String> keyValueList = new ArrayList<String>();
		for (Object key : yamlMap.keySet()) {
			Object value = yamlMap.get(key);
			if (!(value instanceof Map)) {
				String newKey = key.toString()+"="+value.toString();
				keyValueList.add(newKey);
			}
		}
		
		for (Object key : yamlMap.keySet()) {
			Object value = yamlMap.get(key);
			if (value instanceof Map) {
				List<String> subKeyValueList = yamlMap2PropertiesList((Map)value);
				for (String subKeyValue: subKeyValueList) {
					String newKeyValue = key.toString()+"."+subKeyValue;
					keyValueList.add(newKeyValue);
				}
			}
		}
		
		return keyValueList;
	}
}
