/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.ulatina.finalproject.model;

import java.io.Serializable;
import java.util.Objects;
/**
 *
 * @author rodo
 */

public class IngredientTO implements Serializable, Comparable<IngredientTO>{
    
    private int idIngredient;
    private String ingredientName;

    public IngredientTO() {
    }
    
    public IngredientTO(int idIngredient, String ingredientName) {
        this.idIngredient = idIngredient;
        this.ingredientName = ingredientName;
    }

    public int getIdIngredient() {
        return idIngredient;
    }

    public void setIdIngredient(int idIngredient) {
        this.idIngredient = idIngredient;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        IngredientTO ingredientTO = (IngredientTO) o;
        return idIngredient == ingredientTO.idIngredient
                && Objects.equals(ingredientName,   ingredientTO.ingredientName)
                && Objects.equals(idIngredient,     ingredientTO.idIngredient);
    }
    

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + this.idIngredient;
        hash = 53 * hash + Objects.hashCode(this.ingredientName);
        return hash;
    }
    
    @Override
    public String toString() {
        return idIngredient + "";
    }

    @Override
    public int compareTo(IngredientTO o) {
        return ingredientName.compareTo(o.ingredientName);
    }
    
}
