function LoginPage() {
    const errorEl = el('p', { className: 'form-error', id: 'loginError' });

    const form = el('form', {
        className: 'auth-form',
        id: 'loginForm',
        on: { submit: handleLogin }
    }, [
        el('div', { className: 'form-group' }, [
            el('label', { for: 'username', text: 'Username' }),
            el('input', { id: 'username', name: 'username', type: 'text', placeholder: 'yourname', required: true })
        ]),
        el('div', { className: 'form-group' }, [
            el('label', { for: 'password', text: 'Password' }),
            el('input', { id: 'password', name: 'password', type: 'password', placeholder: '••••••••', required: true })
        ]),
        el('button', { type: 'submit', className: 'btn btn-full', text: 'Sign in' }),
        errorEl
    ]);

    const card = el('div', { className: 'auth-card' }, [
        el('div', { className: 'auth-card-head' }, [
            el('h1', { text: 'Welcome back' }),
            el('p', { text: 'Sign in to manage your portfolio.' })
        ]),
        form,
        el('p', { className: 'auth-switch' }, [
            document.createTextNode('No account? '),
            el('a', { href: '#/register', text: 'Create one' })
        ])
    ]);

    return el('div', { className: 'page page-auth' }, [
        NavBar({ loggedIn: false }),
        el('main', { className: 'auth-shell' }, [card])
    ]);
}

async function handleLogin(e) {
    e.preventDefault();
    const errorDiv = document.getElementById('loginError');
    errorDiv.textContent = '';

    const form = e.target;
    const body = {
        username: form.username.value.trim(),
        password: form.password.value
    };

    try {
        const response = await apiCall('/login', 'POST', body);
        localStorage.setItem('token', response.token);
        localStorage.setItem('username', response.username);
        navigateTo('#/dashboard');
    } catch {
        errorDiv.textContent = 'Invalid credentials. Please try again.';
    }
}

function renderLogin(container) {
    mount(container, LoginPage());
}
