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

    //Change Password Dropdown
    const dropdownButton = document.getElementById("dropdownButton");
    const dropdownMenu = document.getElementById("dropdown-menu");
    dropdownButton.addEventListener("click", function () {
        if (dropdownMenu.style.display === "block") {
            dropdownMenu.style.display = "none";
        } else {
            dropdownMenu.style.display = "block";
        }
    });
}