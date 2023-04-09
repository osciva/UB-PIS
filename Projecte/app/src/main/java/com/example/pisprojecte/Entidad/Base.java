package com.example.pisprojecte.Entidad;

public class Base {
    private String nombreBase;
    private String sonido;

    public Base(String nombreBase, String sonido) {
        this.nombreBase = nombreBase;
        this.sonido = sonido;
    }

    public String getNombreBase() {
        return nombreBase;
    }

    public void setNombreBase(String nombreBase) {
        this.nombreBase = nombreBase;
    }

    public String getSonido() {
        return sonido;
    }

    public void setSonido(String sonido) {
        this.sonido = sonido;
    }
}
