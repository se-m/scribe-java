# Extended for better Oauth 2.0

### Dead Simple


```java
//Create service
OAuth20ServiceImpl service = (OAuth20ServiceImpl) new ServiceBuilder()
                                  .provider(LinkedInApi.class)
                                  .apiKey(YOUR_API_KEY)
                                  .apiSecret(YOUR_API_SECRET)
                                  .build();
```

```java
//Create access token
switch (service.api.getFlowType()){			
	case implicit:
		String alink1 = service.getAuthorizationUrl();
		//show this link to user in browser (user gets an Access and Refresh (optional) tokens)
		Token AccessToken = new Token("get this sting from user","");
		Token RefreshToken =  new Token("get this sting from user too (optional)","");
		service.api.useAsAccessToken(AccessToken);
		service.api.useAsRefreshToken(RefreshToken);
		break;
	case standard:
		String alink2 = service.getAuthorizationUrl();
		//show this link to user in browser	(user gets an authorization code)
		String auth_code = "get this string from user";
		service.makeAccessTokenRequest(auth_code, null);
		break;
	case user_cred:
		service.makeAccessTokenRequest("someuser", "somepassword", null);
		break;				
	case client_cred:
		service.makeAccessTokenRequest(null);
		break;
}
```

```java
//Use it
InputStream stream = service.RequestResource("https://example.com/resource.php",null);
```


