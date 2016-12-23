/**
   Fonctions inscriptions
**/

function checkConnecion(){
    ok = true;
     if($('emailConnection').value.length == 0){
       ok = false;
    } else if($('passwordConnection').value.length == 0){
        ok = false;
    }
    
    if(ok && validateEmail($('emailConnection'))){
         $('submitConnection').disabled = false; 
    } else{
         $('submitConnection').disabled = true; 
    }
}

/**
   Fonctions inscriptions
**/

function checkInscription(){
    ok = true;
    if($('pseudoInscription').value.length == 0){
       ok = false;
    } else if($('emailInscription').value.length == 0){
        ok = false;
    }else if($('passwordInscription').value.length == 0){
         ok = false;
    } else if($('confirmPasswordInscription').value.length == 0){
         ok = false;
    }
    
    if(ok){
        if(checkPassword($('passwordInscription'),$('confirmPasswordInscription')) && validateEmail($('emailInscription'))){
            $('submitInscription').disabled = false; 
        }
    } else{
        $('submitInscription').disabled = true; 
    }
}

function checkPasswordInscription(){
    checkPassword($('passwordInscription'),$('confirmPasswordInscription'), true);
}


/**
    Les fonctions génériques
**/

/**
 * If input is a string, gets the element whose ID is that string
 * else, returns the input (allowing to call with either the ID or
 * the element
 * @param elem ID string or element
 * @return Element related
 */
function $(elem) {
	var type=typeof(elem);
	if (type=="string") {
		return document.getElementById(elem);
	}
	return elem;
}

function checkPassword(password, confirmPassword, isColor = false){
    var ok = false;
    if(password.value == confirmPassword.value){
        ok = true;
        if(isColor){
            inputValid(password);
            inputValid(confirmPassword);
        }
    }
    else{
        if(isColor){
            inputError(password);
            inputError(confirmPassword);
        }
    }   
    return ok;
}

function inputValid(control){
    control.style.backgroundColor = "#DEF0D7";
    control.style.border = "solid 1px #B2DBA1";
}

function inputError(control){
    control.style.backgroundColor = "#F5E6E6";
    control.style.border = "solid 1px #DCA7A7";
}

function validateEmail(mail, isColor = false)   
{ 
    var ok = false;
    if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(mail.value))  
    {  
        ok = true;
        if(isColor){
            inputValid(mail);
        }
    }else{
        inputError(mail);
    } 
    return ok;
}