package com.flm.baseproject.enumerator;

@SuppressWarnings("rawtypes")
public enum Profiles {

	PROFILE_ADMIN("Admin", "Admin", new Enum[]
			{ 
					Roles.ROLE_USER_LIST, Roles.ROLE_USER_SAVE, Roles.ROLE_USER_UPDATE, Roles.ROLE_USER_DELETE,
					Roles.ROLE_GENERIC_LIST, Roles.ROLE_GENERIC_SAVE, Roles.ROLE_GENERIC_UPDATE, Roles.ROLE_GENERIC_DELETE
			}
	),
	PROFILE_REGULAR("Regular", "Regular", new Enum[]
			{ 
					Roles.ROLE_GENERIC_LIST, Roles.ROLE_GENERIC_SAVE, Roles.ROLE_GENERIC_UPDATE, Roles.ROLE_GENERIC_DELETE
			}
	);
	
	
	private String name;
	private String description;
	private Enum[] roles;

	private Profiles(String name, String description, Enum[] roles) {
		this.name = name;
		this.description = description;
		this.roles = roles;
	}

	public String getDescription() {
		return description;
	}

	public String getName() {
		return name;
	}

	public Enum[] getRoles() {
		return roles;
	}
	
}
