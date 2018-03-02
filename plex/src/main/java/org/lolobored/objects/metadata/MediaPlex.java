/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lolobored.objects.metadata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * @author laurentlaborde
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MediaPlex {
    @JsonProperty("Part")
    private List<Part> part;

    /**
     * @return the part
     */
    public List<Part> getPart() {
        return part;
    }

    /**
     * @param part the part to set
     */
    public void setPart(List<Part> part) {
        this.part = part;
    }
}
