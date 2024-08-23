package com.bt.fairbilling;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class BillGenerator {

	private static final String TIME_STAMP_FORMATTER = "HH:mm:ss";
	private static final String EMPTY_SPACE = " ";
	private static final String SESSION_START = "Start";
	private static final String SESSION_STOP = "End";
	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_STAMP_FORMATTER);

	public Map<String, UserSession> generateBill(List<String> logData) {

		Map<String, UserSession> userSessions = new HashMap<>();

		LocalTime earliestTimeStamp = null;
		LocalTime latestTimeStamp = null;

		for (String logLine : logData) {

			String[] logLineParts = logLine.split(EMPTY_SPACE);

			if (invalidEntry(logLineParts)) {
				continue;
			}

			LocalTime currentLogTimeStamp = LocalTime.parse(logLineParts[0], DATE_TIME_FORMATTER);

			String userId = logLineParts[1];

			String userAction = logLineParts[2];

			if (earliestTimeStamp == null || currentLogTimeStamp.isBefore(earliestTimeStamp))
				earliestTimeStamp = currentLogTimeStamp; // update earliest timestamp

			if (latestTimeStamp == null || currentLogTimeStamp.isAfter(latestTimeStamp))
				latestTimeStamp = currentLogTimeStamp; // update latest timestamp

			// get the existing userSession or a new one, if it's the first time
			UserSession userSession = userSessions.getOrDefault(userId, new UserSession(0, 0, new ArrayList<>()));

			computeSessionTime(userAction, userSession, currentLogTimeStamp, earliestTimeStamp);

			userSessions.put(userId, userSession);
		}

		fixSessionNoEndTime(userSessions, latestTimeStamp);

		return userSessions;
	}

	private void fixSessionNoEndTime(Map<String, UserSession> userSessions, LocalTime latestTimeStamp) {
		for (Map.Entry<String, UserSession> entry : userSessions.entrySet()) {
			UserSession session = entry.getValue();
			for (LocalTime startTime : session.getStartTimes()) {
				long duration = Duration.between(startTime, latestTimeStamp).toSeconds();
				session.addToDuration(duration);
			}
			session.getStartTimes().clear(); // Clear unmatched starts, already handled
		}
	}

	private void computeSessionTime(String userAction, UserSession userSession, LocalTime currentLogTimeStamp,
			LocalTime earliestTimeStamp) {

		if (userAction.equals(SESSION_START)) {
			userSession.getStartTimes().add(currentLogTimeStamp);
			userSession.incrementCount();
		} else {
			long duration;
			if (!userSession.getStartTimes().isEmpty()) {
				// End time is latest time in the log
				LocalTime startTime = userSession.getStartTimes().remove(userSession.getStartTimes().size() - 1);
				duration = Duration.between(startTime, currentLogTimeStamp).toSeconds();

			} else {
				// No matching start, use earliest time
				duration = Duration.between(earliestTimeStamp, currentLogTimeStamp).toSeconds();
				userSession.incrementCount();
			}
			userSession.addToDuration(duration);
		}
	}

	private boolean invalidEntry(String[] logLineParts) {

		if (logLineParts.length != 3) {
			return true; // invalid entry, as not enough parameters to work with
		}

		try {
			LocalTime.parse(logLineParts[0], DATE_TIME_FORMATTER);
		} catch (DateTimeParseException ex) {
			return true; // invalid entry no timestamp
		}

		String userId = logLineParts[1];

		if (StringUtils.isBlank(userId) || !userId.matches("[A-Za-z0-9]+")) {
			return true; // user id empty or non alhpa-numeric - invalid entry
		}

		String userAction = logLineParts[2];

		if (!(userAction.equals(SESSION_START) || userAction.equals(SESSION_STOP))) {
			return true; // invalid entry - no user action
		}

		return false;
	}

	public void printBill(Map<String, UserSession> userSessions) {
		// print out total billing and all number of sessions and duration for each user

		userSessions.entrySet().forEach((entry -> System.out.printf("%s %d %d%s", entry.getKey(),
				entry.getValue().getCount(), entry.getValue().getDuration(), System.getProperty("line.separator"))));

	}

}