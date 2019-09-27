<?php
    include 'DataBaseConfig.php';
    $response = array();
    $conn = new mysqli($HostName, $UserName, $UserPassword, $DatabaseName);
     
    if($_SERVER['REQUEST_METHOD'] == 'POST')
    {
	$Email = $_POST['Email'];
	$Password = $_POST['Password'];				 
	 
	$SQL = "SELECT * FROM users WHERE (UserType = 1) AND (Email <> '$Email')";
	 
	$providers = array();
	
	try
	{
	    $result = mysqli_query($conn, $SQL);
	    while($row = mysql_fetch_array($result)) 
	    {
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
			
	    	$providers[] = $response;
	    }
	    
	    mysqli_close($conn);    
	}
	catch(Exception $e)
	{
	    $response['message'] = "Login failed";
        }
    }
    
    echo json_encode($providers);
?>
