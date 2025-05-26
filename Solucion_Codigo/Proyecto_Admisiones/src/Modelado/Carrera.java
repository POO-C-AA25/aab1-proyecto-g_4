
package Modelado;
import java.util.ArrayList;
import Controlador.Estadistica;
import java.util.List;
import java.util.Comparator;

public class Carrera {
    private String nombre;
    private String formaAdmision;
    private double puntajeMinimo;
    private double puntajeNivelacion;
    private int cupos;
    private ArrayList<Postulante> postulantes;
    private ArrayList<Postulante> admitidos;

    public Carrera(String nombre, String formaAdmision, double puntajeMinimo, double puntajeNivelacion, int cupos) {
        this.nombre = nombre;
        this.formaAdmision = formaAdmision;
        this.puntajeMinimo = puntajeMinimo;
        this.puntajeNivelacion = puntajeNivelacion;
        this.cupos = cupos;
        this.postulantes = new ArrayList();
        this.admitidos = new ArrayList();
    }
    public void agregarPostulante(Postulante postulante){
        postulantes.add(postulante);
    }
    public void validarAdmision(){
        if (this.formaAdmision.equalsIgnoreCase("Examen Admision")){
            this.postulantes.stream()
            .filter(p -> p.getPuntajeTotal() > this.puntajeMinimo)
            .sorted(Comparator.comparingDouble(Postulante :: getPuntajeTotal).reversed())
            .limit(cupos)
            .forEach(admitidos :: add);    
    }else if (this.formaAdmision.equalsIgnoreCase("Diagnostico")) {
        postulantes.forEach(admitidos :: add);
    }
    }
    public void procesarAdmsiones(){
        validarAdmision();
    }
    public boolean mitadDeCupos(){
        return postulantes.size() < (this.cupos / 2.0);
    }
    public void inscribirPostulante(Postulante postulante){
        agregarPostulante(postulante);
    }
    public Estadistica generarEstadisiticas(){
        List<Carrera> bajoCupo = new ArrayList<>();
        List<Carrera> rechazadas = new ArrayList<>();
        if (mitadDeCupos()) bajoCupo.add(this);
        if (this.formaAdmision.equalsIgnoreCase("Examen Admision") && postulantes.size() > admitidos.size()){
            rechazadas.add(this);
        }
        return new Estadistica(bajoCupo, rechazadas);
    }
    public String getNombre() { 
        return nombre; 
    }
    public String getFormaAdmision() { 
        return formaAdmision; 
    }
    public double getPuntajeMinimo() { 
        return puntajeMinimo; 
    }
    public double getPuntajeNivelacion() { 
        return puntajeNivelacion; 
    }
    public List<Postulante> getAdmitidos() { 
        return admitidos; 
    }
}
