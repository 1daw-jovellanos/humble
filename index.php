<?php
include 'utils.php';
if (isset($_REQUEST["usuarioid"]) && isset($_REQUEST["ejercicioid"])) {
                $usuarioid = $_REQUEST["usuarioid"];
                $ejercicioid = $_REQUEST["ejercicioid"];
} else {
    die ("Parámetros incorrectos");
}
$usuariodir = preg_replace("/[^A-Za-z0-9]/","", $usuarioid);
$entregado = file_exists("uploads/$ejercicioid/entregados/$usuariodir");
$fallido = file_exists("uploads/$ejercicioid/users/$usuariodir");
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
        <p>Soy <strong>Humblebot-α-0.5</strong>. Me ha programado el profe para revisar los
        ejercicios antes de guardarlos.</p>

        <p>Aunque sólo soy un bot, sé comprobar que el programa compila, conozco las reglas de estilo básicas, y
        puedo ejecutar tu programa y probarlo en mi memoria para ver si se ajusta a lo que se espera de él.
        </p>

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
        Carga el fichero utilizando este botón, y yo lo revisaré. Si le doy el visto bueno, lo dejaré archivado para que lo
        mire el profe. Si no le doy el visto bueno, te indicaré por dónde veo problemas lo mejor que pueda, y tendrás que intentar
        rectificarlos.<br>
        <small>(Espero que todo vaya bien. Me acaban de programar y todavía estoy en 
        <a href="https://es.wikipedia.org/wiki/Ciclo_de_vida_del_lanzamiento_de_software" target="_blank">fase alfa</a>)</small><br><br>
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
