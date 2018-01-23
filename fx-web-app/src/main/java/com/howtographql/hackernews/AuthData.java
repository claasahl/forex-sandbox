package com.howtographql.hackernews;

import java.util.Map;

public class AuthData {
    private String email;
    private String password;

    public AuthData() {
    }

    public AuthData(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	@Override
	public String toString() {
		return "AuthData [email=" + email + ", password=" + password + "]";
	}
	
	public static AuthData fromMap(Map<String, Object> map) {
		if(map == null)
			return null;
		String email = (String) map.get("email");
	    String password = (String) map.get("password");
	    return new AuthData(email, password);
	}
}
