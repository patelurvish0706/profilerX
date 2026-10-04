/**
 * Lightweight DOM component utilities for ProfilerX.
 */

function el(tag, props = {}, ...children) {
    const node = document.createElement(tag);

    if (props.className) node.className = props.className;
    if (props.id) node.id = props.id;
    if (props.text) node.textContent = props.text;
    if (props.html) node.innerHTML = props.html;
    if (props.href) node.href = props.href;
    if (props.type) node.type = props.type;
    if (props.placeholder) node.placeholder = props.placeholder;
    if (props.value != null) node.value = props.value;
    if (props.required) node.required = true;
    if (props.minLength) node.minLength = props.minLength;
    if (props.maxLength) node.maxLength = props.maxLength;
    if (props.draggable) node.draggable = true;
    if (props.target) node.target = props.target;
    if (props.for) node.htmlFor = props.for;
    if (props.name) node.name = props.name;
    if (props.accept) node.accept = props.accept;
    if (props.rows) node.rows = props.rows;
    if (props.checked != null) node.checked = props.checked;

    if (props.dataset) {
        Object.entries(props.dataset).forEach(([key, value]) => {
            node.dataset[key] = value;
        });
    }

    if (props.attrs) {
        Object.entries(props.attrs).forEach(([key, value]) => {
            node.setAttribute(key, value);
        });
    }

    if (props.style) Object.assign(node.style, props.style);

    if (props.on) {
        Object.entries(props.on).forEach(([event, handler]) => {
            node.addEventListener(event, handler);
        });
    }

    children.flat().forEach(child => {
        if (child == null) return;
        node.appendChild(typeof child === 'string' ? document.createTextNode(child) : child);
    });

    return node;
}

function clear(node) {
    while (node.firstChild) node.removeChild(node.firstChild);
}

function mount(container, ...nodes) {
    clear(container);
    nodes.flat().forEach(node => container.appendChild(node));
}

function icon(name) {
    const icons = {
        grip: '⠿',
        eye: '◉',
        eyeOff: '○',
        arrow: '→',
        check: '✓',
        link: '↗',
        github: '⌘',
        views: '◎',
        lock: '◆',
        globe: '◯'
    };
    return icons[name] || '•';
}

const SECTION_META = {
    about: { label: 'About', icon: '◈' },
    links: { label: 'Links', icon: '↗' },
    skills: { label: 'Skills', icon: '◆' },
    experience: { label: 'Experience', icon: '▤' },
    projects: { label: 'Projects', icon: '▣' },
    certifications: { label: 'Certifications', icon: '◉' },
    currentlyDoing: { label: 'Currently Doing', icon: '◐' },
    blogs: { label: 'Blog', icon: '▦' }
};

const DEFAULT_SECTION_ORDER = Object.keys(SECTION_META).join(',');

function parseSectionOrder(order) {
    const keys = (order || DEFAULT_SECTION_ORDER).split(',').map(s => s.trim()).filter(Boolean);
    const seen = new Set();
    return keys.filter(key => {
        if (!SECTION_META[key] || seen.has(key)) return false;
        seen.add(key);
        return true;
    });
}

function parseHiddenSections(value) {
    if (!value) return new Set();
    return new Set(value.split(',').map(s => s.trim()).filter(Boolean));
}

function formatDate(value) {
    if (!value) return '';
    const date = new Date(value);
    if (Number.isNaN(date.getTime())) return value;
    return date.toLocaleDateString(undefined, { month: 'short', year: 'numeric' });
}

function escapeHtml(text) {
    const div = document.createElement('div');
    div.textContent = text ?? '';
    return div.innerHTML;
}

function toast(message, type = 'success') {
    let host = document.getElementById('toast-host');
    if (!host) {
        host = el('div', { id: 'toast-host', className: 'toast-host' });
        document.body.appendChild(host);
    }

    const item = el('div', { className: `toast toast-${type}`, text: message });
    host.appendChild(item);
    requestAnimationFrame(() => item.classList.add('show'));
    setTimeout(() => {
        item.classList.remove('show');
        setTimeout(() => item.remove(), 300);
    }, 2800);
}
