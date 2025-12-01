// static/js/symptoms.js

// Option B symptom list (large)
const PREDEFINED_SYMPTOMS = [
  "fever","dry cough","wet cough","sore throat","fatigue","shortness of breath",
  "runny nose","sneezing","chest tightness","nausea","vomiting","diarrhea",
  "headache","body aches","chills","loss of smell","loss of taste","rash",
  "abdominal pain","joint pain","dizziness","sensitivity to light","palpitations",
  "wheezing","confusion"
];

function renderCheckboxes() {
  const container = document.getElementById('checkboxList');
  container.innerHTML = '';

  PREDEFINED_SYMPTOMS.forEach(sym => {
    const id = 'sym-' + sym.replace(/\s+/g,'-');
    const html = `
      <label class="flex items-center gap-3 bg-white border rounded p-2 shadow-sm">
        <input type="checkbox" id="${id}" data-sym="${sym}" />
        <span class="text-sm">${capitalize(sym)}</span>
      </label>
    `;
    container.insertAdjacentHTML('beforeend', html);
  });
}

function capitalize(s){ return s.charAt(0).toUpperCase() + s.slice(1); }

document.addEventListener('DOMContentLoaded', () => {
  renderCheckboxes();

  document.getElementById('toConditionsBtn').addEventListener('click', async () => {
    // collect checked symptoms
    const checked = Array.from(document.querySelectorAll('#checkboxList input[type="checkbox"]:checked'))
      .map(i => i.getAttribute('data-sym').toLowerCase());

    if (checked.length === 0) {
      alert('Please select at least one symptom.');
      return;
    }

    user.symptoms = checked;
    // call backend
    const payload = { age: user.age, sex: user.sex, symptoms: user.symptoms };
    try {
      const resp = await fetch('/api/symptoms/analyze', {
        method: 'POST',
        headers: {'Content-Type':'application/json'},
        body: JSON.stringify(payload)
      });
      if (!resp.ok) throw new Error('Server error');
      const json = await resp.json();
      user.analysis = json;
      renderConditionsList();
      highlightStep('conditions');
      switchPanel('panel-conditions');
    } catch (e) {
      console.error(e);
      alert('Could not analyze symptoms. Check server logs.');
    }
  });
});
