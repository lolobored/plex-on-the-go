/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.lolobored.plex.apis.token;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 * @author laurentlaborde
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccessToken {
  private User user;

  /**
   * @return the user
   */
  public User getUser() {
    return user;
  }

  /**
   * @param user the user to set
   */
  public void setUser(User user) {
    this.user = user;
  }
}
