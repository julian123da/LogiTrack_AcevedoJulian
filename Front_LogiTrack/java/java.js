const tabla = document.querySelector("#tablaProductos tbody");

fetch("http://localhost:8080/api/productos")

.then(response => response.json())

.then(data => {

tabla.innerHTML="";

data.forEach(producto => {

let fila = document.createElement("tr");

fila.innerHTML = `

<td>${producto.id}</td>

<td>${producto.nombre}</td>

<td>$ ${producto.precio}</td>

<td>${producto.stock}</td>

`;

tabla.appendChild(fila);

});

});




const buscador = document.getElementById("buscador");

buscador.addEventListener("keyup", ()=>{

let texto = buscador.value.toLowerCase();

let filas = document.querySelectorAll("tbody tr");

filas.forEach(fila=>{

let nombre = fila.children[1].textContent.toLowerCase();

fila.style.display =
nombre.includes(texto) ? "" : "none";

});

});