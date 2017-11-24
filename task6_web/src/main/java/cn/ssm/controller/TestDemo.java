package cn.ssm.controller;


import cn.ssm.entity.Student;
import cn.ssm.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/test")
public class TestDemo {
    @Autowired
    private StudentService studentService;

    @RequestMapping(value = "/jsp1/{id}")
    public String test11(@PathVariable("id") String id,Model model){
        studentService.findById(id);
        return "/Desk/errorloginUI";
    }
    @RequestMapping(value = "/jsp2/{id}")
    public String test12(@PathVariable("id") String id){
        studentService.findById1(id);
        return "/Desk/errorloginUI";
    }

    @RequestMapping(value = "/jsp3/{id}")
    public String test13(@PathVariable("id") String id){
        studentService.findById2(id);
        return "/Desk/errorloginUI";
    }


    @ResponseBody
    @RequestMapping(value = "/json1/{id}")
    public Student test21(@PathVariable("id") String id){
        Student student = studentService.findById(id);
        return student;
    }

    @ResponseBody
    @RequestMapping(value = "/json2/{id}")
    public Student test22(@PathVariable("id") String id){
        Student student = studentService.findById1(id);
        return student;
    }

    @ResponseBody
    @RequestMapping(value = "/json3/{id}")
    public Student test23(@PathVariable("id") String id){
        Student student = studentService.findById2(id);
        return student;
    }

}
