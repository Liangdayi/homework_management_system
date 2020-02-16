package com.liang.dao;

import com.liang.pojo.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;


@Repository
public class TaskDao {
    @Autowired
    JdbcTemplate jdbcTemplate;
    private static Integer initId=0;

    //添加考核
    // SpringMVC会按请求参数名和POJO属性名进行自动匹配，自动为该对象填充属性值，支持级联属性。
    public void add(Task task){
        if (task.getId()==null){
            task.setId(initId++);
        }
        String sql="insert into taskdemo.task(id,task_name,content,deadline) values (?,?,?,?)";
        Object[] objects=new Object[4];
        objects[0]=task.getId();
        objects[1]=task.getTaskName();
        objects[2]=task.getContent();
        Timestamp createTime = new Timestamp(task.getDeadline().getTime());
        objects[3]=createTime;
        jdbcTemplate.update(sql,objects);
    }


    //获得所有考核信息
    //用于显示考核列表
    public List<Task> getAll(){
        String sql="select * from taskdemo.task";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper(Task.class));
    }

    //考核查询(通过标志码id)
    //查看详情
    public Task getById(Integer id){
        //通过使用RowMapper来映射数据库中的一行数据和实体类的一个实例
        RowMapper<Task> mapper = new BeanPropertyRowMapper<>(Task.class);
        String sql="select * from taskdemo.task where id = ?";
        Task task=jdbcTemplate.queryForObject(sql, mapper,id);
        return task;
    }

    //修改考核信息
    public void updateById(Integer id,Task task){
        String sql="update taskdemo.task set task_name=?,content=?,deadline=? where id=?";
        Object[] objects=new Object[4];
        objects[0]=task.getTaskName();
        objects[1]=task.getContent();
        objects[2]=task.getDeadline();
        objects[3]=id;
        jdbcTemplate.update(sql,objects);

    }

    //删除考核
    public void deleteById(Integer id){
        String sql="delete from taskdemo.task where id = ?";
        jdbcTemplate.update(sql,id);
    }









}
