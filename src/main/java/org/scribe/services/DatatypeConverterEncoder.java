package org.scribe.services;

//import javax.xml.bind.*;
import util.Base64;

public class DatatypeConverterEncoder extends Base64Encoder
{
  @Override
  public String encode(byte[] bytes)
  {
    //return DatatypeConverter.printBase64Binary(bytes);
	  return Base64.encodeToString(bytes, false);
  }

  @Override
  public String getType()
  {
    return "DatatypeConverter";
  }
}
