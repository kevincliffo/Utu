<?php
    include 'DataBaseConfig.php';
    $response = array();
    $conn = new mysqli($HostName, $UserName, $UserPassword, $DatabaseName);
    
    while(true)
    {
		if($_SERVER['REQUEST_METHOD'] == 'POST')
		{
			$Email = $_POST['Email'];
			$MobileNumber = $_POST['MobileNumber'];
			$IDNumber = $_POST['IDNumber'];
			
			$sql = "SELECT * FROM users where Email = '$Email'";
			$response['errorfound'] = "0";
			$response['message'] = "";
			if(mysqli_query($conn, $sql))
			{
				$response['message'] = "Email already used for Registration..!!!";
				$response['errorfound'] = "1";
				break;
			}
			
			$sql = "SELECT * FROM users where MobileNumber = '$MobileNumber'";
			$response['errorfound'] = "0";
			if(mysqli_query($conn, $sql))
			{
				$response['message'] = "Mobile Number already used for Registration..!!!";
				$response['errorfound'] = "1";
				break;
			}
				
			$sql = "SELECT * FROM users where IDNumber = '$IDNumber'";
			$response['errorfound'] = "0";
			if(mysqli_query($conn, $sql))
			{
				$response['message'] = "ID Number already used for Registration..!!!";
				$response['errorfound'] = "1";
				break;
			}
									
			$ImageData = $_POST['Image'];
			$ImageName = $_POST['ProfileImageName'];
			$FullName = $_POST['FullName'];
			$Password = $_POST['Password'];
			$Address = $_POST['Address'];
			$ServiceProviding = $_POST['ServiceProviding'];		
			$Location = $_POST['Location'];			
			$ProfileImageName = $_POST['ProfileImageName'];
			$ProfileImageURL = $_POST['ProfileImageURL'];
			$DateOfBirth = $_POST['DateOfBirth'];
			$UserType = $_POST['UserType'];		
			$Token = $_POST['Token'];	
				
			$ImagePath = "utu_uploads/$ImageName";
			
			$ProfileImageURL = "http://mantikiplan.solutions/utu/$ImagePath";	 
		 
			$InsertSQL = "INSERT INTO users(FullName, Email, Password, IDNumber, MobileNumber, Location, Address, ServiceProviding, ProfileImageName, ProfileImageURL, DateOfBirth, UserType, Token) "
					   . " VALUES ('$FullName', '$Email', '$Password', '$IDNumber', '$MobileNumber', '$Location', '$Address', '$ServiceProviding', '$ProfileImageName', "
					   . "         '$ProfileImageURL', '$DateOfBirth', '$UserType', '$Token')";
		 
			$message = 'Not added';
			if(mysqli_query($conn, $InsertSQL))
			{
				$response['message'] = 'Registration was Successfull';			
						
				file_put_contents($ImagePath,base64_decode($ImageData));
			}
		 
			mysqli_close($conn);
		}
		else
		{
			$response['message'] = "Error occured during Registration";
			$response['errorfound'] = "1";
		}
		break;
	}
    echo json_encode($response);
?>
