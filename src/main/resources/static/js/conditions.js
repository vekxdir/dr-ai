// static/js/conditions.js

function renderConditionsList() {
  const container = document.getElementById('conditionResults');
  container.innerHTML = '';

  if (!user.analysis || !user.analysis.conditions || user.analysis.conditions.length === 0) {
    container.innerHTML = `<div class="text-gray-600">No matching conditions found. Try more symptoms or restart.</div>`;
    return;
  }

  user.analysis.conditions.forEach((c, idx) => {
    const html = `
      <div class="p-4 border rounded hover:shadow cursor-pointer" data-index="${idx}">
        <div class="flex justify-between items-start">
          <div>
            <div class="font-semibold text-purple-700 text-lg">${c.name}</div>
            <div class="text-sm text-gray-600 mt-1">${c.overview}</div>
          </div>
          <div class="text-sm text-gray-500">
            Match: <strong>${Math.round((c.matchScore||0)*100)}%</strong>
          </div>
        </div>
      </div>
    `;
    container.insertAdjacentHTML('beforeend', html);
  });

  // click handlers to pick a condition and go to details
  container.querySelectorAll('[data-index]').forEach(el => {
    el.addEventListener('click', () => {
      const i = parseInt(el.getAttribute('data-index'));
      user.selectedCondition = user.analysis.conditions[i];
      renderDetails();
      highlightStep('details');
      switchPanel('panel-details');
    });
  });

  // back
  document.getElementById('backFromConditions').addEventListener('click', () => {
    highlightStep('symptoms');
    switchPanel('panel-symptoms');
  });
}
