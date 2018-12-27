package com.jeff4w.example.restapi.domain;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

/**
 * @author Lu Weijian
 * @description 备注
 * @email lwj@kapark.cn
 * @date 2018-12-27 9:18
 */

@Entity // 实体
@Table(name = "t_student") // 数据库表名
public class Student {
    @Id // 主键
    @GeneratedValue // 自增
    private Integer id;

    @NotEmpty(message = "学生姓名不能为空") // 表单验证
    @Column(length = 20) // 字段长度
    private String t_name;

    @Column(length = 20) // 字段长度
    private String major;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getT_name() {
        return t_name;
    }

    public void setT_name(String t_name) {
        this.t_name = t_name;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

}
