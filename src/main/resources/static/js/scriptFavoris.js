function toggleFavorite(barCode) {
    let favorites = getFavorites();

    if (favorites.includes(barCode)) {
        favorites = favorites.filter(code => code !== barCode); // Retirer le favori
    } else {
        favorites.push(barCode); // Ajouter le favori
    }

    localStorage.setItem("favorites", JSON.stringify(favorites));
    markFavorites();

    // Recharge la page avec les nouveaux favoris
    let codes = favorites.join(',');
    window.location.href = "/favoris?codes=" + codes;
}


// Fonction pour récupérer les favoris depuis le localStorage
function getFavorites() {
    let favorites = localStorage.getItem("favorites");
    return favorites ? JSON.parse(favorites) : [];
}

// Fonction pour marquer les favoris dans la page
function markFavorites() {
    let favorites = getFavorites();
    document.querySelectorAll(".favorite-checkbox").forEach(checkbox => {
        checkbox.checked = favorites.includes(checkbox.value);
    });
}

// Fonction pour charger les favoris et rediriger vers l'URL avec les codes
function loadFavorites() {
    let codes = getFavorites().join(',');
    let currentUrl = new URL(window.location.href);

    // Vérifie si l'URL contient déjà les codes pour éviter une boucle infinie
    if (!currentUrl.searchParams.has("codes")) {
        window.location.href = "/favoris?codes=" + codes;
    }
}

// Marquer les favoris lorsque la page est chargée
document.addEventListener("DOMContentLoaded", () => {
    markFavorites();
});

window.addEventListener("storage", () => {
    let codes = getFavorites().join(',');
    let currentUrl = new URL(window.location.href);

    if (currentUrl.searchParams.get("codes") !== codes) {
        window.location.href = "/favoris?codes=" + codes;
    }
});
