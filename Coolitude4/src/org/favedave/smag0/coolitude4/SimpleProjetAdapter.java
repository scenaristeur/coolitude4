package org.favedave.smag0.coolitude4;

import java.util.List;

import org.favedave.smag0.coolitude4.projet.Projet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class SimpleProjetAdapter extends ArrayAdapter<Projet> {

	private List<Projet> itemList;
	private Context context;

	public SimpleProjetAdapter(List<Projet> itemList, Context ctx) {
		super(ctx, android.R.layout.simple_list_item_1, itemList);
		this.itemList = itemList;
		this.context = ctx;
	}

	public int getCount() {
		if (itemList != null)
			return itemList.size();
		return 0;
	}

	public Projet getItem(int position) {
		if (itemList != null)
			return itemList.get(position);
		return null;
	}

	public long getItemId(int position) {
		if (itemList != null)
			return itemList.get(position).hashCode();
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View v = convertView;
		if (v == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.list_item, null);
		}
		/*
		 * Projet p = itemList.get(position); TextView text = (TextView)
		 * v.findViewById(R.id.name); text.setText(p.getName());
		 * 
		 * TextView text1 = (TextView) v.findViewById(R.id.surname);
		 * text1.setText(p.getSurname());
		 * 
		 * TextView text2 = (TextView) v.findViewById(R.id.email);
		 * text2.setText(p.getEmail());
		 * 
		 * TextView text3 = (TextView) v.findViewById(R.id.phone);
		 * text3.setText(p.getPhoneNum());
		 */
		return v;

	}

	public List<Projet> getItemList() {
		return itemList;
	}

	public void setItemList(List<Projet> itemList) {
		this.itemList = itemList;
	}

}
