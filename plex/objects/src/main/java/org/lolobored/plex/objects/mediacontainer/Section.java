/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lolobored.plex.objects.mediacontainer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author laurentlaborde
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Section {
    @JsonProperty("MediaContainer")
    private MediaContainer mediaContainer;

    /**
     * @return the MediaContainer
     */
    public MediaContainer getMediaContainer() {
        return mediaContainer;
    }

    /**
     * @param MediaContainer the MediaContainer to set
     */
    public void setMediaContainer(MediaContainer mediaContainer) {
        this.mediaContainer = mediaContainer;
    }
}
