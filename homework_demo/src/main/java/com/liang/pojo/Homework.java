package com.liang.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.util.Date;

@Data
@NoArgsConstructor
public class Homework {
    private Integer id;
    private String studentName;
    private String studentNumber;
    private String path;//文件路径
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date submitDate;
    private Integer taskId;//此作业所属的考核
    private Integer finishFlag;//-1：未通过   0：审核中  1：通过

    public Homework(Integer id,String studentName, String studentNumber,String path,Integer taskId, Integer flag) {
        this.id=id;
        this.studentName = studentName;
        this.studentNumber = studentNumber;
        this.path=path;
        this.submitDate=new Date();
        this.taskId = taskId;
        this.finishFlag = 0;
    }
}
