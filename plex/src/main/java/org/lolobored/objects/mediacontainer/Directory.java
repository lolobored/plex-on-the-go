/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lolobored.objects.mediacontainer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * @author laurentlaborde
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Directory {
    private String key;
    private String type;
    private String title;
    @JsonProperty("Location")
    private List<DirectoryLocation> location;

    /**
     * @return the key
     */
    public String getKey() {
        return key;
    }

    /**
     * @param key the key to set
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the location
     */
    public List<DirectoryLocation> getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(List<DirectoryLocation> location) {
        this.location = location;
    }
}
