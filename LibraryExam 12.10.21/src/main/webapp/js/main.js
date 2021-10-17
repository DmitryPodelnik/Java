document.addEventListener("DOMContentLoaded",()=>{
    const container =
        document.getElementById("books-container")
    if(!container) throw "books-container not found";
    // fetch data (books)
    fetch("books")
        .then(r => r.json())
        .then(j => fillContainer(container, j))

    for (let btn of document.querySelectorAll(".book-delete")) {
        btn.addEventListener("click", deleteClick);
    }
    for (let btn of document.querySelectorAll(".book-edit")) {
        btn.addEventListener("click", editClick);
    }
});

function fillContainer(container, j) {
    // fetch template (html)
    fetch("templates/bookitem.html")
        .then(r => r.text())
        .then( tpl => {
            var html = "" ;
            for(let book of j) {
                html += tpl
                    .replace("{{id}}", book["id"])
                    .replace("{{author}}", book["author"])
                    .replace("{{title}}",  book["title"])
                    .replace("{{cover}}",  book["cover"]);
            }
            container.innerHTML = html;
        });

}

function deleteClick(e) {
    const bid = findBookId(e);
    if (confirm("Таки удалять?")) {
        fetch("books?id=" + bid, {method: "delete"})
            .then(r => r.json())
            .then(j => {
                console.log(j);
            });
    }
}

function editClick(e) {
    const bid = findBookId(e);
    const container = e.target.parentNode;
    const title = container.querySelector("p");
    const author = container.querySelector("b");
    if (typeof title.savedText == 'undefined' &&
        typeof author.savedText == 'undefined') {
        // первое нажатие - edit
        // разрешить редактирование описания
        title.setAttribute("contenteditable", "true");
        author.setAttribute("contenteditable", "true");
        title.focus();
        // сохранить исходный текст (перед редактированием)
        title.savedText = title.innerText;
        author.savedText = author.innerText;
        // добавить кнопку "Х"
        const cancelBtn = document.createElement("div");
        cancelBtn.innerText = "cancel";
        cancelBtn.className = "tool-button";
        cancelBtn.onclick = () => {
            // восстанавливаем сохраненный текст (отменяем изменения)
            title.innerText = title.savedText;
            author.innerText = author.savedText;
            delete title.savedText;
            delete author.savedText;
            container.removeChild(cancelBtn);
            title.removeAttribute("contenteditable");
            author.removeAttribute("contenteditable");
        };
        container.appendChild(cancelBtn);
        container.cancelBtnRef = cancelBtn;
    } else {
        // второе нажатие - save
        title.removeAttribute("contenteditable");
        author.removeAttribute("contenteditable");
        container.removeChild(container.cancelBtnRef);
        delete container.cancelBtnRef;

        if (title.savedText !== title.innerText &&
            author.savedText !== author.innerText) {
            // console.log({id: bid, title: title.innerText });
            fetch("books", {
                method: "PUT",
                body: JSON.stringify({
                    id: bid,
                    title: title.innerText,
                    author: author.innerText,
                }),
                // body: `{"id": "${bid}", "title": "${title.innerText}" }`,
                headers: {
                    "Content-Type": "application/json; charset=utf-8"
                }
            }).then(r => r.json()).then(j => {
                if (j.status > 0) {
                    alert("Update OK");
                    delete title.savedText;
                    delete author.savedText;
                } else {
                    alert("Update error");
                    console.log(j);
                    title.innerText = title.savedText;
                    author.innerText = author.savedText;
                    delete title.savedText;
                    delete author.savedText;
                }
            });
        } else {
            delete title.savedText;
            delete author.savedText;
        }

    }
}

function findBookId(e) {
    const id = document.querySelector(".bookid");
    if( ! id) throw "id not found in parent node";
    return id.innerHTML;
}