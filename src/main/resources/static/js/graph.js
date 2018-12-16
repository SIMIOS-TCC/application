var mapping = $('#graphDiv').data('mapping');
var aps = $('#graphDiv').data('aps');
var referencePoint = $('#graphDiv').data('reference');
var scale = $('#graphDiv').data('scale');
var id = new URL(window.location.href).searchParams.get("id");

var canvas = document.getElementById("graphCanvas");
var context = canvas.getContext("2d");

var updateGraph = function(){
	var token = $("input[name='_csrf']").val();
	var header = $("input[name='_csrf_header']").val();
	
	$.ajax({
        type: "POST",
        contentType : 'application/json; charset=utf-8',
        url: "/simio/update-graph",
        beforeSend: function(xhr) { xhr.setRequestHeader(header, token); },
        data: id,
        success: function (json) {
        	var data = JSON.parse(json);
        	mapping = data["mapping"];
        	referencePoint = data["reference"];
        	scale = data["scale"];
        	aps = data["aps"];
        	drawGraph();
        }
    });
	
    //setTimeout(updateGraph, 2000);
};

window.onload = function(){
	drawGraph();
	updateGraph();
};

$(window).on('resize',function(){
	drawGraph();
});

var drawGraph = function() {
	context.clearRect(0, 0, canvas.width, canvas.height);
	
	var canvasFrame = $('#canvasDiv')[0];
	canvas.width = canvasFrame.offsetWidth;
	canvas.height = window.innerHeight*0.5;
	
	aps.forEach(function(ap) {
		drawAP(ap, referencePoint, scale);
	});
	
	mapping.forEach(function(simio) {
		drawSimio(simio, referencePoint, scale);
	});
}

var drawSimio = function(simio, referencePoint, scale) {
	var point = simio.position.point;
	var timestamp = simio.position.timestamp;
	
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
	
	var distance = (getDistanceToOrigin(originX, originY, x, y)/10).toFixed(2);
	context.font = "10px Arial";
	context.fillText(simio.name + " " + point.x + "," + point.y, x, y-23);
	context.fillText(timestamp, x, y-12);
}

var drawAP = function(ap, referencePoint, scale) {
	var image = document.getElementById("ap-icon");
	
	var originX = canvas.width/2;
	var originY = canvas.height/2;
	
	var scale = originY/scale;
	
	//*10 para correção da escala de pixels - corrigir
	var x = originX + (ap.x - referencePoint.x)*scale;
	var y = originY - (ap.y - referencePoint.y)*scale;
	
	context.beginPath();
	context.moveTo(originX, originY);
	
	context.drawImage(image, x-10, y-10, 15, 15);
	
	var distance = (getDistanceToOrigin(originX, originY, x, y)/10).toFixed(2);
	context.font = "10px Arial";
	context.fillText("AP" + ap.id + " " + ap.x + "," + ap.y, x, y-12);
}

var getDistanceToOrigin = function(originX, originY, x, y) {
	var deltaX = Math.abs(originX - x);
	var deltaY = Math.abs(originY - y);
	return Math.sqrt(Math.pow(deltaX,2) + Math.pow(deltaY,2));
}