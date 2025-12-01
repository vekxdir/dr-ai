// minimal interactions: mobile menu and small fade-in


document.addEventListener('DOMContentLoaded', function(){
// mobile menu (simple toggle)
const btn = document.getElementById('mobileBtn');
if(btn){
btn.addEventListener('click', ()=>{ alert('Mobile menu: implement as needed'); });
}


// hero fade-in
const hero = document.querySelector('h1');
if(hero){
hero.style.opacity = 0;
hero.style.transform = 'translateY(8px)';
setTimeout(()=>{
hero.style.transition = 'opacity 700ms ease, transform 700ms ease';
hero.style.opacity = 1;
hero.style.transform = 'translateY(0)';
}, 120);
}
});