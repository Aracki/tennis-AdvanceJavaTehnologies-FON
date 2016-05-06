
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
        var arrayHeader = ["Naziv lige", "Broj takmičraa", "Naziv takmičenja"];
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
    height: 450,
    width: 500,
    modal: true,
    buttons: {
        "Dodaj ligu": dodajLigu,
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

function dodajLigu() {
    
      var valid = true;
 
      var nazivLige = $('#nazivLige').val();
      var brTakmicara = $('#brojTakmicara').val();
      var takmicenjeId = $('#selectTakmicenja').val();
 
      if ( valid ) {
        
        var liga = {
            naziv: nazivLige,
            brojTakmicara: brTakmicara,
            takmicenje: {
                takmicenjeID: takmicenjeId
            }
        };

        $.ajax({
            url: baseUrlRest + 'liga',
            method: "POST",
            data: JSON.stringify(liga),
            dataType: 'text',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': getCookie('token')
            },
            success: function (response) {
                alert("Uspesno ste kreirali ligu!");
                window.location.href = baseUrl + "lige.html"
            },
            error: function (response) {
                alert("Greska pri unosu lige!");
            }
        });
      }
      return valid;
}

    $('#btnUnosLige').click(function(){
        dialog.dialog("open");
    });

});

  function checkLength( o, n, min, max ) {
      if ( o.val().length > max || o.val().length < min ) {
        o.addClass( "ui-state-error" );
        updateTips( "Length of " + n + " must be between " +
          min + " and " + max + "." );
        return false;
      } else {
        return true;
      }
    }



