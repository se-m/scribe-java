package org.scribe.services;

import java.lang.reflect.Method;

import org.scribe.exceptions.OAuthSignatureException;

public class DatatypeConverterEncoder extends Base64Encoder
{
  @Override
  public String encode(byte[] bytes)
  {   
	  try
	    { 
	     Method mthd= Class.forName("javax.xml.bind.DatatypeConverter").getDeclaredMethod("printBase64Binary",new Class[]{byte[].class});       
	     return new String((String)mthd.invoke(null,new Object[]{bytes}));
	    }
	    catch (Exception e)
	    {
	      //throw new OAuthSignatureException("Can't perform base64 encoding", e);
	    	e.printStackTrace();
	    	return "";
	    } 
  }

  @Override
  public String getType()
  {
    return "DatatypeConverter";
  }
  
  public static boolean isPresent(){
	  try
	    {
	      Class.forName("javax.xml.bind.DatatypeConverter");
	      return true;
	    }
	    catch (ClassNotFoundException e)
	    {
	      return false;
	    }   
  }
}
