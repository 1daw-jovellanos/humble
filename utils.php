<?php

function fileSanitize($file) {
    $file = mb_ereg_replace("([^\w\s\d\-_~,;\[\]\(\).])", '', $file);
    // Remove any runs of periods (thanks falstro!)
    $file = mb_ereg_replace("([\.]{2,})", '', $file);
    return $file;
}


function endsWith($haystack, $needle)
{
    // search forward starting from end minus needle length characters
    if ($needle === '') {
        return true;
    }
    $diff = strlen($haystack) - strlen($needle);
    return $diff >= 0 && strpos($haystack, $needle, $diff) !== false;
}

function preformat($salida) {
    echo "<pre>";
    foreach ($salida as $line) {
        echo htmlspecialchars($line) . "\n";
    }
    echo "</pre>";
}

$cpsep = stripos(PHP_OS, 'WIN') === 0 ? ";" : ":";

