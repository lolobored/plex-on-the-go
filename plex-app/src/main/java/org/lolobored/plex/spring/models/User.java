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
    public static String ADMIN_ROLE="Admin";
    public static String USER_ROLE="User";

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String userName;
    @Column
    private String password;
    private String role;
    @Column
    private String plexLogin;
    @Column
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
