package tienda;

import java.io.Serializable;

public class Respuesta extends Resena implements Serializable {
    private static final long serialVersionUID = 1L;


  public Respuesta(Usuario autor, String comentario) {
    super(autor, comentario);
  }

}
