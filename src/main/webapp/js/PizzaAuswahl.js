var amountPizza = 0;
let selectLanguage = 'deutsch';
let language = 'Sprache';
let deliveryAddress = 'Lieferadresse';
let sum = 'Summe';
let order = 'Bestellen';
let piece = 'Stück';
let total = 'Gesamt';
let price = 'Preis';
let back = 'zurück';
let alert1 = 'Lieferadresse fehlt';
let alert2 = 'Es muss mindestens eine Pizza bestellt werden';

function loadPizza () {
    fetch('./PizzaServlet?all=yes')
        .then(
            function (response) {
                if (response.status !== 200){
                    console.log('Looks like there was a problem. Status Code: ' + response.status);
                    return;
                }

                response.json().then(function (data){
                    console.log(data);

                    var element = document.getElementById("pizzas");

                    var str = "";

                    data.forEach(function(pizza){
                        str += "<tr>" +
                            "<td>" + "<img width='100px' src='" + pizza.image + "'>" + "</td>" +
                            "<td>" + "<span>" + pizza.name + "</span>" + "</td>" +
                            "<td>" + "<span>" + pizza.price +" &euro;</span>" + "</td>" +
                            "<td><select name='pizza" + amountPizza + "' id='input" + amountPizza + "'>";
                        for (let i = 0; i <= 10; i++) {
                            str += "<option value='" + i +"'>" + i + "</option>"
                        }
                        str += "</select></td></tr>";
                        amountPizza++;
                    });

                    element.innerHTML = str;
                    checkCookie(true);

                });
            }
        )
        .catch(function (err){
            console.log('Fetch Error :-S', err);
        });
}

function loadFilteredPizza() {
    fetch('./PizzaServlet')
        .then(
            function (response) {
                if (response.status !== 200){
                    console.log('Looks like there was a problem. Status Code: ' + response.status);
                    return;
                }

                response.json().then(function (data){
                    console.log(data);

                    var element = document.getElementById("pizzas");

                    var sumPrice = 0;

                    element.innerHTML += "<tr>" +
                        "<th>" + "<span>Pizza</span>" + "</th>" +
                        "<th>" + "<span id='price'>" + price + "</span>" + "</th>" +
                        "<th>" + "<span id='piece'>" + piece + "</span>" + "</th>" +
                        "<th>" + "<span id='total'>" + total + "</span>" + "</th>" +
                        "</tr>";

                    data.pizzas.forEach(function(pizza){
                        element.innerHTML += "<tr>" +
                            "<td>" + "<span>" + pizza.name + "</span>" + "</td>" +
                            "<td>" + "<span>" + pizza.price +"&euro;</span>" + "</td>" +
                            "<td>" + "<span>" + pizza.amount +"</span>" + "</td>" +
                            "<td>" + "<span>" + pizza.price * pizza.amount +" &euro;</span>" + "</td>" +
                            "</tr>";
                        sumPrice += pizza.price * pizza.amount;
                        amountPizza++;
                    });

                    element.innerHTML += "<tr>" +
                        "<td>" + "<span></span>" + "</td>" +
                        "<td>" + "<span></span>" + "</td>" +
                        "<td>" + "<span id='sum'>" + sum + ":</span>" + "</td>" +
                        "<td>" + "<span>" + sumPrice + " &euro;</span>" + "</td>" +
                        "</tr>";


                    document.getElementById("lieferadresse").innerText = data.address;
                    checkCookie(false);
                });
            }
        )
        .catch(function (err){
            console.log('Fetch Error :-S', err);
        });
}

function checkInput () {
    for (let i = 0; i < amountPizza; i++){
        if (document.getElementById(("input" + i)).value != 0 ){
            if (document.getElementById("lieferadresse").value != ''){
                return true;
            } else {
                alert(alert1);
                return false;
            }
        }
    }
    alert(alert2);
    return false;
}

function changeLanguage(isAuswahl) {
    var languageGet = document.getElementById('language').value;
    fetch('./Language?language=' + languageGet)
        .then(function (response) {
                if (response.status !== 200){
                    console.log('Looks like there was a problem. Status Code: ' + response.status);
                    return;
                }

                response.json().then(function (data){
                    console.log(data);

                    selectLanguage = data[0];
                    deliveryAddress = data[1];
                    language = data[2];
                    order = data[3];
                    sum = data[4];
                    price = data[5];
                    piece = data[6];
                    total = data[7];
                    back = data[8];
                    alert1 = data[9];
                    alert2 = data[10];

                    if (isAuswahl){
                        setLanguagePizzaAuswahl();
                    } else {
                        setLanguagePizzaBestellung();
                    }
                });
            }
        )
        .catch(function (err){
            console.log('Fetch Error :-S', err);
        });
}

function checkCookie (isAuswahl) {
    fetch('./Language')
        .then(function (response) {
                if (response.status != 200){
                    console.log('Looks like there was a problem. Status Code: ' + response.status);
                    return;
                }

                response.json().then(function (data){
                    console.log(data);

                    selectLanguage = data[0];
                    deliveryAddress = data[1];
                    language = data[2];
                    order = data[3];
                    sum = data[4];
                    price = data[5];
                    piece = data[6];
                    total = data[7];
                    back = data[8];
                    alert1 = data[9];
                    alert2 = data[10];

                    if (isAuswahl){
                        setLanguagePizzaAuswahl();
                    } else {
                        setLanguagePizzaBestellung();
                    }
                });
            }
        )
        .catch(function (err){
            console.log('Fetch Error :-S', err);
        });
}

function setLanguagePizzaAuswahl () {
    console.log(selectLanguage);
    if (selectLanguage === 'deutsch'){
        document.getElementById("language").options[0].setAttribute('selected', true);
    } else {
        document.getElementById("language").options[1].setAttribute('selected', true);
    }
    document.getElementById("lanLanguage").innerText = language;
    document.getElementById("lanLieferadresse").innerText = deliveryAddress;
    document.getElementById("lanBestellen").value = order;
}
function setLanguagePizzaBestellung () {
    console.log(selectLanguage);
    if (selectLanguage === 'deutsch'){
        document.getElementById("language").options[0].setAttribute('selected', true);
    } else {
        document.getElementById("language").options[1].setAttribute('selected', true);
    }

    document.getElementById("lanLieferadresse").innerText = deliveryAddress + ": ";
    document.getElementById("piece").innerText = piece;
    document.getElementById("total").innerText = total;
    document.getElementById("price").innerText = price;
    document.getElementById("back").value = back;
    document.getElementById("sum").innerText = sum + ": ";
}
