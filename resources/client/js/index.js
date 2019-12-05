

function pageLoad() {

    let now = new Date();

    let myHTML = '<div style="text-align:center;">'
        + '<h1>Pizza Ordering System</h1>'
        + '<img src="/client/img/pizza-fun-facts.jpg"  alt="Logo"/>'
        + '<div style="font-style: italic;">'
        + 'Generated at ' + now.toLocaleTimeString()
        + '</div>'
        + '</div>';

    document.getElementById("listDiv").innerHTML = myHTML;

    let pizzaHTML = '<div style="text-align:center;">'
        + '<h1>R PIZZA</h1>'
        + '<img src="/client/img/pizza-fun-facts.jpg"  alt="Logo"/>'
        + '<div style="font-style: italic;">'
        + 'Generated at ' + now.toLocaleTimeString()
        + '</div>'
        + '</div>' +
        `<table>` +
        '<tr>' +
        '<th>ID</th>' +
        '<th>Name</th>' +
        '<th>Image</th>' +
        '<th>Size</th>' +
        '<th>Base</th>' +
        '<th>Crust</th>' +
        //'<th>Quantity</th>' +
        '<th>Price</th>' +
        '<th class="last">Options</th>' +
        '</tr>';

    fetch('/Pizza/list', {method: 'get'}
    ).then(response => response.json()
    ).then(pizzas => {

        for (let pizza of pizzas) {

            pizzaHTML += `<tr>` +
                `<td>${pizza.pizzaID}</td>` +
                `<td>${pizza.pizzaName}</td>` +
                `<td><img src='/client/img/${pizza.pizzaImage}' alt='Picture of ${pizza.pizzaName}' height='100px'></td>` +
                `<td>${pizza.pizzaSize}</td>` +
                `<td>${pizza.pizzaBase}</td>` +
                `<td>${pizza.pizzaCrust}</td>` +
                //`<td>${pizza.pizzaQuantity}</td>` +
                `<td>${pizza.pizzaPrice}</td>` +

                `<td class="last">` +
                `<button class='editButton' data-id='${pizza.pizzaID}'>Edit</button>` +
                `<button class='deleteButton' data-id='${pizza.pizzaID}'>Delete</button>` +
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

        checkLogin();

    });

    document.getElementById("saveButton").addEventListener("click", cancelEditPizza);
    document.getElementById("cancelButton").addEventListener("click", cancelDeletePizza);


        }

function checkLogin() {

    let username = Cookies.get("username");

    let logInHTML = '';

    if (username === undefined) {

        let editButtons = document.getElementsByClassName("editButton");
        for (let button of editButtons) {
            button.style.visibility = "hidden";
        }

        let deleteButtons = document.getElementsByClassName("deleteButton");
        for (let button of deleteButtons) {
            button.style.visibility = "hidden";
        }

        logInHTML = "Not logged in. <a href='/client/login.html'>Log in</a>";

    } else {

        let editButtons = document.getElementsByClassName("editButton");
        for (let button of editButtons) {
            button.style.visibility = "visible";
        }

        let deleteButtons = document.getElementsByClassName("deleteButton");
        for (let button of deleteButtons) {
            button.style.visibility = "visible";
        }

        logInHTML = "Logged in as " + username + ". <a href='/client/login.html?logout'>Log out</a>";

    }

    document.getElementById("loggedInDetails").innerHTML = logInHTML;

}






