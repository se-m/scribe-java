package org.scribe.services;
import util.Base64;

public class InternalEncoder extends Base64Encoder {

	@Override
	public String encode(byte[] bytes) {		
		return Base64.encodeToString(bytes, false);
	}

	@Override
	public String getType() {		
		return "MigBase64";
	}
	public static boolean isPresent(){
		return true;
	}
}
