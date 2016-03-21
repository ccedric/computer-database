$(document).ready(function() { 
	$.validator.setDefaults({
		highlight: function(element) {
			$(element).closest('.form-group').addClass('has-error').removeClass('has-success');
		},
		unhighlight: function(element) {
			$(element).closest('.form-group').removeClass('has-error').addClass('has-success');
		},
	});
	
	$('#loginForm')
	.validate({
		rules : {
			username : {
				required : true,
				minlength : 3,
				maxlength : 45
			},
			password : {
				required: true,
				minlength : 3,
				maxlength : 60
			}
		},
		messages: {
			username : {
				required : messageRequiredUsername,
				minlength : messageMinUsername,
				maxlength : messageMaxUsername
			},
			password : {
				required : messageRequiredPassword,
				minlength : messageMinPassword,
				maxlength : messageMaxPassword
			}
		}
	});

});