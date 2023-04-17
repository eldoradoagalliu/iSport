window.onload = function () {
    //Navigation Bar Dropdown
    const navbarButton = document.getElementById("navbarButton");
    const navbarDropdown = document.getElementById("navbarDropdown");
    navbarButton.addEventListener("click", function () {
        if (navbarDropdown.style.display === "block") {
            navbarDropdown.style.display = "none";
        } else {
            navbarDropdown.style.display = "block";
        }
    });
}