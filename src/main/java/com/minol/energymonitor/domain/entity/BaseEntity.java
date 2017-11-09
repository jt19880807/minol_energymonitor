package com.minol.energymonitor.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;

public class BaseEntity {
    /**
     *主键ID
     */
    private int id;
    /**
     * 是否删除(0：未删除 1:删除)
     */
    private int isdeleted;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp create_time;
    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp update_time;
    /**
     * 创建人
     */
    private int create_by;
    /**
     * 修改人
     */
    private int update_by;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Timestamp getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Timestamp update_time) {
        this.update_time = update_time;
    }

    public int getCreate_by() {
        return create_by;
    }

    public void setCreate_by(int create_by) {
        this.create_by = create_by;
    }

    public int getUpdate_by() {
        return update_by;
    }

    public void setUpdate_by(int update_by) {
        this.update_by = update_by;
    }
}
