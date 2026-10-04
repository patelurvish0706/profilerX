function navigateTo(hash) {
    window.location.hash = hash;
}

function logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('username');
    navigateTo('#/login');
}

function handleRoute() {
    const hash = window.location.hash || '#/';
    const app = document.getElementById('app');
    const token = getToken();

    if (hash === '#/' || hash === '#/home') {
        if (token) return navigateTo('#/dashboard');
        renderHome(app);
    } else if (hash === '#/login') {
        if (token) return navigateTo('#/dashboard');
        renderLogin(app);
    } else if (hash === '#/register') {
        if (token) return navigateTo('#/dashboard');
        renderRegister(app);
    } else if (hash === '#/dashboard') {
        if (!token) return navigateTo('#/login');
        renderDashboard(app);
    } else if (hash.startsWith('#/artifact/')) {
        const username = hash.split('/')[2];
        if (!username) return navigateTo('#/');
        renderPortfolio(app, username);
    } else {
        mount(app, el('div', { className: 'page page-error' }, [
            NavBar({ loggedIn: !!token }),
            el('main', { className: 'empty-state container' }, [
                el('h1', { text: 'Page not found' }),
                el('a', { href: '#/', className: 'btn btn-secondary', text: 'Go home' })
            ])
        ]));
    }
}

window.addEventListener('hashchange', handleRoute);
window.addEventListener('DOMContentLoaded', handleRoute);
