package org.scribe.services;


import org.scribe.exceptions.*;
import java.lang.reflect.Method;

/**
 * Rewrote using reflection
**/ 
public class CommonsEncoder extends Base64Encoder
{

  @Override
  public String encode(byte[] bytes)
  {
    try
    {      
      Method mthd= Class.forName("org.apache.commons.codec.binary.Base64").getDeclaredMethod("encodeBase64",new Class[]{byte[].class,String.class});       
      return new String((String)mthd.invoke(null,new Object[]{bytes, "UTF-8"}));
    }
    catch (Exception e)
    {
    	e.printStackTrace();
      throw new OAuthSignatureException("Can't perform base64 encoding", e);
    }    
  }

  @Override
  public String getType()
  {
    return "CommonsCodec";
  }

  public static boolean isPresent()
  {	
    try
    {
      Class.forName("org.apache.commons.codec.binary.Base64");
      return true;
    }
    catch (ClassNotFoundException e)
    {
      return false;
    }    
  }
}
