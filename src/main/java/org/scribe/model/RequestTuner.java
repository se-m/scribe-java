package org.scribe.model;

import java.net.HttpURLConnection;

public abstract class RequestTuner
{
  public abstract void tuneBeforeConection(Request request);
  public abstract void tuneBeforeSend(Request request);
  public abstract void tuneBeforeResponse(Request request);
  public boolean overrideBodySending (){
	  return false;
  }
  
  public HttpURLConnection getConnection (Request request){
	  return request.connection;
  }
}