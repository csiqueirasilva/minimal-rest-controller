package br.uva.model.user;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class RoleUsuario implements Serializable, Comparable {
	
	@Id
    @GeneratedValue
	@Column
	private Long id;
	
	@Column(unique = true)
	private String role;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}
	
	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public int compareTo(Object o) {
		return this.role.compareTo(((RoleUsuario) o).getRole());
	}
	
}