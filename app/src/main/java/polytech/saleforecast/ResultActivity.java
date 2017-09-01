package polytech.saleforecast;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import polytech.saleforecast.adapter.TableAdapter;
import polytech.saleforecast.chart.LineView;
import polytech.saleforecast.logic.Prediction;
import polytech.saleforecast.model.TableModel;

/*
 * Активити вывода сведений и построения графика по этим сведениям.
 * @Created by Тёма on 15.06.2017.
 * @version 1.0
 */
public class ResultActivity extends AppCompatActivity {
    private Button predictionButton;
    private ArrayList<TableModel> tableItemList;
    private int predictionPeriod = 0;
    /*
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_table:
                    showTable();
                    return true;

                case R.id.navigation_graph:
                    showGraph();
                    return true;
            }
            return false;
        }

    };
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        getSupportActionBar().setIcon(R.drawable.activity_icon);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
        // BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        // navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        View navigationGraph = bottomNavigationView.findViewById(R.id.navigation_graph);
        navigationGraph.setClickable(false);

        // Отображаем таблицу.
        showTable();

        // Строим график прогнозу.
        addListenerOnPredictionButton();
    }

    /*
     * Метод вызывает диалог о значения периода прогноза.
     */
    private void addListenerOnPredictionButton() {
        predictionButton = (Button) findViewById(R.id.prediction_button);
        predictionButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                showPredictionPeriodDialog();
            }

        });
    }

    /*
     * Метод отображает таблицу данных о продажах с сервиса.
     */
    private void showTable(){
        FrameLayout frame = (FrameLayout)findViewById(R.id.content_result_frame);
        frame.removeAllViews();

        View viewTable = getLayoutInflater().inflate(R.layout.layout_table_container, null);
        frame.addView(viewTable);

        prepareTable();
    }

    /*
     * Метод подготавливает данные для таблицы.
     */
    private void prepareTable(){
        tableItemList = new ArrayList<TableModel>();
        ListView tableListView = (ListView) findViewById(R.id.table_list_view);
        TableAdapter adapter = new TableAdapter(this, tableItemList);
        tableListView.setAdapter(adapter);

        for(int i=1; i<=12; i++){
            tableItemList.add(new TableModel(
                    Integer.toString(i),
                    ((int)(Math.random() * (750-450)  + 450)),
                    ((int)(Math.random() * (450-150)  + 150)))
            );
        }
    }

    /*
     * Метод отображает элемент меню с графиком прогноза.
     */
    private void showGraph(){
        FrameLayout frameLayout = (FrameLayout)findViewById(R.id.content_result_frame);
        frameLayout.removeAllViews();

        View viewGraph = getLayoutInflater().inflate(R.layout.layout_graph, null);
        frameLayout.addView(viewGraph);

        drawGraph();
    }

    /*
     * Метод рисует график прогноза по подготовленным данным.
     */
    private void drawGraph(){
        ArrayList<String> timedList = new ArrayList<String>();
        int countTimed = 1;
        for(int i=1; i<=tableItemList.size()+predictionPeriod; i++){
            timedList.add(countTimed + "");
            countTimed++;
            if(countTimed>12){
                countTimed = 1;
            }
        }

        ArrayList<Integer> incomingList = new ArrayList<Integer>();
        ArrayList<Integer> purchasesList = new ArrayList<Integer>();

        for(TableModel tempPeriod : tableItemList){
            incomingList.add(tempPeriod.getIncoming());
            purchasesList.add(tempPeriod.getPurchases());
        }

        ArrayList< ArrayList<Integer> > generalDataList = new ArrayList<>();
        Prediction prediction = new Prediction();
        generalDataList.add(prediction.predictionData(incomingList, predictionPeriod));
        generalDataList.add(prediction.predictionData(purchasesList, predictionPeriod));

        /*generalDataList.add(prediction.predictionData(new ArrayList<Integer>(
                Arrays.asList(
                        560, 655, 585, 525,
                        630, 645, 590, 620,
                        600, 615, 550, 575
                )
        ), predictionPeriod));
        generalDataList.add(prediction.predictionData(new ArrayList<Integer>(
                Arrays.asList(
                        320, 355, 425, 400,
                        360, 345, 390, 415,
                        485, 455, 395, 405
                )
        ), predictionPeriod));*/

        ArrayList<Double> conversionRateList = new ArrayList<>();
        for (int j = 0; j < generalDataList.get(0).size(); j++) {
            double incomingItem = generalDataList.get(0).get(j);
            double purchasesItem = generalDataList.get(1).get(j);
            double conversionRateResult = (purchasesItem/incomingItem)*100;

            conversionRateList.add(Math.rint(100.0 * conversionRateResult) / 100.0);
        }

        // Рисуем график.
        LineView lineView = (LineView)findViewById(R.id.line_view);
        lineView.setGlobalPredictionPeriod(predictionPeriod);
        lineView.setDrawDotLine(false);
        lineView.setShowPopup(LineView.SHOW_POPUPS_All);
        lineView.setBottomTextList(timedList);
        lineView.setColorArray(new int[]{Color.BLACK, Color.RED, Color.GRAY, Color.CYAN});
        lineView.setDataList(generalDataList);
        lineView.setConversionRateList(conversionRateList);
    }

    /*
     * Метод показывает диалог выбора периода прогноза.
     */
    public void showPredictionPeriodDialog(){
        LayoutInflater layoutInflater = LayoutInflater.from(ResultActivity.this);
        View predictionDialogView = layoutInflater.inflate(R.layout.layout_prediction_dialog, null);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ResultActivity.this);
        dialogBuilder.setView(predictionDialogView);

        final EditText predictionInput = (EditText) predictionDialogView.findViewById(R.id.input_text);

        dialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                if( !predictionInput.getText().toString().isEmpty()) {
                                    BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
                                    // bottomNavigationView.setSelectedItemId(R.id.navigation_graph);

                                    View navigationGraph = bottomNavigationView.findViewById(R.id.navigation_graph);
                                    navigationGraph.performClick();

                                    View navigationTable = bottomNavigationView.findViewById(R.id.navigation_table);
                                    navigationTable.setClickable(false);

                                    predictionPeriod = Integer.parseInt(predictionInput.getText().toString().trim());

                                    if (predictionPeriod >= 1 && predictionPeriod<100) {
                                        showGraph();
                                    } else {
                                        Toast.makeText(
                                                ResultActivity.this, "Зазначений період часу має бути в проміжках >0 та <100!",
                                                Toast.LENGTH_SHORT
                                        ).show();
                                    }
                                }else{
                                    Toast.makeText(ResultActivity.this, "Поле обов'язкове для заповнення!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })

                .setNegativeButton("Відмінити",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
    }

}
