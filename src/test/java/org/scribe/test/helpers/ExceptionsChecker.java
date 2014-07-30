package org.scribe.test.helpers;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionsChecker {
	public static boolean containString (Exception e, String searchString){
		// stack trace as a string
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		String s = sw.toString(); 
		//System.out.print(s);
		return s.contains(searchString);
	}
}
