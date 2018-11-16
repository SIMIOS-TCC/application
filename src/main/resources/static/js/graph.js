window.onload = function(){
	var mapping = $('#graphDiv').data('mapping');
	
	mapping.forEach(function(simio) {
		drawSimio(simio);
	});
};

var drawSimio = function(simio) {
	var point = simio.position.point;
	
	var canvas = document.getElementById("graphCanvas");
	var context = canvas.getContext("2d");
	
	var image = document.getElementById("icon-simio");
	
	var originX = canvas.width/2;
	var originY = canvas.height/2;
	
	//*10 para correção da escala de pixels - corrigir
	var x = originX + point.x*10;
	var y = originY - point.y*10;
	
	context.beginPath();
	context.moveTo(originX, originY);
	context.lineTo(x, y);
	context.strokeStyle = '#cccccc';
	context.stroke();
	
	context.drawImage(image, x-20, y-20, 40 ,40);
	
	var options = { minimumSignificantDigits : 2, maximumSignificantDigits : 2 };
	var distance = new Intl.NumberFormat(options).format(getDistanceToOrigin(originX, originY, x, y)/10);
	context.font = "10px Arial";
	context.fillText(simio.name + " " + point.x + ", " + point.y, x+20, y-20); //escala
}

var getDistanceToOrigin = function(originX, originY, x, y) {
	var deltaX = Math.abs(originX - x);
	var deltaY = Math.abs(originY - y);
	return Math.sqrt(Math.pow(deltaX,2) + Math.pow(deltaY,2));
}