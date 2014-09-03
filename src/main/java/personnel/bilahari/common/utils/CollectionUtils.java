package personnel.bilahari.common.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

/**
 * 
 * @author bilahari.th
 *
 */
public class CollectionUtils {

	public static boolean isEmpty(Collection collection){
		
		
		
		HashMap<String, String> ss = new HashMap<String, String>();
		
		ss.put("a", "aa");
		ss.put("b", "bb");
		ss.put("a", "ss");
		
		
		
		return false;
	}
	
	public static void main(String[] args){
		isEmpty(null);
	}
}
