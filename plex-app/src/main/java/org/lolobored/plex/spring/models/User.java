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
	@Column(name="home_directory")
	@Basic(optional=true)
	private String homeDirectory;
	@Column(name="ownership")
	@Basic(optional=true)
	private String ownership;
	@Column(name="admin")
	@Basic
	private boolean admin;
	@OneToOne
	@JoinColumn(name = "plex_user_id")
	@Basic(optional=true)
	private PlexUser plexUser;

	@Transient // --> never store the password... leave that complexity to keycloak
	private String password;

}
