package com.bt.fairbilling;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.Test;

class MainRunnerTest {

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

	@Test
	void testMainNoFileProvided() {
		String expectedOutput = "Please provide log file. Code to run: java FairBilling <full-path-to-log-file>";

		String[] args = new String[0];
		System.setErr(new PrintStream(outContent));

		MainRunner.main(args);

		String actualOutput = outContent.toString();

		assertEquals(expectedOutput.trim(), actualOutput.trim());
	}

	@Test
	void testMainFileProvided() {
		String expectedResult = "ALICE99 4 240\nCHARLIE 3 37\n";

		String[] args = { "src/test/resources/log.txt" };
		System.setOut(new PrintStream(outContent));
		MainRunner.main(args);

		String actualResult = outContent.toString();

		assertEquals(expectedResult, actualResult);
	}

}
