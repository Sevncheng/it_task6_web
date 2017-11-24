package cn.ssm.service.impl;

import cn.ssm.dao.StudentDao;
import cn.ssm.entity.Student;
import cn.ssm.service.StudentService;
import cn.ssm.util.SerializeUtil;
import com.danga.MemCached.MemCachedClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

@Service("mybatisService")
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentDao studentDao;

    @Autowired
    private MemCachedClient memCachedClient;

    @Autowired
    private JedisPool jedisPool;


    private int count = 0;

    public void add(Student student){
        studentDao.add(student);
    }
    public void delete(Student student){
        studentDao.delete(student);
    }
    public void update(Student student){
        studentDao.update(student);
    }
    public List<Student> findAll(){
        return studentDao.findAll();
    }
    public Student findById(String id ){return studentDao.findById(id);}
    public Student findById1(String id){
        Student student = null;
        try {
            student = (Student) memCachedClient.get(id);
            if (student == null) {
                student = studentDao.findById(id);
                if (student!=null){
                    memCachedClient.add(student.getId(),student);
                }
            }
        }catch (Exception e){
            System.out.println("找不到该学生!");
        }
        if(count%3==0){
            memCachedClient.flushAll();
        }
        count++;
        return  student;
    }

    public Student findById2(String id){
        Student student = null;
        Jedis jedis = jedisPool.getResource();
        try {
            student = (Student) SerializeUtil.unserialize(jedis.get(id.getBytes()));
            if (student == null) {
                student = studentDao.findById(id);
                if (student!=null){
                    jedis.set(student.getId().getBytes(),SerializeUtil.serialize(student));
                }
            }
        }catch (Exception e){
            System.out.println("找不到该学生!");
        }
        if(count%3==0){
            jedis.flushAll();
        }
        count++;
        jedisPool.returnResource(jedis);
        return  student;
    }





    public Student findByStudent(Student student){ return studentDao.findByStudent(student);}

    @Override
    public Integer findByStates(String str) {
        return studentDao.findByStates(str);
    }

    @Override
    public Integer findByType(String str) {
        return studentDao.findByType(str);
    }



}
