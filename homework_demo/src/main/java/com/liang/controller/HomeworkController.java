package com.liang.controller;

import com.liang.dao.HomeworkDao;
import com.liang.dao.TaskDao;
import com.liang.pojo.Homework;
import com.liang.pojo.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@Controller
public class HomeworkController {
    @Autowired
    TaskDao taskDao;
    @Autowired
    HomeworkDao homeworkDao;
    Integer taskId;
    //跳转到考核信息页面,让用户选择对应的考核提交作业
    @RequestMapping("/upload")
    public String toSelect(Model model) {
        List<Task> tasks=taskDao.getAll();
        model.addAttribute("tasks",tasks);
        return "homework/tasklist";
    }

    @RequestMapping("/file/{taskId}")
    public String file(@PathVariable("taskId") Integer taskId){
        this.taskId=taskId;
        return "/file";
    }


    /**
     * 实现文件上传
     * */
    @RequestMapping("/addhomework")
    @ResponseBody
    public String fileUpload(@RequestParam("fileName") MultipartFile file,Homework homework){
        if(file.isEmpty()){
            return "false";
        }
        String fileName = file.getOriginalFilename();
        int size = (int) file.getSize();
        System.out.println(fileName + "-->" + size);
        String path = "C:/homeworktest" ;
        File dest = new File(path + "/" + fileName);
        homework.setTaskId(taskId);
        homework.setPath(path+"/"+fileName);
        homework.setFinishFlag(0);
        System.out.println(homework);
        homeworkDao.add(homework);
        if(!dest.getParentFile().exists()){ //判断文件父目录是否存在,不存在则新建
            dest.getParentFile().mkdir();
        }
        try {
            file.transferTo(dest); //保存文件
            return "true";
        } catch (IllegalStateException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "false";
        }
    }
    @RequestMapping("/download/{id}")
    public String downLoad(@PathVariable("id")Integer id,HttpServletResponse response) throws UnsupportedEncodingException {
        String path=homeworkDao.getById(id);
        File file = new File(path);
       String filename=path.substring(path.lastIndexOf("\\")+1);
        if(file.exists()){ //判断文件父目录是否存在
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            // response.setContentType("application/force-download");
            response.setHeader("Content-Disposition", "attachment;fileName=" +   java.net.URLEncoder.encode(filename,"UTF-8"));
            byte[] buffer = new byte[1024];
            FileInputStream fis = null; //文件输入流
            BufferedInputStream bis = null;

            OutputStream os = null; //输出流
            try {
                os = response.getOutputStream();
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                int i = bis.read(buffer);
                while(i != -1){
                    os.write(buffer);
                    i = bis.read(buffer);
                }

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println("----------file download---" + filename);
            try {
                bis.close();
                fis.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return null;
    }

    @RequestMapping("/delhomework/{id}")
    public String delete(@PathVariable("id")Integer id){
        homeworkDao.deleteById(id);
        return "homework/list";
    }
    @RequestMapping("/updateflag/{id}/{flag}")
    public String updateFlag(@PathVariable("id")Integer id,@PathVariable("flag")Integer flag){
        homeworkDao.updateFlagById(id,flag);
        return "homework/list";
    }



}
