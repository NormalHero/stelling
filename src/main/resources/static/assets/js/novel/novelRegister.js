let $rutin =$('.rutin');
let $free =$('.free');

/*정기 연재*/
$rutin.on("click", function () {
    $rutin.css('background-color', '#ef6273');
    $rutin.css('color', 'white');
    $rutin.css('border', 'none');

    $free.css('background-color', 'white');
    $free.css('color', '#ef6273');
    $free.css('border', '1px solid #ef6273');

    $('.days').slideDown();
});

/*자유 연재*/
$free.on("click", function () {
    $free.css('background-color', '#ef6273');
    $free.css('color', 'white');
    $free.css('border', 'none');

    $rutin.css('background-color', 'white');
    $rutin.css('color', '#ef6273');
    $rutin.css('border', '1px solid #ef6273');

    $('.days').slideUp();
});




/* 화살표 함수 */
const label = document.querySelector('.label');
const options = document.querySelectorAll('.optionItem');

// 클릭한 옵션의 텍스트를 라벨 안에 넣음
const handleSelect = (item) => {
    label.parentNode.classList.remove('active');
    label.innerHTML = item.textContent;
    $(label).val(item.textContent);
}
// 옵션 클릭시 클릭한 옵션을 넘김

options.forEach(option => {
    option.addEventListener('click', () => handleSelect(option))

})

// 라벨을 클릭시 옵션 목록이 열림/닫힘
label.addEventListener('click', () => {
    if(label.parentNode.classList.contains('active')) {
        label.parentNode.classList.remove('active');
    } else {
        label.parentNode.classList.add('active');
    }
})

let regex = new RegExp("(.*?)\.(exe|sh|zip|alz|txt)$");
let maxSize = 1024 * 1024 * 40;

$(document).ready(function() {


    function checkFile(fileName, fileSize) {
        if (regex.test(fileName)) {
            alert("업로드할 수 없는 파일의 형식입니다.");
            return false;
        }
        if (fileSize > maxSize) {
            alert("파일 사이즈 초과");
            return false;
        }
        return true;
    }

         let fileTarget = $('#file');
        fileTarget.on('change', function(){ // 값이 변경되면
        let cur=$(".filebox input[type='file']").val();
        let fileOrigin = fileTarget[0].files;

        if(checkFile(fileOrigin[0].name,fileOrigin[0].size)){
            $(".upload-name").val(cur.substring(12, cur.length));

            // $('input[name="novelFileName"]').val(cur.substring(12, cur.length));
        }

    });
});

/*샘플이미지 썸네일만들기*/
$('.imgs').on("click", function () {
    let imgSrc =$(this).attr("src");
    $('.thumnail>img').attr('src',imgSrc);
    $('.thum').val("");
    $('.upload-name').val("");

    $('input[name="novelFileName"]').val(imgSrc.substring(8, imgSrc.length));
});



/*input[type=file] 썸네일만들기*/
function setThumbnail(event) {
    let reader = new FileReader();
    reader.onload = function(event) {
        let img = document.createElement("img");
        img.setAttribute("src", event.target.result);
        $('.thumnail>img').attr('src', $(img).attr('src'));
        // document.querySelector("div#image_container").appendChild(img);
    };

        if (!regex.test(event.target.files[0].name) && event.target.files[0].size<=maxSize) {
            reader.readAsDataURL(event.target.files[0])
         }
    }




//해쉬태그
$(document).ready(function () {
    let tag = {};
    let counter = 0;

    // 입력한 값을 태그로 생성한다.
    function addTag (value) {
        tag[counter] = value;
        counter++; // del-btn 의 고유 id 가 된다.
    }


    $("#tag").on("keypress", function (e) {
        var self = $(this);

        //엔터나 스페이스바 눌렀을때 실행
        if (e.key === "Enter" || e.keyCode == 32) {
            var tagValue = self.val(); // 값 가져오기
            // 해시태그 값 없으면 실행X
            if (tagValue !== "") {
                // 같은 태그가 있는지 검사한다. 있다면 해당값이 array 로 return 된다.
                var result = Object.values(tag).filter(function (word) {
                    return word === tagValue;
                })
                // 해시태그가 중복되었는지 확인
                if (result.length == 0) {
                    //태그 6개 제한
                    if(counter>5){
                        alert("태그는 6개까지만 가능합니다.");
                    }else {
                        $("#tag-list").append("<li class='tag-item'>" +tagValue + "<span class='del-btn' idx='" + counter + "'>&nbsp;x</span></li>");
                        addTag(tagValue);
                        self.val("");
                    }
                } else {
                    alert("태그값이 중복됩니다.");
                }
            }
            e.preventDefault(); // SpaceBar 시 빈공간이 생기지 않도록 방지
        }
    });

    // 삭제 버튼
    // 인덱스 검사 후 삭제
    $(document).on("click", ".del-btn", function (e) {
        var index = $(this).attr("idx");
        tag[index] = "";
        $(this).parent().remove();
        counter--;
    });
});

//작품 정기, 자유 버튼 선택에따라 폼데이터에 보낼 값 설정
$('.btn').on("click", function () {
    if($(this).hasClass('rutin')){
        $("input[name='novelSerialsStatus']").val(1);
    }else if($(this).hasClass('free')){
       $("input:checkbox[name='novelday']").prop("checked", false);
        $("input[name='novelSerialsStatus']").val(2);
    }
})



$("input[type='file']").change(function(e){
    /*소설 메인이미지 저장하기*/
    let inputFile = $("input[type='file']");
    let files = inputFile[0].files;
    let formData = new FormData();
    for(let i = 0; i < files.length; i++){
        formData.append("uploadFile", files[i]);
    }
    /*formdata action으로 변경 */
    $.ajax({
        url: "/novel/uploadAjaxAction",
        data: formData,
        type: "POST",
        // 현재 설정된 헤더 설정을 기본 방식으로 변경하지 못하도록 false로 설정
        processData: false,
        contentType: false,
        success: function(result){
            $(result).each(function (i, item) {
                $('input[name="novelFileName"]').val(item.novelFileName);
                $('input[name="novelUploadPath"]').val(item.novelUploadPath);
                $('input[name="novelUUID"]').val(item.novelUUID);
            });
        },
        error:function () {
            alert("소설메인이미지 저장 실패");
        }

    });


});
    /*form데이터 보내기*/
    $('#insertBtn').on("click", function () {
        if(!$('.titleInput').val()){
            alert("작품명을 입력해주세요");
            return;
        }
        if(!$('textarea[name="novelIntro"]').val()){
            alert("작품소개를 입력해주세요");
            return;
        }

        /*해쉬태그*/
        let hashtag = $('#tag-list').text();
        hashtag = hashtag.replace(/x/gi, " ");
        hashtag = hashtag.replace(/&nbsp/gi, ",");
        $('input[name="novelHashtag"]').val(hashtag);
        $('input[name="novelCategory"]').val($(label).val());
        let title = $('.titleInput').val();
        $('input[name="novelTitle"]').val(title.trim());

        /*연재요일(월~일)*/
        $("input[type=checkbox]").each(function () {
            if ($(this).is(':checked')) {
                $('input[name="' + $(this).val() + '"]').val(1);
            }
        });

        /*폼태그 전송*/
        register.submit();

    });
