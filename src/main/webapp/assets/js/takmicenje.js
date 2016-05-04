var currentId;
var takmicari;

var dialog, form;

$(function(){
    
    currentId = location.search.split('=')[1];
    // load page
    ucitajTakmicenje();  
    ucitajModal();
});

$('#btnBack').click(function(){
    window.location.href = baseUrl + 'pocetna.html';
})


function ucitajTakmicenje(){
    
   $.ajax({
        url: 'http://localhost:8084/tenis/rest/takmicenje?id=' + currentId,
//        url: 'http://localhost:8084/tenis/rest/takmicenje',        
        dataType: 'json',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': getCookie('token')
        },
        success: function (response) {
            napuniStranicu(response[0]);
        },
        error: function(res) {
            console.log(res);
        }
      });
};

function ucitajLige() {
    $.ajax({
        url: 'http://localhost:8084/tenis/rest/liga?id=' + currentId,
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

function ucitajTakmicare() {
    $.ajax({
        url: 'http://localhost:8084/tenis/rest/takmicar?liga=' + currentId,
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

function napuniStranicu(takmicenjeJson){
    console.log(takmicenjeJson);
    $('#takmicenjeNaslov').text(takmicenjeJson.naziv);
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
    

function ucitajModal(){

    dialog = $("#dialog-dodavanje-meca").dialog({
    autoOpen: false,
    height: 450,
    width: 500,
    modal: true,
        buttons: {
            "Dodaj mec": dodajMec,
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
        dodajLigu();
    });
}

function dodajMec(){
    alert('1');
}

$('#btnUnosMeca').click(function (){
    dialog.dialog("open");
});