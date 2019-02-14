package com.chen.infoapp.identity.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "tb_popedom")
public class Popedom implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    //权限与模块是多对一的关系
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Module.class)
    @JoinColumn(name = "module_code", referencedColumnName = "code",
            foreignKey = @ForeignKey(name = "popedom_module"))//更改外键约束名
    private Module module;

    //权限与操作是多对一关系
    @ManyToOne(fetch = FetchType.LAZY,targetEntity = Module.class)
    @JoinColumn(name = "opera_code",referencedColumnName = "code",
                foreignKey = @ForeignKey(name = "popedom_opera"))//更改外键约束名
    private Module opera;

    //权限与角色是多对一的关系
    @ManyToOne(fetch = FetchType.LAZY,targetEntity = Role.class)
    @JoinColumn(name = "role_id",referencedColumnName = "id",
                foreignKey = @ForeignKey(name = "popedom_role"))//更改外键约束名
    private Role role;

    //权限创建人与用户是多对一的关系
    @ManyToOne(fetch = FetchType.LAZY,targetEntity = User.class)
    @JoinColumn(name = "creater",referencedColumnName = "user_id",
                foreignKey = @ForeignKey(name = "popedom_creater"))//更改外键约束名
    private User creater;

    //创建时间
    @Column(name = "create_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    public Popedom() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public Module getOpera() {
        return opera;
    }

    public void setOpera(Module opera) {
        this.opera = opera;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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

