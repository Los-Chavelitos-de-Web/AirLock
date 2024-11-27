package Model;

public class Producto {
    private int ProductoID;
    private String Nombre;
    private String Descripcion;
    private Double PrecioCompra;
    private Double PrecioVenta;
    private int Stock;
    private int ProveedorID;
    private int FechaIngreso;
    private String gen;

    public Producto(int productoID, String nombre, String descripcion, Double precioCompra, Double precioVenta, int stock, int proveedorID, int fechaIngreso, String gen) {
        ProductoID = productoID;
        Nombre = nombre;
        Descripcion = descripcion;
        PrecioCompra = precioCompra;
        PrecioVenta = precioVenta;
        Stock = stock;
        ProveedorID = proveedorID;
        FechaIngreso = fechaIngreso;
        this.gen = gen;
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

    public Double getPrecioCompra() {
        return PrecioCompra;
    }

    public void setPrecioCompra(Double precioCompra) {
        PrecioCompra = precioCompra;
    }

    public Double getPrecioVenta() {
        return PrecioVenta;
    }

    public void setPrecioVenta(Double precioVenta) {
        PrecioVenta = precioVenta;
    }

    public int getStock() {
        return Stock;
    }

    public void setStock(int stock) {
        Stock = stock;
    }

    public String getGen() {
        return gen;
    }

    public void setGen(String gen) {
        this.gen = gen;
    }

    public int getProveedorID() {
        return ProveedorID;
    }

    public void setProveedorID(int proveedorID) {
        ProveedorID = proveedorID;
    }

    public int getFechaIngreso() {
        return FechaIngreso;
    }

    public void setFechaIngreso(int fechaIngreso) {
        FechaIngreso = fechaIngreso;
    }
}
