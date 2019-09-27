<?php
    include 'DataBaseConfig.php';
    
    $conn = new mysqli($HostName, $UserName, $UserPassword, $DatabaseName);
    
    $Token = $_GET['Token']; 
    $sql = "UPDATE users SET Activated = 1 WHERE Token = '$Token'";
    
    $message = 'Not added';
    if(mysqli_query($conn, $sql))
    {
        $message = 'Activation Successfully';
	mysqli_close($conn);
    }
    else
    {
	$message = "Error occured during Ativation";
    }    
    echo $message;
?>
