package FarmEntity;

public class Sent {
    private int id;
    private int purchaseId;
    private String adress;

    public Sent(){}

    public Sent(int id, int purchaseId, String adress) {
        this.id = id;
        this.purchaseId = purchaseId;
        this.adress = adress;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(int purchaseId) {
        this.purchaseId = purchaseId;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    @Override
    public String toString() {
        return "Sent{" +
                "id=" + id +
                ", purchaseId=" + purchaseId +
                ", adress='" + adress + '\'' +
                '}';
    }
}
