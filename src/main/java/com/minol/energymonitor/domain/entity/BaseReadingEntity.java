package com.minol.energymonitor.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;

public class BaseReadingEntity {
    /**
     *主键ID
     */
    private int id;
    /**
     * 表计ID
     */
    private int meter_id;
    /**
     * 读数日期

     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp date;
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

    public int getMeter_id() {
        return meter_id;
    }

    public void setMeter_id(int meter_id) {
        this.meter_id = meter_id;
    }
}
