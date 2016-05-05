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
        dataType: 'json',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': getCookie('token')
        },
        success: function (response) {
            napuniStranicu(response[0]);
            ucitajLige(response[0].takmicenjeID);
        },
        error: function(res) {
            console.log(res);
        }
      });
};

var takmicari;

function ucitajLige(idTakmicenja) {
    $.ajax({
        url: 'http://localhost:8084/tenis/rest/liga?takmicenje=' + idTakmicenja,
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

function ucitajTakmicare(id) {
    $.ajax({
        url: 'http://localhost:8084/tenis/rest/takmicar?liga=' + id,
        dataType: 'json',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': getCookie('token')
        },
        success: function (response) {
            takmicari = response;
            napuniTabeluTakmicara();
            napuniModalRivali();
        }
    });
}

function napuniStranicu(takmicenjeJson){
    console.log(takmicenjeJson);
    $('#takmicenjeNaslov').text(takmicenjeJson.naziv);
}

function napuniTabeluTakmicara() {
        
    var table = document.getElementById('tabelaTakmicari');
    table.innerHTML = "";
    
    if (typeof takmicari !== "undefined") {
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
    });
}

function ucitajLige(takmicenjeID){
    
    if(takmicenjeID){
        $.ajax({
        url: 'http://localhost:8084/tenis/rest/liga?takmicenje=' + takmicenjeID,
        dataType: 'json',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': getCookie('token')
        },
        success: function (response) {
            napuniComboBoxLige(response);
        },
        error: function(res) {
                napuniComboBoxLige();
        }
    });
    } else {
        $.ajax({
        url: 'http://localhost:8084/tenis/rest/liga',
        dataType: 'json',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': getCookie('token')
        },
        success: function (response) {
            napuniComboBoxLige(response);
        },
        error: function(res) {
            console.log(res);
        }
      });
    }
}

function napuniComboBoxLige(lige){
    
    var options = $("#selectLige");
    options.find('option')
    .remove()
    .end();
    if(lige){
        $.each(lige, function() {
            options.append($("<option />").val(this.ligaID).text(this.naziv));
        });        
    } else {
        options.append($("<option />").val('').text(''));
    }
}

$('#selectLige').on('change', function (e) {
    var optionSelected = $("option:selected", this);
    ligaId = this.value;
    ucitajTakmicare(ligaId);
});

function dodajMec(){
    
    // post for inserting match
    
    var domacin = $('#slctDomacin').val();
    var gost = $('#slctGost').val();
    var rezultat = $('#rezultat').val();
    
    var mec = {
        rezultat: rezultat,
        takmicarDID: {
            takmicarID: domacin
        },
        takmicarGID: {
            takmicarID: gost
        }
    }
    
    $.ajax({
        type: "POST",
        url: baseUrlRest + "match",
        dataType: "json",
        data: JSON.stringify(mec),
        headers: {
                'Content-Type': 'application/json',
                'Authorization': getCookie('token')
        },
        success: function (response) {
//            window.location.href = "pocetna.html";
        },
        error: function (response) {
            alert("Niste se uspesno uneli mec!");
        }
    });
}

function napuniModalRivali(){
    var options1 = $("#slctGost");
    var options2 = $("#slctDomacin");

    options1.find('option')
    .remove()
    .end();
    options2.find('option')
    .remove()
    .end();
    
    if(takmicari){
        $.each(takmicari, function() {
            options1.append($("<option />").val(this.takmicarID).text(this.ime + " " + this.prezime));
            options2.append($("<option />").val(this.takmicarID).text(this.ime + " " + this.prezime));
       });        
    }
}

$('#btnUnosMeca').click(function (){
        
    dialog.dialog("open");
});