package tienda;
import com.poo.proyectosegundoparcial.consultas;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class Usuario implements Serializable{
  private static final long serialVersionUID = 1L;
  private String nombre;
  private String username;
  private String mail;
  private String password;
  private Biblioteca biblioteca;
  private Wallet wallet;
  private ArrayList<Resena> resenas;
  private ArrayList<Usuario> lSeguidos;
  private ArrayList<Usuario> lSeguidores;

  public Usuario(){
    
  }
  
  public Usuario(String nombre, String username, String mail, String password) {
    this.nombre = nombre;
    this.username = "@" + username;
    this.mail = mail;
    this.password = password;
    biblioteca = new Biblioteca();
    wallet = new Wallet();
    resenas = new ArrayList<Resena>();
    lSeguidos = new ArrayList<Usuario>();
    lSeguidores = new ArrayList<Usuario>();
  }
  
  public void listarSeguidos(){
    if(!(lSeguidos.size()==0)){
      System.out.print("\n");
      System.out.println("\u001B[35mUsuarios que sigues: \u001B[0m");
      for (Usuario user:lSeguidos){
        System.out.println(user.getUsername());
      }
    }else{
      System.out.println("\nNo haz seguido a ningun usuario");
    }
  }
 
  public void listarSeguidores(){
    if(!(lSeguidores.size()==0)){
      System.out.print("\n");
      System.out.println("\u001B[35mSeguidores: \u001B[0m");
      for (Usuario user:lSeguidores){
        System.out.println(user.getUsername());
      }
    }else{
      System.out.println("\nNo tienes ningun seguidor");
    }
  }
  
  /*
  public void regalarVg(Scanner entrada, String user){
    boolean encontrado = false;
    int opcion = 0;
    int id = 0;
    Sistema sist = (Sistema) consultas.dameSistema();
    for(Usuario u: lSeguidos){
      String usuarioAmigo = u.getUsername();
      if(user.equals(usuarioAmigo)){
        encontrado = true;
        // Sistema.listarInventario();
        System.out.println("Selecciona el videojuego a regalar");
        System.out.print("ID: ");
        boolean con;
        do{
          System.out.print("\u001B[32m▶ \u001B[0m");
          String opcion1 = entrada.nextLine();
          con=Validacion.ValNum(opcion1);
             if(con==true){
            id=Validacion.dameNum(opcion1);
            }
        } while(con==false);
        
        // si u no tiene vg
        if(!u.tieneVg(id)){

          ArrayList<Videojuego> biblUsuer = u.getBiblioteca().getVideojuegos();
          Videojuego vg = sist.dameVg(id);
          double PrecioVg = vg.getPrecio();
          
          // validamos si para el videojuego existen codigos de descuento
          if(vg.getCodigos().size() != 0){
            boolean existeCodigo = false;
            System.out.println("¿Tienes un código de descuento?");
            System.out.println("[1] Sí");
            System.out.println("[2] No");
            System.out.print("\u001B[32m▶ \u001B[0m");

            do{
              System.out.print("\u001B[32m▶ \u001B[0m");
              String opcion1 = entrada.nextLine();
              con=Validacion.ValNum(opcion1);
              if(con==true){
                opcion=Validacion.dameNum(opcion1);
              }
            } while(con==false);
  
            // solicitamos el codigo
            if(opcion == 1){
              System.out.print("Ingresa el código: ");
              String codigo = entrada.nextLine();
  
              // validamos que el codigo sea correcto
              for(Codigo c: vg.getCodigos()){
                if(codigo.equals(c.getCodigo())){
                  System.out.println("Código encontrado");
                  PrecioVg -= PrecioVg * c.getDscPorcentaje();
                  System.out.println("Nuevo Valor de " + vg.getNombre() + ": $" + PrecioVg);
                  existeCodigo = true;
                }
              }
  
              if(!existeCodigo){
                System.out.println("Codigo no encontrado");
                System.out.println("¿Desea continuar?");
                System.out.println("[1] Sí");
                System.out.println("[2] No");
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
          }
          
          if(wallet.getSaldo() >= PrecioVg){
            // actualizamos el saldo del cliente
            Double saldoNuevo = wallet.getSaldo() - PrecioVg;
            wallet.setSaldo(saldoNuevo);
    
            // actualizamos el saldo del desarrollador
            UsuarioDev desarrollador = vg.getDesarrollador();
            Wallet w = desarrollador.getWallet();
            double saldoDev = w.getSaldo() + PrecioVg;
            w.setSaldo(saldoDev);
            desarrollador.setWallet(w);
            
            // se completa la transacción
            System.out.println("Compra realizada exitosamente");
            this.consultarSaldo();
            Videojuego.sumarDescargas(vg);
  
            // agregamos el videojuego a la biblioteca del usuario
            int nJuegos = u.getBiblioteca().getCantJuegos() + 1;
            biblUsuer.add(vg);
            (u.getBiblioteca()).setCantJuegos(nJuegos);
            u.getBiblioteca().setVideojuegos(biblUsuer);
          
            
            encontrado = true;
            break;
            
          }else{
            System.out.println("Saldo insuficiente :(");
            encontrado = true;
          }
        }
        
        // si u tiene vg
        if(u.tieneVg(id)){
          System.out.println("El usuario " + u.getUsername() + " ya tiene " + u.getBiblioteca().dameNombreVg(id) + " en su biblioteca");
        }
      }
    }
    if(!encontrado){
      System.out.println("No sigues a " + user + ". Síguelo para poder interactuar con él y enviarle regalos");
    }
  }*/
  
    
  
    public void agregarSaldo(double monto) {
        Sistema sist = consultas.dameSistema();

        double saldoActual = wallet.getSaldo(); 
        wallet.setSaldo(monto+saldoActual);

        ArrayList<Usuario> lUsuarios = sist.getlUsuarios();

        int i = 0;

        for(Usuario user: lUsuarios){

            if(user.getUsername().equals(this.getUsername())){
                lUsuarios.set(i, this);
                sist.setlUsuarios(lUsuarios);
            }

            i++;

        }

        consultas.escribeSistema(sist);
  }

    public boolean tieneVg(Videojuego vg){  
        for(Videojuego videojuego: this.biblioteca.getVideojuegos()){
          if(vg.getId() == videojuego.getId()){
            return true;

          }
        }
        return false;
  }
    
    
    // regalar sin codigo
    public void regalarVg(Videojuego vg, Usuario user){
        Sistema sist = (Sistema) consultas.dameSistema();    

        ArrayList<Videojuego> inventario = sist.getInventario();
        ArrayList<Usuario> lUsuarios = sist.getlUsuarios();
        
        ArrayList<Videojuego> VideojuegosUsuario = user.getBiblioteca().getVideojuegos();
        double PrecioVg = vg.getPrecio();

        // actualizamos el saldo del desarrollador
        UsuarioDev desarrollador = vg.getDesarrollador();
        Wallet w = desarrollador.getWallet();
        
        if(!this.username.equals(desarrollador.getUsername())){
            double saldoDev = w.getSaldo() + PrecioVg;
            w.setSaldo(saldoDev);
            desarrollador.setWallet(w);

            // actualizamos el saldo del usuario
            double saldo = wallet.getSaldo();
            wallet.setSaldo(saldo - PrecioVg);
        }
        
        Videojuego.sumarDescargas(vg);

        // agregamos el videojuego a la biblioteca del usuario
        VideojuegosUsuario.add(vg);
        user.biblioteca.setVideojuegos(VideojuegosUsuario);
        
        int nJuegos = user.biblioteca.getCantJuegos() + 1;
        user.biblioteca.setCantJuegos(nJuegos);   
                
        int i = 0;
                
        for(Videojuego videojuego: inventario){
        
            if(videojuego.getId() == vg.getId()){
                inventario.set(i, vg);
                sist.setInventario(inventario);
            }

            i++;
        
        }
        
        i = 0;
            
        if(this.username.equals(desarrollador.getUsername())) {
            
            for(Usuario usuario: lUsuarios){

                if(usuario.getUsername().equals(desarrollador.getUsername())){
                    lUsuarios.set(i, this);
                }

                i++;

            }
            
            i = 0;
            
            for(Usuario usuario: lUsuarios){
            
                user.biblioteca.setVideojuegos(VideojuegosUsuario);
                
                if(usuario.getUsername().equals(user.getUsername())){
                    lUsuarios.set(i, user);

                }
                
                i++;
                
            }
        }


        if(!this.username.equals(desarrollador.getUsername())) {
            
            i = 0;

            for(Usuario usuario: lUsuarios){

                if(usuario.getUsername().equals(this.getUsername())){
                    lUsuarios.set(i, this);
                }

                i++;

            }
        
            
            i = 0;
            
            if(desarrollador.getUsername().equals(user.getUsername())){
                user = desarrollador;
                user.biblioteca.setVideojuegos(VideojuegosUsuario);

                for(Usuario usuario: lUsuarios){

                    if(usuario.getUsername().equals(desarrollador.getUsername())){
                        lUsuarios.set(i, user);
                    }

                    i++;

                }
            
            
            }
            
            i = 0;
            
            if(!desarrollador.getUsername().equals(user.getUsername())){
                user.biblioteca.setVideojuegos(VideojuegosUsuario);
                
                for(Usuario usuario: lUsuarios){

                    if(usuario.getUsername().equals(desarrollador.getUsername())){
                        lUsuarios.set(i, desarrollador);
                    }

                    i++;

                }

                i = 0;

                for(Usuario usuario: lUsuarios){

                    if(usuario.getUsername().equals(user.getUsername())){
                        lUsuarios.set(i, user);
                    }

                    i++;

                }
            }
            
        }
        
       sist.setlUsuarios(lUsuarios);
       consultas.escribeSistema(sist);
    
    }
  
    public void regalarVg(Videojuego vg, Usuario user, double PrecioVg){
        Sistema sist = (Sistema) consultas.dameSistema();    

        ArrayList<Videojuego> inventario = sist.getInventario();
        ArrayList<Usuario> lUsuarios = sist.getlUsuarios();
        
        ArrayList<Videojuego> VideojuegosUsuario = user.getBiblioteca().getVideojuegos();

        // actualizamos el saldo del desarrollador
        UsuarioDev desarrollador = vg.getDesarrollador();
        Wallet w = desarrollador.getWallet();
        
        if(!this.username.equals(desarrollador.getUsername())){
            double saldoDev = w.getSaldo() + PrecioVg;
            w.setSaldo(saldoDev);
            desarrollador.setWallet(w);

            // actualizamos el saldo del usuario
            double saldo = wallet.getSaldo();
            wallet.setSaldo(saldo - PrecioVg);
        }
        
        Videojuego.sumarDescargas(vg);

        // agregamos el videojuego a la biblioteca del usuario
        VideojuegosUsuario.add(vg);
        user.biblioteca.setVideojuegos(VideojuegosUsuario);
        
        int nJuegos = user.biblioteca.getCantJuegos() + 1;
        user.biblioteca.setCantJuegos(nJuegos);   
                
        int i = 0;
                        
        vg.setDesarrollador(desarrollador);
        
        for(Videojuego videojuego: inventario){
        
            if(videojuego.getId() == vg.getId()){
                inventario.set(i, vg);
                sist.setInventario(inventario);
            }

            i++;
        
        }
                    
        i = 0;

        if(this.username.equals(desarrollador.getUsername())) {

            for(Usuario usuario: lUsuarios){

                if(usuario.getUsername().equals(desarrollador.getUsername())){
                    lUsuarios.set(i, this);
                }

                i++;

            }
            
            i = 0;
            
            for(Usuario usuario: lUsuarios){
            
                if(usuario.getUsername().equals(user.getUsername())){
                    lUsuarios.set(i, user);

                }
                
                i++;
                
            }
        }


        if(!this.username.equals(desarrollador.getUsername())) {
            
            i = 0;

            for(Usuario usuario: lUsuarios){

                if(usuario.getUsername().equals(this.getUsername())){
                    lUsuarios.set(i, this);
                }

                i++;

            }
        
            
            i = 0;
            
            if(desarrollador.getUsername().equals(user.getUsername())){
                
                user.biblioteca.setVideojuegos(VideojuegosUsuario);

                for(Usuario usuario: lUsuarios){

                    if(usuario.getUsername().equals(desarrollador.getUsername())){
                        lUsuarios.set(i, user);
                    }

                    i++;

                }
            
            
            }
            
            i = 0;
            
            if(!desarrollador.getUsername().equals(user.getUsername())){
                for(Usuario usuario: lUsuarios){

                    if(usuario.getUsername().equals(desarrollador.getUsername())){
                        lUsuarios.set(i, desarrollador);
                    }

                    i++;

                }

                i = 0;

                for(Usuario usuario: lUsuarios){

                    if(usuario.getUsername().equals(user.getUsername())){
                        lUsuarios.set(i, user);
                    }

                    i++;

                }
            }
            
        }
        
       sist.setlUsuarios(lUsuarios);
       consultas.escribeSistema(sist); 
        
    }
    
    // ya sabemos que sí tiene el dinero suficiente
    // sin codigo
    public void comprarVg(Videojuego vg) {
        Sistema sist = (Sistema) consultas.dameSistema();    

        ArrayList<Videojuego> inventario = sist.getInventario();
        ArrayList<Usuario> lUsuarios = sist.getlUsuarios();
        
        ArrayList<Videojuego> VideojuegosUsuario = biblioteca.getVideojuegos();
        double PrecioVg = vg.getPrecio();

        // actualizamos el saldo del desarrollador
        UsuarioDev desarrollador = vg.getDesarrollador();
        Wallet w = desarrollador.getWallet();
        
        if(!this.username.equals(desarrollador.getUsername())){
            double saldoDev = w.getSaldo() + PrecioVg;
            w.setSaldo(saldoDev);
            desarrollador.setWallet(w);

            // actualizamos el saldo del usuario
            double saldo = wallet.getSaldo();
            wallet.setSaldo(saldo - PrecioVg);
        }
        
        Videojuego.sumarDescargas(vg);

        // agregamos el videojuego a la biblioteca del usuario\
        VideojuegosUsuario.add(vg);
        biblioteca.setVideojuegos(VideojuegosUsuario);
        
        int nJuegos = biblioteca.getCantJuegos() + 1;
        biblioteca.setCantJuegos(nJuegos);   
                
        int i = 0;
        
        vg.setDesarrollador(desarrollador);
        
        for(Videojuego videojuego: inventario){
        
            if(videojuego.getId() == vg.getId()){
                inventario.set(i, vg);
                sist.setInventario(inventario);
            }

            i++;
        
        }
            
        i = 0;

        if(this.username.equals(desarrollador.getUsername())) {

            this.biblioteca.setVideojuegos(VideojuegosUsuario);

            for(Usuario usuario: lUsuarios){

                if(usuario.getUsername().equals(desarrollador.getUsername())){
                    lUsuarios.set(i, this);
                }

                i++;

            }

        }

        i = 0;

        if(!this.username.equals(desarrollador.getUsername())) {
            for(Usuario usuario: lUsuarios){

                if(usuario.getUsername().equals(desarrollador.getUsername())){
                    lUsuarios.set(i, desarrollador);
                }

                i++;

            }

            i = 0;

            for(Usuario usuario: lUsuarios){

                if(usuario.getUsername().equals(this.getUsername())){
                    lUsuarios.set(i, this);
                }

                i++;

            }
        }
        
        sist.setlUsuarios(lUsuarios);
        consultas.escribeSistema(sist);
    
    }
   
    // comprar videojuego, pero con codigo
    public void comprarVg(Videojuego vg, double PrecioVg) {
        Sistema sist = (Sistema) consultas.dameSistema();    

        ArrayList<Videojuego> inventario = sist.getInventario();
        ArrayList<Usuario> lUsuarios = sist.getlUsuarios();
        
        ArrayList<Videojuego> VideojuegosUsuario = biblioteca.getVideojuegos();

        // actualizamos el saldo del desarrollador
        UsuarioDev desarrollador = vg.getDesarrollador();
        Wallet w = desarrollador.getWallet();
        
        if(!this.username.equals(desarrollador.getUsername())){
            double saldoDev = w.getSaldo() + PrecioVg;
            w.setSaldo(saldoDev);
            desarrollador.setWallet(w);

            // actualizamos el saldo del usuario
            double saldo = wallet.getSaldo();
            wallet.setSaldo(saldo - PrecioVg);
        }
        
        Videojuego.sumarDescargas(vg);

        // agregamos el videojuego a la biblioteca del usuario\
        VideojuegosUsuario.add(vg);
        biblioteca.setVideojuegos(VideojuegosUsuario);
        
        int nJuegos = biblioteca.getCantJuegos() + 1;
        biblioteca.setCantJuegos(nJuegos);   
                
        int i = 0;
        
        vg.setDesarrollador(desarrollador);
        
        for(Videojuego videojuego: inventario){
        
            if(videojuego.getId() == vg.getId()){
                inventario.set(i, vg);
                sist.setInventario(inventario);
            }

            i++;
        
        }
            
    i = 0;
    
    if(this.username.equals(desarrollador.getUsername())) {
        
        this.biblioteca.setVideojuegos(VideojuegosUsuario);
        
        for(Usuario usuario: lUsuarios){

            if(usuario.getUsername().equals(desarrollador.getUsername())){
                lUsuarios.set(i, this);
            }

            i++;

        }
          
    }
    
    i = 0;
    
    if(!this.username.equals(desarrollador.getUsername())) {
        for(Usuario usuario: lUsuarios){

            if(usuario.getUsername().equals(desarrollador.getUsername())){
                lUsuarios.set(i, desarrollador);
            }

            i++;

        }

        i = 0;

        for(Usuario usuario: lUsuarios){

            if(usuario.getUsername().equals(this.getUsername())){
                lUsuarios.set(i, this);
            }

            i++;

        }
    }

       sist.setlUsuarios(lUsuarios);
       consultas.escribeSistema(sist);    
    }

    public double damePorcentaje(String codigo, Videojuego vg){
        double descuento = 0;
        Sistema sist = consultas.dameSistema();
        int id = vg.getId();
        
        for (Videojuego v: sist.getInventario()) {
            if (v.getId() == id){
                for (Codigo c: v.getCodigos()) {
                    if (c.getCodigo().equals(codigo)) {
                        descuento = c.getDscPorcentaje();
                    }
                
                }
            }
        }
        return descuento;
    }
    
    public void listarBiblioteca() {
    System.out.println("Cantidad Total de Videojuegos: " + biblioteca.getCantJuegos());
    for (Videojuego vg : biblioteca.getVideojuegos()) {
      System.out.print("\n");
      vg.dameDescripcionB();
    }

  }
  
    public void crearResenas(Videojuego videojuego, String comentario){
        Sistema sist = consultas.dameSistema();
        
        Resena resena = new Resena(this, comentario);
        resenas.add(resena);
        videojuego.agregarResena(resena);
        resena.setVideojuego(videojuego);
        
        ArrayList<Videojuego> inventario = sist.getInventario();
        ArrayList<Usuario> lUsuarios = sist.getlUsuarios();
        UsuarioDev desarrollador = videojuego.getDesarrollador();
        
        int i = 0;
        
        for(Videojuego v: inventario){
        
            if(v.getId() == videojuego.getId()){
                inventario.set(i, videojuego);
                sist.setInventario(inventario);
                
            }

            i++;
        
        }
      
        i = 0;
        
        for(Usuario user: lUsuarios){
        
            if(user.getUsername().equals(this.getUsername())){
                lUsuarios.set(i, this);
                sist.setlUsuarios(lUsuarios);
            }

            i++;
        
        }
        
        i = 0;
        
        ArrayList<Videojuego> videojuegos = desarrollador.getVideojuegos();
        
        for(Videojuego v: videojuegos){
            if(v.getId() == videojuego.getId()){
                videojuegos.set(i, videojuego);
            }
        
            i++;
        }

        desarrollador.setVideojuegos(videojuegos);
        
        i = 0;
        
        for(Usuario user: lUsuarios){
        
            if(user.getUsername().equals(desarrollador.getUsername())){
                lUsuarios.set(i, desarrollador);
                sist.setlUsuarios(lUsuarios);
            }

            i++;
        
        }        
        consultas.escribeSistema(sist);
        
    }

    public void agregarSeguidor(String username){
        Sistema sist = consultas.dameSistema();
        ArrayList<Usuario> lUsuarios = sist.getlUsuarios();
        
        for(Usuario user:lUsuarios){
            if(user.getUsername().equals(username)){
                this.lSeguidores.add(user);
                
            }
        }
    }
  
    public void quitarSeguidor(String nom1, String userNoSeguir){
        Sistema sist = consultas.dameSistema();
        ArrayList<Usuario> lUsuarios = sist.getlUsuarios();
        
        for (Usuario user:lUsuarios){
            if(user.getUsername().equals(nom1)){
                this.lSeguidores.remove(user);
            }
        }
  }
  
    public void seguirUser(String userSeguir){
        Sistema sist = consultas.dameSistema();
        
        ArrayList<Usuario> lUsuarios = sist.getlUsuarios();
        Usuario otroUsuario = new Usuario();

        for (Usuario user:lUsuarios){
            
            if(userSeguir.equals(user.getUsername())){
                lSeguidos.add(user);
                user.lSeguidores.add(this);
                otroUsuario = user;
            }
        }
        
        // guardamos nuestro user, y el user al que seguimos
        
        int i = 0;
        
        for(Usuario user: lUsuarios){
        
            if(user.getUsername().equals(this.getUsername())){
                lUsuarios.set(i, this);
                sist.setlUsuarios(lUsuarios);
            }
        
            i++;
        
        } 
        
        i = 0;
        
        for(Usuario user: lUsuarios){
        
            if(user.getUsername().equals(otroUsuario.getUsername())){
                lUsuarios.set(i, otroUsuario);
                sist.setlUsuarios(lUsuarios);
            }
        
            i++;
        
        } 
        
        consultas.escribeSistema(sist);
        
    }

    public void dejarSeguir(String username){
        Sistema sist = consultas.dameSistema();
        
        ArrayList<Usuario> lUsuarios = sist.getlUsuarios();
        Usuario otroUsuario = new Usuario();
        
        for (Usuario user:lSeguidos){
            
            if(user.getUsername().equals(username)){
                this.lSeguidos.remove(user);
                user.lSeguidores.remove(this);
                otroUsuario = user;  
                
                break;
            }
        }
        
        // guardamos nuestro user, y el user al que seguimos
        
        int i = 0;
        
        for(Usuario user: lUsuarios){
        
            if(user.getUsername().equals(this.getUsername())){
                lUsuarios.set(i, this);
                sist.setlUsuarios(lUsuarios);
            }
        
            i++;
        
        } 
        
        i = 0;
        
        for(Usuario user: lUsuarios){
        
            if(user.getUsername().equals(otroUsuario.getUsername())){
                lUsuarios.set(i, otroUsuario);
                sist.setlUsuarios(lUsuarios);
            }
        
            i++;
        
        } 
        
        consultas.escribeSistema(sist);
               
    }

  public void agregarJuegoComprado(Videojuego vg){
    ArrayList<Videojuego> listaV = this.biblioteca.getVideojuegos();
    listaV.add(vg);
    this.biblioteca.setVideojuegos(listaV);
    int descargas = vg.getCantDescargas();
    vg.setCantDescargas(descargas + 1);
  }

  public void seguir(Usuario user){
    this.lSeguidos.add(user);
    ArrayList<Usuario> seguidores = user.getlSeguidores();
    seguidores.add(this);
    user.setlSeguidores(seguidores);
  }

  public void anadirResena(Resena resenaN, Videojuego vg, String nombreVG){
    this.resenas.add(resenaN);
    ArrayList<Resena> res = vg.getResenas();
    //resenaN.setNom_Vg(nombreVG); arreglar esto, ahora usamos videojuego no string
    res.add(resenaN);
    vg.setResenas(res);
  }
  
  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getMail() {
    return mail;
  }

  public void setMail(String mail) {
    this.mail = mail;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Biblioteca getBiblioteca() {
    return biblioteca;
  }

  public void setBiblioteca(Biblioteca biblioteca) {
    this.biblioteca = biblioteca;
  }

  public Wallet getWallet() {
    return wallet;
  }

  public void setWallet(Wallet wallet) {
    this.wallet = wallet;
  }

  public ArrayList<Resena> getResenas() {
    return resenas;
  }

  public void setResenas(ArrayList<Resena> resenas) {
    this.resenas = resenas;
  }

  public ArrayList<Usuario> lSeguidos() {
    return lSeguidos;
  }

  public void setlSeguidos(ArrayList<Usuario> lSeguidos) {
    this.lSeguidos = lSeguidos;
  }

  public ArrayList<Usuario> getlSeguidores() {
    return lSeguidores;
  }

  public void setlSeguidores(ArrayList<Usuario> lSeguidores) {
    this.lSeguidores = lSeguidores;
  }

  public ArrayList<Usuario> getlSeguidos() {
  	return lSeguidos;
  }
  
}