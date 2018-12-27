package com.jeff4w.example.restapi.service.impl;

import com.jeff4w.example.restapi.dao.StudentDao;
import com.jeff4w.example.restapi.domain.Student;
import com.jeff4w.example.restapi.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Lu Weijian
 * @description 备注
 * @email lwj@kapark.cn
 * @date 2018-12-27 9:40
 */
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentDao studentDao;

    @Override
    public Student addStudent(Student student) {
        // TODO Auto-generated method stub
        return studentDao.save(student);
    }

    @Override
    public void delStudentById(Integer id) {
        // TODO Auto-generated method stub
        studentDao.deleteById(id);

    }

    @Override
    public void updateStudent(Student student) {
        // TODO Auto-generated method stub
        studentDao.save(student);
    }

    @Override
    public Optional<Student> findStudentById(Integer id) {
        // TODO Auto-generated method stub
        return studentDao.findById(id);
    }

    @Override
    public List<Student> findAllStudent() {
        // TODO Auto-generated method stub
        return studentDao.findAll();

    }

}

