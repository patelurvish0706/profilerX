function renderRegister(container) {
    container.innerHTML = `
        ${getNavHeader(false)}
        <div class="container" style="max-width: 500px; margin-top: 3rem;">
            <div class="glass-panel text-center">
                <h2>Create Account</h2>
                <p class="mb-2">Join ProfilerX to build your portfolio</p>
                <form id="registerForm" onsubmit="handleRegister(event)">
                    <div class="form-group text-left">
                        <input type="text" id="fullName" placeholder="Full Name" required>
                    </div>
                    <div class="form-group text-left">
                        <input type="text" id="regUsername" placeholder="Username" required>
                    </div>
                    <div class="form-group text-left">
                        <input type="email" id="email" placeholder="Email Address" required>
                    </div>
                    <div class="form-group text-left">
                        <input type="text" id="phone" placeholder="Phone Number (10 digits)" required minlength="10" maxlength="10">
                    </div>
                    <div class="form-group text-left">
                        <input type="password" id="regPassword" placeholder="Password (Min 6 chars)" required minlength="6">
                    </div>
                    <button type="submit" class="btn" style="width: 100%;">Register</button>
                    <div id="registerError" class="mt-2" style="color: red; font-size: 0.9rem;"></div>
                </form>
                <p class="mt-2" style="font-size: 0.9rem;">
                    Already have an account? <a href="#/login" style="color: var(--accent);">Login here</a>
                </p>
            </div>
        </div>
    `;
}

async function handleRegister(e) {
    e.preventDefault();
    const errorDiv = document.getElementById('registerError');
    errorDiv.innerText = '';
    
    const body = {
        fullName: e.target.fullName.value,
        username: e.target.regUsername.value,
        email: e.target.email.value,
        phone: e.target.phone.value,
        password: e.target.regPassword.value
    };

    try {
        const response = await apiCall('/register', 'POST', body);
        localStorage.setItem('token', response.token);
        localStorage.setItem('username', response.username);
        navigateTo('#/dashboard');
    } catch (err) {
        errorDiv.innerText = 'Registration failed. ' + err.message;
    }
}
