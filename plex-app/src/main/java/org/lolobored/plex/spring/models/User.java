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
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String userName;
    @Column
    private String plexLogin;
    private String plexPassword;
    @Column
    private String plexToken;
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private boolean plexTokenValidated;

    public boolean isPlexTokenValidated() {
        return plexToken!=null;
    }
}
