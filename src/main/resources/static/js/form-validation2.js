const emailForm = document.getElementById("email");
const firstName = document.getElementById("firstName");
const lastName = document.getElementById("lastName");
const amount = document.getElementById("amount");
const submitButton = document.getElementById("submit");

const namePattern = /[A-Za-z]{3,}/
const emailPattern = /[A-Za-z][A-Za-z0-9_.]{2,}@\w{2,}.\w{2,}/;
const fullNamePattern = /[A-Za-z]{3,}\s[A-Za-z]{3,}/;
const numberPattern = /\d+/;
const expDatePattern = /\d{2}\/\d{2}/;

firstName.addEventListener("blur", (event) => {
    validateInput(!namePattern.test(firstName.value), firstName);
});

lastName.addEventListener("blur", (event) => {
    validateInput(!namePattern.test(lastName.value), lastName);
});

amount.addEventListener("blur", (event) => {
    validateInput(!numberPattern.test(amount.value), amount);
});

emailForm.addEventListener("blur", (event) => {
    validateInput(!emailPattern.test(emailForm.value), emailForm);
});

submit.onclick = validateAllInputs;

function validateAllInputs(event) {
     inputs = [emailForm, firstName, lastName, amount];
     for(let i = 0; i < inputs.length; i++){
        if(inputs[i].classList.contains("is-invalid")){
            event.preventDefault();
            return;
        }
     }
     document.querySelector("form").action = `/payments/pay/${amount.value}`;
}

function validateInput(condition, field) {
    if(condition) {
        if(field.classList.contains("is-valid")){
            field.classList.remove("is-valid");
        }
        field.classList.add("is-invalid");
    } else {
        if(field.classList.contains("is-invalid")){
            field.classList.remove("is-invalid");
        }
        field.classList.add("is-valid");
    }
}

function isValidCardNumber(number) {
    let sum = 0;
    for(let i = 0; i < number.length; i += 2) {
        let digit = +number[i] + +number[i];
        if(digit >= 10){
            digit = (digit % 10) + (digit / 10);
        }
        number[i] = digit + '';
        sum += +number[i];
    }
    console.log(`cardSum ${sum}`);
    if(sum % 10 === 0) {
        return true;
    }else {
        return false;
    }
}
