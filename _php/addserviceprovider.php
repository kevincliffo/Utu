<?php
    include 'DataBaseConfig.php';
    
    $conn = new mysqli($HostName, $UserName, $UserPassword, $DatabaseName);
    
    if($_SERVER['REQUEST_METHOD'] == 'POST')
    {	
		$UserId = $_POST['UserId'];
		$ServiceProviderName = $_POST['ServiceProviderName'];
		$ServiceName = $_POST['ServiceName'];				 
	 
		$InsertSQL = "INSERT INTO serviceproviders(UserId, ServiceProviderName, ServiceName) "
		           . " VALUES ('$UserId', '$ServiceProviderName', '$ServiceName')";
	 
	 	$message = 'Provider not Activated';
		if(mysqli_query($conn, $InsertSQL))
		{
		    $message = 'Provider acivated';			
			$sql = "UPDATE users SET ServiceProviderActivated = 1 WHERE UserId = '$UserId'";
			mysqli_query($conn, $sql);
		}
	 
		mysqli_close($conn);
    }
    else
    {
	$message = "Error occured ACtivating Service Provider";
    }
    echo $message;
?>
