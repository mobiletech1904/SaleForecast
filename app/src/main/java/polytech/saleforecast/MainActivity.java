package polytech.saleforecast;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
/*
 * Главная активность проекта.
 * @Created by Тёма on 13.06.2017.
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {
    private Button searchButton;
    private Button aboutButton;
    private Button versionButton;
    private Button sendMessageButton;

    /** Навигационное меню главной активности */
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    showMain();
                    addListenerOnSearchButton();
                    return true;

                case R.id.navigation_settings:
                    showSettings();
                    addListenerOnAboutButton();
                    addListenerOnVersionButton();
                    addListenerOnSendMessageButton();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setIcon(R.drawable.activity_icon);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // Отображаем главное окно.
        showMain();

        // Открываем активити вывода результатов.
        addListenerOnSearchButton();
    }

    /**
     * Метод для имитации поиска данных.
     * Данные задаются рандомными числами.
     */
    public void addListenerOnSearchButton() {
        searchButton = (Button) findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intentResult = new Intent(MainActivity.this, ResultActivity.class);
                startActivity(intentResult);
            }

        });
    }

    /**
     * Метод для вывода информации о приложении.
     */
    public void addListenerOnAboutButton() {
        aboutButton = (Button) findViewById(R.id.aboutButton);
        aboutButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Про додаток")
                        .setMessage("Додаток \"Прогноз продажів\" розроблений для прогнозу об'єму " +
                                "продажів й кількості покупців на обраному Web-ресурсі та графічного " +
                                "відображення отриманих результатів у вигляді лінійного графіку.")
                        .setCancelable(false)
                        .setNegativeButton("ОК",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alert = builder.create();
                alert.show();
            }

        });
    }

    /**
     * Метод для вывода информации о версии приложения.
     */
    public void addListenerOnVersionButton() {
        versionButton = (Button) findViewById(R.id.versionButton);
        versionButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Версія додатку")
                        .setMessage("Версія додатку \"Прогноз продажів\": v1.0.0")
                        .setCancelable(false)
                        .setNegativeButton("ОК",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alert = builder.create();
                alert.show();
            }

        });
    }

    /**
     * Метод для отображения контактов автора.
     */
    public void addListenerOnSendMessageButton() {
        sendMessageButton = (Button) findViewById(R.id.sendMessageButton);
        sendMessageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Зв'язок з автором")
                        .setMessage(
                                "artyom4ek@yandex.ru\n" +
                                        "+380993420954")
                        .setCancelable(false)
                        .setNegativeButton("ОК",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alert = builder.create();
                alert.show();
            }

        });
    }

    /**
     * Метод отображает главный слой менюшки главной активити.
     */
    private void showMain(){
        FrameLayout frame = (FrameLayout)findViewById(R.id.content_main_frame);
        frame.removeAllViews();

        View mainView = getLayoutInflater().inflate(R.layout.layout_main, null);
        frame.addView(mainView);
    }

    /**
     * Метод отображает слой информации о приложении менюшки главного активити.
     */
    private void showSettings(){
        FrameLayout frame = (FrameLayout)findViewById(R.id.content_main_frame);
        frame.removeAllViews();

        View mainView = getLayoutInflater().inflate(R.layout.layout_settings, null);
        frame.addView(mainView);
    }

}
