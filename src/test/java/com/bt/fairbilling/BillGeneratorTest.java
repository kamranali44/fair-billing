package com.bt.fairbilling;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BillGeneratorTest {

	private LogReader logReader;
	private BillGenerator billGenerator;
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

	@BeforeEach
	void setup() {
		logReader = new LogReaderImpl();
		billGenerator = new BillGenerator();
		System.setOut(new PrintStream(outContent));
	}

	@Test
	void testGenerateBill() {
		String[] logArray = new String[] { "14:02:03 ALICE99 Start", "14:02:05 CHARLIE End", "14:02:34 ALICE99 End",
				"14:02:58 ALICE99 Start", "14:03:02 CHARLIE Start", "14:03:33 ALICE99 Start", "14:03:35 ALICE99 End",
				"14:03:37 CHARLIE End", "14:04:05 ALICE99 End", "14:04:23 ALICE99 End", "14:04:41 CHARLIE Start" };
		List<String> logData = 	Arrays.asList(logArray);
		
		Map<String, UserSession> expectedResult = new HashMap<String, UserSession>();
		UserSession aliceSession = new UserSession(4, 240, new ArrayList<>());
		UserSession charlieSession = new UserSession(3, 37, new ArrayList<>());
		expectedResult.put("ALICE99", aliceSession);
		expectedResult.put("CHARLIE", charlieSession);
		
		Map<String, UserSession> actualResult = billGenerator.generateBill(logData);

		assertEquals(expectedResult.size(), actualResult.size());
		assertEquals(expectedResult, actualResult);
	}
	
	@Test
	void testGenerateBillInvalidStringLength() {
		String[] logArray = new String[] { "14:02:03 ALICE99 Start", "26:02:03 ALICE99 Start", "14:02:05 CHARLIE End", "14:02:34 ALICE99 End",
				"14:02:58 ALICE99 Start", "14:03:02 CHARLIE Start", "14:03:33 ALICE99 Start", "14:03:35 ALICE99 End",
				"14:03:37 CHARLIE End", "14:04:05 ALICE99 End", "14:04:23 ALICE99 End", "14:04:41 CHARLIE Start" };
		List<String> logData = 	Arrays.asList(logArray);
		
		Map<String, UserSession> expectedResult = new HashMap<String, UserSession>();
		UserSession aliceSession = new UserSession(4, 240, new ArrayList<>());
		UserSession charlieSession = new UserSession(3, 37, new ArrayList<>());
		expectedResult.put("ALICE99", aliceSession);
		expectedResult.put("CHARLIE", charlieSession);
		
		Map<String, UserSession> actualResult = billGenerator.generateBill(logData);

		assertEquals(expectedResult.size(), actualResult.size());
		assertEquals(expectedResult, actualResult);
	}
	
	@Test
	void testGenerateBillInvalidTime() {
		String[] logArray = new String[] { "14:02:03 ALICE99 Start", "14:02:03 ALICE99 Start bib uguyvdiscb", "14:02:05 CHARLIE End", "14:02:34 ALICE99 End",
				"14:02:58 ALICE99 Start", "14:03:02 CHARLIE Start", "14:03:33 ALICE99 Start", "14:03:35 ALICE99 End",
				"14:03:37 CHARLIE End", "14:04:05 ALICE99 End", "14:04:23 ALICE99 End", "14:04:41 CHARLIE Start" };
		List<String> logData = 	Arrays.asList(logArray);
		
		Map<String, UserSession> expectedResult = new HashMap<String, UserSession>();
		UserSession aliceSession = new UserSession(4, 240, new ArrayList<>());
		UserSession charlieSession = new UserSession(3, 37, new ArrayList<>());
		expectedResult.put("ALICE99", aliceSession);
		expectedResult.put("CHARLIE", charlieSession);
		
		Map<String, UserSession> actualResult = billGenerator.generateBill(logData);

		assertEquals(expectedResult.size(), actualResult.size());
		assertEquals(expectedResult, actualResult);
	}
	
	@Test
	void testGenerateBillEmptyUserId() {
		String[] logArray = new String[] { "14:02:03 ALICE99 Start", "14:02:03  Start", "14:02:05 CHARLIE End", "14:02:34 ALICE99 End",
				"14:02:58 ALICE99 Start", "14:03:02 CHARLIE Start", "14:03:33 ALICE99 Start", "14:03:35 ALICE99 End",
				"14:03:37 CHARLIE End", "14:04:05 ALICE99 End", "14:04:23 ALICE99 End", "14:04:41 CHARLIE Start" };
		List<String> logData = 	Arrays.asList(logArray);
		
		Map<String, UserSession> expectedResult = new HashMap<String, UserSession>();
		UserSession aliceSession = new UserSession(4, 240, new ArrayList<>());
		UserSession charlieSession = new UserSession(3, 37, new ArrayList<>());
		expectedResult.put("ALICE99", aliceSession);
		expectedResult.put("CHARLIE", charlieSession);
		
		Map<String, UserSession> actualResult = billGenerator.generateBill(logData);

		assertEquals(expectedResult.size(), actualResult.size());
		assertEquals(expectedResult, actualResult);
	}
	
	@Test
	void testGenerateBillInvalidUserId() {
		String[] logArray = new String[] { "14:02:03 ALICE99 Start", "14:02:03 ALIC?E.99 Start", "14:02:05 CHARLIE End", "14:02:34 ALICE99 End",
				"14:02:58 ALICE99 Start", "14:03:02 CHARLIE Start", "14:03:33 ALICE99 Start", "14:03:35 ALICE99 End",
				"14:03:37 CHARLIE End", "14:04:05 ALICE99 End", "14:04:23 ALICE99 End", "14:04:41 CHARLIE Start" };
		List<String> logData = 	Arrays.asList(logArray);
		
		Map<String, UserSession> expectedResult = new HashMap<String, UserSession>();
		UserSession aliceSession = new UserSession(4, 240, new ArrayList<>());
		UserSession charlieSession = new UserSession(3, 37, new ArrayList<>());
		expectedResult.put("ALICE99", aliceSession);
		expectedResult.put("CHARLIE", charlieSession);
		
		Map<String, UserSession> actualResult = billGenerator.generateBill(logData);

		assertEquals(expectedResult.size(), actualResult.size());
		assertEquals(expectedResult, actualResult);
	}
	
	@Test
	void testGenerateBillInvalidUserAction() {
		String[] logArray = new String[] { "14:02:03 ALICE99 Start", "14:02:03 ALICE99 Started", "14:02:05 CHARLIE End", "14:02:34 ALICE99 End",
				"14:02:58 ALICE99 Start", "14:03:02 CHARLIE Start", "14:03:33 ALICE99 Start", "14:03:35 ALICE99 End",
				"14:03:37 CHARLIE End", "14:04:05 ALICE99 End", "14:04:23 ALICE99 End", "14:04:41 CHARLIE Start" };
		List<String> logData = 	Arrays.asList(logArray);
		
		Map<String, UserSession> expectedResult = new HashMap<String, UserSession>();
		UserSession aliceSession = new UserSession(4, 240, new ArrayList<>());
		UserSession charlieSession = new UserSession(3, 37, new ArrayList<>());
		expectedResult.put("ALICE99", aliceSession);
		expectedResult.put("CHARLIE", charlieSession);
		
		Map<String, UserSession> actualResult = billGenerator.generateBill(logData);

		assertEquals(expectedResult.size(), actualResult.size());
		assertEquals(expectedResult, actualResult);
	}
	
	@Test
	void testPrintBill() {
		
		Map<String, UserSession> userSessions = new HashMap<String, UserSession>();
		UserSession aliceSession = new UserSession(4, 240, new ArrayList<>());
		UserSession charlieSession = new UserSession(3, 37, new ArrayList<>());
		userSessions.put("ALICE99", aliceSession);
		userSessions.put("CHARLIE", charlieSession);
		
		String expectedResult = "ALICE99 4 240\nCHARLIE 3 37\n";
		
		billGenerator.printBill(userSessions);
		String actualResult = outContent.toString();
		
		assertEquals(expectedResult, actualResult);
	}

}
