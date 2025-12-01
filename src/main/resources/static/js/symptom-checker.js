// ======================
// DR-AI Symptom Checker JS
// ======================

// ----- RENDER MAIN UI -----
document.getElementById("app").innerHTML = `
<div class="max-w-5xl mx-auto py-10 px-6">

  <h1 class="text-3xl font-extrabold text-purple-700 text-center">Symptom Checker</h1>

  <div class="mt-10 bg-white rounded-xl card p-6">

    <!-- Steps -->
    <div id="stepTabs" class="flex gap-4 border-b pb-4">
        <button class="stepTab active" data-step="0">Info</button>
        <button class="stepTab" data-step="1">Symptoms</button>
        <button class="stepTab" data-step="2">Conditions</button>
        <button class="stepTab" data-step="3">Details</button>
        <button class="stepTab" data-step="4">Treatment</button>
    </div>

    <!-- Step Panels -->
    <div id="panels" class="mt-6">

      <!-- Step 0 -->
      <div class="panel" data-panel="0">
        <label class="block text-sm">Age</label>
        <input id="age" type="number" class="border p-2 rounded mt-2" value="22">

        <label class="block mt-4 text-sm">Sex</label>
        <select id="sex" class="border p-2 rounded mt-2">
            <option value="">Select</option>
            <option value="male">Male</option>
            <option value="female">Female</option>
        </select>
      </div>

      <!-- Step 1 -->
      <div class="panel hidden" data-panel="1">
        <label class="text-sm">Add Symptom</label>
        <div class="flex gap-2 mt-2">
          <input id="symptomInput" class="border p-2 rounded flex-1" placeholder="e.g. fever">
          <button id="addSymptomBtn" class="bg-purple-600 text-white px-4 py-2 rounded">Add</button>
        </div>

        <ul id="symptomList" class="mt-4 space-y-2"></ul>
      </div>

      <!-- Step 2 -->
      <div class="panel hidden" data-panel="2">
        <div id="conditionList" class="space-y-3"></div>
      </div>

      <!-- Step 3 -->
      <div class="panel hidden" data-panel="3">
        <h2 id="detailTitle" class="text-xl font-bold"></h2>
        <p id="detailOverview" class="mt-3 text-gray-700"></p>
      </div>

      <!-- Step 4 -->
      <div class="panel hidden" data-panel="4">
        <h2 id="treatTitle" class="text-xl font-bold"></h2>
        <p id="treatContent" class="mt-3 text-gray-700"></p>
        <h3 class="mt-4 font-semibold">When to see doctor</h3>
        <p id="doctorWhen" class="text-gray-700"></p>
      </div>

    </div>

    <div class="mt-6 flex justify-between">
      <button id="prevBtn" class="px-4 py-2 border rounded">Previous</button>
      <button id="nextBtn" class="px-4 py-2 bg-purple-600 text-white rounded">Continue</button>
    </div>

  </div>
</div>
`;


// =======================
// STATE
// =======================
let step = 0;
const state = {
    age: null,
    sex: null,
    symptoms: [],
    conditions: [],
    selected: null
};

// =======================
// STEP HANDLING
// =======================
const panels = document.querySelectorAll(".panel");
const tabs = document.querySelectorAll(".stepTab");

function showStep(i) {
    step = i;
    panels.forEach(p => p.classList.add("hidden"));
    document.querySelector(`.panel[data-panel="${i}"]`).classList.remove("hidden");

    tabs.forEach(t => t.classList.remove("active", "text-purple-700"));
    document.querySelector(`.stepTab[data-step="${i}"]`).classList.add("text-purple-700");
}

showStep(0);

// Buttons
document.getElementById("prevBtn").onclick = () => {
    if (step > 0) showStep(step - 1);
};

document.getElementById("nextBtn").onclick = async () => {
    if (step === 1) await fetchRealConditions();
    if (step < 4) showStep(step + 1);
};

// =======================
// SYMPTOMS
// =======================
document.getElementById("addSymptomBtn").onclick = () => {
    const input = document.getElementById("symptomInput");
    const s = input.value.trim();
    if (!s) return;

    state.symptoms.push(s);

    const li = document.createElement("li");
    li.className = "p-2 border rounded flex justify-between";
    li.innerHTML = `${s} <button class="text-red-600">X</button>`;
    li.querySelector("button").onclick = () => {
        li.remove();
        state.symptoms = state.symptoms.filter(x => x !== s);
    };

    document.getElementById("symptomList").appendChild(li);
    input.value = "";
};

// =======================
// REAL API CALL
// =======================
async function fetchRealConditions() {
    state.age = document.getElementById("age").value;
    state.sex = document.getElementById("sex").value;

    const res = await fetch("/api/symptoms/analyze", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(state)
    });

    const data = await res.json();
    state.conditions = data.conditions;

    renderConditions();
}

// =======================
// RENDER CONDITIONS
// =======================
function renderConditions() {
    const area = document.getElementById("conditionList");
    area.innerHTML = "";

    state.conditions.forEach((c, i) => {
        const div = document.createElement("div");
        div.className = "p-4 border rounded bg-purple-50 cursor-pointer";

        div.innerHTML = `
            <div class="font-semibold text-purple-700">${c.name}</div>
            <div class="text-xs text-gray-600">${c.matchLevel}</div>
        `;

        div.onclick = () => {
            state.selected = c;
            showDetails();
            showStep(3);
        };

        area.appendChild(div);
    });
}

// =======================
// SHOW DETAILS + TREATMENT
// =======================
function showDetails() {
    const c = state.selected;

    document.getElementById("detailTitle").innerHTML = c.name;
    document.getElementById("detailOverview").innerHTML = c.overview;

    document.getElementById("treatTitle").innerHTML = c.name + " — Treatment";
    document.getElementById("treatContent").innerHTML = c.treatment;
    document.getElementById("doctorWhen").innerHTML = c.whenToSeeDoctor;
}
