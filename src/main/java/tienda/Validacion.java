package tienda;

import java.io.Serializable;

public class Validacion implements Serializable{
  private static final long serialVersionUID = 1L;


  public static boolean ValNum(String cadena){
    try {
          Double.parseDouble(cadena);
          return true;
        } catch (NumberFormatException e) {
          System.out.println("\u001B[31mEste campo es solo para datos numéricos, vuelve a intentarlo.\u001B[0m");
          return false;    
      }   
  }

  public static int dameNum(String cadena){
    cadena=cadena.replaceAll("\\s+", "");
    int numeroEntero = Integer.parseInt(cadena);
    return numeroEntero;
  }   
  
  public static double dameDou(String cadena){
    cadena=cadena.replaceAll("\\s+", "");
    double numeroDouble = Double.parseDouble(cadena);
    return numeroDouble;
  }  
  
}
