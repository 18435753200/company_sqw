//查看合伙人信息后的返回按钮
    function returnAgentPage(parentId){
    	document.getElementById("f").action='../supplier/getAgentPage';
    	document.getElementById("parid").value=parentId;
    	document.getElementById("f").submit();
    	document.getElementById("f").action='';
    	document.getElementById("parid").value='';
    }