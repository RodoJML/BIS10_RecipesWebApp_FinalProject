/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.ulatina.finalproject.service;

import edu.ulatina.finalproject.model.UserTO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author 
 */

public class UserService extends Service {


    public UserService() {
    }

    public void addUser(String name, String lastName, int age, String email, String password) {

        PreparedStatement pstmt = null;

        String querySQL = "INSERT INTO userRecipe(name, lastName, age, email, password) "
                        + "VALUES (?, ?, ?, ?, ?)";
       
        try {
            connect();

            pstmt = connection.prepareStatement(querySQL);
            
            pstmt.setString(1, name);
            pstmt.setString(2, lastName);
            pstmt.setInt(3, age);
            pstmt.setString(4, email);
            pstmt.setString(5, password);

            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();

        } finally {

            closeStatement(pstmt);
            disconnect();
        }
    }
    
    public UserTO validate(String email, String password) {

        UserTO userTO = null;
        
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String querySQL = "SELECT id, name, lastName, age, email, password"
                        + " FROM userRecipe"
                        + " WHERE email =?"
                        + " AND password =?";

        try {
            connect();

            pstmt = connection.prepareStatement(querySQL);

            pstmt.setString(1, email);
            pstmt.setString(2, password);
            rs = pstmt.executeQuery();
         
            if (rs.next()) {
           
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String lastName = rs.getString("lastName");
                int age = rs.getInt("age");
                String mail = rs.getString("email");
                String pw = rs.getString("password");

                userTO = new UserTO(id, name, lastName, age, mail, pw);
                
            }

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            closeResultSet(rs);
            closeStatement(pstmt);
            disconnect();
        }

        return userTO;
    }
    
    public void list() {
    }
    
    
    public String getUserName(int idUser){
        
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String userName = ""; 
        String userLastName = "";

        String querySQL = "SELECT name, lastName "
                        + "FROM userRecipe "
                        + "WHERE id = ?";      
        
        try {
            connect();

            pstmt = connection.prepareStatement(querySQL);
            pstmt.setInt(1, idUser);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                userName = rs.getString("name");
                userLastName = rs.getString("lastName");
            }

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            closeResultSet(rs);
            closeStatement(pstmt);
            disconnect();
            // Step 4 - Close Connections
        }
        
        return userName + " " + userLastName;
    }

}
