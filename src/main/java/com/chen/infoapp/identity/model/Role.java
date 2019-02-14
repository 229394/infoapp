package com.chen.infoapp.identity.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_role")
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name",length = 50)
    private String name;//角色名字

    @Column(name = "remark",length = 500)
    private String remark;//备注

    @ManyToOne(fetch = FetchType.LAZY,targetEntity = User.class)
    @JoinColumn(name = "creater",referencedColumnName = "user_id",
                foreignKey = @ForeignKey(name = "role_creater"))//更改外键约束名
    //角色创建人与用户多对一的关系
    private User creater;

    @Column(name = "create_date")
    @Temporal(TemporalType.TIMESTAMP)
    //创建时间
    private Date createDate;

    @ManyToOne(fetch = FetchType.LAZY,targetEntity = User.class)
    @JoinColumn(name = "modifier",referencedColumnName = "user_id",
                foreignKey = @ForeignKey(name = "role_modifer"))//更改外键约束名
    private User modifier;//角色修改人与用户是多对一的关系

    @Column(name = "modify_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifyDate;//修改时间
    //角色与用户之间是多对多的关系

    @ManyToMany(fetch = FetchType.LAZY,targetEntity = User.class)
    @JoinTable(name = "tb_user_role",joinColumns = @JoinColumn(name = "role_id",referencedColumnName = "id"),
                                        inverseJoinColumns = @JoinColumn(name = "user_id",referencedColumnName = "user_id"))
    private Set<User> users = new HashSet<>();

    public Role() {
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

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
