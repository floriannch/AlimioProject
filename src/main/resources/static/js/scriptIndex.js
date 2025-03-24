document.addEventListener("DOMContentLoaded", function () {
    // Sélection du logo
    const logo = document.querySelector(".logo");
    if (!logo) {
        console.error("⚠️ Erreur : Élément '.logo' non trouvé !");
        return;
    }

    const text = logo.textContent.trim();
    
    // Réinitialiser le logo pour insérer chaque lettre dans un <span>
    logo.innerHTML = "";
    logo.style.position = "relative";
    logo.style.display = "inline-block";
    logo.style.opacity = "1";  // On l'affiche dès le départ

    // Injecter chaque lettre dans un <span> pour l'effet de vague
    text.split("").forEach((letter, index) => {
        const span = document.createElement("span");
        span.textContent = letter;
        span.style.display = "inline-block";
        span.style.opacity = "0"; // Invisible au départ
        span.style.transform = "translateY(-200px)"; // Chute de haut
        span.style.transition = `transform 1s ease-out ${index * 0.1}s, opacity 1s ease-out ${index * 0.1}s`;
        logo.appendChild(span);
    });

    // Lancer l'animation de chute après un court délai
    setTimeout(() => {
        document.querySelectorAll(".logo span").forEach((span) => {
            span.style.opacity = "1";
            span.style.transform = "translateY(0)";
        });

        // Une fois la chute terminée, démarrer l'effet de vague
        setTimeout(() => {
            document.querySelectorAll(".logo span").forEach((span, index) => {
                span.style.animation = `waveEffect 1s infinite ease-in-out ${index * 0.1}s`;
            });
        }, 1000);
    }, 500);

    // animation en CSS
    const style = document.createElement("style");
    style.innerHTML = `
        @keyframes waveEffect {
            0%, 100% { transform: translateY(0); }
            50% { transform: translateY(-10px); }
        }
    `;
    document.head.appendChild(style);

    console.log("✅ Animation de chute + vague activée !");
});
