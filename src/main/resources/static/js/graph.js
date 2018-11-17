window.onload = function(){
	drawGraph();
};

$(window).on('resize',function(){
	drawGraph();
});

var drawGraph = function() {
	var canvas = document.getElementById("graphCanvas");
	var canvasFrame = $('#canvasDiv')[0];
	canvas.width = canvasFrame.offsetWidth;
	canvas.height = window.innerHeight*0.5;
	
	var mapping = $('#graphDiv').data('mapping');
	var referencePoint = $('#graphDiv').data('reference');
	var scale = $('#graphDiv').data('scale');
	
	mapping.forEach(function(simio) {
		drawSimio(simio, referencePoint, scale);
	});
}

var drawSimio = function(simio, referencePoint, scale) {
	var point = simio.position.point;
	
	var canvas = document.getElementById("graphCanvas");
	var context = canvas.getContext("2d");
	
	var image = document.getElementById("icon-simio");
	
	var originX = canvas.width/2;
	var originY = canvas.height/2;
	
	var scale = originY/scale;
	
	//*10 para correção da escala de pixels - corrigir
	var x = originX + (point.x - referencePoint.x)*scale;
	var y = originY - (point.y - referencePoint.y)*scale;
	
	context.beginPath();
	context.moveTo(originX, originY);
//	context.lineTo(x, y);
//	context.strokeStyle = '#cccccc';
//	context.stroke();
	
	context.drawImage(image, x-10, y-10, 20 ,20);
	
	var options = { minimumSignificantDigits : 2, maximumSignificantDigits : 2 };
	var distance = new Intl.NumberFormat(options).format(getDistanceToOrigin(originX, originY, x, y)/10);
	context.font = "10px Arial";
	context.fillText(simio.name + " " + point.x + "," + point.y, x, y-12); //escala
}

var getDistanceToOrigin = function(originX, originY, x, y) {
	var deltaX = Math.abs(originX - x);
	var deltaY = Math.abs(originY - y);
	return Math.sqrt(Math.pow(deltaX,2) + Math.pow(deltaY,2));
}