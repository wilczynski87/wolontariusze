
const sliderValue = () => {
    const slider = document.getElementById("attitude");
    const sliderOutput = document.getElementById("attitudeOutput");

    sliderOutput.innerHTML = attitudeDisplayer(slider.value);
}
const attitudeDisplayer = (val) => {
    return attitudes[val];
}

const phoneCorrector = (phone) => {
    phone.value = phone.value.replaceAll(/\s/g, "").match(/.{1,3}/g).join(" ");
}

const closeModal = () => {
    const myModal = document.getElementById("loaderModalCloseBtn");
    console.log(myModal)
    myModal.click();
}

const passowrdValidator = (password, passwordCheck) => {
    return password.value == passwordCheck.value;
}

const validatorAddUser = (e, form) => {
    console.log(form)
    e.preventDefault();
    e.stopPropagation();
    
    form.classList.add('was-validated')

    const password = form.querySelector('#password');
    const passwordCheck = form.querySelector('#passwordCheck');
    passowrdValidator(password, passwordCheck) 
        ? passwordCheck.setCustomValidity('') 
        : passwordCheck.setCustomValidity('Hasło jest niepoprawnie powtórzone');

    if(form.checkValidity()) htmx.trigger(form, "sendForm");

    return false;
}

const validatorAddContact = (e) => {
    e.preventDefault();
    e.stopPropagation();
    let form = document.getElementById(`addContactForm`);
    form.classList.add('was-validated')
    if(form.checkValidity()) htmx.trigger("#addContactForm", "sendContactForm");
    return false;
}

const closeModalById = (id) => {
    const myModal = document.getElementById(id);
    const modal = bootstrap.Modal.getInstance(myModal);
    modal.hide();
}