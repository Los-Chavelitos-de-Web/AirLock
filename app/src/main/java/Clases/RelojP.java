package Clases;

import android.net.Uri;

public class RelojP {

    String nombre;
    String tipo;
    Uri img;
    Double precio;

    public RelojP(String nombre, String tipo, Uri img, Double precio) {
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

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }
}
