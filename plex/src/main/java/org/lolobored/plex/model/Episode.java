package org.lolobored.plex.model;


public class Episode extends Media{
  
  // tvshows only
  private Show show;
  private Season season;
  private Integer episode;

  public Episode() {
    type= EPISODE_TYPE;
  }
  
  /**
   * @return the show
   */
  public Show getShow() {
    return show;
  }

  /**
   * @param show the show to set
   */
  public void setShow(Show show) {
    this.show = show;
  }

  /**
   * @return the season
   */
  public Season getSeason() {
    return season;
  }

  /**
   * @param season the season to set
   */
  public void setSeason(Season season) {
    this.season = season;
  }

  /**
   * @return the episode
   */
  public Integer getEpisode() {
    return episode;
  }

  /**
   * @param episode the episode to set
   */
  public void setEpisode(Integer episode) {
    this.episode = episode;
  }

}

