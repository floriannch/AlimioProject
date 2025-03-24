document.addEventListener("DOMContentLoaded", () => {
    const polaroids = document.querySelectorAll(".polaroid");

    // Texte à afficher pour chaque polaroid
    const hoverTexts = {
        "LUCAS GAUDIN": "Lucas, le virtuose de la nutrition, est notre encyclopédie vivante de la santé et du bien-être. Fort de son expérience en santé publique, il a parcouru le monde pour étudier les habitudes alimentaires et a juré de vous offrir une base de données aussi fiable que passionnante.",
        "LENA FOURNIER": "Léna, la créatrice du Petit Pois, est une passionnée des saveurs et des origines. Après avoir lancé son blog culinaire dédié aux légumes oubliés, elle a voulu aller plus loin en rendant la transparence alimentaire accessible à tous. Sa vision ? Un monde où chaque produit raconte une histoire.",
        "FLORIAN NELCHA": "Florian, l’investisseur en macarons surgelés, est un entrepreneur hors pair. Après avoir conquis le marché des douceurs glacées, il a décidé d’unir son sens des affaires à une cause qui lui tenait à cœur : réinventer notre rapport à ce que nous consommons.",
        "EVAN DA COSTA PINA": "Evan juste le mec de la compta..."
    };

    polaroids.forEach((polaroid) => {
        const caption = polaroid.querySelector(".caption").textContent.trim().toUpperCase();
        const hoverText = hoverTexts[caption] || "Découvrez nos experts !";
        const originalContent = polaroid.innerHTML; // Sauvegarde du contenu original
        
        polaroid.addEventListener("mouseenter", () => {
            polaroid.innerHTML = `<div class="hover-text" style="
                display: flex;
                align-items: center;
                justify-content: center;
                width: 100%;
                height: 100%;
                background-color: #40870F;
                color: #ffffff;
                padding: 20px;
                border-radius: 10px;
                box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
                font-size: 16px;
                text-align: center;">
                ${hoverText}
            </div>`;
        });

        polaroid.addEventListener("mouseleave", () => {
            polaroid.innerHTML = originalContent; // Restauration du contenu original
        });
    });
});
