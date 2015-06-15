package oop.ex6.main;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Sjavac {
	// the index of the file's path in the argument list
	private final static int FILE_PATH_INDEX = 0;

	public static void main(String[] args) throws IOException {

		File givenFile = new File(args[FILE_PATH_INDEX]);
		// the file lines in a list

		ArrayList<String> fileLines = (ArrayList<String>) Files.readAllLines(givenFile.toPath(),
				Charset.defaultCharset());
		
		//TODO tomorrow morning:
		//maybe in the fileLines to putt a null or "compiled line" in a line we went over????????
		//to speak with amit about all the todo's in the manager
		//to test the code i wrote last night
		//to look at point reduction on last year's exercises
		
	}
	


}
