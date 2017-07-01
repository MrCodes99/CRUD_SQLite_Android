package com.michell.demo_crud_sqlite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    private Button btMain;
    private ListView lvMain;
    private LVMainAdapter mLVMainAdapter;
    private  int REQUEST_CODE = 70;
    private int UPDATE_CODE = 80;
    private int posi = -1;

    private final View.OnClickListener btMainOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);

            startActivityForResult(intent, REQUEST_CODE);
        }
    };

    private final AdapterView.OnItemClickListener lvMainOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    Persona persona = mLVMainAdapter.getItem(position);

        posi = position;

        Intent intent = new Intent(MainActivity.this, SecondActivity.class);

        intent.putExtra("id", persona.getId().toString());
        intent.putExtra("nombre", persona.getNombre().toString());
        intent.putExtra("apellido", persona.getApellido().toString());
        intent.putExtra("edad", String.valueOf(persona.getEdad()));
        intent.putExtra("documento", persona.getDocumento().toString());
        intent.putExtra("telefono", persona.getTelefono().toString());
        intent.putExtra("disponible", String.valueOf(persona.isDisponible()));

        startActivityForResult(intent, UPDATE_CODE);


            /*ClienteBean obj = (ClienteBean)lsvcliente.getItemAtPosition(position);
        Intent ir = new Intent(this,ClienteRegistrarActivity.class);
        ir.putExtra("cli",obj);
        startActivity(ir);*/

        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btMain = (Button) findViewById(R.id.btMain);
        lvMain = (ListView) findViewById(R.id.lvMain);

        btMain.setOnClickListener(btMainOnClickListener);
        lvMain.setOnItemClickListener(lvMainOnItemClickListener);

        mLVMainAdapter = new LVMainAdapter(MainActivity.this);
        lvMain.setAdapter(mLVMainAdapter);

        try {
            //INICIALIANDO EL DATABASE HELPER
            DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);
            //SI NO EXISTE LA BD EN EL MÓVIL LA COPIA DEL ASSETS SINO IGNORA EL PASO
            dataBaseHelper.createDataBase();

            //OBTENEMOS TODA LA TABLA PERSONA Y LA LISTAMOS EN EL LISTVIEW
            List<Persona> lstPersona = new PersonaDAO(MainActivity.this).listPersonas();
            /*TextView tvDatabase = (TextView) findViewById(R.id.tvDatabase);
            for (Persona persona : lstPersona) {
                Log.d("PERSONA", persona.getNombre() + " " + persona.getApellido());
                tvDatabase.setText(tvDatabase.getText().toString() + persona.getNombre() + " " + persona.getApellido() + "\n");
            }*/
            // AGREGAMOS LA COLECCION EN EL ADAPTADOR
            mLVMainAdapter.addAll(lstPersona);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        /*Persona persona;
        List<Persona> lstPersona = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            persona = new Persona();
            persona.setId(java.util.UUID.randomUUID().toString());
            persona.setNombre(java.util.UUID.randomUUID().toString().replace("-","").substring(0,6));
            persona.setApellido(java.util.UUID.randomUUID().toString().replace("-","").substring(7,13));

            lstPersona.add(persona);
        }

        mSPMainAdapter.addAll(lstPersona);*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK) {
            if(requestCode == REQUEST_CODE) {

                Persona persona= new Persona();

                persona.setNombre(data.getStringExtra("nombre"));
                persona.setApellido(data.getStringExtra("apellido"));
                persona.setEdad(Integer.valueOf(data.getStringExtra("edad")));
                persona.setDocumento(data.getStringExtra("documento"));
                persona.setTelefono(data.getStringExtra("telefono"));
                persona.setDisponible(Boolean.valueOf(data.getStringExtra("disponible")));

                DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);
                //SI NO EXISTE LA BD EN EL MÓVIL LA COPIA DEL ASSETS SINO IGNORA EL PASO
                try {
                    dataBaseHelper.createDataBase();

                    long agregar = new PersonaDAO(MainActivity.this).insert(persona);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                mLVMainAdapter.add(persona);

            }
            else if(requestCode==UPDATE_CODE){

                Persona persona = mLVMainAdapter.getItem(posi);

                mLVMainAdapter.remove(persona);

                persona.setNombre(data.getStringExtra("nombre"));
                persona.setApellido(data.getStringExtra("apellido"));
                persona.setEdad(Integer.valueOf(data.getStringExtra("edad")));
                persona.setDocumento(data.getStringExtra("documento"));
                persona.setTelefono(data.getStringExtra("telefono"));
                persona.setDisponible(Boolean.valueOf(data.getStringExtra("disponible")));

                DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);
                //SI NO EXISTE LA BD EN EL MÓVIL LA COPIA DEL ASSETS SINO IGNORA EL PASO
                try {
                    dataBaseHelper.createDataBase();

                   PersonaDAO dao = new PersonaDAO(MainActivity.this);
                    dao.update(persona);

                } catch (IOException e) {
                    e.printStackTrace();
                }

                mLVMainAdapter.insert(persona,posi);
                mLVMainAdapter.notifyDataSetChanged();
            }
        }
        else if(resultCode == 10) {
            Persona persona = mLVMainAdapter.getItem(posi);

            DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);
            //SI NO EXISTE LA BD EN EL MÓVIL LA COPIA DEL ASSETS SINO IGNORA EL PASO
            try {
                dataBaseHelper.createDataBase();

                PersonaDAO dao = new PersonaDAO(MainActivity.this);
                dao.delete(persona);

            } catch (IOException e) {
                e.printStackTrace();
            }

            mLVMainAdapter.remove(persona);

            mLVMainAdapter.notifyDataSetChanged();
        }

    }
}
