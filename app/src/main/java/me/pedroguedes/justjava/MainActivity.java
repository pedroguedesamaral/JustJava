package me.pedroguedes.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;


/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends ActionBarActivity {

    int cupsQuantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantities(int numberOfCoffees) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffees);
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (cupsQuantity == 100) {
            Toast.makeText(MainActivity.this, "You cannot have more than 100 coffees", Toast.LENGTH_SHORT).show();
            return;
        }
        cupsQuantity = cupsQuantity + 1;
        displayQuantities(cupsQuantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (cupsQuantity == 1) {
            Toast.makeText(MainActivity.this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        cupsQuantity = cupsQuantity - 1;
        displayQuantities(cupsQuantity);
    }

    /**
     * Calculates the price of the order.
     *
     * @return total price
     */
    private int calculatePrice(boolean hasWippedCream, boolean hasChocolate) {
        int basePrice = 5;

        if (hasWippedCream) {
            basePrice = basePrice + 1;
        }

        if (hasChocolate) {
            basePrice = basePrice + 2;
        }

        int price = cupsQuantity * basePrice;
        return price;
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText clientName = (EditText) findViewById(R.id.name_edit_text);
        String name = clientName.getText().toString();


        CheckBox wippedCreamCheckBox = (CheckBox) findViewById(R.id.wipperd_cream_checkbox);
        boolean hasWippedCream = wippedCreamCheckBox.isChecked();

        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_cream_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        int price = calculatePrice(hasWippedCream,hasChocolate);
        String priceMessage = createOrderSummary(price, hasWippedCream, hasChocolate, name);



        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:mobile.gds@gmail.com")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Order Summary for " + name);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

        displayMessage(priceMessage);


    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

    /**
     * This method create the order summary
     *
     * @param price          of the order
     * @param addWippedCream if the wipped cream is checked
     * @param addChocolate   if the chocolate is checked
     * @param name           name of the client
     * @return order summary
     */
    private String createOrderSummary(int price, boolean addWippedCream, boolean addChocolate, String name) {
        String priceMessage = "Hello, " + name;
        priceMessage += "\nAdd Wipped Cream: " + convetBooleanToYesOrNo(addWippedCream);
        priceMessage += "\nAdd Chocolate: " + convetBooleanToYesOrNo(addChocolate);
        priceMessage += "\nCup Quantity: " + cupsQuantity;
        priceMessage += "\nTotal: $" + price;
        priceMessage += "\nThank You!";
        return priceMessage;

    }


    private String convetBooleanToYesOrNo(boolean yesOrNo) {
        if (yesOrNo) {
            return "YES";
        } else {
            return "NO";
        }
    }
}