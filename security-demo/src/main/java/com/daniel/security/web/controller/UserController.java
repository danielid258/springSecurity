package com.daniel.security.web.controller;

import com.daniel.security.exception.UserNotExistException;
import com.daniel.security.web.dto.User;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * on 5/24/2018.
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @DeleteMapping("/{id:\\d+}")
    public void delete(@PathVariable Integer id) {
        System.out.println(id);
    }

    @PutMapping("/{id:\\d+}")
    public User updateUser(@PathVariable Integer id, @Valid @RequestBody User user, BindingResult errors) {
        if (errors.hasErrors())
            errors.getAllErrors().forEach(error -> {
                FieldError fieldError = (FieldError) error;
                String message = fieldError.getField() + ": " + error.getDefaultMessage();
                System.out.println(message);
            });

        user.setId(id);
        return user;
    }

    /**
     * @RequestBody 把请求中json串的字段映射到User对象的对应属性上 如果不加@RequestBody User对象的全部属性都是null
     *
     * @Valid 属性校验 校验请求json中的字段是否满足对象属性的约束
     *
     * BindingResult 收集@Valid不满足约束的校验信息
     *
     * @param user
     * @return
     */
    @PostMapping
    public User createUser(@Valid @RequestBody User user, BindingResult errors) {
        if (errors.hasErrors())
            errors.getAllErrors().forEach(error -> System.out.println(error.getDefaultMessage()));

        System.out.println(ReflectionToStringBuilder.toString(user, ToStringStyle.MULTI_LINE_STYLE));

        user.setId(10);
        return user;
    }

    /**
     * @PathVariable 获取URI中对应位置上的参数 用法和@RequestParam类似
     * 支持正则表达式
     *      {id:\d+} id只接收数字
     *
     * @param id
     * @return
     */
    @JsonView(User.UserDetailView.class)
    @GetMapping("/{id:\\d+}")
    public User getUserById(@ApiParam("user id") @PathVariable Integer id) {
        System.out.println("input getUserById method ... ");

        if (id==1)
            throw new UserNotExistException(id);

        return new User(id, "jack", "ppp");
    }

    /**
     * Pageable 分页对象
     * @PageableDefault
     *
     * @param page
     * @return
     */
    @JsonView(User.UserSimpleView.class)
    @GetMapping
    //public List<User> query(Pageable page) {
    public List<User> query(@PageableDefault(page = 1,size = 10,sort = "id,ASC") Pageable page) {
        System.out.println(ReflectionToStringBuilder.toString(page, ToStringStyle.MULTI_LINE_STYLE));

        List<User> users = new ArrayList<>();

        users.add(new User(1, "daniel", "111"));
        users.add(new User(2, "tom", "222"));
        users.add(new User(3, "jack", "333"));

        return users;
    }

    /**
     * RequestParam
     *
     *  不指定name 请求参数名必须和参数名[userName]相同
     *  指定name 请求参数名必须和name属性值[nickName]相同
     *
     * @param userName
     * @return
     */
    @GetMapping("/name")
    public User getUserByName(@RequestParam(name = "nickName",defaultValue = "jack",required = false) String userName) {
        System.out.println(userName);
        return (new User(1, userName, "111"));
    }

    /**
     * 使用对象接受请求参数 不需要@RequestParam springmvc会把请求参数绑定到对象对应的属性上
     *
     * @param user
     * @return
     */
    @GetMapping("/obj")
    public List<User> getUserByCondition(User user) {

        System.out.println(ReflectionToStringBuilder.toString(user, ToStringStyle.MULTI_LINE_STYLE));

        return null;
    }
}
