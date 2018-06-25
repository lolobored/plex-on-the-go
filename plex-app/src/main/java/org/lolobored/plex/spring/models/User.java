package org.lolobored.plex.spring.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "plexuser")
public class User {
	@Id
	@Column(unique = true)
	private String id;
	@Column(unique = true)
	private String userName;
	@Column
	@Basic(optional=true)
	private String plexLogin;
	@Column
	@Basic(optional=true)
	private String plexPassword;
	@Column
	@Basic(optional=true)
	private String plexToken;
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	@Column
	private boolean plexTokenValidated;
	@Column
	@Basic(optional=true)
	private String homeDirectory;
	@Column
	@Basic
	private boolean admin;

	@Transient // --> never store the password... leave that complexity to keycloak
	private String password;
	@Transient // --> do not store user information... leave that complexity to keycloak
	private String firstName;
	@Transient // --> do not store user information... leave that complexity to keycloak
	private String lastName;

	public boolean isPlexTokenValidated() {
		return plexToken != null;
	}
}
