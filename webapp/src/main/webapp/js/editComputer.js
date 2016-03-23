$(document).ready(function() { 
	$.validator.addMethod("dateFormat",
			function(value, element) {
		if (element.value==""){
			return true;
		}
		return !!messageFormatDate.test(value);
	},
	messageDate);

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
	messageDiscontinuedBeforeIntroduced);
	
	$.validator.setDefaults({
		highlight: function(element) {
			$(element).closest('.form-group').addClass('has-error').removeClass('has-success');
		},
		unhighlight: function(element) {
			$(element).closest('.form-group').removeClass('has-error').addClass('has-success');
		},
	});
	
	$('#formEdit')
	.validate({
		rules : {
			name : {
				required : true
			},
			introduced : {
				dateFormat: true
			},
			discontinued : {
				dateFormat: true,
				datesGreater : true
			},
			id: {
				required :true,
				number:true
			}
		}
	});
});
