<?php
if (isset($_REQUEST["usuarioid"]) && isset($_REQUEST["ejercicioid"])) {
                $usuarioid = $_REQUEST["usuarioid"];
                $ejercicioid = $_REQUEST["ejercicioid"];
} else {
    die ("Parámetros incorrectos");
}
$entregado = file_exists("uploads/$ejercicioid/entregados/$usuarioid");
$fallido = file_exists("uploads/$ejercicioid/users/$usuarioid");
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Humblebot</title>
    <?php include 'bootstrap.inc.html'; ?>
</head>
<body class="container">
<h1>Entrega de ejercicio</h1>
<div class="row">
    <div class="col-md-4">
        <img src="assets/robot3.png" class="img-fluid">
    </div>
    <div class="col-md-8">
        <p>Hola <?=$fallido || $entregado?"de nuevo, ":""?><?=$usuarioid?></p>
<?php if (!$fallido && !$entregado): ?>
        <p>Soy <strong>Humblebot</strong>, y en esta ocasión me voy a encargar de 
        revisar el ejercicio, antes de guardarlo para que lo tenga el profesor.</p>

        <p>Voy a comprobar que el ejercicio compila correctamente, que el código fuente 
            tiene un estilo razonable de acuerdo al estilo acordado en clase, y finalmente
            realizaré algunas pruebas para ver que su comportamiento se ajusta en lo
            fundamental a lo que se pide en el enunciado del ejercicio.</p>
<?php endif;?>            
<?php if ($entregado): ?>
<div class="alert alert-info" role="alert">
  Ya tengo una entrega válida tuya archivada para el profesor.<br>
  Si subes otro código válido, el nuevo sustituirá al anterior.
</div>
<?php elseif ($fallido): ?>
<div class="alert alert-secondary" role="alert">
  La última entrega tuvo dificultades.<br>
  Si ya está arreglada, súbela y la reviso de nuevo.
</div>
<?php endif; ?>


    <hr>
        Carga el fichero utilizando este botón:
        <form action="upload.php" method="post" enctype="multipart/form-data">
            <div class="form-group">
                <input class="form-control-file" type="file" name="fileToUpload" id="fileToUpload"><br>
                <input class="btn" type="submit" value="Subir!" name="submit">
                <input type="hidden" name="usuarioid" value="<?=$usuarioid?>">
                <input type="hidden" name="ejercicioid" value="<?=$ejercicioid?>">
            </div>
        </form>
    </div>
</div>

   


    
</body>
</html>
