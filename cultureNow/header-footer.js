/*
 * copyright cultureNOW 2013
 * created by David Giglio */

/* EVENT LISTENERS */
$(document).on('setheadfoot',function(e){
	setHeadFoot();
});

function setHeadFoot()
{
	var pid = $.mobile.activePage.attr("id");
	var header = A.header; // get default header from config
	
	switch(pid)
	{
		case 'index':
			try{
				header = {  
					"buttons"  : false,
					"title"    : featMgr.finfo.f_title, 
					"subtitle" : featMgr.finfo.r_title 
				} 
			}catch(err){ /* fail silently */ }
			break;
		case 'unit-tests':
			header = { 
				"buttons"  : true,
				"title"    : "UNIT TESTS", 
				"subtitle" : "App Testing Results"
			}  					   
			break;			
		case 'info-main':
			header = { 
				"buttons"  : true,
				"title"    : "ABOUT CULTURENOW", 
				"subtitle" : "History"
			}  					   
			break;
		case 'info-1':
			header = { 
				"buttons"  : true,
				"title"    : "ABOUT CULTURENOW", 
				"subtitle" : "Mission"
			}  					   
			break;
		case 'info-2':
			header = { 
				"buttons"  : true,
				"title"    : "ABOUT CULTURENOW", 
				"subtitle" : "Contact"
			}  					   
			break;
		case 'info-3':
			header = { 
				"buttons"  : true,
				"title"    : "ABOUT CULTURENOW", 
				"subtitle" : "Donate"
			}  					   
			break;
		case 'info-4':
			header = { 
				"buttons"  : true,
				"title"    : "ABOUT CULTURENOW", 
				"subtitle" : "About the App"
			}  					   
			break;
		/* SEARCH FORMS */	
		case 'search-nearby':
			header = { 
				"buttons"  : true,
				"title"    : "SEARCH", 
				"subtitle" : "Search Nearby Sites, Events, and Tours"
			}  
			break;
		case 'search-sites':
			header = { 
				"buttons"  : true,
				"title"    : "SEARCH", 
				"subtitle" : "Search cultureNOW's sites"
			} 
			break;
		case 'search-podcasts':
			header = { 
				"buttons"  : true,
				"title"    : "SEARCH", 
				"subtitle" : "Search cultureNOW's podcasts"
			}		
			break;
		case 'search-events':
			header = { 
				"buttons"  : true,
				"title"    : "SEARCH", 
				"subtitle" : "Search cultureNOW's events"
			}
			break;
		case 'search-tours':
			header = { 
				"buttons"  : true,
				"title"    : "SEARCH", 
				"subtitle" : "Search cultureNOW's tours"
			}
			break;
		/* RESULT DISPLAY */
		case 'list':
			var pagenum = A.searches[A.nav.srchtype][A.nav.listtype].srchinfo.pagenum;
			var ofpages = A.searches[A.nav.srchtype][A.nav.listtype].srchinfo.ofpages;
			if(typeof A.searches[A.nav.srchtype][A.nav.listtype].inputs.text_param !== 'undefined'){
				var title = A.searches[A.nav.srchtype][A.nav.listtype].inputs.text_param;	
			}else{
				var title = "Search Results";	
			}
			
			header = { 
				"buttons"  : true,
				"title"    : title, 
				"subtitle" : 'Showing page: ' + pagenum + ' of ' + ofpages
			}					   
			break;
		case 'search-tours':
			header = { 
				"buttons"  : true,
				"title"    : "SEARCH", 
				"subtitle" : "Search cultureNOW's tours"
			}					   
			break;	
		case 'record-a': 	
			header = { 
				"buttons"  : true,
				"title"    : A.header.title, 
				"subtitle" : A.header.subtitle
			}							 
			break;	
		case 'record-b':
			header = { 
				"buttons"  : true,
				"title"    : A.header.title,
				"subtitle" : A.header.subtitle
			}	
			break;				
		/* MODAL DIALOGS */	
		case 'credits':
			header = { 
				"buttons"  : true,
				"title"    : A.header.title, 
				"subtitle" : A.header.subtitle
			}
			break;
		case 'map':
			/*
			header = { 
				"buttons"  : true,
				"title"    : "SHOWING MAP", 
				"subtitle" : A.header.title
			}
			*/
			break;
		case 'player':
			header = { 
				"buttons"  : true,
				"title"    : A.header.title, 
				"subtitle" : A.header.subtitle
			}
			break;
		case 'mail':
			header = { 
				"buttons"  : true,
				"title"    : "CONTACT CULTURENOW", 
				"subtitle" : "Question, Suggestion, or Problem?"
			}					   
			break;
		case 'mail-result':
			header = { 
				"buttons"  : true,
				"title"    : "EMAIL SENT!", 
				"subtitle" : "Thank you for your time."
			}					   
			break;		
		case 'prefs':
			header = { 
				"buttons"  : true,
				"title"    : "SET PREFERENCES", 
				"subtitle" : "Set to limit search results"
			}					   
			break;	
		case 'webview':
			header = { 
				"buttons"  : true,
				"title"    : "WEBVIEW", 
				"subtitle" : "The content you are viewing is outside of CultureNOW"
			}					   
			break;
		/* DEFAULT*/		
		default:
			header = { 
				"buttons"  : false,
				"title"    : "", 
				"subtitle" : ""
			} 
	}
	$.extend(A.header,header);
	
	var head = insertHeader();
	$.mobile.activePage.find(".header").html(head);
	
	var foot = insertFooter();
	$.mobile.activePage.find(".footer").html(foot);
}

function insertHeader()
{
	var html = [];
	if(A.header.buttons == true){
		html.push('<div class="head-left">');
		html.push('		<a data-rel="back">');
		html.push('			<img class="hf-icon" src="img/icon-back.png" />');
		html.push('		</a>');
		html.push('</div>');
		html.push('<div class="head-center">');
	}else{
		html.push('<div class="head-center full">');
	}
	html.push('		<div id="title">' + A.header.title + '</div>');
	html.push('		<div id="subtitle">' + A.header.subtitle + '</div>');
	html.push('		<div class="clear"></div>');	
	html.push('</div>');
	if(A.header.buttons == true){	
		html.push('<div class="head-right">');
		html.push('		<a href="index.html">');
		html.push('			<img class="hf-icon" src="img/icon-home.png" />');
		html.push('		</a>');   
		html.push('</div>');
	}
	html = html.join("\n");
	return html;
}

function insertFooter()
{
	var html = [];
    html.push('<table width="100%" ');
    html.push('		style="padding-top:6px; background-color:transparent;"');
    html.push('		cellpadding="0"');
    html.push('		cellspacing="0">');       
    html.push('	<tr> ');
    html.push('		<td>');
    html.push('			<a href="p-mod-prefs.html"');
    html.push('			   data-transition="none">');
    html.push('				<img class="hf-icon" src="img/icon-prefs.png" />');
    html.push('			</a>');                
    html.push('		</td>');
    html.push('		<td style="text-align:center; padding-top:0px;">');
    html.push('			<a href="p-info-main.html"');
    html.push('			   data-transition="none">');
    html.push('				<img width="100" src="img/culturenow@2x.png" />');
    html.push('			</a>      ');                
    html.push('		</td>');
    html.push('		<td ');
	html.push('		    class="play-podcast"');
	html.push('		    record_id="00000"');	
	html.push('         style="text-align:right;">');
    html.push('					<img class="hf-icon" src="img/icon-player.png" />');                      
    html.push('		</td>');
    html.push('	</tr>');
    html.push('</table>');
	html = html.join("\n");
	return html;
}