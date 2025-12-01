// static/js/details.js

function renderDetails() {
  const box = document.getElementById('detailsBox');
  if (!user.selectedCondition) {
    box.innerHTML = `<div class="text-gray-600">Select a condition to view details.</div>`;
    return;
  }
  const c = user.selectedCondition;
  box.innerHTML = `
    <h3 class="font-semibold text-xl mb-2">${c.name}</h3>
    <p class="mb-3">${c.overview}</p>
    <p class="text-sm text-gray-700"><strong>Suggested care / what to expect:</strong><br/>${c.treatment}</p>
  `;
}

// next/back wiring
document.addEventListener('DOMContentLoaded', () => {
  document.getElementById('backFromDetails').addEventListener('click', () => {
    highlightStep('conditions');
    switchPanel('panel-conditions');
  });

  document.getElementById('toTreatmentBtn').addEventListener('click', () => {
    if (!user.selectedCondition) {
      alert('Please choose a condition first.');
      return;
    }
    renderTreatment();
    highlightStep('treatment');
    switchPanel('panel-treatment');
  });
});
