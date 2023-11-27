package tienda;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Videojuego implements Serializable{
    private static final long serialVersionUID = 1L;
    private int id;
    private UsuarioDev desarrollador;
    private Date fechaLanzamiento;
    private String nombre;
    private Categoria categoria;
    private double precio;
    private int cantDescargas = 0;
    private ArrayList<Codigo> codigos;
    private ArrayList<Resena> resenas;
    private File portada;

  public Videojuego(){
    
  }
  
  public Videojuego(UsuarioDev user, String nombre, Categoria categoria, double precio, File portada, int id){
    this.id = id;
    desarrollador = user;
    fechaLanzamiento = new Date();
    this.nombre = nombre;
    this.categoria = categoria;
    this.precio = precio;
    codigos = new ArrayList<Codigo>();
    resenas = new ArrayList<Resena>();
    this.portada = portada;

    // el contador irá aumentando a medida que se ingrese un videojuego
    }

  // descripción para inventario
  public void dameDescripcion(){
    System.out.println("\nID: " + id);
    System.out.println("Desarrollador: " + desarrollador.getUsername());
    System.out.println("Nombre: " + nombre);
    System.out.println("Categoría: " + categoria);
    System.out.println("Precio: $" + precio);
    System.out.println("Cantidad de descargas: " + cantDescargas + "\n");
  }

  // descripción para biblioteca
  public void dameDescripcionB(){
    System.out.println("ID: " + id);
    System.out.println("Nombre: " + nombre);
    System.out.println("Desarrollador: " + desarrollador.getUsername());
    System.out.println("Categoría: " + categoria);
  }
  
  
  public static void sumarDescargas(Videojuego vg){
    vg.cantDescargas ++;
      System.out.println("cant descargas nuevas: " + vg.cantDescargas);
  }
  
  public void agregarResena(Resena resena){
    resenas.add(resena);
  }
  
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public UsuarioDev getDesarrollador() {
		return desarrollador;
	}

	public void setDesarrollador(UsuarioDev desarrollador) {
		this.desarrollador = desarrollador;
	}

	public Date getFechaLanzamiento() {
		return fechaLanzamiento;
	}

	public void setFechaLanzamiento(Date fechaLanzamiento) {
		this.fechaLanzamiento = fechaLanzamiento;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public int getCantDescargas() {
		return cantDescargas;
	}

	public void setCantDescargas(int cantDescargas) {
		this.cantDescargas = cantDescargas;
	}

	public ArrayList<Codigo> getCodigos() {
		return codigos;
	}

	public void setCodigos(ArrayList<Codigo> codigos) {
		this.codigos = codigos;
	}

	public ArrayList<Resena> getResenas() {
		return resenas;
	}

	public void setResenas(ArrayList<Resena> resenas) {
		this.resenas = resenas;
	}
        
        public File getPortada(){
            return portada;
        }
 
}