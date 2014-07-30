package org.scribe.builder;

import static org.junit.Assert.*;

import org.junit.*;
import org.scribe.builder.api.*;
import org.scribe.model.*;
import org.scribe.oauth.*;
import org.scribe.test.helpers.ExceptionsChecker;

public class ServiceBuilderTest
{
  private ServiceBuilder builder;

  @Before
  public void setup()
  {
    builder = new ServiceBuilder();
  }

  @Test
  public void shouldReturnConfigDefaultValues()
  {
    builder.provider(ApiMock.class).apiKey("key").apiSecret("secret").build();
    assertEquals(ApiMock.config.getApiKey(), "key");
    assertEquals(ApiMock.config.getApiSecret(), "secret");
    assertEquals(ApiMock.config.getCallback(), OAuthConstants.OUT_OF_BAND);
    assertEquals(ApiMock.config.getSignatureType(), SignatureType.Header);
  }

  @Test
  public void shouldAcceptValidCallbackUrl()
  {
    builder.provider(ApiMock.class).apiKey("key").apiSecret("secret").callback("http://example.com").build();
    assertEquals(ApiMock.config.getApiKey(), "key");
    assertEquals(ApiMock.config.getApiSecret(), "secret");
    assertEquals(ApiMock.config.getCallback(), "http://example.com");
  }
  
  @Test
  public void shouldBuildWithoutApiSecret()
  {
    builder.provider(ApiMock.class).apiKey("key").callback("http://example.com").build();
    assertEquals(ApiMock.config.getApiKey(), "key");
    assertFalse(ApiMock.config.hasApiSecret());
    assertEquals(ApiMock.config.getApiSecret(), null);
    assertEquals(ApiMock.config.getCallback(), "http://example.com");
  }
  
  //if secret empty - don't pass it. If you pass secret - it must be not empty.
  @Test
  public void shouldNotAcceptEmptySecret()
  {
		try{
			builder.provider(ApiMock.class).apiKey("key").apiSecret("").callback("http://example.com").build();
			fail("Exception expecting");
		}catch (IllegalArgumentException e){
			assertTrue(ExceptionsChecker.containString(e, "Invalid Api secret"));
		}catch (Exception e){
			fail("IllegalArgumentException expecting");
		}
  
		
		try{
			builder.provider(ApiMock.class).apiKey("key").apiSecret(null).callback("http://example.com").build();
			fail("Exception expecting");
		}catch (IllegalArgumentException e){
			assertTrue(ExceptionsChecker.containString(e, "Invalid Api secret"));
		}catch (Exception e){
			fail("IllegalArgumentException expecting");
		}  
  }

  @Test
  public void shouldAcceptASignatureType()
  {
    builder.provider(ApiMock.class).apiKey("key").apiSecret("secret").signatureType(SignatureType.QueryString).build();
    assertEquals(ApiMock.config.getApiKey(), "key");
    assertEquals(ApiMock.config.getApiSecret(), "secret");
    assertEquals(ApiMock.config.getSignatureType(), SignatureType.QueryString);
  }

  @Test(expected=IllegalArgumentException.class)
  public void shouldNotAcceptNullAsCallback()
  {
    builder.provider(ApiMock.class).apiKey("key").apiSecret("secret").callback(null).build(); 
  }

  @Test
  public void shouldAcceptAnScope()
  {
    builder.provider(ApiMock.class).apiKey("key").apiSecret("secret").scope("rss-api").build();
    assertEquals(ApiMock.config.getApiKey(), "key");
    assertEquals(ApiMock.config.getApiSecret(), "secret");
    assertEquals(ApiMock.config.getScope(), "rss-api");
  }

  public static class ApiMock extends Api
  {
    public static OAuthConfig config;

    public OAuthService createService(OAuthConfig config)
    {
      ApiMock.config = config;
      return null;
    }
  }
}
