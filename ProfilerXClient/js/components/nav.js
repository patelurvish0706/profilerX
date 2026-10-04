function NavBar({ loggedIn = false, minimal = false } = {}) {
    const links = loggedIn
        ? [
            el('a', { href: '#/dashboard', className: 'nav-link', text: 'Dashboard' }),
            el('button', {
                className: 'nav-link nav-btn',
                text: 'Logout',
                on: { click: (e) => { e.preventDefault(); logout(); } }
            })
        ]
        : [
            el('a', { href: '#/login', className: 'nav-link', text: 'Sign in' }),
            el('a', { href: '#/register', className: 'btn btn-sm', text: 'Get started' })
        ];

    return el('header', { className: `site-nav${minimal ? ' site-nav-minimal' : ''}` }, [
        el('a', { href: loggedIn ? '#/dashboard' : '#/', className: 'brand' }, [
            el('span', { className: 'brand-mark' }),
            el('span', { className: 'brand-text', text: 'ProfilerX' })
        ]),
        el('nav', { className: 'nav-links' }, links)
    ]);
}
