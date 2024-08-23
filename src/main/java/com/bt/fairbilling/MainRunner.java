package com.bt.fairbilling;

import java.util.List;
import java.util.Map;

public class MainRunner {

	public static void main(String[] args) {

		if (args.length != 1) {
			System.err.println("Please provide log file. Code to run: java FairBilling <full-path-to-log-file>");
			return;
		}

		// Get the file location as first argument
		String fileLocation = args[0];

		// Read the log
		LogReader logReader = new LogReaderImpl();
		List<String> logData = logReader.read(fileLocation);

		// Generate the bill
		BillGenerator billGenerator = new BillGenerator();
		Map<String, UserSession> userSessions = billGenerator.generateBill(logData);
		billGenerator.printBill(userSessions);

	}

}
