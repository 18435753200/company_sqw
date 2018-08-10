(function(window){
	window.blues_rushbuy={
		clicks:'click',
		hrefBack:function (dom){
			dom.addEventListener(blues_rushbuy.clicks,function (){
				history.back();
			},false);
		},
		/*
		{
			deadline:101245152,//最终结束的时间戳,
			callback:function (){},//时间结束后的方法,
			secdom:secdom,//秒的dom节点,
		}
		*/
		timerSecond:function(agmt_time){  
			var second,bools,dates=new Date(),nowtime=Math.round(agmt_time.deadline-dates.getTime()/1000);
			if(nowtime<1){
				nowtime=0;
			}
			init();
			function init(){
				second=nowtime;
				bools=[false];
				if(second<10){
					agmt_time.secdom.innerHTML='0'+second;
				}
				setTimeout(secondRun,1000);
			}
			function secondRun(){
				if(second>0){
					if(((Math.round(new Date().getTime()/1000)+nowtime)-agmt_time.deadline)<3){
						second--;
						nowtime--;
						if(second<10){
							agmt_time.secdom.innerHTML='0'+second;
						}else{
							agmt_time.secdom.innerHTML=second;
						}
						setTimeout(secondRun,1000);
					}else{
						nowtime=agmt_time.deadline-Math.round(new Date().getTime()/1000);
						init();
					}
				}else{
					bools[0]=true;
					doThing();
				}
			}
			function doThing(){
				if(bools[0]){
					if(agmt_time.callback!=''&&agmt_time.callback!=undefined){
						agmt_time.callback();
					}
				}
			}
		},
		/*
		{
			deadline:101245152,//最终结束的时间戳,
			callback:function (){},//时间结束后的方法,
			secdom:secdom,//秒的dom节点,
			mintdom:mintdom,//分钟的dom节点,
			hourdom:,hourdom,//小时的dom节点,
			daydom:daydom,//天的dom节点
		}
		*/
		timer:function(agmt_time){  
			var day,hour,minute,second,bools,dates=new Date(),nowtime=Math.round(agmt_time.deadline-dates.getTime()/1000);
			if(nowtime<1){
				nowtime=0;
			}
			init();
			function init(){
				day   =Math.floor(nowtime/86400);
				hour  =Math.floor((nowtime%86400)/3600);
				minute=Math.floor(((nowtime%86400)%3600)/60);
				second=Math.floor(((nowtime%86400)%3600)%60);
				bools=[false,false,false,false]; 
				if(second<10){
					agmt_time.secdom.innerHTML='0'+second;
				}else{
					agmt_time.secdom.innerHTML=second;
				}
				if(minute<10){
					agmt_time.mintdom.innerHTML='0'+minute;
				}else{
					agmt_time.mintdom.innerHTML=minute;
				}
				if(hour<10){
					agmt_time.hourdom.innerHTML='0'+hour;
				}else{
					agmt_time.hourdom.innerHTML=hour;
				}
				
				if(day<10){
					agmt_time.daydom.innerHTML='0'+day;
				}else{
					agmt_time.daydom.innerHTML=day;
				}
				
				setTimeout(secondRun,1000);	
			}
			function secondRun(){
				if(second>0){
					if(((Math.round(new Date().getTime()/1000)+nowtime)-agmt_time.deadline)<3){
						second--;
						nowtime--;
						if(second<10){
							agmt_time.secdom.innerHTML='0'+second;
						}else{
							agmt_time.secdom.innerHTML=second;
						}
						setTimeout(secondRun,1000);
					}else{
						nowtime=agmt_time.deadline-Math.round(new Date().getTime()/1000);
						init();
					}
				}else{
					bools[0]=true;
					minuteRun();
				}
			}
			function minuteRun(){
				if(minute>0){
					minute--;
					if(minute<10){
						agmt_time.mintdom.innerHTML='0'+minute;
					}else{
						agmt_time.mintdom.innerHTML=minute;
					}
					bools[0]=false;
					second=60;
					secondRun();
				}else{
					bools[1]=true;
					hourRun();
				}
			}
			function hourRun(){
				if(hour>0){
					hour--;
					if(hour<10){
						agmt_time.hourdom.innerHTML='0'+hour;
					}else{
						agmt_time.hourdom.innerHTML=hour;
					}
					bools[1]=false;
					minute=60;
					minuteRun();
				}else{
					bools[2]=true;
					dayRun();
				}
			}
			function dayRun(){
				if(day>0){
					day--;
					
					if(day<10){
						agmt_time.daydom.innerHTML='0'+day;
					}else{
						agmt_time.daydom.innerHTML=day;
					}
					//agmt_time.daydom.innerHTML=day;
					
					bools[2]=false;
					hour=12;
					hourRun();
				}else{
					bools[3]=true;
					doThing();
				}
			}
			function doThing(){
				if(bools[0]&&bools[1]&&bools[2]&&bools[3]){
					if(agmt_time.callback!=''&&agmt_time.callback!=undefined){
						agmt_time.callback();
					}
				}
			}
			
		},
		
	};
	/*
	 *；类似于计时器的脚本动画
	 */
	window.requestAnimFrame=(function(){
		return  window.requestAnimationFrame || window.webkitRequestAnimationFrame || window.mozRequestAnimationFrame || function(callback) {setTimeout(callback, 1000 / 60);}
	}());
})(window)
