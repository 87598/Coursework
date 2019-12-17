function pageLoad(){
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
            //button.addEventListener("click", editPizza);
        }

        let deleteButtons = document.getElementsByClassName("deleteButton");
        for (let button of deleteButtons) {
            //button.addEventListener("click", deletePizza);
        }


    });

    //document.getElementById("saveButton").addEventListener("click", cancelEditPizza);
    //document.getElementById("cancelButton").addEventListener("click", cancelDeletePizza);

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




