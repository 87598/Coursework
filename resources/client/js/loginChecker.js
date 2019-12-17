let admin = false;
let loggedIn = false;

function checkLogin() {

    //alert("Checking!!!!");

    let username = Cookies.get("customerUser");
    let admin = Cookies.get("staffUser");

    let logInHTML = '';

    if (admin !== undefined) {

        logInHTML = "Logged in as ADMIN (" + admin + "). <a href='/client/adminLogin.html?adminLogout'>Log out</a>";
        admin = true;
        loggedIn = true;

    } else if (username !== undefined) {

        logInHTML = "Logged in as " + username + ". <a href='/client/login.html?logout'>Log out</a>";
        admin = false;
        loggedIn = true;

    } else {

        logInHTML = "Not logged in. <a href='/client/login.html'>Log in</a>";
        admin = false;
        loggedIn = false;

    }

    document.getElementById("loggedInDetails").innerHTML = logInHTML;

    console.log("Admin: " + admin);

}

