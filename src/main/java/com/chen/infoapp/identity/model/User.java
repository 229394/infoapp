package com.chen.infoapp.identity.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_user")
public class User implements Serializable {

    @Id
    @Column(name = "user_id",length = 50)
    private String userId;

    @Column(name = "password",length = 50)
    private String password;

    @Column(name = "name",length = 50)
    private String name;

    @Column(name = "sex")
    //性别:1男,2女
    private short sex = 1;

    //用户与部门是多对一的关系
    @ManyToOne(fetch = FetchType.LAZY,targetEntity = Dept.class)
    @JoinColumn(name = "dept_id",referencedColumnName = "id",
                foreignKey = @ForeignKey(name = "user_dept"))//更改外键约束名
    private Dept dept;

    //用户与职位是多对一的关系
    @ManyToOne(fetch = FetchType.LAZY,targetEntity = Job.class)
    @JoinColumn(name = "job_code",referencedColumnName = "code",
                foreignKey = @ForeignKey(name = "user_job"))//更改外键约束
    private Job job;

    //邮箱
    @Column(name = "email",length = 50)
    private String email;

    //电话号码
    @Column(name = "tel",length = 50)
    private String tel;

    //手机号码
    @Column(name = "phone",length = 50)
    private String phone;

    //qq号码
    @Column(name = "qq_num",length = 50)
    private String qqNum;

    //问题编号
    @Column(name = "question")
    private Short question;

    //回答结果
    @Column(name = "answer",length = 200)
    private String answer;

    //状态:0新建,1审核,2不通过审核,3冻结
    @Column(name = "state")
    private Short state = 0;

    //用户创建者与用户是多对一的关系
    @ManyToOne(fetch = FetchType.LAZY,targetEntity = User.class)
    @JoinColumn(name = "creater",referencedColumnName = "user_id",
                foreignKey = @ForeignKey(name = "user_creater"))//更改外键约束名
    private User creater;//用户创建者

    //创建时间
    @Column(name = "create_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    //用户修改者与用户是多对一的关系
    @ManyToOne(fetch = FetchType.LAZY,targetEntity = User.class)
    @JoinColumn(name = "modifier",referencedColumnName = "user_id",
                foreignKey = @ForeignKey(name = "user_modifier"))//更改外键约束名
    private User modifier;//用户修改者

    //修改时间
    @Column(name = "modify_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifyDate;

    @ManyToOne(fetch = FetchType.LAZY,targetEntity = User.class)
    @JoinColumn(name = "checker",referencedColumnName = "user_id",
                foreignKey = @ForeignKey(name = "user_checker"))//更改外键约束名
    private User checker;//部门审核人

    //审核时间
    @Column(name = "check_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date checkDate;

    //用户与角色存在多对多的联系
    @ManyToMany(fetch = FetchType.LAZY,targetEntity = Role.class,mappedBy = "users")
    private Set<Role> roles = new HashSet<>();

    public User() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public short getSex() {
        return sex;
    }

    public void setSex(short sex) {
        this.sex = sex;
    }

    public Dept getDept() {
        return dept;
    }

    public void setDept(Dept dept) {
        this.dept = dept;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getQqNum() {
        return qqNum;
    }

    public void setQqNum(String qqNum) {
        this.qqNum = qqNum;
    }

    public Short getQuestion() {
        return question;
    }

    public void setQuestion(Short question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Short getState() {
        return state;
    }

    public void setState(Short state) {
        this.state = state;
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

    public User getChecker() {
        return checker;
    }

    public void setChecker(User checker) {
        this.checker = checker;
    }

    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}


