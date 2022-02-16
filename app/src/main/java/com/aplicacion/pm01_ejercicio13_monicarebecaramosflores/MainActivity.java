package com.aplicacion.pm01_ejercicio13_monicarebecaramosflores;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText d_Dni, d_Nombres, d_Apellidos, d_Edad, d_Correo, d_Direccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        d_Dni = (EditText) findViewById(R.id.txtDni);
        d_Nombres = (EditText) findViewById(R.id.txtNombres);
        d_Apellidos = (EditText) findViewById(R.id.txtApellidos);
        d_Edad = (EditText) findViewById(R.id.txtEdad);
        d_Correo = (EditText) findViewById(R.id.txtCorreo);
        d_Direccion = (EditText) findViewById(R.id.txtDireccion);
    }
    // Guardar registros
    public void Registrar(View view){
        SQLiteConexion cnx = new SQLiteConexion(this, "BasedeDatos", null, 1);
        SQLiteDatabase BasedeDatos = cnx.getWritableDatabase();

        String Dni = d_Dni.getText().toString();
        String Nombres = d_Nombres.getText().toString();
        String Apellidos = d_Apellidos.getText().toString();
        String Edad = d_Edad.getText().toString();
        String Correo= d_Correo.getText().toString();
        String Direccion = d_Direccion.getText().toString();

        if (!Dni.isEmpty() && !Nombres.isEmpty() && !Apellidos.isEmpty() && !Edad.isEmpty() && !Correo.isEmpty() && !Direccion.isEmpty()){

            ContentValues registro = new ContentValues();
            registro.put("Dni", Dni);
            registro.put("Nombres", Nombres);
            registro.put("Apellidos", Apellidos);
            registro.put("Edad", Edad);
            registro.put("Correo", Correo);
            registro.put("Direccion", Direccion);

            BasedeDatos.insert("persona", null,registro);

            BasedeDatos.close();
            d_Dni.setText("");
            d_Nombres.setText("");
            d_Apellidos.setText("");
            d_Edad.setText("");
            d_Correo.setText("");
            d_Direccion.setText("");
            Toast.makeText(this, "Guardando registros", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Por favor ingrese correctamente los campos", Toast.LENGTH_SHORT).show();
        }
    }

    // Consultar registros
    public void Consultar(View view){
        SQLiteConexion cnx = new SQLiteConexion(this, "BasedeDatos", null, 1);
        SQLiteDatabase BasedeDatos = cnx.getWritableDatabase();

        String Dni = d_Dni.getText().toString();

        if (!Dni.isEmpty()){
            Cursor fila = BasedeDatos.rawQuery("select nombre, apellidos, edad, correo, direccion where dni ="+Dni, null);

            if (fila.moveToFirst()){
                d_Dni.setText(fila.getString(0));
                d_Nombres.setText(fila.getString(1));
                d_Apellidos.setText(fila.getString(2));
                d_Edad.setText(fila.getString(3));
                d_Correo.setText(fila.getString(4));
                d_Direccion.setText(fila.getString(5));
                BasedeDatos.close();
            }else{
                Toast.makeText(this, "No se encontro el registro", Toast.LENGTH_LONG).show();
                BasedeDatos.close();
            }

        }else{
            Toast.makeText(this, "Por favor ingrese el campo DNI que desea consultar", Toast.LENGTH_LONG).show();
        }
    }
}