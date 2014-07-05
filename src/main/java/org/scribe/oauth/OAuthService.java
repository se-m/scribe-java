package org.scribe.oauth;

import org.scribe.model.*;

/**
 * The main Scribe object. 
 * 
 * A facade responsible for the retrieval of request and access tokens and for the signing of HTTP requests.  
 * 
 * @author Pablo Fernandez
 */
public interface OAuthService
{
	

	

  /**
   * Returns the OAuth version of the service.
   * 
   * @return oauth version as string
   */
  public String getVersion();
  
  /**
   * Signs am OAuth request
   * 
   * @param accessToken access token (obtained previously)
   * @param request request to sign
   */
  public void signRequest(Token accessToken, OAuthRequest request);
  
  
}
