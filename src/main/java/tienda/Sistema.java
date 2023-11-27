package tienda;
import java.io.Serializable;
import java.util.Scanner;
import java.util.ArrayList;

public class Sistema implements Serializable{
  private static final long serialVersionUID = 1L;
  private ArrayList<Usuario> lUsuarios;
  private ArrayList<Videojuego> inventario;

  public Sistema() {
    lUsuarios = new ArrayList<Usuario>();
    inventario = new ArrayList<Videojuego>();
  }

  public boolean existeUsuario(String username){
    boolean esIgual = false;
    for (Usuario user : lUsuarios) {
        if(user.getUsername().equals(username)){
            esIgual = true;
        }
    }
    
    System.out.println(esIgual);
    return esIgual;   
  }
  
  public boolean existeUser(String username){
    boolean esIgual = false;
    for (Usuario user : lUsuarios) {
        if(user.getUsername().equals(username) || user.getMail().equals(username) ){
            esIgual = true;
        }
    }
    
    System.out.println(esIgual);
    return esIgual;   
  }

  public boolean existeMail(String mail){
    boolean esIgual = false;
    for (Usuario user : lUsuarios) {
        if(user.getMail().equals(mail)){
            esIgual = true;
        }
    }
    return esIgual;   
  }
  
  public void crearCuenta(String nombre, String correo, String password, String username, boolean esDesarrollador) {
      if(esDesarrollador){
        lUsuarios.add(new UsuarioDev(nombre, correo, password, username));
      }
      
      if(!esDesarrollador){
        lUsuarios.add(new Usuario(nombre, correo, password, username));
      }

  }
    
  public void restablecerPassword(Scanner entrada, Usuario user) {
    System.out.println("\u001B[35mRestablecer contraseña\u001B[0m");
    System.out.println("Por motivos de seguridad es necesario que vuelva a ingresar su username");
    System.out.print("Usuario: @");
    String username = "@" + entrada.nextLine();
    boolean encontrado = false;

    for (Usuario usuario : lUsuarios) {
      if (username.equals(usuario.getUsername())) {
        if(username.equals(user.getUsername())){
          System.out.println("E-Mail asociado: " + user.getMail());
          System.out.print("Ingrese la nueva contraseña: ");
          String password = entrada.next();
          user.setPassword(password);
          encontrado = true;
        }
      }
    }

    if (!encontrado) {
      System.out.println("\u001B[31mUsuario incorrecto\u001B[0m");
    }
  }
  
  public boolean restablecerPassword(String email, String username, String password){
      boolean encontrado = false;
      
      for(Usuario usuario: lUsuarios){
          if(usuario.getUsername().equals(username)){
              if(usuario.getMail().equals(email)){
                  usuario.setPassword(password);
                  encontrado = true;
              }
          }
      }
      
      return encontrado;

  }

  public Usuario iniciarSesion(String credencial, String password) {

    for (Usuario user : lUsuarios) {
      if (credencial.equals(user.getUsername()) || credencial.equals(user.getMail())) {
        if (password.equals(user.getPassword())) {
          System.out.println("exitoso");
          return user;
        }
      }
    }
    return null;
  }

  public void listarInventario(Scanner entrada) {
    System.out.println("\u001B[35mInventario de PixelPlay\u001B[0m");
    int opcion = 0;
    for (Videojuego vg : inventario) {
      vg.dameDescripcion();
    }
    while(opcion != 1){
      if(!(opcion==0)){
        System.out.println("\u001B[31mLa opcion ingresada no esta en el rango :c\u001B[0m");
      }
      System.out.println("[1] Regresar");
      boolean con;
      do{
        System.out.print("\u001B[32m▶ \u001B[0m");
        String opcion1 = entrada.nextLine();
        con=Validacion.ValNum(opcion1);
        if(con==true){
          opcion=Validacion.dameNum(opcion1);
        }
      } while(con==false);
    }
  }

  public void listarInventario(){
    System.out.println("\u001B[35mInventario de PixelPlay\u001B[0m");
    for (Videojuego vg : inventario) {
      vg.dameDescripcion();
    }
  }

  public ArrayList<Usuario> getlUsuarios() {
    return lUsuarios;
  }

  public Videojuego dameVg(int id) {
    for (Videojuego vg : inventario) {
      if (vg.getId() == id) {
        return vg;
      }
    }
    return null;
  }

  public void setlUsuarios(ArrayList<Usuario> lUsuarios) {
    this.lUsuarios = lUsuarios;
  }

  public ArrayList<Videojuego> getInventario() {
    return inventario;
  }

  public void setInventario(ArrayList<Videojuego> inventario) {
    this.inventario = inventario;
  }

}