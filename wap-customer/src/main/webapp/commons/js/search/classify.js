function addEvent(obj,sEvent,fn){
	if(obj.addEventListener){
		obj.addEventListener(sEvent,fn,false);
	}else{
		obj.attachEvent('on'+sEvent,fn);
	}
}

function screenheight(){
	var oScr=document.querySelector('.screen');
	var oUl=document.querySelectorAll('.list ul');
	
	oScr.style.height=oUl[0].offsetHeight*(oUl.length+3)+'px';
}


function tab(){
	var aLi=document.querySelectorAll('.grabble li');
	var aDiv=document.querySelectorAll('.choose div');
	var oScr=document.querySelector('.list .screen');
	console.log(oScr);
	for(var i=0;i<aLi.length;i++){
		(function(index){
			addEvent(aLi[i],'click',function(ev){
				oEvent=ev||event;
				oScr.style.display='block';
				window.oEvent.cancelBubble = true;
				tab1(index);
			});
		})(i);
	}
	addEvent(document,'click',function(){
		//oScr.style.display='none';
		for(var i=0;i<aLi.length;i++){
			aLi[i].className='';
			//aDiv[i].classList.remove('show');			
		}
	});
	function tab1(index){
		for(var i=0;i<aLi.length;i++){
			aLi[i].className='';
			aDiv[i].classList.remove('show');
		}
		aLi[index].className='cur';
		aDiv[index].classList.add('show');
		if(aDiv[index].classList.contains('may')){
			var aLi1=aDiv[index].querySelectorAll('li');
			for(var i=0;i<aLi1.length;i++){
				aLi1[i].onclick=function(){
					this.parentNode.parentNode.classList.remove('show');
					oScr.style.display='none';
					for(var j=0;j<aLi.length;j++){
						aLi[j].className='';
					}
				}
			}
		}
	}
}

function scrol(){
	var oDiv1=document.querySelector('.grabble');
	var oDiv2=document.querySelector('.dresses');
	var aLi=oDiv1.querySelectorAll('li');

	window.onscroll=function(){
		var scrollTop=document.documentElement.scrollTop||document.body.scrollTop;
		var top=oDiv1.offsetTop||oDiv2.offsetTop;
		if(scrollTop>=top){
			oDiv1.style.position='fixed';
			oDiv2.style.display='block';
		}else{
			oDiv1.style.position='';
			oDiv2.style.display='none';
		}
		for(var i=0;i<aLi.length;i++){
			addEvent(aLi[i],'click',function(){
				document.documentElement.scrollTop=0;
				document.body.scrollTop=0;
				oDiv1.style.position='';
				oDiv2.style.display='none';				
			});
		}
	};
}

