package com.liang.controller;

import com.liang.dao.HomeworkDao;
import com.liang.dao.TaskDao;
import com.liang.pojo.Homework;
import com.liang.pojo.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class TaskController {
    @Autowired
    TaskDao taskDao;
    @Autowired
    HomeworkDao homeworkDao;


    //显示考核信息列表
    @RequestMapping("/tasks")
    public String list(Model model){
        List<Task> tasks=taskDao.getAll();
        model.addAttribute("tasks",tasks);
        return"task/list";
    }

    @GetMapping("add")
    public String toAdd(Model model){
        return "/task/add";
    }

    //SpringMVC会按请求参数名和POJO属性名进行自动匹配，自动为该对象填充属性值，支持级联属性。
    //添加考核
    @PostMapping("/addtask")
    public  String addTask(Task task){
        taskDao.add(task);//调用底层业务方法保存员工信息
        //添加的操作
        return "redirect:/tasks";
    }

    //删除考核
    @GetMapping("/deltask/{id}")
    public String deleteTask(@PathVariable("id")Integer id){
        taskDao.deleteById(id);
        return "redirect:/tasks";
    }

    //显示某一考核详情，通过参数id
    @GetMapping("/task/{id}")
    public String show(@PathVariable("id") Integer id, Model model){
        Task task=taskDao.getById(id);
        model.addAttribute("task",task);
        return "task/detail";//跳转到详情页
    }

    //到修改页面
    @GetMapping("/toupdate/{id}")
    public String toUpdate(@PathVariable("id") Integer id,Model model){
        Task task=taskDao.getById(id);
        model.addAttribute("task",task);
        return "task/update";//到修改页面
    }

    //进行修改
    @RequestMapping("/update")
    public String update(Task task){
        taskDao.updateById(task.getId(),task);
        return "redirect:/tasks";//回到考核列表
    }

    //查看作业列表
    @RequestMapping("/view/{taskId}")
    public String viewHomework(@PathVariable("taskId") Integer taskId,Model model){
        List<Homework> homeworks=homeworkDao.getAllByTaskId(taskId);
        model.addAttribute("homeworks",homeworks);
        return "homework/list";
    }






}
