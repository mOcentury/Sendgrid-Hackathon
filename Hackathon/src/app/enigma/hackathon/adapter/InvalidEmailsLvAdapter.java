package app.enigma.hackathon.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import app.enigma.hackathon.R;
import app.enigma.hackathon.object.InvalidEmail;

public class InvalidEmailsLvAdapter extends ArrayAdapter<InvalidEmail> {
	Context context;
	static int layoutResourceId = R.layout.lv_item_invalid_email;
	List<InvalidEmail> al;

	public InvalidEmailsLvAdapter(Context context, List<InvalidEmail> al) {
		super(context, layoutResourceId, al);
		this.context = context;
		this.al = al;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;

		if(row == null) {
			LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = li.inflate(layoutResourceId, null);
		}

		InvalidEmail ali = al.get(position);

		if(ali != null) {
			TextView tvEmail = (TextView) row.findViewById(R.id.tvEmail);
			TextView tvReason = (TextView) row.findViewById(R.id.tvReason);

			if (tvEmail != null) {
				tvEmail.setHint(ali.getEmail());
			}

			if (tvReason != null) {
				tvReason.setText(ali.getReason());
			}
		}

		return row;
	}
}
