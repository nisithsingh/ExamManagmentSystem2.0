var i;
var counter;

function startTimer(timeLeft){
	
	//document.getElementById("start").disabled=true;
	if(i==null){
		i=timeLeft;
		//document.getElementById("timerDisplay").innerHTML = Math.floor(i/3600) +":" +(Math.floor(i/60)%60) +":" + (i%60) ;
		counter=setInterval(runTimer, 1000);
	}
	else{
		//document.getElementById("timerDisplay").innerHTML = Math.floor(i/3600) +":" + (Math.floor(i/60)%60) +":" + (i%60) ;
		counter=setInterval(runTimer, 1000);
	}
	
	
}

function stopTimer(){
	clearInterval(counter);
	//document.getElementById("start").disabled=false;
}

function runTimer(){


	
	var j = Math.floor(i/60);
	var k = Math.floor(i/3600)
	
	document.getElementById("timerDisplay").innerHTML = k + ":" + (j%60) +":" + (i%60) ;

	if(i%60==59)
			j-=1;
	if(j%60==59)
			k--;
	i--;
		

	if(i<0)
		{
			clearInterval(counter);
			//document.getElementById("start").disabled=true;
			
			return;
		}
		
	//console.log(i);
		
	}

//To make JS execction stop totally. 
//This doesn't happen in setTimeout or setInterval

function pausecomp(ms) {
ms += new Date().getTime();
while (new Date() < ms){}
} 
