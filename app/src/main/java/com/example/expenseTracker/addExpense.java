package com.example.expenseTracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

public class addExpense extends AppCompatActivity {

    RelativeLayout rLayoutExpense;
    EditText edtAmount, edtCategory, edtDesc;
    Button btnAdd;

    public void init(){
        rLayoutExpense = findViewById(R.id.rLayoutExpense);
        edtAmount = findViewById(R.id.edtAmount);
        edtCategory = findViewById(R.id.edtCategory);
        edtDesc = findViewById(R.id.edtDesc);
        btnAdd = findViewById(R.id.btnAdd);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_expense);

        init();

        Intent toHome = new Intent();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String amountText = edtAmount.getText().toString();
                String category = edtCategory.getText().toString();
                String desc = edtDesc.getText().toString();

                if (amountText.isEmpty() || category.isEmpty()){
                    if (amountText.isEmpty()) edtAmount.setError("Please Enter Amount");
                    if (category.isEmpty()) edtCategory.setError("Please Enter Category");
                    return;
                }

                if (!category.matches("[a-zA-Z ]+")){
                    edtCategory.setError("Invalid Category Name");
                    return;
                }

                category = category.trim();

                if (category.isEmpty()){
                    edtCategory.requestFocus();
                    edtCategory.setError("Category name should not be Whitespace");
                    return;
                }

                double amount;

                try {
                    amount = Double.parseDouble(amountText);
                } catch (NumberFormatException e) {
                    edtAmount.setError("Invalid Amount");
                    return;
                }

                toHome.putExtra("amount", amount);
                toHome.putExtra("category", category);
                toHome.putExtra("desc", desc);

                setResult(RESULT_OK, toHome);
                finish();
            }
        });
    }
}