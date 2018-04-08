$('#confirmExclusionModal').on('show.bs.modal', function(event) {
	var button = $(event.relatedTarget);
	
	var simioId = button.data('id');
	
	var modal = $(this);
	var form = modal.find('form');
	var action = form.data('url-base');
	if (!action.endsWith('/')) {
		action += '/';
	}
	form.attr('action', action + simioId);
	
	//modal.find('.modal-header h4').html('Are you sure you want to delete simio <strong>' + simioName + '</strong>?');
});