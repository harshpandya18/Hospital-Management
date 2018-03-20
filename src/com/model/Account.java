
package com.model;

import com.admin.CancelApptMain;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;


public class Account {
    //Step 1(Declare Variables)
    String user="root";
    String pass="";
    String url="jdbc:mysql://localhost:3306/hospital";
    String driver="com.mysql.jdbc.Driver";
    Connection con;
    //Step 2(Load the driver)
    void dbConnect() throws ClassNotFoundException, SQLException{
        Class.forName(driver);//step2
      
      //Step 3(Create the Connection)
         con=DriverManager.getConnection(url, user, pass);
       
        
       
    }
    void dbClose() throws SQLException{
        con.close();
    }
   
    public void insertPatientInfo(String name,String contact,String age, String doctor_name, String date,String time) throws ClassNotFoundException, SQLException{
        dbConnect();
        String sql="insert into patient (name,contact,age,dname,date,time) values(?,?,?,?,?,?)";
        /*String sql="create or replace trigger trig"
                + " after "
                + "insert on patient"
                + " referencing new as new old as old"
                + " for each row"
                + " when(:new.contact = :old.contact) "
                + "begin"
                + " insert into patient (:new.name,:new.contact,:new.age,:new.dname,:new.date,:new.time) values(?,?,?,?,?,?) "
                + " end; ";*/
     PreparedStatement pstmt=con.prepareStatement(sql);
     pstmt.setString(1,name);
     pstmt.setString(2,contact);
     pstmt.setString(3,age);
     pstmt.setString(4,doctor_name);
     pstmt.setString(5,date);
     pstmt.setString(6,time);  
     pstmt.executeUpdate();
        dbClose();
    }
    
      
    
    public ResultSet fetchPatient() throws ClassNotFoundException, SQLException{
        dbConnect();
        String sql="select * from patient where date=?";
        PreparedStatement pstmt=con.prepareStatement(sql);
        DateFormat df=DateFormat.getDateInstance();
        String d=df.format(new Date());
        pstmt.setString(1,d);
        
        ResultSet rset=pstmt.executeQuery();
        return rset;
    }
    
    public ResultSet fetchPatientTomorrow() throws ClassNotFoundException, SQLException{
        dbConnect();
         DateFormat df=DateFormat.getDateInstance();
        Calendar c=Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE,1);
        Date d1=c.getTime();  //displays tomorrow's date
        String d2=df.format(d1);
        String sql="select * from patient where date=?";
        PreparedStatement pstmt=con.prepareStatement(sql);
       
        String d=df.format(new Date());
        pstmt.setString(1,d2);
        
        ResultSet rset=pstmt.executeQuery();
        return rset;
    }
    
    
    

    public void cancelPatient(String name , String age , String contact) throws ClassNotFoundException, SQLException {
       dbConnect();
       String sql="delete from patient where name=? and age=? and contact=? ";
        PreparedStatement pstmt=con.prepareStatement(sql);
     pstmt.setString(1,name);
     pstmt.setString(2,age);
     pstmt.setString(3,contact);
     pstmt.executeUpdate();
     dbClose();
    }
}
