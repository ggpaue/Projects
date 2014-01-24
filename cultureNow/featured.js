/*
 * copyright cultureNOW 2013
 * created by David Giglio */

function FeaturedManager()
{	
	var FeatMgr = this;
	
	var ajgf = null; // ajax storage
	var MAX_ATTEMPTS = 5; 
	var gf_a = 0; // attempts
	
	var inputs = {
		"search_type"    : "get_featured",
		"app_type"       : A.config.app_type,
		"version_number" : A.config.version,
		"date_start"     : A.config.date_start
	}	
	
	this.got_f = false;
	this.finfo = {
		"f_title" : "CULTURENOW",
		"r_title" : "Getting today's feature...",
		"f_gtime" : "",
		"f_table" : "",
		"f_recid" : ""
	}
	
	this.getFeatured = function()
	{
		console.log($.param(inputs)); // print POST string to console, use for tests
		ajgf = $.ajax({
			url: "http://mobile.culturenow.org/runSearch.php",
			type:'POST',
			dataType:'json',
			data: inputs,
			success: function(json){
				if(json.result == 'success'){ 
					json.finfo.r_title = (json.finfo.f_table == 'item' ? 'sites' : 'events');
					// change table returned as "item" or "single_events" to "sites" and "events" respectively
					json.finfo.f_table = (json.finfo.f_table == 'item' ? 'sites' : 'events');
					// push into the A object
					$.extend(FeatMgr.finfo, json.finfo);
					$.extend(A.tables[json.finfo.f_table], json.record);
					$(document).trigger('getfeatsuccess');
				}else{
					$(document).trigger('getfeatfailed',json);
				}
				ajgf = null;
			},
			error: function(jqXHR, textStatus, errorThrown){
				$(document).trigger('getfeaterror');
				ajgf = null;
			}
		});		
	}

	/* getFeatured() EVENT LISTENERS */
	$(document).on('getfeatsuccess',function(){
		FeatMgr.got_f = true;
		displayFeatured();							 
	});
	
	$(document).on('getfeatfailed', function(event,data){	
		gf_a++;
		if(gf_a < MAX_ATTEMPTS){ 				
			setTimeout(function(){ FeatMgr.getFeatured(); },500);
			console.log('feature not found. attempt #' + gf_a);
		}else{
			console.log('feature not found. max attempts reached.');
			sendErrorEmail('failed to get feature' + JSON.stringify(data));
		}											 
	});
	
	$(document).on('getfeaterror',function(){	
		gf_a++;
		if(gf_a < MAX_ATTEMPTS){ 				
			setTimeout(function(){ FeatMgr.getFeatured(); },500);
			console.log('could not reach server. attempt #' + gf_a);
		}else{
			console.log('could not reach server. max attempts reached.');
			sendErrorEmail('get feature error');
		}												 
	});	
	
	/* ACTIONS */
	displayFeatured = function()
	{
		var fm = FeatMgr.finfo;
		
		A.nav.srchtype = fm.f_table;
		
		var frecord = A.tables[fm.f_table][fm.f_recid];
		var images = $.parseJSON(frecord.images);
		var imgurl = 'http://culturenow.org/media/new_images/' + images[0].record_id + '/web.jpg';
		$("#featured_image img").attr("src",imgurl);
		
		$("#featured_image").addClass("go-to-record");
		$("#featured_image").attr("table",frecord.from_table);
		$("#featured_image").attr("record_id",frecord.record_id);
		
		FeatMgr.finfo.r_title = (fm.f_table == 'sites' ? frecord.name_title : frecord.title)
	
		$("#featured_image img").load(function(){ 
			$("#featured_image").center(); 
			$("#featured_image #photo-credit").html(images[0].photo_credit);
		}); 
		$(document).trigger('setheadfoot');	
	}	
	
}