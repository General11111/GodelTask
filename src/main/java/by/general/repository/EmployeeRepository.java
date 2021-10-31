package by.general.repository;

import by.general.entity.Employee;
import by.general.utils.ConnectionUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

public class EmployeeRepository {

    private Connection con;
    private PreparedStatement st;
    private ResultSet rs;

    private static final String FIND_ALL_QUERY = "SELECT * FROM public.employeedb;";
    private static final String CREATE_QUERY = "INSERT INTO public.employeedb (employee_id, first_name, last_name, " +
            "department_id, job_title, gender, date_of_birth)\n" +
            "VALUES (?,?,?,?,?,?,?);";
    private static final String UPDATE_QUERY = "UPDATE employeedb SET first_name = ?, last_name = ?, " +
            "department_id = ?, job_title = ?, gender = ?, date_of_birth = ? WHERE employee_id = ?;";
    private static final String  DELETE_BY_ID_QUERY = "DELETE FROM employeedb WHERE employeedb.employee_id = ?;";
    private static final String  FIND_BY_ID_QUERY = "SELECT * FROM employeedb WHERE employeedb.employee_id = ?;";

    public void create (Employee employee) throws Exception{
        try {
            con = ConnectionUtils.getConnection();
            st = con.prepareStatement(CREATE_QUERY);
            st.setInt(1, employee.getEmployeeId());
            st.setString(2, employee.getFirstName());
            st.setString(3, employee.getLastName());
            st.setInt(4, employee.getDepartmentId());
            st.setString(5, employee.getJobTitle());
            st.setString(6, employee.getGender());
            st.setDate(7, employee.getBirthDate());
            int savedRows = st.executeUpdate();
            System.out.println("EmployeeRepository -> saved " + savedRows + " employee(s)");
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error!", ex);
        }finally {
            ConnectionUtils.close(con,st,rs);
        }
    }

    public void update(Employee employee) throws Exception{
        Connection con = ConnectionUtils.getConnection();
        PreparedStatement st = con.prepareStatement(UPDATE_QUERY);
        st.setString(1, employee.getFirstName());
        st.setString(2, employee.getLastName());
        st.setInt(3, employee.getDepartmentId());
        st.setString(4, employee.getJobTitle());
        st.setString(5, employee.getGender());
        st.setDate(6, employee.getBirthDate());
        st.setInt(7, employee.getEmployeeId());
        int updateRows = st.executeUpdate();
        System.out.println(updateRows + " employee(s) was updated");
        con.close();
    }

    public List<Employee> findAll (){
        try {
            con = ConnectionUtils.getConnection();
            st = con.prepareStatement(FIND_ALL_QUERY);
            rs = st.executeQuery();
            List<Employee> foundEmployees = extractAll(rs);
            System.out.println(foundEmployees.size() + " employees were found.");
            return foundEmployees;
        }catch (Exception ex){
            ex.printStackTrace();
            throw new RuntimeException("Error!", ex);
        }finally {
            ConnectionUtils.close(con,st,rs);
        }
    }

    public Employee findById(int id){
        try{
            con = ConnectionUtils.getConnection();
            st = con.prepareStatement(FIND_BY_ID_QUERY);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            Employee foundEmployee = extract(rs);
            return foundEmployee;
        }catch (Exception ex){
            ex.printStackTrace();
            throw new RuntimeException("Error!", ex);
        }finally {
            ConnectionUtils.close(con,st,rs);
        }
    }

    public void deleteById (int id){
        try {
            Connection con = ConnectionUtils.getConnection();
            PreparedStatement st = con.prepareStatement(DELETE_BY_ID_QUERY);
            st.setInt(1,id);
            int updatedRows = st.executeUpdate();
            System.out.println(updatedRows + " employees was deleted.");
        }catch (Exception ex){
            ex.printStackTrace();
            throw new RuntimeException("Error!", ex);
        }finally {
            ConnectionUtils.close(con,st,rs);
        }
    }

    private Employee extract (ResultSet rs)throws Exception{
        if(rs.next()){
            Employee employee = new Employee();
            employee.setEmployeeId(rs.getInt("employee_id"));
            employee.setFirstName(rs.getString("first_name"));
            employee.setLastName(rs.getString("last_name"));
            employee.setDepartmentId(rs.getInt("department_id"));
            employee.setJobTitle(rs.getString("job_title"));
            employee.setGender(rs.getString("gender"));
            employee.setBirthDate(rs.getDate("date_of_birth"));
            System.out.println("Found order: "+ employee);
            return employee;
        }
        System.out.println("There is no order.");
        return null;
    }

    private List<Employee> extractAll(ResultSet rs)throws SQLException{
        List <Employee> foundEmployees = new ArrayList<>();
        while(rs.next()){
            int foundId = rs.getInt("employee_id");
            String foundFirstName = rs.getString("first_name");
            String foundLastName = rs.getString("last_name");
            int foundDepartmentId = rs.getInt("department_id");
            String foundJobTitle = rs.getString("job_title");
            String foundGender = rs.getString("gender");
            Date foundBirthDate = rs.getDate("date_of_birth");
            Employee employee = new Employee(foundId, foundFirstName, foundLastName, foundDepartmentId,
                    foundJobTitle, foundGender, foundBirthDate);
            foundEmployees.add(employee);
        }
        return foundEmployees;
    }
}
