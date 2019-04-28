package com.room.entity;


/**
 *Author Lotus[www.coder520.com]
 * 公司信息实体类
*/
public class ComListEntity {

    private String comName;

    private String relName;

    private String telNum;

    private String eMailNum;

    public String getComName() {
        return comName;
    }

    public void setComName(String comName) {
        this.comName = comName;
    }

    public String getRelName() {
        return relName;
    }

    public void setRelName(String relName) {
        this.relName = relName;
    }

    public String getTelNum() {
        return telNum;
    }

    public void setTelNum(String telNum) {
        this.telNum = telNum;
    }

    public String geteMailNum() {
        return eMailNum;
    }

    public void seteMailNum(String eMainNum) {
        this.eMailNum = eMainNum;
    }
}
