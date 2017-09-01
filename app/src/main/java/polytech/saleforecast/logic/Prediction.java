package polytech.saleforecast.logic;

import java.util.ArrayList;

/*
 * Класс для работы с прогнозированием.
 * @Created by Тёма on 16.06.2017.
 * @version 1.0
 */
public class Prediction {
    /**
     * Метод возвращает список данных прогноза.
     * @param inputDataList входной список данных
     * @param period период прогноза
     * @return список данных прогноза
     */
    public ArrayList<Integer> predictionData(ArrayList<Integer> inputDataList, int period){
        int start = 0;

        while(start < period) {
            int sumDada = 0;
            int countInputDataList = inputDataList.size();
            for (int i = countInputDataList-3; i < countInputDataList; i++) {
                sumDada += inputDataList.get(i);
            }
            //int result = (int)(sumDada/(countInputDataList - start));
            int result = (int)(sumDada/3);
            inputDataList.add(result);
            start++;
        }

        return inputDataList;
    }
}
