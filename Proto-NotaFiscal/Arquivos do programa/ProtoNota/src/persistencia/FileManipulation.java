package persistencia;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileManipulation {
	public static void OverwriteThisToThat(String text, String fileName) {
		try (FileWriter writer = new FileWriter(fileName)) {
			writer.write(text);
			System.out.println(text + " -> was writen to -> " + fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void AppendThisToThat(String text, String fileName) {
		try (FileWriter writer = new FileWriter(fileName, true)) {
			writer.write(text);
			System.out.println(text + " -> was writen to -> " + fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String readFirstLineFrom(String fileName) {
		File myObj = new File(fileName);
		String data = "";
		try (Scanner myReader = new Scanner(myObj)) {
			data += myReader.nextLine();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return data;
	}
	
	public static String readFromThis(String fileName) {
		File myObj = new File(fileName);
		String data = "";
		try (Scanner myReader = new Scanner(myObj)) {
			while (myReader.hasNextLine()) {
				String tempData = myReader.nextLine();
				data.concat(tempData);
			} 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return data;
	}
	
	public static void DeleteFile(String fileName) {
		File myObj = new File(fileName);
		
		if (myObj.delete()) {
			System.out.println("deleted : " + fileName);
		} else {
			System.out.println("Did not delete: " + fileName);
		}
	}
	
	
}