package tienda;
import com.poo.proyectosegundoparcial.consultas;
import java.io.Serializable;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Resena implements Serializable{
  private static final long serialVersionUID = 1L;
  private Usuario autor;
  private String comentario;
  private Date fecha;
  private Videojuego videojuego;
  private int id;
  private static int contador = 1;
  private Respuesta respuesta;
  private boolean res;

  public Resena(Usuario autor, String comentario) {
    this.autor = autor;
    this.comentario = comentario;
    fecha = new Date();
    id = contador;
    contador ++;
  }

  public static void dameResena(Usuario user){
    ArrayList resenas = new ArrayList<Resena>();
    resenas = user.getResenas();

    if(!(resenas.size()==0)){ 
      SimpleDateFormat formatoFecha= new SimpleDateFormat("dd/MM/yyyy");
       for (Resena res : user.getResenas()) {
         if(res instanceof Respuesta){
            String fechaFormateada = formatoFecha.format(res.getFecha());
            System.out.println("\nId #"+ res.getId() + " - " + fechaFormateada);;
            System.out.println("\u001B[35mR\u001B[0m: " + "\n" + res.getComentario());
         }
         else{
          String fechaFormateada = formatoFecha.format(res.getFecha());
          System.out.println("\nId #"+ res.getId() + " - " + fechaFormateada);
          System.out.println("\u001B[32m" + res.getVideojuego() + "\u001B[0m");
          System.out.println(res.getComentario());
         }

      }
    }else{
      System.out.print("\n");
      System.out.println("Aun no haz hecho ninguna reseña");
    }
  }

  public static void dameResena(int id){
    int bandera=0;
    Sistema sist = (Sistema) consultas.dameSistema();
    for(Videojuego vg: sist.getInventario()){
      if(id == vg.getId()){
        bandera=1;
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        if(vg.getResenas().size() != 0){
          for(Resena res: vg.getResenas()){
            
            if(!(res instanceof Respuesta)){}
              String fechaFormateada = formatoFecha.format(res.getFecha());
              System.out.println(vg.getNombre() + " - " + fechaFormateada);
              System.out.println(res.getAutor().getUsername() + ": " + res.getComentario());
              System.out.println("");
              if(res.res == true){
                
                Respuesta respuestaR = res.getRespuesta();
                fechaFormateada = formatoFecha.format(respuestaR.getFecha()); // supuestamente esta null esa fecha
                System.out.println(respuestaR.getAutor().getUsername() + " \u001B[35mreplying\u001B[0m " + res.getAutor().getUsername() + " - " + fechaFormateada + " - [" + respuestaR.getId() + "]");
                System.out.println(respuestaR.getComentario());
                System.out.println("");
                
              }
            }
          } else{
            System.out.println("\nActualmente " + vg.getNombre() + " no tiene ninguna reseña.");
          }
        }
      }
    if(bandera==0){
      System.out.println("El ID que ingresaste no existe");
    }
  }
  
  public void dameResena(){
    if(!(this instanceof Respuesta)){
      SimpleDateFormat formatoFecha= new SimpleDateFormat("dd/MM/yyyy");
      String fechaFormateada = formatoFecha.format(this.fecha);
      System.out.println("\nId #"+ this.id + " - " + fechaFormateada);
      System.out.println(this.autor.getUsername() + ": " + this.comentario);
      if(this.res == true){
        System.out.println("Respondida: Sí");
      } else{
        System.out.println("Respondida: No");
      }
    }
  }
  
  public Videojuego getVideojuego() {
	 return videojuego;
  }

  public void setVideojuego(Videojuego nom_Vg) {
	 this.videojuego = nom_Vg;
  }

  public Usuario getAutor() {
    return autor;
  }

  public void setAutor(Usuario autor) {
    this.autor = autor;
  }

  public String getComentario() {
    return comentario;
  }

  public void setComentario(String comentario) {
    this.comentario = comentario;
  }

  public Date getFecha() {
    return fecha;
  }

  public void setFecha(Date fecha) {
    this.fecha = fecha;
  }

  public int getId(){
    return id;
  }

  public void setId(int id){
    this.id = id;
  }

  public void setRes(boolean b){
    this.res = b;
  }

  public static int getContador() {
  	return contador;
  }
  
  public static void setContador(int contador) {
  	Resena.contador = contador;
  }
  
  public Respuesta getRespuesta() {
  	return respuesta;
  }
  
  public void setRespuesta(Respuesta respuesta) {
  	this.respuesta = respuesta;
  }
  
  public boolean isRes() {
  	return res;
  }
  
}