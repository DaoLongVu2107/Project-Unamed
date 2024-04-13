package com.itboy.DACNPM.Enity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.sql.Date;

@Entity
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idDocument;
    private String symbolNumber;
    private Date date;
    private String describeOfDoc;
    private String issuingAuthority;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int version;
    private String field;
    private String fileUrl;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Document() {

    }

    public int getIdDocument() {
        return idDocument;
    }

    public void setIdDocument(int idDocument) {
        this.idDocument = idDocument;
    }

    public String getSymbolNumber() {
        return symbolNumber;
    }

    public void setSymbolNumber(String symbolNumber) {
        this.symbolNumber = symbolNumber;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescribeOfDoc() {
        return describeOfDoc;
    }

    public void setDescribeOfDoc(String describeOfDoc) {
        this.describeOfDoc = describeOfDoc;
    }

    public String getIssuingAuthority() {
        return issuingAuthority;
    }

    public void setIssuingAuthority(String issuingAuthority) {
        this.issuingAuthority = issuingAuthority;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public Document(int idDocument, String symbolNumber, Date date, String describeOfDoc, String issuingAuthority, String version, String field, String fileUrl) {
        this.idDocument = idDocument;
        this.symbolNumber = symbolNumber;
        this.date = date;
        this.describeOfDoc = describeOfDoc;
        this.issuingAuthority = issuingAuthority;
        this.version = version;
        this.field = field;
        this.fileUrl = fileUrl;
    }

    public Document(int idDocument, String symbolNumber, Date date, String describeOfDoc, String issuingAuthority, int version, String field, String fileUrl) {
        this.idDocument = idDocument;
        this.symbolNumber = symbolNumber;
        this.date = date;
        this.describeOfDoc = describeOfDoc;
        this.issuingAuthority = issuingAuthority;
        this.version = version;
        this.field = field;
        this.fileUrl = fileUrl;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
