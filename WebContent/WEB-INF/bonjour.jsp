<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Test</title>
    </head>
    <body>
        <p>Bonjour à vous !</p>
        <p>Vous êtes ${ andy.prenom } ${ andy.nom }, et vous ${ andy.estNulEnAnglais ? 'êtes' : 'n\'êtes pas' } nul en anglais.</p>
        <p>L'autre personne est ${ xavier.prenom } ${ xavier.nom }, et il ${ xavier.estNulEnAnglais ? 'est' : 'n\'est pas' } nul en anglais.</p>
    </body>
</html>