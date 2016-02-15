$(document).ready(function() { 
	$.validator.addMethod("dateFormat",
			function(value, element) {
		if (element.value==""){
			return true;
		}
		if(/^[1-2][0-9][0-9][0-9]-[0-2][0-9]-[0-3][0-9] [0-2][0-9]:[0-5][0-9]:[0-5][0-9]$/gm.test(value)){
			return true;
		} else {
			return false;
		}
	},
	"Please enter a date in the format yyyy-mm-dd hh:mm:ss.");

	
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
   			}
		}
	});
});
function checkIntroduced(document) {
	element = document.getElementById("introduced")
	re = /^[1-2][0-9][0-9][0-9]-[0-2][0-9]-[0-3][0-9] [0-2][0-9]:[0-5][0-9]:[0-5][0-9]$/

		if (element.value == "" || element.value.match(re)) {
			$("#errorIntroduced").style.visibility = "hidden";
			element.style.borderColor = "#CCC";
			return true;
		} else {
			$("#errorIntroduced").style.visibility = "visible";
			return false;
		}
}