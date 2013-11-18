/*******************************************************
 * Document Similarity
 * class Similartiy
 *
 * 
 *******************************************************/
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


public class Similarity {
	private HashMap<String, Integer> count;
	private ArrayList<String> words;
	private File file;
	private long lineNum;
	private long wordNum;
	//constructor with string as input 
	public Similarity(String str){
		if (str == null)
			throw new Error("no input!");
		//initiate original string list
		words = new ArrayList<String>();
		String[] s = str.split(" ");
		for (String string : s){
			if (string.equals("") || !(isWord(string)))
				continue;
			words.add(string);
		}
        //initiate hash map
		count = new HashMap<String, Integer>(10, 0.65f);
		countWord();
		//initiate counter
		lineNum = 0;
		wordNum = words.size();
	}
	//constructor with file name as input
	public Similarity(File filename){
		if (filename == null)
			throw new Error("no input file!");
		//initiate
		words = new ArrayList<String>();
		count = new HashMap<String, Integer>(10, 0.65f);
		file = filename;
		lineNum = 0;
		wordNum = 0;
		//get information from file
		readFile();
		countWord();
	}
	//count word frequency from original word array to hash table
	private void countWord(){
		if (words == null)
			throw new Error("no input!");
		for (String word: words){
			Integer freq = count.get(word);
			if (freq == null){
				freq = 1;
			}
			else
				if(freq + 1 > 0)
					freq++;
				else
					throw new Error("word frequency overflow!");
			//update key-value pair
			count.put(word, freq);
		}
	}
	//calculate enclidean norm with parameter
	public double euclideanNorm(HashMap<String, Integer> text){
		double result = 0;
		for (long i : text.values()){
			double tmp = result + i*i;
			if (tmp < result)
				throw new Error("euclideanNorm result overflow!");
			else
				result = tmp;
		}
		return Math.sqrt(result);
	}
	//calculate enclidean norm without parameter
	public double euclideanNorm(){
		double result = 0;
		for (long i : count.values()){//i should be long to prevent overflow
			double tmp = result + i*i;
			if (tmp < result)
				throw new Error("enclideanNorm result overflow!");
			else
				result = tmp;
		}
		return Math.sqrt(result);
	}
	//read from file to a string array
	private void readFile(){
		Scanner scanner = null;
		try {
			scanner = new Scanner(file);
			while (scanner.hasNextLine()){
				String line = scanner.nextLine();
				String[] wordOfLine = line.split("\\W");
				lineNum++;//accumulate line
				for (String word : wordOfLine){
					if (word.equals("") || !(isWord(word)))
						continue;
					words.add(word.toLowerCase());
					if (wordNum + 1 > wordNum)//in case of overflow
						wordNum++;//accumulate word
					else
						throw new Error(" word number overflow!");
				}
			}
		} catch (FileNotFoundException e) {
			throw new Error("cannot find file!");
		} finally{
			if (scanner != null)
				scanner.close();
		}

	}
	//number of lines
	public long numberOfLines(){
		return lineNum;
	}
	//number of words
	public long numOfWords(){
		return wordNum;
	}
	//number of uniq words
	public long numOfWordsNoDups(){
		return count.size();

	}
	// map with hashed words
	public HashMap<String, Integer> getMap(){
		return count;
	}
	//calculate dot product of current map to parameter
	public double dotProduct(HashMap<String, Integer> text){
		double result = 0;
		for (String s : count.keySet()){
			if (count.get(s) == null || text.get(s) == null)
				continue;
			result += count.get(s) * text.get(s);
		}
		return result;
	}
	//calculate distance of current map to parameter
	public double distance(HashMap<String, Integer> text){
		if (euclideanNorm() == 0 || euclideanNorm(text) == 0)
			throw new Error("one of the input is empty, cannot calculate distance!");
		double result = (dotProduct(text) / (euclideanNorm()) / euclideanNorm(text));
		return Math.acos(result);
	}
	//check if text is word
	private boolean isWord(String text) {
		return text.matches("[a-zA-Z]+");
	}
}
