package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.dao.EmployeeDao;
import model.entities.Department;
import model.entities.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeDaoJDBC implements EmployeeDao {
    private Connection connection;
    public EmployeeDaoJDBC(Connection connection){
        this.connection = connection;
    }

    @Override
    public void insert(Employee obj) {
        PreparedStatement st = null;
        try{
            st = connection.prepareStatement(
                    "INSERT INTO employee " +
                    "(Name, Email, BirthDate, BaseSalary, DepartmentId) " +
                    "VALUES " +
                    "(?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

            st.setString(1,obj.getName());
            st.setString(2,obj.getEmail());
            st.setDate(3,new java.sql.Date(obj.getBirthDate().getTime()));
            st.setDouble(4,obj.getBaseSalary());
            st.setInt(5,obj.getDepartment().getId());

            st.executeUpdate();
        }catch (SQLException e){
            throw new DbException(e.getMessage());
        }finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void update(Employee obj) {
        PreparedStatement st = null;
        try{
            st = connection.prepareStatement(
                    "UPDATE employee " +
                    "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? " +
                    "WHERE Id = ?" );
            st.setString(1,obj.getName());
            st.setString(2,obj.getEmail());
            st.setDate(3,new java.sql.Date(obj.getBirthDate().getTime()));
            st.setDouble(4,obj.getBaseSalary());
            st.setInt(5,obj.getDepartment().getId());
            st.executeUpdate();
        }catch (SQLException e){
            throw new DbException(e.getMessage());
        }finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;
        try{
            st = connection.prepareStatement("DELETE FROM employee WHERE Id = ?");
            st.setInt(1,id);
            st.executeUpdate();
        }catch (SQLException e){
            throw new DbException(e.getMessage());
        }
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
                Department dep = instantiateDepartment(rs);
                Employee obj = instantiateEmployee(rs,dep);
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

    private Employee instantiateEmployee(ResultSet rs, Department dep) throws SQLException {
        Employee obj = new Employee();
        obj.setId(rs.getInt("Id"));
        obj.setName(rs.getString("Name"));
        obj.setEmail(rs.getString("Email"));
        obj.setBirthDate(rs.getDate("BirthDate"));
        obj.setBaseSalary(rs.getDouble("BaseSalary"));
        obj.setDepartment(dep);
        return obj;
    }

    @Override
    public List<Employee> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = connection.prepareStatement(
                    "SELECT employee.*,department.Name as DepName " +
                    "FROM employee INNER JOIN department " +
                    "ON employee.DepartmentId = department.Id " +
                    "ORDER BY Name");

            rs = st.executeQuery();
            List<Employee> list = new ArrayList<>();
            Map<Integer,Department> map = new HashMap<>();
            while (rs.next()){
                Department dep = map.get(rs.getInt("DeparmeentId"));
                if (dep == null){
                    Department department = instantiateDepartment(rs);
                    map.put(rs.getInt("DepartmentId"),department);
                }
                Employee employee = instantiateEmployee(rs,dep);
            }
        return list;

        }catch (SQLException e){
            throw new DbException(e.getMessage());
        }finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    private Department instantiateDepartment(ResultSet rs) throws SQLException {
        Department dep = new Department();
        dep.setId(rs.getInt("DepartmentId"));
        dep.setName(rs.getString("DepName"));
        return dep;
    }
}
