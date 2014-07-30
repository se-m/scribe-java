package org.scribe.https;


import org.scribe.builder.api.ApiFlow;
import org.scribe.builder.api.DefaultApi20;



//etc/host    127.0.0.1 testhome.com
public class testhomeApi extends DefaultApi20 {
	
		
	public testhomeApi(){
		super();
		flow = ApiFlow.client_cred;		
	}
	
	//for test purposes
	public void changeFlow (ApiFlow flow){
		this.flow=flow;
	}
	
	@Override
	protected String getAccessTokenEndpoint() {
		return "https://testhome.com";
	}

	@Override
	public String getAuthorizationUrl() {
		return "https://testhome.com";
	}
	
	
}
