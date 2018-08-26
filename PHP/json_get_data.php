<?php

$host="localhost";
$db_name="xyz";
$mysql_user="xyz";
$mysql_pass="xyz";
$name=$_POST["name"];

//$server_name="localhost";
$sql="SELECT * FROM medicine where name='{$name}' ";

$con = mysqli_connect($host,$mysql_user,$mysql_pass,$db_name);

$result = mysqli_query($con,$sql);

$response = array();

while($row = mysqli_fetch_array($result)){
array_push($response,array("name"=>$row[0],"cname"=>$row[1],"price"=>$row[2],"dosageform"=>$row[3],"symptums"=>$row[4],"conscituent"=>$row[5],"conscituentperunit"=>$row[6],"sideeffects"=>$row[7],"warning"=>$row[8],"substitute"=>$row[9],"isgeneric"=>$row[10]));

}

echo json_encode(array("server_response"=>$response));

mysqli_close($con);
?>
