function RegisterPage() {
    const errorEl = el('p', { className: 'form-error', id: 'registerError' });

    const form = el('form', {
        className: 'auth-form',
        id: 'registerForm',
        on: { submit: handleRegister }
    }, [
        el('div', { className: 'form-row' }, [
            el('div', { className: 'form-group' }, [
                el('label', { for: 'fullName', text: 'Full name' }),
                el('input', { id: 'fullName', name: 'fullName', type: 'text', placeholder: 'Alex Developer', required: true })
            ]),
            el('div', { className: 'form-group' }, [
                el('label', { for: 'regUsername', text: 'Username' }),
                el('input', { id: 'regUsername', name: 'regUsername', type: 'text', placeholder: 'alexdev', required: true })
            ])
        ]),
        el('div', { className: 'form-group' }, [
            el('label', { for: 'email', text: 'Email' }),
            el('input', { id: 'email', name: 'email', type: 'email', placeholder: 'you@example.com', required: true })
        ]),
        el('div', { className: 'form-group' }, [
            el('label', { for: 'phone', text: 'Phone' }),
            el('input', {
                id: 'phone', name: 'phone', type: 'text',
                placeholder: '10-digit number', required: true, minLength: 10, maxLength: 10
            })
        ]),
        el('div', { className: 'form-group' }, [
            el('label', { for: 'regPassword', text: 'Password' }),
            el('input', {
                id: 'regPassword', name: 'regPassword', type: 'password',
                placeholder: 'Min. 6 characters', required: true, minLength: 6
            })
        ]),
        el('p', { className: 'form-note', text: 'Email and phone verification will be required before publishing.' }),
        el('button', { type: 'submit', className: 'btn btn-full', text: 'Create account' }),
        errorEl
    ]);

    const card = el('div', { className: 'auth-card auth-card-wide' }, [
        el('div', { className: 'auth-card-head' }, [
            el('h1', { text: 'Create your portfolio' }),
            el('p', { text: 'Join ProfilerX and get a public page at artifact/username.' })
        ]),
        form,
        el('p', { className: 'auth-switch' }, [
            document.createTextNode('Already registered? '),
            el('a', { href: '#/login', text: 'Sign in' })
        ])
    ]);

    return el('div', { className: 'page page-auth' }, [
        NavBar({ loggedIn: false }),
        el('main', { className: 'auth-shell' }, [card])
    ]);
}

async function handleRegister(e) {
    e.preventDefault();
    const errorDiv = document.getElementById('registerError');
    errorDiv.textContent = '';

    const form = e.target;
    const body = {
        fullName: form.fullName.value.trim(),
        username: form.regUsername.value.trim(),
        email: form.email.value.trim(),
        phone: form.phone.value.trim(),
        password: form.regPassword.value
    };

    try {
        const response = await apiCall('/register', 'POST', body);
        localStorage.setItem('token', response.token);
        localStorage.setItem('username', response.username);
        navigateTo('#/dashboard');
    } catch (err) {
        errorDiv.textContent = err.message || 'Registration failed. Please check your details.';
    }
}

function renderRegister(container) {
    mount(container, RegisterPage());
}
