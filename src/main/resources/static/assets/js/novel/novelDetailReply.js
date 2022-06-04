
let replyType ="best";
showList(subNovelNumber);

/*댓글 BEST , TOTAL 클릭 이벤트*/
$('.pink').on("click", function () {
    $('.pink').attr('src', '/images/icon/체크핑크.png');
    $('.gray').attr('src', '/images/icon/체크그레이.png');
    $('.mentsWrap').html("");
    replyType="best";
    startPage=1;
    showList(subNovelNumber);
})

$('.gray').on("click", function () {
    $('.pink').attr('src', '/images/icon/체크그레이.png');
    $('.gray').attr('src', '/images/icon/체크핑크.png');
    $('.mentsWrap').html("");
    replyType="current";
    startPage=1;
    showList(subNovelNumber);
})




//댓글 등록
$('.send').on("click", function () {
    let replyContent=$('textarea[name="replyContent"]').val();

    add({"novelNumber": novelNumber, "subNovelNumber": subNovelNumber,"replyContent": replyContent, "userNumber":userNumber},
        function (result) {
            console.log(result);
            showList(subNovelNumber);
            startPage=1;
        })
    $('textarea[name="replyContent"]').val("");

})

//댓글 추가
function add(reply, callback, error) {
    console.log("reply add 실행")
    $.ajax({
        type: "POST",
        url: "/reply/add",
        data: JSON.stringify(reply),
        contentType: "application/json; charset=utf-8",
        success: function(result, status, xhr){
            if(callback){
                callback(result);

            }
        },
        error: function(xhr, status, er){
            if(error){
                alert("댓글등록 실패");
            }
        }
    });
}

function showList(subNovelNumber){
    if(replyType ==="current"){
        getReplyListPageing(subNovelNumber,0,function (replyVOList, userVOList) {

            let str = "";
            let replyVO = replyVOList.content;
            let userVO = userVOList.content;

            if(replyVOList.content.length== null || replyVOList.content.totalElements == 0){
                $('.mentsWrap').html("<p>등록된 댓글이 없습니다.</p>");
                return;
            }
            for (let i=0; i<replyVO.length; i++){
                let replyUploadDate =replyVO[i].replyUpdateDate+"";
                str += "<div class='profilePlusText' style='position: relative;'>";
                // str += "<div class='Best'>Best</div>";
                str += "<div>";
                str += "<div class='profile'><img src='/myPage/display?fileName="+ userVO[i].userFilePath+"/"+userVO[i].userUuid + "_"+userVO[i].userFileName+"' class='img'/></div>";
                str += "</div>";
                str += "<div style='width: 678px;'  class='middleText'>";
                str += "<div style='font-size: 12px;'>" + userVO[i].userNickName + "</div>";
                str += "<div>" + replyVO[i].replyContent + "</div>";
                str += "</div>";
                str += "<div>";
                if(userNumber==replyVO[i].userVO.userNumber){
                    str+="<div class='xIcon' data-replynumber='"+replyVO[i].replyNumber+"'>";
                    str+="<img src='/images/icon/엑스.png' width='100%'>"
                    str+="</div>";
                }else{
                    str += "<div style='text-align: right; display: flex;'>";
                    str += "<button class='mentBtns mentBtns1' style='margin-right: 6px; border-color:#cbcbcb; color:#cbcbcb;' data-replynum ='" + replyVO[i].replyNumber + "' type='button'>";
                    str += "<img src='/images/icon/좋아요.png' height='20' width='20' class='likeBtn'/>";
                    str += "<span style='vertical-align: super;'>" + replyVO[i].replyUp + "</span>";
                    str += "</button>";
                    str += "<button class='mentBtns siren' style='border-color: #ef6e73;' id='" + replyVO[i].replyNumber + "'><img src='/images/icon/사이렌full.png' height='20' width='20'/></button>";
                    str += "</div>";
                }
                str += "<div class='date'>";
                str += replyUploadDate.substring(0, 10);
                str += "</div>";
                str += "</div>";
                str += "</div>";
            };
            $('.mentsWrap').html(str);
        });
        //댓글 추천순이라면
    }else{
        bestReplyListPageing(subNovelNumber, 0, function (replyVOList, userVOList) {
            let str = "";
            let replyVO = replyVOList.content;
            let userVO = userVOList.content;

            if (replyVO.totalElements == 0) {
                $('.mentsWrap').html("<p>등록된 댓글이 없습니다.</p>");
                return;
            }
            for (let i = 0; i < replyVO.length; i++) {
                let replyUploadDate = replyVO[i].replyUpdateDate + "";
                str += "<div class='profilePlusText' style='position: relative;'>";

                str += "<div class='Best'>Best</div>";
                str += "<div>";
                str += "<div class='profile'><img src='/myPage/display?fileName="+ userVO[i].userFilePath+"/"+userVO[i].userUuid + "_"+userVO[i].userFileName+"' class='img'/></div>";
                str += "</div>";
                str += "<div style='width: 678px;'  class='middleText'>";
                str += "<div style='font-size: 12px;'>" + userVO[i].userNickName + "</div>";
                str += "<div>" + replyVO[i].replyContent + "</div>";
                str += "</div>";
                str += "<div>";
                if(userNumber==replyVO[i].userVO.userNumber){
                    str+="<div class='xIcon' data-replynumber='"+replyVO[i].replyNumber+"'>";
                    str+="<img src='/images/icon/엑스.png' width='100%'>"
                    str+="</div>";
                }else{
                    str += "<div style='text-align: right; display: flex;'>";
                    str += "<button class='mentBtns mentBtns1' style='margin-right: 6px; border-color:#cbcbcb; color:#cbcbcb;' data-replynum ='" + replyVO[i].replyNumber + "' type='button'>";
                    str += "<img src='/images/icon/좋아요.png' height='20' width='20' class='likeBtn'/>";
                    str += "<span style='vertical-align: super;'>" + replyVO[i].replyUp + "</span>";
                    str += "</button>";
                    str += "<button class='mentBtns siren' style='border-color: #ef6e73;' id='" + replyVO[i].replyNumber + "'><img src='/images/icon/사이렌full.png' height='20' width='20'/></button>";
                    str += "</div>";
                }
                str += "<div class='date'>";
                str += replyUploadDate.substring(0, 10);
                str += "</div>";
                str += "</div>";
                str += "</div>";
            };
            $('.mentsWrap').html(str);
        });
    }
}

/*댓글 삭제 기능*/
$('.replyWrap').on("click", "div.xIcon",function () {
    let replynumber= $(this).data("replynumber");
    Swal.fire({
        title: '해당 댓글을 삭제하시겠습니까?',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#ef6e73',
        cancelButtonColor: '#a8a8a8',
        confirmButtonText: '네',
        cancelButtonText: '아니요',
        fontSize : 15,
    }).then((result) => {
        if (result.isConfirmed) {

            Swal.fire({
                confirmButtonColor: '#ef6e73',
                title:'삭제되었습니다.'
            });
            $.ajax({
                type:"get",
                url:"/reply/deleteReply?replyNumber="+replynumber,
                success:function () {
                    showList(subNovelNumber);
                },
                error:function (error) {
                    alert("댓글 신고 실패");
                    console.log(error);
                }

            });

        }
    })
});


/*댓글 좋아요 기능*/
$('.replyWrap').on("click", "button.mentBtns1",function () {
    let replyNum=$(this).data("replynum");
    if ($(this).children('img').attr('src') == '/images/icon/좋아요full.png') {
        let $number = parseInt($(this).children('span').html());
        $(this).css('border-color', '#cbcbcb');
        $(this).css('color', '#cbcbcb');
        $(this).children('img').attr('src', '/images/icon/좋아요.png');
        $(this).children('span').html($number - 1);
        replyUp({"replyNum":replyNum, "num":-1}, function(result){
        });

    } else {

        let $number = parseInt($(this).children('span').html());
        $(this).css('border-color', '#5A94FF');
        $(this).css('color', '#5A94FF');
        $(this).children('img').attr('src', '/images/icon/좋아요full.png');
        $(this).children('span').html($number + 1);
        replyUp({"replyNum":replyNum, "num":1},function(result) {
        });
    }

});
/*댓글 좋아요기능 ajax*/
function replyUp(reply, callback, error){

    $.ajax({
        type: "GET",
        url: "/reply/"+reply.replyNum+"/"+reply.num,
        success: function(result, status, xhr){
            if(callback){
                callback(result);
            }
        },
        error: function(xhr, status, er){
            if(error){
                error(er);
            }
        }

    });
}


/*댓글 신고*/
$('.replyWrap').on("click", "button.siren",function () {
    Swal.fire({
        title: '해당 댓글을 신고하시겠습니까?',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#ef6e73',
        cancelButtonColor: '#a8a8a8',
        confirmButtonText: '네',
        cancelButtonText: '아니요',
        fontSize : 15,
    }).then((result) => {
        if (result.isConfirmed) {
            Swal.fire({
                confirmButtonColor: '#ef6e73',
                title:'신고되었습니다.'
            })
            $.ajax({
                type:"get",
                url:"/novelDetail/siren?replyNumber="+$(this).attr('id'),
                success:function (result) {
                    console.log(result);
                },
                error:function (error) {
                    alert("댓글 신고 실패");
                    console.log(error);
                }

            })

        }

    })
});


let startPage=1;
$(window).scroll(function () {
    let scrT = $(window).scrollTop();

    if(scrT +1>= $(document).height() - $(window).height()){

        if(replyType==="best"){
            getReplyListPageing(subNovelNumber,startPage,function (replyVOList, userVOList) {

                let str = "";
                let replyVO = replyVOList.content;
                let userVO = userVOList.content;
                // let replyArea = $('.replyWrap');

                if(replyVO.totalElements == 0){
                    $('.mentsWrap').html("<p>등록된 댓글이 없습니다.</p>");
                    return;
                }
                for (let i=0; i<replyVO.length; i++){
                    let replyUploadDate =replyVO[i].replyUpdateDate+"";
                    str += "<div class='profilePlusText' style='position: relative;'>";
                    // str += "<div class='Best'>Best</div>";
                    str += "<div>";
                    str += "<div class='profile'><img src='/myPage/display?fileName="+ userVO[i].userFilePath+"/"+userVO[i].userUuid + "_"+userVO[i].userFileName+"' class='img'/></div>";
                    str += "</div>";
                    str += "<div style='width: 678px;' class='middleText'>";
                    str += "<div style='font-size: 12px;'>" + userVO[i].userNickName + "</div>";
                    str += "<div>" + replyVO[i].replyContent + "</div>";
                    str += "</div>";
                    str += "<div>";
                    if(userNumber==replyVO[i].userVO.userNumber){
                        str+="<div class='xIcon' data-replynumber='"+replyVO[i].replyNumber+"'>";
                        str+="<img src='/images/icon/엑스.png' width='100%'>"
                        str+="</div>";
                    }else{
                        str += "<div style='text-align: right; display: flex;'>";
                        str += "<button class='mentBtns mentBtns1' style='margin-right: 6px; border-color:#cbcbcb; color:#cbcbcb;' data-replynum ='" + replyVO[i].replyNumber + "' type='button'>";
                        str += "<img src='/images/icon/좋아요.png' height='20' width='20' class='likeBtn'/>";
                        str += "<span style='vertical-align: super;'>" + replyVO[i].replyUp + "</span>";
                        str += "</button>";
                        str += "<button class='mentBtns siren' style='border-color: #ef6e73;' id='" + replyVO[i].replyNumber + "'><img src='/images/icon/사이렌full.png' height='20' width='20'/></button>";
                        str += "</div>";
                    }
                    str += "<div class='date'>";
                    str += replyUploadDate.substring(0, 10);
                    str += "</div>";
                    str += "</div>";
                    str += "</div>";
                };
                $('.mentsWrap').append(str);
            });
            startPage++;
            //댓글 추천순이라면
        }else{
            bestReplyListPageing(subNovelNumber, startPage, function (replyVOList, userVOList) {

                console.log("메소드2");
                console.log(startPage);
                let str = "";
                let replyVO = replyVOList.content;
                let userVO = userVOList.content;

                if (replyVO.totalElements == 0) {
                    ReplyCmt.html("<p>등록된 댓글이 없습니다.</p>");
                    return;
                }
                for (let i = 0; i < replyVO.length; i++) {
                    let replyUploadDate = replyVO[i].replyUpdateDate + "";
                    str += "<div class='profilePlusText' style='position: relative;'>";
                    // str += "<div class='Best'>Best</div>";
                    str += "<div>";
                    str += "<div class='profile'><img src='/myPage/display?fileName="+ userVO[i].userFilePath+"/"+userVO[i].userUuid + "_"+userVO[i].userFileName+"' class='img'/></div>";
                    str += "</div>";
                    str += "<div style='width: 678px;' class='middleText'>";
                    str += "<div style='font-size: 12px;'>" + userVO[i].userNickName + "</div>";
                    str += "<div>" + replyVO[i].replyContent + "</div>";
                    str += "</div>";
                    str += "<div>";
                    if(userNumber==replyVO[i].userVO.userNumber){
                        str+="<div class='xIcon' data-replynumber='"+replyVO[i].replyNumber+"'>";
                        str+="<img src='/images/icon/엑스.png' width='100%'>"
                        str+="</div>";
                    }else{
                        str += "<div style='text-align: right; display: flex;'>";
                        str += "<button class='mentBtns mentBtns1' style='margin-right: 6px; border-color:#cbcbcb; color:#cbcbcb;' data-replynum ='" + replyVO[i].replyNumber + "' type='button'>";
                        str += "<img src='/images/icon/좋아요.png' height='20' width='20' class='likeBtn'/>";
                        str += "<span style='vertical-align: super;'>" + replyVO[i].replyUp + "</span>";
                        str += "</button>";
                        str += "<button class='mentBtns siren' style='border-color: #ef6e73;' id='" + replyVO[i].replyNumber + "'><img src='/images/icon/사이렌full.png' height='20' width='20'/></button>";
                        str += "</div>";
                    }
                    str += "<div class='date'>";
                    str += replyUploadDate.substring(0, 10);
                    str += "</div>";
                    str += "</div>";
                    str += "</div>";
                };
                $('.mentsWrap').append(str);
            });
            startPage++;
        }
    }
});

/*댓글 최신순 불러오기 */
function getReplyListPageing(subNovelNumber,startPage, callback, error) {

    $.ajax({
        type:"get",
        url:"/reply/getReplyUserDTOBySubNum?subNovelNumber="+subNovelNumber+"&page="+startPage,
        success:function (ReplyUserDTO) {
            if(callback){
                callback(ReplyUserDTO.replyVOList, ReplyUserDTO.userVOList);
            }
        },
        error:function (error) {
            alert("댓글불러오기실패");
        },
    });
}

/*댓글 추천순 불러오기 */
function bestReplyListPageing(subNovelNumber,startPage, callback, error) {

    $.ajax({
        type:"get",
        url:"/reply/getReplyUpUserDTOBySubNum?subNovelNumber="+subNovelNumber+"&page="+startPage,
        success:function (ReplyUserDTO) {
            if(callback){
                callback(ReplyUserDTO.replyVOList, ReplyUserDTO.userVOList);
            }
        },
        error:function (error) {
            alert("댓글불러오기실패");
        },
    });
}