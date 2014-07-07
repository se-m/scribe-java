package org.scribe.model;

public class ErrorInfo {
	public static final int unknown_error				=-1;
	public static final int no_error					=0;
	public static final int empty_response				=1;
	public static final int invalid_request				=2;
	public static final int unauthorized_client			=3;
	public static final int access_denied				=4;
	public static final int unsupported_response_type	=5;
	public static final int invalid_scope				=6;
	public static final int server_error				=7;
	public static final int temporarily_unavailable		=8;
	public static final int invalid_grant				=9;
	public static final int invalid_client				=10;
	public static final int unsupported_grant_type      =11;
	
	protected int error;
	protected String error_description;
	protected String error_uri;
	protected String rawResponse;
	
	public ErrorInfo(){
		error_description="";
		error_uri="";
		error=no_error;
		rawResponse="";
	}
	
	public ErrorInfo(int error,String description,String uri,String response){
		error_description = description;
		this.error = error;
		error_uri = uri; 
		rawResponse = response;
	}
	
	public ErrorInfo(String errorID,String description,String uri,String response){
		error=unknown_error;
		error_uri = uri;
		error_description = description;
		rawResponse = response;
		setErrorByID(errorID);		
	}
	
	protected void setErrorByID (String id){
		if (OAuthConstants.ACCESS_DENIED.equals(id)) 				error=access_denied;
		if (OAuthConstants.INVALID_REQUEST.equals(id)) 				error=invalid_request;
		if (OAuthConstants.UNAUTHORIZED_CLIENT.equals(id)) 			error=unauthorized_client;
		if (OAuthConstants.INVALID_GRANT.equals(id)) 				error=invalid_grant;
		if (OAuthConstants.INVALID_CLIENT.equals(id)) 				error=invalid_client;
		if (OAuthConstants.INVALID_SCOPE.equals(id)) 				error=invalid_scope;
		if (OAuthConstants.UNSUPPORTED_GRANT_TYPE.equals(id)) 		error=unsupported_grant_type;
		if (OAuthConstants.UNSUPPORTED_RESPONSE_TYPE.equals(id)) 	error=unsupported_response_type;
		if (OAuthConstants.SERVER_ERROR.equals(id)) 				error=server_error;
		if (OAuthConstants.TEMPORARILY_UNAVAILABLE.equals(id)) 		error=temporarily_unavailable;		
	}
	
	public String getDescription(){
		return error_description;
	}
	
	public String getUrl(){
		return error_uri;
	}
	
	public int getError(){
		return error;
	}
	
	public String getRawResponse(){
		return rawResponse;
	}
}
