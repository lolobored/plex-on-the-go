package org.lolobored.plex.spring.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "plex_users")
public class PlexUser {

	@Id
	@Column(name="user_id", unique = true)
	private String id;
	@Column(name="username")
	private String username;
	@Column(name="token")
	private String token;
	@Column(name="main")
	private Boolean mainUser;
	@Transient
	private String password;
	@Column(name="machine_id")
	private String machineId;
	@Column(name="machine_name")
	private String machineName;
	@Column(name="machine_token")
	private String machineToken;
}
