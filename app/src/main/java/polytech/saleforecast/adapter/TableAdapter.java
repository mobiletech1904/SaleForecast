package polytech.saleforecast.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import polytech.saleforecast.R;
import polytech.saleforecast.model.TableModel;

/*
 * Катомный адаптер для отображения данных в 3 колонки.
 * @Created by Тёма on 15.06.2017.
 * @version 1.0
 */
public class TableAdapter extends BaseAdapter {
    public ArrayList<TableModel> tableItemList;
    private Activity activity;

    public TableAdapter(Activity activity, ArrayList<TableModel> tableItemList) {
        super();
        this.activity = activity;
        this.tableItemList = tableItemList;
    }

    @Override
    public int getCount() {
        return tableItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return tableItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        TextView timed;
        TextView incoming;
        TextView purchases;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater = activity.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layout_table_content, null);
            holder = new ViewHolder();

            holder.timed = (TextView) convertView.findViewById(R.id.timed);
            holder.incoming = (TextView) convertView.findViewById(R.id.incoming);
            holder.purchases = (TextView) convertView.findViewById(R.id.purchases);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        TableModel item = tableItemList.get(position);
        holder.timed.setText(item.getTimed().toString());
        holder.incoming.setText(item.getIncoming().toString());
        holder.purchases.setText(item.getPurchases().toString());

        return convertView;
    }
}
