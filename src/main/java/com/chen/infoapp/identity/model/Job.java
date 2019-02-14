package com.chen.infoapp.identity.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "tb_job")
public class Job implements Serializable {

    @Id
    @Column(name = "code",length = 100)
    //职位编号
    private String code;

    @Column(name = "name",length = 50)
    private String name;

    @Column(name = "remark",length = 300)
    private String remark;

    public Job() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
