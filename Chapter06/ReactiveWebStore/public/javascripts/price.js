/**
 * This functions loads the price in the HTML component.
 */
function loadPrice(doc){
	jQuery.get( "http://localhost:9000/rnd/rxbat", function( response ) { 
		doc.getElementById("price").value = parseFloat(response)
	}).fail(function(e) {
	    alert('Wops! We was not able to call http://localhost:9000/rnd/rxba. Error: ' + e.statusText); 
	});
}