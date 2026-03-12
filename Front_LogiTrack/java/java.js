fetch("http://localhost:8080/api/productos")
.then(response => response.json())
.then(data => {

const tabla = document.querySelector("#tablaProductos tbody");

data.forEach(producto => {

let fila = `
<tr>
<td>${producto.id}</td>
<td>${producto.nombre}</td>
<td>${producto.precio}</td>
<td>${producto.stock}</td>
</tr>
`;

tabla.innerHTML += fila;

});

});