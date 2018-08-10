/**
 * JS中全局的变量
 */
var rootpath = "/cs/cs";

/**
 * 刷新验证码
 * 
 * ~获取验证码的jquery对象
 * ~刷新请求(给验证码的src重新赋值)
 * 
 * @param imgCode:验证码图片的id
 * @returns
 */
function refreshCode(imgCode)
{
	var imgCodeJq = $("#" + imgCode);
	imgCodeJq.attr("src", rootpath + "/code.do?now="+ new Date())
	return false ; 
}

