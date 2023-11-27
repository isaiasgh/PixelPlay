package tienda;
import java.io.Serializable;
import java.util.ArrayList;

public class Biblioteca implements Serializable{
  private static final long serialVersionUID = 1L;
  private ArrayList<Videojuego> videojuegos;
  private int cantJuegos = 0;

  public Biblioteca(){
    videojuegos = new ArrayList<Videojuego>();
  }

  public String dameNombreVg(int id){
    String nombre = "";
    for(Videojuego vg: videojuegos){
      if(vg.getId() == id){
        nombre = vg.getNombre();
      }
    }
    return nombre;
  }

  public ArrayList<Videojuego> getVideojuegos() {
  	return videojuegos;
  }

  public void setVideojuegos(ArrayList<Videojuego> videojuegos) {
  	this.videojuegos = videojuegos;
  }
  
  public int getCantJuegos() {
  	return cantJuegos;
  }
  
  public void setCantJuegos(int cantJuegos) {
  	this.cantJuegos = cantJuegos;
  }
}
