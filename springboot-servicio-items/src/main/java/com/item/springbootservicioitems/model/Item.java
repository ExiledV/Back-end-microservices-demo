package com.item.springbootservicioitems.model;

public class Item {

    private Producto producto;
    private Integer cantidad;

    public Item(){}
    public Item(Producto p, Integer c){
        this.producto = p;
        this.cantidad = c;
    }

    public Double getTotal(){
        return this.producto.getPrecio() * this.getCantidad();
    }

    //GETTERS AND SETTERS
    public Producto getProducto() {
        return producto;
    }
    public void setProducto(Producto producto) {
        this.producto = producto;
    }
    public Integer getCantidad() {
        return cantidad;
    }
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
}
