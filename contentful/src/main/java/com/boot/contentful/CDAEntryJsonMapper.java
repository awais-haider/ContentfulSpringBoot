package com.boot.contentful;

import com.contentful.java.cda.CDAContentType;
import com.contentful.java.cda.CDAType;

import java.io.Serializable;
import java.util.Map;

public class CDAEntryJsonMapper implements Serializable {


    private String id;
    private Map<String, Object> rawFields;
    private Map<String, Object> attrs;
    private String locale;
    private CDAContentType cdaContentType;
    private CDAType type;

    public CDAEntryJsonMapper(String id, Map<String, Object> rawFields, Map<String, Object> attrs, String locale, CDAContentType cdaContentType, CDAType type) {
        this.id = id;
        this.rawFields = rawFields;
        this.attrs = attrs;
        this.locale = locale;
        this.cdaContentType = cdaContentType;
        this.type = type;
    }

    public CDAEntryJsonMapper() {

    }

    public Map<String, Object> getRawFields() {
        return rawFields;
    }

    public void setRawFields(Map<String, Object> rawFields) {
        this.rawFields = rawFields;
    }

    public Map<String, Object> getAttrs() {
        return attrs;
    }

    public void setAttrs(Map<String, Object> attrs) {
        this.attrs = attrs;
    }

    @Override
    public String toString() {
        return "CDAEntryJsonMapper{" +
                "id='" + id + '\'' +
                ", rawFields=" + rawFields +
                ", attrs=" + attrs +
                ", locale='" + locale + '\'' +
                ", cdaContentType=" + cdaContentType +
                ", type=" + type +
                '}';
    }
}
