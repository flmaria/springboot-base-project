package com.flm.baseproject.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name = "tb_user", uniqueConstraints = { @UniqueConstraint(columnNames = { "login" }) })
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotEmpty
	@Column(name = "name", nullable = false, length = 40)
	private String name;

	@NotEmpty
	@Column(name = "login", nullable = false, length = 40)
	private String login;

	@NotEmpty
	@Email
	@Column(name = "email", nullable = false, length = 40)
	private String email;

	@JsonIgnore
	@Column(name = "password", nullable = false, length = 100)
	private String password;
	
	@Transient
	private String newPassword;
	
	@OneToOne
    @JoinColumn(name = "profile_id", referencedColumnName = "id", nullable = false)
    private Profile profile;
	
}
