/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.ulatina.finalproject.model;

import edu.ulatina.finalproject.service.IngredientService;
import java.io.Serializable;

/**
 *
 * @author rodo
 */
public class RecipeIngredientsTO implements Serializable{
    
    private int idRecipe;
    private int idUser;
    private int idIngredient;
    private int quantity;
    private String ingredientName;
    private IngredientService is = new IngredientService();

    public RecipeIngredientsTO(int idRecipe, int idUser, int idIngredient, int quantity) {
        this.idRecipe = idRecipe;
        this.idUser = idUser;
        this.idIngredient = idIngredient;
        this.quantity = quantity;
        this.ingredientName = is.getIngredientName(idIngredient);
    }

    public RecipeIngredientsTO() {
    }

    public int getIdRecipe() {
        return idRecipe;
    }

    public void setIdRecipe(int idRecipe) {
        this.idRecipe = idRecipe;
    }

    public int getIdIngredient() {
        return idIngredient;
    }

    public void setIdIngredient(int idIngredient) {
        this.idIngredient = idIngredient;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }
    
}
