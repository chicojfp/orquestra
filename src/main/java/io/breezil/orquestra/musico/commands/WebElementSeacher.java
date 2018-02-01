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
	private List<WebElementInfo> weInfos;
	
	public WebElementSeacher(String fileName) {
		this.weInfos = this.loadWEInfos(fileName);
	}
	
	@SuppressWarnings("serial")
	private List<WebElementInfo> loadWEInfos(String fileName) {
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

	public WebElementInfo findItem(String name) {
		for (WebElementInfo webElementInfo : weInfos) {
			if (webElementInfo.getName().equals(name)) {
				return webElementInfo;
			}
		}
		return null;
	}

}
