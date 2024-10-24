package Model;

import android.net.Uri;

public class RelojP {

    String nombre;
    String tipo;
    Uri img;
    Float precio;

    public RelojP(String nombre, String tipo, Uri img, Float precio) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.img = img;
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Uri getImg() {
        return img;
    }

    public void setImg(Uri img) {
        this.img = img;
    }

    public Float getPrecio() {
        return precio;
    }

    public void setPrecio(Float precio) {
        this.precio = precio;
    }
}
