package com.bt.fairbilling;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LogReaderTest {

	private LogReader logReader;

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

	@BeforeEach
	void setup() {
		logReader = new LogReaderImpl();
		System.setErr(new PrintStream(outContent));
	}

	@Test
	void testFileDoesNotExist() {
		String testLogPath = "randomFilePath.txt";
		String expectedOutput = "randomFilePath.txt (No such file or directory)";

		List<String> logData = logReader.read(testLogPath);
		String actualOutput = outContent.toString();

		assertEquals(expectedOutput.trim(), actualOutput.trim());
		assertEquals(0, logData.size());
	}

	@Test
	void testLoadFile() {
		String testLogPath = "src/test/resources/log.txt";

		String expectedOutput = "";
		int expectedLogSize = 11;

		List<String> logData = logReader.read(testLogPath);
		String actualOutput = outContent.toString();

		assertEquals(expectedOutput.trim(), actualOutput.trim());
		assertEquals(expectedLogSize, logData.size());
	}

}
