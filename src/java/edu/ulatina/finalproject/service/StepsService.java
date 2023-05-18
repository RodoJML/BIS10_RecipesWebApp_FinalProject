/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.ulatina.finalproject.service;

import edu.ulatina.finalproject.model.StepsTO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rodo
 */
public class StepsService extends Service {

    public StepsService() {
    }

    public void addStepsEmpty(int idRecipe, int idUser, int StepNumber) {

        PreparedStatement pstmt = null;

        String querySQL = "INSERT INTO steps(id_recipe_FK, id_users_FK, stepNumber) "
                + "VALUES (?, ?, ?)";

        try {
            connect();
            pstmt = connection.prepareStatement(querySQL);
            pstmt.setInt(1, idRecipe);
            pstmt.setInt(2, idUser);
            pstmt.setInt(3, StepNumber);
            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            closeStatement(pstmt);
            disconnect();

        }
    }

    public void addStepsComplete(String description, int idRecipe, int idUser, int stepNumber) {
        PreparedStatement pstmt = null;

        String querySQL = "UPDATE steps "
                        + "SET stepDescription = ? "
                        + "WHERE id_recipe_FK = ? "
                        + "AND id_users_FK = ? "
                        + "AND stepNumber = ?";

        try {
            connect();
            pstmt = connection.prepareStatement(querySQL);
            pstmt.setString(1, description);
            pstmt.setInt(2, idRecipe);
            pstmt.setInt(3, idUser);
            pstmt.setInt(4, stepNumber);
            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            closeStatement(pstmt);
            disconnect();

        }
    }

    public List<StepsTO> list(int recipeID) {
                
        List<StepsTO> listStepsRecipe = new ArrayList<StepsTO>();

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String querySQL = "SELECT idstep, id_recipe_FK, id_users_FK, stepNumber, stepDescription "
                        + "FROM steps WHERE id_recipe_FK = ?";

        try {
            connect();

            pstmt = connection.prepareStatement(querySQL);
            pstmt.setInt(1, recipeID);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                int idStep = rs.getInt("idstep");
                int idRecipeFK = rs.getInt("id_recipe_FK");
                int idUserFK = rs.getInt("id_users_FK");
                int stepNumber = rs.getInt("stepNumber");
                String stepDescription = rs.getString("stepDescription");

                StepsTO step = new StepsTO(idStep, idRecipeFK, idUserFK, stepNumber, stepDescription);

                listStepsRecipe.add(step);
            }

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            closeResultSet(rs);
            closeStatement(pstmt);
            disconnect();
        }
        
        return listStepsRecipe;
    }
    
    public void clearSteps(int idRecipe){
        
        PreparedStatement pstmt1 = null;

        String querySQL1 = "DELETE FROM steps "
                         + "WHERE id_recipe_FK = ?";

        try {
            connect();
            pstmt1 = connection.prepareStatement(querySQL1);
            pstmt1.setInt(1, idRecipe);
            pstmt1.executeUpdate();
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeStatement(pstmt1);
            disconnect();
        }
        
    }

}
