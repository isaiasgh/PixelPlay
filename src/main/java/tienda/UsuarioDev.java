package tienda;
import com.poo.proyectosegundoparcial.consultas;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class UsuarioDev extends Usuario implements Serializable{
  private static final long serialVersionUID = 1L;
  private ArrayList<Videojuego> videojuegos;
  // se refiere a la cantidad de juegos que el usuario ha desarrollado
  private int cantJuegos = 0;

  public UsuarioDev(String nombre, String username, String mail, String password) {
    super(nombre, username, mail, password);
    videojuegos = new ArrayList<Videojuego>();
  }

    public UsuarioDev() {
    }

  public void publicarVj(String nombre, double precio, Categoria categoria, String codigo, double porcentaje, File portada) {
    Sistema sist = (Sistema) consultas.dameSistema();
    Videojuego vg = new Videojuego(this, nombre, categoria, precio, portada, sist.getInventario().size());
    videojuegos.add(vg);
    cantJuegos ++;
    
    Codigo cod = new Codigo(codigo, porcentaje);
    vg.getCodigos().add(cod);
    ArrayList<Videojuego> Inventario = sist.getInventario();
    Inventario.add(vg);
    sist.setInventario(Inventario);
    
    ArrayList<Usuario> lUsuarios = sist.getlUsuarios();
    
    int i = 0;
    
    for(Usuario usuario: lUsuarios){
        
        if(usuario.getUsername().equals(this.getUsername())){
            lUsuarios.set(i, this);
            sist.setlUsuarios(lUsuarios);
        }
        
        i++;
        
    }
    
    consultas.escribeSistema(sist);
  }
  
  public void publicarVj(String nombre, double precio, Categoria categoria, File portada) {
    Sistema sist = (Sistema) consultas.dameSistema();
    
    Videojuego vg = new Videojuego(this, nombre, categoria, precio, portada, sist.getInventario().size());
    videojuegos.add(vg);
    ArrayList<Videojuego> Inventario = sist.getInventario();
    Inventario.add(vg);
    
    sist.setInventario(Inventario);
    
    ArrayList<Usuario> lUsuarios = sist.getlUsuarios();
    
    int i = 0;
    
    for(Usuario usuario: lUsuarios){
        
        if(usuario.getUsername().equals(this.getUsername())){
            lUsuarios.set(i, this);
            sist.setlUsuarios(lUsuarios);
        }
        
        i++;
        
    }
    
    
    consultas.escribeSistema(sist);
  }

  public void anadirRespuesta(Respuesta respuesta1, Videojuego vg, Resena resena){
    resena.setRes(true);
    resena.setRespuesta(respuesta1);
    ArrayList<Resena> UR = this.getResenas();
    UR.add(respuesta1);
    this.setResenas(UR);
  }

  public static boolean existeCodigo(Videojuego vg, String codigo){
      boolean existe = false;
      for (Codigo c : vg.getCodigos()){
          if(codigo.equals(c.getCodigo())){
              existe = true;
          
          }
      
      }
      
      return existe;
  }
  
  public void crearCodigo(Videojuego vg, String codigo, Double descuento) {
        
        Sistema sist = consultas.dameSistema(); 
        ArrayList<Videojuego> inventario = sist.getInventario();
        
        ArrayList<Usuario> lUsuarios = sist.getlUsuarios();
        
        Codigo codigoNuevo = new Codigo(codigo, descuento);
        ArrayList<Codigo> codigosVgUser = vg.getCodigos();
        codigosVgUser.add(codigoNuevo);
        vg.setCodigos(codigosVgUser);
        
        int i = 0;
        
        for(Videojuego videojuego: inventario){
        
            if(videojuego.getId() == vg.getId()){
                inventario.set(i, vg);
                sist.setInventario(inventario);
            }

            i++;
        
        }
        
    i = 0;
    
    for(Usuario usuario: lUsuarios){
        
        if(usuario.getUsername().equals(this.getUsername())){
            lUsuarios.set(i, this);
            sist.setlUsuarios(lUsuarios);
        }
        
        i++;
        
    }
        
    
        consultas.escribeSistema(sist);
    }
  
    public void eliminarCodigo(Videojuego vg, String codigo){
      Sistema sist = consultas.dameSistema(); 
      ArrayList<Videojuego> inventario = sist.getInventario();

      ArrayList<Usuario> lUsuarios = sist.getlUsuarios();

      ArrayList<Codigo> codigosVgUser = vg.getCodigos();

      int i = 0;

      for(Codigo c: codigosVgUser){
          if(c.getCodigo().equals(codigo)){
              i = codigosVgUser.indexOf(c);
              
          }
      }

      codigosVgUser.remove(i);
      vg.setCodigos(codigosVgUser);

      i = 0;

      for(Videojuego videojuego: inventario){

          if(videojuego.getId() == vg.getId()){
              inventario.set(i, vg);
              sist.setInventario(inventario);
          }

          i++;

      }

      i = 0;

      for(Usuario usuario: lUsuarios){

          if(usuario.getUsername().equals(this.getUsername())){
              lUsuarios.set(i, this);
              sist.setlUsuarios(lUsuarios);
          }

          i++;

      }

      consultas.escribeSistema(sist);
  }
    
  public void listarVgpublicados() {
    if (!videojuegos.isEmpty()) {
      for (Videojuego vg : videojuegos) {
        System.out.println("");
        vg.dameDescripcionB();
      }
    } else {
      System.out.println("Aun no tienes videojuegos publicados :c");
    }
  }

  public void verResenas() {
    int ban = 0;
    for (Videojuego vg : videojuegos) {
      ArrayList<Resena> resenas = vg.getResenas();
      if (resenas.size() == 0) {
        System.out.println("\n" + vg.getNombre() + " no tiene reseñas\n");
      } else {
        ban = 1;
        System.out.println("Reseñas de " + vg.getNombre() + ": ");
        for (Resena res : resenas) {
          res.dameResena();
        }
      }
    }
    if (ban == 1) {
      Scanner entrada = new Scanner(System.in);
      this.responderResena(entrada);
    }
  }

  public void responderResena(Scanner entrada) {
    int opcion = 0;
    do {
      System.out.println("\n[1] Responder reseña");
      System.out.println("[2] Regresar");
      opcion = entrada.nextInt();
    } while (opcion > 2 || opcion < 1);

    switch (opcion) {
      case 1:
        boolean existe = false;
        System.out.print("ID: ");
        int id = entrada.nextInt();
        for (Videojuego vg : this.videojuegos) {
          for (Resena r : vg.getResenas()) {
            if (r.getId() == id) {
              if(r instanceof Respuesta){
                System.out.println("Error. No puedes responderte a ti mismo.");
              }
              else{
                System.out.print("Respuesta: ");
                entrada.nextLine();
                String respuesta = entrada.nextLine();
                Respuesta respuestaDev = new Respuesta(this, respuesta);
                System.out.println("Respuesta añadida exitosamente :D");
                r.setRes(true);
                r.setRespuesta(respuestaDev);
                ArrayList resenasDev = this.getResenas();
                resenasDev.add(respuestaDev);
                this.setResenas(resenasDev);
                ArrayList resenasVg = vg.getResenas();
                resenasVg.add(respuestaDev);
                vg.setResenas(resenasVg);
                existe = true;
                break;
              }
            }
          }
        }
        if (!existe) {
          System.out.println("Id incorrecto");
        }

        break;

      case 2:
        break;
    }
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