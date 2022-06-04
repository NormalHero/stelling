//배너



const slides = document.querySelector('.M_Banner_imgs');  //전체 슬라이드 컨테이너
const slideImg = document.getElementsByClassName('banner_img');  //모든 슬라이드들

const slideCount = slideImg.length; // 슬라이드 개수 
const prev = document.querySelector('.left'); //이전 버튼 
const next = document.querySelector('.right'); //다음 버튼


currentIdx =0;
mcurrentIdx = currentIdx-1;
pcurrentIdx =currentIdx+1;
lastslideCount= slideCount-1
function set (){
   currentIdx===slideCount?currentIdx=0:"";
   mcurrentIdx===slideCount?mcurrentIdx=0:"";
   pcurrentIdx===slideCount?pcurrentIdx=0:"";
   pcurrentIdx===-1?pcurrentIdx=lastslideCount:"";
   currentIdx===-1?currentIdx=lastslideCount:"";
   mcurrentIdx===-1?mcurrentIdx=lastslideCount:"";
  
   Mslide = slideImg[mcurrentIdx]
   mainslide = slideImg[currentIdx]
   Pslide = slideImg[pcurrentIdx]
   
}
///시작
window.onload=()=>{
//초기값 세팅
removes()
change()

//오토 시작
auto ()
Illustrator()

}


//  slideImg[i] 클래스 삭제 중복방지
function removes(){
   for(i=0; i< slideCount;i++){
      slideImg[i].classList.remove('addd','leftadd','rightadd')
   }
}

next.addEventListener('click',function(){
   mcurrentIdx++
   currentIdx++
   pcurrentIdx++
   
   removes()
   change()
  
})
prev.addEventListener('click',function(){
   mcurrentIdx--;
   currentIdx--;
   pcurrentIdx--;
   set ()
   removes()
   change()
 
})

function change(){
   set ()

   mainslide.classList.add('addd');
   Mslide.classList.add('leftadd')
   Pslide.classList.add('rightadd')
}
   
   
//자동 슬라이드
function auto (){
   set();
   mcurrentIdx++
   currentIdx++
   pcurrentIdx++
   removes();
   change();

   setTimeout(auto, 4000);
}

//hot 옆에 시간 
// RealTime()
function RealTime (){
let today = new Date()
let hour = today.getHours()
let RealMM = "";

if(hour ==0){RealMM="오후"}else if(hour <13){RealMM="오전"}else{RealMM="오후" }
hour = (hour==0)?12:hour;
if(hour <10){hour="0"+hour}
if(hour >12){hour=hour-12}
console.log(today)
const RealTime = document.createElement('a') 
writer = document.createTextNode("\n" +RealMM + hour+"시기준")
RealTime.append(writer)
document.querySelector('.RealTime').append(RealTime);

}
// 클릭했을때 색입히기
let tagspan = document.querySelectorAll('.tagspan');

function clickHandler() {  
     for (var i = 0; i < tagspan.length; i++){
        tagspan[i].classList.remove('clinkadd');    
     }
     this.classList.add('clinkadd');
}

 for (var i = 0; i < tagspan.length; i++){
    tagspan[i].addEventListener('click', clickHandler);
 }

// 

function makeHot(v,index,List){
   
   
   const divWrap =document.createElement('div')
   divWrap.className="divWrap"
   divWrap.id=`divWrap${index}`
 
   divWrap.onclick=ff
   
   const divimg =document.createElement('div')
   divimg.className="divimg"
   const divDesc =document.createElement('div')
   divDesc.className="divDesc"
   const divDescHead =document.createElement('div')
   divDescHead.className="divDescHead"
   const divDescRank =document.createElement('div')
   divDescRank.className="divDescRank" 
   const divDescTitle =document.createElement('div')
   divDescTitle.className="divDescTitle"       
   const pwirter = document.createElement('pwirter')
   pwirter.className="pwirter"    
   const divGenre =document.createElement('div')
   divGenre.className="divGenre"       
   const img = document.createElement('img')
   img.src=`https://image.tmdb.org/t/p/w500${v.poster_path}`;
   img.className  ="tagsimg"
   const RankSpan = document.createElement('span') 
   RankSpan.className ="RankSpan"  
   Ranking = document.createTextNode(`${index+1}위`)
   TitleSpan = document.createTextNode(`${v.title}`)
   writer = document.createTextNode(`${v.original_title}`)
   const GenerSpan = document.createElement('span') 
   GenerSpan.className="GenerSpan"
   GenerText =document.createTextNode(`#${index} #${v.vote_count} #${v.genre_ids[0]}`)     

   
  
   divWrap.append(divimg,divDesc)        
   divimg.append(img) 
   divDesc.append(divDescHead,pwirter,divGenre)
   divDescHead.append(divDescRank,divDescTitle)
   divDescRank.append(RankSpan)
   divDescTitle.append(TitleSpan)
   RankSpan.append(Ranking)
   pwirter.append(writer)
   divGenre.append(GenerSpan)
   GenerSpan.append(GenerText)
  
   List.appendChild(divWrap)

   
   //페이지 이동
function ff(){
   location.href=`../../../templates/novel/round.html`
}
   
}
cccc()
function cccc(){
   img.slice(0,3)?.map((v,index)=>{
      let List = document.querySelector('.tags_left')
     console.log(v)
      makeHot(v,index,List);}
      )
   
  
  img.slice(3,6)?.map((v,index)=>{
      let List = document.querySelector('.tags_middle')
      makeHot(v,index,List);
     
  })  
  img.slice(6,9)?.map((v,index)=>{
      let List = document.querySelector('.tags_right')
      makeHot(v,index,List);})  
  }
  







///메인컨테이너

// function main_sectionList(v,sectionList){
//
//
//     const x_item =document.createElement('div')
//     x_item.className="x_item"
//     x_item.onclick=ff
//     const x_item_img=document.createElement('div')
//     x_item_img.className="x_item_img"
//     const img = document.createElement('img')
//     img.className="ximg"
//     img.src=`https://image.tmdb.org/t/p/w500${v.poster_path}`;
//     const h3= document.createElement('h3')
//     const p = document.createElement('p')
//     h3text = document.createTextNode(`${v.original_title}`)
//     ptext=document.createTextNode(`${v.title}`)
//
//
//
//     x_item.appendChild(x_item_img)
//     x_item_img.appendChild(img)
//     x_item.appendChild(h3)
//     x_item.appendChild(p)
//     h3.appendChild(h3text)
//     p.appendChild(ptext)
//
//     sectionList.appendChild(x_item)
//     //페이지 지동
//     function ff(){
//         location.href=`./novel/round.html?v=`+v.id;
//     }
//
//
//
// }





