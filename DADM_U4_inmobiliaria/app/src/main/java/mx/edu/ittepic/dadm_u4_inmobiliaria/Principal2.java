package mx.edu.ittepic.dadm_u4_inmobiliaria;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class Principal2 extends AppCompatActivity {
    BaseDatos base;
    EditText identificacion2, identificacion3, domiciolio, venta, renta, fecha;
    ImageButton insertar, consultar, eliminar, actualizar;

    int num = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal2);
        identificacion2 = findViewById(R.id.idss);
        domiciolio = findViewById(R.id.domicilios);
        venta = findViewById(R.id.venta);
        renta = findViewById(R.id.renta);
        fecha = findViewById(R.id.fechatrans);
        identificacion3 = findViewById(R.id.ide);

        consultar = findViewById(R.id.editara);
        eliminar = findViewById(R.id.eliminaciona);
        actualizar = findViewById(R.id.actualizaciona);
        insertar = findViewById(R.id.guardaraa);


        base = new BaseDatos(this, "primera", null, 2);
        consultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consolta();
            }
        });
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pedirID(3);
                num = 0;
            }
        });
        insertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pedirID(1);
                num = 0;
            }
        });

        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (num == 2) {
                    aplicarActualizar();
                } else {
                    pedirID(2);
                }
            }
        });
    }

    private void consolta() {

        AlertDialog.Builder alerta = new AlertDialog.Builder(this);


        alerta.setTitle("Atencion")//.setMessage(mensaje)

                .setPositiveButton("Confirmar cambio ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {


                            SQLiteDatabase tabla = base.getWritableDatabase();


                            String SQL = "INSERT INTO INMUEBLE VALUES(1,'%2',3,4,'%5',6)";
                            SQL = SQL.replace("1", identificacion2.getText().toString());
                            SQL = SQL.replace("%2", domiciolio.getText().toString());
                            SQL = SQL.replace("3", venta.getText().toString());
                            SQL = SQL.replace("4", renta.getText().toString());
                            SQL = SQL.replace("%5", fecha.getText().toString());
                            SQL = SQL.replace("6", identificacion3.getText().toString());
                            tabla.execSQL(SQL);

                            Toast.makeText(Principal2.this, "Si se pudo", Toast.LENGTH_LONG).show();
                            tabla.close();

                        } catch (SQLiteException e) {

                            Toast.makeText(Principal2.this, "No se pudo", Toast.LENGTH_LONG).show();

                        }
                    }
                })
                .setNegativeButton("Cancelar", null).show();

    }
    private void aplicarActualizar() {

        try {
            num = 0;
            SQLiteDatabase tabla = base.getWritableDatabase();
            String SQL = "UPDATE INMUEBLE SET DOMICILIO='" + domiciolio.getText().toString() + "', " +
                    "PRECIOVENTA=" + venta.getText().toString() + ", " +
                    "PRECIORENTA=" + renta.getText().toString() + "," +
                    "FECHATRANSACCION='" + fecha.getText().toString() +
                    "' WHERE ID=" + identificacion2.getText().toString();
            tabla.execSQL(SQL);
            tabla.close();
            Toast.makeText(this, "Se actualizo", Toast.LENGTH_LONG).show();

        } catch (SQLiteException e) {
            Toast.makeText(this, "No se pudo actualizar", Toast.LENGTH_LONG).show();
        }
        habilitarBotonesYLimpiarCampos();
    }


    private void habilitarBotonesYLimpiarCampos() {
        identificacion2.setText("");
        identificacion3.setText("");
        domiciolio.setText("");
        renta.setText("");
        venta.setText("");
        fecha.setText("");

        insertar.setEnabled(true);
        consultar.setEnabled(true);
        eliminar.setEnabled(true);
        identificacion2.setEnabled(true);
        identificacion3.setEnabled(true);


    }

    private void pedirID(final int origen) {
        final EditText pidoID = new EditText(this);
        pidoID.setInputType(InputType.TYPE_CLASS_NUMBER);
        pidoID.setHint("Valor entero mayor de 0");
        String mensaje = "Escriba el id a buscar";

        AlertDialog.Builder alerta = new AlertDialog.Builder(this);

        if (origen == 2) {
            mensaje = "Ecriba el id a modificar";
        }
        if (origen == 3) {
            mensaje = "Escriba que desea eliminar";
        }

        alerta.setTitle("Atencion").setMessage(mensaje)
                .setView(pidoID)
                .setPositiveButton("Buscar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (pidoID.getText().toString().isEmpty()) {
                            Toast.makeText(Principal2.this, "Debes escribir un numero", Toast.LENGTH_LONG).show();
                            return;
                        }
                        buscarDato(pidoID.getText().toString(), origen);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Cancelar", null).show();
    }

    private void buscarDato(String idaBuscar, int origen) {
        try {

            SQLiteDatabase tabla = base.getReadableDatabase();

            String SQL = "SELECT *FROM INMUEBLE WHERE ID=" + idaBuscar;

            Cursor resultado = tabla.rawQuery(SQL, null);
            if (resultado.moveToFirst()) {//mover le primer resultado obtenido de la consulta
                //si hay resulta´do
                if (origen == 3) {
                    //se consulto para borrar
                    String dato = idaBuscar + "&" + resultado.getString(1) + "&" + resultado.getString(2) +
                            "&" + resultado.getString(3) + "&" + resultado.getString(4) + "&" + resultado.getString(5);
                    invocarConfirmacionEliminacion(dato);
                    return;
                }

                identificacion2.setText(resultado.getString(0));
                domiciolio.setText(resultado.getString(1));
                venta.setText(resultado.getString(2));
                renta.setText(resultado.getString(3));
                fecha.setText(resultado.getString(4));
                identificacion3.setText(resultado.getString(5));
                Toast.makeText(Principal2.this, "Datos mostrados correctamente", Toast.LENGTH_LONG).show();
                if (origen == 2) {
                    //modificar
                    insertar.setEnabled(false);
                    consultar.setEnabled(false);
                    eliminar.setEnabled(false);
                    identificacion2.setEnabled(false);
                    identificacion3.setEnabled(false);


                    AlertDialog.Builder confir = new AlertDialog.Builder(this);
                    confir.setTitle("IMPORTNATE").setMessage("estas seguro que deseas aplicar cambios de actualizacion")
                            .setPositiveButton("si", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    num = 2;
                                    Toast.makeText(Principal2.this, "Preciona Update de nuevo para confirmar los cambios", Toast.LENGTH_LONG).show();
                                    dialog.dismiss();
                                }
                            }).setNegativeButton("cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            habilitarBotonesYLimpiarCampos();
                            dialog.cancel();
                        }
                    }).show();
                }
            } else {
                //no hay resultado!
                Toast.makeText(this, "No se ENCONTRO EL RESULTADO", Toast.LENGTH_LONG).show();
            }
            tabla.close();

        } catch (SQLiteException e) {
            Toast.makeText(this, "No se pudo buscar", Toast.LENGTH_LONG).show();
        }
    }

    private void invocarConfirmacionEliminacion(String dato) {
        String datos[] = dato.split("&");
        final String id = datos[0];
        String nombre = datos[1];

        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setTitle("atencion").setMessage("Usted eliminara esta informacion y no podra recuperar ")
                .setPositiveButton("Si a todo", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        eliminarIdtodo(id);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Cancelar", null).show();
    }

    private void eliminarIdtodo(String idEliminar) {
        try {
            SQLiteDatabase tabla = base.getReadableDatabase();

            String SQL = "DELETE FROM INMUEBLE WHERE ID=" + idEliminar;
            tabla.execSQL(SQL);
            tabla.close();
            identificacion2.setText("");
            identificacion3.setText("");
            renta.setText("");
            fecha.setText("");
            venta.setText("");
            domiciolio.setText("");


            Toast.makeText(this, "Se elimino el dato", Toast.LENGTH_LONG).show();
        } catch (SQLiteException e) {
            Toast.makeText(this, "No se pudo eliminar", Toast.LENGTH_LONG).show();
        }
    }
}







