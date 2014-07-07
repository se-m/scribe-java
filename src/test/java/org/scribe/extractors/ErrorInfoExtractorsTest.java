package org.scribe.extractors;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.scribe.model.ErrorInfo;

public class ErrorInfoExtractorsTest {
	@Test
	public void JsonExtractSomeErrors () {
		ErrorInfo err;
		
		//some Errors
		err = new JsonErrorInfoExtractor().extract(
				"{\"error\":\"invalid_client\",\"error_description\":\"The client credentials are invalid\"}", 400);
		
		assertEquals("err 1",err.getError(),ErrorInfo.invalid_client);
		assertEquals("desc 1",err.getDescription(),"The client credentials are invalid");
		
		
		err = new JsonErrorInfoExtractor().extract(
				"{\"error\":\"invalid_grant\",\"error_description\":\"Invalid username and password combination\"}", 400);


		assertEquals("err 2",err.getError(),ErrorInfo.invalid_grant);
		assertEquals("desc 2",err.getDescription(),"Invalid username and password combination");

		err = new JsonErrorInfoExtractor().extract(
				"{\"error\":\"invalid_token\",\"error_description\":\"The access token provided is invalid\"}", 400);
		

		assertEquals("err 3",err.getError(),ErrorInfo.invalid_token);
		assertEquals("desc 3",err.getDescription(),"The access token provided is invalid");

			
		//no error by code 
		err = new JsonErrorInfoExtractor().extract(
				"{\"error\":\"invalid_client\",\"error_description\":\"The client credentials are invalid\"}", 200);
		
		assertEquals("no err 1",err.getError(),ErrorInfo.no_error);
	}
	
}
