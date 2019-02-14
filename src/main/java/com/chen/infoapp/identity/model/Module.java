package com.chen.infoapp.identity.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "tb_module")
public class Module implements Serializable {

    @Id
    @Column(name = "code",length = 100)
    private String code;

    @Column(name = "name",length = 50)
    private String name;

    @Column(name = "url",length = 100)
    private String url;

    @Column(name = "remark",length = 500)
    private String remark;

    //模块修改人与用户是多对一的关系
    @ManyToOne(fetch = FetchType.LAZY,targetEntity = User.class)
    @JoinColumn(name = "modifier",referencedColumnName = "user_id",
                foreignKey =@ForeignKey(name = "module_modifier"))//更改外键约束
    private User modifier;

    @Column(name = "modify_date")
    @Temporal(TemporalType.TIMESTAMP)
    //修改的时间
    private Date modifyDate;

    @ManyToOne(fetch = FetchType.LAZY,targetEntity = User.class)
    @JoinColumn(name = "creater",referencedColumnName = "user_id",
                foreignKey = @ForeignKey(name = "module_creater"))//更改外键约束
    //模块创建人与用户是多对一的关系
    private User creater;

    //创建时间
    @Column(name = "create_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    public Module() {
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public User getModifier() {
        return modifier;
    }

    public void setModifier(User modifier) {
        this.modifier = modifier;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public User getCreater() {
        return creater;
    }

    public void setCreater(User creater) {
        this.creater = creater;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
