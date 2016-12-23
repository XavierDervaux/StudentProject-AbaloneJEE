/**
    Initialisation
**/

var MIN_MDP = 6;

function init(){
    $('[data-toggle="popover"]').popover()
}

/**
    Fonctions setting
**/
function checkSetting(){
    var ok = true;
    
    if($id('emailSetting').value.length == 0){
        ok = false;
    }else if($id('passwordSetting').value.length == 0){
         ok = false;
    } else if($id('confirmPasswordSetting').value.length == 0){
         ok = false;
    }
    
    if(ok && validateEmail($id('emailSetting')) && checkPassword($id('passwordSetting'),$id('confirmPasswordSetting'))){
         $id('submitSetting').disabled = false; 
    } else{
         $id('submitSetting').disabled = true; 
    }
}

function checkPasswordSetting(){
    checkPassword($id('passwordSetting'),$id('confirmPasswordSetting'), true);
}

/**
   Fonctions Connexion
**/

function checkConnecion(){
    ok = true;
     if($id('emailConnection').value.length == 0){
       ok = false;
    } else if($id('passwordConnection').value.length == 0){
        ok = false;
    }
    
    if(ok && validateEmail($id('emailConnection'))){
         $id('submitConnection').disabled = false; 
    } else{
         $id('submitConnection').disabled = true; 
    }
}

/**
   Fonctions inscriptions
**/

function checkInscription(){
    ok = true;
    if($id('pseudoInscription').value.length == 0){
       ok = false;
    } else if($id('emailInscription').value.length == 0){
        ok = false;
    }else if($id('passwordInscription').value.length == 0){
         ok = false;
    } else if($id('confirmPasswordInscription').value.length == 0){
         ok = false;
    }
    
    if(ok){
        if(checkPassword($id('passwordInscription'),$id('confirmPasswordInscription')) && validateEmail($id('emailInscription'))){
            $id('submitInscription').disabled = false; 
        }
    } else{
        $id('submitInscription').disabled = true; 
    }
}

function checkPasswordInscription(){
    checkPassword($id('passwordInscription'),$id('confirmPasswordInscription'), true);
}


/**
    Les fonctions génériques
**/

function $id(elem) {
	var type=typeof(elem);
	if (type=="string") {
		return document.getElementById(elem);
	}
	return elem;
}

function checkPassword(password, confirmPassword, isColor = false){
    var ok = false;
    if(password.value.length >= MIN_MDP && (password.value == confirmPassword.value)){
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