package com.example.prosk.biiim;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;

import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class activity_caisse extends Activity {

    private EditText editText_TR;
    private EditText editText_Cb;
    private EditText editText_Depenses;
    private TextView textView_result;
    private EditText editText_CA;

    //*************************
    //Start of "Main"
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_caisse);

        // Init textviews / edittexts
        init_textView();
        init_editTexts();

        // Listeners on EditTexts
        expression_listener(editText_Cb);
        expression_listener(editText_TR);
        expression_listener(editText_Depenses);

       onDoneListener(editText_Cb, editText_Depenses, editText_TR);

    }

    //*************************
    //***End of "Main"


    //**********************************************
    // Maths Function
    public void calculate(EditText editTxt) {

        String txt = editTxt.getText().toString();
        if (txt.isEmpty()) {
            txt = "0";
        }
        char[] strtochar = txt.toCharArray();
        int i = strtochar.length;
        char operator = strtochar[i - 1];
        if (operator == '*' || operator == '+') {
            Toast.makeText(getApplicationContext(), "Invalid statement",
                    Toast.LENGTH_SHORT).show();
        }
        else {
        Expression expression = new ExpressionBuilder(txt).build();
            try {
                double result = expression.evaluate();
                editTxt.setText(Double.toString(result));
                editTxt.setSelection(editTxt.getText().length());

            } catch (ArithmeticException ex) {
                editTxt.setText("0");
            }
        }

    }

    //*********************************************************
    //
    public void calculate_TR(View view){
        calculate(editText_TR);
    }
    public void calculate_Cb(View view){
        calculate(editText_Cb);
    }
    public void calculate_Depenses(View view){
        calculate(editText_Depenses);
    }

    //**********************************************************************************************
    //  Action on imeOption:Done
    public void onDoneListener(final EditText Cb, final EditText Depenses, final EditText TR){
        editText_TR.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if(actionId == EditorInfo.IME_ACTION_NEXT  && TR.getId() == R.id.editText_TR){
                    calculate_TR(editText_TR);
                }
                return false;
            }
        });

        editText_Cb.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_NEXT && Cb.getId() == R.id.editText_Cb){
                    calculate_Cb(editText_Cb);
                }
                return false;
            }
        });

        editText_Depenses.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_NEXT && Depenses.getId() == R.id.editTxt_Depenses){
                    calculate_Depenses(editText_Depenses);
                }
                return false;
            }
        });
    }

    //**********************************************
    // Init Functions buttons/ textViews / EditTexts
    public void init_textView(){
        textView_result = (TextView) findViewById(R.id.textView_result);
    }

    public void init_editTexts(){
        editText_Cb = (EditText) findViewById(R.id.editText_Cb);
        editText_TR = (EditText) findViewById(R.id.editText_TR);
        editText_Depenses = (EditText) findViewById(R.id.editTxt_Depenses);
        editText_CA = (EditText) findViewById(R.id.editTxt_CA);
    }

    //**********************************
    // Expression Listener

    public void expression_listener(final EditText editText){
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (count > 0) {
                    String str = editText.getText().toString();
                    if (str.isEmpty()) {
                    } else {
                        char lastEntry = s.charAt(start);
                        char[] chars = str.toCharArray();
                        if(chars[0] == '*'|| chars[0] == '.' ||chars[0] =='+'){
                            editText.setText("");
                        }
                        int i = chars.length;
                        if (i > 2) {
                            char previousEntry = chars[i - 2];
                            if (previousEntry == lastEntry || previousEntry+lastEntry =='.'+'*'
                                    || previousEntry+lastEntry == '*'+'.'
                                    || previousEntry+lastEntry == '+'+'.'
                                    || previousEntry+lastEntry == '.'+'+'
                                    || previousEntry+lastEntry == '*'+'+'
                                    || previousEntry+lastEntry == '+'+'*') {
                                if (previousEntry == '*' || previousEntry == '+' || previousEntry =='.') {
                                    char[] charsfinal = new char[i - 1];
                                    str.getChars(0, i - 1, charsfinal, 0);
                                    String stringFinal;
                                    stringFinal = String.valueOf(charsfinal);
                                    editText.setText(stringFinal);
                                    editText.setSelection(editText.getText().length());

                                }
                            }
                            //textView_result.setText(Character.toString(c));
                        }
                    }
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    public void sendMail(View v){

        String email[] = {"soumsoumaesthetic@gmail.com"};
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, email);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Bilan caisse");
        intent.putExtra(Intent.EXTRA_TEXT, "Chiffre d'affaire :" + editText_CA.getText().toString()+"\n"
        + "Tickets restaurant :"+ editText_TR.getText().toString()+"\n"
        + "Cartes baincaires :" + editText_Cb.getText().toString() +"\n"
        + "Depenses :" + editText_Depenses.getText().toString() +"\n"
        + textView_result.getText().toString());
        startActivity(Intent.createChooser(intent,"Send mail..."));

    }

    public void calculate_Total(View view) {
        String TR = editText_TR.getText().toString();
        String CB = editText_Cb.getText().toString();
        String Depenses = editText_Depenses.getText().toString();
        String CA = editText_CA.getText().toString();
        double result;

        if (TR.isEmpty()) { TR = "0"; }
        if (CB.isEmpty()) { CB = "0"; }
        if (Depenses.isEmpty()) { Depenses = "0"; }
        if (CA.isEmpty()) { CA = "0"; }

        result = Double.parseDouble(CA) - (Double.parseDouble(TR) + Double.parseDouble(CB) + Double.parseDouble(Depenses));
        textView_result.setText("A rendre : " + Double.toString(result));
    }
}


