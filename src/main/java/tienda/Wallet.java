package tienda;

import com.poo.proyectosegundoparcial.consultas;
import java.io.Serializable;

public class Wallet implements Serializable{
  private static final long serialVersionUID = 1L;
  private double saldo = 0;

  public Wallet(){
    
  }
  
  public double getSaldo() {
  	return saldo;
  }
  
  public void setSaldo(double saldo) {
    this.saldo = saldo;
  }
  
  @Override
  public String toString(){
      return "" + saldo;
  }
}