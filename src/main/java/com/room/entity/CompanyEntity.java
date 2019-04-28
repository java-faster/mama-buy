package com.room.entity;

import java.io.Serializable;

public class CompanyEntity implements Serializable{
    private int company_id;
    private String company_name;
    private int manager_id;
    private String manager_name;
    private String invite_code;

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public void setManager_id(int manager_id) {
        this.manager_id = manager_id;
    }

    public void setManager_name(String manager_name) {
        this.manager_name = manager_name;
    }

    public void setInvite_code(String invite_code) {
        this.invite_code = invite_code;
    }

    public int getCompany_id() {
        return company_id;
    }

    public String getCompany_name() {
        return company_name;
    }

    public int getManager_id() {
        return manager_id;
    }

    public String getManager_name() {
        return manager_name;
    }

    public String getInvite_code() {
        return invite_code;
    }

}
