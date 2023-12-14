package com.ali.controller;

import com.ali.service.CourseService;
import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.annotation.DSTransactional;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author roy
 * @date 2022/3/17
 * @desc 使用DynamicDataSource框架，通过@DS注解快速切换数据源
 */

@Controller
@RequestMapping("/DS")
public class CourseControllerDS {
    @Resource
    CourseService courseService;

    @Resource
    DynamicRoutingDataSource dataSource;

    @ResponseBody
    @RequestMapping("/queryCourse")
    public Object queryOrder(){

//        return courseMapper.selectList(null);
        // 不指定数据源，默认读取read库
        return courseService.selectCourse();
        // 通过DynamicDataSourceContextHolder切换数据源
//        Object res;
//        try {
//            DynamicDataSourceContextHolder.push("write");
//            res = courseService.selectCourse();
//            return res;
//        } finally {
//            DynamicDataSourceContextHolder.poll();
//        }
    }

    @ResponseBody
    @RequestMapping("/queryCourse3")
    public Object queryOrder3(){

//        return courseMapper.selectList(null);
        // 不指定数据源，默认读取read库
        return courseService.selectCourse3();
        // 通过DynamicDataSourceContextHolder切换数据源
//        Object res;
//        try {
//            DynamicDataSourceContextHolder.push("write");
//            res = courseService.selectCourse();
//            return res;
//        } finally {
//            DynamicDataSourceContextHolder.poll();
//        }
    }

    @ResponseBody
    @RequestMapping("/sessionTest")
    public Object sessionTest(@RequestParam(value = "dsKey",defaultValue = "read") String dsKey, HttpServletRequest request){
//        return courseMapper.selectList(null);
        request.getSession().setAttribute("rw",dsKey);
        return courseService.selectCourse2();
    }

    @ResponseBody
    @RequestMapping("/createCourse")
    public String createCourse(String name){
//        courseMapper.insert(course);
        courseService.createCourse(name);
        courseService.createCourse(name+"1");
        int i = 1/0;
        return "SUCCESS BY DS";
    }
    @ResponseBody
    @RequestMapping("/createCourse2")
    @DSTransactional
    public String createCourse2(String name){
//        courseMapper.insert(course);
        courseService.createCourse(name);
        courseService.createCourse(name+"1");
        int i = 1/0;
        return "SUCCESS BY DS 2";
    }
    @ResponseBody
    @RequestMapping("/addDB")
    public Object addDB(){
        DruidDataSource tmpdb = new DruidDataSource();
        tmpdb.setUsername("root");
        tmpdb.setPassword("root");
        tmpdb.setUrl("jdbc:mysql://localhost:3306/coursedb2?serverTimezone=UTC");
        tmpdb.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.addDataSource("test",tmpdb);
        return "DB added";
    }
}
