package com.jeff4w.example.restapi.dao;

import com.jeff4w.example.restapi.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Lu Weijian
 * @description 备注
 * @email lwj@kapark.cn
 * @date 2018-12-27 9:33
 */
public interface StudentDao extends JpaRepository<Student, Integer> {
}

