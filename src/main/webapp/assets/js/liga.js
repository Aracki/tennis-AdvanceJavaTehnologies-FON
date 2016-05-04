var currentId;
var takmicari;

$(function(){
    
    currentId = location.search.split('=')[1];
    // load page
    ucitajLigu();    
    ucitajTakmicare();
});

$('#btnBack').click(function(){
    window.location.href = baseUrl + 'pocetna.html';
})


function ucitajLigu(){
    
   $.ajax({
        url: 'http://localhost:8084/tenis/rest/liga?id=' + currentId,
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

function napuniStranicu(ligaJson){
    console.log(ligaJson);
    $('#ligaNaslov').text(ligaJson.naziv);
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