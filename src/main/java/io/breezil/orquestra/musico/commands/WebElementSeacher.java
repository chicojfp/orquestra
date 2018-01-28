package io.breezil.orquestra.musico.commands;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

public class WebElementSeacher {
	
	@SuppressWarnings("serial")
	public List<WebElementInfo> loadWEInfos(String fileName) {
		List<WebElementInfo> data = new ArrayList<>();
		try {
			JsonReader reader = new JsonReader(new FileReader(fileName));
			Type gsonType = new TypeToken<List<WebElementInfo>>(){}.getType();
			data = new Gson().fromJson(reader, gsonType);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return data;
	}

}
