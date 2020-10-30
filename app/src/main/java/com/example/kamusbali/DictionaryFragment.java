package com.example.kamusbali;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.kamusbali.Config.Config;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DictionaryFragment extends Fragment {

    private FragmentListener listener;
    ArrayAdapter<String> adapter;
    ListView dicList;
    private ArrayList<String> mSource = new ArrayList<String>();

    public DictionaryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dictionary, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dicList = view.findViewById(R.id.dictionaryList);
        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1,mSource);
        dicList.setAdapter(adapter);
        dicList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String name = (String) parent.getItemAtPosition(position);
                if (listener!=null)
                    System.out.println("NAMA ITEM KLIK "+name);
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(Config.SHARED_PREF_item, name);
                    editor.commit();

                listener.onItemClick(mSource.get(position));
            }
        });
    }

    public void resetDataSource(ArrayList<String> source){
        mSource = source;
        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1,mSource);
        dicList.setAdapter(adapter);
    }

    public void filterValue(String value){
        adapter.notifyDataSetChanged();
        adapter.getFilter().filter(value);

        System.out.println("DATA INI "+value);
//        int size = adapter.getCount();
//        for (int i=0; i<size; i++){
//            if(adapter.getItem(i).startsWith(value)){
//                dicList.setSelection(i);
//                break;
//            }
//        }
    }

    public void setOnFragmentListener(FragmentListener listener){
        this.listener = listener;
    }
}