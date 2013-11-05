
// don't change this
var FONTSIZEDEFAULT = 0;

/**
 *	Change the font size from an element.
 *	@param element id from the element to be changed.
 *	@param offset how much the font will be increased. Use 0 to return to the default;
 *	@param event event that triggered this funciont. (Optional)
 * */
function fontSize (element, offset, e) {
	var oldSize;

        var keynum = 0;
        
        if (e == undefined) {
            keynum = 13;
        } else if(window.event) { // IE
            keynum = e.keyCode
        } else if(e.which) { // Netscape/Firefox/Opera
            keynum = e.which
        }
        
        if (keynum == 13 || keynum == 32) {
            // caso nao tenha um tamanho definido, coloca 10 com padrao
            try {
                 oldSize = parseInt($("#" + element).css("font-size"));
            } catch (err) {
                 oldSize = 16;
            }

            // define o tamanho padrao
            if (FONTSIZEDEFAULT == 0) FONTSIZEDEFAULT = oldSize;

            // define o novo tamanho
            var newSize;
            if (offset != 0) newSize = oldSize + offset;
            else {
                    newSize = FONTSIZEDEFAULT;
            }

            setFontSizeAbsolute(element, newSize);
            $.cookie("FONTSIZEDEFAULT", FONTSIZEDEFAULT, { path: '/' , expires: 7});
        }
}

/**
*	Change the font size from an element.
*	@param element id from the element to be changed
*	@param newSize new absolute size of the font.
*/
function setFontSizeAbsolute (element, newSize) {
	$("#" + element).css("font-size", newSize + "px");
	
	// define os cookies para serem acessiveis em todo o servidor, e que durem por sete dias seguidos.
	$.cookie("fontSizeElement", element, { path: '/' , expires: 7});
	$.cookie("fontSizeSize", newSize, { path: '/' , expires: 7});
}

/**
 * Change the page stylesheet.
 * @param stylesheet filename of the new stylesheet to be used.
 * 
 * This script will change the style with title "Document Style" for the one defined by "stylesheet".
 * */
function changeStyle (stylesheet) {
	var style = $('link[@rel*=style][title="Document Style"]');
	style.attr("href", stylesheet);
	
	// define os cookies para serem acessiveis em todo o servidor, e que durem por sete dias seguidos.
	$.cookie("cangeStyleStylesheet", stylesheet, { path: '/' , expires: 7});
}

/**
* Loads the default settings
*/
$(window).load(function () {
  var fontSizeElement = $.cookie("fontSizeElement");
  var fontSizeSize = $.cookie("fontSizeSize");
  
  if (fontSizeElement != null && fontSizeSize != null) {
	FONTSIZEDEFAULT = $.cookie("FONTSIZEDEFAULT");
	setFontSizeAbsolute(fontSizeElement, parseInt(fontSizeSize));
  }
  
  var changeStyleStylesheet = $.cookie("cangeStyleStylesheet");
  if (changeStyleStylesheet != null) {
	changeStyle(changeStyleStylesheet);
  }
});
