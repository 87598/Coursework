function pageLoad() {

    let now = new Date();

    let myHTML = '<div style="text-align:center;">'
        + '<h1>Welcome to my API powered website!</h1>'
        + '<img src="/client/img/keanu reeves.jpg.jfif"  alt="Logo"/>'
        + '<div style="font-style: italic;">'
        + 'Generated at ' + now.toLocaleTimeString()
        + '</div>'
        + '</div>';

    document.getElementById("testDiv").innerHTML = myHTML;

    let pizzaHTML = `<table>` +
        '<tr>' +
        '<th>Id</th>' +
        '<th>Name</th>' +
        '<th>Image</th>' +
        '<th>Description</th>' +
        '<th>Size</th>' +
        '<th>Price</th>' +
        '<th class="last">Options</th>' +
        '</tr>';

    fetch('/Pizza/list', {method: 'get'}
    ).then(response => response.json()
    ).then(pizzas => {

        for (let pizza of pizzas) {

            pizzaHTML += `<tr>` +
                `<td>${pizza.id}</td>` +
                `<td>${pizza.name}</td>` +
                `<td><img src='/client/img/${pizza.image}' alt='Picture of ${pizza.name}' height='100px'></td>` +
                `<td>${pizza.size}</td>` +
                `<td class="last">` +
                `<button class='editButton' data-id='${pizza.id}'>Edit</button>` +
                `<button class='deleteButton' data-id='${pizza.id}'>Delete</button>` +
                `</td>` +
                `</tr>`;

        }
        pizzaHTML += '</table>';

        document.getElementById("listDiv").innerHTML = pizzaHTML;

        let editButtons = document.getElementsByClassName("editButton");
        for (let button of editButtons) {
            button.addEventListener("click", editPizza);
        }

        let deleteButtons = document.getElementsByClassName("deleteButton");
        for (let button of deleteButtons) {
            button.addEventListener("click", deletePizza);
        }


    });



}


