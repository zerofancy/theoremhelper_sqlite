package com.zerofancy.theoremhelper.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

 

public class PropertyConstants {

	private static Properties properties;

	

	private static void setProperty(){

		if (properties==null) {

			properties = new Properties();

			ClassLoader loader = Thread.currentThread().getContextClassLoader();

			try {
				if( new File("application.properties").exists()) {
					properties.load(new FileInputStream("application.properties"));
				}else {
					properties.load(loader.getResourceAsStream("application.properties"));
				}

			} catch (IOException e) {

				e.printStackTrace();

			}

		}

	}

	

	public static String getPropertiesKey(String key){

		if (properties==null) {

			setProperty();

		}

		return properties.getProperty(key, "default");

	}

}
