function renderSectionEditor(key) {
    const editor = el('div', { className: 'section-editor fade-in' });

    if (key === 'about') {
        const profile = dashboardState.profile;
        mount(editor, 
            el('form', { className: 'settings-form', on: { submit: async (e) => {
                e.preventDefault();
                try {
                    const updated = await apiCall('/dashboard/customize', 'PUT', { about: e.target.about.value.trim() });
                    dashboardState.profile = { ...dashboardState.profile, ...updated };
                    toast('About updated');
                } catch { toast('Failed to update About', 'error'); }
            } } }, [
                el('div', { className: 'form-group' }, [
                    el('textarea', { name: 'about', rows: 4, text: profile.about || '', placeholder: 'Write a short professional introduction…' })
                ]),
                el('button', { type: 'submit', className: 'btn', text: 'Save About' })
            ])
        );
    } else if (key === 'skills') {
        renderListEditor(editor, 'skills', dashboardState.skills, 'skillName', 'Skill name (e.g. JavaScript)');
    } else if (key === 'links') {
        renderListEditor(editor, 'links', dashboardState.links, 'url', 'URL (e.g. https://github.com/you)', true);
    } else if (key === 'experience') {
        renderComplexEditor(editor, 'experience', dashboardState.experience, ['companyName', 'role', 'description']);
    } else if (key === 'projects') {
        renderComplexEditor(editor, 'projects', dashboardState.projects, ['projectName', 'description', 'liveLink', 'github']);
    } else if (key === 'certifications') {
        renderComplexEditor(editor, 'certifications', dashboardState.certifications, ['title', 'issuer', 'credentialLink']);
    } else if (key === 'currentlyDoing') {
        renderComplexEditor(editor, 'currentlyDoing', dashboardState.currentlyDoing, ['title', 'description']);
    } else if (key === 'blogs') {
        renderComplexEditor(editor, 'blogs', dashboardState.blogs, ['title', 'content']);
    } else {
        mount(editor, el('p', { text: `Editor for ${key} is not implemented yet.`, className: 'form-note' }));
    }

    return editor;
}

function renderListEditor(container, type, items, fieldName, placeholder, hasLabel = false) {
    const listContainer = el('ul', { className: 'editor-item-list' });
    
    function refreshList() {
        clear(listContainer);
        items.forEach(item => {
            const li = el('li', { className: 'editor-list-item' }, [
                el('span', { text: item[fieldName] || item.label || 'Item' }),
                el('button', { type: 'button', className: 'btn-delete', text: 'Delete', on: { click: () => deleteItem(type, item.id) } })
            ]);
            mount(listContainer, li);
        });
    }

    const formFields = [
        el('input', { name: fieldName, required: true, placeholder })
    ];
    if (hasLabel) {
        formFields.unshift(el('input', { name: 'label', required: true, placeholder: 'Label (e.g. GitHub)' }));
    }

    const form = el('form', { className: 'editor-add-form', on: { submit: (e) => addItem(e, type) } }, [
        ...formFields,
        el('button', { type: 'submit', className: 'btn btn-secondary', text: 'Add' })
    ]);

    mount(container, listContainer, form);
    refreshList();
}

function renderComplexEditor(container, type, items, fields) {
    const listContainer = el('ul', { className: 'editor-item-list' });
    
    function refreshList() {
        clear(listContainer);
        items.forEach(item => {
            const displayTitle = item[fields[0]] || 'Item';
            const li = el('li', { className: 'editor-list-item' }, [
                el('span', { text: displayTitle }),
                el('button', { type: 'button', className: 'btn-delete', text: 'Delete', on: { click: () => deleteItem(type, item.id) } })
            ]);
            mount(listContainer, li);
        });
    }

    const formInputs = fields.map(f => {
        if (f === 'description' || f === 'content') {
            return el('textarea', { name: f, placeholder: f, rows: 3, required: f === fields[0] || f === 'content' });
        }
        return el('input', { name: f, placeholder: f, required: f === fields[0] });
    });

    const form = el('form', { className: 'editor-add-form complex-form', on: { submit: (e) => addItem(e, type) } }, [
        ...formInputs,
        el('button', { type: 'submit', className: 'btn btn-secondary', text: 'Add' })
    ]);

    mount(container, listContainer, form);
    refreshList();
}

async function addItem(e, type) {
    e.preventDefault();
    const formData = new FormData(e.target);
    const body = Object.fromEntries(formData.entries());
    
    // Map type to endpoint
    const endpointMap = {
        'skills': 'skill',
        'links': 'link',
        'experience': 'experience',
        'projects': 'project',
        'certifications': 'certification',
        'currentlyDoing': 'currentlyDoing',
        'blogs': 'blog'
    };

    try {
        const added = await apiCall(`/dashboard/sections/${endpointMap[type]}`, 'POST', body);
        dashboardState[type].push(added);
        toast(`Added to ${type}`);
        renderSectionList();
    } catch {
        toast('Failed to add item', 'error');
    }
}

async function deleteItem(type, id) {
    if (!confirm('Are you sure you want to delete this?')) return;
    const endpointMap = {
        'skills': 'skill',
        'links': 'link',
        'experience': 'experience',
        'projects': 'project',
        'certifications': 'certification',
        'currentlyDoing': 'currentlyDoing',
        'blogs': 'blog'
    };
    try {
        await apiCall(`/dashboard/sections/${endpointMap[type]}/${id}`, 'DELETE');
        dashboardState[type] = dashboardState[type].filter(item => item.id !== id);
        toast('Deleted');
        renderSectionList();
    } catch {
        toast('Failed to delete', 'error');
    }
}
