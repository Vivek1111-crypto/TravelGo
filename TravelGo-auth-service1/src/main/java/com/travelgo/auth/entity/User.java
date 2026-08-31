
//package com.travelgo.auth.entity;
package com.travelgo.auth.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="user")
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
private long id;
private String firstName;
private String lastName;
@Column(unique=true)
private String email;

private String password;

@Enumerated(EnumType.STRING)
private Role role;

public User() {
	super();
	// TODO Auto-generated constructor stub
}

public User(long id, String firstName, String lastName, String email, String password, Role role) {
	super();
	this.id = id;
	this.firstName = firstName;
	this.lastName = lastName;
	this.email = email;
	this.password = password;
	this.role = role;
}

public long getId() {
	return id;
}

public void setId(long id) {
	this.id = id;
}

public String getFirstName() {
	return firstName;
}

public void setFirstName(String firstame) {
	this.firstName = firstame;
}

public String getLastName() {
	return lastName;
}

public void setLastName(String lastName) {
	this.lastName = lastName;
}

public String getEmail() {
	return email;
}

public void setEmail(String email) {
	this.email = email;
}

public String getPassword() {
	return password;
}

public void setPassword(String password) {
	this.password = password;
}

public Role getRole() {
	return role;
}

public void setRole(Role role) {
	this.role = role;
}



}
