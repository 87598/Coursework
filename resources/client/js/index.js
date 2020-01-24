

function pageLoad() {
    checkLogin();

    //this makes the config option hidden from users who aren't admins
    let admin = Cookies.get("staffUser");
    if(admin != null){
        //if they are admins then the config menu is visible
        document.getElementById("config").style.visibility = "visible";
    }
    else{
        //if the user isn't an admin then the menu is hidden
        document.getElementById("config").style.visibility = "hidden";
    }




    let now = new Date();

    let myHTML = '<div style="text-align:center;">'
        + '<h1>Pizza Ordering System</h1>'
        + '<img src="/client/img/pizza-fun-facts.jpg"  alt="Logo"/>'
        + '<div style="font-style: italic;">'
        + '</div>';

    document.getElementById("listDiv").innerHTML = myHTML;

    let myIndex = 0;
    carousel();

    //this is the way that the background of the front page is made into a gallery
    function carousel() {
        let i;
        let x = document.getElementsByClassName("mySlides");
        for (i = 0; i < x.length; i++) {
            x[i].style.display = "none";
        }
        myIndex++;
        if (myIndex > x.length) {myIndex = 1}
        x[myIndex-1].style.display = "block";
        //this is what determines that duration that each image would be displayed
        setTimeout(carousel, 3000);
    }


    let pizzaHTML = '<div style="text-align:center;">'
        +'<h1>R PIZZA</h1>'
        + '<img src="/client/img/pizza-fun-facts.jpg"  alt="Logo"/>'
        + '<div style="font-style: italic;">'

        + '</div>' +
        //this generates the table which will be populated
        `<table>` +
        '<tr>' +
        //the names of the different fields that will be viewable to customers from the table Pizzas
        '<th>ID</th>' +
        '<th>Name</th>' +
        '<th>Image</th>' +
        '<th>Size</th>' +
        '<th>Base</th>' +
        '<th>Crust</th>' +
        '<th>Price</th>' +
        '</tr>';
    fetch('/Pizza/list', {method: 'get'}
    ).then(response => response.json()
    ).then(pizzas => {

        for (let pizza of pizzas) {

            pizzaHTML += `<tr>` +
                //this populates the table being used for the Pizzas database
                `<td>${pizza.pizzaID}</td>` +
                `<td>${pizza.pizzaName}</td>` +
                //this allows an image to presented in the table so that the customer can see the pizza
                `<td><img src='/client/img/${pizza.pizzaImage}' alt='Picture of ${pizza.pizzaName}' height='100px'></td>` +
                `<td>${pizza.pizzaSize}</td>` +
                `<td>${pizza.pizzaBase}</td>` +
                `<td>${pizza.pizzaCrust}</td>` +
                `<td>${pizza.pizzaPrice}</td>` +

                `<td class="last">` +
                `</td>` +
                `</tr>`;

        }
        pizzaHTML += '</table>';

        document.getElementById("listDiv").innerHTML = pizzaHTML;

    });

        }











