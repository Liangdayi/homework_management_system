package com.liang.dao;

import com.liang.pojo.Homework;
import com.liang.pojo.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Repository
public class HomeworkDao {
    @Autowired
    JdbcTemplate jdbcTemplate;
    private static Integer initId=0;


    //添加作业
    // SpringMVC会按请求参数名和POJO属性名进行自动匹配，自动为该对象填充属性值，支持级联属性。
    public void add(Homework homework){
        if(homework.getId()==null){
            homework.setId(initId++);
        }
        String sql="insert into taskdemo.homework(id,student_name,student_number,path,submit_date,task_id,finish_flag) values (?,?,?,?,?,?,?)";
        Object[] objects=new Object[7];
        objects[0]=homework.getId();
        objects[1]=homework.getStudentName();
        objects[2]=homework.getStudentNumber();
        objects[3]=homework.getPath();
        homework.setSubmitDate(new Date());
        Timestamp createTime = new Timestamp(homework.getSubmitDate().getTime());
        objects[4]=createTime;
        objects[5]=homework.getTaskId();
        objects[6]=homework.getFinishFlag();
        jdbcTemplate.update(sql,objects);
    }

    //获得某考核下的所有作业信息
    //用于显示作业列表
    public List<Homework> getAllByTaskId(Integer taskId){
        String sql="select * from taskdemo.homework where task_id = ?";
        RowMapper<Homework> mapper = new BeanPropertyRowMapper<>(Homework.class);
        List<Homework> homeworkList = jdbcTemplate.query(sql, mapper, taskId);
        return homeworkList;
    }

    //作业删除
    public void deleteById(Integer id){
        String sql="delete  from taskdemo.homework where id = ?";
        jdbcTemplate.update(sql,id);
    }

    //通过id查找作业的资源
    public String getById(Integer id){
        String sql="select path from taskdemo.homework where id = ?";
        return (String) jdbcTemplate.queryForObject(sql,new Object[]{id},String.class);
    }
    //修改作业的状态
    public void updateFlagById(Integer id,Integer flag){
        String sql="update taskdemo.homework set finish_flag=? where id=?";
        Object[] objects=new Object[2];
        objects[0]=flag;
        objects[1]=id;
        jdbcTemplate.update(sql,objects);

    }
}
