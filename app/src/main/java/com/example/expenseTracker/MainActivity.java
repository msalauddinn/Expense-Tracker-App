package com.example.expenseTracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView txtAppName;
    private ActivityResultLauncher<Intent> addExpenseLauncher;
    RelativeLayout rLayoutMain;
    LinearLayout llExpenseContainer;
    Button btnAdd, btnReset;

    public void init(){
        txtAppName = findViewById(R.id.txtAppName);
        rLayoutMain = findViewById(R.id.rLayoutMain);
        btnAdd = findViewById(R.id.btnAdd);
        llExpenseContainer = findViewById(R.id.llExpenseContainer);
        btnReset = findViewById(R.id.btnReset);
    }

    private void addExpenseView(double amount, String category, String desc){
        TextView expenseDetails = new TextView(this);

        String finalView;

        String formattedAmount = String.format(Locale.US,"%.2f", amount);

        finalView = getString(R.string.amount) + " : " + formattedAmount + "\n" +
                    getString(R.string.category) + " : " + category + "\n" +
                    desc;

        expenseDetails.setText(finalView);
        expenseDetails.setPadding(20, 20, 20, 20);
        expenseDetails.setTextSize(20);
        expenseDetails.setBackgroundColor(getColor(R.color.lightCyan));
        expenseDetails.setBackground(AppCompatResources.getDrawable(MainActivity.this, R.drawable.round_box_cyan));
        expenseDetails.setTextColor(getColor(R.color.black));

        LinearLayout.LayoutParams marginSet = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );

        marginSet.setMargins(0, 0, 0, 30);

        expenseDetails.setLayoutParams(marginSet);

        llExpenseContainer.addView(expenseDetails);
    }

    private void addExpenseView(double amount, String category){
        TextView expenseDetails = new TextView(this);

        String finalView;

        finalView = getString(R.string.amount) + " : " + amount + "\n" +
                getString(R.string.category) + " : " + category;

        expenseDetails.setText(finalView);
        expenseDetails.setPadding(20, 20, 20, 20);
        expenseDetails.setTextSize(23);
        expenseDetails.setBackgroundColor(getColor(R.color.lightCyan));
        expenseDetails.setBackground(AppCompatResources.getDrawable(MainActivity.this, R.drawable.round_box_cyan));
        expenseDetails.setTextColor(getColor(R.color.black));

        LinearLayout.LayoutParams marginSet = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );

        marginSet.setMargins(0, 0, 0, 30);

        expenseDetails.setLayoutParams(marginSet);

        llExpenseContainer.addView(expenseDetails);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        addExpenseLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Intent data = result.getData();

                        double amount = data.getDoubleExtra("amount", 0);
                        String category = data.getStringExtra("category");
                        String desc = data.getStringExtra("desc");

                        if (category != null && !category.isEmpty()) {
                            if (desc == null || desc.isEmpty()) {
                                addExpenseView(amount, category);
                            } else {
                                addExpenseView(amount, category, desc);
                            }
                        }
                    }
                }
        );


        Animation transition_x = AnimationUtils.loadAnimation(MainActivity.this, R.anim.transition_x);
        rLayoutMain.startAnimation(transition_x);

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llExpenseContainer.removeAllViews();
                Toast.makeText(MainActivity.this, "All expenses cleared", Toast.LENGTH_SHORT).show();
            }
        });

        btnAdd.setOnClickListener(v -> {
            Intent toExpense = new Intent(MainActivity.this, addExpense.class);
            addExpenseLauncher.launch(toExpense);
        });

    }
}