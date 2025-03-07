// 要打字的文本，包含换行符分隔的两行
const text = "第一章——火烧黑云，眼见喜，是为贪。\n黑熊精和金池在百年前早便认识并结为好友，黑熊知晓了金池的佛道并心向往善，故而教授了金池一些长寿的法门，且给了金池一些钱财，助金池去往城市里的僧院之中修行，但金池由于没有一件好看的袈裟来证明自己的地位导致在早年一直心怀自卑，逐渐助长了他的贪欲之心，为此他换上更好更漂亮的袈裟，在僧人中脱颖而出，长生的金池最终成为了观音禅院的长老。在他的管理下，寺院收集了不少袈裟和好物，直到唐僧到来。他见了唐僧的袈裟，心生恶念，但黑熊精不知是也想要这件袈裟还是不想让老友如此卑劣，夺走了它，最终金池长老死于火海，化为冤魂，黑熊精则和原作一样被菩萨收服。。";
const speed = 100; // 打字速度，每100毫秒打一个字符
let index = 0; // 当前打字的字符索引
let line = 1; // 当前行号，1表示第一行，2表示第二行

// 打字函数
function type() {
    const typewriter = document.getElementById('typewriter'); // 获取打字机容器
    if (index < text.length) { // 如果还有字符未打完
        const currentChar = text.charAt(index); // 获取当前字符
        const tempSpan = document.createElement('span'); // 创建一个新的span元素

        // 根据行号应用样式
        if (line === 1) {
            tempSpan.classList.add('bold'); // 第一行加粗
        } else if (line === 2) {
            tempSpan.classList.add('color-change'); // 第二行改变颜色
        }

        // 处理换行符
        if (currentChar === '\n') {
            tempSpan.innerHTML = '<br>'; // 插入换行
            line++; // 进入下一行
        } else {
            tempSpan.textContent = currentChar; // 显示当前字符
        }

        typewriter.appendChild(tempSpan); // 将span元素添加到打字机容器
        index++; // 移动到下一个字符
        setTimeout(type, speed); // 设置定时器，继续打字
    }
}


// 当页面加载完成后开始打字
window.onload = type;

document.addEventListener('DOMContentLoaded', function () {
    const substance = document.getElementById('myBox');
    const secondPage = document.querySelector('.second-page'); // 假设第二个页面有类名.second-page

    function isElementInViewport(el) {
        const rect = el.getBoundingClientRect();
        return (
            rect.top >= 0 &&
            rect.left >= 0 &&
            rect.bottom <= (window.innerHeight || document.documentElement.clientHeight) &&
            rect.right <= (window.innerWidth || document.documentElement.clientWidth)
        );
    }

    function handleScroll() {
        const secondPageRect = secondPage.getBoundingClientRect();
        // 检查第二个页面的顶部是否进入视口，且底部还没有完全离开视口
        if (secondPageRect.top <= window.innerHeight && secondPageRect.bottom >= 0) {
            substance.classList.add('visible');
        } else {
            substance.classList.remove('visible');
        }
    }

    window.addEventListener('scroll', handleScroll);
    handleScroll(); // 初始检查
});
