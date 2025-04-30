package com.example.drugs_interactions_checker1;

import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class VerifyInteractionActivity extends AppCompatActivity {

    // Declare Views
    EditText drug1Input;
    EditText drug2Input;
    Button checkButton;
    TextView resultText;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_interaction);

        // Initialize Views
        drug1Input = findViewById(R.id.drug1Input);
        drug2Input = findViewById(R.id.drug2Input);
        checkButton = findViewById(R.id.checkButton);
        resultText = findViewById(R.id.resultText);

        // Initialize the DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Inside the onCreate method:
        try {
            databaseHelper.openDatabase(); // Try opening the database first.
        } catch (SQLException e) {
            // If database doesn't exist, copy it and then open it
            databaseHelper.copyDatabase();
            databaseHelper.openDatabase();
        }

        // Check interaction when checkButton is clicked
        checkButton.setOnClickListener(v -> {
            String drug1 = drug1Input.getText().toString().trim();
            String drug2 = drug2Input.getText().toString().trim();

            // Check if both drugs are entered
            if (drug1.isEmpty() || drug2.isEmpty()) {
                Toast.makeText(this, "Please enter both drug names!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Check interaction in the database
            try {
                // Get interaction details from database
                String interaction = databaseHelper.getInteraction(drug1, drug2);

                if (interaction != null) {
                    resultText.setText("Interaction: " + interaction); // Display interaction
                } else {
                    resultText.setText("No interaction found in the database."); // No interaction
                }
            } catch (SQLException e) {
                resultText.setText("Error checking interaction.");
                e.printStackTrace();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Ensure the database is properly closed when the activity is destroyed
        databaseHelper.closeDatabase();
    }
}
