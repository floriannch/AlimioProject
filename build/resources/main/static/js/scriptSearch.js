document.addEventListener("DOMContentLoaded", () => {
    markFavorites();
});

function toggleFavorite(barCode) {
    let favorites = getFavorites();

    if (favorites.includes(barCode)) {
        favorites = favorites.filter(code => code !== barCode);
    } else {
        favorites.push(barCode);
    }

    localStorage.setItem("favorites", JSON.stringify(favorites));
    markFavorites();
}

function getFavorites() {
    let favorites = localStorage.getItem("favorites");
    return favorites ? JSON.parse(favorites) : [];
}

function markFavorites() {
    let favorites = getFavorites();
    document.querySelectorAll(".favorite-checkbox").forEach(checkbox => {
        checkbox.checked = favorites.includes(checkbox.value);
    });
}

window.onload = markFavorites;