package com.flm.baseproject.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Generic table that serves as an example of an entity that stores data created by a given user
 * FLMChange
 *
 */
@Entity
@Table(name = "tb_generic")
public class Generic {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
    
	@Column(name = "name", nullable = false)
	private String name;
    
	@OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;
	
	
	public Generic() {
		super();
	}
	
	public Generic(String name, User user) {
		super();
		this.name = name;
		this.user = user;
	}

	
    public long getId() {
        return id;
    }
    
	public void setId(long id) {
        this.id = id;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Generic [id=" + id + ", name=" + name + ", user=" + user + "]";
	}
	
}
