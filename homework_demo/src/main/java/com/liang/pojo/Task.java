package com.liang.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;


import java.util.Date;




@Data
@NoArgsConstructor
public class Task {
    private Integer id;//标识编号
    private String taskName;//名称
    private String content;//内容
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date deadline;//截至日期


    public Task(Integer id, String taskName, String content) {
        this.id = id;
        this.taskName = taskName;
        this.content = content;
        this.deadline=new Date();
    }
}
