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
            window.location.href = "pocetna.html";
            document.cookie = "token=" + response.token;
        },
        error: function (response) {
            alert("Niste se uspešno ulogovali!");
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
        
        var tHead = document.createElement('THEAD');
        tHead.border = '2';
        var arrayHeader = ["NAZIV", "DATUM POČETKA", "TIP TAKMIČENJA", ""];
        var tr = tHead.appendChild(document.createElement("TR"));
        for (var i = 0; i < arrayHeader.length; i++) {
            tr.appendChild(document.createElement("TH")).appendChild(document.createTextNode(arrayHeader[i]));
        }
        table.appendChild(tHead);

        var table_body = document.createElement('TBODY');
        table.border = '2';
        table.appendChild(table_body);
        
        for (var x = 0; x < takmicenja.length; x++) {
            var tr = document.createElement('TR');
            table_body.appendChild(tr);
            for (var j = 0; j <= 3; j++) {

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
                        b.className = "btn btn-danger";
                        b.appendChild(document.createTextNode("Obriši"));
                        b.id = "DDD" + takmicenja[x].takmicenjeID;
                        td.appendChild(b);
                        break;
                    default:
                }
                tr.appendChild(td);
            }
        }
    }
}

function ucitajLigeZaComboBox(){
       $.ajax({
        url: 'http://localhost:8084/tenis/rest/liga',
        dataType: 'json',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': getCookie('token')
        },
        success: function (response) {
            napuniLigeDropdown(response);
        },
        error: function(res) {
            console.log(res);
        }
      });
}

function ucitajTakmicenjaZaDropdown(takmicenjaJson){
       $.ajax({
        url: 'http://localhost:8084/tenis/rest/takmicenje',
        dataType: 'json',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': getCookie('token')
        },
        success: function (response) {
            napuniTakmicenjaDropdown(response);
        },
        error: function(res) {
            console.log(res);
        }
      });
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

$('#btnUnosTakmicenje').click(function () {
    window.location.href = "unosTakmicenja.html";
});

function ubaciTipove() {
    $.ajax({
        url: 'http://localhost:8084/tenis/rest/tipTakmicenja',
        dataType: 'json',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': getCookie('token')
        },
        success: function (response) {
            for (var i = 0; i < response.length; i++) {
                var option = document.createElement("option");
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

        var naziv = $('#naziv').val();

        var str = {
            naziv: naziv,
            datumPocetka: new Date(),
            tiptakmicenja: {
                tiptakmicenjaID: $('#ido').val(),
            }
        };
        
        if (naziv === "") {
            alert("Niste ispravno uneli podatke!");
            return;
        }

        $.ajax({
            url: 'http://localhost:8084/tenis/rest/takmicenje',
            method: "POST",
            data: JSON.stringify(str),
            headers: {
                'Content-Type': 'application/json',
                'Authorization': getCookie('token')
            },
            success: function (response) {
                alert("Uspešno ste kreirali takmičenje!");
                window.location.href = baseUrl + "pocetna.html"
            },
            error: function (response) {
                alert("Greška pri unosu takmičenja!");
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
    
    var table = document.getElementById('tabelaTakmicari');
    table.innerHTML = "";
    
    if (typeof takmicari !== "undefined") {
       
        var tHead = document.createElement('THEAD');
        var arrayHeader = ["Ime", "Prezime", "Opis", "Broj pobeda"];
        var tr = tHead.appendChild(document.createElement('TR'));
        for (var i = 0; i < arrayHeader.length; i++) {
            tr.appendChild(document.createElement("TH")).appendChild(document.createTextNode(arrayHeader[i]));
        }
        table.appendChild(tHead);
        
         var table_body = document.createElement('TBODY');
        table.border = '2';
        table.appendChild(table_body);

        for (var x = 0; x < takmicari.length; x++) {
            var tr = document.createElement('TR');
            table_body.appendChild(tr);
            for (var j = 0; j <= 5; j++) {
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
                    case 4:
                        var b = document.createElement('BUTTON');
                        b.className = "btn btn-info";
                        b.appendChild(document.createTextNode("Izmeni"));
                        b.id = "III" + takmicari[x].takmicarID;
                        td.appendChild(b);
                        break;
                    case 5:
                        var b = document.createElement('BUTTON');
                        b.className = "btn btn-danger";
                        b.appendChild(document.createTextNode("Obrisi"));
                        b.id = "XXX" + takmicari[x].takmicarID;
                        td.appendChild(b);
                        break;
                    default:
                }
                tr.appendChild(td);
            }
        }
    }
}

// function for deleting takmicenje
$(document).on('click', '[id^=' + 'DDD' + "]", function () {
            var id = jQuery(this).attr("id");
            var niz = id.split('DDD');
            var id1 = niz[1];

            var r = confirm("Da li ste sigurni da zelite da obrisete takmicenje?");
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
                            alert("Greška pri brisanju takmicenja...")
                        }
                    });
                }            
});

var dialogEditTakmicar, form;

$(function (){
    ucitajMesta();
    
    $(".search").keyup(function () {
        var searchTerm = $(".search").val();
        var searchSplit = searchTerm.replace(/ /g, "'):containsi('");

        $.extend($.expr[':'], {'containsi': function (elem, i, match, array) {
                return (elem.textContent || elem.innerText || '').toLowerCase().indexOf((match[3] || "").toLowerCase()) >= 0;
            }
        });

        $(".tblTakmicenja tbody tr").not(":containsi('" + searchSplit + "')").each(function (e) {
            $(this).attr('visible', 'false');
        });

        $(".tblTakmicenja tbody tr:containsi('" + searchSplit + "')").each(function (e) {
            $(this).attr('visible', 'true');
        });

        if (searchTerm === "") {
//            refresh();
        }
    });
});

function ucitajMesta(){
    $.ajax({
        url: baseUrlRest + "mesto",
        dataType: 'json',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': getCookie('token')
        },
        success: function (response) {
            napuniComboBoxMesto(response);
        },
        error: function(res) {
            console.log(res);
        }
    });
}

function napuniComboBoxMesto(mesta){

        var options = $("#selectMesto");
        options.find('option')
        .remove()
        .end();
        if(mesta){
            $.each(mesta, function() {
                options.append($("<option />").val(this.ptt).text(this.naziv));
            });        
        } else {
            options.append($("<option />").val('').text(''));
        }
    }

function ucitajModal(){

    dialogEditTakmicar = $("#dialog-edit-takmicara").dialog({
    autoOpen: false,
    height: 450,
    width: 500,
    modal: true,
        buttons: {
            "Sacuvaj izmene": sacuvajIzmeneTakmicara,
            Cancel: function() {
                dialogEditTakmicar.dialog( "close" );
            }
        },
        close: function() {
            form[ 0 ].reset();
        }
    }); 

    form = dialogEditTakmicar.find( "form" ).on( "submit", function( event ) {
        event.preventDefault();
    });
}

function sacuvajIzmeneTakmicara(){
    
    var ime = $('#ime').val();
    var prezime = $('#prezime').val();
    var opis = $('#opis').val();
    var ptt = $('#selectMesto').val();
    var ligaId = $('#selectLige').val();
    
    var takmicarJson = {
        ime: ime,
        prezime: prezime,
        opis: opis,
        takmicarID: idSelectedTakmicar,
        mesto: {
            ptt: ptt
        },
        liga: {
            ligaID: ligaId
        }
    }
    
    $.ajax({
            url: 'http://localhost:8084/tenis/rest/takmicar',
            method: "PUT",
            data: JSON.stringify(takmicarJson),
            headers: {
                'Content-Type': 'application/json',
                'Authorization': getCookie('token')
            },
            success: function (response) {
                alert("Uspešno ste izmenili takmičara!");
                window.location.href = baseUrl + "takmicari.html"
            },
            error: function (response) {
                alert("Greška pri izmeni takmičara!");
            }
        });
}

var idSelectedTakmicar;

// function for editing takmicar
$(document).on('click', '[id^=' + 'III' + "]", function () {
            var id = jQuery(this).attr("id");
            var niz = id.split('III');
            var id1 = niz[1];
            idSelectedTakmicar = id1;
            
            ucitajTakmicara(id1);
});

// function for deleting takmicar
$(document).on('click', '[id^=' + 'XXX' + "]", function () {
            var id = jQuery(this).attr("id");
            var niz = id.split('XXX');
            var id1 = niz[1];
            idSelectedTakmicar = id1;
            
            obrisiTakmicara(id1);
});

function ucitajTakmicara(id){
    $.ajax({
        url: 'http://localhost:8084/tenis/rest/takmicar?idTakmicara=' + id,
        dataType: 'json',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': getCookie('token')
        },
        success: function (response) {
            napuniPoljaEditTakmicara(response);
        }
    });
}


function obrisiTakmicara(id){
    $.ajax({
        url: 'http://localhost:8084/tenis/rest/takmicar?id=' + id,
        method: 'DELETE',
        headers: {
            'Authorization': getCookie('token')
        },
        success: function (response) {
            refreshTakmicara();
            alert("Uspešno ste obrisali takmičara!")
        }
    });
}

function napuniPoljaEditTakmicara(takmicarJson){
    $('#ime').val(takmicarJson.ime);
    $('#prezime').val(takmicarJson.prezime);
    $('#opis').val(takmicarJson.opis);
    $('#selectMesto').val(takmicarJson.mesto.ptt);
    $('#selectMesto').change();

    dialogEditTakmicar.dialog("open");
}

function refreshTakmicenja() {
    ucitajTakmicenja();
    var id = 0;
    id = window.setInterval(ucitajTakmicenja(), 100);
    window.clearInterval(id);
}

function refreshTakmicara() {
    ucitajTakmicare();
    var id = 0;
    id = window.setInterval(ucitajTakmicare(), 100);
    window.clearInterval(id);
}

$(function(){   
    ucitajTakmicenja();
    
    function napuniComboBoxTakmicenja(takmicenja){
    var options = $("#selectTakmicenja");
    
    $.each(takmicenja, function() {
        options
                .append('<option value=' + this.takmicenjeID + '>' + this.naziv + '</option>');
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
            ucitajTakmicenjaZaDropdown(response);
        },
        error: function(res) {
            console.log(res);
        }
    });
}
});

function napuniLigeDropdown(result){
    var options = $("#slct");
    $.each(result, function() {
        options
            .append($('<li>').append($("<a>").attr('href', baseUrl + 'liga.html?id=' + this.ligaID).text(this.naziv)));
    });
}

function napuniTakmicenjaDropdown(result){
    var options = $("#slctTakmicenja");
    $.each(result, function() {
        options
            .append($('<li>').append($("<a>").attr('href', baseUrl + 'takmicenje.html?id=' + this.takmicenjeID).text(this.naziv)));
    });
}