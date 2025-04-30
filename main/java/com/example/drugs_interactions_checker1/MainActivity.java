package com.example.drugs_interactions_checker1;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.widget.Toast;

import com.journeyapps.barcodescanner.ScanOptions;
import com.journeyapps.barcodescanner.ScanContract;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
String scannedDrug = null;
    private final ActivityResultLauncher<ScanOptions> qrLauncher = registerForActivityResult(
            new ScanContract(), result -> {
                if (result.getContents() != null) {
                    String scannedData = result.getContents();

                    // Check if the scanned data is a URL
                    if (scannedData.startsWith("http://") || scannedData.startsWith("https://")) {
                        // Open the scanned URL
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(scannedData));
                        startActivity(browserIntent);
                    } else {
                    }
                }
            }
    );


    public void onVerifyIntbtnclick(View view) {
        Intent intent = new Intent(MainActivity.this, VerifyInteractionActivity.class);
        startActivity(intent);
    }

    public void onScanQRcodebtn(View view) {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Scan the drug QR code");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setBarcodeImageEnabled(true);
        qrLauncher.launch(options);
    }

    public void ondrugreminderbtn(View view) {
        Intent intent = new Intent(MainActivity.this, Drugremainder.class);
        startActivity(intent);
    }
}