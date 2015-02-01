package com.example.minutrade;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
	private EditText etName, etPrice, etDescription;
	private Button btnInsert, btnDelete, btnUpdate, btnSearch, btnList;
	private String name, price, description, notification;
	public static ArrayList<Product> products = new ArrayList<Product>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		etName = (EditText) findViewById(R.id.etName);
		etPrice = (EditText) findViewById(R.id.etPrice);
		etDescription = (EditText) findViewById(R.id.etDescription);
		btnInsert = (Button) findViewById(R.id.btnInsert);
		btnSearch = (Button) findViewById(R.id.btnSearch);
		btnDelete = (Button) findViewById(R.id.btnDelete);
		btnUpdate = (Button) findViewById(R.id.btnUpdate);
		btnList = (Button) findViewById(R.id.btnList);

		btnInsert.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				name = etName.getText().toString().toUpperCase();
				price = etPrice.getText().toString();
				description = etDescription.getText().toString().toUpperCase();

				if (valDatos()) {
					alerta("Error", notification);
				} else {
					insert();
				}

			}
		});

		btnSearch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				name = etName.getText().toString().toUpperCase();

				if ((name.length() < 1)) {
					alerta("Error", "\nName can't be null");
				} else {
					search();
				}

			}
		});

		btnDelete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				name = etName.getText().toString().toUpperCase();

				if ((name.length() < 1)) {
					alerta("Error", "\nName can't be null");
				} else {
					delete();
				}

			}
		});

		btnUpdate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				name = etName.getText().toString().toUpperCase();
				price = etPrice.getText().toString();
				description = etDescription.getText().toString().toUpperCase();

				if (valDatos()) {
					alerta("Error", notification);
				} else {
					update();
				}

			}
		});

		btnList.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				searchAll();
				Intent i = new Intent(MainActivity.this, List.class);
				startActivity(i);

			}
		});
	}

	public boolean valDatos() {
		boolean error = false;
		notification = "";
		if (name.length() < 1) {
			notification += "\nName can't be null";
			error = true;
		}
		if (price.length() < 1) {
			notification += "\nPrice can't be null";
			error = true;
		}
		if (description.length() < 1) {
			notification += "\nDescription can't be null";
			error = true;
		}

		return error;
	}

	public void alerta(String titulo, String texto) {
		AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
		dialogo1.setTitle(titulo);
		dialogo1.setMessage(texto);
		dialogo1.setCancelable(false);
		dialogo1.setPositiveButton("Aceptar",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialogo1, int id) {

					}
				});
		dialogo1.show();
	}

	public void insert() {
		AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "admin",
				null, 1);
		SQLiteDatabase bd = admin.getWritableDatabase();

		ContentValues item = new ContentValues();
		item.put("name", name);
		item.put("price", price);
		item.put("description", description);
		long result = bd.insert("products", null, item);
		bd.close();
		if (result != -1) {
			etName.setText("");
			etPrice.setText("");
			etDescription.setText("");
			Toast.makeText(this, "Product saved", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(this, "Duplicate name", Toast.LENGTH_SHORT).show();
		}

	}

	public void search() {
		AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "admin",
				null, 1);
		SQLiteDatabase bd = admin.getWritableDatabase();

		Cursor fila = bd.rawQuery(
				"select price, description  from products where name='" + name
						+ "'", null);
		if (fila.moveToFirst()) {
			etPrice.setText(fila.getString(0));
			etDescription.setText(fila.getString(1));
		} else {
			Toast.makeText(this, "Product not found", Toast.LENGTH_SHORT)
					.show();
			etPrice.setText("");
			etDescription.setText("");
		}

		bd.close();

	}

	public void delete() {
		AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "admin",
				null, 1);
		SQLiteDatabase bd = admin.getWritableDatabase();
		int result = bd.delete("products", "name='" + name + "'", null);
		bd.close();
		if (result == 1) {
			Toast.makeText(this, "Product deleted successfully",
					Toast.LENGTH_SHORT).show();
			etName.setText("");
			etPrice.setText("");
			etDescription.setText("");
		} else
			Toast.makeText(this, "Product not found", Toast.LENGTH_SHORT)
					.show();
	}

	public void update() {
		AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "admin",
				null, 1);
		SQLiteDatabase bd = admin.getWritableDatabase();
		ContentValues item = new ContentValues();
		item.put("name", name);
		item.put("price", price);
		item.put("description", description);
		int result = bd.update("products", item, "name='" + name + "'", null);
		bd.close();
		if (result == 1)
			Toast.makeText(this, "Product updated successfully",
					Toast.LENGTH_SHORT).show();
		else
			Toast.makeText(this, "Product not found", Toast.LENGTH_SHORT)
					.show();
	}

	public void searchAll() {
		AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "admin",
				null, 1);
		SQLiteDatabase bd = admin.getWritableDatabase();
		products.clear();

		Cursor cursor = bd.rawQuery("select * from products", null);
		if (cursor.moveToFirst()) {

			while (cursor.isAfterLast() == false) {
				String name = cursor.getString(cursor.getColumnIndex("name"));
				String price = cursor.getString(cursor.getColumnIndex("price"));
				String description = cursor.getString(cursor
						.getColumnIndex("description"));
				Product aux = new Product(name, price, description);
				products.add(aux);
				cursor.moveToNext();
			}
		}
		bd.close();
	}

}