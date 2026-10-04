async function renderPortfolio(container, username) {
    mount(container, el('div', { className: 'page page-portfolio' }, [
        el('main', { className: 'portfolio-loading', text: 'Loading portfolio…' })
    ]));

    try {
        const data = await apiCall(`/${username}`);
        mount(container, PortfolioPage(data, username));
    } catch (err) {
        mount(container, el('div', { className: 'page page-error' }, [
            NavBar({ loggedIn: !!getToken(), minimal: true }),
            el('main', { className: 'empty-state container' }, [
                el('h1', { text: 'Profile unavailable' }),
                el('p', { text: err.message || 'This portfolio is private or does not exist.' }),
                el('a', { href: '#/', className: 'btn btn-secondary', text: 'Back home' })
            ])
        ]));
    }
}

function PortfolioPage(data, username) {
    const profile = data.profile || {};
    const dev = data.developer || {};
    const themeColor = profile.themeColor || '#111111';
    const bgColor = profile.backgroundColor || '#fafafa';
    const hidden = parseHiddenSections(profile.hiddenSections);

    const order = parseSectionOrder(profile.sectionOrder).filter(key => !hidden.has(key));
    const sections = order
        .map(key => buildPortfolioSection(key, data, themeColor))
        .filter(Boolean);

    const page = el('div', {
        className: 'portfolio-page',
        style: {
            '--portfolio-accent': themeColor,
            '--portfolio-bg': bgColor
        }
    }, [
        portfolioHero(dev, username, profile),
        el('main', { className: 'portfolio-body container-narrow' }, sections),
        el('footer', { className: 'portfolio-footer' }, [
            el('p', { text: `Built with ProfilerX · artifact/${username}` })
        ])
    ]);

    return page;
}

function portfolioHero(dev, username, profile) {
    const initials = (dev.fullName || username || '?')
        .split(' ')
        .map(w => w[0])
        .join('')
        .slice(0, 2)
        .toUpperCase();

    return el('header', { className: 'portfolio-hero' }, [
        el('div', { className: 'portfolio-hero-inner container-narrow' }, [
            el('div', { className: 'avatar', text: initials }),
            el('h1', { className: 'portfolio-name', text: dev.fullName || username }),
            el('p', { className: 'portfolio-handle', text: `@${username}` }),
            profile.about
                ? el('p', { className: 'portfolio-tagline', text: profile.about.split('\n')[0].slice(0, 120) })
                : null,
            el('div', { className: 'portfolio-meta' }, [
                dev.email ? el('span', { text: dev.email }) : null,
                dev.phone ? el('span', { text: dev.phone }) : null,
                profile.profileViews != null
                    ? el('span', { className: 'view-count', text: `${profile.profileViews} views` })
                    : null
            ].filter(Boolean))
        ])
    ]);
}

function buildPortfolioSection(key, data, accent) {
    const meta = SECTION_META[key];
    if (!meta) return null;

    let body = null;

    switch (key) {
        case 'about':
            body = renderAbout(data.profile?.about);
            break;
        case 'links':
            body = renderLinks(data.links);
            break;
        case 'skills':
            body = renderSkills(data.skills);
            break;
        case 'experience':
            body = renderExperience(data.experience);
            break;
        case 'projects':
            body = renderProjects(data.projects);
            break;
        case 'certifications':
            body = renderCertifications(data.certifications);
            break;
        case 'currentlyDoing':
            body = renderCurrentlyDoing(data.currentlyDoing);
            break;
        case 'blogs':
            body = renderBlogs(data.blogs);
            break;
    }

    if (!body) return null;

    return el('section', { className: 'portfolio-section fade-in', id: `section-${key}` }, [
        el('div', { className: 'section-marker', style: { backgroundColor: accent } }),
        el('div', { className: 'section-content' }, [
            el('h2', { className: 'section-title', text: meta.label }),
            body
        ])
    ]);
}

function renderAbout(text) {
    if (!text?.trim()) return null;
    return el('div', { className: 'about-text' }, text.split('\n').map(line =>
        el('p', { text: line })
    ));
}

function renderLinks(links) {
    if (!links?.length) return null;
    return el('div', { className: 'link-grid' }, links.map(link =>
        el('a', {
            href: link.url,
            className: 'link-card',
            target: '_blank',
            rel: 'noopener noreferrer'
        }, [
            el('span', { className: 'link-title', text: link.title }),
            el('span', { className: 'link-arrow', text: icon('link') })
        ])
    ));
}

function renderSkills(skills) {
    if (!skills?.length) return null;
    return el('div', { className: 'skill-cloud' }, skills.map(s =>
        el('span', { className: 'skill-tag', text: s.skillName })
    ));
}

function renderExperience(items) {
    if (!items?.length) return null;
    return el('div', { className: 'timeline' }, items.map(item =>
        el('article', { className: 'timeline-item' }, [
            el('div', { className: 'timeline-dot' }),
            el('div', { className: 'timeline-body' }, [
                el('h3', { text: `${item.role} · ${item.companyName}` }),
                el('p', { className: 'timeline-date', text: `${formatDate(item.startAt)} — ${item.endAt ? formatDate(item.endAt) : 'Present'}` }),
                item.description ? el('p', { className: 'timeline-desc', text: item.description }) : null
            ])
        ])
    ));
}

function renderProjects(projects) {
    if (!projects?.length) return null;
    return el('div', { className: 'project-grid' }, projects.map(p =>
        el('article', { className: 'project-card' }, [
            el('div', { className: 'project-head' }, [
                el('h3', { text: p.projectName }),
                p.status ? el('span', { className: `project-status status-${(p.status || '').toLowerCase()}`, text: p.status }) : null
            ]),
            p.description ? el('p', { className: 'project-desc', text: p.description }) : null,
            p.techStack?.length ? el('div', { className: 'project-stack' }, p.techStack.map(t =>
                el('span', { className: 'stack-tag', text: t })
            )) : null,
            el('div', { className: 'project-links' }, [
                p.github ? el('a', { href: p.github, target: '_blank', rel: 'noopener', text: 'GitHub' }) : null,
                p.liveLink ? el('a', { href: p.liveLink, target: '_blank', rel: 'noopener', text: 'Live demo' }) : null
            ].filter(Boolean))
        ])
    ));
}

function renderCertifications(certs) {
    if (!certs?.length) return null;
    return el('div', { className: 'cert-list' }, certs.map(c =>
        el('article', { className: 'cert-card' }, [
            el('h3', { text: c.title }),
            el('p', { className: 'cert-meta', text: [c.issuer, formatDate(c.issueDate)].filter(Boolean).join(' · ') }),
            c.credentialLink ? el('a', { href: c.credentialLink, target: '_blank', rel: 'noopener', text: 'View credential' }) : null
        ])
    ));
}

function renderCurrentlyDoing(items) {
    if (!items?.length) return null;
    return el('div', { className: 'doing-list' }, items.map(item =>
        el('article', { className: 'doing-card' }, [
            el('h3', { text: item.title }),
            item.startedAt ? el('p', { className: 'doing-date', text: `Since ${formatDate(item.startedAt)}` }) : null,
            item.description ? el('p', { text: item.description }) : null
        ])
    ));
}

function renderBlogs(blogs) {
    if (!blogs?.length) return null;
    return el('div', { className: 'blog-list' }, blogs.map(b =>
        el('article', { className: 'blog-card' }, [
            el('h3', { text: b.title }),
            b.createdAt ? el('p', { className: 'blog-date', text: formatDate(b.createdAt) }) : null,
            el('div', { className: 'blog-content', html: escapeHtml(b.content).replace(/\n/g, '<br>') })
        ])
    ));
}
