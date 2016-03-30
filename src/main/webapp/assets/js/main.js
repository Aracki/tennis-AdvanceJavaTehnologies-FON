$('#logIn').click(function () {
    logIn();
});

var baseUrl = "http://localhost:8084/tenis/"
var baseUrlRest = baseUrl + "rest/";

function logIn() {
    var username = document.getElementById("user").value;
    var password = document.getElementById("pass").value;
    $.ajax({
        type: "POST",
        url: baseUrlRest + "administrator/login",
        dataType: "json",
        headers: {
            'Content-Type': 'application/json'
        },
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', make_base_auth(username, password));
        },
        success: function (response) {
            // json_token = response;
            window.location.href = "pocetna.html";
            document.cookie = "token=" + response.token;
//            alert(response.token);
        },
        error: function (response) {
            alert("Niste se uspesno ulogovali!");
        }

    });
}

function make_base_auth(user, password) {
    var tok = user + ':' + password;
    var hash = btoa(tok);
    return "Basic " + hash;
}

var takmicenja;
function ucitajTakmicenja() {
    $.ajax({
        url: 'http://localhost:8084/tenis/rest/takmicenje',
        dataType: 'json',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': getCookie('token')
        },
        success: function (response) {
            takmicenja = response;
            napuniTabeluTakmicenja(response);
        }

    });
}




function timeConverter(UNIX_timestamp) {
    var a = new Date(UNIX_timestamp * 1000);
    var months = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
    var year = a.getFullYear();
    var month = months[a.getMonth()];
    var date = a.getDate();
    var hour = a.getHours();
    var min = a.getMinutes();
    var sec = a.getSeconds();
    var time = date + ' ' + month + ' ' + year + ' ' + hour + ':' + min + ':' + sec;
    return time;
}

function napuniTabeluTakmicenja(takmicenja) {
    if (typeof takmicenja !== "undefined") {
        
        
        var table = document.getElementById('tabelaTakmicenja');
        table.innerHTML = "";      
        var table_body = document.createElement('TBODY');
        table.border = '1';
        table.appendChild(table_body);
        var tHead = document.createElement('THEAD');
        var arrayHeader = ["Naziv", "Datum pocetka", "Tip takmicenja"];
        for (var i = 0; i < arrayHeader.length; i++) {
            tHead.appendChild(document.createElement("TH")).appendChild(document.createTextNode(arrayHeader[i]));
        }

        for (var x = 0; x < takmicenja.length; x++) {
            var tr = document.createElement('TR');
            table_body.appendChild(tr);
            for (var j = 0; j < 4; j++) {

                var a = new Date(takmicenja[x].datumPocetka);
                var months = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
                var year = a.getUTCFullYear();
                var month = months[a.getMonth()];
                var date = a.getDate();
                var time = date + ' ' + month + ' ' + year;

                var td = document.createElement('TD');
                td.width = '50';
                switch (j) {
                    case 0:
                        td.appendChild(document.createTextNode(takmicenja[x].naziv));
                        break;
                    case 1:
                        td.appendChild(document.createTextNode(time));
                        break;
                    case 2:
                        td.appendChild(document.createTextNode(takmicenja[x].tiptakmicenja.nazivTipa));
                        break;
                    case 3:
                         var b = document.createElement('BUTTON');
                        b.className = "button btn-danger";
                        b.appendChild(document.createTextNode("Obriši"));
                        b.id = "DDD" + takmicenja[x].takmicenjeID;
                        td.appendChild(b);
                        break;
                    default:
                }
                tr.appendChild(td);
            }
        }
        table.appendChild(tHead);
    }
}

function setCookie(cname, cvalue, exdays) {
    var d = new Date();
    d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
    var expires = "expires=" + d.toUTCString();
    document.cookie = cname + "=" + cvalue + "; " + expires;
}

function getCookie(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for (var i = 0; i < ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ')
            c = c.substring(1);
        if (c.indexOf(name) === 0)
            return c.substring(name.length, c.length);
    }
    return "";
}

$('#btnUnosTipaTakmicenje').click(function () {

});
$('#btnUnosTakmicenje').click(function () {
    window.location.href = "unosTakmicenja.html";
});
$('#btnUnosTipaTakmicenje').click(function () {

});
function ubaciTipove() {
    $.ajax({
        url: 'http://localhost:8084/tenis/rest/tipTakmicenja',
        dataType: 'json',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': getCookie('token')
        },
//        async: false,
        success: function (response) {
            for (var i = 0; i < response.length; i++) {
                var option = document.createElement("option");
//                option.name = "tiptakmicenjaID";
                option.value = response[i].tiptakmicenjaID;
                option.innerHTML = response[i].nazivTipa;
                option.id = "ido";
                var select = document.getElementById("tipTakmicenjaSlc");
                select.appendChild(option);
            }
        }


    });
    
    // dodavanje takmicenja
    $('#btnDodajTakmicenje').click(function () {

        var str = {
            naziv: $('#naziv').val(),
            datumPocetka: new Date(),
            tiptakmicenja: {
                tiptakmicenjaID: $('#ido').val(),
            }
        };

        $.ajax({
            url: 'http://localhost:8084/tenis/rest/takmicenje',
            method: "POST",
            data: JSON.stringify(str),
            headers: {
                'Content-Type': 'application/json',
                'Authorization': getCookie('token')
            },
            success: function (response) {
                alert("Uspesno ste kreirali takmicenje!");
                window.location.href = baseUrl + "pocetna.html"
            },
            error: function (response) {
                alert("Greska pri unosu takmicenja!");
            }
        });
    })
}

var takmicari;
function ucitajTakmicare() {
    $.ajax({
        url: 'http://localhost:8084/tenis/rest/takmicar',
        dataType: 'json',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': getCookie('token')
        },
        success: function (response) {
            takmicari = response;
            napuniTabeluTakmicara();
        }

    });
}
function napuniTabeluTakmicara() {
    if (typeof takmicari !== "undefined") {
        var table = document.getElementById('tabelaTakmicari');
        var table_body = document.createElement('TBODY');
        table.border = '1';
        table.appendChild(table_body);
        var tHead = document.createElement('THEAD');
        var arrayHeader = ["Ime", "Prezime", "Opis", "Broj pobeda"];
        for (var i = 0; i < arrayHeader.length; i++) {
            tHead.appendChild(document.createElement("TH")).appendChild(document.createTextNode(arrayHeader[i]));
        }

        for (var x = 0; x < takmicari.length; x++) {
            var tr = document.createElement('TR');
            table_body.appendChild(tr);
            for (var j = 0; j < 4; j++) {
                var td = document.createElement('TD');
                td.width = '50';
                switch (j) {
                    case 0:
                        td.id = "td_id";
                        td.appendChild(document.createTextNode(takmicari[x].ime));
                        break;
                    case 1:
                        td.appendChild(document.createTextNode(takmicari[x].prezime));
                        break;
                    case 2:
                        td.appendChild(document.createTextNode(takmicari[x].opis));
                        break;
                    case 3:
                        td.appendChild(document.createTextNode(takmicari[x].brojPobeda));
                        break;
                    default:
                }
                tr.appendChild(td);
            }
        }
        table.appendChild(tHead);
    }
}

function sl() {
    var list = $('#slct');
    list.append(' <a href="#" data-toggle="dropdown" class="dropdown-toggle">Lige <b class="caret"></b></a>');
    list.append('<ul class="dropdown-menu">')
    for (var i = 0; i < lige.length; i++) {
        // Create the list item:
        var item = document.createElement('li');

        // Set its contents:
        item.appendChild(document.createTextNode(lige[i].naziv));

        // Add it to the list:


        list.append(item);
    }

    list.append('  </ul>')

}

$(document).on('click', '[id^=' + 'DDD' + "]", function () {
            var id = jQuery(this).attr("id");
            var niz = id.split('DDD');
            var id1 = niz[1];

            var r = confirm("Da li ste sigurni?");
                if (r === true) {
                    $.ajax({
                        url: baseUrlRest + "takmicenje/" + id1,
                        method: 'DELETE',
                        headers: {
                            'Content-Type': 'application/json',
                            'Authorization': getCookie('token')
                        },
                        success: function (response) {
                            refreshTakmicenja();
                            alert("Usprešno ste obrisali takmicenje!");
                        },
                        error: function (response) {
                            alert("Greska pri brisanju takmicenja...")
                        }
                    });
                }
            
});

function refreshTakmicenja() {
    ucitajTakmicenja();
    var id = 0;
    id = window.setInterval(ucitajTakmicenja(), 100);
    window.clearInterval(id);
}

$(function(){
    
    ucitajTakmicenja();
    
    function napuniComboBoxTakmicenja(takmicenja){
    
    var options = $("#selectTakmicenja");
$.each(takmicenja, function() {
    options.append($("<option />").val(this.takmicenjeID).text(this.naziv));
});
    
}

function ucitajTakmicenja(){
    $.ajax({
        url: 'http://localhost:8084/tenis/rest/takmicenje',
        dataType: 'json',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': getCookie('token')
        },
        success: function (response) {
            napuniComboBoxTakmicenja(response);
        },
        error: function(res) {
            console.log(res);
        }
    });
}


});