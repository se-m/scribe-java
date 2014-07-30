package org.scribe.services;

import static org.junit.Assert.*;

import java.util.Arrays;
import org.junit.Test;

public class EncoderTest {
	private String Base64EStr ="this is test base64 string";
	private String Base64DStr ="dGhpcyBpcyB0ZXN0IGJhc2U2NCBzdHJpbmc=";
	
	 @Test
	 public void CommonEncTest (){
		 if (CommonsEncoder.isPresent()){
			  assertEquals(Base64DStr, new CommonsEncoder().encode(Base64EStr.getBytes()));
		 }
	 }
	 
	 @Test
	 public void InternaEncTest (){
		 if (InternalEncoder.isPresent()){
			  assertEquals(Base64DStr, new InternalEncoder().encode(Base64EStr.getBytes()));
		 }
	 }
	 
	 @Test
	 public void DatatypeEncTest(){
		 if (DatatypeConverterEncoder.isPresent()){
			  assertEquals(Base64DStr, new DatatypeConverterEncoder().encode(Base64EStr.getBytes()));
		 } 
	 }
	 
	 //to get rid of surprises
	 //and increase test coverage :)
	 //pure MigBase64Tests
	 
	 @Test
	 public void Base64Tests (){
		 byte[] ebytes=Base64EStr.getBytes();
		 byte[] dbytes=Base64DStr.getBytes();
		 
		 assertArrayEquals("string normal",util.Base64.decode(Base64DStr),ebytes);
		 assertArrayEquals("char normal",util.Base64.decode(Base64DStr.toCharArray()),ebytes);
		 assertArrayEquals("bytes normal",util.Base64.decode(dbytes),ebytes);
		 
		 assertArrayEquals("byte fast",util.Base64.decodeFast(dbytes),ebytes);
		 assertArrayEquals("char fast",util.Base64.decodeFast(Base64DStr.toCharArray()),ebytes);
		 assertArrayEquals("string fast",util.Base64.decodeFast(Base64DStr),ebytes);
		 		 
		 assertArrayEquals("encode bytes",util.Base64.encodeToByte(ebytes,false),dbytes);
		 //long string
		 String lngS = Base64EStr+Base64EStr+Base64EStr+Base64EStr+Base64EStr+Base64EStr;
		 String rfcS = util.Base64.encodeToString(lngS.getBytes(),true);
		 String NrfcS = util.Base64.encodeToString(lngS.getBytes(),false);
		 
		 assertTrue(rfcS.contains( "\r\n"));
		 assertTrue(!NrfcS.contains("\r\n"));
		 
		 assertArrayEquals("long fast rfc (max 76 per line)",util.Base64.decodeFast(rfcS),lngS.getBytes()); 
		 assertArrayEquals("long fast no rfc (one single line)",util.Base64.decodeFast(NrfcS),lngS.getBytes());
		 assertArrayEquals("long normal no rfc (next line in other place)"
				 			,util.Base64.decode(NrfcS.substring(0,10)+"\r\n"+NrfcS.substring(10)),lngS.getBytes());
		 
		 assertFalse("long fast no rfc (next line in other place)"
				 ,Arrays.equals(util.Base64.decodeFast(NrfcS.substring(0,10)+"\r\n"+NrfcS.substring(10)), lngS.getBytes()));
		 
		 //empty
		 byte[] empty=new byte[0];
		 
		 assertArrayEquals("decode empty",util.Base64.decodeFast(empty),empty); 
		 assertArrayEquals("encode empty",util.Base64.encodeToByte(empty, false),empty);
	 }
}

//
//
