package com.example.minutrade;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class List extends ActionBarActivity {
	private ListView lv1;
	private TextView empty;
	public static ArrayList<Product> products = new ArrayList<Product>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);
		lv1 = (ListView) findViewById(R.id.lv1);
		empty = (TextView) findViewById(R.id.empty);
		products.clear();
		for (int i = 0; i <= com.example.minutrade.MainActivity.products.size() - 1; i++) {
			products.add(com.example.minutrade.MainActivity.products.get(i));
		}
		if (products.size()==0){
			empty.setText("There are no products registered products.");
		}

		Adaptador adapter = new Adaptador(this);
		lv1.setAdapter(adapter);
	}

	class Adaptador extends ArrayAdapter<Product> {

		Activity context;

		Adaptador(Activity context) {
			super(context, R.layout.listitem_titular, products);
			this.context = context;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = context.getLayoutInflater();
			View item = inflater.inflate(R.layout.listitem_titular, null);

			TextView lblTitulo = (TextView) item.findViewById(R.id.LblTitulo);
			TextView lblSubtitulo = (TextView) item
					.findViewById(R.id.LblSubTitulo);

			lblTitulo.setText(products.get(position).getName() + " - $"
					+ products.get(position).getPrice());
			lblSubtitulo.setText(products.get(position).getDescription());

			return (item);
		}
	}

}
