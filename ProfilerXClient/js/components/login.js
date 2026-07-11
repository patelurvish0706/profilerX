function renderLogin(container) {
    container.innerHTML = `
        ${getNavHeader(false)}
        <div class="container" style="max-width: 400px; margin-top: 4rem;">
            <div class="glass-panel text-center">
                <h2>Welcome Back</h2>
                <p class="mb-2">Login to manage your portfolio</p>
                <form id="loginForm" onsubmit="handleLogin(event)">
                    <div class="form-group text-left">
                        <input type="text" id="username" placeholder="Username" required>
                    </div>
                    <div class="form-group text-left">
                        <input type="password" id="password" placeholder="Password" required>
                    </div>
                    <button type="submit" class="btn" style="width: 100%;">Login</button>
                    <div id="loginError" class="mt-2" style="color: red; font-size: 0.9rem;"></div>
                </form>
                <p class="mt-2" style="font-size: 0.9rem;">
                    Don't have an account? <a href="#/register" style="color: var(--accent);">Register here</a>
                </p>
            </div>
        </div>
    `;
}

async function handleLogin(e) {
    e.preventDefault();
    const errorDiv = document.getElementById('loginError');
    errorDiv.innerText = '';
    
    const body = {
        username: e.target.username.value,
        password: e.target.password.value
    };

    try {
        const response = await apiCall('/login', 'POST', body);
        localStorage.setItem('token', response.token);
        localStorage.setItem('username', response.username);
        navigateTo('#/dashboard');
    } catch (err) {
        errorDiv.innerText = 'Login failed. Please check your credentials.';
    }
}
