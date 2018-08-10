var CONTEXTPATH = $("#conPath").val();
function countChar(textareaID,spanID,maxNum)
{
    //得到输入的字符的长度
var NowNum = document.getElementById(textareaID).value.length;
//判断输入的长度是否超过规定的长度
if(NowNum>maxNum)
{
   //如果超过就截取规定长度的内容
document.getElementById(textareaID).value = document.getElementById(textareaID).value.substring(0,maxNum);
}
else
{
    //得到当前的输入长度并且显示在页面上
document.getElementById(spanID).innerHTML = NowNum;
}
}
//得到当前的输入长度并且显示在页面上
function SetLength(textareaID,spanID)
{
var NowNum = document.getElementById(textareaID).value.length;

document.getElementById(spanID).innerHTML = NowNum;
}

function PageInit()
{
SetLength('txtF_Content','counter');
}
function operateNotice(avg){
	var noticeId = $("#noticeId").val();
	var txtF_Content = $("#txtF_Content").val();
	$.ajax({
		type:'post',
		url:CONTEXTPATH+'/infrastructure/operateNotice',
		data: 'noticeId='+noticeId+'&content='+txtF_Content+'&type='+avg,
		success:function(data){
			if(data=='1'){
				tipMessage("操作成功！",function(){
					location.href=CONTEXTPATH+'/infrastructure/selectNotice';
				});
			}else{
				tipMessage("操作失败，请检查后重试！",function(){
				});
			}
			
		}
	});
}