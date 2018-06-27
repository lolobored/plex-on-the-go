package org.lolobored.plex.spring.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "users")
public class User {
	@Id
	@Column(unique = true)
	private String id;
	@Column(unique = true, name="username")
	private String userName;
	@Column(name="first_name")
	private String firstName;
	@Column(name="last_name")
	private String lastName;
	@Column(name="plex_login")
	@Basic(optional=true)
	private String plexLogin;
	@Transient
	private String plexPassword;
	@Column(name="plex_token")
	@Basic(optional=true)
	private String plexToken;
	@Column(name="home_directory")
	@Basic(optional=true)
	private String homeDirectory;
	@Column(name="admin")
	@Basic
	private boolean admin;

	@Transient // --> never store the password... leave that complexity to keycloak
	private String password;

	public boolean isPlexTokenValidated() {
		return plexToken != null;
	}
}
