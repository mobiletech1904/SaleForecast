package polytech.saleforecast.model;

/*
 * Модель данных сведений о продажах с сайта.
 * @Created by Тёма on 13.06.2017.
 * @version 1.0
 */
public class TableModel {
    private String timed;
    private Integer incoming;
    private Integer purchases;

    public TableModel(String timed, Integer incoming, Integer purchases){
        this.timed = timed;
        this.incoming = incoming;
        this.purchases = purchases;
    }

    public String getTimed() {
        return timed;
    }

    public void setTimed(String timed) {
        this.timed = timed;
    }

    public Integer getIncoming() {
        return incoming;
    }

    public void setIncoming(Integer incoming) {
        this.incoming = incoming;
    }

    public Integer getPurchases() {
        return purchases;
    }

    public void setPurchases(Integer purchases) {
        this.purchases = purchases;
    }
}
