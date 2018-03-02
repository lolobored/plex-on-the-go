package org.lolobored.model;

import org.apache.commons.lang3.StringUtils;


public class Season {
  private Integer seasonNumber;
  private String summary;
  

  /**
   * @return the seasonNumber
   */
  public Integer getSeasonNumber() {
    return seasonNumber;
  }

  /**
   * @param seasonNumber the seasonNumber to set
   */
  public void setSeasonNumber(Integer seasonNumber) {
    this.seasonNumber = seasonNumber;
  }
  
  public String formatSeasonNumber(){
    return StringUtils.leftPad(seasonNumber.toString(), 2, "0");
  }
}
