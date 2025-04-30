package com.example.drugs_interactions_checker1;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "drugs.db";
    private static final int DATABASE_VERSION = 1;

    private final Context context;
    private SQLiteDatabase database;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        context.deleteDatabase(DATABASE_NAME);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE IF NOT EXISTS drug_interactions (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "drug1 TEXT, " +
                "drug2 TEXT, " +
                "interaction TEXT)";
        db.execSQL(createTable);
        insertSampleInteractions(db);
    }

    private void insertSampleInteractions(SQLiteDatabase db) {
        db.execSQL("INSERT INTO drug_interactions (drug1, drug2, interaction) VALUES ('Aspirin', 'Ibuprofen', 'Increased risk of gastrointestinal bleeding')");
        db.execSQL("INSERT INTO drug_interactions (drug1, drug2, interaction) VALUES ('Warfarin', 'Amoxicillin', 'May increase risk of bleeding')");
        db.execSQL("INSERT INTO drug_interactions (drug1, drug2, interaction) VALUES ('Metformin', 'Alcohol', 'Increased risk of lactic acidosis')");
        db.execSQL("INSERT INTO drug_interactions (drug1, drug2, interaction) VALUES ('Lisinopril', 'Potassium supplements', 'Hyperkalemia risk')");
        db.execSQL("INSERT INTO drug_interactions (drug1, drug2, interaction) VALUES ('Atorvastatin', 'Grapefruit', 'Increased statin concentration')");
        db.execSQL("INSERT INTO drug_interactions (drug1, drug2, interaction) VALUES ('Metformin', 'Cimetidine', 'Increased metformin plasma concentration; risk of lactic acidosis')");
        db.execSQL("INSERT INTO drug_interactions (drug1, drug2, interaction) VALUES ('Digoxin', 'Verapamil', 'Increased digoxin levels; monitor for toxicity')");
        db.execSQL("INSERT INTO drug_interactions (drug1, drug2, interaction) VALUES ('Theophylline', 'Ciprofloxacin', 'Increased theophylline levels; risk of toxicity')");
        db.execSQL("INSERT INTO drug_interactions (drug1, drug2, interaction) VALUES ('Lithium', 'NSAIDs', 'Increased lithium levels; monitor for toxicity')");
        db.execSQL("INSERT INTO drug_interactions (drug1, drug2, interaction) VALUES ('Phenytoin', 'Fluconazole', 'Increased phenytoin levels; monitor for toxicity')");
        db.execSQL("INSERT INTO drug_interactions (drug1, drug2, interaction) VALUES ('Sildenafil', 'Nitrates', 'Severe hypotension risk; contraindicated')");
        db.execSQL("INSERT INTO drug_interactions (drug1, drug2, interaction) VALUES ('Linezolid', 'SSRIs', 'Risk of serotonin syndrome; avoid combination')");
        db.execSQL("INSERT INTO drug_interactions (drug1, drug2, interaction) VALUES ('Carbamazepine', 'Erythromycin', 'Increased carbamazepine levels; risk of toxicity')");
        db.execSQL("INSERT INTO drug_interactions (drug1, drug2, interaction) VALUES ('Simvastatin', 'Clarithromycin', 'Risk of serious muscle damage (rhabdomyolysis)')");
        db.execSQL("INSERT INTO drug_interactions (drug1, drug2, interaction) VALUES ('Warfarin', 'Amiodarone', 'Increased risk of bleeding')");
        db.execSQL("INSERT INTO drug_interactions (drug1, drug2, interaction) VALUES ('Metformin', 'Cimetidine', 'Increased metformin levels; risk of lactic acidosis')");
        db.execSQL("INSERT INTO drug_interactions (drug1, drug2, interaction) VALUES ('Digoxin', 'Verapamil', 'Increased digoxin concentration')");
        db.execSQL("INSERT INTO drug_interactions (drug1, drug2, interaction) VALUES ('Theophylline', 'Ciprofloxacin', 'Risk of theophylline toxicity')");
        db.execSQL("INSERT INTO drug_interactions (drug1, drug2, interaction) VALUES ('Lithium', 'NSAIDs', 'Increased lithium levels')");
        db.execSQL("INSERT INTO drug_interactions (drug1, drug2, interaction) VALUES ('Phenytoin', 'Fluconazole', 'Increased phenytoin toxicity')");
        db.execSQL("INSERT INTO drug_interactions (drug1, drug2, interaction) VALUES ('Linezolid', 'Fluoxetine', 'Risk of serotonin syndrome')");
        db.execSQL("INSERT INTO drug_interactions (drug1, drug2, interaction) VALUES ('Carbamazepine', 'Erythromycin', 'Carbamazepine toxicity')");
        db.execSQL("INSERT INTO drug_interactions (drug1, drug2, interaction) VALUES ('Atorvastatin', 'Grapefruit', 'Increased statin levels')");
        db.execSQL("INSERT INTO drug_interactions (drug1, drug2, interaction) VALUES ('Cisapride', 'Ketoconazole', 'Fatal cardiac arrhythmias')");
        db.execSQL("INSERT INTO drug_interactions (drug1, drug2, interaction) VALUES ('MAOIs', 'Tyramine-rich foods', 'Hypertensive crisis')");
        db.execSQL("INSERT INTO drug_interactions (drug1, drug2, interaction) VALUES ('Fluoxetine', 'Tramadol', 'Risk of seizures')");
        db.execSQL("INSERT INTO drug_interactions (drug1, drug2, interaction) VALUES ('Omeprazole', 'Clopidogrel', 'Reduced antiplatelet effect')");
        db.execSQL("INSERT INTO drug_interactions (drug1, drug2, interaction) VALUES ('Cyclosporine', 'Rifampin', 'Reduced cyclosporine levels')");
        db.execSQL("INSERT INTO drug_interactions (drug1, drug2, interaction) VALUES ('Methotrexate', 'NSAIDs', 'Increased methotrexate toxicity')");
        db.execSQL("INSERT INTO drug_interactions (drug1, drug2, interaction) VALUES ('Spironolactone', 'ACE inhibitors', 'Severe hyperkalemia')");
        db.execSQL("INSERT INTO drug_interactions (drug1, drug2, interaction) VALUES ('Allopurinol', 'Azathioprine', 'Bone marrow suppression')");
        db.execSQL("INSERT INTO drug_interactions (drug1, drug2, interaction) VALUES ('Macrolides', 'QT-prolonging agents', 'Arrhythmia risk')");
        // Add additional 80 interactions similarly

        // Add more entries (simplified loop for bulk entries)
        for (int i = 1; i <= 95; i++) {
            String drug1 = "Drug" + i;
            String drug2 = "Drug" + (i + 1);
            String interaction = "Interaction between " + drug1 + " and " + drug2;
            db.execSQL("INSERT INTO drug_interactions (drug1, drug2, interaction) VALUES (?, ?, ?)",
                    new Object[]{drug1, drug2, interaction});
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS drug_interactions");
        onCreate(db);
    }

    public void openDatabase() throws SQLException {
        if (database == null || !database.isOpen()) {
            database = getReadableDatabase();
            Log.d("DatabaseHelper", "Database opened internally");

            Cursor cursor = database.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
            boolean foundDrugTable = false;

            if (cursor.moveToFirst()) {
                do {
                    String tableName = cursor.getString(0);
                    Log.d("DatabaseHelper", "Table found: " + tableName);
                    if (tableName.equals("drug_interactions")) {
                        foundDrugTable = true;
                    }
                } while (cursor.moveToNext());
            }

            if (!foundDrugTable) {
                Log.e("DatabaseHelper", "Expected table 'drug_interactions' not found in database.");
            }

            cursor.close();
        }
    }

    public void closeDatabase() {
        if (database != null && database.isOpen()) {
            database.close();
            Log.d("DatabaseHelper", "Database closed.");
        }
    }

    public String getInteraction(String drug1, String drug2) {
        String interaction = null;
        String query = "SELECT drug1, drug2, interaction FROM drug_interactions WHERE " +
                "(LOWER(drug1) = LOWER(?) AND LOWER(drug2) = LOWER(?)) " +
                "OR (LOWER(drug1) = LOWER(?) AND LOWER(drug2) = LOWER(?))";

        Cursor cursor = database.rawQuery(query, new String[]{drug1, drug2, drug2, drug1});

        if (cursor.moveToFirst()) {
            interaction = cursor.getString(2);
            Log.d("DB_QUERY", "Interaction: " + interaction);
        } else {
            Log.d("DB_QUERY", "No interaction found.");
        }

        cursor.close();
        return interaction;
    }

    public void copyDatabase() {
    }
}
