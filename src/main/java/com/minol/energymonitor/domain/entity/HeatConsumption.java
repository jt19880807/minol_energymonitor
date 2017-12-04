package com.minol.energymonitor.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;

public class HeatConsumption {
    /**
     * id
     */
    private int id;
    /**
     * 读数日期
     */
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm")
    private Timestamp date;
    /**
     * 项目ID
     */
    private int project_id;
    /**
     * 热耗
     */
    private double consumption;
    /**
     * 是否删除(0：未删除 1:删除)
     */
    private int isdeleted;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp create_time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public double getConsumption() {
        return consumption;
    }

    public void setConsumption(double consumption) {
        this.consumption = consumption;
    }

    public int getIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(int isdeleted) {
        this.isdeleted = isdeleted;
    }

    public Timestamp getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Timestamp create_time) {
        this.create_time = create_time;
    }
}
