package com.example.agenda;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.agenda.adaptadores.ListaContactosAdapter;
import com.example.agenda.db.DbContactos;
import com.example.agenda.db.DbHelper;
import com.example.agenda.entidades.Contactos;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener { // Se Agrega Implementacion De La Lupa

    SearchView txtBuscar; // <------
    RecyclerView listaContactos;
    ArrayList<Contactos> listaArrayContactos;
    FloatingActionButton fabNuevo;
    ListaContactosAdapter adapter; // Se Hase Publico Para Todo Los Metodos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtBuscar = findViewById(R.id.txtBuscar); // Lupa De Busqueda De Contacto
        listaContactos = findViewById(R.id.listaContactos);
        fabNuevo = findViewById(R.id.favNuevo);
        listaContactos.setLayoutManager(new LinearLayoutManager(this));

        DbContactos dbContactos = new DbContactos(MainActivity.this);

        listaArrayContactos = new ArrayList<>();

        adapter = new ListaContactosAdapter(dbContactos.mostrarContactos()); // Adapter Donde Se Trabaja ( Lista Contacto Adapter )
        listaContactos.setAdapter(adapter); // Se Envia Todo Los Resusltados De Las Consultas ( Para Procesar Correctamente Nuestra Lista )

        fabNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nuevoRegistro();
            }
        });

        txtBuscar.setOnQueryTextListener(this); // Se Indica Funcionabilidad Al Momento De Leer o Buscar Un Tecto
    }

    public boolean onCreateOptionsMenu(Menu menu){ // Metodo
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_principal, menu); // Indica Nuestro Menu Principal
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){ // Metodo
        switch (item.getItemId()){ // Se Evalua
            case R.id.menuNuevo: // Trae El ID Del Elemento Selecionado
                nuevoRegistro();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void nuevoRegistro(){ // Metodo
        Intent intent = new Intent(this, NuevoActivity.class); // Para Pasar De Una Vista a Otra
        startActivity(intent); // Pasa A Otra Actividad
    }

    // Estos 2 Metodos Tiene La Facibilidad De La Busqueda En Tienpo Real
    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) { // Sale De Aqui El Metodo String LLamado (S)
        adapter.filtrado(s); // Metodo a Utilizar Para Realizar Los Filtros De La Lupa
        return false;
    }
} // Realiza La Comparacion De Forma Interna Para Realizar La Busqueda De Forma Correcta