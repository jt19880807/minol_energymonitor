package com.minol.energymonitor.domain.entity;

public class Project extends BaseEntity {

    /**
     * 项目名称
     */
    private String name;
    /**
     * 负责人
     */
    private String principal;
    /**
     * 联系人
     */
    private String contact;
    /**
     * 建设单位
     */
    private String party_a;
    /**
     * 施工单位
     */
    private String construction_unit;
    /**
     * 监理单位
     */
    private String supervisor_unit;
    /**
     * 设计单位
     */
    private String design_unit;
    /**
     * 使用单位
     */
    private String use_unit;
    /**
     * 所属地区
     */
    private String district;
    /**
     * 详细地址
     */
    private String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getParty_a() {
        return party_a;
    }

    public void setParty_a(String party_a) {
        this.party_a = party_a;
    }

    public String getConstruction_unit() {
        return construction_unit;
    }

    public void setConstruction_unit(String construction_unit) {
        this.construction_unit = construction_unit;
    }

    public String getSupervisor_unit() {
        return supervisor_unit;
    }

    public void setSupervisor_unit(String supervisor_unit) {
        this.supervisor_unit = supervisor_unit;
    }

    public String getDesign_unit() {
        return design_unit;
    }

    public void setDesign_unit(String design_unit) {
        this.design_unit = design_unit;
    }

    public String getUse_unit() {
        return use_unit;
    }

    public void setUse_unit(String use_unit) {
        this.use_unit = use_unit;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
