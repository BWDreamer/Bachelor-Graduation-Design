let slide= document.querySelector('#slide');
let container=document.querySelector('.container');
let s_buttton=document.querySelectorAll('.s_buttton')

let zhenjia = true;
let move = () => {
    let listes = document.querySelectorAll('.item');
    if (zhenjia) {
        slide.appendChild(listes[0]);
    } else {
        slide.prepend(listes[listes.length - 1]);
    }
}

let timer = setInterval(move, 2000);
container.addEventListener('mouseenter', () => {
    clearInterval(timer)
})
container.addEventListener('mouseleave', () => {
    timer = setInterval(move, 2000);
})
s_buttton[1].onclick = () => {
    zhenjia = true;
    move();
}
s_buttton[0].onclick = () => {
    zhenjia = false;
    move();

}