package com.doczilla.students.controller;

import com.doczilla.students.dao.StudentDao;
import com.doczilla.students.dao.impl.StudentDaoImpl;
import com.doczilla.students.domain.Student;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class ControllerServlet extends HttpServlet {

    private StudentDao studentDao;

    public void init() {
        String jdbcURL = getServletContext().getInitParameter("jdbcURL");
        String jdbcUsername = getServletContext().getInitParameter("jdbcUsername");
        String jdbcPassword = getServletContext().getInitParameter("jdbcPassword");

        studentDao = new StudentDaoImpl(jdbcURL, jdbcUsername, jdbcPassword);

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();
        switch (action) {
            case "/new":
                showNewForm(request, response);
                break;
            case "/insert":
                insertStudent(request, response);
                break;
            case "/delete":
                deleteStudent(request, response);
                break;
            default:
                listStudent(request, response);
                break;
        }
    }

    private void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Integer id = Integer.parseInt(request.getParameter("id"));
        studentDao.delete(id);
        response.sendRedirect("list");
    }

    private void insertStudent(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Student newStudent = new Student();
        newStudent.setName(request.getParameter("name"));
        newStudent.setLastname(request.getParameter("lastname"));
        newStudent.setMiddleName(request.getParameter("middleName"));
        String birthdayFromPage = request.getParameter("birthday");
        if (!Objects.equals(birthdayFromPage, "") && birthdayFromPage != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            newStudent.setBirthday(LocalDate.parse(birthdayFromPage, formatter));
        }
        newStudent.setGroup(request.getParameter("group"));

        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<Student>> constraintViolations = validator.validate(newStudent);
        if (!constraintViolations.isEmpty()) {
            StringBuilder errors = new StringBuilder("<ul>");
            constraintViolations.forEach(constraintViolation ->
                    errors.append("<li>").append(constraintViolation.getPropertyPath()).append(" ")
                            .append(constraintViolation.getMessage()).append("</li>"));
            errors.append("</ul>");
            request.setAttribute("student", newStudent);
            request.setAttribute("errors", errors.toString());
            RequestDispatcher dispatcher = request.getRequestDispatcher("StudentForm.jsp");
            dispatcher.forward(request, response);
        } else {
            studentDao.save(newStudent);
            response.sendRedirect("list");
        }
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("StudentForm.jsp");
        dispatcher.forward(request, response);
    }

    private void listStudent(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Student> students = studentDao.findAll();
        request.setAttribute("students", students);
        RequestDispatcher dispatcher = request.getRequestDispatcher("StudentList.jsp");
        dispatcher.forward(request, response);
    }

}
