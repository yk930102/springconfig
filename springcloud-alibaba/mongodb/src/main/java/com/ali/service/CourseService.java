package com.ali.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.dynamic.datasource.annotation.DSTransactional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author roy
 * @date 2022/3/17
 * @desc
 */
@Service
@DS("read")
public class CourseService {


    @Resource
    private JdbcTemplate jdbcTemplate;

//    @DS("#header.key")
//    @DS("#session.key")
    @DS("#test!=null?'':T(com.example.dynamic.service.impl.UserServiceImpl).threadLocal.get()")
    public List selectCourse(){
        return jdbcTemplate.queryForList("select * from student");
    }

    @DS("write")
    @DSTransactional
    public List selectCourse3(){
        return jdbcTemplate.queryForList("select * from student");
    }

    @DS("#session.rw")
    public List selectCourse2(){
        return jdbcTemplate.queryForList("select * from student");
    }

    @DS("write")
    @DSTransactional
    public int createCourse(String name){
        int res = jdbcTemplate.update("insert into student (name) values(?)", name);
//        int i = 1/0;
        return res;
    }
}
