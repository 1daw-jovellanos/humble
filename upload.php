<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Entrega</title>
    <?php include "bootstrap.inc.html"?>
</head>
<body class="container">
<div><img src="assets/robot3.png" style="width:200px;"></div>
<?php
$debug = false;
include 'utils.php';
if ($debug) {
    echo "Separator is $cpsep";
}
$nombreusuario = isset($_REQUEST["usuarioid"]) ? $_REQUEST["usuarioid"] : "victor";
$ejercicio = isset($_REQUEST["ejercicioid"]) ? $_REQUEST["ejercicioid"] : "sample";
$usuario = preg_replace("/[^A-Za-z0-9]/","", $nombreusuario);


$path ="uploads/$ejercicio";
$config = parse_ini_file("$path/config.ini");
$package = $config["package"];
$testfile = $config["testfile"];
$expectedfilename = $config["filename"];
$pathuser = "$path/users/$usuario";
$pathpackage = "$pathuser/$package";

if (!file_exists($pathpackage)) {
    //crear directorio
    mkdir($pathpackage, 0777, true);
} 

$filename = $_FILES["fileToUpload"]["name"];
$ok = true;
if ($filename != $expectedfilename) {
    echo "Esperaba que el fichero se llamase '$expectedfilename'";
    $ok = false;
}


// copiar y compilar
if ($ok) {
    echo "<h2>Compilando..</h2>";
    move_uploaded_file($_FILES["fileToUpload"]["tmp_name"], $pathpackage . "/" . $_FILES["fileToUpload"]["name"]);
    $salida = [];
    $code = 0;
    exec("javac -encoding UTF-8 $pathpackage/$filename 2>&1", $salida, $code);
    if ($code == 0) {
        echo "<p>Compilado con éxito</p>";
    } else {
        echo "<p>Hay problemas al compilar. Compruébalo, por favor</p>";
        if ($debug) {
            preformat($salida);
        }
        $ok = false;
    }
}

// checkstyle
if ($ok) {
    echo "<h2>Comprobando estilo.</h2>";
    exec("java -Duser.language=es -jar ./java/checkstyle-8.14-all.jar -c ./java/sun_checks.xml $pathpackage/$filename 2>&1" ,
    $salida, $code);
    if ($code == 0) {
        echo "<p>Estilo ok</p>";
    } else {
        echo "<p>He encontrado algunas dificultades en el estilo:</p>";
        $s2 = [];        
        foreach ($salida as $line) {
            if (strpos($line,"[")===0) {
                array_push($s2, substr($line, strpos($line, $filename)));
            }
        }
        preformat($s2);
        $ok = false; // --------------------------------------------------
    }
}
// pruebas
if ($ok) {
    echo "<h2>Pruebas unitarias.</h2>";
    $salida="";
    copy("$path/$testfile.java", "$pathpackage/$testfile.java");
    // exec("javac -encoding UTF-8 -cp java/junit-4.12.jar:java/hamcrest-core-1.3.jar:$pathuser $pathpackage/$testfile.java 2>&1" ,
    exec("javac -encoding UTF-8 -cp java/junit-4.12.jar".$cpsep."java/hamcrest-core-1.3.jar $pathpackage/*.java 2>&1" ,
$salida, $code);
    if ($code == 0) {
        echo "<p>Pruebas compiladas</p>";
    } else {
        echo "<p>Las compilación de las pruebas ha fallado.<br>";
        echo "Comprueba que los miembros públicos y protegidos de su código siguen las especificaciones, <br>";
        echo "especialmente nombres de métodos, paquete y otrosmiembros</p>";
        if ($debug) {
            preformat($salida);
        }
        $ok = false;
    }
}

if (!file_exists("CorrePruebas.class")) {
    exec("javac -encoding UTF-8 -cp java/junit-4.12.jar".$cpsep."java/hamcrest-core-1.3.jar CorrePruebas.java 2>&1" ,
    $salida, $code);
    if ($code != 0) {
        echo "<p>No se pudo compilar el módulo núcleo de las pruebas. Por favor, díselo al profe.</p>";
        if ($debug) {
            preformat($salida);
        }
        $ok = false;
    }
}

if ($ok) {
    echo "</p>Ejecutando pruebas</p>";
    $salida="";
    exec("java -cp java/junit-4.12.jar".$cpsep."java/hamcrest-core-1.3.jar".$cpsep.".".$cpsep."$pathuser CorrePruebas $package.$testfile 2>&1" ,
    $salida, $code);
    if ($code == 0) {
        echo "<p>Pruebas superadas.</p>";
    } else {
        echo "<p>Pruebas no superadas.</p>";
        preformat($salida);
        $ok = false;
    }
}

echo '<br><br>';

if ($ok) {
    if (!file_exists("$path/entregados/$usuario")) {
        mkdir("$path/entregados/$usuario", 0777, true);
    }
    $date=date("YmdHis");
    copy("$pathpackage/$filename", "$path/entregados/$usuario/$date-$filename");
?>
<div class="alert alert-success" role="alert">
  <h4 class="alert-heading">¡Bien!</h4>
  El ejercicio ha quedado grabado para revisión.
</div>
<?php
} else {
?>
<div class="alert alert-danger" role="alert">
  <h4 class="alert-heading">Estoy desolado.</h4>
  La entrega tiene dificultades que deben ser subsanadas.
</div>
<?php    
}
?>
<a href="index.php?usuarioid=<?=$nombreusuario?>&ejercicioid=<?=$ejercicio?>">Volver</a>
</body>
</html>