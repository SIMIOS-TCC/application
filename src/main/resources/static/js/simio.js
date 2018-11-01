$('#confirmExclusionModal').on('show.bs.modal', function(event) {
	var button = $(event.relatedTarget);
	
	var id = button.data('id');
	
	var modal = $(this);
	var form = modal.find('form');
	var action = form.data('url-base');
	if (!action.endsWith('/')) {
		action += '/';
	}
	form.attr('action', action + id);
	
	var simioName = button.data('name');
	var title = "Delete <strong>" + simioName + "</strong> from the registers?";
	modal.find('.modal-title').html(title);
});

$('.js-toggle-active').on('click', function(event){
	event.preventDefault();
	var button = $(event.currentTarget);
	var urlBase = button.attr('href');
	
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	
	var response = $.ajax({
		url: urlBase,
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