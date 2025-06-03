
package Modelado;

import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

public class Carrera {
    public String nombre;
    public String formaAdmision;
    public int puntajeMinimo;
    private double puntajeNivelacion;
    private int cupos;
    private ArrayList<Postulante> postulantes;
    private ArrayList<Postulante> admitidos;

    public Carrera(String nombre, int puntajeMinimo, int cupos, String formaAdmision) {
        this.nombre = nombre;
        this.puntajeMinimo = puntajeMinimo;
        this.cupos = cupos;
        this.formaAdmision = formaAdmision;
        this.postulantes = new ArrayList<>();
        this.admitidos = new ArrayList<>();
    }

    public void agregarPostulante(Postulante postulante) {
        postulantes.add(postulante);
    }

    public void validarAdmision() {
    if (this.formaAdmision.equalsIgnoreCase("Examen Admision")) {
        this.postulantes.stream()
            .filter(p -> p.calcularPuntajeTotal()>= this.puntajeMinimo)
            .sorted(Comparator.comparingDouble(Postulante::getPuntajeTotal).reversed())
            .limit(cupos)
            .forEach(admitidos::add);
    } else if (this.formaAdmision.equalsIgnoreCase("Diagnostico")) {
        this.postulantes.stream()
            .filter(p -> p.calcularPuntajeTotal()>= this.puntajeMinimo)
            .sorted(Comparator.comparingDouble(Postulante::getPuntajeTotal).reversed())
            .limit(cupos)
            .forEach(admitidos::add);
    }
}
    public void procesarAdmisiones() {
        validarAdmision();
    }

    public boolean mitadDeCupos() {
        return postulantes.size() < (this.cupos / 2.0);
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
    public int getCupos() { 
        return cupos; 
    }
    public ArrayList<Postulante> getPostulantes() { 
        return postulantes; 
    }
    public List<Postulante> getAdmitidos() { 
        return admitidos; 
    }

    @Override
    public String toString() {
        return "Carrera: " + nombre + ", Puntaje mínimo: " + puntajeMinimo +
               ", Cupos: " + cupos + ", Tipo de admisión: " + formaAdmision;
    }
}