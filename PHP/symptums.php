<?php

$host="localhost";
$db_name="xyz";
$mysql_user="xyz";
$mysql_pass="xyz";
$name=$_POST["name"];

//$server_name="localhost";
$sql="SELECT name,price medicine where symptums='{$name}' ";

$con = mysqli_connect($host,$mysql_user,$mysql_pass,$db_name);

$result = mysqli_query($con,$sql);

$response = array();

while($row = mysqli_fetch_array($result)){
array_push($response,array("name"=>$row[0],"price"=>$row[1]));
}

echo json_encode(array("server_response"=>$response));

mysqli_close($con);
?>
