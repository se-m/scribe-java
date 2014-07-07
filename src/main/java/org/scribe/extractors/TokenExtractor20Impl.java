package org.scribe.extractors;

import java.util.regex.*;

import org.scribe.exceptions.*;
import org.scribe.model.*;
import org.scribe.utils.*;

/**
 * Default implementation of {@link TokenExtractor}. Conforms to OAuth 2.0
 */
public class TokenExtractor20Impl implements TokenExtractor
{
  private static final String EMPTY_SECRET = "";

  public static final Pattern accessTokenPattern = Pattern.compile(OAuthConstants.ACCESS_TOKEN+"=([^&]+)");
  public static final Pattern refreshTokenPattern = Pattern.compile(OAuthConstants.REFRESH_TOKEN+"refresh_token=([^&]+)");
  private Pattern tokenPattern;
  
  public TokenExtractor20Impl(){
	  tokenPattern=accessTokenPattern;
  }
  
  public TokenExtractor20Impl(Pattern aTokenPattern){
	  tokenPattern = aTokenPattern;
  }

  public Token extract(String response)
  {
    Preconditions.checkEmptyString(response, "Response body is incorrect. Can't extract a token from an empty string");

    Matcher matcher = tokenPattern.matcher(response);
    if (matcher.find())
    {
      String token = OAuthEncoder.decode(matcher.group(1));
      return new Token(token, EMPTY_SECRET, response);
    } 
    else
    {
      throw new OAuthException("Response body is incorrect. Can't extract a token from this: '" + response + "'", null);
    }
  }
}
