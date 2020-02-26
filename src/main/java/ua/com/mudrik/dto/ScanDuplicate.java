package ua.com.mudrik.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "scan_duplicate", uniqueConstraints = {
    @UniqueConstraint(columnNames = "ID")})
public class ScanDuplicate implements Serializable {

    private static final long serialVersionUID = -1798075738699354676L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private Integer id;

    @Column(name = "createdDate")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date creationDate;

    @Column(name = "scan_code", nullable = false, length = 100)
    private String scanCode;

    @Column(name = "lamin", length = 100)
    private String laminName;

    @Column(name = "scaner_name", nullable = false, length = 100)
    private String scanerName;

    @Column(name = "new", nullable = false, length = 100)
    private Boolean isNewDupl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "duplicate_scan")
    private Scan duplicateScan;

    public ScanDuplicate() {
        creationDate = new Date();
        isNewDupl = true;
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

    public String getLaminName() {
        return laminName;
    }

    public void setLaminName(String laminName) {
        this.laminName = laminName;
    }

    public Scan getDuplicateScan() {
        return duplicateScan;
    }

    public void setDuplicateScan(Scan duplicateScan) {
        this.duplicateScan = duplicateScan;
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
        final ScanDuplicate other = (ScanDuplicate) obj;
        return !(!Objects.equals(this.id, other.id) && (this.id == null || !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "ScanDuplicate{" + "id=" + id + ", creationDate=" + creationDate + ", scanCode=" + scanCode + ", laminName=" + laminName + ", scanerName=" + scanerName + ", duplicateScan=" + duplicateScan + '}';
    }

}
