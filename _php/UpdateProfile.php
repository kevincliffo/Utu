<?php
    include 'DataBaseConfig.php';
    $response = array();
    $conn = new mysqli($HostName, $UserName, $UserPassword, $DatabaseName);
    
    while(true)
    {
		if($_SERVER['REQUEST_METHOD'] == 'POST')
		{
			$Email = $_POST['Email'];
			$ServiceProviding = $_POST['ServiceProviding'];		
			$Location = $_POST['Location'];	
			$UserType = $_POST['UserType'];
			
			$response['errorfound'] = "0";
			$response['message'] = "";
						
			if($UserType == "0")
			{
				$sql = "UPDATE users SET Location = '$Location' WHERE Email = '$Email'";
			}
			else if($UserType == "1")
			{
				$sql = "UPDATE users SET Location = '$Location', ServiceProviding = '$ServiceProviding' WHERE Email = '$Email'";
			}
			
			if(mysqli_query($conn, $sql))
			{
				$response['message'] = "User Profile Updated Successfully";
				$response['errorfound'] = "0";
				
				$SQL = "SELECT * FROM users WHERE Email = '$Email'";
				 
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
							$response['Location'] = $row['Location'];	  
							$response['ServiceProviding'] = $row['ServiceProviding'];	  		    					
						}
					}	    
				}
				catch(Exception $e)
				{
					$response['message'] = "Error retrieving User Details" + $e->getMessage();
				}				
				
				break;
			}
		 
			mysqli_close($conn);
		}
		else
		{
			$response['message'] = "Error occured during Profile Update";
			$response['errorfound'] = "1";
		}
		break;
	}
    echo json_encode($response);;
?>
