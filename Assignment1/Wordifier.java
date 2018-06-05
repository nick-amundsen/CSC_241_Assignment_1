/* 
 * Wordifier.java
 *
 * Implements methods for iteratively learning words from a 
 * character-segmented text file, and then evaluates how good they are
 *
 * Students may only use functionality provided in the packages
 *     java.lang
 *     java.util 
 *     java.io
 * 
 * Use of any additional Java Class Library components is not permitted 
 * 
 * PUT BOTH OF YOUR NAMES HERE
 * 
 *
 */

import java.util.LinkedList;
import java.util.Set;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.io.BufferedReader;

public class Wordifier {

	// loadSentences
    // Preconditions:
    //    - textFilename is the name of a plaintext input file
    // Postconditions:
    //  - A LinkedList<String> object is returned that contains
    //    all of the tokens in the input file, in order
    // Notes:
    //  - If opening any file throws a FileNotFoundException, print to standard error:
    //        "Error: Unable to open file " + textFilename
    //        (where textFilename contains the name of the problem file)
    //      and then exit with value 1 (i.e. System.exit(1))
	public static LinkedList<String> loadSentences( String textFilename ) {
		LinkedList<String> token_list = new LinkedList<String>(); //Create new linked list to hold the tokens from the text file.
    	try { //Try to open the text file, throw exception if unable to do so.
			FileReader fr = new FileReader(textFilename); //Opens a file reader to convert the text file into an array
			BufferedReader br = new BufferedReader(fr); //This reader is used to read the lines of the file
			String str;
			while((str = br.readLine())!=null){ //Continue while there are lines to read
		        str.toCharArray(); //Converts to char array to be put into the linked list
		        str = str.replaceAll(" ", "");
		        for(int i = 0; i < str.length(); i++) {
		        	token_list.add(Character.toString(str.charAt(i)));
		        }
		}
			fr.close();
		} catch (FileNotFoundException e) {
			System.out.println("Error: Unable to open file " + textFilename); //Prints out the error message if the file cannot be found
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return token_list; //Returns a linked list with all the tokens from the text file.
	}	
	
    // findNewWords
    // Preconditions:
    //    - bigramCounts maps bigrams to the number of times the bigram appears in the data
    //    - scores maps bigrams to its bigram product score 
    //    - countThreshold is a threshold on the counts
    //    - probabilityThreshold is a threshold on the bigram product score 
    // Postconditions:
    //    - A HashSet is created and returned, containing all bigrams that meet the following criteria
    //        1) the bigram is a key in bigramCounts
    //        2) the count of the bigram is >= countThreshold
    //        3) the score of the bigram is >= probabilityThreshold
    //      Formatting note: keys in the returned HashSet should include a space between the two tokens in the bigram
	public static HashSet<String> findNewWords( HashMap<String,Integer> bigramCounts, HashMap<String,Double> scores, int countThreshold, double probabilityThreshold ) {
		return null;
	}

    // resegment
    // Preconditions:
    //    - previousData is the LinkedList representation of the data
    //    - newWords is the HashSet containing the new words (after merging)
    // Postconditions:
    //    - A new LinkedList is returned, which contains the same information as
    //      previousData, but any pairs of words in the newWords set have been merged
    //      to a single entry (merge from left to right)
    //
    //      For example, if the previous linked list contained the following items:
    //         A B C D E F G H I
    //      and the newWords contained the entries "B C" and "G H", then the returned list would have 
    //         A BC D E F GH I
	public static LinkedList<String> resegment( LinkedList<String> previousData, HashSet<String> newWords ) {
		return null;
	}

    // computeCounts
    // Preconditions:
    //    - data is the LinkedList representation of the data
    //    - bigramCounts is an empty HashMap that has already been created
    // Postconditions:
    //    - bigramCounts maps each bigram appearing in the data to the number of times it appears
	public static void computeCounts(LinkedList<String> data, HashMap<String,Integer> bigramCounts ) {
		int i = 0;
		LinkedList<String> clone = new LinkedList<String>();
		clone = (LinkedList<String>) data.clone();
		while (i  < data.size()-1) {
			String bigram = clone.getFirst() +" "+ clone.get(1); //Creates a single token from two words adjacent to each other.
			incrementHashMap(bigramCounts, bigram, 1); //Uses the method given to us to add the token to the map and/or increase its value.
			clone.removeFirst();						   //removes the first token in the linked list so that it does not get used to make another bigram.
			i++;
		}
		return;
	}

	 // convertCountsToProbabilities 
    // Preconditions:
    //    - bigramCounts maps each bigram appearing in the data to the number of times it appears
    //    - bigramProbs is an empty HashMap that has already been created
    //    - leftUnigramProbs is an empty HashMap that has already been created
    //    - rightUnigramProbs is an empty HashMap that has already been created
    // Postconditions:
    //    - bigramProbs maps bigrams to their joint probability
    //        (where the joint probability of a bigram is the # times it appears over the total # bigrams)
    //    - leftUnigramProbs maps words in the first position to their "marginal probability"
    //    - rightUnigramProbs maps words in the second position to their "marginal probability"
    public static void convertCountsToProbabilities(HashMap<String,Integer> bigramCounts, HashMap<String,Double> bigramProbs, HashMap<String,Double> leftUnigramProbs, HashMap<String,Double> rightUnigramProbs ) {
    	for (String key : bigramCounts.keySet()) {
    		bigramProbs.put(key,(double)(bigramCounts.get(key)/ (double) bigramCounts.size())); //Puts the same key in the new hash set with a probability value instead of the count.
			
		}
    	HashMap<String,Integer> leftCounts= new HashMap<String,Integer>();
    	HashMap<String,Integer> rightCounts = new HashMap<String,Integer>();
    	//This loop will iterate through bigramCounts and create a variable for the left and right words in each bigram, place them in sperate lists, and then figure out the total count of each one.
    	for (String key : bigramCounts.keySet()) {
    		String bigramLeft = key; //Temporary string for the left token in the bigram.
    		String bigramRight = key; //Temporary string for the right token in the bigram.
    		bigramLeft = bigramLeft.replaceAll(" .*", ""); //Regular expression removes everything after whitespace, leaving just the first token.
    		bigramRight = bigramRight.replaceAll(".* ", ""); //Regular expression removes everything before whitespace, leaving just the second token.
    		if (!leftCounts.containsKey(bigramLeft)) { //Adds left token to "counts" hashmap only if there isn't one already there.
    			leftCounts.put(bigramLeft, 0);
    		} 
    		if (!rightCounts.containsKey(bigramRight)) { //Adds right token to "counts" hashmap only if there isn't one already there.
    			rightCounts.put(bigramRight, 0);
    		} else {
    			continue;
    		}
    		
    	}
    	for (String key : bigramCounts.keySet()) { //Iterate through the lists of single left and right tokens.
    		for (String keyLeft : leftCounts.keySet()) { //Iterate through the list of keys for the hashmap
    			if (key.matches(keyLeft+".*")) { //Checks to see if the key at that index matches the left token.
    				leftCounts.put(keyLeft,leftCounts.get(keyLeft)+bigramCounts.get(key));
    			} else {
    				continue;
    			}
    		}
    		for (String keyRight : rightCounts.keySet()) { //Iterate through the list of keys for the hashmap
    			if (key.matches(".*"+keyRight)) { //Checks to see if the key at that index matches the right token.
    				rightCounts.put(keyRight,rightCounts.get(keyRight)+bigramCounts.get(key));
    			} else {
    				continue;
    			}
    		}
    	}
    	for (String keyLeft : leftCounts.keySet()) {
    		leftUnigramProbs.put(keyLeft, (double) (leftCounts.get(keyLeft)/ (double) bigramCounts.size()));
    	}
    	for (String keyRight : rightCounts.keySet()) {
    		rightUnigramProbs.put(keyRight, (double) (rightCounts.get(keyRight)/ (double) bigramCounts.size()));
    	}
		return;
	}

	// getScores
    // Preconditions:
    //    - bigramProbs maps bigrams to to their joint probability
    //    - leftUnigramProbs maps words in the first position to their probability
    //    - rightUnigramProbs maps words in the first position to their probability
    // Postconditions:
    //    - A new HashMap is created and returned that maps bigrams to
    //      their "bigram product scores", defined to be P(w1|w2)P(w2|w1)
    //      The above product is equal to P(w1,w2)/sqrt(P_L(w1)*P_R(w2)), which 
    //      is the form you will want to use
	public static HashMap<String,Double> getScores( HashMap<String,Double> bigramProbs, HashMap<String,Double> leftUnigramProbs, HashMap<String,Double> rightUnigramProbs ) {
		HashMap<String,Double> bigramScores = new HashMap<String,Double>(); //This will be returned at end.
		//This loop will iterate through all the bigrams and calculate their probability score.
		for (String key : bigramProbs.keySet()) {
			String leftUnigram = null; //This will be the left unigram.
			String rightUnigram = null; //This will be the right unigram.
			//This loop will search through the left keys array to find the one that matches the left unigram in this bigram.
			for (String leftKey : leftUnigramProbs.keySet()) {
				if (key.startsWith(leftKey)) { //If the bigram starts with this left unigram .
					leftUnigram = leftKey;
				} else { //Continue searching.
					continue;
				}
			}
			//This loop will search through the right keys array to find the one that matches the right unigram in this bigram.
			for (String rightKey : rightUnigramProbs.keySet()) {
				if (key.startsWith(rightKey, leftUnigram.length()+1)) { //If the bigram ends with this right unigram.
					rightUnigram = rightKey;
				} else { //Continue searching.
					continue;
				}
			}
			bigramScores.put(key, bigramProbs.get(key)/Math.sqrt(leftUnigramProbs.get(leftUnigram)*rightUnigramProbs.get(rightUnigram))); //Places the correct key in place with the correct probability score.
		}
		return bigramScores;
	}

    // getVocabulary
    // Preconditions:
    //    - data is a LinkedList representation of the data
    // Postconditions:
    //    - A new HashMap is created and returned that maps words
    //      to the number of times they appear in the data
	public static HashMap<String,Integer> getVocabulary( LinkedList<String> data ) {
		return null;
	}

    // loadDictionary
    // Preconditions:
    //    - dictionaryFilename is the name of a dictionary file
    // Postconditions:
    //    - A new HashSet is created and returned that contains
    //      all unique words appearing in the dictionary
	public static HashSet<String> loadDictionary( String dictionaryFilename ) {
		HashSet<String> dictionarySet = new HashSet<String>(); //Create new hash set to hold the tokens from the dictionary text file.
    	try { //Try to open the text file, throw exception if unable to do so.
			FileReader fr = new FileReader(dictionaryFilename); //Opens a file reader to convert the text file into an array.
			BufferedReader br = new BufferedReader(fr); //This reader is used to read the lines of the file.
			String str;
			while((str = br.readLine())!=null){ //Continue while there are lines to read, create str as the line from the text file.
				str = str.toLowerCase(); //Make all letters lower-case.
				dictionarySet.add(str); //Add the word to the end of the hash set.
		}
			fr.close();
			return dictionarySet; //Returns a hash set with all the tokens from the text file.
		} catch (FileNotFoundException e) {
			System.out.println("Error: Unable to open file " + dictionaryFilename); //Prints out the error message if the file cannot be found.
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dictionarySet;
	}

    // incrementHashMap
    // Preconditions:
    //  - map is a non-null HashMap 
    //  - key is a key that may or may not be in map
    //  - amount is the amount that you would like to increment key's value by
    // Postconditions:
    //  - If key was already in map, map.get(key) returns amount more than it did before
    //  - If key was not in map, map.get(key) returns amount
    // Notes:
    //  - This method has been provided for you 
	private static void incrementHashMap(HashMap<String,Integer> map,String key,int amount) {
		if( map.containsKey(key) ) {
			map.put(key,map.get(key)+amount);
		} else {
			map.put(key,amount);
		}
		return;
	}

    // printNumWordsDiscovered
    // Preconditions:
    //    - vocab maps words to the number of times they appear in the data
    //    - dictionary contains the words in the dictionary
    // Postconditions:
    //    - Prints each word in vocab that is also in dictionary, in sorted order (alphabetical, ascending)
    //        Also prints the counts for how many times each such word occurs
    //    - Prints the number of unique words in vocab that are also in dictionary 
    //    - Prints the total of words in vocab (weighted by their count) that are also in dictionary 
	// Notes:
    //    - See example output for formatting
	public static void printNumWordsDiscovered( HashMap<String,Integer> vocab, HashSet<String> dictionary ) {
		return;
	}

}
