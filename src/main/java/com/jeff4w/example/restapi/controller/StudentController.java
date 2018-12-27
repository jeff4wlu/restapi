package com.jeff4w.example.restapi.controller;

import com.jeff4w.example.restapi.common.ResponseResult;
import com.jeff4w.example.restapi.common.RestResultGenerator;
import com.jeff4w.example.restapi.common.Utils;
import com.jeff4w.example.restapi.domain.Student;
import com.jeff4w.example.restapi.service.StudentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


/**
 * @author Lu Weijian
 * @description 备注
 * @email lwj@kapark.cn
 * @date 2018-12-27 9:49
 */
@RestController
@RequestMapping("/api/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    /**
     * 显示所有
     * url:"http://localhost/student/findall"
     *
     * @return
     */
    @GetMapping(value = "")
    public ResponseResult<List<Student>> findAllStudent() {
        List<Student> all = studentService.findAllStudent();
        return RestResultGenerator.genSuccessResult(all);
    }


    /**
     * 查找 restful 风格
     * url:"http://localhost/student/findone/1"
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    public ResponseResult<Student> findStudent(@PathVariable("id") Integer id) {
        Student student = studentService.findStudentById(id).get();
        return RestResultGenerator.genSuccessResult(student);
    }


    /**
     * 删除 restful 风格
     * url:"http://localhost/student/deleteone/4"
     * 注意无法通过浏览器的链接来模拟检验,可以通过 jquery的 $.ajax方法，并type="delete"
     *
     * @param id
     */
    @DeleteMapping(value = "/{id}")
    public ResponseResult deleteStudent(@PathVariable Integer id) {
        studentService.delStudentById(id);
        return RestResultGenerator.genSuccessResult();
    }


    /**
     * 增加 restful 风格
     * url:"http://localhost/student/addone"
     * 通过<form>表单模拟验证
     *
     * @param student
     */
    @PostMapping(value = "")
    public ResponseResult<Student> addStudent(@Valid @RequestBody Student student) {
        Student save = studentService.addStudent(student);
        return RestResultGenerator.genSuccessResult(save);
    }


    /**
     * 修改 restful 风格
     * url:"http://localhost/student/updateone"
     * 验证：可以通过 jquery的 $.ajax方法，并type="put",同时注意data形式——A=a&B=b&C=c
     *
     * @param student
     */
    @PutMapping(value = "/{id}")
    public ResponseResult<Student> updateStudentAll(@PathVariable Integer id, @Valid @RequestBody Student newStudent) {
        Student student = studentService.findStudentById(id).get();
        // copy all new user props to user except id
        BeanUtils.copyProperties(newStudent, student, "id");
        student = studentService.addStudent(student);

        return RestResultGenerator.genSuccessResult(student);
    }

    @PatchMapping(value = "/{id}")
    public ResponseResult<Student> update(@PathVariable Integer id, @Valid @RequestBody Student newStudent) throws Exception {
        Student student = studentService.findStudentById(id).get();
        // copy all new user props to user except null props
        BeanUtils.copyProperties(newStudent, student, Utils.getNullPropertyNames(newStudent));
        student = studentService.addStudent(student);

        return RestResultGenerator.genSuccessResult(student);
    }

}
