function getSubNovelList(novelNumber, callback, error) {

    $.ajax({
        url : "/novel/getSubNovelList?novelNumber="+novelNumber,
        // dataType: "json",
        type: "GET",
        // async: false,
        success: function(result) {
            callback(result);
        },
        error: function (){alert("실패");}
    });


}

function getNovelVo(novelNumber, callback, error) {

    $.ajax({
        url : "/novel/getNovelVO/"+novelNumber,
        // dataType: "json",
        type: "GET",
        // async: false,
        success: function(result) {
            callback(result);
        },
        error: function (){
            if(error){
                console.log(error);
            }
            alert("실패");}
    });
}


function getOrderBySubNovelList(novelNumber, callback, error){
    $.ajax({
             url : "/novel/orderBySubNovelList?novelNumber="+novelNumber,
             type: "get",
             success: function(result) {
                callback(result);

             },
               error: function () {
                   if (error) {
                       alert("실패");
                   }
               }
           });
}



 // function ResCnt() {
 //
 //    let result= [];
 //    $.ajax({
 //      url : "/dummy/Res.json",
 //
 //      dataType: "json",
 //      type: "get",
 //      async: false,
 //      success: function(data) {
 //         result= data
 //
 //      },
 //        error: function (){alert("실패");}
 //    });
 //    return result;
 //
 // }



 function write() { 
 
   let result= [];
   $.ajax({
     url : "/dummy/writeDummy.json",
    
     dataType: "json",
     type: "get",
     async: false,
     success: function(data) {
        result= data
      
     },
       error: function (){alert("실패");}
   });
   return result;

}
function reply() { 
 
   let result= [];
   $.ajax({
     url : "https://dummyjson.com/comments",
    
     dataType: "json",
     type: "get",
     async: false,
     success: function(data) {
        result= data.comments
      
     },
       error: function (){alert("실패");}
   });
   return result;

}