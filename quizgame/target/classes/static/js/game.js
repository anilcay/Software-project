/* src/main/resources/static/js/game.js */
function checkAuth() {
  // Örnek: oturum kontrolü
  fetch('/api/user/me')
    .then(r => (r.ok ? r.json() : Promise.reject()))
    .then(u => console.log('Kullanıcı:', u))
    .catch(() => (window.location.href = '/index.html'));
}

window.onload = checkAuth;   // HTML tarafına onload yazmanıza gerek kalmaz
