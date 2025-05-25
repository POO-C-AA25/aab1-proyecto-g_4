
package Modelado;
import java.util.ArrayList;
import java.util.stream.*;
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
    
}
