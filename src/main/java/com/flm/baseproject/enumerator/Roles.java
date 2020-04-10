package com.flm.baseproject.enumerator;

public enum Roles {
	
	//User
	ROLE_USER_LIST("/users/**", "user list"),
	ROLE_USER_SAVE("/users/**", "user save"),
	ROLE_USER_UPDATE("/users/**", "user update"),
	ROLE_USER_DELETE("/users/**", "user delete"),
	
	//Profile
	ROLE_PROFILE_LIST("/profiles/**", "profile list");
	
	//Generic Page
//	ROLE_GENERIC_LIST("/generics/**", "generics list"),
//	ROLE_GENERIC_SAVE("/generics/**", "generics save"),
//	ROLE_GENERIC_UPDATE("/generics/**", "generics update"),
//	ROLE_GENERIC_DELETE("/generics/**", "generics delete");
	
	private String url;
	private String description;

	private Roles(String url, String description) {
		this.url = url;
		this.description = description;
	}

	public static Roles find(String role) {
		for (Roles r : values()) {
			if (r.toString().equals(role)) {
				return r;
			}
		}
		return null;
	}

	public String getUrl() {
		return url;
	}

	public String getDescription() {
		return description;
	}

}
