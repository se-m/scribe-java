package org.scribe.builder.api;

import org.scribe.exceptions.OAuthException;
import org.scribe.extractors.*;
import org.scribe.model.*;
import org.scribe.oauth.*;
import org.scribe.utils.Preconditions;

/**
 * Default implementation of the OAuth protocol, version 2.0 (draft 11)
 *
 * This class is meant to be extended by concrete implementations of the API,
 * providing the endpoints and endpoint-http-verbs.
 *
 * If your Api adheres to the 2.0 (draft 11) protocol correctly, you just need to extend
 * this class and define the getters for your endpoints.
 *
 * If your Api does something a bit different, you can override the different
 * extractors or services, in order to fine-tune the process. Please read the
 * javadocs of the interfaces to get an idea of what to do.
 *
 * @author Diego Silveira
 *
 */
public abstract class DefaultApi20 extends Api
{
	protected Token RefreshToken= Token.empty();
	protected OAuthConfig config=null;   
	protected ErrorInfo lastError=new ErrorInfo();
	protected int lastResponseCode=0;
    
  public  DefaultApi20 (){
	  flow = ApiFlow.standard;
  }
	
 
  protected TokenExtractor getAccessTokenExtractor() {
    return new JsonTokenExtractor(JsonTokenExtractor.accessTokenPattern);
  }
  
  protected TokenExtractor getRefreshTokenExtractor(){
	  return new JsonTokenExtractor(JsonTokenExtractor.refreshTokenPattern);
  }
  
  protected ErrorInfoExtractor getErrorInfoExtractor(){
	  return new JsonErrorInfoExtractor();
  }
  
  
  /**
   * Make use your token as Refresh token
   * @param token
   */
  
  public void useAsRefreshToken (Token token){	  
	  if (token==null) RefreshToken=Token.empty();
	  else RefreshToken=token;
  }
  
  public Token getRefreshToken (){
	  return RefreshToken;
  }
  
 
	
  /**
   * Returns the verb for the access token endpoint (defaults to GET)
   * 
   * @return access token endpoint verb
   */
  protected Verb getAccessTokenVerb()
  {
    return Verb.POST;
  }
  
  protected Verb getRefreshTokenVerb()
  {
    return getAccessTokenVerb();
  }
	
  /**
   * Returns the URL that receives the access token requests.
   * 
   * @return access token URL
   */
  protected abstract String getAccessTokenEndpoint();
  
  protected String getRefreshTokenEndpoint(){
	  return getAccessTokenEndpoint();
  }
  
  public boolean checkForError (Response response){
	  lastResponseCode = response.getCode();
	  if (response.isSuccessful()) {
		  lastError = new ErrorInfo();
	  }else{
		  lastError = getErrorInfoExtractor().extract(response.getBody(), response.getCode());
	  };
	  return lastError.getError() == ErrorInfo.no_error; 
  }
  
  public ErrorInfo getLastError (){
	  return lastError;
  }
  
  public boolean hasError (){
	  return lastError.getError() != ErrorInfo.no_error;
  }
  
  public int getLastResponseCode(){
	  return lastResponseCode;
  }
  
  public boolean parseAccessTokenResponse (Response response){
	  checkForError(response);
	  RefreshToken = Token.empty();
	  try{
		  try{
			  AccessToken = getAccessTokenExtractor().extract(response.getBody());  
		  }catch (OAuthException e) {
			  AccessToken = Token.empty();			  
			  return false;
		  }
		  
		  try{
		  	  RefreshToken = getRefreshTokenExtractor().extract(response.getBody());
		  }catch (OAuthException e){
			  RefreshToken = Token.empty();
		  }
	  }catch (IllegalArgumentException e){
		  AccessToken = Token.empty();
		  RefreshToken = Token.empty();
		  return false;
	  }	  
	 return true; 
  }
  
  protected RequestParameterInserter getRequestParameterInserter(Request request){
	  if (request.getVerb()==Verb.POST){
		  return new RequestParameterInserterBody(request);
	  }else{
		  return new RequestParameterInserterQuery(request);
	  }
  }
  
  /**
   * for standard flow
   * @param auth_code
   * @return ready to use OAuthRequest to get accessToken
   */
  public OAuthRequest getAccessTokenRequest(String auth_code){
	  Preconditions.checkNotNull(config, "config is not set yet");
	  OAuthRequest request = new OAuthRequest(getAccessTokenVerb(), getAccessTokenEndpoint());
	  RequestParameterInserter params = getRequestParameterInserter(request);
	  params.Add(OAuthConstants.GRAND_TYPE,"authorization_code");
	  params.Add(OAuthConstants.CLIENT_ID, config.getApiKey());
	  if (config.hasApiSecret())  params.Add(OAuthConstants.CLIENT_SECRET, config.getApiSecret());
	  params.Add(OAuthConstants.CODE, auth_code);
	  if (config.hasCallback()) params.Add(OAuthConstants.REDIRECT_URI, config.getCallback());
	  if (config.hasScope()) params.Add(OAuthConstants.SCOPE, config.getScope());
	  return request;	  
  }
  
  /**
   * access token by refresh token (any flow)
   * @param refreshToken
   * @return
   */
  public OAuthRequest getAccessTokenRequest(Token refreshToken){
	  Preconditions.checkNotNull(config, "config is not set yet");
	  if (refreshToken==null) refreshToken=RefreshToken;	  
	  OAuthRequest request = new OAuthRequest(getRefreshTokenVerb(), getRefreshTokenEndpoint());
	  RequestParameterInserter params = getRequestParameterInserter(request);	  
	  params.Add(OAuthConstants.GRAND_TYPE,"refresh_token");
	  params.Add(OAuthConstants.CLIENT_ID, config.getApiKey());
	  if (config.hasApiSecret()) params.Add(OAuthConstants.CLIENT_SECRET, config.getApiSecret());
	  params.Add(OAuthConstants.REFRESH_TOKEN, refreshToken.getToken());	 
	  return request;	  
  }
  
  /**
   * User credential flow
   * @param username
   * @param password
   * @return
   */
   
  public OAuthRequest getAccessTokenRequest(String username, String password){
	  Preconditions.checkNotNull(config, "config is not set yet");  	  
	  OAuthRequest request = new OAuthRequest(getAccessTokenVerb(), getAccessTokenEndpoint());
	  RequestParameterInserter params = getRequestParameterInserter(request);
	  params.Add(OAuthConstants.GRAND_TYPE,"password");
	  params.Add(OAuthConstants.CLIENT_ID, config.getApiKey());
	  if (config.hasApiSecret()) params.Add(OAuthConstants.CLIENT_SECRET, config.getApiSecret());
	  params.Add("username", username);
	  params.Add("password", password);
	  if (config.hasScope()) params.Add(OAuthConstants.SCOPE, config.getScope());	  
	  return request;
  }
  
  
  /**
   * client credential flow
   * @return
   */
  
  public OAuthRequest getAccessTokenRequest(){
	  Preconditions.checkNotNull(config, "config is not set yet");	  	  
	  OAuthRequest request = new OAuthRequest(getAccessTokenVerb(), getAccessTokenEndpoint());
	  RequestParameterInserter params = getRequestParameterInserter(request);
	  params.Add(OAuthConstants.GRAND_TYPE,"client_credentials");
	  params.Add(OAuthConstants.CLIENT_ID, config.getApiKey());
	  if (config.hasApiSecret()) params.Add(OAuthConstants.CLIENT_SECRET, config.getApiSecret());	  
	  if (config.hasScope()) params.Add(OAuthConstants.SCOPE, config.getScope());
	  return request;
  }
  
   
  /**
   * Returns the URL where you should redirect your users to authenticate
   * your application.
   *
   * @return the URL where you should redirect your users
   */
  
  public abstract String getAuthorizationUrl();
  /**
   * {@inheritDoc}
   */
  public OAuthService createService(OAuthConfig config)
  {
	this.config = config;
    return new OAuth20ServiceImpl(this);
  }

}

abstract class RequestParameterInserter {
	protected Request request;
	RequestParameterInserter(Request request){
		this.request=request;
	}
	public abstract void Add(String key, String value);
}

class RequestParameterInserterBody extends RequestParameterInserter{
	RequestParameterInserterBody(Request request) {
		super(request);		
	}

	public void Add(String key, String value){
		request.addBodyParameter(key,value);
	}
}

class RequestParameterInserterQuery extends RequestParameterInserter{
	RequestParameterInserterQuery(Request request) {
		super(request);		
	}

	public void Add(String key, String value){
		request.addQuerystringParameter(key,value);
	}
}


