package com.bt.fairbilling;

import java.time.LocalTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserSession {
	private int count;
	private long duration;
	private List<LocalTime> startTimes;

	public void incrementCount() {
		this.count++;
	}

	public void addToDuration(long duration) {
		this.duration += duration;
	}
}
