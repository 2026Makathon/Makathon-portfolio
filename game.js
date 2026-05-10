//캔버스 세팅
let canvas;
let ctx;
canvas = document.createElement("canvas")
ctx = canvas.getContext("2d")
// ctx.drawImage(arrowImage,arrowList[i].x,arrowImage[i].y,10,10);
canvas.width=600;
canvas.height=800;
document.body.appendChild(canvas);

let startButton = document.createElement("button");
let gameStarted = false; // 게임 시작 여부
let backgroundImage,userImage,arrowImage,mushroomImage,gameOverImage;
let arrowSound;
let bgmSound;
let restartButton = document.createElement("button");
let gameOver = false // true이면 게임이 끝남, false면 게임 계속 진행
let score = 0; // 현재 점수
let mushroomInterval; // 주황버섯 생성 간격을 저장하는 변수
let gameInterval; // 게임 루프를 저장하는 변수


let userX = canvas.width/2-80 // 
let userY = canvas.height-175

let arrowList = [] // 화살들을 저장하는 리스트
function arrow(){
    this.x=0;
    this.y=0;
    this.init=function(){
        this.x = userX+73;
        this.y = userY+5;
        this.alive=true // true면 살아있는 화살 false면 죽은 화살
        arrowList.push(this);
    };
    this.update = function(){
        this.y-=12;
    };

    this.checkHit = function(){
        for(let i=0; i < mushroomList.length; i++){   
        if(this.x >= mushroomList[i].x && 
            this.x <= mushroomList[i].x + 48 &&
            this.y >= mushroomList[i].y &&
            this.y <= mushroomList[i].y + 48){
            //화살이 주황버섯과 만나 없어짐, 점수 획득
            score++;
            this.alive = false // 죽은 화살
            mushroomList.splice(i,1);
        }
        } 
    }


}

function generateRandomValue(min,max){
    let randomNum = Math.floor(Math.random()*(max-min+1))+min;
    return randomNum;
}

let mushroomList=[]
function mushroom(){
    this.x=0;
    this.y=0;
    this.init = function(){
        this.y=0;
        this.x=generateRandomValue(0,canvas.width-48);
        mushroomList.push(this);
    };
    this.update=function(){
        this.y += 2; // 주황버섯의 속도 조절
        if(this.y >= canvas.height-48){
            gameOver = true;
        }
    };
}


function loadImage(){

    // 1. 이미지 로드
    backgroundImage = new Image();
    backgroundImage.src= "./images/background.png";

    userImage = new Image();
    userImage.src= "./images/User.png";

    arrowImage = new Image();
    arrowImage.src = "./images/arrow.png";

    mushroomImage = new Image();
    mushroomImage.src = "./images/Mushroom.gif";

    gameOverImage = new Image();
    gameOverImage.src = "./images/gameover.png";

    // 2. 사운드 로드
    arrowSound = new Audio();
    arrowSound.src = "./sounds/arrow.mp3";
    arrowSound.volume = 0.3; // 화살 소리 볼륨 조절

    bgmSound = new Audio();
    bgmSound.src = "./sounds/bgm.mp3";
    bgmSound.loop = true; // 배경음악 반복 재생
    bgmSound.volume = 0.4; // 배경음악 볼륨 조절

    // 3. 시작 버튼 설정
    startButton.innerText = "시작하기";
    startButton.style.position = "absolute";
    startButton.style.left = "265px";
    startButton.style.top = "500px";
    startButton.style.fontSize = "24px";
    startButton.style.color = "white";
    startButton.style.backgroundColor = "#84cb49";
    document.body.appendChild(startButton);

    startButton.addEventListener("click", function(){
    startButton.style.display = "none"; // 버튼 숨기기
    gameStarted = true;
    bgmSound.play();
    createmushroom(); // 주황버섯 생성 시작
})

    // 4. 재시작 버튼 설정
    restartButton.innerText = "재시작";
    restartButton.style.position = "absolute";
    restartButton.style.left = "270px";
    restartButton.style.top = "500px";
    restartButton.style.fontSize = "24px";
    restartButton.style.color = "white";
    restartButton.style.backgroundColor = "#84cb49";
    restartButton.style.display = "none";
    document.body.appendChild(restartButton);
    restartButton.addEventListener("click", function(){
    clearInterval(gameInterval);
    restartButton.style.display = "none";
    gameOver = false;
    gameStarted = true;
    score = 0;
    arrowList = [];
    mushroomList = [];
    userX = canvas.width/2 - 80;
    bgmSound.currentTime = 0;
    bgmSound.play();
    createmushroom();
    main();
    });

}


let keysDown={}
function setupKeyboardListener(){
    document.addEventListener("keydown",function(event){   
        
        
        keysDown[event.keyCode] = true;
    });
    document.addEventListener("keyup",function(event){
        delete keysDown[event.keyCode]
        
        if(event.keyCode == 32 && gameStarted){
            createarrow() // 화살 생성

        }
    })
}

function createarrow(){
    arrowSound.currentTime = 0; // 화살 소리 재생 시점 초기화
    arrowSound.play(); // 화살 소리 재생
    let b = new arrow();
    b.init();
}

function createmushroom(){
    clearInterval(mushroomInterval); // 기존에 존재하는 interval이 있다면 제거
    mushroomInterval = setInterval(function(){
        let e = new mushroom()
        e.init()
    },700)
}

function update(){
    if( 39 in keysDown){
        userX += 6; // right 유저 속도
    }
    if( 37 in keysDown){
        userX -= 6; // left 유저 속도
    }

    if(userX <= -50){
        userX= -50;
    }
    if(userX >= canvas.width - 110){
        userX=canvas.width - 110;
    }
    // 화살의 y좌표 업데이트하는 함수 호출 및 주황버섯과 화살이 닿는지 확인
    for(let i=0; i<arrowList.length; i++){
        if(arrowList[i].alive){
        arrowList[i].update();
        arrowList[i].checkHit();
    }
    }

    arrowList = arrowList.filter(arrow => arrow.alive); // 살아있는 화살만 남김

    for(let i=0; i<mushroomList.length; i++){
        mushroomList[i].update();
    }
}



function render(){
    ctx.drawImage(backgroundImage, 0, 0, canvas.width, canvas.height);
    ctx.drawImage(userImage, userX,userY);
ctx.fillStyle = "white";
ctx.font = "30px Arial";
ctx.fillText(`Score:${score}`, 10, 30);

    for(let i=0; i<arrowList.length; i++){
        if(arrowList[i].alive){
            ctx.drawImage(arrowImage, arrowList[i].x, arrowList[i].y);
        }
    }
    for(let i=0; i<mushroomList.length; i++){
        ctx.drawImage(mushroomImage,mushroomList[i].x,mushroomList[i].y)
    }
}

function main(){
    bgmSound.play(); // 배경음악 재생
    gameInterval = setInterval(function(){
    if(!gameOver){
        if(gameStarted){
        update();
        }
        render(); 
        }else{
        bgmSound.pause(); // 게임 오버 시 배경음악 일시정지
        ctx.drawImage(gameOverImage, 55, 100, 500, 500);
        restartButton.style.display = "block"; // 재시작 버튼 보이기
        clearInterval(gameInterval); // 게임 루프 중지
        }
    },1000/60) // 60fps
}

loadImage();
setupKeyboardListener();
main();