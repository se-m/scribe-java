package org.scribe.https;

import org.scribe.builder.api.ApiFlow;
import org.scribe.builder.api.DefaultApi20;

public class localhostApi  extends DefaultApi20 {
	
	public localhostApi(){
		super();
		flow = ApiFlow.client_cred;		
	}
	@Override
	protected String getAccessTokenEndpoint() {
		return "https://localhost";
	}

	@Override
	public String getAuthorizationUrl() {
		return "https://localhost";
	}
}