package com.trieutruong.projectzero.response;

import java.util.List;

import com.trieutruong.projectzero.model.GeneralModelResponse;
import com.trieutruong.projectzero.model.TicketInfo;

public class TimezoneResponse extends GeneralModelResponse {
	private List<String> timezones;

	public TimezoneResponse(String msg, List<String> timezones) {
		super.setStatus(Boolean.TRUE.toString());
		super.setMsg(msg);
		this.setTimezones(timezones);
	}

	public List<String> getTimezones() {
		return timezones;
	}

	public void setTimezones(List<String> timezones) {
		this.timezones = timezones;
	}

}
