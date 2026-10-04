function HomePage() {
    const hero = el('section', { className: 'hero' }, [
        el('p', { className: 'eyebrow', text: 'Developer portfolios, refined' }),
        el('h1', { className: 'hero-title', html: 'Your work.<br>One elegant link.' }),
        el('p', {
            className: 'hero-subtitle',
            text: 'ProfilerX gives every developer a customizable public portfolio — reorder sections, tune your theme, and share artifact/username with the world.'
        }),
        el('div', { className: 'hero-actions' }, [
            el('a', { href: '#/register', className: 'btn', text: 'Create portfolio' }),
            el('a', { href: '#/login', className: 'btn btn-ghost', text: 'Sign in' })
        ]),
        el('div', { className: 'hero-preview' }, [
            el('div', { className: 'preview-card' }, [
                el('div', { className: 'preview-header' }),
                el('div', { className: 'preview-line w-60' }),
                el('div', { className: 'preview-line w-40' }),
                el('div', { className: 'preview-tags' }, [
                    el('span', { className: 'preview-tag' }),
                    el('span', { className: 'preview-tag' }),
                    el('span', { className: 'preview-tag' })
                ]),
                el('div', { className: 'preview-block' }),
                el('div', { className: 'preview-block short' })
            ])
        ])
    ]);

    const features = el('section', { className: 'features container' }, [
        el('h2', { className: 'section-heading', text: 'Everything you need' }),
        el('div', { className: 'feature-grid' }, [
            featureCard('Customizable sections', 'About, skills, projects, experience, and more — show what matters, hide what doesn\'t.'),
            featureCard('Drag to reorder', 'Arrange portfolio sections in any order with a simple drag-and-drop dashboard.'),
            featureCard('Theme control', 'Set accent and background colors to match your personal brand.'),
            featureCard('Public by default', 'Share artifact/username — visitors see a clean, consistent layout.')
        ])
    ]);

    return el('div', { className: 'page page-home' }, [
        NavBar({ loggedIn: !!getToken() }),
        hero,
        features,
        el('footer', { className: 'site-footer' }, [
            el('p', { text: 'ProfilerX — built for developers who care about craft.' })
        ])
    ]);
}

function featureCard(title, body) {
    return el('article', { className: 'feature-card' }, [
        el('h3', { text: title }),
        el('p', { text: body })
    ]);
}

function renderHome(container) {
    mount(container, HomePage());
}
