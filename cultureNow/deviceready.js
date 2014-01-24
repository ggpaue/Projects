/*
 * copyright cultureNOW 2013
 * created by David Giglio */

function onDeviceReady()
{
	/* CORDOVA CONFIG ***************************************************************************************/
	/********************************************************************************************************/
	var version_number = function(version) {
		A.config.version = version;
	};
	cordova.exec(version_number, null, "GetVersionNumber", "getVersionNumber", []);	
	
	/* JQUERY ***********************************************************************************************/
	/********************************************************************************************************/
	$.ajaxSetup({ cache: false });
	
	/* JQUERY MOBILE ****************************************************************************************/
	/********************************************************************************************************/
	$(document).bind('mobileinit', function(){
		$.mobile.defaultPageTransition="none";
		$.mobile.loader.prototype.options.textVisible=false;
		$.mobile.loader.prototype.options.html = '<div class="loading"></div>';
		$.mobile.pushStateEnabled = false;
	});
	
	/* INITIALIZE OBJECTS ***************************************************************************************/
	/************************************************************************************************************/	
	setTimeout(function(){
		cnxMgr = new ConnectionManager();
		cnxMgr.establishConnection();
		cnxMgr.timer = setInterval(function(){ cnxMgr.checkConnection(); },CHK_CXN_INTERVAL);
		
		locMgr = new LocationManager();
		locMgr.getLocation();
		locMgr.timer = setInterval(function(){ locMgr.getLocation(); },GET_LOC_INTERVAL);	
		
		prefMgr = new PrefsManager();
		
		featMgr = new FeaturedManager();
		featMgr.getFeatured();
		featMgr.timer = setInterval(function(){ featMgr.getFeatured(); },CHK_FEAT_INTERVAL);	
	
		listMgr = new ListManager();
		
		mapMgr = new MapManager();	
		
		podPlay = new PodcastPlayer('00000');	
	},10);
	
	/* LAYOUT & DISPLAY ****************************************************************************************/
	/***********************************************************************************************************/
	// disable show/hide tooldbar on tap
	$(document).on('pageinit','[data-role=page]', function(){
		$('[data-position=fixed]').fixedtoolbar({ tapToggle:false});
	});

	/* GET REMOTE SET UP VALUES ********************************************************************************/
	/***********************************************************************************************************/
	getSetUp();

	/* LOAD PAGES **********************************************************************************************/
	/***********************************************************************************************************/
	$.mobile.loadPage("p-search-nearby.html");

	/* PAGE EVENT LISTENERS ************************************************************************************/
	/***********************************************************************************************************/	
	//universal
	$(document).on('pagebeforeshow', function(){
		$(document).trigger('setheadfoot');
	});
	
	$(document).on('pagebeforeshow', function(){
		var pid = $.mobile.activePage.attr("id")
		switch(pid){
			case 'search-nearby':
				A.nav.srchtype = 'nearby';
				A.nav.listtype = 'sites';
				break;
			case 'search-sites':
				A.nav.srchtype = 'sites';
				A.nav.listtype = 'sites';
				break;
			case 'search-podcasts':
				A.nav.srchtype = 'podcasts';
				A.nav.listtype = 'podcasts';
				break;
			case 'search-events':
				A.nav.srchtype = 'events';
				A.nav.listtype = 'events';
				break;
			case 'search-tours':
				A.nav.srchtype = 'tours';
				A.nav.listtype = 'tours';
				break;
		}
	});
	
	// individual
	$(document).on('pageinit', '#search-nearby', function(event){
		try{
			fillSearchNearbyForm(locMgr.curAddr);
		}catch(err){
			alert(err);	
		}
	});
	
	console.log(JSON.stringify(podPlay));
}