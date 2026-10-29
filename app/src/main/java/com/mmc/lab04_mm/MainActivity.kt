package com.mmc.lab04_mm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mmc.lab04_mm.ui.theme.Lab04_mmTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab04_mmTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = MaterialTheme.colorScheme.background
                ) { innerPadding ->
                    PantallaApiario(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun PantallaApiario(modifier: Modifier = Modifier) {
    var responsable by rememberSaveable { mutableStateOf("") }
    var codigoColmena by rememberSaveable { mutableStateOf("") }
    var requiereSeguimiento by rememberSaveable { mutableStateOf(false) }
    var mensaje by rememberSaveable { mutableStateOf("") }
    var registroGuardado by rememberSaveable { mutableStateOf(false) }
    var responsableGuardado by rememberSaveable { mutableStateOf("") }
    var colmenaGuardada by rememberSaveable { mutableStateOf("") }
    var seguimientoGuardado by rememberSaveable { mutableStateOf(false) }
    var contadorTemporal by remember { mutableStateOf(0) }
    var contadorPersistente by rememberSaveable { mutableStateOf(0) }
    var bloqueado by rememberSaveable { mutableStateOf(false) }
    var mensajeBloqueo by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Apiario Inteligente",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Registro y control de inspecciones",
            style = MaterialTheme.typography.bodyMedium
        )

        Image(
            painter = painterResource(id = R.drawable.img_colmena),
            contentDescription = "Ilustración de una colmena",
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp),
            contentScale = ContentScale.Fit
        )

        HorizontalDivider()

        Text(
            text = "Datos de la inspección",
            style = MaterialTheme.typography.titleMedium
        )

        LabelAndField(
            etiqueta = "Nombre del responsable",
            valor = responsable,
            onValorCambio = { responsable = it }
        )

        LabelAndField(
            etiqueta = "Código de colmena",
            valor = codigoColmena,
            onValorCambio = { codigoColmena = it }
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Requiere seguimiento")

            Switch(
                checked = requiereSeguimiento,
                onCheckedChange = { requiereSeguimiento = it },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = MaterialTheme.colorScheme.onSecondary,
                    checkedTrackColor = MaterialTheme.colorScheme.secondary,
                    uncheckedThumbColor = MaterialTheme.colorScheme.outline,
                    uncheckedTrackColor = MaterialTheme.colorScheme.surface
                )
            )
        }

        Button(
            onClick = {
                if (responsable.isBlank() || codigoColmena.isBlank()) {
                    mensaje = "Complete todos los campos obligatorios"
                    registroGuardado = false
                } else {
                    responsableGuardado = responsable
                    colmenaGuardada = codigoColmena
                    seguimientoGuardado = requiereSeguimiento

                    mensaje = "Registro guardado correctamente"
                    registroGuardado = true
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Guardar registro")
        }

        if (mensaje.isNotEmpty()) {
            Text(
                text = mensaje,
                color = if (registroGuardado) {
                    MaterialTheme.colorScheme.secondary
                } else {
                    MaterialTheme.colorScheme.error
                },
                style = MaterialTheme.typography.bodyMedium
            )
        }

        if (registroGuardado) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF0F4EB))
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Resumen de inspección",
                    style = MaterialTheme.typography.titleMedium
                )

                Text(text = "Responsable: $responsableGuardado")
                Text(text = "Colmena: $colmenaGuardada")

                Text(
                    text = "Seguimiento: " +
                            if (seguimientoGuardado) "Sí" else "No"
                )
            }
        }

        HorizontalDivider(
            modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
        )

        Text(
            text = "Control de inspecciones",
            style = MaterialTheme.typography.titleMedium
        )

        OutlinedButton(
            onClick = {
                bloqueado = !bloqueado

                mensajeBloqueo = if (bloqueado) {
                    "Los contadores están bloqueados"
                } else {
                    "Los contadores están desbloqueados"
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = MaterialTheme.colorScheme.secondary
            ),
            border = BorderStroke(
                1.dp,
                MaterialTheme.colorScheme.secondary
            )
        ) {
            Text(
                text = if (bloqueado) "Desbloquear contadores"
                else "Bloquear contadores"
            )
        }

        if (mensajeBloqueo.isNotEmpty()) {
            Text(text = mensajeBloqueo)
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(modifier = Modifier.weight(1f)) {
                TarjetaContador(
                    titulo = "Observaciones",
                    contador = contadorTemporal,
                    textoBoton = "Agregar",
                    onIncrementar = {
                        if (!bloqueado) {
                            contadorTemporal++
                            mensajeBloqueo = ""
                        } else {
                            mensajeBloqueo = "No se puede agregar una observación. Contadores bloqueados."
                        }
                    }
                )
            }

            Box(modifier = Modifier.weight(1f)) {
                TarjetaContador(
                    titulo = "Colmenas inspeccionadas",
                    contador = contadorPersistente,
                    textoBoton = "Agregar",
                    onIncrementar = {
                        if (!bloqueado) {
                            contadorPersistente++
                            mensajeBloqueo = ""
                        } else {
                            mensajeBloqueo = "No se puede agregar una colmena. Contadores bloqueados."
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun LabelAndField(
    etiqueta: String,
    valor: String,
    onValorCambio: (String) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(text = etiqueta)

        OutlinedTextField(
            value = valor,
            onValueChange = onValorCambio,
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.secondary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                cursorColor = MaterialTheme.colorScheme.secondary
            )
        )
    }
}

@Composable
fun TarjetaContador(
    titulo: String,
    contador: Int,
    textoBoton: String,
    onIncrementar: () -> Unit
) {
    Column(
        modifier = Modifier
            .background(
                color = Color(0xFFFFFDF8),
                shape = RoundedCornerShape(12.dp)
            )
            .border(
                width = 1.dp,
                color = Color(0xFFD8D3C8),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = titulo,
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
            maxLines = 2
        )

        Text(
            text = "$contador",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Button(
            onClick = onIncrementar,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary
            )
        ) {
            Text(text = textoBoton)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PantallaApiarioPreview() {
    Lab04_mmTheme {
        PantallaApiario()
    }
}