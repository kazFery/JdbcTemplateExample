package com.sg.jdbctcomplexexample.dao;

import com.sg.jdbctcomplexexample.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class EmployeeDaoDatabase implements EmployeeDao{

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public List<Employee> getAllEmployees() {
        return jdbc.query("SELECT * FROM employee ", new EmployeeMapper());
    }

    @Override
    public Employee getEmployeeById(int id) {
        try{
           return  jdbc.queryForObject("SELECT * FROM employee WHERE id = ?", new EmployeeMapper(), id);
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    @Transactional
    public Employee addEmployee(Employee employee) {
        final String INSERT_EMPOLYEE = "INSERT INTO employee(firstName, lastName) VALUES (?,?)";
        jdbc.update(INSERT_EMPOLYEE, employee.getFirstName(), employee.getLastName());
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        employee.setId(newId);
        return employee;
    }

    @Override
    public void updateEmployee(Employee employee) {
        final String UPDATE_EMPLOYEE = "UPDATE employee SET firstName = ? , lastName = ? WHERE id = ?";
        jdbc.update(UPDATE_EMPLOYEE,employee.getFirstName(), employee.getLastName(), employee.getId());
    }

    @Override
    @Transactional
    public void deleteEmployeeById(int id) {
         jdbc.update("DELETE FROM meeting_employee WHERE employeeId = ?", id);
         jdbc.update("DELETE FROM employeeWHERE id = ?", id);
    }

    public static final class EmployeeMapper implements RowMapper<Employee> {

        @Override
        public Employee mapRow(ResultSet rs, int i) throws SQLException {
            Employee emp = new Employee();
            emp.setId(rs.getInt("id"));
            emp.setFirstName(rs.getString("firstName"));
            emp.setLastName(rs.getString("lastName"));
            return emp;
        }
    }
}
