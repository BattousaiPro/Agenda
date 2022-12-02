package com.example.agenda.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agenda.R;
import com.example.agenda.VerActivity;
import com.example.agenda.entidades.Contactos;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListaContactosAdapter extends RecyclerView.Adapter<ListaContactosAdapter.ContactoViewHolder> {


    ArrayList<Contactos> listaContactos; // Trae La Informacion De Todo Los contactos
    ArrayList<Contactos> listaOriginal; // Permite Filtros De Los Contactos

    //Contructor
    public ListaContactosAdapter(ArrayList<Contactos> listaContactos) { // Asigna Lo Que Trae El Contructor
        this.listaContactos = listaContactos;
        listaOriginal = new ArrayList<>();
        listaOriginal.addAll(listaContactos); // Trae Toda La Informacion Que Genera La Lista De La Base De Datos Que Se Reguistran
    }

    @NonNull
    @Override
    public ContactoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item_contactos, null, false); // Resible Contecto De Cada Elemento De La Lista
        return new ContactoViewHolder(view); // Envia Toda La Informacion
    }

    @Override
    public void onBindViewHolder(@NonNull ContactoViewHolder holder, int position) { // <-------- De La Que Inicia Desde 0
        holder.viewNombre.setText(listaContactos.get(position).getNombre()); // Trae Los Elementos De La Posision Que Se Esta Indicando
        holder.viewTelefono.setText(listaContactos.get(position).getTelefono()); // Trae Los Elementos De La Posision Que Se Esta Indicando
        holder.viewCorreo.setText(listaContactos.get(position).getCorreo_electornico()); // Trae Los Elementos De La Posision Que Se Esta Indicando
    }

    public void filtrado(final String txtBuscar) { // Recibe Cadena De Texto De La Busqueda
        int longitud = txtBuscar.length(); // Calculo Fuera Del Metodo Para Aumentar La Velocidad
        if (longitud == 0) {
            listaContactos.clear(); // Se Limpia lista De Contacto
            listaContactos.addAll(listaOriginal); // Si no Trae Informacion Que Reguese Todo Los Datos Que Ya Tenemos
        } else { // Si Encuenta Caracter En El Caso De Filtracion
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                List<Contactos> collecion = listaContactos.stream() // Trae Informacion De La Lista Contacto
                        .filter(i -> i.getNombre().toLowerCase().contains(txtBuscar.toLowerCase())) // Se Manda a Buscar ( txtBuscar )
                        .collect(Collectors.toList()); // Para Pasar Las Letras Masyusculas a Minusculas Y si Usuario Escribe Con Minusculas Para Generar Una Mayor Frecuencia En La Navedracion
                listaContactos.clear();
                listaContactos.addAll(collecion);
            } else {
                for (Contactos c : listaOriginal) { // Para Generar Compatibilidad En Caso Que No Sea Compatible Con La Vercion
                    if (c.getNombre().toLowerCase().contains(txtBuscar.toLowerCase())) { // Trae Solo Lo Que Se Busca
                        listaContactos.add(c); // Muestra Los Resultados De Forma Correcta
                    }
                }
            }
        }
        notifyDataSetChanged(); // Se Notifican Los Cambios
    }

    @Override
    public int getItemCount() {
        return listaContactos.size();
    } // Tamaño De La Linta

    public class ContactoViewHolder extends RecyclerView.ViewHolder {

        TextView viewNombre;
        TextView viewTelefono;
        TextView viewCorreo;

        public ContactoViewHolder(@NonNull View itemView) { // Dentro De Su Contructor
            super(itemView);

            viewNombre = itemView.findViewById(R.id.viewNombre); // Asegura Los Elementos De La Lista
            viewTelefono = itemView.findViewById(R.id.viewTelefono); // Asegura Los Elementos De La Lista
            viewCorreo = itemView.findViewById(R.id.viewCorreo); // Asegura Los Elementos De La Lista

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) { // Cuando Se Preciona el Item De La Lista Se Dispara El Vento
                    Context context = view.getContext(); // Trael El Contexto Enviado
                    Intent intent = new Intent(context, VerActivity.class); // Al Momento De Precionan Nos Dirigimos a Nuestra Clase ( VerActivity )
                    intent.putExtra("ID", listaContactos.get(getAdapterPosition()).getId()); // Se Trae El ID De Cada Posicion De Los Contactos
                    context.startActivity(intent); // Se Le Pasa La Informacion a La Vista De Contactos
                }
            });
        }
    }
}