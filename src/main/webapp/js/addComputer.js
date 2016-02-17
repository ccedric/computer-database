$(document).ready(function() { 
	$.validator.addMethod("dateFormat",
			function(value, element) {
		if (element.value==""){
			return true;
		}
		if(/^[1-2][0-9][0-9][0-9]-[0-2][0-9]-[0-3][0-9]$/gm.test(value)){
			return true;
		} else {
			return false;
		}
	},
	"Please enter a date in the format yyyy-mm-dd hh:mm.");

	$.validator.addMethod("datesGreater",
			function(value, element) {
		if ($("#discontinued").val()==""){
			return true;
		} else{
			if ($("#discontinued").val()==""){
				return false;
			}
		}
		return ($("#discontinued").val()>$("#introduced").val());
	},
	"Discontinued date must be greater than introduced.");
	
	$.validator.setDefaults({
		highlight: function(element) {
			$(element).closest('.form-group').addClass('has-error').removeClass('has-success');
		},
		unhighlight: function(element) {
			$(element).closest('.form-group').removeClass('has-error').addClass('has-success');
		},
	});
	
	$('#formAdd')
	.validate({
		rules : {
			computerName : {
				required : true
			},
			introduced : {
				dateFormat: true
			},
			discontinued : {
				dateFormat: true,
				datesGreater : true
			}
		}
	});
});
