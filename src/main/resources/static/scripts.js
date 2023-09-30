
const sliderValue = () => {
    const slider = document.getElementById("attitude");
    const sliderOutput = document.getElementById("attitudeOutput");

    sliderOutput.innerHTML = attitudeDisplayer(slider.value);
}
const attitudeDisplayer = (val) => {
    return attitudes[val];
}

const phoneValidator = () => {
    const phone = document.getElementById("phone");
    phone.value = phone.value.replaceAll(/\s/g, "").match(/.{1,3}/g).join(" ");
}