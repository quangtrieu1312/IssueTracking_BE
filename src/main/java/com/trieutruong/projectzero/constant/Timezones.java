package com.trieutruong.projectzero.constant;

import java.util.List;
import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

@Component
public class Timezones {
	
	private List<String> timezones;
	
	
	public List<String> getTimezones() {
		return timezones;
	}


	public void setTimezones(List<String> timezones) {
		this.timezones = timezones;
	}


	@PostConstruct
	private void init() {
		String[] validIDs = TimeZone.getAvailableIDs();
		for (String id : validIDs) {
			this.timezones.add(id);
		}
	}
}
