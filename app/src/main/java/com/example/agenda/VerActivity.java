package com.example.agenda;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.agenda.db.DbContactos;
import com.example.agenda.entidades.Contactos;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class VerActivity extends AppCompatActivity {

    EditText txtNombre;
    EditText txtTelefono;
    EditText txtCorreo;
    Button btnGuarda;
    FloatingActionButton fabEditar, fabEliminar;

    Contactos contacto;
    int id = 0; // ( Gloval )

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver);
        // Variables Replicadas De La Vista  ( Metodos Principales )
        txtNombre = findViewById(R.id.txtNombre);
        txtTelefono = findViewById(R.id.txtTelefono);
        txtCorreo = findViewById(R.id.txtCorreoElectronico);
        fabEditar = findViewById(R.id.fabEditar);
        fabEliminar = findViewById(R.id.fabEliminar);
        btnGuarda = findViewById(R.id.btnGuarda);
        btnGuarda.setVisibility(View.INVISIBLE);

        if(savedInstanceState == null){ // Recibe Variable Que Estamos Pasando
            Bundle extras = getIntent().getExtras(); // Enviamos Extra
            if(extras == null){ // Resivimos Extras Para No Tener Errores
                id = Integer.parseInt(null);
            } else {
                id = extras.getInt("ID");
            }
        } else {
            id = (int) savedInstanceState.getSerializable("ID"); // Se Realiza Casteo Para Validar El Parametro Enviado
        }

        final DbContactos dbContactos = new DbContactos(VerActivity.this);
        contacto = dbContactos.verContacto(id);

        if(contacto != null){ // Se Asigna Validacion Para Que Traiga La Validacion Correcta
            txtNombre.setText(contacto.getNombre());
            txtTelefono.setText(contacto.getTelefono());
            txtCorreo.setText(contacto.getCorreo_electornico());
            txtNombre.setInputType(InputType.TYPE_NULL); // Para Que No Habra El Teclado
            txtTelefono.setInputType(InputType.TYPE_NULL); // Para Que No Habra El Teclado
            txtCorreo.setInputType(InputType.TYPE_NULL); // Para Que No Habra El Teclado
        }

        // Se Crea Evento De Boton
        fabEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VerActivity.this, EditarActivity.class);
                intent.putExtra("ID", id);
                startActivity(intent);
            }
        });

        fabEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(VerActivity.this); // Mensaje Que Le Indica Al Usuario Si Esta Seguro De Borrar El Elemento De La Vista
                builder.setMessage("¿Desea eliminar este contacto?") // Se Indica Mensaje Al Usuario
                        .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) { // Se Agrega Evento Al Precionar El Boton

                                if(dbContactos.eliminarContacto(id)){ // LLama Al Metodo a La Base De Datos (ID Variable Gloval )
                                    lista(); // Se Regresa A La Lista
                                }
                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() { // En Caso Que La Respuesta Sea Negativa
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) { // Se Genera Otra Opcion En Caso Que se Precione El No

                            }
                        }).show(); // Muentra Alestar
            }
        });
    }

    private void lista(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent); // Pasa Al IF De ( onClick )
    }
}
  // AndroidManifest De La Linea 39 a 45 Flecha hacia a tras
 // AndroidManifest De La Linea 47 a 54 Generar Vista Nueva Para Inclucion  ( fab )