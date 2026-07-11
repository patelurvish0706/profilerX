// Simple router
function navigateTo(hash) {
    window.location.hash = hash;
}

function handleRoute() {
    const hash = window.location.hash || '#/login';
    const app = document.getElementById('app');
    
    // Check auth
    const token = localStorage.getItem('token');
    
    if (hash === '#/login') {
        if(token) return navigateTo('#/dashboard');
        renderLogin(app);
    } else if (hash === '#/register') {
        if(token) return navigateTo('#/dashboard');
        renderRegister(app);
    } else if (hash === '#/dashboard') {
        if (!token) return navigateTo('#/login');
        renderDashboard(app);
    } else if (hash.startsWith('#/artifact/')) {
        const username = hash.split('/')[2];
        renderPortfolio(app, username);
    } else {
        app.innerHTML = '<div class="container text-center mt-3"><h1>404 Not Found</h1></div>';
    }
}

// Global Nav Header
function getNavHeader(isLoggedIn) {
    return `
    <nav>
        <a href="#/dashboard" class="logo">ProfilerX</a>
        <div class="links">
            ${isLoggedIn 
                ? `<a href="#/dashboard">Dashboard</a>
                   <a href="#" onclick="logout()">Logout</a>` 
                : `<a href="#/login">Login</a>
                   <a href="#/register">Register</a>`
            }
        </div>
    </nav>
    `;
}

function logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('username');
    navigateTo('#/login');
}

window.addEventListener('hashchange', handleRoute);
window.addEventListener('DOMContentLoaded', handleRoute);
