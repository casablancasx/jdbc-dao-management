package model.dao;

import db.DB;
import model.dao.impl.DepartmentDaoJDBC;
import model.dao.impl.EmployeeDaoJDBC;

public class DaoFactory {
    public static EmployeeDao createEmployeeDao(){
        return new EmployeeDaoJDBC(DB.getConnection());
    }

    public static  DepartmentDao createDepartmentDao(){
        return new DepartmentDaoJDBC(DB.getConnection());
    }
}
