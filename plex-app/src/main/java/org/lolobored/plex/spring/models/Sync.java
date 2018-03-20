package org.lolobored.plex.spring.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "sync")
public class Sync {
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column
	private String userName;
	@Column
	private String quality;
	@Column
	private String elasticSearchId;
	@Column
	private Integer percentageProgress;
	@Column
	private String status;

}
