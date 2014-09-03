package personnel.bilahari.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class IoUtils {
	public static String readLine(){
		String line=null;
		BufferedReader bufRead = new BufferedReader(new InputStreamReader(System.in)) ;	
		try {
			line = bufRead.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return line;
	}
}
