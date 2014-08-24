package org.scribe.model;

import static org.junit.Assert.*;

import java.io.*;

import org.junit.*;

public class ResponseTest
{

  private Response response;
  private ConnectionStub connection;

  @Before
  public void setup() throws Exception
  {
    connection = new ConnectionStub();
    connection.addResponseHeader("one", "one");
    connection.addResponseHeader("two", "two");
    response = new Response(connection);
  }

  @Test
  public void shouldPopulateResponseHeaders()
  {
    assertEquals(2, response.getHeaders().size());
    assertEquals("one", response.getHeader("one"));
  }

  @Test
  public void shouldParseBodyContents()
  {
    assertEquals("contents", response.getBody());
    assertEquals(1, connection.getTimesCalledInpuStream());
  }

  @Test
  public void shouldParseBodyContentsOnlyOnce()
  {
    assertEquals("contents", response.getBody());
    assertEquals("contents", response.getBody());
    assertEquals("contents", response.getBody());
    assertEquals(1, connection.getTimesCalledInpuStream());
  }
  
  @Test
  public void shouldReturnEmptyStreamIfBodyParsed()
  { 
    try {
    	assertEquals(8,response.getStream().available());
    	assertEquals("contents", response.getBody());
		assertEquals(0,response.getStream().available());
	} catch (IOException e) {	
		fail();
		e.printStackTrace();
	}  
  }
  
  @Test
  public void shouldParseOver64kb()
  { 
	byte[] a68kb = new byte[0x11000];//68 kb  (0x10000 - magic inside streamutils)
	
	byte j=33;//ASCII - !	
	for (int i=0; i<68*1024;++i){
		if (i%1024==0) ++j;
		a68kb[i]=j;
	}
	
	try {
		connection = new ConnectionStub(new ByteArrayInputStream(a68kb));
		response = new Response(connection);
	} catch (Exception e1) {
		fail();
		e1.printStackTrace();
	}
	
    try {
    	assertEquals(68*1024,response.getStream().available());
    	assertEquals(new String(a68kb), response.getBody());
		
	} catch (IOException e) {		
		fail();
		e.printStackTrace();
	}  
  }

  @Test
  public void shouldHandleAConnectionWithErrors() throws Exception
  {
    Response errResponse = new Response(new FaultyConnection());
    assertEquals(400, errResponse.getCode());
    assertEquals("errors", errResponse.getBody());
  }

  private static class FaultyConnection extends ConnectionStub
  {

    public FaultyConnection() throws Exception
    {
      super();
    }

    @Override
    public InputStream getErrorStream()
    {
      return new ByteArrayInputStream("errors".getBytes());
    }

    @Override
    public int getResponseCode() throws IOException
    {
      return 400;
    }
  }
}
