@file:Suppress("SpellCheckingInspection")

package com.example.test1

import android.app.AlertDialog
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
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
                    CenterAll(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Test1Theme {
        Greeting("Android")
    }
}

@Preview(showBackground = true)
@Composable
fun notaView(){
    var nota by remember { mutableStateOf("") }
    var notaLiteral by remember { mutableStateOf("")}
    var showEmptyAlert by remember { mutableStateOf(false) }
    var showOutLimitAlert by remember { mutableStateOf(false) }


    Column(modifier = Modifier.padding(16.dp)){
        TextField(
            value = nota,
            onValueChange = {nota = it
                            showEmptyAlert = nota.isEmpty()
                            showOutLimitAlert = nota.toIntOrNull() !in 0..100},
            label = {Text("Nota: ")},
            placeholder = {Text("Ejemplo: 85")},
            modifier = Modifier)
        Button(onClick = {
            notaLiteral = notaToLiteral(nota.toByte())
            nota = ""
        })
        {
            Text(text = "Calcular la nota",
                modifier = Modifier)
        }
        Text(text = "Nota: $notaLiteral",
            modifier = Modifier)
        if(showEmptyAlert){
            emptyAlert(
                showDialog = showEmptyAlert,
                onDismiss = { showEmptyAlert = false },
                onConfirm = { showEmptyAlert = false }
            )
            nota = ""
        }
        if(showOutLimitAlert){
            outLimitAlert(
                showDialog = showOutLimitAlert,
                onDismiss = { showOutLimitAlert = false },
                onConfirm = { showOutLimitAlert = false }
            )
            nota = ""
        }
    }
}

fun notaToLiteral(calificacion: Byte): String {
    var literal = "";
    if(calificacion in 90..100){
        literal = "A"
        return literal
    }else if(calificacion in 80..89){
        literal = "B"
        return literal
    }else if(calificacion>70){
        literal = "C"
        return literal
    }
    else{
        literal = "F"
        return literal
    }
}

@Composable
fun emptyAlert(
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
fun outLimitAlert(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
){
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            title = { Text("Nota fuera de rango") },
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
fun CenterAll(modifier: Modifier = Modifier){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        notaView()
    }
}