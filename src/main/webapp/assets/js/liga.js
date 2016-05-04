var currentId;

$(function(){
    
    currentId = location.search.split('=')[1];
    // load page
    ucitajLigu();    
});


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

function napuniStranicu(ligaJson){
    console.log(ligaJson);
    $('#ligaNaslov').text(ligaJson.naziv);
}