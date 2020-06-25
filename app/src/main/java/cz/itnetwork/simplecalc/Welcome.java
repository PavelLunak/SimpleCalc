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

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        getSupportActionBar().hide();

        // Nastavíme a spustíme animaci pro logo
        ImageView imgLogo = findViewById(R.id.imgLogo);
        Animation animSpsoaLogo = AnimationUtils.loadAnimation(this, R.anim.welcome_sc);
        /* Animace se „aplikuje“ na imageView, což je ID našeho loga.
           Veškeré potřebné nastavení pro samotnou animaci je v souboru welcome_sc.xml
        */
        imgLogo.startAnimation(animSpsoaLogo); // spuštění samotné animace

        // Vytvoření druhého vlákna s časovačem, po uplynutí limitu je spuštěna další aktivita
        Thread thrdWlcmscrnDelay = new Thread() {
            public void run() {
                try {
                    sleep(3500);
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    finish();
                }
            }
        };
        thrdWlcmscrnDelay.start();
    }
}