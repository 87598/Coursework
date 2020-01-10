function pageLoad() {
    checkLogin();

    let configPizza = '<div style="text-align:center;">'
        + '<h1>ADMIN PAGE</h1>'
        + '<div style="font-style: italic;">'
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

            configPizza += `<tr>` +
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
        configPizza += '</table>';

        document.getElementById("listDiv").innerHTML = configPizza;

        let editButtons = document.getElementsByClassName("editButton");
        for (let button of editButtons) {
            button.addEventListener("click", editPizza);
        }

        let deleteButtons = document.getElementsByClassName("deleteButton");
        for (let button of deleteButtons) {
            button.addEventListener("click", deletePizza);
        }

    });

    document.getElementById("saveButton").addEventListener("click", saveEditPizza);
    document.getElementById("cancelButton").addEventListener("click", cancelEditPizza);

   /*----------------------------------------------------------*/
    let configDrink = '<div style="text-align:center;">'
    + '<div style="font-style: italic;">'
    + '</div>'
    + '</div>' +
    `<table>` +
    '<tr>' +
    '<th>ID</th>' +
    '<th>Name</th>' +
    '<th>Image</th>' +
    //'<th>Quantity</th>' +
    '<th>Price</th>' +
    '<th class="last">Options</th>' +
    '</tr>';

fetch('/Drink/list', {method: 'get'}
).then(response => response.json()
).then(pizzas => {

    for (let drink of drinks) {

        configDrink += `<tr>` +
            `<td>${drink.drinkID}</td>` +
            `<td>${drink.drinkName}</td>` +
            `<td><img src='/client/img/${drink.drinkImage}' alt='Picture of ${drink.drinkName}' height='100px'></td>` +
            //`<td>${pizza.pizzaQuantity}</td>` +
            `<td>${drink.drinkPrice}</td>` +

            `<td class="last">` +
            `<button class='editButton' data-id='${drink.drinkID}'>Edit</button>` +
            `<button class='deleteButton' data-id='${drink.drinkID}'>Delete</button>` +
            `</td>` +
            `</tr>`;

    }
    configDrink += '</table>';

    document.getElementById("listDiv").innerHTML = configDrink;

    let editButtons = document.getElementsByClassName("editButton");
    for (let button of editButtons) {
        //button.addEventListener("click", editPizza);
    }

    let deleteButtons = document.getElementsByClassName("deleteButton");
    for (let button of deleteButtons) {
        //button.addEventListener("click", deletePizza);
    }


});



}

/*----------------------------------------------------------*/

function editPizza(event) {

    const id = event.target.getAttribute("data-id");

    if (id === null) {

        document.getElementById("editHeading").innerHTML = 'Add new pizza:';

        document.getElementById("pizzaID").value = '';
        document.getElementById("pizzaName").value = '';
        document.getElementById("pizzaSize").value = '';
        document.getElementById("pizzaBase").value = '';
        document.getElementById("pizzaCrust").value = '';
        document.getElementById("pizzaImage").value = '';
        document.getElementById("pizzaPrice").value = '';

        document.getElementById("listDiv").style.display = 'none';
        document.getElementById("editDiv").style.display = 'block';

    } else {

        fetch('/Pizza/get/' + id, {method: 'get'}
        ).then(response => response.json()
        ).then(pizza => {

            if (pizza.hasOwnProperty('error')) {
                alert(pizza.error);
            } else {

                document.getElementById("editHeading").innerHTML = 'Editing ' + pizza.pizzaName + ':';

                document.getElementById("pizzaID").value = pizza.pizzaID;
                document.getElementById("pizzaName").value = pizza.pizzaName;
                document.getElementById("pizzaSize").value = pizza.pizzaSize;
                document.getElementById("pizzaBase").value = pizza.pizzaBase;
                document.getElementById("pizzaCrust").value = pizza.pizzaCrust;
                document.getElementById("pizzaImage").value = pizza.pizzaImage;
                document.getElementById("pizzaPrice").value = pizza.pizzaPrice;


                document.getElementById("listDiv").style.display = 'none';
                document.getElementById("editDiv").style.display = 'block';

            }

        });

    }

}

function saveEditPizza(event) {

    event.preventDefault();

    if (document.getElementById("pizzaName").value.trim() === '') {
        alert("Please provide a pizza name.");
        return;
    }

    if (document.getElementById("pizzaImage").value.trim() === '') {
        alert("Please provide a pizza image.");
        return;
    }

    if (document.getElementById("pizzaSize").value.trim() === '') {
        alert("Please provide a pizza size.");
        return;
    }

    if (document.getElementById("pizzaCrust").value.trim() === '') {
        alert("Please provide a pizza crust.");
        return;
    }

    if (document.getElementById("pizzaPrice").value.trim() === '') {
        alert("Please provide a pizza price.");
        return;
    }

    if (document.getElementById("pizzaBase").value.trim() === '') {
        alert("Please provide a pizza base.");
        return;
    }

    const id = document.getElementById("pizzaID").value;
    const form = document.getElementById("pizzaForm");
    const formData = new FormData(form);

    let apiPath = '';
    if (id === '') {
        apiPath = '/Pizza/create';
    } else {
        apiPath = '/Pizza/update';
    }

    fetch(apiPath, {method: 'post', body: formData}
    ).then(response => response.json()
    ).then(responseData => {

        if (responseData.hasOwnProperty('error')) {
            alert(responseData.error);
        } else {
            document.getElementById("listDiv").style.display = 'block';
            document.getElementById("editDiv").style.display = 'none';
            pageLoad();
        }
    });

}

function cancelEditPizza(event) {

    event.preventDefault();

    document.getElementById("listDiv").style.display = 'block';
    document.getElementById("editDiv").style.display = 'none';

}


function deletePizza(event) {

    const ok = confirm("Are you sure?");

    if (ok === true) {

        let id = event.target.getAttribute("data-id");
        let formData = new FormData();
        formData.append("pizzaID", id);

        fetch('/Pizza/delete', {method: 'post', body: formData}
        ).then(response => response.json()
        ).then(responseData => {

                if (responseData.hasOwnProperty('error')) {
                    alert(responseData.error);
                } else {
                    pageLoad();
                }
            }
        );
    }

}

function cancelDeletePizza(event) {

    event.preventDefault();

    document.getElementById("listDiv").style.display = 'block';
    document.getElementById("editDiv").style.display = 'none';

}




