/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$('#btnUnosLige').click(function (){
    
    
    
    
});

var lige;
function ucitajLige() {
    $.ajax({
        url: 'http://localhost:8084/tenis/rest/liga',
        dataType: 'json',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': getCookie('token')
        },
        success: function (response) {
            lige = response;
            napuniTabeluLiga();
        }

    });
}
function napuniTabeluLiga() {
    if (typeof lige !== "undefined") {
        var table = document.getElementById('tabelaLiga');
        var table_body = document.createElement('TBODY');
        table.border = '1';
        table.appendChild(table_body);
        var tHead = document.createElement('THEAD');
        var arrayHeader = ["Naziv", "Broj takmicara", "Takmicenje"];
        for (var i = 0; i < arrayHeader.length; i++) {
            tHead.appendChild(document.createElement("TH")).appendChild(document.createTextNode(arrayHeader[i]));
        }

        for (var x = 0; x < lige.length; x++) {
            var tr = document.createElement('TR');
            table_body.appendChild(tr);
            for (var j = 0; j <= 2; j++) {
                var td = document.createElement('TD');
                td.width = '50';
                switch (j) {
                    case 0:
                        td.appendChild(document.createTextNode(lige[x].naziv));
                        break;
                    case 1:
                        td.appendChild(document.createTextNode(lige[x].brojTakmicara));
                        break;
                    case 2:
                        td.appendChild(document.createTextNode(lige[x].takmicenje.naziv));
                        break;
                    default:
                }
                tr.appendChild(td);
            }
        }
        table.appendChild(tHead);
    }
}


$(function() {

var dialog, form;

dialog = $( "#dialog-form" ).dialog({
    autoOpen: false,
    height: 700,
    width: 510,
    modal: true,
    buttons: {
        "Dodaj stavku": dodajLigu(),
        Cancel: function() {
            dialog.dialog( "close" );
        }
    },
    close: function() {
        form[ 0 ].reset();
    }
});

form = dialog.find( "form" ).on( "submit", function( event ) {
    event.preventDefault();
    dodajLigu()();
});

function dodajLigu() {
    var valid = true;   
    
    return valid;
}

    $('#btnUnosLige').click(function(){

//        listaStavkiJson = [];
//
//        var jsonS = {
//            "datum" : todayDate(),
//            "jmbg" : getCookie('dobavljac')
//        };
//        var json = JSON.stringify(jsonS);
//
//        $.ajax({
//            type: "POST",
//            url: getCookie("basicURL") + "dnevnaberba",
//            data: json,
//            headers: {
//                'Content-Type': 'application/json'
//            },
//            success: function (response) {
//                dnevnaBerbaId = response[0].generated_key;
//            },
//            error: function (response) {
//                refresh();
//            }
//        });



        dialog.dialog("open");
    });

});

