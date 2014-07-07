package org.scribe.https;


import org.scribe.builder.api.Api20Flow;
import org.scribe.builder.api.DefaultApi20;
import org.scribe.extractors.JsonTokenExtractor;
import org.scribe.extractors.TokenExtractor;



//etc/host    127.0.0.1 testhome.com
public class testhomeApi extends DefaultApi20 {
	
	public testhomeApi(){
		flow = Api20Flow.client_cred;		
	}
	@Override
	protected String getAccessTokenEndpoint() {
		return "https://testhome.com";
	}

	@Override
	public String getAuthorizationUrl() {
		return "https://testhome.com";
	}
	
	@Override 
	  protected TokenExtractor getAccessTokenExtractor() {
	    return new JsonTokenExtractor(JsonTokenExtractor.accessTokenPattern);
	  }
	@Override  
	  protected TokenExtractor getRefreshTokenExtractor(){
		  return new JsonTokenExtractor(JsonTokenExtractor.refreshTokenPattern);
	  }
}
