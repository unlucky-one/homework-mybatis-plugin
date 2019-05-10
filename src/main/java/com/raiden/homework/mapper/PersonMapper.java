package com.raiden.homework.mapper;

import com.raiden.homework.model.Person;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface PersonMapper {
    int insert(Person record);

    List<Person> selectAll(RowBounds rowBounds);
}