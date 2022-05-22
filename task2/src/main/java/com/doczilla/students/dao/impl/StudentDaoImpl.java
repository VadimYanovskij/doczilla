package com.doczilla.students.dao.impl;

import com.doczilla.students.dao.StudentDao;
import com.doczilla.students.domain.Student;
import com.doczilla.students.exception.DaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDaoImpl implements StudentDao {

    public static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String SAVE_NEW_STUDENT_QUERY = "INSERT INTO student (`name`, `lastname`, `middle_name`, " +
            "`birthday`, `group`) VALUES (?, ?, ?, ?, ?)";
    public static final String FIND_ALL_QUERY = "SELECT * FROM student";
    public static final String DELETE_QUERY = "DELETE FROM student WHERE id=?";

    private final String jdbcURL;
    private final String jdbcUsername;
    private final String jdbcPassword;
    private Connection connection;

    public StudentDaoImpl(String jdbcURL, String jdbcUsername, String jdbcPassword) {
        this.jdbcURL = jdbcURL;
        this.jdbcUsername = jdbcUsername;
        this.jdbcPassword = jdbcPassword;
    }

    @Override
    public void save(Student student) throws DaoException {
        PreparedStatement statement = null;
        try {
            connect();
            statement = connection.prepareStatement(SAVE_NEW_STUDENT_QUERY);
            statement.setString(1, student.getName());
            statement.setString(2, student.getLastname());
            statement.setString(3, student.getMiddleName());
            statement.setDate(4, Date.valueOf(student.getBirthday()));
            statement.setString(5, student.getGroup());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            close(statement);
            close(connection);
        }

    }

    @Override
    public List<Student> findAll() throws DaoException {
        List<Student> students = new ArrayList<>();
        Statement statement = null;
        try {
            connect();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(FIND_ALL_QUERY);
            while (resultSet.next()) {
                Student student = new Student();
                student.setId(resultSet.getInt("id"));
                student.setName(resultSet.getString("name"));
                student.setLastname(resultSet.getString("lastname"));
                student.setMiddleName(resultSet.getString("middle_name"));
                student.setBirthday(resultSet.getTimestamp("birthday").toLocalDateTime().toLocalDate());
                student.setGroup(resultSet.getString("group"));
                students.add(student);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            close(statement);
            close(connection);
        }
        return students;
    }

    @Override
    public void delete(Integer id) throws DaoException {
        PreparedStatement statement = null;
        try {
            connect();
            statement = connection.prepareStatement(DELETE_QUERY);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            close(statement);
            close(connection);
        }
    }

    private void connect() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName(JDBC_DRIVER);
            } catch (ClassNotFoundException e) {
                throw new SQLException(e);
            }
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        }
    }

    private void close(Statement statement) throws DaoException {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private void close(Connection connection) throws DaoException {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
