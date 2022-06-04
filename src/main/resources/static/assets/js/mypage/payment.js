var active = document.getElementsByClassName("active");

function handleClick(event) {
    console.log(event.target);
    console.log(event.target.classList);

    active[0].classList.remove("clicked");
    if (event.target.classList[1] === "clicked") {
        event.target.classList.remove("clicked");
    } else {
        for (var i = 0; i < active.length; i++) {
            active[i].classList.remove("clicked");
        }

        event.target.classList.add("clicked");
    }
}
function init() {
    active[0].classList.add("clicked");
    for (var i = 0; i < active.length; i++) {
        active[i].addEventListener("click", handleClick);
    }
}
init();


//left_block
var left = document.getElementsByClassName("left");

function Click(event) {
    console.log(event.target);
    console.log(event.target.classList);

    left[0].classList.remove("oned");
    if (event.target.classList[1] === "oned") {
        event.target.classList.remove("oned");
    } else {
        for (var i = 0; i < active.length; i++) {
            left[i].classList.remove("oned");
        }

        event.target.classList.add("oned");
    }
}
function test() {
    left[5].classList.add("oned");
    for (var i = 0; i < active.length; i++) {
        left[i].addEventListener("on", Click);
    }
}
test();


//날짜 유효성 검사
$('#btnSearch').click(function () {
    var dateFrom = document.getElementById('dateFrom'); //시작일
    var dateTo = document.getElementById('dateTo'); //종료일
    var today = new Date(); //오늘 날짜

    //시작일
    dateFrom = new Date(dateFrom.value);
    var fromYear = dateFrom.getFullYear();
    var fromMonth = dateFrom.getMonth() + 1;
    var fromDay = dateFrom.getDate();

    //날짜 지정을 하지 않았을 때 NaN이 발생하여 0으로 처리
    if (isNaN(fromYear) || isNaN(fromMonth) || isNaN(fromDay)){
        fromYear = 0;
        fromMonth = 0;
        fromDay = 0;
    }
    dateFrom = fromYear +'/'+ fromMonth +'/'+fromDay;


    //종료일
    dateTo = new Date(dateTo.value);
    var toYear = dateTo.getFullYear();
    var toMonth = dateTo.getMonth() + 1;
    var toDay = dateTo.getDate();
    //날짜 지정을 하지 않았을 때 NaN이 발생하여 0으로 처리
    if (isNaN(toYear) || isNaN(toMonth) || isNaN(toDay)){
        toYear = 0;
        toMonth = 0;
        toDay = 0;
    }
    dateTo = toYear +'/'+ toMonth +'/'+toDay;

    //오늘날짜 날짜 형식으로 지정
    var todayYear = today.getFullYear();
    var todayMonth = today.getMonth() + 1;
    var todayDay = today.getDate();
    today = todayYear +'/'+ todayMonth +'/'+todayDay;

    // 날짜 조회 시, 시작일이 오늘 날짜보다는 크고, 종료일이 시작일보다는 커야하기 때문에 조건을 걸어줌
    if(dateFrom <= today && dateTo >= dateFrom ){
        return true;
    } else {
        alert("해당 기간의 조회가 불가능합니다.");
    }
}); //click() end


//내 포인트 / 내 후원 내역 이동
document.querySelector('.payTableWrap2').style.display = 'none';
document.querySelector('.myPoint').addEventListener('click',function(){
    clickchange1 ()
})
document.querySelector('.mySupport').addEventListener('click',function(){
    clickchange2 ()
})

function clickchange1() {
    document.querySelector('.payTableWrap2').style.display = 'none';
    document.querySelector('.payTableWrap').style.display = "block";
}

function clickchange2() {
    document.querySelector('.payTableWrap').style.display = 'none';
    document.querySelector('.payTableWrap2').style.display = 'block';
}

//페이지 이동하는 form태그 가져와서 action경로 직접 넣는다


$(".page-link").click(function(e){
    e.preventDefault();
    $(pageForm).find("input[name='pageNum']").val($(this).attr("href")); // 사용자가 클릭한 페이지
    // $(pageForm).attr("action", "/myLibrary/payList?userNumber=" + userNumber);
    $(pageForm).submit();
})