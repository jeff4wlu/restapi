package com.jeff4w.example.restapi.controller;

import com.jeff4w.example.restapi.common.Restful.ResponseResult;
import com.jeff4w.example.restapi.common.Restful.RestResultGenerator;
import com.jeff4w.example.restapi.common.Utils;
import com.jeff4w.example.restapi.domain.Student;
import com.jeff4w.example.restapi.service.StudentService;
import io.swagger.annotations.*;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
@Api("学生信息api")
public class StudentController {

    @Autowired
    private StudentService studentService;

    /**
     * 显示所有
     * url:"http://localhost/student/findall"
     *
     * @return
     */
    @ApiOperation(value = "查询全部学生信息",notes = "查询全部学生信息")
    @ApiResponses({
            @ApiResponse(code=400,message = "请求参数没有填好"),
            @ApiResponse(code=404,message="请求路径没有找到")
    })
    @GetMapping(value = "")
    @RequiresAuthentication
    public ResponseResult<List<Student>> findAllStudent() throws Exception {
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
    @ApiOperation(value = "根据id查询学生的信息",notes = "查询数据库中某个学生的信息")
    @ApiImplicitParam(name ="id",value = "学生id",paramType = "path",required = true,dataType = "Long")
    @ApiResponses({
            @ApiResponse(code=400,message = "请求参数没有填好"),
            @ApiResponse(code=404,message="请求路径没有找到")
    })
    @GetMapping(value = "/{id}")
    @RequiresPermissions(logical = Logical.OR, value = {"userInfo:view", "edit"})
    public ResponseResult<Student> findStudent(@PathVariable Integer id) throws Exception {
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
    @ApiOperation(value = "根据id删除学生的信息",notes = "删除数据库中某个学生的信息")
    @ApiImplicitParam(name ="id",value = "学生id",paramType = "path",required = true,dataType = "Long")
    @ApiResponses({
            @ApiResponse(code=400,message = "请求参数没有填好"),
            @ApiResponse(code=404,message="请求路径没有找到")
    })
    @DeleteMapping(value = "/{id}")
    public ResponseResult deleteStudent(@PathVariable Integer id) throws Exception {
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
    @ApiOperation(value = "新增学生的信息",notes = "新增数据库中某个学生的全部信息")
    @ApiImplicitParam(name ="student",value = "学生实体对象",paramType = "body",required = true,dataType = "Student")
    @ApiResponses({
            @ApiResponse(code=400,message = "请求参数没有填好"),
            @ApiResponse(code=404,message="请求路径没有找到")
    })
    @PostMapping(value = "")
    public ResponseResult<Student> addStudent(@Valid @RequestBody Student student) throws Exception {
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
    @ApiOperation(value = "根据id更新学生的全部信息",notes = "更新数据库中某个学生的全部信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "学生id",dataType = "Long",paramType = "path",example = "1112"),
            @ApiImplicitParam(name = "newStudent",value = "学生实体对象",dataType = "Student",paramType = "body",example = "1112")
    })
    @ApiResponses({
            @ApiResponse(code=400,message = "请求参数没有填好"),
            @ApiResponse(code=404,message="请求路径没有找到")
    })
    @PutMapping(value = "/{id}")
    public ResponseResult<Student> updateStudentAll(@PathVariable Integer id, @Valid @RequestBody Student newStudent) throws Exception {
        Student student = studentService.findStudentById(id).get();
        // copy all new user props to user except id
        BeanUtils.copyProperties(newStudent, student, "id");
        student = studentService.addStudent(student);

        return RestResultGenerator.genSuccessResult(student);
    }

    @ApiOperation(value = "根据id更新学生的部分信息",notes = "更新数据库中某个学生的部分信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "学生id",dataType = "Long",paramType = "path",example = "1112"),
            @ApiImplicitParam(name = "newStudent",value = "学生实体对象",dataType = "Student",paramType = "body",example = "1112")
    })
    @ApiResponses({
            @ApiResponse(code=400,message = "请求参数没有填好"),
            @ApiResponse(code=404,message="请求路径没有找到")
    })
    @PatchMapping(value = "/{id}")
    public ResponseResult<Student> update(@PathVariable Integer id, @Valid @RequestBody Student newStudent) throws Exception {
        Student student = studentService.findStudentById(id).get();
        // copy all new user props to user except null props
        BeanUtils.copyProperties(newStudent, student, Utils.getNullPropertyNames(newStudent));
        student = studentService.addStudent(student);

        return RestResultGenerator.genSuccessResult(student);
    }

}
