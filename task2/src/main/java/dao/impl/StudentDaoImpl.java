package dao.impl;

import dao.StudentDao;
import db.ConnectionCreator;
import domain.Student;
import exception.DaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDaoImpl implements StudentDao {

    public static final String SAVE_NEW_STUDENT_QUERY = "INSERT INTO student (name, lastname, middle_name, " +
            "birthday, group) VALUES (?, ?, ?, ?, ?)";
    public static final String FIND_ALL_QUERY = "SELECT * FROM student";
    public static final String DELETE_QUERY = "DELETE FROM student WHERE id=?";

    @Override
    public void save(Student student) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionCreator.createConnection();
            statement = connection.prepareStatement(SAVE_NEW_STUDENT_QUERY);
            statement.setString(1, student.getName());
            statement.setString(2, student.getLastname());
            statement.setString(3, student.getMiddleName());
            statement.setDate(4, Date.valueOf(student.getBirthday()));
            statement.setInt(5, student.getGroup());
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
        Connection connection = null;
        Statement statement = null;
        try {
            connection = ConnectionCreator.createConnection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(FIND_ALL_QUERY);
            while (resultSet.next()) {
                Student student = new Student();
                student.setId(resultSet.getInt("id"));
                student.setName(resultSet.getString("name"));
                student.setLastname(resultSet.getString("lastname"));
                student.setMiddleName(resultSet.getString("middle_name"));
                student.setBirthday(resultSet.getTimestamp("birthday").toLocalDateTime().toLocalDate());
                student.setGroup(resultSet.getInt("group"));
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
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionCreator.createConnection();
            statement = connection.prepareStatement(DELETE_QUERY);
            statement.setInt(1, id);
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            close(statement);
            close(connection);
        }
    }
}
