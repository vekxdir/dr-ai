// static/js/treatment.js

function renderTreatment(){
  const box = document.getElementById('treatmentBox');
  if (!user.selectedCondition) {
    box.innerHTML = `<div class="text-gray-600">Select a condition first.</div>`;
    return;
  }
  // keep same treatment text; could be more structured later
  box.innerHTML = `
    <h3 class="font-semibold text-lg mb-2">${user.selectedCondition.name}</h3>
    <p class="mb-3">${user.selectedCondition.treatment}</p>
    <p class="text-sm text-gray-600">Note: This is informational only. Always consult a doctor for diagnosis and prescription.</p>
  `;
}

document.addEventListener('DOMContentLoaded', () => {
  document.getElementById('backFromTreatment').addEventListener('click', () => {
    highlightStep('details');
    switchPanel('panel-details');
  });

  document.getElementById('restartBtn').addEventListener('click', () => {
    window.location.reload();
  });
});
