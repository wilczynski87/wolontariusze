


// console.log(slider)
// console.log(sliderOutput)
// console.log(slider.value)

const sliderValue = (e) => {
    console.log(e)
    const slider = document.getElementById("attitude");
    const sliderOutput = document.getElementById("attitudeOutput");
    sliderOutput.innerHTML = slider.value;
}