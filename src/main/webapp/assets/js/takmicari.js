$(function(){
    ucitajLige();
    ucitajMesta();
    ucitajModal();
});

var ligaId = null;
var takmicenjeID;

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
        
        var options = $("#selectMesto1");
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

$('#selectTakmicenja').on('change', function (e) {
    var optionSelected = $("option:selected", this);
    takmicenjeID = this.value;
   
    ucitajLige(takmicenjeID);
});

$('#selectLige').on('change', function (e) {
    var optionSelected = $("option:selected", this);
    ligaId = this.value;
});

var dialog, form;

$(function() {


dialog = $( "#dialog-dodavanje-takmicara" ).dialog({
    autoOpen: false,
    height: 450,
    width: 500,
    modal: true,
    buttons: {
        "Dodaj takmicara": dodajTakmicara,
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

function dodajTakmicara() {
    
    // TODO: DODATI u path /{ligaid}
    // ... i kreirati takmicara postom za tu ligu (svaka liga max 10 npr.)
    
      var valid = true;
 
      var ime = $('#ime1').val();
      var prezime = $('#prezime1').val();
      var opis = $('#opis1').val();
      var mesto = $('#selectMesto1').val();

      if ( valid ) {
        var takmicar = {
            ime:ime,
            prezime: prezime,
            opis: opis,
            mesto: {
                ptt: mesto
            }
        };

        $.ajax({
            url: baseUrlRest + 'takmicar/' + $('#selectLige').val(),
            method: "POST",
            data: JSON.stringify(takmicar),
            dataType: 'text',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': getCookie('token')
            },
            success: function (response) {
                alert("Uspesno ste kreirali takmicara!");
                window.location.href = baseUrl + "takmicari.html";
            },
            error: function (response) {
                alert("Greska pri unosu takmicara!");
            }
        });
      }
      return valid;
}

    $('#btnUnosTakmicara').click(function(){
        dialog.dialog("open");
    });
});

/// 777
$('#btnUnosTakmicara').click(function(){
        
        if (ligaId != null) {
            napuniRivale();
            dialog.dialog("open");
        }
    });

function napuniRivale(){
    var gost = $('slctGost');
    var domacin = $('slctDomacin');
    
    $.each(takmicari, function() {
        gost
            .append($('<li>')
            .append($("<a>")
            .text(this.ime)));
   
        domacin
            .append($('<li>')
            .append($("<a>")
            .text(this.ime)));
    });   
}