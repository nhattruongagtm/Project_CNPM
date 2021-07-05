package com.example.project_cnpm.Admin.DishesManagement;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.project_cnpm.Admin.AdminPage;
import com.example.project_cnpm.Database.Database;
import com.example.project_cnpm.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class DishActivity extends AppCompatActivity {

    String urlGetData = "https://appfooddb.000webhostapp.com/getdata.php";
    ImageView btnMenuAdmin;
    ImageView btnHome;
    TextView txtHome;
    ListView lvDish;
    ArrayList<com.example.project_cnpm.Admin.DishesManagement.Dish> arrayDishes;
    DishAdapter adapter;
    Button btnAdd;
    HashMap<String,String> categories = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_dish_management);

        btnAdd = findViewById(R.id.btnAdd);
        btnMenuAdmin = findViewById(R.id.btnMenuAdmin);
        btnHome = findViewById(R.id.imgHome);
        txtHome = findViewById(R.id.txtHome);
        lvDish = findViewById(R.id.listViewDish);

        arrayDishes = new ArrayList<>();
        adapter = new DishAdapter(this, R.layout.show_one_dish, arrayDishes);
        lvDish.setAdapter(adapter);

      //  ReadJSON(urlGetData);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // lấy danh mục
                String url = "https://appfooddb.000webhostapp.com/getCategories.php";
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONArray array = new JSONArray(response);
                                    for (int i = 0; i< array.length();i++){

                                        JSONObject object = array.getJSONObject(i);
                                        String id = object.getString("idCategory");
                                        String name = object.getString("name");

                                        categories.put(id,name);

                                        //                       categories.put(id,name);
//                                spin.add(name);
                                    }
                                    Intent intent = new Intent(DishActivity.this,AddDishActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("categories",categories);
                                    intent.putExtra("categories",bundle);
                                    startActivity(intent);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // Toast.makeText(context, "Loi load danh muc!", Toast.LENGTH_SHORT).show();
                            }
                        });

                Database.getInstance(DishActivity.this).excuteQuery(stringRequest);

               // startActivity(new Intent(DishActivity.this, AddDishActivity.class));
            }
        });
        btnMenuAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getBaseContext());
                dialog.setContentView(R.layout.dialog_admin);

                setPosition(dialog,120);

                dialog.show();
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DishActivity.this, AdminPage.class));
            }
        });

        txtHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DishActivity.this, AdminPage.class));
            }
        });
    }
    public void setPosition(Dialog dialog,int yValue) {
        Window window = dialog.getWindow();
        WindowManager.LayoutParams param = window.getAttributes();
        param.gravity = Gravity.TOP | Gravity.LEFT;
        param.y = yValue;
        window.setAttributes(param);
        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }
//    private void ReadJSON(String url){
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//                        for (int i = 0; i < response.length(); i++) {
//                            try {
//                                JSONObject object = response.getJSONObject(i);
//                                arrayDishes.add(new Dish(
//                                        object.getString("ID"),
//                                        object.getString("TenMonAn"),
//                                        object.getString("Loai"),
//                                        object.getString("SizeS"),
//                                        object.getInt("GiaS"),
//                                        object.getString("SizeL"),
//                                        object.getInt("GiaL"),
//                                        object.getString("MoTa")
//                                ));
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                        adapter.notifyDataSetChanged();
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(DishActivity.this, "Lỗi!", Toast.LENGTH_SHORT).show();
//            }
//        }
//        );
//        requestQueue.add(jsonArrayRequest);
//    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.add_student, menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        if(item.getItemId() == R.id.menuAddStudent){
//            startActivity(new Intent(MainActivity.this, AddSinhVienActivity.class));
//        }
//        return super.onOptionsItemSelected(item);
//    }

}

