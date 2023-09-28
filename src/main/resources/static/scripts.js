
const sliderValue = () => {
    const slider = document.getElementById("attitude");
    const sliderOutput = document.getElementById("attitudeOutput");

    sliderOutput.innerHTML = attitudeDisplayer(slider.value);
}
const attitudeDisplayer = (val) => {
    return attitudes[val];
}
