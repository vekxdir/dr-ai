function switchPanel(oldId, newId) {
    document.getElementById(oldId).classList.add("hidden");
    document.getElementById(newId).classList.remove("hidden");
}

function highlightStep(step) {
    document.querySelectorAll(".step").forEach(s =>
        s.classList.remove("text-purple-600"));

    document.querySelector(`[data-step="${step}"]`)
        .classList.add("text-purple-600");
}
