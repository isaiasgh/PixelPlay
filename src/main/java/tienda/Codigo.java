package tienda;

import java.io.Serializable;

public class Codigo implements Serializable{
    private static final long serialVersionUID = 1L;
    private String codigo;
    private double dscPorcentaje;

  public Codigo(String codigo, double dscPorcentaje){
    this.codigo = codigo;
    this.dscPorcentaje = dscPorcentaje;
  }
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public double getDscPorcentaje() {
		return dscPorcentaje;
	}
	public void setDscPorcentaje(double dscPorcentaje) {
		this.dscPorcentaje = dscPorcentaje;
	}  
}