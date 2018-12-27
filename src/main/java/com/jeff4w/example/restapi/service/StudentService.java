package com.jeff4w.example.restapi.service;

import com.jeff4w.example.restapi.domain.Student;

import java.util.List;
import java.util.Optional;

/**
 * @author Lu Weijian
 * @description 备注
 * @email lwj@kapark.cn
 * @date 2018-12-27 9:37
 */
public interface StudentService {

    public Student addStudent(Student student);
    public void delStudentById(Integer id);
    public void updateStudent(Student student);
    public Optional<Student> findStudentById(Integer id);
    public List<Student> findAllStudent();

}

