/*
---

description: A content assist for textares of your webpage.

license: GNU General Public License, version 2.

authors:
- Andrea Dessì

requires:
 core/1.2.4:
  - all
 more/1.2.2.4:  
  - Element.Forms 

provides: [MooContentAssist,MooContentAssistWordXMLExtractor]

...
*/

/*
    Title: MooContentAssist Doc Reference
*/

/*
    Class: MooContentAssist
    
    It adds a content/code assist functionality to your textareas.

    Changelog:
    
    	09 Mar 2010 v0.70.4 - fixed positioning of assist window.
        01 Jul 2010 v0.70.4 - converter from xml to words object, fixed bug on foundlist, fixed bug on assist window position
        27 Jun 2010 v0.70 - theme changer, new demo with theme toggler
        11 Jun 2010 v0.70 - configurable number of item shown in the box
        10 Jun 2010 v0.70 - scrollable result box, scrollable result box shows always the current item in the middle
        04 Jun 2010 v0.68 - few standard methods for positioning, css rules methods
        24 May 2010 v0.68 - fixed textarea scroll when inserting keywords, fixed assistWindow position
        23 May 2010 v0.66 - first dot fixed, occurence text highlight fixed, animation now is a parameter
        22 May 2010 v0.64 - ie7 fixes
        21 May 2010 v0.63 - added events "click" and "over" to the shown items, when showing assistWindow first item is already selected, added "." trigger
        21 May 2010 v0.60 - added styles for items, window positioning 
        20 May 2010 v0.55 - fixed textarea events
        16 May 2010 v0.25 - fixed words data structure
        15 May 2010 v0.15 - added completed text, events and keys
        13 May 2010 v0.0  - hello word	
    
    Info:
	
		Version - 0.70.4
		Date - 01 Jul 2010
		
	Parameters:
	
	    el - {String} The html id of the textarea
	    options - {Object} An Object containing all the setting
    
    The Options Object:
        The option object has this properties:

        animationDuration - {Number}{Default 225} It defines how long will be the show/hide animation
        aniationTransition - {Object}{Default Fx.Transitions.Sine.easeOut} A Fx.Transitions object used for animations in show/hide events
        frameSize - {String|El|Number} Sets the height of window in order to contain X elements. Could be a html id, an element or a number
        css - {Object} Contains all the css properties objects for the content assist window elements
        css.assistWindow - {String|Object} The css class/properties for the container of everything
        css.assistList - {String|Object} The css class/properties for the container of the items
        css.completedItem - {String|Object} The css class/properties for the container of single item in the list
        css.activeItem - {String|Object} The css class/properties for highlighted/active item from the list 
        css.occurence - {String|Object} The css class/properties for the matched text in the keyword
        css.completedText - {String|Object} The css class/properties for the completed/help text in the keyword
        words - {Object|String} The vocabulary. If a string is passed could be an xml or an URL to xml file.
    
    
    The Words Vocabulary Object:
        Everything works if you give the right json words object. Use this as root:
        
        (start code)
words: {
    "key1":
    "key2":
    "key3":
}        
        (end)
        
        Then put in the *words* object your keys. 
        If the key doesn't have subkey, so it has just children keywords, use the array form:
        
        (start code)
"key_without_subkeys": ["word1","word2","word3" ...]
        (end)
        
        If the key has subkeys, use the object form:
        (start code)
"key_with_subkeys":  {
    "subkey1": ["word","word","word" ...],
    "subkey2": ["word","word","word" ...],
    ...
}
        (end)
        
        The exaple with mixed keys (with and without subkeys)
        (start code)
words: {
    "key1": ["a","b","c"],
    "key2": [null],
    "key3": {
       "subkey1": {
           "subsub1": ["tr","ol","ololo"],
           "subsub2": ["ulla","lala","laaaa"],
           "subsub3": [null]
       },
       "subkey2": ["test1","test2","test3"],
       "subkey3": ["weeeeeeeeee!"]
    }
}     
        (end)
        
        If you prefer the xml version use this sintax:
        (start code)
         <words>
             <key><name>mykey</name></key>
             <key><name>mykey2</name></key>

             <key>
                 <name>mykey_with_subkeys</name>
                 
                 <key><name>sub_k_1</name></key>
                 <key><name>sub_k_2</name></key>
                 <key>
                     <name>sub_k_3</name>
                     
                     <key><name>subsub_k_1</name></key>    
                     <key><name>subsub_k_2</name></key>    
                 </key>
             </key>
             
         </words>
        (end)
        
*/
var MooContentAssist = new Class({
	version: "MooContentAssist v0.70.4",
	Implements: [Events, Options],
	options: {
		animationDuration: 150,
		aniationTransition:  Fx.Transitions.Sine.easeOut,
        frameSize: 3,
		css: {
			"activeItem": {
				"margin-bottom": "0.125em",
				"cursor": "pointer",
				"color":"red",
				"background-color": "#51607C",
				"padding": "0.125em 0 0 0",
                "height": "1.5em",
                "display": "block",
                "float": "left",
                "width": "100%"
			},
			"completedItem": {
				"margin-bottom": "0.125em",
				"cursor": "pointer",
				"padding": "0.125em 0 0 0",
                "height": "1.5em",
                "display": "block",
                "float": "left",
                "width": "100%"                
			},
			"assistList": {
				"margin": "0",
				"padding": "0",
				"list-style-type": "none"
			},
			"occurence": {
				"color": "#8DBBD9",
				"font-weight": "bold",
				"margin-left": "5px"
			},
			"completedText": {
				"color": "#fff"	
			},
			"assistWindow": {
				"border": "1px solid black",
				"background-color": "#414E67",
				"padding": "6px 0 3px 0",
				"font-family": "arial,helvetica,clean,sans-serif",
				"font-size": "13px",
				"opacity": "0.97",
                "height": "8.75em",
                "overflow": "auto"
			},
            "messages": {
                "padding": "0 3px",
                "color": "yellow",
                "font-size": "13px",
                "margin": 0
            }
		},		
		//toggler option is not in use. still TODO
        toggler: "toggler",
		words: { },
		messages: {
			nothingFound: "Nothing was found"
		}
	},
	initialize: function(el, options){
		if ($defined(el)) {
			this.setOptions(options);
			this.initializeEvents();
			this.initializeTextarea(el);
			this.initializeVocabulary();
			this.initializeTogglers();
		}
	},
	initializeTogglers: function() {
		if ($defined(this.options.toggler)) {
			//TODO
		}
	},
	initializeVocabulary: function(){
		/*
		 * Function: initializeVocabulary
		 * 
		 * Gets the words object and build a new Hash()
		 * 
		 */
		switch($type(this.options.words)) {
            case "string":
                this.vocabulary = new Hash(new MooContentAssistWordXMLExtractor(this.options.words));
                break;
        	default: 
        		this.vocabulary = new Hash(this.options.words);
        }
	},
	initializeEvents: function() {
		/*
		 * Function: initializeEvents
		 * 
		 * It setups the event for the MooContentAssist instance, adding the events: startAssist, hide, show, destroy. 
		 * 
		 */
		this.addEvents({
			"startAssist": function(position){ this.startAssist(position); }.bind(this),	
			"hide": function() { this.hide(); }.bind(this),
			"show": function() { this.show(); }.bind(this),
			"destroy": function() { this.destroy(); }.bind(this)
		});
	},
	initializeTextarea: function(el){
		/*
		 * Function: initializeTextarea
		 * 
		 * Get the element and store it, adds the events and the logic for firing the "startAssist" event.
		 * 
		 */
		
		this.editorTextarea = document.id(el); 
		this.editorTextarea.addEvents({
			"keydown": function(ev) {
					
					if ( (ev.control || ev.alt) && ev.key == "space" ) {
						ev.preventDefault();
						this.fireEvent("startAssist",ev.target.getCaretPosition());
					}
					else if ($defined(this.assistWindow) && ((ev.key == "up") || (ev.key == "down"))) {
						ev.preventDefault();
					}
					else if ($defined(this.assistWindow) && ev.key == "esc") {
						this.fireEvent("destroy");
					}
					/* fix for webkit/trident - start */
					if ($defined(this.assistWindow) && (Browser.Engine.webkit || Browser.Engine.version == 6 || Browser.Engine.version == 5)){
						if (ev.key == "up") {
							ev.preventDefault();
							this.selectItemUp();
						}
						else if (ev.key == "down") {
							ev.preventDefault();
							this.selectItemDown();
						}
						else if (ev.key == "enter" && $defined(this.selectedItem)) {
							ev.preventDefault();
							this.useSelectedItem();						
							this.fireEvent("destroy");
						}
						else if (ev.key == "enter" && !$defined(this.selectedItem)) {
							this.fireEvent("destroy");
						}
					} 
					/* fix for webkit/trident - end */
				}.bind(this),
			"keypress": function(ev) {
					if ($defined(this.assistWindow)) {
						if (ev.key == "up") {
							ev.preventDefault();
							this.selectItemUp();
						}
						else if (ev.key == "down") {
							ev.preventDefault();
							this.selectItemDown();
						}
						else if (ev.key == "enter" && $defined(this.selectedItem)) {
							ev.preventDefault();
							this.useSelectedItem();
							this.fireEvent("destroy");
						}
						else if (ev.key == "enter" && !$defined(this.selectedItem)) {
							this.fireEvent("destroy");
						}
					}
				}.bind(this),				
			"keyup": function(ev) {
					if (((ev.control || ev.alt) && ev.key == "space" ) || ev.key == "alt" || ev.key == "control") { }
					else if ($defined(this.assistWindow) && ev.key != "shift") {
                        if (!($defined($try(function() { return this.getFirst("ul"); }.bind(this.assistWindow))))) {
                            //if nothing is found state
                            this.fireEvent("destroy");
                        }
                        else {
                            //if it's really containing items
                            if ((ev.key != "up" && ev.key != "down") || (ev.key == ".")) {
    							this.fireEvent("startAssist",this.editorTextarea.getCaretPosition());
    						}
                        }
					}
					else if ($defined(this.assistWindow) && ev.key == ".") {
						this.fireEvent("startAssist",this.editorTextarea.getCaretPosition());
					}
				}.bind(this)
		});
	},
	selectItemUp: function() {
		/*
		 * Function: selectItemUp
		 * 
		 * Highlight the previous item from the list. Usually used when the user press the up arrow key.
		 * 
		 */
		var item;
        if ($defined(this.selectedItem) && $defined(this.selectedItem.getPrevious())) {
            item = this.selectedItem.getPrevious();
		} 
		else { 
			//TODO : refactor, make configurable "ul" and "li"
            item = this.assistWindow.getLast("ul").getLast("li");
		}
        this.selectItem(item);
        this.scrollToItem(item);
	},
	selectItemDown: function() {
		/*
		 * Function: selectItemDown
		 * 
		 * Highlight the next item from the list. Usually used when the user press the down arrow key.
		 * 
		 */
		var item;
        if ($defined(this.selectedItem) && $defined(this.selectedItem.getNext())) {
            item = this.selectedItem.getNext();
		} 
		else { 
			//TODO : refactor, make configurable "ul" and "li"
            item = $try(function() { return this.assistWindow.getFirst("ul").getFirst("li"); }.bind(this));
		}
        if ($defined(item)){
            this.selectItem(item);
            this.scrollToItem(item);
        }
	},
    scrollToItem: function(item) {
            var assistWindowScroller = new Fx.Scroll(this.assistWindow,{
                    duration: this.options.animationDuration,
                    offset: {"x": 0, "y": this.assistWindow.getStyle('padding-top').toInt()*-1}
                    
                });
            //item height
            var i = item.getComputedSize({"styles": ["margin","padding","border"]}).totalHeight;
            //box height
            var f = (this.assistWindow.getComputedSize({"styles": ["padding"]}).totalHeight/i); f = f.toInt();
            //current item
            var c = this.assistWindow.getElements('li').indexOf(item);
            //index
            var indexToScrollTo = ((c/f).toInt()) * f; 
			//calculate the current "frame"
			if (c > (f/2).toInt()) {
				indexToScrollTo = c - (f/2).toInt(); 
			}
			//scroll to item at that index
            assistWindowScroller.toElement(this.assistWindow.getElements('li')[indexToScrollTo]);
	},
	selectItem: function(item) {
		/*
		 * Function: selectItem
		 * 
		 * Gets an item and highlights it. Always called from selectItemUp and selectItemDown functions.
		 * 
		 * Parameters:
		 * item -  the item to highlight
		 *  
		 */
		if ($defined(this.selectedItem) && $defined(item) && $chk(item.get("text")) ) {
			this.removeCss(this.selectedItem,this.options.css.activeItem);
			this.applyCss(this.selectedItem,this.options.css.completedItem);
		}
		if ($defined(item) && $chk(item.get("text"))) {
			var start;
			if (this.namespace.getLast() == "/") { 
				start = 0; 
			}
			else { 
				start = this.namespace.getLast().length;
			}			
			this.selectedItem = item;
			this.completedText = item.get("text").substring(start); 			
			this.removeCss(item,this.options.css.completedItem);
			this.applyCss(item,this.options.css.activeItem);
		}
	},
	useSelectedItem: function() {
		/*
		 * Function: useSelectedItem
		 * 
		 * Gets the text from the current highlighted item and writes it to the textarea.
		 *
		 */
		if($defined(this.completedText)) {
			var scrollTop = this.editorTextarea.scrollTop;
			var position = this.editorTextarea.getCaretPosition();
			var textbefore = this.editorTextarea.get("value").substring(0, position);
			var textafter = this.editorTextarea.get("value").substring(position);
			this.editorTextarea.set("value", textbefore + this.completedText + textafter);
			this.editorTextarea.setCaretPosition(textbefore.length + this.completedText.length);
			//this.editorTextarea.focus();
			this.editorTextarea.scrollTop = scrollTop;
			this.completedText=null;
		}
		this.fireEvent("destroy");
	},
	destroy: function() { 
		/*
		 * Function: destroy
		 * 
		 * This will destroy the assistedWindow and the items list calling the hide function
		 * 
		 */
		if ($defined(this.assistWindow)) { 
			this.hide();
			//this.assistWindow.destroy(); 
		}
		this.assistWindow = null;
	},
	hide: function() {
		/*
		 * Function: hide
		 * 
		 * Will hide the window with the item
		 * 
		 */
		var fx = new Fx.Morph(this.assistWindow, {
			duration: this.options.animationDuration, 
			onChainComplete: function() {
				this.destroy();
			}.bind(this.assistWindow),
			transition: this.options.aniationTransition
		});
		fx.set({"opacity":1});
		fx.start({
		    'opacity': 0
		});
	},
	show: function() {
		/*
		 * Function: show
		 * 
		 * Will show the window with the items.
		 * 
		 */
		var fx = new Fx.Morph(this.assistWindow, {
			duration: this.options.animationDuration, 
			transition: this.options.aniationTransition
		});
		fx.set({"opacity":0});
		fx.start({
		    'opacity': 1
		});
	},
	startAssist: function(position) {
		/*
		 * Function: startAssist
		 * 
		 * Will check for the current "namespace" and lets start rock.
		 * 
		 * Parameters:
		 * position - {Number} The textarea position where start to read
		 * 
		 */
		if (!$defined(position)) position = this.editorTextarea.getCaretPosition();
		this.position = position;
		this.destroy();			
		this.namespace = this.findNameSpaceElements(position);
		if ($chk(this.namespace)) {
			this.doAssist();
		}
        else {
            this.fireEvent("destroy");
        }
	},
	findNameSpaceElements: function(position) {
		/*
		 * Function: findNameSpaceElements
		 * 
		 * Will discovere where the namespace or the object tree.
		 * 
		 * Parameters:
		 * position - {Number} The textarea position where start to read
		 * 
		 * Returns: 
		 * {Array} the namespace
		 * 
		 */
		var namespace = null;
		var endPosition = position;
		position=position-1;
		if ($type(position) == "number") {
			var test = true;
			var previousChar = "";
			while(position >= 0 && test) {
				var c = this.editorTextarea.get("value").substring(position,position+1);
				if (!(/\S/.test(c))) { test = false; }
				else {
					if (previousChar=="" && c==""){
						test = false;
						position = position+1;
					}
					else {
						previousChar = c;
						position = position-1;
					}
				}			
			}
			var namespaceFlat = this.editorTextarea.get("value").substring(position+1,endPosition).trim();
			if (namespaceFlat.length > 0 && namespaceFlat != ".") {
				namespace = namespaceFlat.split(".");
				if (namespace[namespace.length-1] == "") namespace[namespace.length-1] = "/"; 
			}
			else if (namespaceFlat.length == 0 && namespaceFlat == "") {
				namespace = ["/"];
			}
		}
		return namespace;
	},
	doAssist: function() {
		/*
		 * Function: doAssist
		 * 
		 * It will read the already discovered namespace and will get the right items from the vocabulary. 
		 * When something useful is found will call the makeAssistWindow function.
		 * 
		 */
		var namespace = this.namespace;
		if($chk(namespace)) {
			var foundList = [];
			var vocabulary = this.vocabulary;
			if (namespace.length > 1) {
				var arrayNotation="";
				for (var i = 0; i < namespace.length-1; i++) {
					if (!(i == namespace.length-1 && namespace[i] == "/" )) {
						arrayNotation = arrayNotation + "['" + namespace[i] + "']";
					}
				} 
				try {
					vocabulary = eval("vocabulary.getClean()"+arrayNotation);
					if($type(vocabulary) == "object") {
						vocabulary = new Hash(vocabulary).getKeys();
					}
				} catch (e) { }
			} else {
				vocabulary = vocabulary.getKeys();
			}
			if($defined(vocabulary) && $chk(vocabulary) && $type(vocabulary) == "array" && vocabulary.length > 0 ) {
				var lastWord = this.namespace.getLast();
				if ($chk(lastWord) && lastWord != "/") {
					vocabulary.each(function(item,index){
						if ($type(item) == "object") item = new Hash(item).getKeys()[0];
						if(this.checkString(item,lastWord)) {
							foundList.push(item);
						}
					}.bind(this));
				} 
				else {
					foundList = vocabulary;				
				}				
			}
            
            foundList = foundList.clean();
            
			if (foundList.length > 0) { 
				this.makeAssistWindow(foundList);
				this.selectItemDown();
			}
            else {
                //this.fireEvent("destroy");
            	this.makeAssistWindow([]);
            }
		}		
	},
	makeAssistWindow: function(foundList) {
		/*
		 * Function: makeAssistWindow
		 * 
		 * Will build the element containing the help.
		 * 
		 * Parameters:
		 * foundList - {Array} The vocabulary found for the current namespace
		 * 
		 */
		var scrollTop = this.editorTextarea.scrollTop;
		foundList.sort();
		var w;
		if (!($defined(this.assistWindow) && document.id(this.assistWindow))) {
			w = new Element("div",{
				"styles": { 
					"width": this.editorTextarea.getSize().x-2,
					"position": "absolute",
					"opacity": "0"
				}
			}).inject(this.editorTextarea,"after");
			w.setStyles({
				"left":this.editorTextarea.getCoordinates().left,
				"top": this.editorTextarea.getCoordinates().top+this.editorTextarea.getSize().y
			});
			
            this.assistWindow = w;
			this.applyCss(w,this.options.css.assistWindow);
		} else { 
			w = this.assistWindow; 
			w.empty();
		}
		
		//TODO: add blur event for destroying the assistWindow
		
		this.selectedItem = null;
		this.fireEvent("show");
		if (foundList.length > 0) {
			this.assistList = new Element("ul").inject(this.assistWindow);
			this.applyCss(this.assistList,this.options.css.assistList);
		}
		var versionEl = new Element("span",{ 
			text: this.version, 
			styles:{
				"color": "#596A88",
				"margin": 0,
				"padding": 0, 
				"position": "absolute",
				"font-size": "9px",
				"top": "0",
				"width": "100%",
				"background-color": "transparent",
				"text-align": "right"
		}}).inject(this.assistWindow);
		
		versionEl.setStyles({
			"left": 0
		});
		
        if (foundList.length > 0) {
            foundList.each(function(item, index){
    			this.makeAssistItem(item);
    		}.bind(this));
            this.setFrameSize();
        }
        else {
	        this.setFrameSize("1em");
            this.makeMessage(this.options.messages.nothingFound);
        }
		this.editorTextarea.scrollTop = scrollTop;
        
	},
	setFrameSize: function(size) {
		/*
		 * Function: setFrameSize
		 * 
		 * Set the height of the assistWindow in order to contain and show a certain number of items.
		 * 
		 * Parameters:
		 * size - {String|El|Number} If null this.options.frameSize will be used.
		 * 
		 * If size is String will be converted to a number, if possible, otherwise an element with that id will be searched in the dom.
		 * 
		 * If size is an Element its value will be retrieved with the standard function ( ex. size.get("value") )
		 * 
		 */        
		if (!$defined(size)) {
			size = this.options.frameSize;
		}
		var frameSize=3;
		switch ($type(size)) {
			case "string": 
				if ($type(size.toInt()) == "number") {
					frameSize = size.toInt();
				}
				else if ($type(document.id(size)) == "element") {
					var f = document.id(size);
					f = $try(function() { return this.get("value"); }.bind(f));
					if ($chk(f)) {
						frameSize = f;
					}
				}
				break;
			case "number":
				frameSize = size;
				break;
			case "element":
				if ($chk( $try( function() { return  size.get("value").toInt(); } ) )) {
					frameSize = size.get("value").toInt();
				}
				break;
			default: 
				frameSize = 3;
		}
        var children = $try(function() { return this.assistWindow.getElements("li").length; }.bind(this) );
		if (children == 0) {
            this.assistWindow.setStyle("height", "1.5em");
        }
        else {
            if (children < frameSize) { frameSize = children; } 
            this.assistWindow.setStyle("height",(this.assistWindow.getElements("li")[0].getComputedSize({"styles": ["padding","margin","border"]}).totalHeight * frameSize) + "px");
        }
	},
    makeMessage: function(text){
        var messageElement = new Element("p",{
                "text": text
                });
	    messageElement.inject(this.assistWindow);
        this.applyCss(messageElement,this.options.css.messages);
    },
	applyCss: function(el,css) {
		/*
		 * Function: applyCss
		 * 
		 * Check and apply the css passed. Css parameter could be a "Style Object" or a String. If passed a string a css class with that name will be added to the element. 
		 * 
		 * Parameters:
		 * el - {Element} the element to apply the css properties 
		 * css - {Object|string} the css properties
		 */
		if ($defined(css)) {
			if ($type(css)=="object") el.setStyles(css);
			else if ($type(css)=="string") el.addClass(css);
		}		
	},
	removeCss: function(el,css) {
		/*
		 * Function: removeCss
		 * 
		 * Removes the css properties passed. If css is a string that class will be removed otherwise the style will be resetted.
		 * 
		 * Parameters:
		 * el - {Element} the element to remove the css properties 
		 * css - {Object|string} the css properties
		 * 
		 */
		if ($defined(css)) {
			if ($type(css)=="string") el.removeClass(css);
			else el.set("style","");
		}		
	},
	setCss: function(css) {
		/*
		 * Function: setCss
		 * 
		 * Apply css properties to MooContentAssist instance.
		 * 
		 * Parameters:
		 * css - {Object} the css properties or class to apply to MooContentAssist elements.
		 * 
		 */
		if ($defined(css) && $chk(css)) { this.options.css = css; }
	},
	makeAssistItem: function(item) {
		/*
		 * Function: makeAssistItem
		 * 
		 * Will build the an item to add to the list.
		 * 
		 * Parameters:
		 * item - {Object|String} the text of the item in the assistedWindow list  
		 * 
		 */
		if ($type(item) == "object") item = new Hash(item).getKeys()[0];
		var occurencePosition = this.namespace.getLast()=="/" ? 0 : this.namespace.getLast().length;  
		var occurrence = item.substring(0,occurencePosition);
		var completedText = item.substring(occurencePosition,item.length);
		var itemEl = new Element("li").inject(this.assistList);
		this.applyCss(itemEl,this.options.css.completedItem);
		itemEl.addEvents({
			"click": function(ev){ 
				ev.stopPropagation();
				this[0].selectItem(this[1]);
				this[0].useSelectedItem();
			}.bind([this,itemEl]),
			"mouseover": function(ev) {
				this[0].selectItem(this[1]);
			}.bind([this,itemEl])
		});
		var occurrenceEl = new Element("span",{ "text": occurrence }).inject(itemEl,"top");
		this.applyCss(occurrenceEl,this.options.css.occurence);
		var completedEl = new Element("span",{ "text": completedText}).inject(itemEl,"bottom");
		this.applyCss(completedEl,this.options.css.completedText);
	},
	checkString: function(a,b) {
		/*
		 * Function: checkString
		 * 
		 * checkString returns true when the "a" string starts with the "b" string.
		 * 
		 * Parameters:
		 * a - {String} the string to test
		 * b - {String} the pattern to match
		 * 
		 * Returns:
		 * true|false
		 */
		var b = b.replace(/\./gi, "\\\\.").replace(/\[/gi, "\\\[").replace(/\]/gi, "\\\]").replace(/\{/gi, "\\\{").replace(/\}/gi, "\\\}").replace(/\(/gi, "\\\(").replace(/\)/gi, "\\\)").replace(/\^/gi, "\\\^").replace(/\|/gi, "\\\|").replace(/\*/gi, "\\\*").replace(/\$/gi, "\\\$");
		var test = a.test("^"+b,"i"); 
		return test; 
	}
});
/* XML WORD EXTRACTOR */
var MooContentAssistWordXMLExtractor = new Class({
	Implements: [Events, Options],
	initialize: function(root, options){
		if ($defined(root)) {
            this.root = this.findRoot(root);
			this.obj = {};
			var children = this.root.getChildren("key");
			for (var i = 0; i < children.length;i++) {
				var item = children[i];
				var key = this.extractName(item);
				var value = this.extractObj(item);
				this.obj[key] = value;
			}
			return this.obj;
		}
	},
    findRoot: function(root) {
        var discoveredRoot = null;
        switch ($type(root)) {
            case "string":   
                if (root.contains("<")) { 
                    discoveredRoot =  XML.hashesToTree(XML.rootToHashes(XML.rootFromString(root)));
                } else {
                    discoveredRoot =  XML.hashesToTree(XML.rootToHashes(XML.rootFromFile(root.toURI())));
                }
                if (discoveredRoot.length > 0) { 
                	discoveredRoot = discoveredRoot[0];
                }
            break;
            
            default:
	            discoveredRoot = null;
        }
        return discoveredRoot;
    },
	getObj: function() {
		return this.obj;
	},
	extractName: function(itemTree) { 
		return itemTree.getElement("name").get("text").trim().clean();
	},
	extractObj: function(itemTree) {
		var obj = null;
		var children = itemTree.getChildren("key");
		if (children.length > 0) {
			obj = {};
			for (var i = 0; i < children.length; i++) {
				var item = children[i];
				var name = this.extractName(item);
				obj[name] = this.extractObj(item);
			}
		} 
		return obj;
	}
});