/*
 * copyright cultureNOW
 * created by David Giglio 
 */

/**
  * Sets up the Connection Manager (cnxMgr in js/config.js)
  * The Connection Manager detects the connection state and reports to the user if the connection is lost.
  * Acts as a singleton. Only one instance should ever exist at a time.
  *
  * @class The instance of the ConnectionManager 
  */ 
function ConnectionManager()
{
	
	/**
	 * @property {string} networkState string representation of current connection status
     */
	var networkState;
	
	/** 
	 * @property {object} timer setInterval timer for rechecking the connection.
	 * @property {bool} cstate current connection status
	 * @property {bool} pstate previous connection status 
	 */	
	this.timer = null;	
	this.pstate = false;
	this.cstate = false;

	/** 
	 * @property {object} array of string representations of possible connection states
	 */	
	var states = {};
	states[Connection.UNKNOWN]  = 'Unknown connection';
	states[Connection.ETHERNET] = 'Ethernet connection';
	states[Connection.WIFI]     = 'WiFi connection';
	states[Connection.CELL_2G]  = 'Cell 2G connection';
	states[Connection.CELL_3G]  = 'Cell 3G connection';
	states[Connection.CELL_4G]  = 'Cell 4G connection';
	states[Connection.NONE]     = 'No network connection';
	
    /**
	 * initial check of connection status 
	 *
	 * @returns {void}	 
	 */		
	this.establishConnection = function(){
		console.log('running establishConnection()');
		networkState = navigator.network.connection.type;
		if(states[networkState] == 'No network connection' || states[networkState] == 'Unknown connection'){
			this.cstate = false;
			this.pstate = false;
			console.log('remote connection could not be established.');
		}else{
			this.cstate = true;
			this.pstate = true;
			console.log('remote connection established');
		}	
		console.log('cxn est: ' + this.cstate);
	}
	
    /**
	 * subsequent checks of connection status 
	 * if connection state changes, alert is fired
	 *
	 * @returns {void}	 
	 */			
	this.checkConnection = function(){
		console.log('running checkConnection()');
		networkState = navigator.network.connection.type;
		if(states[networkState] == 'No network connection' || states[networkState] == 'Unknown connection'){
			this.cstate = false;
			console.log('the remote connection is lost.');
		}else{
			this.cstate = true;
			console.log('the remote connection is good.');
		}
		if(this.cstate != this.pstate){
			if(this.cstate === false){
				alert('Your connection to the internet has been lost. The app may not work correctly.');
			}else if(this.cstate === true){
				alert('Your connection has been reestablished');
			} 
		}
		this.pstate = this.cstate;
		console.log('cxn chk: ' + this.cstate);
	}
}