package com;

import java.util.HashMap;

public class Product {
    private String aName;
    private String aIngredients;
    private String aNutriScore;
    private HashMap<String, Boolean> aAllergens;
    private String aBarCode;
    private String aImage;

    // Ajoute des paramètres supplémentaires si nécessaire

    // Constructeur
    public Product(final String pName, final String pIngredients, final String pNutriScore, final String pBarCode, final HashMap<String, Boolean> pAllergens, final String pImage){
        this.aName = pName;
        this.aIngredients = pIngredients;
        this.aNutriScore = pNutriScore;
        this.aBarCode = pBarCode;
        this.aAllergens = pAllergens;
        this.aImage = pImage;
    }

    // Getters et Setters
    public String getaName() {
        return aName;
    }

    public void setaName(final String pName) {
        this.aName = pName;
    }

    public void setaBarCode(final String pBarCode){
        this.aBarCode = pBarCode;
    }

    public String getaIngredients() {
        return this.aIngredients;
    }

    public String getaBarCode(){
        return this.aBarCode;
    }

    public void setaIngredients(final String pIngredients) {
        this.aIngredients = pIngredients;
    }

    public String getaNutriScore() {
        return this.aNutriScore;
    }

    public void setaNutriScore(final String pNutriScore) {
        this.aNutriScore = pNutriScore;
    }

    public HashMap<String, Boolean> getaAllergens() {
        return this.aAllergens;
    }

    public void setaAllergens(HashMap<String, Boolean> pAllergens) {
        this.aAllergens = pAllergens;
    }

    public void addAllergen(String pAllergen, boolean contains) {
        this.aAllergens.put(pAllergen, contains);
    }

    public boolean containsAllergen(String pAllergen) {
        return this.aAllergens.getOrDefault(pAllergen.toLowerCase(), false);
    }

    public void setaImage(final String pImage){
        this.aImage = pImage;
    }

    public String getaImage(){
        return this.aImage;
    }
    
    // Méthode toString pour faciliter le débogage
    @Override
    public String toString() {
        return "Product{name='" + this.aName + "', ingredients='" + this.aIngredients + "', nutriscore='" + this.aNutriScore + "', allergens=" + this.aAllergens + "'}";
    }
}
