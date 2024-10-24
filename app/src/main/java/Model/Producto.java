package Model;

import java.util.Date;

public class Producto {
    private int ProductoID;
    private String Nombre;
    private String Descripcion;
    private Float PrecioCompra;
    private Float PrecioVenta;
    private int Stock;
    private int ProveedorID;
    private Date FechaIngreso;

    public Producto(int productoID, String nombre, String descripcion, Float precioCompra, Float precioVenta, int stock, int proveedorID, Date fechaIngreso) {
        ProductoID = productoID;
        Nombre = nombre;
        Descripcion = descripcion;
        PrecioCompra = precioCompra;
        PrecioVenta = precioVenta;
        Stock = stock;
        ProveedorID = proveedorID;
        FechaIngreso = fechaIngreso;
    }

    public int getProductoID() {
        return ProductoID;
    }

    public void setProductoID(int productoID) {
        ProductoID = productoID;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public Float getPrecioCompra() {
        return PrecioCompra;
    }

    public void setPrecioCompra(Float precioCompra) {
        PrecioCompra = precioCompra;
    }

    public Float getPrecioVenta() {
        return PrecioVenta;
    }

    public void setPrecioVenta(Float precioVenta) {
        PrecioVenta = precioVenta;
    }

    public int getStock() {
        return Stock;
    }

    public void setStock(int stock) {
        Stock = stock;
    }

    public int getProveedorID() {
        return ProveedorID;
    }

    public void setProveedorID(int proveedorID) {
        ProveedorID = proveedorID;
    }

    public Date getFechaIngreso() {
        return FechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        FechaIngreso = fechaIngreso;
    }
}
