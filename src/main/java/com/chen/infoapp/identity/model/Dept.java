package com.chen.infoapp.identity.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "tb_dept")
public class Dept implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name",length = 50)
    private String name;

    @Column(name = "remark",length = 500)
    private String remark;

    @ManyToOne(fetch = FetchType.LAZY,targetEntity = User.class)
    @JoinColumn(name = "modifier",referencedColumnName = "user_id",
                foreignKey = @ForeignKey(name = "dept_modifier"))//更改外键约束
    private User modifier;//修改者

    @Column(name = "modify_date")
    private Date modifyDate;//修改时间

    @ManyToOne(fetch = FetchType.LAZY,targetEntity = User.class)
    @JoinColumn(name = "creater",referencedColumnName = "user_id",
                foreignKey = @ForeignKey(name = "dept_creater"))//更改外键约束
    private User creater;//创建者

    @Column(name = "create_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date create_date;//创建时间

    public Dept() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }
}
