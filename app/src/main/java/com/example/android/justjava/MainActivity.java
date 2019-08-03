package com.example.android.justjava;



import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 0;
    int ChocolatePrice = 0;
    int WhippedCreamPrice = 0;
    String GlobalPriceMessge;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    /**
     * this methode is to calculate the Price
     */
    private int calculatePrice(boolean AddWhippedCream, boolean AddChocolate){
        int basePrice = 30;

        if(AddChocolate){
            basePrice += 10;
        }
        if(AddWhippedCream){
            basePrice += 5;
        }

        return quantity * basePrice;
    }


    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView priceTextView = findViewById(R.id.price_text_view);
        priceTextView.setText(message);
    }


    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        String inputName;
        CheckBox CheckedWhippedCream =  findViewById(R.id.WhippedCream_checkbox);
        Boolean WhippedCreamState = CheckedWhippedCream.isChecked();
        Log.i("mainActivity", " has whippedCream ? : " + WhippedCreamState);

        CheckBox CheckChocolate =  findViewById(R.id.Chocolatecheckbox);
        Boolean  ChocolateState = CheckChocolate.isChecked();
        Log.i("mainActivity", " has Chocolate ? : " + ChocolateState);

        EditText EnteredText =  findViewById(R.id.EditText_UserName);
        inputName = EnteredText.getText().toString();
        Log.i("mainActivity", " user name : " + inputName);

        int price = calculatePrice(WhippedCreamState, ChocolateState);

        calculatePrice(WhippedCreamState, ChocolateState);
        createOrderSummary(price, WhippedCreamState, ChocolateState, inputName);

        composeEmail(inputName);

    }

    public void composeEmail(String name){
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this

        intent.putExtra(Intent.EXTRA_SUBJECT, "JustJava App order for : " + name);
        intent.putExtra(Intent.EXTRA_EMAIL, "ClientEmail@gmail.com");
        intent.putExtra(Intent.EXTRA_TEXT, GlobalPriceMessge);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * this method is to summaries the client order
     */
    private void createOrderSummary(int price, boolean WhippedCreamChecked, boolean ChocolateChecked, String UserName){
        String priceMessage = " ";
        String ForCheckedWhippedCream;
        String ForCheckedChocolate;

       if (WhippedCreamChecked){
           ForCheckedWhippedCream = "With Cream. (+5da)" ;
           WhippedCreamPrice = 5*quantity;
        }
        else {
           ForCheckedWhippedCream = "Without Cream. ";
           WhippedCreamPrice = 0;
        }

        if (ChocolateChecked){
            ForCheckedChocolate = "With Chocolate. (+10da)";
            ChocolatePrice = 10*quantity;
        }
        else {
            ForCheckedChocolate = "Without Chocolate.";
            ChocolatePrice = 0;
        }

        if ( quantity == 0) {
            priceMessage = "Client name : " + UserName;
             priceMessage += "\n" + ForCheckedChocolate + "\n";
            priceMessage+= ForCheckedWhippedCream + "\n" + price + " Da" + "\n No order yet!";
            displayMessage(priceMessage);
        }
        else if (quantity >= 2 && quantity < 5) {
            priceMessage = "Client name : " + UserName;
             priceMessage += "\n" + ForCheckedChocolate + "\n" + ForCheckedWhippedCream + "\n";
            priceMessage += price + " Da" + "\n and you are " + (5 - quantity) + " cups away from 1 free cup of coffee";
            displayMessage(priceMessage);
        }
        else if (quantity == 1){
            priceMessage = "Client name : " + UserName;
             priceMessage += "\n" + ForCheckedChocolate + "\n" + ForCheckedWhippedCream;
            priceMessage += "\n" + price + " Da" + "\n Thank you!";
            displayMessage(priceMessage);
        }

        else if (quantity == 5){
            priceMessage = "Client name : " + UserName;
            priceMessage += "\n" + ForCheckedChocolate + "\n" + ForCheckedWhippedCream;
            priceMessage += "\n" + price + " Da" + "\n and you get 1 free cup of coffee on the house";
            displayMessage(priceMessage);
        }
        else if (quantity > 5){
            priceMessage = "Client name : " + UserName;
           priceMessage += "\n" + ForCheckedChocolate + "\n" + ForCheckedWhippedCream;
            priceMessage += "\n" + price + " Da" + " that's a lot of coffee mate!";
            displayMessage(priceMessage);
        }


        GlobalPriceMessge = priceMessage;
    }




    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText(" " + number);
    }


    /**
     * this method is for incrementing number of orders
     */
    public void increment(View view) {
        quantity++;
        display(quantity);

    }

    /**
     * this method is for decrementing the number of orders
     */

    public void decrement(View view) {

        if (quantity == 0) {
            quantity = 0;
            display(quantity);
        }
        else {
            quantity--;
            display(quantity);
        }
    }


}