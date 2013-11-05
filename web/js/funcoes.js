//function toogleStyle() {
//	var checkbox = document.getElementById("mudaTemaCheck");
//	if (checkbox.checked) {
//		changeStyle('themes/black/style.css');
//	} else {
//		changeStyle('themes/standard/style.css');
//	}
//}

function clearInput (input) {
	if (input.value == input.defaultValue) {
		input.value = "";
	} else if (input.value == "") {
		input.value = input.defaultValue;
	}
}
