async function renderPortfolio(container, username) {
    container.innerHTML = '<div class="container text-center mt-3">Loading profile...</div>';
    
    try {
        const data = await apiCall(\`/\${username}\`, 'GET');
        
        const profile = data.profile;
        const dev = data.developer;
        
        // Minimal Premium Design: white background, subtle tint from themeColor
        const themeColor = profile.themeColor || '#2563eb';
        
        // Render
        let html = \`
            <div style="background-color: white; min-height: 100vh;">
                <!-- Header -->
                <div style="background: linear-gradient(135deg, rgba(255,255,255,1) 0%, \${themeColor}15 100%); padding: 4rem 2rem; text-align: center; border-bottom: 1px solid var(--border);">
                    <h1 style="font-size: 3rem; margin-bottom: 0.5rem; color: var(--text-main);">\${dev.fullName}</h1>
                    <p style="font-size: 1.2rem; color: var(--text-muted);">\${dev.email} | \${dev.phone}</p>
                </div>
                
                <div class="container" style="max-width: 800px; padding: 3rem 1rem;">
        \`;

        // Render sections based on order
        const order = profile.sectionOrder || 'about,links,skills,experience,projects,certifications,currentlyDoing,blogs';
        const sections = order.split(',');

        sections.forEach(sec => {
            html += renderSection(sec, data);
        });

        html += \`
                </div>
            </div>
        \`;

        container.innerHTML = html;

    } catch (err) {
        container.innerHTML = \`
            <div class="container text-center mt-3">
                <h1>Profile Unavailable</h1>
                <p>\${err.message || 'This profile is either private or does not exist.'}</p>
                <a href="#/" style="color: var(--accent); margin-top: 2rem; display: inline-block;">Go Home</a>
            </div>
        \`;
    }
}

function renderSection(sectionKey, data) {
    let content = '';
    
    if (sectionKey === 'about' && data.profile.about) {
        content = \`<p>\${data.profile.about}</p>\`;
    } 
    else if (sectionKey === 'skills' && data.skills && data.skills.length > 0) {
        content = '<div style="display:flex; flex-wrap:wrap; gap:0.5rem;">';
        data.skills.forEach(s => {
            content += \`<span style="padding: 0.4rem 0.8rem; background: var(--bg-color); border: 1px solid var(--border); border-radius: 20px; font-size: 0.9rem;">\${s.skillName}</span>\`;
        });
        content += '</div>';
    }
    else if (sectionKey === 'projects' && data.projects && data.projects.length > 0) {
        data.projects.forEach(p => {
            content += \`
                <div style="border-left: 3px solid var(--accent); padding-left: 1rem; margin-bottom: 1.5rem;">
                    <h3>\${p.projectName}</h3>
                    <p style="font-size:0.9rem; margin-bottom:0.5rem;">\${p.status}</p>
                    <p>\${p.description || ''}</p>
                </div>
            \`;
        });
    }
    else if (sectionKey === 'experience' && data.experience && data.experience.length > 0) {
        data.experience.forEach(e => {
            content += \`
                <div style="margin-bottom: 1.5rem;">
                    <h3>\${e.role} @ \${e.companyName}</h3>
                    <p style="font-size:0.9rem; margin-bottom:0.5rem;">\${e.startAt || 'N/A'} - \${e.endAt || 'Present'}</p>
                    <p>\${e.description || ''}</p>
                </div>
            \`;
        });
    }

    if (!content) return ''; // don't render empty sections

    const titles = {
        'about': 'About Me',
        'links': 'Links',
        'skills': 'Skills & Expertise',
        'experience': 'Experience',
        'projects': 'Projects',
        'certifications': 'Certifications',
        'currentlyDoing': 'Currently Doing',
        'blogs': 'Blog'
    };

    return \`
        <div class="portfolio-section glass-panel" style="margin-bottom: 2rem; border-left: 4px solid \${data.profile.themeColor || 'var(--accent)'}; border-radius: 8px 16px 16px 8px;">
            <h2 style="margin-bottom: 1.5rem; font-size: 1.5rem;">\${titles[sectionKey]}</h2>
            \${content}
        </div>
    \`;
}
