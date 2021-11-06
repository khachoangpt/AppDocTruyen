package com.example.doctruyenapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.doctruyenapp.R;
import com.example.doctruyenapp.model.Account;

import java.util.List;

public class AdapterInformation extends BaseAdapter {
    private Context context;
    private int layout;

    private List<Account> accountList;

    public AdapterInformation(Context context, int layout, List<Account> accountList) {
        this.context = context;
        this.layout = layout;
        this.accountList = accountList;
    }

    @Override
    public int getCount() {
        return accountList.size();
    }

    @Override
    public Object getItem(int i) {
        return accountList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(layout, null);
        TextView txtAccountName = (TextView) convertView.findViewById(R.id.text_fullname);
        Account account = accountList.get(i);
        txtAccountName.setText(account.username);
        return convertView;
    }
}
