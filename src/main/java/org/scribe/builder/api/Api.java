package org.scribe.builder.api;

import org.scribe.model.*;
import org.scribe.oauth.*;

/**
 * Contains all the configuration needed to instantiate a valid {@link OAuthService}
 * 
 * @author Pablo Fernandez
 *
 */
public abstract class Api
{  
	
	protected ApiFlow flow;
	 
	 
	protected Token AccessToken = Token.empty();
  /**
   * Creates an {@link OAuthService}
   * 
   * @param apiKey your application api key
   * @param apiSecret your application api secret
   * @param callback the callback url (or 'oob' for out of band OAuth)
   * @param scope the OAuth scope
   * 
   * @return fully configured {@link OAuthService}
   */
  public abstract OAuthService createService(OAuthConfig config);

  
 /**
  * token must be obtained before this call (e.g. use {@link service}.{@link makeAccessTokenRequest} for Standard oAuth 2.0 flow) 
  * @return stored Access {@link Token}
  */
  public Token getAccessToken (){
	  return AccessToken;
  }
  
  /**
   * Make use your token as Access token
   * @param {@link Token}
   */

  public void useAsAccessToken (Token token){
	  if (token==null) AccessToken=Token.empty();
	  else AccessToken=token;
  }
  
  
  /**
   * obtain what kind of oauth flow is used
   * @return kind of flow
   */
  
  public ApiFlow getFlowType (){
	  return flow;
  }
  
}
