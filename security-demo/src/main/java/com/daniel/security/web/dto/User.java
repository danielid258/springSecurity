package com.daniel.security.web.dto;

import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Past;
import java.util.Date;

/**
 * on 5/24/2018.
 */
public class User {
    /**
     * @JsonView 限定在不同的视图中对于相同类型的对象 返回不同的属性
     *      如 password属性只在用户详情视图中返回 但用户列表视图中不返回
     *
     * 实现步骤
     *  1 定义多个视图接口
     *  2 在各个属性上加上@JsonView并指定对应视图接口
     *
     */
    public interface UserSimpleView {}
    public interface UserDetailView extends UserSimpleView{}

    private Integer id;

    private String userName;

    @NotBlank(message = "password can not be null or empty")
    private String password;

    @Past(message = "birthday date must be past")
    private Date birthday;

    public User() {
    }

    public User(Integer id, String userName, String password) {
        this.id = id;
        this.userName = userName;
        this.password = password;
    }

    @JsonView(UserSimpleView.class)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @JsonView(UserSimpleView.class)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @JsonView(UserDetailView.class)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @JsonView(UserSimpleView.class)
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
