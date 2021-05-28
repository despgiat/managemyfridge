package com.example.managemyfridge;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

public class MyFridge extends Fragment {

    private Fridge fridge; //We need to access all of the fridge items and we need to know the current Date
    private static final String FRIDGE = "fridge";
    ImageButton addItemButton;
    TextView warning;
    HomeFragment.HomeFragmentListener activityCallback; //For communication with the activity
    RecyclerView.Adapter adapterFridgeItems;


    public static MyFridge newInstance(Fridge fridge) {
        MyFridge fragment = new MyFridge();

        Bundle args = new Bundle();
        args.putSerializable(FRIDGE, fridge);
        //args.putString(DATE, currentDate);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);

        try {
            activityCallback = (HomeFragment.HomeFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement HomeFragmentListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            fridge = (Fridge) getArguments().getSerializable(FRIDGE);
            //currentDate = getArguments().getString(DATE);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.my_fridge_fragment, container, false);
        activityCallback.UpdateData(fridge);

        addItemButton = view.findViewById(R.id.addItemButton);
        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainScreen) getActivity()).addNewItem();
            }
        });

        warning = view.findViewById(R.id.noProductsTextView);

        if(fridge.getFridgeItems().size() == 0)
        {
            warning.setVisibility(View.VISIBLE);
        }
        else
        {
            warning.setVisibility(View.GONE);
        }

        RecyclerView productsRecyclerView = view.findViewById(R.id.fridgeProductsRecyclerView);
        LinearLayoutManager linearLayoutManagerToday = new LinearLayoutManager(this.getContext());

        productsRecyclerView.setLayoutManager(linearLayoutManagerToday);
        adapterFridgeItems = new EditableProductRecyclerAdapter(fridge.getFridgeItems(), getResources().getColor((R.color.teal_200)));
        productsRecyclerView.setAdapter(adapterFridgeItems);

        return  view;
    }

    //public void openProduct(int id)
    //public void editProduct(int id)
    //public void deleteProduct(int id)

}