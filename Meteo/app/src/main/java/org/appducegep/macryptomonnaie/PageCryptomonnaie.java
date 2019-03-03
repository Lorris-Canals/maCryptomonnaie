package org.appducegep.macryptomonnaie;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.*;

public class PageCryptomonnaie extends AppCompatActivity {

    private TextView libelleTitre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_meteo);
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        libelleTitre = (TextView) findViewById(R.id.message);

        JSONObject jsonobj = null;

        try {
            URL url = new URL("https://blockchain.info/fr/ticker");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                jsonobj = new JSONObject(stringBuilder.toString());
            }
            finally{
                urlConnection.disconnect();
            }
        }
        catch(Exception e) {
            Log.e("ERROR", e.getMessage(), e);
        }
        System.out.println(jsonobj);

        try {
            String symbol = jsonobj.getJSONObject("CAD").getString("symbol");
            String min15= jsonobj.getJSONObject("CAD").getString("15m");
            String last = jsonobj.getJSONObject("CAD").getString("last");
            String buy = jsonobj.getJSONObject("CAD").getString("buy");
            String sell = jsonobj.getJSONObject("CAD").getString("sell");

            System.out.println("");
            System.out.println("/////////////////////////////");
            System.out.println("/// Bitcoin");
            System.out.println("/// Symbol = " + symbol);
            System.out.println("/// 15 min = " + min15);
            System.out.println("/// Last = " + last + "\n");
            System.out.println("/// Buy = " + buy);
            System.out.println("/// Sell = " + sell);
            System.out.println("/////////////////////////////");


            TextView affichageTitrePage = (TextView)this.findViewById(R.id.titre_page_meteo);
            affichageTitrePage.setText("Cours du Bitcoin" );


            TextView affichageMeteo = (TextView)this.findViewById(R.id.meteo);
            affichageMeteo.setText("Symbol : " + symbol + "\n");
            affichageMeteo.append("\n\n");
            affichageMeteo.append("15 min : " + min15 + "\n");
            affichageMeteo.append("Last : " + last + "\n");
            affichageMeteo.append("Buy : " + buy + "\n");
            affichageMeteo.append("Sell : " + sell + "\n");


            CryptomonnaieDAO cryptomonnaieDAO = new CryptomonnaieDAO(getApplicationContext());
            cryptomonnaieDAO.ajouterCryptomonnaie(symbol, min15, last, buy, sell);


        }
         catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
