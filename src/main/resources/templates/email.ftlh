<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Spring Boot Email using FreeMarker</title>

</head>
<body>
<div style="margin-top: 10px">Greetings,</div>
<div>
    You are receiving this email because you are part of inventory mailing list for the item mentioned in the email.
</div>
<div>Threshold reached for item <b>${thresholdReachedEmailEvent.itemName}</b></div>
<br/>
<div>Threshold quantity is set at <b>${thresholdReachedEmailEvent.thresholdQuantity}</b></div>
<br/>
<div>Current Available quantity is <b>${thresholdReachedEmailEvent.remainingQuantity}</b></div>
</body>
</html>