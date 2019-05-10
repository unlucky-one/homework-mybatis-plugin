package com.raiden.homework;

import com.raiden.homework.mapper.PersonMapper;
import com.raiden.homework.model.Person;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Author: Raiden
 * Date: 2019/5/8
 */
public class test {

    public static void main(String[] args) {
        try {
            String resource = "mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);


            SqlSession session = sqlSessionFactory.openSession();
//            PersonDao person = session.getMapper(PersonDao.class);
//            PersonExample example=new PersonExample();
//            List<Person> people = person.selectByExample(example);
//            example.setLimit(10);
            RowBounds rowBounds = new RowBounds(0, 10);

            PersonMapper person = session.getMapper(PersonMapper.class);
            List<Person> people = person.selectAll(rowBounds);
            System.out.println(people.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
