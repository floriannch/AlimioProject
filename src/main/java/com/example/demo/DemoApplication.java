package com.example.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.Product;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
@Controller
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
    @GetMapping("/") // Page d'accueil
    public String getMethodName(@RequestParam(value = "param", required = false, defaultValue = "default") String param) { // ne pas oublier le required = false
        return "homePage";
    }
    
    // Recherche filtre possible : Nutriscore, allergène, additifs
    @GetMapping("/recherche")
    public String searchPage(
            @RequestParam(value = "query", required = false) String query,
            @RequestParam(value = "nutriscore", required = false) String nutriscore,
            @RequestParam(value = "allergen", required = false) String allergen,
            Model model) {

        if (query != null && !query.isEmpty()) {
            try {
                // Construire l'URL de recherche
                String url = "https://world.openfoodfacts.org/cgi/search.pl?search_terms=" + query + 
                            "&search_simple=1&action=process&json=1";

                RestTemplate restTemplate = new RestTemplate();
                String response = restTemplate.getForObject(url, String.class);

                // Analyse de la réponse JSON
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(response);
                JsonNode products = rootNode.path("products");

                List<Product> productList = new ArrayList<>();
                HashMap<String, Boolean> allergensMap = new HashMap<>();

                if (products.isArray() && products.size() > 0) {
                    for (JsonNode product : products) {
                        // On récupère tous les éléments que l'on souhaite afficher
                        String productName = product.path("product_name").asText("Nom inconnu");
                        String ingredientsText = product.path("ingredients_text").asText("Ingrédients non trouvés");
                        String nutriScoreGrade = product.path("nutriscore_grade").asText("");
                        String barCode = product.path("code").asText("");
                        String image = product.path("image_front_url").asText("");

                        JsonNode allergenTags = product.path("allergens_tags");

                        if (allergenTags.isArray()) {
                            for (JsonNode allergenNode : allergenTags) {
                                allergensMap.put(allergenNode.asText().toLowerCase(), true);
                            }
                        }

                        Product productObj = new Product(productName, ingredientsText, nutriScoreGrade, barCode ,allergensMap, image);

                        // Appliquer le filtre sur le Nutri-Score + allergènes
                        if (this.isValidNutriScore(nutriScoreGrade, nutriscore) && this.isAllergenFree(productObj, allergen)) {
                            productList.add(productObj);
                        }
                    }
                }

                // Ajouter les produits au modèle
                model.addAttribute("products", productList);

            } catch (Exception e) {
                model.addAttribute("message", "Erreur lors de l'appel à l'API : " + e.getMessage());
            }
        } else {
            model.addAttribute("message", "Veuillez entrer un nom de produit.");
        }

        return "search";
    }

    /// Ensemble de fonction et méthodes supplémentaire
    
    // Retourne true si le nutriscore du produit est >= à celui sélectionné
    private boolean isValidNutriScore(String productScore, String filterScore) {
        if (filterScore == null || filterScore.isEmpty()) {
            return true; // Pas de filtre, tous les produits sont valides
        }

        // Traduire les Nutri-Scores en valeurs numériques
        Map<String, Integer> nutriScoreMap = Map.of(
            "a", 1,
            "b", 2,
            "c", 3,
            "d", 4,
            "e", 5
        );

        Integer productValue = nutriScoreMap.get(productScore.toLowerCase());
        Integer filterValue = nutriScoreMap.get(filterScore.toLowerCase());

        // Si le Nutri-Score du produit ou du filtre est invalide, considérer comme non valide
        if (productValue == null || filterValue == null) {
            return false;
        }

        return productValue <= filterValue; // Produit valide si son Nutri-Score est supérieur ou égal
    }

    private boolean isAllergenFree(Product product, String filterAllergen) {
        if (filterAllergen == null || filterAllergen.isEmpty()) {
            return true; // Pas de filtre, tous les produits sont valides
        }
    
        return !product.containsAllergen(filterAllergen.toLowerCase()); // false si l'allergène est présent
    }
    
}
