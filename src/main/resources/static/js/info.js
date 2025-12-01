// static/js/info.js
window.user = window.user || {
  age: 22,
  sex: 'Male',
  symptoms: [],
  analysis: null,
  selectedCondition: null
};

function switchPanel(showId) {
  document.querySelectorAll('.panel').forEach(p => p.classList.remove('active'));
  document.getElementById(showId).classList.add('active');
}

function highlightStep(step) {
  document.querySelectorAll('.step').forEach(s => s.classList.remove('text-purple-600'));
  const el = document.querySelector(`[data-step="${step}"]`);
  if (el) el.classList.add('text-purple-600');
}

document.addEventListener('DOMContentLoaded', () => {
  // sex buttons
  document.getElementById('btnMale').addEventListener('click', () => {
    user.sex = 'Male';
    document.getElementById('btnMale').classList.add('bg-purple-500','text-white');
    document.getElementById('btnFemale').classList.remove('bg-purple-500','text-white');
    document.getElementById('btnFemale').classList.add('bg-gray-200');
  });
  document.getElementById('btnFemale').addEventListener('click', () => {
    user.sex = 'Female';
    document.getElementById('btnFemale').classList.add('bg-purple-500','text-white');
    document.getElementById('btnMale').classList.remove('bg-purple-500','text-white');
    document.getElementById('btnMale').classList.add('bg-gray-200');
  });

  document.getElementById('toSymptomsBtn').addEventListener('click', () => {
    user.age = parseInt(document.getElementById('ageInput').value) || 22;
    highlightStep('symptoms');
    switchPanel('panel-symptoms');
  });

  document.getElementById('backFromSymptoms').addEventListener('click', () => {
    highlightStep('info');
    switchPanel('panel-info');
  });

  // initial highlight
  highlightStep('info');
});
