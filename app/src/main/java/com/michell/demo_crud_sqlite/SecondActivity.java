package com.michell.demo_crud_sqlite;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by MICHELL on 27/06/2017.
 */

public class SecondActivity extends AppCompatActivity {

    private EditText etNombre, etApellido, etEdad, etDocumento, etTelefono, etDisponible;
    private Spinner spDisponible;
    private Button btActualizar, btEliminar;

    private final View.OnClickListener btActualizarOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            String name = etNombre.getText().toString();
            String ape = etApellido.getText().toString();
            String edad = etEdad.getText().toString();
            String doc = etDocumento.getText().toString();
            String tel = etTelefono.getText().toString();
            String dis = spDisponible.getSelectedItem().toString();

            if(dis.equals("Activo")) {

                intent.putExtra("nombre", name);
                intent.putExtra("apellido", ape);
                intent.putExtra("edad", edad);
                intent.putExtra("documento", doc);
                intent.putExtra("telefono", tel);
                intent.putExtra("disponible", String.valueOf(true));

                setResult(RESULT_OK, intent);
                finish();
            }
            else {
                intent.putExtra("nombre", name);
                intent.putExtra("apellido", ape);
                intent.putExtra("edad", edad);
                intent.putExtra("documento", doc);
                intent.putExtra("telefono", tel);
                intent.putExtra("disponible", String.valueOf(false));

                setResult(RESULT_OK, intent);
                finish();
            }

        }
    };

    private final View.OnClickListener btEliminarOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            String name = etNombre.getText().toString();
            String ape = etApellido.getText().toString();
            String edad = etEdad.getText().toString();
            String doc = etDocumento.getText().toString();
            String tel = etTelefono.getText().toString();
            String dis = spDisponible.getSelectedItem().toString();

            if(dis.equals("Activo")) {

                intent.putExtra("nombre", name);
                intent.putExtra("apellido", ape);
                intent.putExtra("edad", edad);
                intent.putExtra("documento", doc);
                intent.putExtra("telefono", tel);
                intent.putExtra("disponible", String.valueOf(true));

                setResult(10, intent);
                finish();
            }
            else {
                intent.putExtra("nombre", name);
                intent.putExtra("apellido", ape);
                intent.putExtra("edad", edad);
                intent.putExtra("documento", doc);
                intent.putExtra("telefono", tel);
                intent.putExtra("disponible", String.valueOf(false));

                setResult(10, intent);
                finish();
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);

        etNombre = (EditText) findViewById(R.id.etNombre);
        etApellido = (EditText) findViewById(R.id.etApellido);
        etEdad = (EditText) findViewById(R.id.etEdad);
        etDocumento = (EditText) findViewById(R.id.etDocumento);
        etTelefono = (EditText) findViewById(R.id.etTelefono);
        //etDisponible = (EditText) findViewById(R.id.etDisponible);
        spDisponible = (Spinner) findViewById(R.id.spDisponible);

        btActualizar = (Button) findViewById(R.id.btActualizar);
        btEliminar = (Button) findViewById(R.id.btEliminar);

        Intent intent = getIntent();

        String nombre = intent.getStringExtra("nombre");
        String ape = intent.getStringExtra("apellido");
        String edad = intent.getStringExtra("edad");
        String doc = intent.getStringExtra("documento");
        String tel = intent.getStringExtra("telefono");
        String dis = intent.getStringExtra("disponible");


        etNombre.setText(nombre);
        etApellido.setText(ape);
        etEdad.setText(edad);
        etDocumento.setText(doc);
        etTelefono.setText(tel);
        spDisponible.setSelected(Boolean.parseBoolean(dis));

        String name = etNombre.getText().toString();

        if(name.equals("") || name == null) {
            btActualizar.setText("REGISTRAR");
            btEliminar.setEnabled(false);
        }


        btActualizar.setOnClickListener(btActualizarOnClickListener);
        btEliminar.setOnClickListener(btEliminarOnClickListener);


    }
}
