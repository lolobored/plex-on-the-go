/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lolobored.plex.apis.token;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author laurentlaborde
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    private String authentication_token;

    /**
     * @return the authentication_token
     */
    public String getAuthentication_token() {
        return authentication_token;
    }

    /**
     * @param authentication_token the authentication_token to set
     */
    public void setAuthentication_token(String authentication_token) {
        this.authentication_token = authentication_token;
    }
}
