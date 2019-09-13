package com.simarro.calculadora;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {
	EditText CampoA,CampoB;
	TextView CampoResultado,result;
	String A,B;
	String cadena;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CampoResultado = (TextView)findViewById(R.id.textView4);
        
        result = (TextView)findViewById(R.id.textView5);
        CampoA = (EditText)findViewById(R.id.editText1);
        CampoB = (EditText)findViewById(R.id.editText2);
        final Button btn_suma = (Button) findViewById(R.id.button1);
        final Button btn_resta = (Button) findViewById(R.id.button2);
        final Button btn_multi = (Button) findViewById(R.id.button3);
        final Button btn_Divi = (Button) findViewById(R.id.button4);
       
		
   /*** SUMA **/     
        btn_suma.setOnClickListener(new View.OnClickListener() {
        	 public void onClick(View v) {
        		A=CampoA.getText().toString(); 	
        		B = CampoB.getText().toString();
        			 int A2=Integer.parseInt(A);
        			 int B2=Integer.parseInt(B);
        		int valor=A2+B2;
        		cadena = String.valueOf(valor);
        		result.setText(cadena);
        		 
        	 }
        	 
        });
   /** RESTA **/
        btn_resta.setOnClickListener(new View.OnClickListener() {
       	 public void onClick(View v) {
       	 A=CampoA.getText().toString(); 	
 			B = CampoB.getText().toString();
 			 int A2=Integer.parseInt(A);
 			 int B2=Integer.parseInt(B);
       		int valor=A2-B2;
       		cadena = String.valueOf(valor);
       		result.setText(cadena);
       		 
       	 }
       	 
       });
        
     /** MULTI ***/
        btn_multi.setOnClickListener(new View.OnClickListener() {
          	 public void onClick(View v) {
          	 A=CampoA.getText().toString(); 	
    			B = CampoB.getText().toString();
    			 int A2=Integer.parseInt(A);
    			 int B2=Integer.parseInt(B);
          		int valor=A2*B2;
          		cadena = String.valueOf(valor);
          		result.setText(cadena);
          		 
          	 }
          	 
          });
        
      /** Divi **/
        
        btn_Divi.setOnClickListener(new View.OnClickListener() {
         	 public void onClick(View v) {
         	 A=CampoA.getText().toString(); 	
   			B = CampoB.getText().toString();
   			 int A2=Integer.parseInt(A);
   			 int B2=Integer.parseInt(B);
         		int valor=A2%B2;
         		cadena = String.valueOf(valor);
         		result.setText(cadena);
         		 
         	 }
         	 
         });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
