package org.scribe.services;

import static org.junit.Assert.*;

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
	 public void MigEncTest (){
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
}

//
//
