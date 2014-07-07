package org.scribe.extractors;

import static org.junit.Assert.*;

import org.junit.*;
import org.scribe.exceptions.OAuthException;
import org.scribe.model.*;

public class JsonTokenExtractorTest
{
  private String response1= "'{ \"access_token\":\"I0122HHJKLEM21F3WLPYHDKGKZULAUO4SGMV3ABKFTDT3T3X\"}'";
  private String response2= "'{ \"refresh_token\":\"I0122HHJKLEM2___askshvLAsk$^^14SGMV3ABKFTDT3T3X\"}'";
  private JsonTokenExtractor extractor = new JsonTokenExtractor();

  @Test
  public void shouldParseResponse()
  {
    Token token = extractor.extract(response1);
    assertEquals(token.getToken(), "I0122HHJKLEM21F3WLPYHDKGKZULAUO4SGMV3ABKFTDT3T3X");
  }
  
  @Test
  public void EmptyEqualAccess()
  {
	JsonTokenExtractor access = new JsonTokenExtractor(JsonTokenExtractor.accessTokenPattern);  
    Token token1 = extractor.extract(response1);
    Token token2 = access.extract(response1);
    assertEquals(token1.getToken(), token2.getToken());
  }
  
  @Test
  public void RshouldParseRefreshToken()
  {   
    assertEquals(new JsonTokenExtractor(JsonTokenExtractor.refreshTokenPattern).extract(response2).getToken(), "I0122HHJKLEM2___askshvLAsk$^^14SGMV3ABKFTDT3T3X");
  }
  @Test(expected=OAuthException.class)
  public void RshouldnotParseAccessToken()
  {   
    new JsonTokenExtractor(JsonTokenExtractor.refreshTokenPattern).extract(response1).getToken();
  }

  @Test(expected=IllegalArgumentException.class)
  public void shouldThrowExceptionIfForNullParameters()
  {
    extractor.extract(null);
  }

  @Test(expected=IllegalArgumentException.class)
  public void shouldThrowExceptionIfForEmptyStrings()
  {
    extractor.extract("");
  }
}
