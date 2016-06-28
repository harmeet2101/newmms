package com.mbopartners.mbomobile.data.db.generated.model;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "TABLE_COMPANY".
 */
public class TableCompany {

    private Long id;
    /** Not-null value. */
    private String MboId;
    /** Not-null value. */
    private String Name;

    public TableCompany() {
    }

    public TableCompany(Long id) {
        this.id = id;
    }

    public TableCompany(Long id, String MboId, String Name) {
        this.id = id;
        this.MboId = MboId;
        this.Name = Name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /** Not-null value. */
    public String getMboId() {
        return MboId;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setMboId(String MboId) {
        this.MboId = MboId;
    }

    /** Not-null value. */
    public String getName() {
        return Name;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setName(String Name) {
        this.Name = Name;
    }

}
