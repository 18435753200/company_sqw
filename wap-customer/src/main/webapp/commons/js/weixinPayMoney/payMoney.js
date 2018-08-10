//点击键盘数字输入到价格显示

function keypress(e){

    e.preventDefault();
    var target = e.target;
    var value = target.getAttribute('data-value');
    var dot = valueCur.match(/\.\d{2,}$/);
    if(!value || (value !== 'delete' && dot)){
        return;
    }
    var valueOld = valueCur;

    switch(value){
        case '0' :
            valueCur = valueCur === '0' ? valueCur : valueCur + value;
            break;
        case 'dot' :
            valueCur = valueCur === '' ? valueCur : valueCur.indexOf('.') > -1 ? valueCur : valueCur + '.';
            break;
        case 'delete' :
            valueCur = valueCur.slice(0,valueCur.length-1);
            break;
        default :
            valueCur = valueCur === '0' ? value : valueCur + value;
    }

    if (Number(valueCur) > 100000){

        tips.show("消费总额不可大于10万");
        valueCur = valueOld;
    }

    format();
}

//format
function format(){
    var arr = valueCur.split('.');
    var right = arr.length === 2 ? '.'+arr[1] : '';
    var num = arr[0];
    var left = '';
    while(num.length > 3){
        left = ',' + num.slice(-3) + left;
        num = num.slice(0,num.length - 3);
    }
    left = num + left;
    valueFormat = left+right;
    valueFinal = valueCur === '' ? 0 : parseFloat(valueCur);
    check();
}

//check
function check(){
    amount.innerHTML = valueFormat;
}


var keyboard = getId('keyboard');
var amount = getId('amount');
var valueCur = '';


//添加点击事件
new Hammer(keyboard).on('tap',keypress);
