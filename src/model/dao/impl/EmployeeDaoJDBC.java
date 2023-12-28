package model.dao.impl;

import model.dao.EmployeeDao;
import model.entities.Employee;

import java.sql.Connection;
import java.util.List;

public class EmployeeDaoJDBC implements EmployeeDao {
    private Connection connection;
    public EmployeeDaoJDBC(Connection connection){
        this.connection = connection;
    }

    @Override
    public void insert(Employee obj) {
        
    }

    @Override
    public void update(Employee obj) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Employee findById(Integer id) {
        return null;
    }

    @Override
    public List<Employee> findAll() {
        return null;
    }
}
