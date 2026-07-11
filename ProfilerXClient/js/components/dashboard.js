let currentProfile = {};

async function renderDashboard(container) {
    container.innerHTML = `
        ${getNavHeader(true)}
        <div class="container mt-3">
            <div class="glass-panel text-center mb-3">
                <h1 id="welcomeText">Dashboard</h1>
                <p>Manage your public portfolio and settings</p>
                <div class="mt-2">
                    <a id="publicUrl" href="#" target="_blank" class="btn" style="background-color: var(--text-main);">View Public Profile</a>
                </div>
            </div>

            <div class="glass-panel mb-3">
                <h2>Profile Settings</h2>
                <form id="settingsForm" onsubmit="saveSettings(event)">
                    <div class="form-group">
                        <label>Visibility (Public?)</label>
                        <select id="visibility" style="padding:0.5rem; width:100%; border-radius:8px;">
                            <option value="true">Public</option>
                            <option value="false">Private</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label>Accent Theme Color</label>
                        <input type="color" id="themeColor" value="#2563eb" style="padding:0; height:40px; cursor:pointer;">
                        <small style="display:block; margin-top:5px;">Used subtly to tint your profile</small>
                    </div>
                    <button type="submit" class="btn">Save Settings</button>
                    <span id="settingsMsg" style="margin-left:1rem; color:green;"></span>
                </form>
            </div>

            <div class="glass-panel">
                <h2>Reorder Sections</h2>
                <p class="mb-2">Drag and drop to reorder how sections appear on your portfolio. Click "Save Order" when done.</p>
                
                <div id="sectionList">
                    <!-- Sections will be rendered here -->
                </div>

                <button class="btn mt-2" onclick="saveSectionOrder()">Save Order</button>
                <span id="orderMsg" style="margin-left:1rem; color:green;"></span>
            </div>
        </div>
    `;

    try {
        const data = await apiCall('/dashboard');
        const developer = data.developer;
        currentProfile = data.profile;

        document.getElementById('welcomeText').innerText = \`Welcome, \${developer.fullName}\`;
        
        const username = localStorage.getItem('username');
        document.getElementById('publicUrl').href = \`#/artifact/\${username}\`;

        document.getElementById('visibility').value = currentProfile.visibility;
        document.getElementById('themeColor').value = currentProfile.themeColor || '#2563eb';

        const order = currentProfile.sectionOrder || 'about,links,skills,experience,projects,certifications,currentlyDoing,blogs';
        renderSections(order.split(','));

    } catch (err) {
        console.error(err);
        if(err.message.includes('JWT') || err.message.includes('authenticate')) {
            logout();
        }
    }
}

function renderSections(orderArray) {
    const list = document.getElementById('sectionList');
    list.innerHTML = '';

    const labels = {
        'about': 'About',
        'links': 'Links',
        'skills': 'Skills',
        'experience': 'Experience',
        'projects': 'Projects',
        'certifications': 'Certifications',
        'currentlyDoing': 'Currently Doing',
        'blogs': 'Blogs'
    };

    orderArray.forEach(key => {
        if(!labels[key]) return; // ignore unknown
        const div = document.createElement('div');
        div.className = 'draggable-item';
        div.draggable = true;
        div.dataset.key = key;
        div.innerHTML = \`<span>\${labels[key]}</span> <span>☰</span>\`;
        
        div.addEventListener('dragstart', handleDragStart);
        div.addEventListener('dragover', handleDragOver);
        div.addEventListener('drop', handleDrop);
        div.addEventListener('dragenter', handleDragEnter);
        
        list.appendChild(div);
    });
}

let draggedItem = null;

function handleDragStart(e) {
    draggedItem = this;
    e.dataTransfer.effectAllowed = 'move';
    this.classList.add('dragging');
}

function handleDragOver(e) {
    e.preventDefault();
    e.dataTransfer.dropEffect = 'move';
    return false;
}

function handleDragEnter(e) {
    e.preventDefault();
}

function handleDrop(e) {
    e.stopPropagation();
    if (draggedItem !== this) {
        const list = document.getElementById('sectionList');
        const items = Array.from(list.children);
        const draggedIndex = items.indexOf(draggedItem);
        const droppedIndex = items.indexOf(this);
        
        if (draggedIndex < droppedIndex) {
            this.parentNode.insertBefore(draggedItem, this.nextSibling);
        } else {
            this.parentNode.insertBefore(draggedItem, this);
        }
    }
    draggedItem.classList.remove('dragging');
    return false;
}

async function saveSettings(e) {
    e.preventDefault();
    const body = {
        visibility: document.getElementById('visibility').value === 'true',
        themeColor: document.getElementById('themeColor').value
    };

    try {
        await apiCall('/dashboard/customize', 'PUT', body);
        const msg = document.getElementById('settingsMsg');
        msg.innerText = 'Settings saved!';
        setTimeout(() => msg.innerText = '', 3000);
    } catch (err) {
        alert('Failed to save settings');
    }
}

async function saveSectionOrder() {
    const list = document.getElementById('sectionList');
    const items = Array.from(list.children);
    const order = items.map(el => el.dataset.key).join(',');

    try {
        await apiCall('/dashboard/customize', 'PUT', { sectionOrder: order });
        const msg = document.getElementById('orderMsg');
        msg.innerText = 'Order saved!';
        setTimeout(() => msg.innerText = '', 3000);
    } catch (err) {
        alert('Failed to save order');
    }
}
