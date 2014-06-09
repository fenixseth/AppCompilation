package pl.reisspartners.pjakprogramowanie.appcompilation;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class UnitConverter extends ActionBarActivity {

    EditText editFrom;
    EditText editTo;
    Button unit1Button;
    Button unit2Button;
    Button catButton;

    int unit1=-1;
    int unit2=-1;
    int category=0;

    CharSequence[] elementsUnits;
    CharSequence[] categories = {"Temperature", "Distance", "Currency", "Area", "Speed"};
    CharSequence[] array1 = {"Celsius", "Farenheit", "Kelvin"};
    CharSequence[] array2 = {"Meter", "Kilometer", "Mile", "Foot", "Inch"};
    CharSequence[] array3 = {"PLN", "EUR", "USD", "CHF", "GPB"};
    CharSequence[] array4 = {"m²", "hectare", "km²", "foot²", "mile²"};
    CharSequence[] array5 = {"km/h", "miles/h", "m/s"};
    CharSequence[][] units = {array1, array2, array3, array4, array5};

    double[] temperatures = {};
    double[] currencies = {1, 4.18404, 3.0284, 3.42889, 5.08499};
    double[] distances = {1, 1000, 1609.344, 0.3048, 0.0254};
    double[] areas = {1, 10000, 1000000, 0.09290304, 2589988.11};
    double[] speeds = {1, 1.609344, 3.6};
    double[][] converters= {temperatures, distances, currencies, areas, speeds};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_converter);
        editFrom = (EditText)findViewById(R.id.editText);
        editTo = (EditText)findViewById(R.id.editText2);
        unit1Button = (Button)findViewById(R.id.button);
        unit2Button = (Button)findViewById(R.id.button2);
        catButton = (Button)findViewById(R.id.button4);
    }


    public void change1Unit(View view) {
        //wybranie odpowiednich jednostek do wyświetlenia, wybór tablicy z tablicy tablic
        elementsUnits = units[category];
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose category");
        builder.setItems(elementsUnits, new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int item){
                unit1Button.setText(elementsUnits[item]);
                unit1 = item;
            }

        });
        builder.create();
        builder.show();
    }

    public void change2Unit(View view) {
        //wybranie odpowiednich jednostek do wyświetlenia, wybór tablicy z tablicy tablic
        elementsUnits = units[category];
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose category");
        builder.setItems(elementsUnits, new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int item){
                unit2Button.setText(elementsUnits[item]);
                unit2 =item;
            }

        });
        builder.create();
        builder.show();


    }

    public void changeCategory(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose category");
        builder.setItems(categories, new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int item){
                catButton.setText(categories[item]);
                category = item; //zapisanie w zmiennej, jaką kategorię wybrał użytkownik
                //zresetowanie wyboru poprzednich jednostek
                unit1 = -1;
                unit2 = -1;
                unit1Button.setText("Select Unit");
                unit2Button.setText("Select Unit");

            }

        });
        builder.create();
        builder.show();
    }

    public void count(View view) {
        //sprawdzenie, czy użytkownik wpisał jakąś wartość w editFrom
        if (editFrom.getText().toString().isEmpty()){
            //jeśli nie wpisał, wyświetlamy toast
            Context context = getApplicationContext();
            CharSequence text="No data given";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }else {
            //jeśli wpisał, pobieramy wpisaną wartość
            double from = Double.parseDouble(editFrom.getText().toString());

            //sprawdzenie, czy użytkownik wybrał kat., i obie jednostki
            if (category == -1 || unit1 == -1 || unit2 ==-1){
                //jeśli nie, wyświetlamy ostrzeżenie
                Context context = getApplicationContext();
                CharSequence text="Pick units and category";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
            else {
                //jeśli wszystko wybrał kontynuujemy

                //przeliczanie temperatury wymaga specjalnego algorytmu

                //sprawdzamy, czy użytkownik wybrał temperaturę
                if (category == 0) {
                    //jeśli tak, przeliczamy najpierw wszystkie temperatury na C
                    switch (unit1) {//sprawdzenie, którą jednostkę źródłową wybrał użytkownik

                        //jeśli wybrał C, nic nie rób

                        case 1: //jeśli Farenheit
                            //z F na C
                            from = 5 / 9 * (from - 32);
                            break;

                        case 2: //jeśli Kelvin
                            //z K na C
                            from = from - 273.15;
                            break;
                    }

                    //teraz zmienna from zawiera wartość temperatury w C

                    switch (unit2) {//sprawdzenie, którą jednostkę docelową wybrał użytkownik

                        //jeśli wybrał C, nic nie rób

                        case 1: //jeśli Farenheit
                            //z C na F
                            from = 32 + 9 / 5 * from;
                            break;

                        case 2://jeśli Kelvin
                            //z C na K
                            from = from + 273.15;
                            break;

                    }

                } else {
                    /*jeżeli użytkownik nie wybrał temperatury, sprawa jest prosta, przeliczamy na pierwszą
                    jednostkę z tablicy, a następnie na docelową*/
                    from = from * converters[category][unit1];
                    from = from / converters[category][unit2];
                }
                editTo.setText(String.valueOf(from)); // wypisanie wyniku do editText
            }
        }
    }

    public void pasteData(View view) {

    }


    public void copyData(View view) {
        

    }
}