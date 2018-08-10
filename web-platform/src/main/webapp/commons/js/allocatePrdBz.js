
function opetatorStock() {
	var str = document.getElementsByName("skuId");
	var objarray = str.length;
	// var chestr = "";
	for ( var i = 0; i < objarray; i++) {
		if (str[i].checked == true) {
			// chestr += str[i].value + ",";
			var tr = $('#ope tr').eq(str[i].value);
			var htm = appendTr(tr,$('#sto tr:last'));

			var st8 = tr.children('td').eq(8).text();
			var st12 = tr.children('td').eq(12).text();
			var st11 = tr.children('td').eq(11).text();

			var flag = vaild(st12, st8,st11);
			if (flag) {
				// $('#sto').append('<tr>' + tr.html() + '</tr>');
				$('#sto').append(htm);
			}
		}
	}

	document.getElementById("pud").style.display = "";
}

function appendTr(obj,lastTr) {
	
	var th0;
	if(lastTr.find("td:eq(0)").text()=="")
	{
		th0=1;
	}else{
		th0=parseInt(lastTr.find("td:eq(0)").text())+1;
	}
	var html = "<tr><td>"+th0+"</td><td><input type='checkbox' name='skuName'  value='"+th0+"'></td><td>"+obj.children('td').eq(2).text()+"</td><td>"+obj.children('td').eq(3).text()+"</td><td>"+obj.children('td').eq(4).text()+"</td><td>"+obj.children('td').eq(5).text()+"</td><td>"+obj.children('td').eq(6).text()+"</td><td>"+obj.children('td').eq(7).text()+"</td><td>"+obj.children('td').eq(8).text()+"</td><td>"+obj.children('td').eq(9).text()+"</td><td style='display:none'>"+obj.children('td').eq(10).text()+"</td><td style='display:none'>"+obj.children('td').eq(11).text()+"</td><td style='display:none'>"+obj.children('td').eq(12).text()+"</td><td>"+obj.children('td').eq(13).text()+"</td><td style='display:none'>"+obj.children('td').eq(14).text()+"</td><td style='display:none'>"+obj.children('td').eq(15).text()+"</td><td style='display:none'>"+obj.children('td').eq(16).text()+"</td><td>"+obj.children('td').eq(17).text()+"</td></tr>";
	console.info(html);
	return html;
}

function appendNewTr(obj) {
	//var html = "<tr><td >"+obj.children('td').eq(0).text()+"</td><td ><input type='checkbox' name='skuName' value='"+obj.children('td').eq(0).text()+"'></td><td >"+obj.children('td').eq(2).text()+"</td><td >"+obj.children('td').eq(3).text()+"</td><td >"+obj.children('td').eq(4).text()+"</td><td >"+obj.children('td').eq(5).text()+"</td><td >"+obj.children('td').eq(6).text()+"</td><td >"+obj.children('td').eq(7).text()+"</td><td ><input type='text' name='allQty' id='allQty"+obj.children('td').eq(0).text()+"'/></td><td >"+obj.children('td').eq(11).text()+"</td><td >"+obj.children('td').eq(8).text()+"</td><td >"+obj.children('td').eq(9).text()+"</td><td >"+obj.children('td').eq(10).text()+"</td><td >"+obj.children('td').eq(12).text()+"</td><td >"+obj.children('td').eq(13).text()+"</td></tr>";
	//var html = "<tr><td>"+obj.children('td').eq(0).text()+"</td><td><input type='checkbox' name='skuName' value='"+obj.children('td').eq(0).text()+"'></td><td><input type='text' id='pcode"+obj.children('td').eq(0).text()+"' name='pcode"+obj.children('td').eq(0).text()+"' value='"+obj.children('td').eq(2).text()+"' readonly='readonly'></td><td><input type='text' id='barCode"+obj.children('td').eq(0).text()+"' name='barCode"+obj.children('td').eq(0).text()+"' value='"+obj.children('td').eq(3).text()+"'readonly='readonly'></td><td><input type='text' id='pname"+obj.children('td').eq(0).text()+"' name='pname"+obj.children('td').eq(0).text()+"' value='"+obj.children('td').eq(4).text()+"'readonly='readonly'></td><td><input type='text' id='format"+obj.children('td').eq(0).text()+"' name='format"+obj.children('td').eq(0).text()+"' value='"+obj.children('td').eq(5).text()+"'readonly='readonly'></td><td><input type='text' id='unit"+obj.children('td').eq(0).text()+"' name='unit"+obj.children('td').eq(0).text()+"' value='"+obj.children('td').eq(6).text()+"'readonly='readonly'></td><td><input type='text' id='stockQty"+obj.children('td').eq(0).text()+"' name='stockQty"+obj.children('td').eq(0).text()+"' value='"+obj.children('td').eq(7).text()+"'readonly='readonly'></td><td><input type='text' id='transferQty"+obj.children('td').eq(0).text()+"' name='transferQty"+obj.children('td').eq(0).text()+"' value=''></td><td><input type='text' id='isgenuine"+obj.children('td').eq(0).text()+"' name='isgenuine"+obj.children('td').eq(0).text()+"' value='"+obj.children('td').eq(11).text()+"'readonly='readonly'></td><td><input type='text' id='batchNumber"+obj.children('td').eq(0).text()+"' name='batchNumber"+obj.children('td').eq(0).text()+"' value='"+obj.children('td').eq(8).text()+"'readonly='readonly'></td><td><input type='text' id='batchId"+obj.children('td').eq(0).text()+"' name='batchId"+obj.children('td').eq(0).text()+"' value='"+obj.children('td').eq(9).text()+"'readonly='readonly'></td><td><input type='text' id='productionDate"+obj.children('td').eq(0).text()+"' name='productionDate"+obj.children('td').eq(0).text()+"' value='"+obj.children('td').eq(10).text()+"'readonly='readonly'></td><td><input type='text' id='skuId"+obj.children('td').eq(0).text()+"' name='skuId"+obj.children('td').eq(0).text()+"' value='"+obj.children('td').eq(12).text()+"'readonly='readonly'></td><td><input type='text' id='pid"+obj.children('td').eq(0).text()+"' name='pid"+obj.children('td').eq(0).text()+"' value='"+obj.children('td').eq(13).text()+"'readonly='readonly'></td></tr>";
	var html = "<tr class='sotClass'><td>"+obj.children('td').eq(0).text().trim()+"</td><td><input class='sm' type='checkbox' name='skuName' value='"+obj.children('td').eq(0).text().trim()+"'></td><td><input type='text' id='pcode"+obj.children('td').eq(0).text().trim()+"' name='pcode' value='"+obj.children('td').eq(2).text().trim()+"' readonly='readonly'></td><td><input type='text' id='barCode"+obj.children('td').eq(0).text().trim()+"' name='barCode' value='"+obj.children('td').eq(3).text().trim()+"'readonly='readonly'></td><td><input type='text' id='pname"+obj.children('td').eq(0).text().trim()+"' name='pname' value='"+obj.children('td').eq(4).text().trim()+"'readonly='readonly'></td><td><input type='text' id='format"+obj.children('td').eq(0).text().trim()+"' name='format' value='"+obj.children('td').eq(5).text().trim()+"'readonly='readonly'></td><td><input type='text' id='unit"+obj.children('td').eq(0).text().trim()+"' name='unit' value='"+obj.children('td').eq(6).text().trim()+"'readonly='readonly'></td><td><input type='text' id='stockQty"+obj.children('td').eq(0).text().trim()+"' name='stockQty' value='"+obj.children('td').eq(7).text().trim()+"'readonly='readonly'></td><td><input type='text' id='transferQty"+obj.children('td').eq(0).text().trim()+"' name='transferQty'  onblur='onblurInfo($(this),"+obj.children('td').eq(7).text().trim()+");'  value=''></td><td><input type='text' id='isgenuine"+obj.children('td').eq(0).text().trim()+"' name='isgenuine' value='"+obj.children('td').eq(11).text().trim()+"'readonly='readonly'></td><td><input type='text' id='batchNumber"+obj.children('td').eq(0).text().trim()+"' name='batchNumber' value='"+obj.children('td').eq(8).text().trim()+"'readonly='readonly'></td><td><input type='text' id='batchId"+obj.children('td').eq(0).text().trim()+"' name='batchId' value='"+obj.children('td').eq(9).text().trim()+"'readonly='readonly'></td><td><input type='text' id='productionDate"+obj.children('td').eq(0).text().trim()+"' name='productionDate' value='"+obj.children('td').eq(10).text().trim()+"'readonly='readonly'></td><td style='display:none'><input type='text' id='skuId"+obj.children('td').eq(0).text().trim()+"' name='skuId' value='"+obj.children('td').eq(12).text().trim()+"'readonly='readonly'></td><td style='display:none'><input type='text' id='pid"+obj.children('td').eq(0).text().trim()+"' name='pid' value='"+obj.children('td').eq(13).text().trim()+"'readonly='readonly'></td><td style='display:none'><input type='text' id='price"+obj.children('td').eq(0).text().trim()+"' name='price' value='"+obj.children('td').eq(14).text().trim()+"'readonly='readonly'></td></tr>";
	//var html = "<tr class='sotClass'><td>"+obj.children('td').eq(0).text().trim()+"</td><td><input type='checkbox' name='skuName' value='"+obj.children('td').eq(0).text().trim()+"'></td><td><input type='text' id='pcode"+obj.children('td').eq(0).text().trim()+"' name='pcode' value='"+obj.children('td').eq(2).text().trim()+"' readonly='readonly'></td><td><input type='text' id='barCode"+obj.children('td').eq(0).text().trim()+"' name='barCode' value='"+obj.children('td').eq(3).text().trim()+"'readonly='readonly'></td><td><input type='text' id='pname"+obj.children('td').eq(0).text().trim()+"' name='pname' value='"+obj.children('td').eq(4).text().trim()+"'readonly='readonly'></td><td><input type='text' id='format"+obj.children('td').eq(0).text().trim()+"' name='format' value='"+obj.children('td').eq(5).text().trim()+"'readonly='readonly'></td><td><input type='text' id='unit"+obj.children('td').eq(0).text().trim()+"' name='unit' value='"+obj.children('td').eq(6).text().trim()+"'readonly='readonly'></td><td><input type='text' id='stockQty"+obj.children('td').eq(0).text().trim()+"' name='stockQty' value='"+obj.children('td').eq(7).text().trim()+"'readonly='readonly'></td><td><input type='text' id='transferQty"+obj.children('td').eq(0).text().trim()+"' name='transferQty'  onblur='onblurInfo("+obj.children('td').eq(0).text().trim()+");'  value=''></td><td><input type='text' id='isgenuine"+obj.children('td').eq(0).text().trim()+"' name='isgenuine' value='"+obj.children('td').eq(11).text().trim()+"'readonly='readonly'></td><td><input type='text' id='batchNumber"+obj.children('td').eq(0).text().trim()+"' name='batchNumber' value='"+obj.children('td').eq(8).text().trim()+"'readonly='readonly'></td><td><input type='text' id='batchId"+obj.children('td').eq(0).text().trim()+"' name='batchId' value='"+obj.children('td').eq(9).text().trim()+"'readonly='readonly'></td><td><input type='text' id='productionDate"+obj.children('td').eq(0).text().trim()+"' name='productionDate' value='"+obj.children('td').eq(10).text().trim()+"'readonly='readonly'></td><td><input type='text' id='skuId"+obj.children('td').eq(0).text().trim()+"' name='skuId' value='"+obj.children('td').eq(12).text().trim()+"'readonly='readonly'></td><td><input type='text' id='pid"+obj.children('td').eq(0).text().trim()+"' name='pid' value='"+obj.children('td').eq(13).text().trim()+"'readonly='readonly'></td></tr>";
	return html;
}

function vaild(skuID, butchNo,isgenuine) {
	var flag = false;

	var len = $("#sto tr").length;
	for ( var i = 1; i <= len; i++) {
		var tr = $('#sto tr').eq(i);
		var st8 = tr.children('td').eq(8).text();
		var st11 = tr.children('td').eq(11).text();
		var st12 = tr.children('td').eq(12).text();
		if (skuID == st12 && butchNo == st8 &&isgenuine==st11) {
			flag = false;
			break;
		} else {
			flag = true;
		}
	}

	return flag;
}

function opetatorCompe() {
	var str = document.getElementsByName("skuName");
	var objarray = str.length;
	for ( var i = 0; i < objarray; i++) {
		if (str[i].checked == true) {
			 var tr = $('#sto tr').eq(str[i].value);
			
			 var pWindow=window.dialogArguments;
			 //console.info("--0"+tr.children('td').eq(0).html()+"--1"+tr.children('td').eq(1).html()+"--2"+tr.children('td').eq(2).html()+"--3"+tr.children('td').eq(3).html()+"--4"+tr.children('td').eq(4).html()+"--5"+tr.children('td').eq(5).html()+"--6"+tr.children('td').eq(6).html()+"--7"+tr.children('td').eq(7).html()+"--8"+tr.children('td').eq(8).html()+"--9"+tr.children('td').eq(9).html()+"--10"+tr.children('td').eq(10).html()+"--11"+tr.children('td').eq(11).html()+"--12"+tr.children('td').eq(12).html()+"--13"+tr.children('td').eq(13).html()+"--14"+tr.children('td').eq(14).html()+"--15"+tr.children('td').eq(15).html()+"--16"+tr.children('td').eq(16).html()+"--17"+tr.children('td').eq(17).html()+"--18"+tr.children('td').eq(18).html());
			 if(pWindow != null){
				 
				 pWindow.doThingsAfterAdd(tr,tr.children('td').eq(12).html(),tr.children('td').eq(8).html(),tr.children('td').eq(11).html());
				 window.close();
			 }else{
				 window.opener.doThingsAfterAdd(tr,tr.children('td').eq(12).html(),tr.children('td').eq(8).html(),tr.children('td').eq(11).html());
				 window.close();
			 }
			//window.parent.document.test();
		}
	}
}