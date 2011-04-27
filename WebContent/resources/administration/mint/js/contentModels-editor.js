var UTILS =  {
	getVocabulary: function(namespace) {
		var currentNamespace = namespace;
		this._currentVocabulary = null;
		var extractedVocabulary = null;
		var vocabulary = ENTANDO_MODEL_VOCABULARY;

		var vocabularyManager_Extract = function(namespace,vocabulary) {
				if (namespace[0] === "") { 
					namespace=Array.clone(namespace);
					namespace.shift(); 
				}
				var vocabularyFound = [];
				var found = null;
				var searchKey = null;
				if (namespace.length === 1){
					found = vocabulary; 
					if (namespace[0] != "/") {
						searchKey = namespace[0];
							searchKey = searchKey.replace(/\*/g,"\\\*");
							searchKey = searchKey.replace(/\./g,"\\\.");
							searchKey = searchKey.replace(/\?/g,"\\\?");
							searchKey = searchKey.replace(/\[/g,"\\\[");
							searchKey = searchKey.replace(/\]/g,"\\\]");
							searchKey = searchKey.replace(/\(/g,"\\\(");
							searchKey = searchKey.replace(/\)/g,"\\\)");
							searchKey = searchKey.replace(/\{/g,"\\\{");
							searchKey = searchKey.replace(/\}/g,"\\\}");
							searchKey = searchKey.replace(/\^/g,"\\\^");
							searchKey = searchKey.replace(/\$/g,"\\\$");
					}
				}
				else if (namespace.length > 1) {
					if (namespace[namespace.length-1] != "/") {
						searchKey = namespace[namespace.length-1];
							searchKey = searchKey.replace(/\|/g,"\\\|");
							searchKey = searchKey.replace(/\*/g,"\\\*");
							searchKey = searchKey.replace(/\./g,"\\\.");
							searchKey = searchKey.replace(/\?/g,"\\\?");
							searchKey = searchKey.replace(/\[/g,"\\\[");
							searchKey = searchKey.replace(/\]/g,"\\\]");
							searchKey = searchKey.replace(/\(/g,"\\\(");
							searchKey = searchKey.replace(/\)/g,"\\\)");
							searchKey = searchKey.replace(/\{/g,"\\\{");
							searchKey = searchKey.replace(/\}/g,"\\\}");
							searchKey = searchKey.replace(/\^/g,"\\\^");
							searchKey = searchKey.replace(/\$/g,"\\\$");
					}
					namespace=Array.clone(namespace);
					namespace.pop();
					var tempFound = vocabulary;
					for (var i=0;i<namespace.length;i++) {
						try {
							tempFound = tempFound[namespace[i]];
						}
						catch (e) {
							tempFound = null;
						}
					}
					found = tempFound;
				}
				if (null !== found) {
					if(typeOf(found)=="object") {
						Object.each(found,function(value,key){
							if (searchKey === null || key.test("^"+searchKey,"i")) {
								vocabularyFound.push(key);
							}
						});
					}
					else if(typeOf(found)=="array") {
						Array.each(found,function(item,index,object) {
							if(typeOf(item)=="string" || typeOf(item)=="number") {
								item = item.toString();
								if (item.length>0) {
									if (searchKey === null || item.test("^"+searchKey,"i")) {
										vocabularyFound.push(item.toString());
									}	
								}
							}
						});
					}
					vocabularyFound.sort();
				}
				return vocabularyFound;
			};

		extractedVocabulary = vocabularyManager_Extract(namespace, vocabulary);
		return extractedVocabulary;
	},
	namespaceParser: function(nameSpaceString,caretPosition) {
		if (nameSpaceString===undefined) { nameSpaceString=""; }
		if (typeOf(caretPosition) != "number") { caretPosition=nameSpaceString.length; }
		var namespace = [];
		var allowed  = ["$"];
		/* parser start */
		var positionStart = 0;
		var i = 0;
		for (i=caretPosition-1;i>0;--i) {
			var character = nameSpaceString.charAt(i);
			var previousCharacter = nameSpaceString.charAt(i+1);
			if (character===undefined) {
				break;
			}
			if (character=="." && previousCharacter==".") {
				positionStart = i+1+1; 
				break; 
			}
			var cursorJump = 0;
			var endsWithAllowed = allowed.some(function(item) {
				if (item.length==1) {
					return character==item;
				}
				else if (nameSpaceString.substring(i-item.length+1,i+1)==item) {
					//cursorJump = item.length+1;
					cursorJump = item.length-1;
					return true;
				}
				else if (nameSpaceString.substring(i,i+item.length)==item) {
					return true;
				}
			});
			if (cursorJump>0) {
				i = i-cursorJump;
				character=nameSpaceString[i];
				previousCharacter=nameSpaceString.charAt(i+1);
				continue;
			}
			if ( character!="." && !(/^\w$/.test(character) || endsWithAllowed ) ) {
				positionStart = i+1;
				if (previousCharacter!==undefined) {
					var jumpPrevious = 0;
					if (previousCharacter==".") {
						//if theres a dot ".", just move forward of 1 position and exit the loop.
						jumpPrevious = 1;
						positionStart = i+1+jumpPrevious;
						break;
					}
					var previousCharacterEndsWithAllowed = allowed.some(function(item) {
						if (item.length==1) {
							if (previousCharacter==item) {
								jumpPrevious=1;
								return true;	
							}
						} 
						//forward seek
							else if (nameSpaceString.substring(i,i+item.length) == item ) {
								jumpPrevious=item.length;
								return true;
							}
						//back seek
							else if (nameSpaceString.substring(i-item.length+1,i+1) == item) {
								jumpPrevious= (-(item.length));
								return true;
							}
					});
					if (!previousCharacterEndsWithAllowed && !/^\w$/.test(previousCharacter)) { 
						//here only allowed
						positionStart = i+1+jumpPrevious;
					} 
				}
				break;
			}
		}
		if(positionStart>caretPosition) {
			positionStart=caretPosition;
		}
		nameSpaceString = nameSpaceString.substring(positionStart,caretPosition).trim();
		if (nameSpaceString.length>0) {
			namespace=nameSpaceString.split(".");
			if (namespace[namespace.length-1]==="") {
				namespace[namespace.length-1] = "/";
			}
		}
		else {
			namespace=["/"];
		}
		/* parser end */
		return namespace;	
	},
	useItemSelected: function(token, text, namespace) {
		var returnValue = "";
		if (text!==undefined && token!== undefined && namespace!==undefined) {
			var textarea = token.string;
			var position = token.end
			var namespace = namespace[namespace.length-1];
			var completedText=null;
			if (namespace=="/") {
				completedText=text;
			}
			else {
				completedText = text.substring(namespace.length,text.length);
			}
			var adjustCaseText = text.substring(0,text.length-completedText.length);
			var textbefore = textarea.substring(0, position);
			textbefore = textbefore.substring(0,textbefore.length-adjustCaseText.length)+adjustCaseText;
			var textafter = textarea.substring(position);
			returnValue = textbefore + completedText + textafter;
		}
		return returnValue;
	}
};

/******************************************************/
window.addEvent("domready", function(){
////////////domready
if (contentModelsEditorActive) {

	// Minimal event-handling wrapper.
	function stopEvent(){
		/*
		if (this.preventDefault) {
			this.preventDefault();
			if (this.stopPropagation) {
				this.stopPropagation();
			}
		}
		else {
			this.returnValue = false;
			this.cancelBubble = true;
		}
		*/
		this.preventDefault();
	}
	
	function addStop(event){
		/*
		already done by mootools
		if (!event.stop) 
			event.stop = stopEvent;
		*/
		return event;
	}
	function connect(node, type, handler){
		function wrapHandler(event){
			handler(addStop(event || window.event));
		}
		if (typeof node.addEventListener == "function") 
			node.addEventListener(type, wrapHandler, false);
		else 
			node.attachEvent("on" + type, wrapHandler);
	}
	
	function forEach(arr, f){
		for (var i = 0, e = arr.length; i < e; ++i) 
			f(arr[i]);
	}
	
	var editor = CodeMirror.fromTextArea(document.id("newModel_contentShape"), {
		lineNumbers: true,
		mode: "xml",
		onKeyEvent: function(i, e){
			// Hook into ctrl-space
			if (e.keyCode == 32 && (e.ctrlKey || e.metaKey) && !e.altKey) {
				e.preventDefault(); //e.stop();
				return startComplete();
			}
		}
	});
	
	function startComplete(){
		// We want a single cursor position.
		if (editor.somethingSelected()) 
			return;
		// Find the token at the cursor
		var cur = editor.getCursor(false), token = editor.getTokenAt(cur), tprop = token;
		var namespace = UTILS.namespaceParser(token.string, token.end);
		var completions = UTILS.getVocabulary(namespace);
		
		if (!completions.length) {
			return;
		}
		
		function insert(str){
			var itemSel = UTILS.useItemSelected(token, str, namespace);
			editor.replaceRange(itemSel, {
				line: cur.line,
				ch: token.start
			}, {
				line: cur.line,
				ch: token.end
			});
			editor.save();
		}
		// When there is only one completion, use it directly.
		if (completions.length == 1) {
			insert(completions[0]);
			return true;
		}
		// Build the select widget
		var complete = document.createElement("div");
		complete.className = "completions";
		var sel = complete.appendChild(document.createElement("select"));
		sel.multiple = true;
		for (var i = 0; i < completions.length; ++i) {
			var opt = sel.appendChild(document.createElement("option"));
			opt.appendChild(document.createTextNode(completions[i]));
		}
		sel.firstChild.selected = true;
		sel.size = Math.min(10, completions.length);
		var pos = editor.cursorCoords();
		complete.style.left = pos.x + "px";
		complete.style.top = pos.yBot + "px";
		document.body.appendChild(complete);
		// Hack to hide the scrollbar.
		if (completions.length <= 10) 
			complete.style.width = (sel.clientWidth - 1) + "px";
		
		var done = false;
		function close(){
			if (done) 
				return;
			done = true;
			complete.parentNode.removeChild(complete);
		}
		function pick(){
			insert(sel.options[sel.selectedIndex].value);
			close();
			setTimeout(function(){
				editor.focus();
			}, 50);
		}
		connect(sel, "blur", close);
		connect(sel, "keydown", function(event){
			var code = event.keyCode;
			// Enter and space
			if (code == 13 || code == 32) {
				event.preventDefault(); //event.stop();
				pick();
			}
			// Escape
			else  
				if (code == 27) {
					event.preventDefault(); //event.stop();
					close();
					editor.focus();
				}
				else 
					if (code != 38 && code != 40) {
						close();
						editor.focus();
						setTimeout(startComplete, 50);
					}
		});
		connect(sel, "dblclick", pick);
		
		sel.focus();
		// Opera sometimes ignores focusing a freshly created node
		if (window.opera) 
			setTimeout(function(){
				if (!done) 
					sel.focus();
			}, 100);
		return true;
	}
}
});