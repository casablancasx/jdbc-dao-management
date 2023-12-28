package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.dao.EmployeeDao;
import model.entities.Department;
import model.entities.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        ResultSet rs = null;
        PreparedStatement st = null;
        try{
            st = connection.prepareStatement(
                    "SELECT employee.*,department.Name as DepName " +
                    "FROM employee INNER JOIN department " +
                    "ON employee.DepartmentId = department.Id " +
                    "WHERE seller.Id = ?");
            st.setInt(1,id);
            rs = st.executeQuery();
            if (rs.next()){
                Department dep = new Department();
                dep.setId(rs.getInt("DepartmentId"));
                dep.setName(rs.getString("DepName"));
                Employee obj = new Employee();
                obj.setId(rs.getInt("Id"));
                obj.setName(rs.getString("Name"));
                obj.setEmail(rs.getString("Email"));
                obj.setBirthDate(rs.getDate("BirthDate"));
                obj.setBaseSalary(rs.getDouble("BaseSalary"));
                obj.setDepartment(dep);
                return obj;
            }
            return null;
        }catch (SQLException e){
            throw new DbException(e.getMessage());
        }finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
    }

    @Override
    public List<Employee> findAll() {
        return null;
    }
}
