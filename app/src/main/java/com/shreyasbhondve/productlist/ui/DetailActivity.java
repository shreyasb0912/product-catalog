package com.shreyasbhondve.productlist.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.shreyasbhondve.productlist.MyApplication;
import com.shreyasbhondve.productlist.R;
import com.shreyasbhondve.productlist.db.DataManager;
import com.shreyasbhondve.productlist.di.component.ApplicationComponent;
import com.shreyasbhondve.productlist.di.component.DaggerDetailActivityComponent;
import com.shreyasbhondve.productlist.di.component.DetailActivityComponent;
import com.shreyasbhondve.productlist.di.qualifier.ApplicationContext;
import com.shreyasbhondve.productlist.pojo.ProductCatalog;
import com.shreyasbhondve.productlist.retrofit.APIInterface;


import org.w3c.dom.Text;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    DetailActivityComponent detailActivityComponent;

    @Inject
    public APIInterface apiInterface;

    @Inject
    @ApplicationContext
    public Context mContext;

    TextView textView;
    TextView selectSizeTextView;
    TextView selectColorTextView;
    TextView priceTextView;
    Button addToCartButton;

    List<ProductCatalog.Category.Product.Variants> variantsList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        textView = findViewById(R.id.textView);
        selectSizeTextView = findViewById(R.id.selectSize);
        selectColorTextView = findViewById(R.id.selectColor);
        priceTextView = findViewById(R.id.price);
        addToCartButton = findViewById(R.id.addToCart);

        textView.setText("Sneakers");


        selectSizeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //openContextMenu(v);
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(DetailActivity.this, textView);
                //Inflating the Popup using xml file
                //popup.getMenuInflater().inflate(R.menu.filter_menu, popup.getMenu());
                popup.getMenu().add(1, 1, 1, "L");
                popup.getMenu().add(1, 2, 2, "XL");
                popup.getMenu().add(1, 3, 3, "XXL");

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        //Toast.makeText(MainActivity.this,"You Clicked : " + item.getTitle(),Toast.LENGTH_SHORT).show();
                        selectSizeTextView.setText(item.getTitle());
                        return true;
                    }
                });

                popup.show();//showing popup menu
            }
        });

        selectColorTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //openContextMenu(v);
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(DetailActivity.this, textView);
                //Inflating the Popup using xml file
                //popup.getMenuInflater().inflate(R.menu.filter_menu, popup.getMenu());
                popup.getMenu().add(1, 1, 1, "Blue");
                popup.getMenu().add(1, 2, 2, "Red");
                popup.getMenu().add(1, 3, 3, "Green");

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        //Toast.makeText(MainActivity.this,"You Clicked : " + item.getTitle(),Toast.LENGTH_SHORT).show();
                        selectColorTextView.setText(item.getTitle());
                        priceTextView.setText("Price " + getString(R.string.Rs) + " " + 3000);
                        return true;
                    }
                });

                popup.show();//showing popup menu
            }
        });

        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


}
