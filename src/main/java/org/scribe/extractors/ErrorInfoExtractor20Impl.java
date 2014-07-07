package org.scribe.extractors;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.scribe.model.ErrorInfo;
import org.scribe.model.OAuthConstants;
import org.scribe.utils.OAuthEncoder;

public class ErrorInfoExtractor20Impl implements ErrorInfoExtractor {

	@Override
	public ErrorInfo extract(String response, int responseCode) {
		if (responseCode==200){
			return new ErrorInfo(ErrorInfo.no_error,"","",response);
		}else{
			String id="";
			String desc="";
			String url="";
			Pattern p1 = Pattern.compile("[#?&]"+OAuthConstants.ERROR+"=([^&]+)");
			Matcher m1 = p1.matcher(response);		
			
			if (m1.find())
				id=m1.group(1);
		    					
			Pattern p2 = Pattern.compile("[#?&]"+OAuthConstants.ERROR_DESCRIPTION+"=([^&]+)");
			Matcher m2 = p2.matcher(response);			
			
			if (m2.find())
				desc = OAuthEncoder.decode(m2.group(1));
						
			Pattern p3 = Pattern.compile("[#?&]"+OAuthConstants.ERROR_URI+"=([^&]+)");
			Matcher m3 = p3.matcher(response);			
			
			if (m3.find())
				url = OAuthEncoder.decode(m3.group(1));
			
			return new ErrorInfo(id,desc,url,response);
		}		
	}

}
