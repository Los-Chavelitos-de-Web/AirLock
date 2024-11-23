package Model;

import android.net.Uri;

public class ProdCart {

    private int prod_id;
    private Uri uri;
    private String prod_name;
    private Double price;
    private int cant;

    public ProdCart(int prod_id, Uri uri, String prod_name, Double price, int cant) {
        this.prod_id = prod_id;
        this.uri = uri;
        this.prod_name = prod_name;
        this.price = price;
        this.cant = cant;
    }

    public int getProd_id() {
        return prod_id;
    }

    public void setProd_id(int prod_id) {
        this.prod_id = prod_id;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public String getProd_name() {
        return prod_name;
    }

    public void setProd_name(String prod_name) {
        this.prod_name = prod_name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getCant() {
        return cant;
    }

    public void setCant(int cant) {
        this.cant = cant;
    }
}
