package com.example.calculator;

import static org.mozilla.javascript.Context.*;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView result, solution;
    MaterialButton buttonC, buttonBracketOpen, buttonBracketClose;
    MaterialButton buttonDivide, buttonMultiply, buttonPlus, buttonMinus, buttonEquals;
    MaterialButton button0, button1, button2, button3, button4, button5, button6, button7, button8, button9;
    MaterialButton buttonAC, buttonDot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        result = findViewById(R.id.result_text_view);
        solution = findViewById(R.id.solution_text_view);

        assignID(buttonC,R.id.button_c);
        assignID(buttonBracketOpen,R.id.button_open_bracket);
        assignID(buttonBracketClose,R.id.button_close_bracket);
        assignID(buttonDivide,R.id.button_divide);
        assignID(buttonMultiply,R.id.button_multiply);
        assignID(buttonPlus,R.id.button_add);
        assignID(buttonMinus,R.id.button_subtract);
        assignID(buttonEquals,R.id.button_equals);
        assignID(button0,R.id.button_0);
        assignID(button1,R.id.button_1);
        assignID(button2,R.id.button_2);
        assignID(button3,R.id.button_3);
        assignID(button4,R.id.button_4);
        assignID(button5,R.id.button_5);
        assignID(button6,R.id.button_6);
        assignID(button7,R.id.button_7);
        assignID(button8,R.id.button_8);
        assignID(button9,R.id.button_9);
        assignID(buttonAC,R.id.button_AC);
        assignID(buttonDot,R.id.button_dot);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    void assignID(MaterialButton btn, int id)
    {
        btn = findViewById(id);
        btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

       MaterialButton button =(MaterialButton) view;
       String buttonText = button.getText().toString();
       String dataToCalculate = solution.getText().toString();

       if(buttonText.equals("AC"))
       {
           solution.setText("");
           result.setText("0");
           return;
       }

       if(buttonText.equals("="))
       {
           solution.setText(result.getText());
           return;
       }

       if(buttonText.equals("C"))
       {
           dataToCalculate = dataToCalculate.substring(0, dataToCalculate.length()-1);
       }else{
           dataToCalculate = dataToCalculate+buttonText;
       }

       solution.setText(dataToCalculate);

       String finalResult = getResult(dataToCalculate);

       if(!finalResult.equals("Err"))
       {
           result.setText(finalResult);
       }

    }

    String getResult(String data){
        try{
            Context context = enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initSafeStandardObjects();
            String finalResult = context.evaluateString(scriptable,data,"JavaScript", 1, null). toString();
            if(finalResult.endsWith(".0"))
            {
                finalResult = finalResult.replace(".0","");
            }
            return finalResult;

        }catch(Exception e){
            return "Err";
        }
    }
}