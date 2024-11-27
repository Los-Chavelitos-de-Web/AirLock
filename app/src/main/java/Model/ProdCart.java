package Model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;

public class ProdCart implements Parcelable {

    private int cart_id;
    private Uri uri;
    private String title;
    private Double unit_price;
    private int quantity;
    private int prod_id;
    private String currency_id;

    public ProdCart(int cart_id, Uri uri, String prod_name, Double price, int cant, int prod_id) {
        this.cart_id = cart_id;
        this.uri = uri;
        this.title = prod_name;
        this.unit_price = price;
        this.quantity = cant;
        this.prod_id = prod_id;
        this.currency_id = "PEN";
    }

    protected ProdCart(Parcel in) {
        cart_id = in.readInt();
        prod_id = in.readInt();
        quantity = in.readInt();
        uri = in.readParcelable(Uri.class.getClassLoader());
        title = in.readString();
        currency_id = in.readString();
        if (in.readByte() == 0) {
            unit_price = null;
        } else {
            unit_price = in.readDouble();
        }
    }

    public static final Creator<ProdCart> CREATOR = new Creator<ProdCart>() {
        @Override
        public ProdCart createFromParcel(Parcel in) {
            return new ProdCart(in);
        }

        @Override
        public ProdCart[] newArray(int size) {
            return new ProdCart[size];
        }
    };

    public int getProd_id() {
        return prod_id;
    }

    public void setProd_id(int prod_id) {
        this.prod_id = prod_id;
    }

    public String getCurrency_id() {
        return currency_id;
    }

    public void setCurrency_id(String currency_id) {
        this.currency_id = currency_id;
    }

    public int getCart_id() {
        return cart_id;
    }

    public void setCart_id(int prod_id) {
        this.cart_id = prod_id;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public String getProd_name() {
        return title;
    }

    public void setProd_name(String prod_name) {
        this.title = prod_name;
    }

    public Double getPrice() {
        return unit_price;
    }

    public void setPrice(Double price) {
        this.unit_price = price;
    }

    public int getCant() {
        return quantity;
    }

    public void setCant(int cant) {
        this.quantity = cant;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(cart_id);
        parcel.writeParcelable(uri, i);
        parcel.writeString(title);
        parcel.writeString(currency_id);
        parcel.writeInt(quantity);
        if (unit_price == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeDouble(unit_price);
        }
    }
}
