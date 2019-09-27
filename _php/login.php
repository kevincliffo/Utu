<?php
    include 'DataBaseConfig.php';
    $response = array();
    $conn = new mysqli($HostName, $UserName, $UserPassword, $DatabaseName);
    
    $value = 'EMPTY';
    if($_SERVER['REQUEST_METHOD'] == 'POST')
    {
	$Email = $_POST['Email'];
	$Password = $_POST['Password'];				 
	 
	$SQL = "SELECT * FROM users WHERE Email = '$Email' AND Password = '$Password'";
	 
	$result = mysqli_query($conn, $SQL);
	try
	{
	    if ($result = mysqli_query($conn, $SQL)) 
	    {
	        if ($row = mysqli_fetch_assoc($result )) 
	        {	    	    
	    	    $name = $row['FullName'];
				$response['UserId'] = $row['UserId'];
				$response['FullName'] = $row['FullName'];
				$response['Email'] = $row['Email'];
				$response['Password'] = $row['Password'];
				$response['IDNumber'] = $row['IDNumber'];
				$response['MobileNumber'] = $row['MobileNumber'];
				$response['Address'] = $row['Address'];
				$response['ProfileImageName'] = $row['ProfileImageName'];
				$response['ProfileImageURL'] = $row['ProfileImageURL'];
				$response['DateOfBirth'] = $row['DateOfBirth'];
				$response['UserType'] = $row['UserType'];
				$response['Activated'] = $row['Activated'];	    
	    		$response['ServiceProviderActivated'] = $row['ServiceProviderActivated'];
	    		    
	            $response['message'] = "Login successful..!!!";	 
	    	    mysqli_close($conn);
	        }
	    }	    
	}
	catch(Exception $e)
	{
	    $response['message'] = "Login failed";
        }
    }
    
    echo json_encode($response);
?>
