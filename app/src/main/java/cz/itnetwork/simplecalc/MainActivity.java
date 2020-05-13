/*
 *  _____ _______         _                      _
 * |_   _|__   __|       | |                    | |
 *   | |    | |_ __   ___| |___      _____  _ __| | __  ___ ____
 *   | |    | | '_ \ / _ \ __\ \ /\ / / _ \| '__| |/ / / __|_  /
 *  _| |_   | | | | |  __/ |_ \ V  V / (_) | |  |   < | (__ / /
 * |_____|  |_|_| |_|\___|\__| \_/\_/ \___/|_|  |_|\_(_)___/___|
 *                   ___
 *                  |  _|___ ___ ___
 *                  |  _|  _| -_| -_|  LICENCE
 *                  |_| |_| |___|___|
 *
 *   PROGRAMOVÁNÍ  <>  DESIGN  <>  PRÁCE/PODNIKÁNÍ  <>  HW A SW
 *
 * Tento zdrojový kód pochází z IT sociální sítě WWW.ITNETWORK.CZ
 *
 * Můžete ho upravovat a používat jak chcete, musíte však zmínit
 * odkaz na http://www.itnetwork.cz
 */

package cz.itnetwork.simplecalc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    final String MUJ_TAG = "muj_tag";

    // Proměnné pro reference na elementy v XML
    EditText etNumber1;         // Políčko pro první číslo
    EditText etNumber2;         // Políčko pro druhé číslo
    Spinner spinnerOperation;   // Rozbalovací nabídka operátorů
    TextView labelResult;       // Label pro výsledek výpočtu

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Nastavení layoutu vzhledu GUI
        setContentView(R.layout.activity_main);

        // Uložení referencí na elementy v XML do proměnných
        etNumber1 = findViewById(R.id.etNumber1);
        etNumber2 = findViewById(R.id.etNumber2);
        spinnerOperation = findViewById(R.id.spinnerOperation);
        labelResult = findViewById(R.id.labelResult);

        Log.d(MUJ_TAG, "onCreate()");

        // Získání pole s položkami pro Spinner (rozbalovací menu) z resources (res/values/strings.xml)
        String[] operatorsArray = getResources().getStringArray(R.array.operators);

        // Vytvoření kolekce z pole pro následující řádek
        List list = new ArrayList(Arrays.asList(operatorsArray));

        // Vytvoření adaptéru pro Spinner
        // R.layout.spinner_item odkazuje na XML vzhled položky Spinneru, která bude zvolená
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, list);

        // Nastavení vzhledu ostatních (nevybraných) položek ve Spinneru
        dataAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        // Nastavení adaptéru Spinneru
        spinnerOperation.setAdapter(dataAdapter);
    }

    // Metoda pro vypočtení zadané operace, která je volána kliknutím na tlačítko "=". Po kliknutí
    // na tlačítko "=" je tato metoda volána díky parametru tlačítka android:onClick="calculate"
    // v XML návrhu layoutu
    public void calculate(View view) {
        double number1;         // Proměnná pro první číslo
        double number2;         // Proměnná pro druhé číslo
        double result = 0;      // Proměnná pro výsledek výpočtu

        try {
            // Převod řetězců ze zadávacích polí na desetinné číslo.
            // Kvůli této operaci je tento blok kódu v bloku try-catch pro odchycení případné výjimky
            // NumberFormatException, ke které dojde při zadání nečíselné hodnoty do pole pro číslo
            // a díky které by mohla aplikace spadnout.
            number1 = Double.parseDouble(etNumber1.getText().toString());
            number2 = Double.parseDouble(etNumber2.getText().toString());

            // Zjištění, který operátor je zvolen ve Spinneru
            if (spinnerOperation.getSelectedItem().equals("+")) {
                result = number1 + number2;
            } else if (spinnerOperation.getSelectedItem().equals("-")) {
                result = number1 - number2;
            } else if (spinnerOperation.getSelectedItem().equals("*")) {
                result = number1 * number2;
            } else if (spinnerOperation.getSelectedItem().equals("/")) {
                // Ošetření možnosti požadavku na dělení nulou
                if (number2 == 0) {
                    labelResult.setText("NULOU DĚLIT NELZE!");
                    return;
                }
                result = number1 / number2;
            }

            Log.d(MUJ_TAG, "Výpočet: " + number1 + spinnerOperation.getSelectedItem() + number2 + "=" + result);

            NumberFormat nf = new DecimalFormat("#.###");
            labelResult.setText(nf.format(result));
        } catch (NumberFormatException e) {
            // Došlo k zadání nečíselné hodnoty. Tímto zabráníme pádu aplikace a s výjimkou
            // si poradíme po svém...
            labelResult.setText("Chybné zadání...");
        }
    }
}
