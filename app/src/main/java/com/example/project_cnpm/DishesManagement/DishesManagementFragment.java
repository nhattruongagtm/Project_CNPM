package com.example.project_cnpm.DishesManagement;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_cnpm.Admin.AdminPage;
import com.example.project_cnpm.R;

import java.util.ArrayList;

public class DishesManagementFragment extends Fragment {
    ImageView btnMenuAdmin;
    RecyclerView recyclerDishesManegement;

    ImageView btnHome;
    TextView txtHome;
    Button btnAdd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dish_management, container, false);

        recyclerDishesManegement = view.findViewById(R.id.dish_management_recycler);

        createDishesManagement();

        btnMenuAdmin = view.findViewById(R.id.btnMenuAdmin);

        ///
        btnAdd = view.findViewById(R.id.btnAdd);
        ///

        btnHome = view.findViewById(R.id.imgHome);
        txtHome = view.findViewById(R.id.txtHome);

        ///
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Fragment f = new AddDishFragment();
//                loadFragment(f);
                startActivity(new Intent(getActivity(), AddDishActivity.class));
            }
        });
        ///

        btnMenuAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.dialog_admin);

                setPosition(dialog,120);

                dialog.show();
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AdminPage.class));
            }
        });

        txtHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AdminPage.class));
            }
        });
        return view;
    }
        ////
    private void loadFragment(Fragment fragment) {
        // replace fragment
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.dish_management_fragment,fragment).commit();
        }
        ////

    public void setPosition(Dialog dialog,int yValue) {
        Window window = dialog.getWindow();
        WindowManager.LayoutParams param = window.getAttributes();
        param.gravity = Gravity.TOP | Gravity.LEFT;
        param.y = yValue;
        window.setAttributes(param);
        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }
    public void createDishesManagement(){

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerDishesManegement.setLayoutManager(layoutManager);

        DishesAdapter adapter = new DishesAdapter(getActivity(),getDishesMangement());
        recyclerDishesManegement.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }
    public ArrayList<DishManagement> getDishesMangement(){
        ArrayList<DishManagement> list = new ArrayList<>();
        list.add(new DishManagement("001","Pizza hải sản cao cấp",159000,0,"Pizza",true, R.drawable.pizza_home_page_item));
        list.add(new DishManagement("001","Pizza hải sản cao cấp",159000,0,"Pizza",true, R.drawable.pizza_home_page_item));
        list.add(new DishManagement("001","Pizza hải sản cao cấp",159000,0,"Pizza",true, R.drawable.pizza_home_page_item));
        list.add(new DishManagement("001","Pizza hải sản cao cấp",159000,0,"Pizza",true, R.drawable.pizza_home_page_item));
        list.add(new DishManagement("001","Pizza hải sản cao cấp",159000,0,"Pizza",true, R.drawable.pizza_home_page_item));
        list.add(new DishManagement("001","Pizza hải sản cao cấp",159000,0,"Pizza",true, R.drawable.pizza_home_page_item));
        list.add(new DishManagement("001","Pizza hải sản cao cấp",159000,0,"Pizza",true, R.drawable.pizza_home_page_item));
        list.add(new DishManagement("001","Pizza hải sản cao cấp",159000,0,"Pizza",true, R.drawable.pizza_home_page_item));
        return list;
    }

}
