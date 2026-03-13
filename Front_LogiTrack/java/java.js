/* ═══════════════════════════════════════════════════════
   app.js — Lógica principal del Sistema de Inventario
   API base: http://localhost:8080/api
═══════════════════════════════════════════════════════ */
 
// ─────────────────────────────────────────────────────
// CONFIGURACIÓN
// ─────────────────────────────────────────────────────
const BASE = 'http://localhost:8080/api';
const PAGE_SIZE = 12;
 
/**
 * Definición de módulos: endpoint, campos de formulario y color de acento.
 * Agrega o modifica los campos según tu modelo de datos real.
 */
const MODULES = {
  producto: {
    label: 'Producto',
    icon: '📦',
    color: 'var(--c-producto)',
    endpoint: '/producto',
    fields: [
      { key: 'nombre',      label: 'Nombre',      type: 'text',   required: true  },
      { key: 'descripcion', label: 'Descripción',  type: 'text'                    },
      { key: 'precio',      label: 'Precio',       type: 'number', required: true  },
      { key: 'stock',       label: 'Stock',        type: 'number'                  },
      { key: 'estado',      label: 'Estado',       type: 'select', options: ['ACTIVO', 'INACTIVO'] },
    ],
  },
  usuario: {
    label: 'Usuario',
    icon: '👤',
    color: 'var(--c-usuario)',
    endpoint: '/usuario',
    fields: [
      { key: 'nombre',   label: 'Nombre',   type: 'text',  required: true  },
      { key: 'apellido', label: 'Apellido', type: 'text'                   },
      { key: 'email',    label: 'Email',    type: 'email', required: true  },
      { key: 'telefono', label: 'Teléfono', type: 'text'                   },
      { key: 'rol',      label: 'Rol',      type: 'select', options: ['ADMIN', 'OPERADOR', 'VISOR'] },
      { key: 'estado',   label: 'Estado',   type: 'select', options: ['ACTIVO', 'INACTIVO'] },
    ],
  },
  bodega: {
    label: 'Bodega',
    icon: '🏭',
    color: 'var(--c-bodega)',
    endpoint: '/bodega',
    fields: [
      { key: 'nombre',    label: 'Nombre',    type: 'text',   required: true },
      { key: 'ubicacion', label: 'Ubicación', type: 'text'                   },
      { key: 'capacidad', label: 'Capacidad', type: 'number'                 },
      { key: 'estado',    label: 'Estado',    type: 'select', options: ['ACTIVO', 'INACTIVO'] },
    ],
  },
  auditoria: {
    label: 'Auditoría',
    icon: '🔍',
    color: 'var(--c-auditoria)',
    endpoint: '/auditoria',
    readOnly: true,           // sin botones de crear / editar / eliminar
    fields: [
      { key: 'accion',  label: 'Acción',   type: 'text'             },
      { key: 'tabla',   label: 'Tabla',    type: 'text'             },
      { key: 'usuario', label: 'Usuario',  type: 'text'             },
      { key: 'fecha',   label: 'Fecha',    type: 'datetime-local'   },
      { key: 'detalle', label: 'Detalle',  type: 'textarea'         },
    ],
  },
  movimiento: {
    label: 'Movimiento',
    icon: '🔄',
    color: 'var(--c-movimiento)',
    endpoint: '/movimiento',
    fields: [
      { key: 'tipo',        label: 'Tipo',        type: 'select', options: ['ENTRADA', 'SALIDA', 'TRASLADO'] },
      { key: 'fecha',       label: 'Fecha',       type: 'datetime-local' },
      { key: 'usuarioId',   label: 'Usuario ID',  type: 'number' },
      { key: 'bodegaId',    label: 'Bodega ID',   type: 'number' },
      { key: 'observacion', label: 'Observación', type: 'textarea' },
    ],
  },
  detalleMovimiento: {
    label: 'Detalle Movimiento',
    icon: '📋',
    color: 'var(--c-detalle)',
    endpoint: '/detalleMovimiento',
    fields: [
      { key: 'movimientoId',    label: 'Movimiento ID',   type: 'number', required: true },
      { key: 'productoId',      label: 'Producto ID',     type: 'number', required: true },
      { key: 'cantidad',        label: 'Cantidad',        type: 'number', required: true },
      { key: 'precioUnitario',  label: 'Precio Unitario', type: 'number'                 },
      { key: 'observacion',     label: 'Observación',     type: 'textarea'               },
    ],
  },
};
 
// ─────────────────────────────────────────────────────
// ESTADO DE LA APLICACIÓN
// ─────────────────────────────────────────────────────
let currentModule = 'producto';
let allData       = [];
let filteredData  = [];
let currentPage   = 1;
let sortKey       = null;
let sortAsc       = true;
let editingId     = null;
let deletingId    = null;
 
// ─────────────────────────────────────────────────────
// INICIALIZACIÓN
// ─────────────────────────────────────────────────────
document.addEventListener('DOMContentLoaded', () => {
  switchModule('producto');
  // Precarga de contadores de todos los módulos en paralelo
  Object.keys(MODULES).forEach(fetchCount);
});
 
// ─────────────────────────────────────────────────────
// CONTADOR EN EL SIDEBAR
// ─────────────────────────────────────────────────────
async function fetchCount(mod) {
  try {
    const res = await fetch(`${BASE}${MODULES[mod].endpoint}`);
    if (!res.ok) return;
    const json = await res.json();
    const data = normalizeData(json);
    const el = document.getElementById(`cnt-${mod}`);
    if (el) el.textContent = data.length;
  } catch (_) {
    // silencioso: el sidebar no debe mostrar error por conteos fallidos
  }
}
 
// ─────────────────────────────────────────────────────
// CAMBIO DE MÓDULO
// ─────────────────────────────────────────────────────
function switchModule(mod) {
  currentModule = mod;
  const cfg = MODULES[mod];
 
  // Cambiar variable CSS de acento
  document.documentElement.style.setProperty('--current', cfg.color);
 
  // Marcar ítem activo en el nav
  document.querySelectorAll('.nav-item').forEach(el =>
    el.classList.toggle('active', el.dataset.module === mod)
  );
 
  // Actualizar topbar
  document.getElementById('page-title').textContent       = cfg.label;
  document.getElementById('page-badge').textContent       = `GET /api${cfg.endpoint}`;
  document.getElementById('page-badge').style.color       = cfg.color;
  document.getElementById('tableTitle').textContent       = `Listado de ${cfg.label}`;
  document.getElementById('createBtn').style.display      = cfg.readOnly ? 'none' : '';
 
  // Limpiar estado
  allData      = [];
  filteredData = [];
  currentPage  = 1;
  sortKey      = null;
  document.getElementById('globalSearch').value = '';
 
  renderTable();
  loadCurrent();
}
 
// ─────────────────────────────────────────────────────
// FETCH — CARGAR DATOS DEL MÓDULO ACTIVO
// ─────────────────────────────────────────────────────
async function loadCurrent() {
  const cfg = MODULES[currentModule];
  const btn = document.getElementById('refreshBtn');
  btn.disabled  = true;
  btn.innerHTML = '<span class="spinner"></span>';
 
  showLoading();
 
  try {
    const res = await fetch(`${BASE}${cfg.endpoint}`);
    if (!res.ok) throw new Error(`HTTP ${res.status} — ${res.statusText}`);
 
    const json = await res.json();
    allData      = normalizeData(json);
    filteredData = [...allData];
    currentPage  = 1;
    sortKey      = null;
 
    // Actualizar estadísticas
    const now = new Date().toLocaleTimeString('es-CO');
    document.getElementById('stat-time').textContent    = now;
    document.getElementById('stat-endpoint').textContent = cfg.endpoint;
    document.getElementById('stat-total').textContent   = allData.length;
 
    // Actualizar contador del sidebar
    const cntEl = document.getElementById(`cnt-${currentModule}`);
    if (cntEl) cntEl.textContent = allData.length;
 
    document.getElementById('api-status').textContent = '● Conectado';
 
    renderTable();
    toast(`${allData.length} registros cargados`, 'success');
 
  } catch (err) {
    showError(err.message);
    document.getElementById('api-status').textContent = '✕ Error';
    toast(`Error: ${err.message}`, 'error');
 
  } finally {
    btn.disabled  = false;
    btn.innerHTML = '↻ Actualizar';
  }
}
 
/**
 * Normaliza distintos formatos de respuesta JSON:
 * array puro, { data: [] }, { content: [] }, { items: [] }, { result: [] }
 * o un objeto simple envuelto en array.
 */
function normalizeData(json) {
  if (Array.isArray(json))          return json;
  if (Array.isArray(json?.data))    return json.data;
  if (Array.isArray(json?.content)) return json.content;
  if (Array.isArray(json?.items))   return json.items;
  if (Array.isArray(json?.result))  return json.result;
  if (typeof json === 'object' && json !== null) return [json];
  return [];
}
 
// ─────────────────────────────────────────────────────
// RENDER TABLA
// ─────────────────────────────────────────────────────
function renderTable() {
  if (!filteredData.length) {
    if (!allData.length) return; // el estado de carga ya se mostrará
    showEmpty();
    updateStats();
    return;
  }
 
  const keys = Object.keys(filteredData[0]).filter(k => k !== '__proto__');
  const cfg  = MODULES[currentModule];
 
  // — Encabezado —
  document.getElementById('tblHead').innerHTML = `
    <tr>
      ${keys.map(k => `
        <th onclick="sortBy('${k}')" class="${sortKey === k ? 'sorted' : ''}">
          ${fmtKey(k)} ${sortKey === k ? (sortAsc ? '↑' : '↓') : ''}
        </th>
      `).join('')}
      ${!cfg.readOnly ? '<th style="text-align:right">Acciones</th>' : ''}
    </tr>
  `;
 
  // — Cuerpo paginado —
  const start    = (currentPage - 1) * PAGE_SIZE;
  const pageData = filteredData.slice(start, start + PAGE_SIZE);
 
  document.getElementById('tblBody').innerHTML = pageData.map(row => `
    <tr>
      ${keys.map(k => `
        <td title="${esc(String(row[k] ?? ''))}">
          ${renderCell(k, row[k])}
        </td>
      `).join('')}
      ${!cfg.readOnly ? `
        <td class="actions" style="text-align:right">
          <button class="btn btn-ghost btn-sm"
            onclick='openEditModal(${JSON.stringify(row).replace(/'/g, "&#39;")})'>✏</button>
          <button class="btn btn-danger btn-sm"
            onclick="askDelete(${row.id ?? row.Id ?? row.ID ?? 'null'})">🗑</button>
        </td>
      ` : ''}
    </tr>
  `).join('');
 
  updateStats();
  renderPagination();
}
 
/** Convierte camelCase / snake_case a texto legible */
function fmtKey(k) {
  return k.replace(/([A-Z])/g, ' $1').replace(/_/g, ' ').trim();
}
 
/** Renderiza el contenido de una celda con colores semánticos */
function renderCell(key, value) {
  if (value === null || value === undefined) {
    return `<span style="color:var(--muted2)">—</span>`;
  }
 
  const str = String(value);
  const lk  = key.toLowerCase();
 
  // Booleanos
  if (value === true)  return `<span class="badge badge-green">Sí</span>`;
  if (value === false) return `<span class="badge badge-red">No</span>`;
 
  // Estado / status
  if (lk.includes('estado') || lk.includes('status')) {
    const map = { ACTIVO: 'badge-green', INACTIVO: 'badge-red', ACTIVA: 'badge-green', INACTIVA: 'badge-red' };
    return `<span class="badge ${map[str.toUpperCase()] || 'badge-blue'}">${esc(str)}</span>`;
  }
 
  // Tipo de movimiento
  if (lk === 'tipo' || lk.includes('tipo')) {
    const map = { ENTRADA: 'badge-green', SALIDA: 'badge-red', TRASLADO: 'badge-yellow', ADMIN: 'badge-purple', OPERADOR: 'badge-blue', VISOR: 'badge-orange' };
    return `<span class="badge ${map[str.toUpperCase()] || 'badge-blue'}">${esc(str)}</span>`;
  }
 
  // Rol de usuario
  if (lk === 'rol') {
    const map = { ADMIN: 'badge-purple', OPERADOR: 'badge-blue', VISOR: 'badge-orange' };
    return `<span class="badge ${map[str.toUpperCase()] || 'badge-blue'}">${esc(str)}</span>`;
  }
 
  // Montos / precios
  const isMoneyKey = lk.includes('precio') || lk.includes('monto') || lk.includes('valor')
    || lk.includes('saldo') || lk.includes('amount') || lk.includes('unitario');
  if (isMoneyKey && !isNaN(parseFloat(value))) {
    const n = parseFloat(value);
    const color = n >= 0 ? 'var(--c-bodega)' : '#ef4444';
    return `<span style="color:${color};font-weight:600">$${n.toLocaleString('es-CO', { minimumFractionDigits: 2 })}</span>`;
  }
 
  // Cantidades
  const isQtyKey = lk.includes('stock') || lk.includes('cantidad') || lk.includes('capacidad');
  if (isQtyKey && !isNaN(parseFloat(value))) {
    return `<span style="color:var(--c-movimiento)">${parseFloat(value).toLocaleString('es-CO')}</span>`;
  }
 
  // Fechas
  if (lk.includes('fecha') || lk.includes('date') || lk.includes('created') || lk.includes('updated')) {
    try {
      const d = new Date(value);
      if (!isNaN(d)) {
        const date = d.toLocaleDateString('es-CO');
        const time = d.toLocaleTimeString('es-CO', { hour: '2-digit', minute: '2-digit' });
        return `<span style="color:#94a3b8">${date} <span style="color:var(--muted)">${time}</span></span>`;
      }
    } catch (_) {}
  }
 
  // IDs / códigos
  if (lk === 'id' || lk.endsWith('id') || lk.includes('codigo') || lk.includes('code')) {
    return `<span style="color:var(--c-usuario);font-family:var(--mono)">${esc(str)}</span>`;
  }
 
  // Email
  if (lk.includes('email')) {
    return `<span style="color:var(--c-auditoria)">${esc(str)}</span>`;
  }
 
  return `<span>${esc(str)}</span>`;
}
 
/** Escapa caracteres HTML peligrosos */
function esc(s) {
  return String(s)
    .replace(/&/g, '&amp;')
    .replace(/</g, '&lt;')
    .replace(/>/g, '&gt;')
    .replace(/"/g, '&quot;');
}
 
// ─────────────────────────────────────────────────────
// FILTRO Y ORDENAMIENTO
// ─────────────────────────────────────────────────────
function applyFilter() {
  const query = document.getElementById('globalSearch').value.toLowerCase().trim();
  filteredData = query
    ? allData.filter(row =>
        Object.values(row).some(v => String(v ?? '').toLowerCase().includes(query))
      )
    : [...allData];
  currentPage = 1;
  renderTable();
}
 
function sortBy(key) {
  if (sortKey === key) {
    sortAsc = !sortAsc;
  } else {
    sortKey = key;
    sortAsc = true;
  }
  filteredData.sort((a, b) => {
    const va = a[key], vb = b[key];
    if (va === null || va === undefined) return 1;
    if (vb === null || vb === undefined) return -1;
    const res = String(va).localeCompare(String(vb), 'es', { numeric: true });
    return sortAsc ? res : -res;
  });
  currentPage = 1;
  renderTable();
}
 
// ─────────────────────────────────────────────────────
// ESTADÍSTICAS Y PAGINACIÓN
// ─────────────────────────────────────────────────────
function updateStats() {
  const start = (currentPage - 1) * PAGE_SIZE;
  const end   = Math.min(start + PAGE_SIZE, filteredData.length);
 
  document.getElementById('stat-total').textContent    = allData.length      || '—';
  document.getElementById('stat-showing').textContent  = filteredData.length ? (end - start) || '—' : '—';
  document.getElementById('stat-filtered').textContent = filteredData.length || '—';
  document.getElementById('recordCount').textContent   = filteredData.length
    ? `${filteredData.length} registro(s)` : '';
}
 
function renderPagination() {
  const total = filteredData.length;
  const pages = Math.ceil(total / PAGE_SIZE);
  const bar   = document.getElementById('paginationBar');
 
  if (pages <= 1) { bar.style.display = 'none'; return; }
  bar.style.display = 'flex';
 
  const s = (currentPage - 1) * PAGE_SIZE;
  document.getElementById('paginationInfo').textContent =
    `${Math.min(s + 1, total)}–${Math.min(s + PAGE_SIZE, total)} de ${total}`;
 
  const container = document.getElementById('paginationBtns');
  container.innerHTML = '';
 
  const addBtn = (label, page, disabled, active) => {
    const btn = document.createElement('button');
    btn.className = 'page-btn' + (active ? ' active' : '');
    btn.textContent = label;
    btn.disabled = disabled;
    btn.onclick = () => { currentPage = page; renderTable(); };
    container.appendChild(btn);
  };
 
  addBtn('«', 1,                currentPage === 1,     false);
  addBtn('‹', currentPage - 1,  currentPage === 1,     false);
 
  let start = Math.max(1, currentPage - 2);
  let end   = Math.min(pages, start + 4);
  if (end - start < 4) start = Math.max(1, end - 4);
  for (let i = start; i <= end; i++) addBtn(i, i, false, i === currentPage);
 
  addBtn('›', currentPage + 1,  currentPage === pages, false);
  addBtn('»', pages,             currentPage === pages, false);
}
 
// ─────────────────────────────────────────────────────
// ESTADOS DE TABLA
// ─────────────────────────────────────────────────────
function showLoading() {
  document.getElementById('tblHead').innerHTML = '';
  document.getElementById('tblBody').innerHTML = `
    <tr><td class="tbl-state" colspan="99">
      <span class="spinner" style="margin-bottom:10px;display:inline-block"></span><br>
      Consultando API...
    </td></tr>
  `;
  document.getElementById('paginationBar').style.display = 'none';
}
 
function showEmpty() {
  document.getElementById('tblHead').innerHTML = '';
  document.getElementById('tblBody').innerHTML = `
    <tr><td class="tbl-state" colspan="99">
      <span class="icon">🔍</span>
      Sin resultados para la búsqueda actual.
    </td></tr>
  `;
  document.getElementById('paginationBar').style.display = 'none';
}
 
function showError(msg) {
  document.getElementById('tblHead').innerHTML = '';
  document.getElementById('tblBody').innerHTML = `
    <tr><td class="tbl-state" colspan="99">
      <span class="icon">⚠</span>
      <div style="color:#ef4444;margin-bottom:4px">Error al conectar con la API</div>
      <div style="font-size:11px;color:var(--muted)">${esc(msg)}</div>
      <br>
      <button class="btn btn-ghost btn-sm" onclick="loadCurrent()">↻ Reintentar</button>
    </td></tr>
  `;
  document.getElementById('paginationBar').style.display = 'none';
}
 
// ─────────────────────────────────────────────────────
// MODAL — CREAR
// ─────────────────────────────────────────────────────
function openCreateModal() {
  editingId = null;
  const cfg = MODULES[currentModule];
  document.getElementById('modalTitle').innerHTML = `${cfg.icon} Nuevo ${cfg.label}`;
  buildFormFields(cfg.fields, null);
  document.getElementById('saveBtn').textContent = 'Crear';
  openModal('formModal');
}
 
// ─────────────────────────────────────────────────────
// MODAL — EDITAR
// ─────────────────────────────────────────────────────
function openEditModal(row) {
  editingId = row.id ?? row.Id ?? row.ID ?? null;
  const cfg = MODULES[currentModule];
  document.getElementById('modalTitle').innerHTML = `${cfg.icon} Editar ${cfg.label}`;
  buildFormFields(cfg.fields, row);
  document.getElementById('saveBtn').textContent = 'Actualizar';
  openModal('formModal');
}
 
/** Construye dinámicamente los campos del formulario */
function buildFormFields(fields, data) {
  document.getElementById('formFields').innerHTML = fields.map(f => {
    const val  = data ? (data[f.key] ?? '') : '';
    const full = f.type === 'textarea' ? 'full' : '';
    let input  = '';
 
    if (f.type === 'select') {
      const opts = f.options.map(o =>
        `<option value="${o}" ${val === o ? 'selected' : ''}>${o}</option>`
      ).join('');
      input = `<select name="${f.key}" ${f.required ? 'required' : ''}>${opts}</select>`;
 
    } else if (f.type === 'textarea') {
      input = `<textarea name="${f.key}">${esc(String(val))}</textarea>`;
 
    } else {
      input = `<input
        type="${f.type}"
        name="${f.key}"
        value="${esc(String(val))}"
        ${f.required ? 'required' : ''}
      />`;
    }
 
    return `
      <div class="form-group ${full}">
        <label>${f.label}${f.required ? ' *' : ''}</label>
        ${input}
      </div>
    `;
  }).join('');
}
 
// ─────────────────────────────────────────────────────
// GUARDAR (POST / PUT)
// ─────────────────────────────────────────────────────
async function saveRecord() {
  const cfg  = MODULES[currentModule];
  const form = document.getElementById('dataForm');
  const body = {};
 
  cfg.fields.forEach(f => {
    const el = form.elements[f.key];
    if (!el) return;
    let v = el.value.trim();
    if (f.type === 'number' && v !== '') v = parseFloat(v);
    if (v !== '') body[f.key] = v;
  });
 
  const btn = document.getElementById('saveBtn');
  btn.disabled    = true;
  btn.textContent = '⏳';
 
  try {
    const url    = editingId ? `${BASE}${cfg.endpoint}/${editingId}` : `${BASE}${cfg.endpoint}`;
    const method = editingId ? 'PUT' : 'POST';
 
    const res = await fetch(url, {
      method,
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(body),
    });
 
    if (!res.ok) {
      const txt = await res.text();
      throw new Error(`HTTP ${res.status}: ${txt}`);
    }
 
    closeModal('formModal');
    toast(editingId ? 'Registro actualizado ✓' : 'Registro creado ✓', 'success');
    await loadCurrent();
 
  } catch (err) {
    toast(`Error: ${err.message}`, 'error');
 
  } finally {
    btn.disabled    = false;
    btn.textContent = editingId ? 'Actualizar' : 'Crear';
  }
}
 
// ─────────────────────────────────────────────────────
// ELIMINAR (DELETE)
// ─────────────────────────────────────────────────────
function askDelete(id) {
  if (id === null || id === undefined || id === 'null') {
    toast('No se pudo determinar el ID del registro', 'error');
    return;
  }
  deletingId = id;
  openModal('confirmModal');
  document.getElementById('confirmDeleteBtn').onclick = confirmDelete;
}
 
async function confirmDelete() {
  const cfg = MODULES[currentModule];
  const btn = document.getElementById('confirmDeleteBtn');
  btn.disabled    = true;
  btn.textContent = '⏳';
 
  try {
    const res = await fetch(`${BASE}${cfg.endpoint}/${deletingId}`, { method: 'DELETE' });
    if (!res.ok) throw new Error(`HTTP ${res.status}`);
    closeModal('confirmModal');
    toast('Registro eliminado', 'info');
    await loadCurrent();
 
  } catch (err) {
    toast(`Error: ${err.message}`, 'error');
 
  } finally {
    btn.disabled    = false;
    btn.textContent = 'Eliminar';
  }
}
 
// ─────────────────────────────────────────────────────
// HELPERS DE MODAL
// ─────────────────────────────────────────────────────
function openModal(id)  { document.getElementById(id).classList.add('open');    }
function closeModal(id) { document.getElementById(id).classList.remove('open'); }
 
// Cerrar modal al hacer clic en el overlay
document.querySelectorAll('.modal-overlay').forEach(overlay => {
  overlay.addEventListener('click', e => {
    if (e.target === overlay) overlay.classList.remove('open');
  });
});
 
// ─────────────────────────────────────────────────────
// TOASTS
// ─────────────────────────────────────────────────────
function toast(msg, type = 'info') {
  const icons = { success: '✓', error: '✕', info: 'ℹ' };
  const el    = document.createElement('div');
  el.className = `toast ${type}`;
  el.innerHTML = `<span>${icons[type]}</span><span>${msg}</span>`;
  document.getElementById('toastContainer').appendChild(el);
  setTimeout(() => { el.style.opacity = '0'; }, 3000);
  setTimeout(() => { el.remove();             }, 3400);
}
 