package com.bt.fairbilling;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LogReaderImpl implements LogReader {

	@Override
	public List<String> read(String fileLocation) {
		List<String> logData = new ArrayList<>();

		try (FileReader fileReader = new FileReader(fileLocation)) {
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			String line;

			while ((line = bufferedReader.readLine()) != null) {
				logData.add(line);
			}
		} catch (IOException ex) {
			System.err.println(ex.getMessage());
		}
		return logData;
	}
}
