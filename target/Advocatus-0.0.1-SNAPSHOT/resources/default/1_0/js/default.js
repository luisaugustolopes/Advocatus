/**
 * 
 */
function handleSubmitRequest(xhr, status, args, dialogName, formName) {
	dialog = jQuery('#' + dialogName);
	if (args.validationFailed) {
		dialog.effect("shake", {
			times : 3
		}, 100);
	} else {
		clearForm(formName);
		newUserDialog.hide();
		userDialog.hide();
	}
}
function clearForm(formName) {
	jQuery('#' + formName).each(function() {
		this.reset();
	});
}

function hideDialog(args, dialogName) {
	if (!args.validationFailed) {
		// dialog = jQuery('#'+dialogName);
		// dialog.effect("shake", { times:3 }, 100);
		dialogName.hide();
	}
}


