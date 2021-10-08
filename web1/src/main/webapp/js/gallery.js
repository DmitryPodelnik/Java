<<<<<<< HEAD
document.addEventListener("DOMContentLoaded",()=>{
    for( let btn of document.querySelectorAll(".picture button") ) {
        btn.addEventListener("click",deleteClick);
    }
    for( let btn of document.querySelectorAll(".picture .tool-download") ) {
        btn.addEventListener("click",downloadClick);
    }
=======
document.addEventListener("DOMContentLoaded", ()=>{
    for(let btn of document.querySelectorAll(".picture button")){
    btn.addEventListener("click", deleteClick);
}
>>>>>>> 01a956e5dbad6870f7ca975992d844ff3d772484
});

function deleteClick(e) {
    const tt = e.target.parentNode.querySelector("tt");
<<<<<<< HEAD
    if( ! tt) throw "tt not found in parent node";
    const pid = tt.innerHTML;
    if(confirm("Таки удалять?")){
        fetch("?id="+pid,{method:"delete"})
            .then(r => r.json())
            .then(j => {
                console.log(j);
            });
    }
}

function downloadClick(e) {
    const tt = e.target.parentNode.querySelector("tt");
    if( ! tt) throw "tt not found in parent node";
    const pid = tt.innerHTML;
    console.log(pid);
    window.location = "download/" + pid;
=======
    if (!tt) {
        throw "tt not found in parent node";
    }
    const pid = tt.innerHTML;
    if (confirm("Таки удалять?")) {
        fetch("?id=" + pid, {method: "delete"})
            .then(r => r.json())
            .then(j => {
                console.log(j);
            })
    }
    console.log(pid);
>>>>>>> 01a956e5dbad6870f7ca975992d844ff3d772484
}