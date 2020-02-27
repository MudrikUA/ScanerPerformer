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
@Table(name = "scan_bug_records", uniqueConstraints = {
    @UniqueConstraint(columnNames = "ID")})
public class ScanBug implements Serializable {

    private static final long serialVersionUID = -1798070738699354676L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private Integer id;

    @Column(name = "index_val")
    private Integer index;

    @Column(name = "created_date")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date creationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "panel_c")
    private Scan currentPanel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "panel_l")
    private Scan leftPanel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "panel_r")
    private Scan rightPanel;

    public ScanBug() {
        creationDate = new Date();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Scan getLeftPanel() {
        return leftPanel;
    }

    public void setLeftPanel(Scan leftPanel) {
        this.leftPanel = leftPanel;
    }

    public Scan getRightPanel() {
        return rightPanel;
    }

    public void setRightPanel(Scan rightPanel) {
        this.rightPanel = rightPanel;
    }

    public Scan getCurrentPanel() {
        return currentPanel;
    }

    public void setCurrentPanel(Scan currentPanel) {
        this.currentPanel = currentPanel;
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
        final ScanBug other = (ScanBug) obj;
        return !(!Objects.equals(this.id, other.id) && (this.id == null || !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "ScanBug{" + "id=" + id + ", index=" + index + ", creationDate=" + creationDate + ", currentPanel=" + currentPanel + ", leftPanel=" + leftPanel + ", rightPanel=" + rightPanel + '}';
    }

}
