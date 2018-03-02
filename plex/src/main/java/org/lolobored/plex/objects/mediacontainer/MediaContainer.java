/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lolobored.plex.objects.mediacontainer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigInteger;
import java.util.List;

import org.lolobored.plex.objects.metadata.Metadata;

/**
 * @author laurentlaborde
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MediaContainer {
    private BigInteger size;
    private Boolean allowSync;
    private String title1;
    @JsonProperty("Directory")
    private List<Directory> directory;
    @JsonProperty("Metadata")
    private List<Metadata> metadata;

    /**
     * @return the size
     */
    public BigInteger getSize() {
        return size;
    }

    /**
     * @param size the size to set
     */
    public void setSize(BigInteger size) {
        this.size = size;
    }

    /**
     * @return the allowSync
     */
    public Boolean getAllowSync() {
        return allowSync;
    }

    /**
     * @param allowSync the allowSync to set
     */
    public void setAllowSync(Boolean allowSync) {
        this.allowSync = allowSync;
    }

    /**
     * @return the title1
     */
    public String getTitle1() {
        return title1;
    }

    /**
     * @param title1 the title1 to set
     */
    public void setTitle1(String title1) {
        this.title1 = title1;
    }

    /**
     * @return the Directory
     */
    public List<Directory> getDirectory() {
        return directory;
    }

    /**
     * @param Directory the Directory to set
     */
    public void setDirectory(List<Directory> directory) {
        this.directory = directory;
    }

    /**
     * @return the metadata
     */
    public List<Metadata> getMetadata() {
        return metadata;
    }

    /**
     * @param metadata the metadata to set
     */
    public void setMetadata(List<Metadata> metadata) {
        this.metadata = metadata;
    }
}
