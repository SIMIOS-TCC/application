$('#confirmExclusionModal').on('show.bs.modal', function(event) {
	var button = $(event.relatedTarget);
	
	var simioId = button.data('id');
	var simioName = button.data('name');
	
	var modal = $(this);
	var form = modal.find('form');
	var action = form.data('url-base');
	if (!action.endsWith('/')) {
		action += '/';
	}
	form.attr('action', action + simioId);
	
	modal.find('.modal-header h4').html('Are you sure you want to delete simio <strong>' + simioName + '</strong>?');
});

$('.js-toggle-active').on('click', function(event){
	event.preventDefault();
	var button = $(event.currentTarget);
	var urlReceber = button.attr('href');
	
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	
	var response = $.ajax({
		url: urlReceber,
		type: 'PUT',
		beforeSend: function(xhr) { xhr.setRequestHeader(header, token); }
	});
	
	response.done(function(data) {
		var id = button.data('id');
		if(data == "false") {
			$('[data-rule=' + id + ']').html('<span class="label label-danger">Inactive</span>');
		} else {
			$('[data-rule=' + id + ']').html('<span class="label label-success">Active</span>');
		}
	});
	
	response.fail(function(e) {
		console.log(e);
	});
});

$(function(){
	$('[rel="tooltip"]').tooltip();
});