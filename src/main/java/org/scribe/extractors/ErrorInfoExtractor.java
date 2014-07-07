package org.scribe.extractors;

import org.scribe.model.ErrorInfo;


public interface  ErrorInfoExtractor {
	public  ErrorInfo extract(String response, int responseCode);
	
}
