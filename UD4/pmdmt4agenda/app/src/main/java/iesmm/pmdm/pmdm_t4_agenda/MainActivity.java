package iesmm.pmdm.pmdm_t4_agenda;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final String FILENAME  ="contactos.csv";
    private ArrayList<Contacto>contactos=new ArrayList<>();
    private ArrayAdapter<Contacto> adapter;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        confirmarPermisoLlamada();
        recuperarDatos();
        addItems(contactos);
    }

    private void recuperarDatos() {
        BufferedReader b= null;
        try {

            InputStreamReader inputStreamReader = new InputStreamReader( this.openFileInput(FILENAME),"UTF-8");
            b = new BufferedReader(inputStreamReader);
                String linea="";
                while((linea=b.readLine())!=null){
                    if(!linea.isEmpty()){
                        String[] cadena= linea.split(";");
                        contactos.add(new Contacto(cadena[0],cadena[1],cadena[2]));
                    }
                }


        } catch (FileNotFoundException e) {
            e.printStackTrace();

    } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addItems(ArrayList contactos){
        ListView lista=this.findViewById(R.id.listView1);
        adapter =new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,contactos);
        lista.setAdapter(adapter);


        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                muestraDialogo(adapter.getItem(i));
            }
        });

    }

    private void muestraDialogo(Contacto contacto){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Deseas realizar alguna acción?")
                .setCancelable(false)
                .setPositiveButton("Llamar al " + contacto.getTelefono(), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                      realizarLlamada(contacto.getTelefono());
                    }
                })

                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }


    public void realizarLlamada(String telefono) {
        Uri uri= Uri.parse("tel:"+telefono);
        this.startActivity(new Intent(Intent.ACTION_CALL,uri));
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean confirmarPermisoLlamada() {
        boolean confirmado = false;

        if (ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
        {
            this.requestPermissions(new String[]{android.Manifest.permission.CALL_PHONE}, 0);
        }
        else
            confirmado = true;

        return confirmado;
    }
}