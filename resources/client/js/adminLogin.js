function pageLoad() {
    checkLogin();
    if(window.location.search === '?adminLogout') {
        document.getElementById('content').innerHTML = '<h1>Logging out, please wait...</h1>';
        adminLogout();
    } else {
        document.getElementById("loginButton").addEventListener("click", adminLogin);
    }

}
function adminLogin(event) {

    event.preventDefault();

    const form = document.getElementById("loginForm");
    const formData = new FormData(form);

    fetch("/Staff/login", {method: 'post', body: formData}
    ).then(response => response.json()
    ).then(responseData => {

        if (responseData.hasOwnProperty('error')) {
            alert(responseData.error);
        } else {
            Cookies.set("staffUser", responseData.staffUser);
            Cookies.set("token", responseData.token);

            window.location.href = '/client/index.html';
        }
    });
}
function adminLogout() {

    fetch("/Staff/logout", {method: 'post'}
    ).then(response => response.json()
    ).then(responseData => {
        if (responseData.hasOwnProperty('error')) {

            alert(responseData.error);

        } else {

            Cookies.remove("staffUser");
            Cookies.remove("token");

            window.location.href = '/client/index.html';

        }
    });
}


