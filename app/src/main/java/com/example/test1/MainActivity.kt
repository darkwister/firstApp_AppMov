package com.example.test1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.test1.ui.theme.Test1Theme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Test1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppInstance(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}


@Composable
fun NotaView(){
    var nota by remember { mutableStateOf("") }
    var notaLast by remember {mutableStateOf("")}
    var notaLiteral by remember { mutableStateOf("")}
    var showEmptyAlert by remember { mutableStateOf(false) }
    var showOutLimitAlert by remember { mutableStateOf(false) }


    Column(modifier = Modifier.padding(16.dp)){
        TextField(
            value = nota,
            onValueChange = {nota = it
                            showOutLimitAlert = nota.toIntOrNull() !in 0..100},
            label = {Text("Nota numerica: ")},
            placeholder = {Text("Ejemplo: 85")},
            modifier = Modifier,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ))
        Button(onClick = {
            if(nota.isEmpty()){
               showEmptyAlert = true
            }
            else {
                notaLiteral = notaToLiteral(nota.toByte())
                notaLast = nota
                nota = ""
            }
        })
        {
            Text(text = "Calcular la nota",
                modifier = Modifier)
        }
        Text(text = "Nota literal: $notaLiteral - $notaLast",
            modifier = Modifier.padding(20.dp).size(170.dp))

        if(showEmptyAlert){
            EmptyAlert(
                showDialog = showEmptyAlert,
                onDismiss = { showEmptyAlert = false },
                onConfirm = { showEmptyAlert = false }
            )
        }
        if(showOutLimitAlert){
            OutLimitAlert(
                showDialog = showOutLimitAlert,
                onDismiss = { showOutLimitAlert = false },
                onConfirm = { showOutLimitAlert = false }
            )
            nota = ""
        }
    }
}

fun notaToLiteral(calificacion: Byte): String {
    var literal = ""
    when (calificacion) {
        in 90..100 -> literal = "A"
        in 80..89 -> literal = "B"
        in 70..79 -> literal = "C"
        in 0..69 -> literal = "F"
    }
    return literal
}

@Composable
fun EmptyAlert(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            title = { Text("Nota vacia") },
            text = { Text("Por favor ingrese una nota") },
            confirmButton = {
                Button(onClick = { onConfirm() }) {
                    Text("Aceptar")
                }
            },
            containerColor = AlertDialogDefaults.containerColor,
            titleContentColor = Color.Blue,
            tonalElevation = AlertDialogDefaults.TonalElevation,
            shape = AlertDialogDefaults.shape,
        )
    }
}
@Composable
fun OutLimitAlert(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
){
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            title = { Text("La nota debe ser un numero dentro del rango") },
            text = { Text("Por favor ingrese una nota entre 0 y 100") },
            confirmButton = {
                Button(onClick = { onConfirm() }) {
                    Text("Aceptar")
                }
            }
        )
    }
}

@Composable
fun MiData(){
    val nombre = "Job Jefferson Pérez Cabrera"
    val matricula = "2023-0188"
    val foto = painterResource(R.drawable.myself)
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom
    ){
        Text(
            text = nombre,
            modifier = Modifier.padding(16.dp)
        )
        Text(
            text = matricula,
            modifier = Modifier.padding(16.dp)
        )
    }
    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
    ){
        Image(
            painter = foto,
            "Yo mismo",
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(130.dp).padding(16.dp)
        )
    }

}

@Preview
@Composable
fun AppInstance(modifier: Modifier = Modifier){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        NotaView()
    }
    MiData()
}