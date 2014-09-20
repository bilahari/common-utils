package personnel.bilahari.common.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class StringUtils {

	public static boolean isEmptyString(String str){

		return str==null||str.trim().length()<=0;
	}

	/**
	 * 
	 * @param sentence	: a sentence string
	 * @return			: list of words in the sentence
	 */
	public static ArrayList<String> getWordsInaSentence(String sentence){

		ArrayList<String> wordsList = new ArrayList<String>();
		StringTokenizer stringTokenizer = new StringTokenizer(sentence, " ");
		while (stringTokenizer.hasMoreElements()) {
			wordsList.add(stringTokenizer.nextElement().toString());
		}
		return wordsList;
	}
}
