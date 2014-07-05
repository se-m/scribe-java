package org.scribe.oauth;

import java.io.InputStream;

import org.scribe.builder.api.*;
import org.scribe.exceptions.OAuthException;
import org.scribe.model.*;

public class OAuth20ServiceImpl implements OAuthService
{
 
  
	 private static final String VERSION = "2.0";
		
	  public final DefaultApi20 api; 
  
  /**
   * Default constructor
   * 
   * @param api OAuth2.0 api information
   * @param config OAuth 2.0 configuration param object
   */
  public OAuth20ServiceImpl(DefaultApi20 api) {
	  this.api = api; 
  }

  
  /**
   * standard flow 
   * @param auth_code
   * @param tuner
   * @return true if no errors
   */
  public boolean makeAccessTokenRequest (String auth_code,RequestTuner tuner){
	  if (api.getFlowType()!=ApiFlow.standard) throw new OAuthException("this method is for standard flow only");
	  OAuthRequest request = api.getAccessTokenRequest(auth_code);	  	  
	  return api.parseAccessTokenResponse(request.send(tuner));
  }
  /**
   * {@inheritDoc}
   */
  public boolean refreshAccessToken (RequestTuner tuner){
	  return makeAccessTokenRequest(api.getRefreshToken(),tuner);
  }
  /**
   * {@inheritDoc}
   */
  public boolean makeAccessTokenRequest (Token refreshToken,RequestTuner tuner){
	  OAuthRequest request = api.getAccessTokenRequest(refreshToken);
	  return api.parseAccessTokenResponse(request.send(tuner));
  }
  
  /**
   * client credentials flow
   * @return
   */
  public boolean makeAccessTokenRequest (RequestTuner tuner){
	  if (api.getFlowType()!=ApiFlow.client_cred) throw new OAuthException("this method is for client credentials flow only");
	  OAuthRequest request = api.getAccessTokenRequest();
	  return api.parseAccessTokenResponse(request.send(tuner));
  }
  
  /**
   * user credentials flow
   * @param usrLogin
   * @param usrPassword
   * @param tuner
   * @return
   */
  
  public boolean makeAccessTokenRequest (String usrLogin, String usrPassword,RequestTuner tuner){
	  if (api.getFlowType()!=ApiFlow.user_cred) throw new OAuthException("this method is for user credentials flow only");
	  OAuthRequest request = api.getAccessTokenRequest(usrLogin,usrPassword);
	  return api.parseAccessTokenResponse(request.send(tuner));
  }
  
 
  

  
  public Response makeRequest (OAuthRequest request, RequestTuner tuner){
	  signRequest(api.getAccessToken(),request);
	  Response response=request.send(tuner);
	  api.checkForError(response);
	  return response;
  }
  /**
   * {@inheritDoc}
   */
  public InputStream RequestResource (String URL,RequestTuner tuner)
  {
	  OAuthRequest request  = new OAuthRequest(Verb.GET,URL);
	  return makeRequest(request,tuner).getStream();	  
  }

  public void signRequest(Token accessToken, OAuthRequest request)
  {
    request.addQuerystringParameter(OAuthConstants.ACCESS_TOKEN, accessToken.getToken());
  }
  /**
   * {@inheritDoc}
   */
  public String getAuthorizationUrl()
  {
    return api.getAuthorizationUrl();
  }
  /**
   * {@inheritDoc}
   */
  public String getVersion()
  {
    return VERSION;
  }
}
