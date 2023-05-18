/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.ulatina.finalproject.service;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author rodo
 */
public class Service {
    
    protected Connection connection = null;
    
    private String host;
    private String port;
    private String schema;
    private String user;
    private String password;
    private String driver;
    private String dbURL;

    public Service() {
        this.host       = "localhost";
        this.port       = "3306";
        this.schema     = "BIS10_DB2";
        this.user       = "root";
        this.password   = "rjml2290";
        this.driver     = "com.mysql.cj.jdbc.Driver";
        this.dbURL      = "jdbc:mysql://" + this.host + ":" + this.port + "/" + this.schema;
    }
    
    public void connect(){
        
        try{
            Class.forName(driver);
            this.connection = DriverManager.getConnection(dbURL, user, password);
            
        }catch(ClassNotFoundException e){
            e.printStackTrace();
            
        }catch(SQLException e){
            
        }
    }
    
    public void closeStatement(Statement stmt){
        try {
            if (stmt != null && !stmt.isClosed()) {
                stmt.close();
                stmt = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void closeResultSet(ResultSet rs){
        try {
            if (rs != null && !rs.isClosed()) {
                rs.close();
                rs = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void disconnect(){
        try {
             if(connection != null && !connection.isClosed()){
                 connection.close();
                 connection = null;
             }
        } catch (Exception e) {

        }
    }
    
    
    public void reDirect(String url){
        
        HttpServletRequest request;
        // Http standard has 2 communication ways, "request" and "respond"
        // This method will handle the "request" piece
        
        try{
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath() + url);
        } catch(Exception e){
            
        }
    }
    
}
