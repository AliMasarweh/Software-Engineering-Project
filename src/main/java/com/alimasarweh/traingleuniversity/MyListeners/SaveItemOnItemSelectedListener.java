package com.alimasarweh.traingleuniversity.MyListeners;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

public class SaveItemOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

    private String item;

    public  SaveItemOnItemSelectedListener(){
        super();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        item = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void setToFirst(ArrayAdapter<String> adapterView){
        item = adapterView.getItem(0);
    }

    public String getSelectedItem() {
        return item;
    }
}
