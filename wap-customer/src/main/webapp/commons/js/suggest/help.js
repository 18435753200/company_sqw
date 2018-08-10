window.onload=window.onresize=function(){
	document.documentElement.style.fontSize=document.documentElement.clientWidth/16+'px';	
};
document.addEventListener('DOMContentLoaded',function(){
	document.documentElement.style.fontSize=document.documentElement.clientWidth/16+'px';
	
	var aA=document.querySelectorAll('.headly1 a');
	var aDiv=document.querySelectorAll('.cently');

	for(var i=0;i<aA.length;i++){
		aA[i].index=i;
		aA[i].onclick=function(){
			for(var i=0;i<aA.length;i++){
				aA[i].className='';
				aDiv[i].style.display='none';
			}
			this.className='active';
			aDiv[this.index].style.display='block';
		};
	}
});
