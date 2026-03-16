const API_BASE = (() => {
      const host = location.hostname && location.hostname !== '' ? location.hostname : 'localhost';
      return `${location.protocol === 'file:' ? 'http:' : location.protocol}//${host}:8081`;
    })();

    const TOKEN_KEY = 'logitrack_token';
    const USER_KEY = 'logitrack_user';

    const state = {
      token: localStorage.getItem(TOKEN_KEY) || '',
      username: localStorage.getItem(USER_KEY) || '',
      currentSection: 'dashboard',
      data: {
        usuarios: [],
        bodegas: [],
        productos: [],
        movimientos: [],
        detalles: [],
        auditorias: []
      }
    };

    const sectionConfig = {
      dashboard: {
        label: 'Resumen general',
        description: 'Vista principal del sistema LogiTrack.'
      },
      usuarios: {
        label: 'Usuarios',
        description: 'Gestión de usuarios. Desde este frontend solo se crea o actualiza con rol EMPLEADO.',
        path: '/api/usuario',
        key: 'usuarios',
        createLabel: 'Crear empleado',
        note: 'Este frontend fuerza el rol del usuario a EMPLEADO para respetar tu requisito.',
        fields: [
          { name: 'nombre', label: 'Nombre', type: 'text', required: true },
          { name: 'documento', label: 'Documento', type: 'text', required: true },
          { name: 'username', label: 'Username', type: 'text', required: true },
          { name: 'password', label: 'Contraseña', type: 'password', required: true }
        ],
        buildPayload: values => ({ ...values, rol: 'EMPLEADO' }),
        columns: [
          { header: 'ID', render: r => r.id ?? '-' },
          { header: 'Nombre', render: r => r.nombre ?? '-' },
          { header: 'Documento', render: r => r.documento ?? '-' },
          { header: 'Username', render: r => r.username ?? '-' },
          { header: 'Rol', render: r => badge((r.rol || 'EMPLEADO').toLowerCase(), r.rol || 'EMPLEADO') }
        ]
      },
      bodegas: {
        label: 'Bodegas',
        description: 'Registro y control de bodegas distribuidas en distintas ubicaciones.',
        path: '/api/bodega',
        key: 'bodegas',
        createLabel: 'Nueva bodega',
        fields: [
          { name: 'nombre', label: 'Nombre', type: 'text', required: true },
          { name: 'ubicacion', label: 'Ubicación', type: 'text', required: true },
          { name: 'capacidad', label: 'Capacidad', type: 'number', required: true, min: 1 },
          { name: 'usuarioId', label: 'Encargado', type: 'select', required: true, options: 'usuariosEmpleados' }
        ],
        buildPayload: values => ({ ...values, capacidad: Number(values.capacidad), usuarioId: Number(values.usuarioId) }),
        columns: [
          { header: 'ID', render: r => r.id ?? '-' },
          { header: 'Nombre', render: r => r.nombre ?? '-' },
          { header: 'Ubicación', render: r => r.ubicacion ?? '-' },
          { header: 'Capacidad', render: r => r.capacidad ?? '-' },
          { header: 'Encargado', render: r => nested(r, 'usuario.nombre') || '-' }
        ]
      },
      productos: {
        label: 'Productos',
        description: 'Catálogo general de productos y su relación con las bodegas.',
        path: '/api/producto',
        key: 'productos',
        createLabel: 'Nuevo producto',
        fields: [
          { name: 'nombre', label: 'Nombre', type: 'text', required: true },
          { name: 'categoria', label: 'Categoría', type: 'text', required: true },
          { name: 'precio', label: 'Precio', type: 'number', step: '0.01', required: true, min: 0 },
          { name: 'stock', label: 'Stock', type: 'number', required: true, min: 0 },
          { name: 'bodegaId', label: 'Bodega', type: 'select', required: true, options: 'bodegas' }
        ],
        buildPayload: values => ({ ...values, precio: Number(values.precio), stock: Number(values.stock), bodegaId: Number(values.bodegaId) }),
        columns: [
          { header: 'ID', render: r => r.id ?? '-' },
          { header: 'Nombre', render: r => r.nombre ?? '-' },
          { header: 'Categoría', render: r => r.categoria ?? '-' },
          { header: 'Precio', render: r => money(r.precio) },
          { header: 'Stock', render: r => r.stock ?? '-' },
          { header: 'Bodega', render: r => nested(r, 'bodega.nombre') || '-' }
        ]
      },
      movimientos: {
        label: 'Movimientos',
        description: 'Entradas, salidas y transferencias registradas en el backend.',
        path: '/api/movimiento',
        key: 'movimientos',
        createLabel: 'Nuevo movimiento',
        note: 'Tu backend actual exige usuario, bodega origen y bodega destino en el DTO. Por eso este formulario pide ambos IDs siempre.',
        fields: [
          { name: 'fecha', label: 'Fecha y hora', type: 'datetime-local', required: true },
          { name: 'tipoMovimiento', label: 'Tipo de movimiento', type: 'select', required: true, options: [
            { value: 'ENTRADA', label: 'ENTRADA' },
            { value: 'SALIDA', label: 'SALIDA' },
            { value: 'TRANSFERENCIA', label: 'TRANSFERENCIA' }
          ]},
          { name: 'usuarioId', label: 'Usuario responsable', type: 'select', required: true, options: 'usuarios' },
          { name: 'bodegaOrigenId', label: 'Bodega origen', type: 'select', required: true, options: 'bodegas' },
          { name: 'bodegaDestinoId', label: 'Bodega destino', type: 'select', required: true, options: 'bodegas' }
        ],
        buildPayload: values => ({
          ...values,
          fecha: toIsoDate(values.fecha),
          usuarioId: Number(values.usuarioId),
          bodegaOrigenId: Number(values.bodegaOrigenId),
          bodegaDestinoId: Number(values.bodegaDestinoId)
        }),
        mapInitial: row => ({
          fecha: toDatetimeLocal(row.fecha),
          tipoMovimiento: row.tipoMovimiento,
          usuarioId: nested(row, 'usuario.id'),
          bodegaOrigenId: nested(row, 'bodegaOrigen.id'),
          bodegaDestinoId: nested(row, 'bodegaDestino.id')
        }),
        columns: [
          { header: 'ID', render: r => r.id ?? '-' },
          { header: 'Fecha', render: r => formatDate(r.fecha) },
          { header: 'Tipo', render: r => badge((r.tipoMovimiento || '').toLowerCase(), r.tipoMovimiento || '-') },
          { header: 'Usuario', render: r => nested(r, 'usuario.nombre') || '-' },
          { header: 'Origen', render: r => nested(r, 'bodegaOrigen.nombre') || '-' },
          { header: 'Destino', render: r => nested(r, 'bodegaDestino.nombre') || '-' }
        ]
      },
      detalles: {
        label: 'Detalle de movimientos',
        description: 'Productos asociados a cada movimiento y sus cantidades.',
        path: '/api/detalleMovimiento',
        key: 'detalles',
        createLabel: 'Nuevo detalle',
        fields: [
          { name: 'cantidad', label: 'Cantidad', type: 'number', required: true, min: 1 },
          { name: 'movimientoId', label: 'Movimiento', type: 'select', required: true, options: 'movimientos' },
          { name: 'productoId', label: 'Producto', type: 'select', required: true, options: 'productos' }
        ],
        buildPayload: values => ({ ...values, cantidad: Number(values.cantidad), movimientoId: Number(values.movimientoId), productoId: Number(values.productoId) }),
        columns: [
          { header: 'ID', render: r => r.id ?? '-' },
          { header: 'Cantidad', render: r => r.cantidad ?? '-' },
          { header: 'Movimiento', render: r => nested(r, 'movimiento.id') || '-' },
          { header: 'Tipo mov.', render: r => nested(r, 'movimiento.tipoMovimiento') || '-' },
          { header: 'Producto', render: r => nested(r, 'producto.nombre') || '-' }
        ]
      },
      auditorias: {
        label: 'Auditorías',
        description: 'Registro de operaciones realizadas sobre las entidades del sistema.',
        path: '/api/auditoria',
        key: 'auditorias',
        createLabel: 'Nueva auditoría',
        fields: [
          { name: 'entidad', label: 'Entidad afectada', type: 'text', required: true },
          { name: 'operacion', label: 'Operación', type: 'select', required: true, options: [
            { value: 'INSERT', label: 'INSERT' },
            { value: 'UPDATE', label: 'UPDATE' },
            { value: 'DELETE', label: 'DELETE' }
          ]},
          { name: 'fecha', label: 'Fecha y hora', type: 'datetime-local', required: true },
          { name: 'idUsuario', label: 'Usuario', type: 'select', required: true, options: 'usuarios' }
        ],
        buildPayload: values => ({ ...values, fecha: toIsoDate(values.fecha), idUsuario: Number(values.idUsuario) }),
        mapInitial: row => ({
          entidad: row.entidad,
          operacion: row.operacion,
          fecha: toDatetimeLocal(row.fecha),
          idUsuario: nested(row, 'usuario.id')
        }),
        columns: [
          { header: 'ID', render: r => r.id ?? '-' },
          { header: 'Entidad', render: r => r.entidad ?? '-' },
          { header: 'Operación', render: r => badge((r.operacion || '').toLowerCase(), r.operacion || '-') },
          { header: 'Fecha', render: r => formatDate(r.fecha) },
          { header: 'Usuario', render: r => nested(r, 'usuario.nombre') || '-' }
        ]
      }
    };

    const loginView = document.getElementById('loginView');
    const appView = document.getElementById('appView');
    const navList = document.getElementById('navList');
    const pageTitle = document.getElementById('pageTitle');
    const pageSubtitle = document.getElementById('pageSubtitle');
    const metricsGrid = document.getElementById('metricsGrid');
    const overviewBox = document.getElementById('overviewBox');
    const dashboardSection = document.getElementById('dashboardSection');
    const dataSection = document.getElementById('dataSection');
    const sectionTitle = document.getElementById('sectionTitle');
    const sectionDescription = document.getElementById('sectionDescription');
    const tableContainer = document.getElementById('tableContainer');
    const searchInput = document.getElementById('searchInput');
    const addBtn = document.getElementById('addBtn');
    const clearSearchBtn = document.getElementById('clearSearchBtn');
    const refreshBtn = document.getElementById('refreshBtn');
    const logoutBtn = document.getElementById('logoutBtn');
    const sessionUser = document.getElementById('sessionUser');
    const sessionInfo = document.getElementById('sessionInfo');
    const connectionChip = document.getElementById('connectionChip');
    const toastRoot = document.getElementById('toastRoot');
    const modalRoot = document.getElementById('modalRoot');

    document.getElementById('loginForm').addEventListener('submit', handleLogin);
    refreshBtn.addEventListener('click', () => loadAllData(true));
    logoutBtn.addEventListener('click', logout);
    addBtn.addEventListener('click', () => openCreateModal(state.currentSection));
    searchInput.addEventListener('input', renderCurrentSection);
    clearSearchBtn.addEventListener('click', () => {
      searchInput.value = '';
      renderCurrentSection();
    });

    buildNavigation();
    boot();

    function boot() {
      connectionChip.textContent = `Backend: ${API_BASE}`;
      if (state.token) {
        showApp();
        loadAllData();
      } else {
        showLogin();
      }
    }

    function buildNavigation() {
      const items = [
        { key: 'dashboard', label: 'Resumen general' },
        { key: 'usuarios', label: 'Usuarios' },
        { key: 'bodegas', label: 'Bodegas' },
        { key: 'productos', label: 'Productos' },
        { key: 'movimientos', label: 'Movimientos' },
        { key: 'detalles', label: 'Detalle movimiento' },
        { key: 'auditorias', label: 'Auditorías' }
      ];

      navList.innerHTML = items.map(item => `
        <button class="nav-btn ${item.key === state.currentSection ? 'active' : ''}" data-section="${item.key}" type="button">
          ${item.label}
        </button>
      `).join('');

      navList.querySelectorAll('.nav-btn').forEach(btn => {
        btn.addEventListener('click', () => setSection(btn.dataset.section));
      });
    }

    async function handleLogin(event) {
      event.preventDefault();
      const form = event.currentTarget;
      const username = form.username.value.trim();
      const password = form.password.value.trim();

      try {
        const response = await fetch(`${API_BASE}/auth/login`, {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({ username, password })
        });

        const data = await parseResponse(response);
        if (!response.ok) {
          throw new Error(extractError(data) || 'No fue posible iniciar sesión.');
        }

        state.token = data.token;
        state.username = username;
        localStorage.setItem(TOKEN_KEY, data.token);
        localStorage.setItem(USER_KEY, username);
        showToast('Inicio de sesión correcto.', 'success');
        showApp();
        await loadAllData();
      } catch (error) {
        showToast(error.message || 'Error al iniciar sesión.', 'error');
      }
    }

    function showLogin() {
      loginView.classList.remove('hidden');
      appView.classList.add('hidden');
    }

    function showApp() {
      loginView.classList.add('hidden');
      appView.classList.remove('hidden');
      sessionUser.textContent = `Sesión: ${state.username || 'admin'}`;
      sessionInfo.textContent = 'JWT activo para consumir las rutas seguras del backend LogiTrack.';
    }

    function logout() {
      state.token = '';
      state.username = '';
      localStorage.removeItem(TOKEN_KEY);
      localStorage.removeItem(USER_KEY);
      showLogin();
      showToast('Sesión cerrada.', 'success');
    }

    function setSection(section) {
      state.currentSection = section;
      buildNavigation();
      const config = sectionConfig[section];
      pageTitle.textContent = config.label;
      pageSubtitle.textContent = config.description;

      if (section === 'dashboard') {
        dashboardSection.classList.remove('hidden');
        dataSection.classList.add('hidden');
        renderDashboard();
        return;
      }

      dashboardSection.classList.add('hidden');
      dataSection.classList.remove('hidden');
      sectionTitle.textContent = config.label;
      sectionDescription.textContent = config.description;
      addBtn.textContent = config.createLabel || 'Nuevo registro';
      renderCurrentSection();
    }

    async function loadAllData(showReloadToast = false) {
      try {
        const [usuarios, bodegas, productos, movimientos, detalles, auditorias] = await Promise.all([
          api('/api/usuario'),
          api('/api/bodega'),
          api('/api/producto'),
          api('/api/movimiento'),
          api('/api/detalleMovimiento'),
          api('/api/auditoria')
        ]);

        state.data.usuarios = ensureArray(usuarios);
        state.data.bodegas = ensureArray(bodegas);
        state.data.productos = ensureArray(productos);
        state.data.movimientos = ensureArray(movimientos);
        state.data.detalles = ensureArray(detalles);
        state.data.auditorias = ensureArray(auditorias);

        renderDashboard();
        renderCurrentSection();
        if (showReloadToast) showToast('Datos actualizados.', 'success');
      } catch (error) {
        showToast(error.message || 'No se pudieron cargar los datos.', 'error');
      }
    }

    async function api(path, options = {}) {
      const headers = { 'Content-Type': 'application/json', ...(options.headers || {}) };
      if (state.token) headers.Authorization = `Bearer ${state.token}`;

      const response = await fetch(`${API_BASE}${path}`, { ...options, headers });
      const data = await parseResponse(response);

      if (response.status === 401 || response.status === 403) {
        logout();
        throw new Error('Tu sesión expiró o el token no es válido.');
      }

      if (!response.ok) {
        throw new Error(extractError(data) || `Error ${response.status}`);
      }

      return data;
    }

    function renderDashboard() {
      const metrics = [
        { label: 'Usuarios', value: state.data.usuarios.length, mini: 'Personal registrado' },
        { label: 'Bodegas', value: state.data.bodegas.length, mini: 'Centros de almacenamiento' },
        { label: 'Productos', value: state.data.productos.length, mini: `${countLowStock()} con stock bajo` },
        { label: 'Movimientos', value: state.data.movimientos.length, mini: `${state.data.detalles.length} detalles asociados` }
      ];

      metricsGrid.innerHTML = metrics.map(item => `
        <article class="metric-card panel">
          <div class="label">${item.label}</div>
          <div class="value">${item.value}</div>
          <div class="mini">${item.mini}</div>
        </article>
      `).join('');

      const topProducts = [...state.data.productos]
        .sort((a, b) => (a.stock ?? 0) - (b.stock ?? 0))
        .slice(0, 4);

      const topMoves = [...state.data.movimientos]
        .slice(0, 4)
        .map(m => `${badge((m.tipoMovimiento || '').toLowerCase(), m.tipoMovimiento || '-')}
          <div style="margin-top:8px;font-weight:700;">#${m.id} · ${formatDate(m.fecha)}</div>
          <div class="muted">${nested(m, 'bodegaOrigen.nombre') || '-'} → ${nested(m, 'bodegaDestino.nombre') || '-'}</div>`);

      overviewBox.innerHTML = `
        <div style="display:grid;grid-template-columns:repeat(auto-fit,minmax(260px,1fr));gap:16px;">
          <div class="panel" style="padding:18px;">
            <h3 style="margin-top:0;">Productos con menor stock</h3>
            ${topProducts.length ? topProducts.map(p => `
              <div style="padding:12px 0;border-bottom:1px solid rgba(255,255,255,.06);">
                <strong>${p.nombre}</strong>
                <div class="muted">Stock: ${p.stock ?? 0} · Bodega: ${nested(p, 'bodega.nombre') || '-'}</div>
              </div>
            `).join('') : '<div class="muted">No hay productos registrados.</div>'}
          </div>
          <div class="panel" style="padding:18px;">
            <h3 style="margin-top:0;">Últimos movimientos cargados</h3>
            ${topMoves.length ? topMoves.map(item => `<div style="padding:12px 0;border-bottom:1px solid rgba(255,255,255,.06);">${item}</div>`).join('') : '<div class="muted">No hay movimientos registrados.</div>'}
          </div>
          <div class="panel" style="padding:18px;">
            <h3 style="margin-top:0;">Lectura rápida del backend</h3>
            <div class="muted" style="line-height:1.7;">
              <div>• Login consumiendo <strong>/auth/login</strong></div>
              <div>• CRUDs conectados a rutas seguras con JWT</div>
              <div>• Usuarios del front limitados a <strong>EMPLEADO</strong></div>
              <div>• Front adaptado al nombre y contexto real de <strong>LogiTrack S.A.</strong></div>
            </div>
          </div>
        </div>
      `;
    }

    function renderCurrentSection() {
      if (state.currentSection === 'dashboard') return;
      const config = sectionConfig[state.currentSection];
      if (!config) return;
      const rawRows = [...(state.data[config.key] || [])];
      const term = searchInput.value.trim().toLowerCase();
      const rows = term
        ? rawRows.filter(row => JSON.stringify(row).toLowerCase().includes(term))
        : rawRows;
      renderTable(config, rows);
    }

    function renderTable(config, rows) {
      if (!rows.length) {
        tableContainer.innerHTML = `<div class="empty-state">No hay registros para mostrar en este módulo.</div>`;
        return;
      }

      const headers = config.columns.map(col => `<th>${col.header}</th>`).join('') + '<th>Acciones</th>';
      const body = rows.map(row => `
        <tr>
          ${config.columns.map(col => `<td>${col.render(row)}</td>`).join('')}
          <td>
            <div class="table-actions">
              <button class="action-btn edit-btn" type="button" data-edit="${config.key}" data-id="${row.id}">Editar</button>
              <button class="action-btn delete-btn" type="button" data-delete="${config.key}" data-id="${row.id}">Eliminar</button>
            </div>
          </td>
        </tr>
      `).join('');

      tableContainer.innerHTML = `
        <div class="table-wrap">
          <table>
            <thead><tr>${headers}</tr></thead>
            <tbody>${body}</tbody>
          </table>
        </div>
      `;

      tableContainer.querySelectorAll('[data-edit]').forEach(btn => {
        btn.addEventListener('click', () => openEditModal(state.currentSection, Number(btn.dataset.id)));
      });
      tableContainer.querySelectorAll('[data-delete]').forEach(btn => {
        btn.addEventListener('click', () => removeRecord(state.currentSection, Number(btn.dataset.id)));
      });
    }

    function openCreateModal(section) {
      const config = sectionConfig[section];
      if (!config || !config.path) return;
      openFormModal({
        title: config.createLabel || 'Nuevo registro',
        description: config.description,
        section,
        mode: 'create',
        initialValues: {}
      });
    }

    function openEditModal(section, id) {
      const config = sectionConfig[section];
      const row = (state.data[config.key] || []).find(item => Number(item.id) === Number(id));
      if (!row) return;

      const initialValues = config.mapInitial ? config.mapInitial(row) : Object.fromEntries(config.fields.map(field => {
        if (field.name.endsWith('Id')) {
          const baseName = field.name.replace(/Id$/, '');
          return [field.name, nested(row, `${baseName}.id`) ?? row[field.name] ?? ''];
        }
        return [field.name, row[field.name] ?? ''];
      }));

      openFormModal({
        title: `Editar ${config.label.slice(0, -1) || config.label}`,
        description: `Actualiza la información del registro #${id}.`,
        section,
        mode: 'edit',
        id,
        initialValues
      });
    }

    function openFormModal({ title, description, section, mode, id, initialValues }) {
      const config = sectionConfig[section];
      const fields = config.fields || [];

      modalRoot.innerHTML = `
        <div class="modal">
          <div class="modal-card">
            <div class="modal-head">
              <div>
                <h3>${title}</h3>
                <p>${description}</p>
              </div>
              <button class="btn btn-secondary" type="button" id="closeModalBtn">Cerrar</button>
            </div>
            <form id="dynamicForm">
              <div class="form-grid">
                ${fields.map(field => renderField(field, initialValues[field.name] ?? '')).join('')}
              </div>
              ${config.note ? `<div class="form-note">${config.note}</div>` : ''}
              <div class="modal-actions">
                <button class="btn btn-secondary" type="button" id="cancelModalBtn">Cancelar</button>
                <button class="btn btn-primary" type="submit">${mode === 'create' ? 'Guardar' : 'Actualizar'}</button>
              </div>
            </form>
          </div>
        </div>
      `;

      const close = () => modalRoot.innerHTML = '';
      document.getElementById('closeModalBtn').addEventListener('click', close);
      document.getElementById('cancelModalBtn').addEventListener('click', close);
      modalRoot.querySelector('.modal').addEventListener('click', (event) => {
        if (event.target.classList.contains('modal')) close();
      });

      document.getElementById('dynamicForm').addEventListener('submit', async (event) => {
        event.preventDefault();
        const formData = new FormData(event.currentTarget);
        const values = Object.fromEntries([...formData.entries()].map(([key, value]) => [key, String(value).trim()]));
        const payload = config.buildPayload ? config.buildPayload(values, mode) : values;

        try {
          const path = mode === 'create' ? config.path : `${config.path}/${id}`;
          const method = mode === 'create' ? 'POST' : 'PUT';
          await api(path, { method, body: JSON.stringify(payload) });
          close();
          await loadAllData();
          setSection(section);
          showToast(mode === 'create' ? 'Registro creado correctamente.' : 'Registro actualizado correctamente.', 'success');
        } catch (error) {
          showToast(error.message || 'No se pudo guardar el registro.', 'error');
        }
      });
    }

    function renderField(field, value) {
      const isFull = field.type === 'textarea';
      const attrs = [
        `name="${field.name}"`,
        `id="field_${field.name}"`,
        field.required ? 'required' : '',
        field.min != null ? `min="${field.min}"` : '',
        field.step != null ? `step="${field.step}"` : ''
      ].filter(Boolean).join(' ');

      if (field.type === 'select') {
        const options = resolveOptions(field.options);
        return `
          <div class="field ${isFull ? 'full' : ''}">
            <label for="field_${field.name}">${field.label}</label>
            <select ${attrs}>
              <option value="">Selecciona una opción</option>
              ${options.map(opt => `<option value="${escapeHtml(opt.value)}" ${String(value) === String(opt.value) ? 'selected' : ''}>${escapeHtml(opt.label)}</option>`).join('')}
            </select>
          </div>
        `;
      }

      if (field.type === 'textarea') {
        return `
          <div class="field full">
            <label for="field_${field.name}">${field.label}</label>
            <textarea ${attrs}>${escapeHtml(value)}</textarea>
          </div>
        `;
      }

      return `
        <div class="field ${isFull ? 'full' : ''}">
          <label for="field_${field.name}">${field.label}</label>
          <input type="${field.type}" value="${escapeHtml(value)}" ${attrs} />
        </div>
      `;
    }

    function resolveOptions(options) {
      if (Array.isArray(options)) return options;
      if (options === 'usuarios') {
        return state.data.usuarios.map(u => ({ value: u.id, label: `${u.nombre} (${u.username})` }));
      }
      if (options === 'usuariosEmpleados') {
        return state.data.usuarios
          .filter(u => String(u.rol || '').toUpperCase() === 'EMPLEADO')
          .map(u => ({ value: u.id, label: `${u.nombre} (${u.username})` }));
      }
      if (options === 'bodegas') {
        return state.data.bodegas.map(b => ({ value: b.id, label: `${b.nombre} · ${b.ubicacion}` }));
      }
      if (options === 'productos') {
        return state.data.productos.map(p => ({ value: p.id, label: `${p.nombre} · stock ${p.stock ?? 0}` }));
      }
      if (options === 'movimientos') {
        return state.data.movimientos.map(m => ({ value: m.id, label: `#${m.id} · ${m.tipoMovimiento || '-'} · ${formatDate(m.fecha)}` }));
      }
      return [];
    }

    async function removeRecord(section, id) {
      const config = sectionConfig[section];
      const ok = confirm(`¿Seguro que quieres eliminar el registro #${id} de ${config.label}?`);
      if (!ok) return;

      try {
        await api(`${config.path}/${id}`, { method: 'DELETE' });
        await loadAllData();
        setSection(section);
        showToast('Registro eliminado correctamente.', 'success');
      } catch (error) {
        showToast(error.message || 'No se pudo eliminar el registro.', 'error');
      }
    }

    function showToast(message, type = 'success') {
      const toast = document.createElement('div');
      toast.className = `toast ${type}`;
      toast.textContent = message;
      toastRoot.appendChild(toast);
      setTimeout(() => {
        toast.style.opacity = '0';
        toast.style.transform = 'translateY(8px)';
      }, 2600);
      setTimeout(() => toast.remove(), 3200);
    }

    function ensureArray(value) {
      return Array.isArray(value) ? value : [];
    }

    async function parseResponse(response) {
      const text = await response.text();
      if (!text) return null;
      try {
        return JSON.parse(text);
      } catch {
        return text;
      }
    }

    function extractError(data) {
      if (!data) return '';
      if (typeof data === 'string') return data;
      return data.message || data.error || data.detalle || data.detalles || '';
    }

    function money(value) {
      const amount = Number(value ?? 0);
      return new Intl.NumberFormat('es-CO', { style: 'currency', currency: 'COP', maximumFractionDigits: 2 }).format(amount);
    }

    function formatDate(value) {
      if (!value) return '-';
      const date = new Date(value);
      if (Number.isNaN(date.getTime())) return String(value);
      return new Intl.DateTimeFormat('es-CO', {
        dateStyle: 'medium',
        timeStyle: 'short'
      }).format(date);
    }

    function toIsoDate(value) {
      if (!value) return null;
      const date = new Date(value);
      return Number.isNaN(date.getTime()) ? value : date.toISOString();
    }

    function toDatetimeLocal(value) {
      if (!value) return '';
      const date = new Date(value);
      if (Number.isNaN(date.getTime())) return '';
      const offset = date.getTimezoneOffset();
      const adjusted = new Date(date.getTime() - offset * 60000);
      return adjusted.toISOString().slice(0, 16);
    }

    function nested(obj, path) {
      return path.split('.').reduce((acc, key) => acc && acc[key] != null ? acc[key] : null, obj);
    }

    function badge(kind, text) {
      const safeKind = escapeHtml(kind || '').toLowerCase();
      return `<span class="badge ${safeKind}">${escapeHtml(text)}</span>`;
    }

    function countLowStock() {
      return state.data.productos.filter(p => Number(p.stock ?? 0) < 10).length;
    }

    function escapeHtml(value) {
      return String(value ?? '')
        .replace(/&/g, '&amp;')
        .replace(/</g, '&lt;')
        .replace(/>/g, '&gt;')
        .replace(/"/g, '&quot;')
        .replace(/'/g, '&#39;');
    }
