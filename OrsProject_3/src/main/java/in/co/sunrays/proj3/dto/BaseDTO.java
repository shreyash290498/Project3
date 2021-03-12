package in.co.sunrays.proj3.dto;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Parent class of all DTOs in application. It contains generic attributes.
 *
 * @author SUNRAYS Technologies
 * @version 1.0
 * @Copyright (c) SUNRAYS Technologies
 *
 */

public abstract class BaseDTO implements Serializable, DropdownList,
        Comparable<BaseDTO> {

    /**
     * Non Business primary key
     */
    protected long id;

    /**
     * Contains USER ID who created this database record
     */
    protected String createdBy;

    /**
     * Contains USER ID who modified this database record
     */
    protected String modifiedBy;

    /**
     * Contains Created Timestamp of database record
     */
    protected Timestamp createdDatetime;

    /**
     * Contains Modified Timestamp of database record
     */
    protected Timestamp modifiedDatetime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Timestamp getCreatedDatetime() {
        return createdDatetime;
    }

    public void setCreatedDatetime(Timestamp createdDatetime) {
        this.createdDatetime = createdDatetime;
    }

    public Timestamp getModifiedDatetime() {
        return modifiedDatetime;
    }

    public void setModifiedDatetime(Timestamp modifiedDatetime) {
        this.modifiedDatetime = modifiedDatetime;
    }

    public int compareTo(BaseDTO next) {
        return getValue().compareTo(next.getValue());
    }
}
