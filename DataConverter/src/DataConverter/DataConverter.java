package DataConverter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

/**
 * This class simply reads the string and converts the data. It sends out a result to a text file.
 * This was required of my work at Volvo.
 * @author Marcus
 *
 */
public class DataConverter {
	private String entryIdentifier = "entry";
	private String outputDir;
	private File inputFile;
	private FileInputStream fis;
	private PrintStream fos;
	private final int minSize = 5;
	private final int maxSize = 8;

	public DataConverter() {
		//Null
	}
	
	/**
	 * This method sets up the input file.
	 */
	public void setInputFile(String filePath) {
		this.inputFile = new File(filePath);
	}
	
	/**
	 * This method sets up the file input stream reader.
	 */
	public void setupInputStream() {
		try {
			fis = new FileInputStream(inputFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method sets the output directory.
	 */
	public void setOutputDirectory(String dir) {
		this.outputDir = dir;
	}
	
	/**
	 * This uses jsoup to change the content of the tags	
	 * @return
	 */
	public String otherTry() {
		Document doc = null;
		String htmlContent = "";
		Scanner scanner = null;
		try {
			scanner = new Scanner(inputFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (scanner.hasNextLine()) {
			htmlContent += scanner.nextLine();
		}
		scanner.close();
		doc = Jsoup.parse(htmlContent, "UTF-8", Parser.xmlParser());
		Elements entries = doc.select(entryIdentifier);
		int i = 0;
		for (Element element : entries) {
			//Avoid tags with "N/A" in them
			if (element.text().contains("n") || element.text().toString().contains("a")) {
				i++;
				continue;
			}
			
			if (element.text().length() < minSize || element.text().length() > maxSize) {
				i++;
				continue;
			}
			String elementToChange = element.text();
			Scanner scan = new Scanner(elementToChange);
			String firstItem = "";
			String secondItem = "";
			while (scan.hasNext()) {
				firstItem = scan.next();
				secondItem = scan.next();
			}
			String firstItemFixed = firstItem.replaceAll("()", "");
			String secondItemFixed =  secondItem.replaceAll("[()]", "");
			i++;
			element.text(secondItemFixed+ " " + "(" + firstItemFixed + ")");
			scan.close();
		}
		return doc.toString();
	}
	
	/**
	 * This method prints the file somewhere.
	 * @param name
	 * @param html
	 */
	public void setupOutput(String name, String html) {
		File f = new File(name);
		try {
			fos = new PrintStream(f.getAbsolutePath());
			fos.print(html);
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	public static void main(String [] args) {
		DataConverter d = new DataConverter();
		Scanner reader = new Scanner(System.in);
		System.out.println("Enter input file directory: \n");
		String inputFile = reader.next();
		System.out.println("Almost there. Enter output file directory: \n");
		String outputFile = reader.next();
		System.out.print("Here goes nothing...");
		d.setInputFile(inputFile);
		d.setupInputStream();
		String html = d.otherTry(); 
		d.setupOutput(outputFile, html);
		reader.close();
		System.out.println("Done");
	}
	
	
}
