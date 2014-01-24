/*
 * copyright cultureNOW 2013
 * created by David Giglio 
 */
 
/**
 * Gets key value pair from url query string.
 * Used to maintain back button functionality among dynamically created jquery mobile pages
 *
 * @param {string} name name of the key to retrieve
 * @returns {string}	 
 */
$.urlParam = function(name){
	var results = new RegExp('[\\?&]' + name + '=([^&#]*)').exec(window.location.href);
	return results[1] || 0;
}

/**
 * Gets key value pair from url query string.
 * Used to maintain back button functionality among dynamically created jquery mobile pages
 *
 * @param   {string} table Name of top-level object to retrieve from local storage (A.tables) 
 * @param   {array} arr Array of ids to retrieve from local storage (A.tables)
 * @returns {object} robj JSON object containing requested records	 
 */
function recordsFromLocalTables(table,arr){
	var robj = {};
	for(i in arr){
		robj[arr[i]] = A.tables[table][arr[i]];	
	}
	return robj;
}

/**
 * Count the number of properties an object contains
 * Syntax: Object.size(myObject)
 *
 * @param   {object} object Any object
 * @returns {int} size Number of properties object contains
 */
Object.size = function(obj) {
    var size = 0, key;
    for (key in obj) {
        if (obj.hasOwnProperty(key)) size++;
    }
    return size;
};

/**
 * Capitalizes a string. Syntax: myString.capitalize()
 *
 * @this  {string} this String to be capitalized
 * @returns {string} this Capitalized string
 */
String.prototype.capitalize = function() {
    return this.charAt(0).toUpperCase() + this.slice(1);
}

String.prototype.allCaps = function() {
    return this.toUpperCase();
}

/**
 * Creates address string from address object
 * T1 (type 1) returns string as street address w/o city/state info like [ num street ]
 *
 * @param {object} addrObj Object containing address information
 * @returns {string} adr Address string
 */
function addrT1(addrObj)
{ // single line: num street
	var arr = [];
	var adr = '';
	if(addrObj.add_number != ''){
		arr.push(addrObj.add_number);
	}
	if(addrObj.add_street != ''){
		arr.push(addrObj.add_street);
	}
	adr = arr.join(" ");
	return adr;
}

/**
 * Creates address string from address object
 * T2 (type 2) returns string as full street address like [ num street city, state ]
 *
 * @param {object} addrObj Object containing address information
 * @returns {string} adr Address string
 */
function addrT2(addrObj)
{ // single line: num street city, state
	var arr = [];
	var adr = '';
	if(this.add_number != ''){
		arr.push(addrObj.add_number);
	}
	if(this.add_street != ''){
		arr.push(addrObj.add_street);
	}
	if(this.city != ''){
		arr.push(addrObj.city);
	}
	if(this.state != ''){
		arr.push(addrObj.state);
	}
	adr = arr.join(" ");
	return adr;
}

/**
 * Creates address string from address object
 * T2 (type 2) returns string as full street address like [ num street city, state ]
 *
 * @param {object} addrObj Object containing address information
 * @returns {string} adr Address string
 */
function addrT3(addrObj)
{ // single line: num street city, state
	var arr = [];
	var adr = '';
	if(this.city != ''){
		arr.push(addrObj.city);
	}
	if(this.state != ''){
		arr.push(addrObj.state);
	}
	adr = arr.join(" ");
	return adr;
}


/**
 * Returns todays date in requested format
 *
 * @param {string} format format to return date string as. Only current option is 'mysql': YYYY-MM-DD
 * @returns {string} date Date string in requested format
 */
function today(format){
	var date = new Date();
	switch(format){
		case 'mysql':
		date = date.getUTCFullYear() + '-' + 
		        ('00' + (date.getUTCMonth()+1)).slice(-2) + '-' + 
				('00' + date.getUTCDate()).slice(-2);
			break;
		default:
		break;
	}
	return date;
}

/**
 * Returns today + 1 year in requested format
 *
 * @param {string} format format to return date string as. Only current option is 'mysql': YYYY-MM-DD
 * @returns {string} date Date string in requested format
 */
function yearFromToday(format){
	var date = new Date();
	switch(format){
		case 'mysql':
		date = (date.getUTCFullYear() + 1) + '-' + 
		       ('00' + (date.getUTCMonth()+1)).slice(-2) + '-' + 
			   ('00' + date.getUTCDate()).slice(-2);
			break;
		default:
		break;
	}
	return date;
}

/**
 * Returns time in HH:MM:SS format from seconds only format
 *
 * @param {float} float Seconds
 * @returns {string} time in HH:MM:SS format
 */
function sec2Min(d)
{
	d = Number(d);
	var h = Math.floor(d / 3600);
	var m = Math.floor(d % 3600 / 60);
	var s = Math.floor(d % 3600 % 60);
	return ((h > 0 ? h + ":" : "") + (m > 0 ? (h > 0 && m < 10 ? "0" : "") + m + ":" : "0:") + (s < 10 ? "0" : "") + s);
}

/**
 * Checks for string as valid email.
 * Used in p-info-2.html. Via stackoverflow
 *
 * @param {string} emailAddress USer input string
 * @returns {bool} time in HH:MM:SS format
 */
function isValidEmailAddress(emailAddress) {
    var pattern = new RegExp(/^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i);
    return pattern.test(emailAddress);
};

/**
 * Centers DOM element inside its parent
 *
 * @this {DOMelement} DOM element to be centered
 * @returns {this} this Centered DOM element
 */
$.fn.center = function()
{
    var parent = this.parent();
	this.css("position","absolute");
	var offset = { "w":"0", "h":"0" };
	    offset.h = ((parent.height() - $(this).outerHeight()) / 2) + "px";
	    offset.w = ((parent.width() - $(this).outerWidth()) / 2) + "px";
	this.css("top",offset.h);
    this.css("left", offset.w);
    return this;
}