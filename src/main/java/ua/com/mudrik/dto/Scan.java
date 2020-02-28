package ua.com.mudrik.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.UniqueConstraint;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Mudrik
 */
@Entity
@Table(name = "scan_records", uniqueConstraints = {
    @UniqueConstraint(columnNames = "ID")})
public class Scan implements Serializable {

    private static final long serialVersionUID = -1798070786993154676L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private Integer id;

    @Column(name = "index_val")
    private Integer index;

    @Column(name = "scaner_name", nullable = false, length = 100)
    private String scanerName;

    @Column(name = "scan_code", nullable = false, length = 100)
    private String scanCode;

    @Column(name = "created_date")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date creationDate;

    @Column(name = "panel_pos")
    private Integer panelPosition;

    @Column(name = "lamin", length = 100)
    private String laminName;

    @Column(name = "bd")
    private Integer bugCount;

    @Column(name = "res")
    private String reservedData;

    public Scan() {
        creationDate = new Date();
        bugCount = 0;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getScanerName() {
        return scanerName;
    }

    public void setScanerName(String scanerName) {
        this.scanerName = scanerName;
    }

    public String getScanCode() {
        return scanCode;
    }

    public void setScanCode(String scanCode) {
        this.scanCode = scanCode;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getPanelPosition() {
        return panelPosition;
    }

    public void setPanelPosition(Integer panelPosition) {
        this.panelPosition = panelPosition;
    }

    public String getLaminName() {
        return laminName;
    }

    public void setLaminName(String laminName) {
        this.laminName = laminName;
    }

    public Integer getBugCount() {
        return bugCount;
    }

    public void setBugCount(Integer bugCount) {
        this.bugCount = bugCount;
    }

    public String getReservedData() {
        return reservedData;
    }

    public void setReservedData(String reservedData) {
        this.reservedData = reservedData;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Scan other = (Scan) obj;
        return !(!Objects.equals(this.id, other.id) && (this.id == null || !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "Scan{" + "id=" + id + ", index=" + index + ", scanerName=" + scanerName + ", scanCode=" + scanCode + ", creationDate=" + creationDate + ", panelPosition=" + panelPosition + ", laminName=" + laminName + ", bugCount=" + bugCount + ", reservedData=" + reservedData + '}';
    }

}
