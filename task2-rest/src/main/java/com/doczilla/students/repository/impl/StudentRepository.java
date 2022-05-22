package com.doczilla.students.repository.impl;

import com.doczilla.students.domain.Student;
import com.doczilla.students.repository.CommonRepository;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.ResultSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class StudentRepository implements CommonRepository<Student> {

    public static final String SAVE_NEW_STUDENT_QUERY = "INSERT INTO student (`name`, `lastname`, `middle_name`, `birthday`, " +
            "`group`) VALUES (:name, :lastname, :middle_name, :birthday, :group)";
    public static final String FIND_ALL_QUERY = "SELECT * FROM student";
    public static final String DELETE_QUERY = "DELETE FROM student WHERE id = :id";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public StudentRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public void save(Student student) {
        Map<String, Object> namedParameters = new HashMap<>();
        namedParameters.put("name", student.getName());
        namedParameters.put("lastname", student.getLastname());
        namedParameters.put("middle_name", student.getMiddleName());
        namedParameters.put("birthday", Date.valueOf(student.getBirthday()));
        namedParameters.put("group", student.getGroup());
        this.jdbcTemplate.update(SAVE_NEW_STUDENT_QUERY, namedParameters);
    }

    @Override
    public List<Student> findAll() {
        return jdbcTemplate.query(FIND_ALL_QUERY, studentRowMapper);
    }

    @Override
    public void delete(Student student) {
        Map<String, Integer> namedParameters = Collections.singletonMap("id", student.getId());
        this.jdbcTemplate.update(DELETE_QUERY, namedParameters);
    }

    private RowMapper<Student> studentRowMapper = (ResultSet resultSet, int rowNum) -> {
        Student student = new Student();
        student.setId(resultSet.getInt("id"));
        student.setName(resultSet.getString("name"));
        student.setLastname(resultSet.getString("lastname"));
        student.setMiddleName(resultSet.getString("middle_name"));
        student.setBirthday(resultSet.getTimestamp("birthday").toLocalDateTime().toLocalDate());
        student.setGroup(resultSet.getString("group"));
        return student;
    };
}
