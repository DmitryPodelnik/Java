document.addEventListener("submit",sendClick);

function sendClick(e) {
    e.preventDefault();
    // console.log(e.target); return;  // e.target - form
    const author = e.target.querySelector("input[name=author]");
    const title  = e.target.querySelector("input[name=title]");
    const cover  = e.target.querySelector("input[name=cover]");
    // TODO data validation

    if (author.value === null || author.value.length < 2) {
        alert("Author length must be longer than 1!");
        return;
    }
    if (title.value === null || title.value.length < 2) {
        alert("Title length must be longer than 1!");
        return;
    }
    if (cover.value === null) {
        alert("You must upload a file!");
        return;
    }

    const formData = new FormData();
    formData.append("author", author.value);
    formData.append("title",  title.value);
    formData.append("cover",  cover.files[0]);
    fetch("books", { method: "POST", body: formData })
        .then(r => r.text()).then(console.log)
}

function coverChange(e) {
    if(e.target.files) {
        const coverImg = document.getElementById("coverImg");
        coverImg.src=URL.createObjectURL(e.target.files[0]);
    }
}
