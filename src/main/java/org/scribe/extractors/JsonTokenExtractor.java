package org.scribe.extractors;

import java.util.regex.*;

import org.scribe.exceptions.*;
import org.scribe.model.*;
import org.scribe.utils.*;

public class JsonTokenExtractor implements TokenExtractor
{
  public static final Pattern accessTokenPattern = Pattern.compile('"'+OAuthConstants.ACCESS_TOKEN+"\":\\s*\"(\\S*?)\"");
  public static final Pattern refreshTokenPattern = Pattern.compile('"'+OAuthConstants.REFRESH_TOKEN+"\":\\s*\"(\\S*?)\"");
  
  private Pattern tokenPattern;

  public JsonTokenExtractor (Pattern aTokenPattern){
	  tokenPattern=aTokenPattern;
  }
  
  public JsonTokenExtractor (){
	  tokenPattern = accessTokenPattern;
  }


  public Token extract(String response)
  {
    Preconditions.checkEmptyString(response, "Cannot extract a token from a null or empty String");
    Matcher matcher = tokenPattern.matcher(response);
    if(matcher.find())
    {
      return new Token(matcher.group(1), "", response);
    }
    else
    {
      throw new OAuthException("Cannot extract an access token. Response was: " + response);
    }
  }

}