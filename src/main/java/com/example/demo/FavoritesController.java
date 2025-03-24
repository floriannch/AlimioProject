package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import com.Product;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class FavoritesController {

    // Cette méthode montre la liste des produits favoris
    @GetMapping("/favoris")
    public String showFavorites(
            @RequestParam(value = "codes", required = false) List<String> codes,
            Model model) {

        List<Product> favoriteProducts = new ArrayList<>();

        // Si on a des codes de produits
        if (codes != null && !codes.isEmpty()) {
            RestTemplate restTemplate = new RestTemplate();
            ObjectMapper objectMapper = new ObjectMapper();

            for (String code : codes) {
                try {
                    // Récupérer les informations du produit depuis l'API
                    String url = "https://world.openfoodfacts.org/api/v0/product/" + code + ".json";
                    String response = restTemplate.getForObject(url, String.class);
                    JsonNode rootNode = objectMapper.readTree(response);
                    JsonNode productNode = rootNode.path("product");

                    if (!productNode.isMissingNode()) {
                        // Extraire les informations nécessaires
                        String productName = productNode.path("product_name").asText("Nom inconnu");
                        String ingredientsText = productNode.path("ingredients_text").asText("Ingrédients non trouvés");
                        String nutriScoreGrade = productNode.path("nutriscore_grade").asText("");
                        String barCode = productNode.path("code").asText("");
                        String image = productNode.path("image_front_url").asText("");

                        // Extraire les allergènes
                        JsonNode allergenTags = productNode.path("allergens_tags");
                        HashMap<String, Boolean> allergensMap = new HashMap<>();
                        if (allergenTags.isArray()) {
                            for (JsonNode allergenNode : allergenTags) {
                                allergensMap.put(allergenNode.asText().toLowerCase(), true);
                            }
                        }

                        // Créer l'objet Product
                        Product product = new Product(productName, ingredientsText, nutriScoreGrade, barCode, allergensMap, image);
                        favoriteProducts.add(product);
                        
                    }
                } catch (Exception e) {
                    System.out.println("Erreur lors de la récupération du produit : " + e.getMessage());
                }
            }
        }

        // Ajouter les produits favoris au modèle
        model.addAttribute("favorites", favoriteProducts);
        return "favoris"; // Retourne le template favoris
    }

    
}
