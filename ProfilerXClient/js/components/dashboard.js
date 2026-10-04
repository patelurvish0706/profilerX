let dashboardState = {
    profile: {},
    sectionOrder: [],
    hiddenSections: new Set(),
    skills: [],
    links: [],
    experience: [],
    projects: [],
    certifications: [],
    currentlyDoing: [],
    blogs: []
};

async function renderDashboard(container) {
    mount(container, el('div', { className: 'page page-dashboard' }, [
        NavBar({ loggedIn: true }),
        el('main', { className: 'dashboard-shell container', id: 'dashboardRoot' }, [
            el('div', { className: 'loading-state', text: 'Loading dashboard…' })
        ])
    ]));

    try {
        const data = await apiCall('/dashboard');
        dashboardState.profile = data.profile || {};
        
        let orderString = data.arrangement ? data.arrangement.sectionOrder : data.profile.sectionOrder;
        dashboardState.sectionOrder = parseSectionOrder(orderString);
        dashboardState.hiddenSections = parseHiddenSections(data.profile.hiddenSections); // if still storing here

        dashboardState.skills = data.skills || [];
        dashboardState.links = data.links || [];
        dashboardState.experience = data.experience || [];
        dashboardState.projects = data.projects || [];
        dashboardState.certifications = data.certifications || [];
        dashboardState.currentlyDoing = data.currentlyDoing || [];
        dashboardState.blogs = data.blogs || [];

        const missing = Object.keys(SECTION_META).filter(k => !dashboardState.sectionOrder.includes(k));
        dashboardState.sectionOrder.push(...missing);

        renderDashboardContent();
    } catch (err) {
        const root = document.getElementById('dashboardRoot');
        if (err.message.includes('authenticate') || err.message.includes('JWT') || err.message.includes('403')) {
            logout();
            return;
        }
        mount(root, el('div', { className: 'empty-state' }, [
            el('h2', { text: 'Could not load dashboard' }),
            el('p', { text: err.message || 'Please try again later.' })
        ]));
    }
}

function renderDashboardContent() {
    const username = getUsername() || 'you';
    const profile = dashboardState.profile;
    const views = profile.profileViews ?? 0;
    const isPublic = profile.visibility !== false;

    const root = document.getElementById('dashboardRoot');
    mount(root,
        dashboardHeader(username, views, isPublic),
        dashboardStats(views, isPublic),
        dashboardSettings(profile),
        dashboardSections(),
        dashboardContentEditors(),
        dashboardAnalytics(views)
    );

    bindDashboardEvents();
    renderSectionList();
}

function dashboardHeader(username, views, isPublic) {
    return el('section', { className: 'dash-header panel fade-in' }, [
        el('div', { className: 'dash-header-copy' }, [
            el('p', { className: 'eyebrow', text: 'Developer dashboard' }),
            el('h1', { text: `Welcome, ${username}` }),
            el('p', { text: 'Customize your portfolio, control visibility, and preview your public page.' })
        ]),
        el('div', { className: 'dash-header-actions' }, [
            el('a', {
                href: `#/artifact/${username}`,
                className: 'btn btn-secondary',
                target: '_blank',
                text: 'Preview portfolio'
            }),
            el('span', {
                className: `status-pill ${isPublic ? 'status-public' : 'status-private'}`,
                text: isPublic ? 'Public' : 'Private'
            })
        ])
    ]);
}

function dashboardStats(views, isPublic) {
    return el('section', { className: 'stat-grid fade-in' }, [
        statCard('Total views', views, 'Lifetime portfolio impressions'),
        statCard('Visibility', isPublic ? 'Public' : 'Private', isPublic ? 'Anyone with the link can view' : 'Hidden from visitors'),
        statCard('Sections', `${dashboardState.sectionOrder.length - dashboardState.hiddenSections.size} visible`, 'Active portfolio sections')
    ]);
}

function statCard(label, value, hint) {
    return el('article', { className: 'stat-card panel' }, [
        el('p', { className: 'stat-label', text: label }),
        el('p', { className: 'stat-value', text: String(value) }),
        el('p', { className: 'stat-hint', text: hint })
    ]);
}

function dashboardSettings(profile) {
    return el('section', { className: 'panel dash-panel fade-in' }, [
        el('div', { className: 'panel-head' }, [
            el('h2', { text: 'Profile settings' }),
            el('p', { text: 'Theme, visibility, and about content.' })
        ]),
        el('form', { id: 'settingsForm', className: 'settings-form', on: { submit: saveSettings } }, [
            el('div', { className: 'form-row' }, [
                el('div', { className: 'form-group' }, [
                    el('label', { for: 'visibility', text: 'Portfolio visibility' }),
                    el('select', { id: 'visibility', name: 'visibility', className: 'input-select' }, [
                        el('option', { value: 'true', text: 'Public — visible to everyone' }),
                        el('option', { value: 'false', text: 'Private — hidden from visitors' })
                    ])
                ])
            ]),
            el('div', { className: 'form-row' }, [
                el('div', { className: 'form-group' }, [
                    el('label', { for: 'themeColor', text: 'Accent color' }),
                    el('input', { id: 'themeColor', name: 'themeColor', type: 'color', value: profile.themeColor || '#111111' })
                ]),
                el('div', { className: 'form-group' }, [
                    el('label', { for: 'backgroundColor', text: 'Background color' }),
                    el('input', { id: 'backgroundColor', name: 'backgroundColor', type: 'color', value: profile.backgroundColor || '#fafafa' })
                ])
            ]),

            el('div', { className: 'form-group' }, [
                el('label', { for: 'resumeUpload', text: 'Resume upload' }),
                el('input', { id: 'resumeUpload', type: 'file', accept: '.pdf,.doc,.docx', className: 'file-input' }),
                el('p', { className: 'form-note', text: 'AI resume extraction — coming soon. Upload is stored locally for preview.' })
            ]),
            el('div', { className: 'form-actions' }, [
                el('button', { type: 'submit', className: 'btn', text: 'Save settings' })
            ])
        ])
    ]);
}

function dashboardSections() {
    return el('section', { className: 'panel dash-panel fade-in' }, [
        el('div', { className: 'panel-head' }, [
            el('h2', { text: 'Section layout' }),
            el('p', { text: 'Drag to reorder. Toggle visibility for each section. Changes are saved automatically.' })
        ]),
        el('div', { id: 'sectionList', className: 'section-list' })
    ]);
}

function dashboardContentEditors() {
    return el('section', { className: 'panel dash-panel fade-in' }, [
        el('div', { className: 'panel-head' }, [
            el('h2', { text: 'Content editors' }),
            el('p', { text: 'Manage the content for each section.' })
        ]),
        el('div', { id: 'contentEditorList', className: 'content-editor-list' })
    ]);
}

function dashboardAnalytics(views) {
    const bars = [40, 65, 45, 80, 55, 70, views % 100 || 30].map((h, i) =>
        el('div', { className: 'bar', style: { height: `${Math.min(h, 100)}%` }, attrs: { 'aria-label': `Day ${i + 1}` } })
    );

    return el('section', { className: 'panel dash-panel fade-in' }, [
        el('div', { className: 'panel-head' }, [
            el('h2', { text: 'Analytics' }),
            el('p', { text: 'Profile performance at a glance.' })
        ]),
        el('div', { className: 'analytics-grid' }, [
            el('div', { className: 'analytics-card' }, [
                el('p', { className: 'stat-label', text: 'Total views' }),
                el('p', { className: 'analytics-number', text: String(views) })
            ]),
            el('div', { className: 'analytics-card' }, [
                el('p', { className: 'stat-label', text: 'This month' }),
                el('p', { className: 'analytics-number', text: String(Math.max(0, Math.floor(views * 0.4))) })
            ]),
            el('div', { className: 'analytics-card analytics-chart' }, [
                el('p', { className: 'stat-label', text: 'Daily views (sample)' }),
                el('div', { className: 'bar-chart' }, bars)
            ])
        ])
    ]);
}

function bindDashboardEvents() {
    const profile = dashboardState.profile;
    document.getElementById('visibility').value = profile.visibility === false ? 'false' : 'true';
}

function renderSectionList() {
    const list = document.getElementById('sectionList');
    clear(list);

    dashboardState.sectionOrder.forEach(key => {
        const meta = SECTION_META[key];
        const hidden = dashboardState.hiddenSections.has(key);

        const header = el('div', { className: 'section-header' }, [
            el('span', { className: 'section-grip', text: icon('grip') }),
            el('span', { className: 'section-icon', text: meta.icon }),
            el('span', { className: 'section-label', text: meta.label }),
            el('button', {
                type: 'button',
                className: 'visibility-toggle',
                text: hidden ? 'Hidden' : 'Visible',
                on: { click: (e) => { e.stopPropagation(); toggleSectionVisibility(key); } }
            })
        ]);

        const item = el('div', {
            className: `section-item${hidden ? ' section-item-hidden' : ''}`,
            draggable: true,
            dataset: { key }
        }, [header]);

        item.addEventListener('dragstart', onDragStart);
        item.addEventListener('dragend', onDragEnd);
        item.addEventListener('dragover', onDragOver);
        item.addEventListener('drop', onDrop);
        list.appendChild(item);
    });

    // Also render content editors separately
    const editorList = document.getElementById('contentEditorList');
    if (editorList) {
        clear(editorList);
        dashboardState.sectionOrder.forEach(key => {
            const meta = SECTION_META[key];
            const editorWrapper = el('div', { className: 'content-editor-card' }, [
                el('h3', { text: meta.label }),
                typeof renderSectionEditor === 'function' ? renderSectionEditor(key) : null
            ]);
            editorList.appendChild(editorWrapper);
        });
    }
}

let draggedSection = null;

function onDragStart(e) {
    draggedSection = this;
    this.classList.add('dragging');
    e.dataTransfer.effectAllowed = 'move';
}

function onDragEnd() {
    this.classList.remove('dragging');
    draggedSection = null;
}

function onDragOver(e) {
    e.preventDefault();
    e.dataTransfer.dropEffect = 'move';
}

function onDrop(e) {
    e.preventDefault();
    if (!draggedSection || draggedSection === this) return;

    const list = document.getElementById('sectionList');
    const items = [...list.children];
    const from = items.indexOf(draggedSection);
    const to = items.indexOf(this);

    const order = [...dashboardState.sectionOrder];
    const [moved] = order.splice(from, 1);
    order.splice(to, 0, moved);
    dashboardState.sectionOrder = order;
    renderSectionList();
    saveSectionLayout();
}

function toggleSectionVisibility(key) {
    if (dashboardState.hiddenSections.has(key)) {
        dashboardState.hiddenSections.delete(key);
    } else {
        dashboardState.hiddenSections.add(key);
    }
    renderSectionList();
    saveSectionLayout();
}

async function saveSettings(e) {
    e.preventDefault();
    const form = e.target;

    const body = {
        visibility: form.visibility.value === 'true',
        themeColor: form.themeColor.value,
        backgroundColor: form.backgroundColor.value
    };

    try {
        const updated = await apiCall('/dashboard/customize', 'PUT', body);
        dashboardState.profile = { ...dashboardState.profile, ...updated };
        toast('Settings saved');
        renderDashboardContent();
    } catch {
        toast('Failed to save settings', 'error');
    }
}

async function saveSectionLayout() {
    const body = {
        sectionOrder: dashboardState.sectionOrder.join(','),
        hiddenSections: [...dashboardState.hiddenSections].join(',')
    };

    try {
        const updated = await apiCall('/dashboard/customize', 'PUT', body);
        dashboardState.profile = { ...dashboardState.profile, ...updated };
        toast('Section layout saved');
    } catch {
        toast('Failed to save layout', 'error');
    }
}
