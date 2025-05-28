package Ejecutor;

import Modelado.Postulante;
import Modelado.Carrera;
import Controlador.Estadistica;
import java.util.ArrayList;
import java.util.Random;
import java.util.List;

public class Ejecutor_SistemaAdmisiones {

    public static void main(String[] args) {
        ArrayList<Carrera> carrerasDisponibles = inicializarCarreras();
        ArrayList<Postulante> postulantes = new ArrayList<>();
        agregarPostulantesSimulados(postulantes, carrerasDisponibles);
        for (Postulante p : postulantes) {
            Carrera c = p.getCarreraDeseada();
            if (c != null) {
                c.agregarPostulante(p);
            }
        }
        for (Carrera c : carrerasDisponibles) {
            c.procesarAdmisiones();
        }
        List<Carrera> bajoCupo = new ArrayList<>();
        List<Carrera> rechazadas = new ArrayList<>();

        for (Carrera c : carrerasDisponibles) {
            if (c.mitadDeCupos()) {
                bajoCupo.add(c);
            }
            if (c.getPostulantes().size() > c.getAdmitidos().size()) {
                rechazadas.add(c);
            }
        }

        Estadistica estadistica = new Estadistica(bajoCupo, rechazadas);

        System.out.println("=== Resultados de admisión por carrera ===");
        for (Carrera c : carrerasDisponibles) {
            System.out.println("Carrera: " + c.getNombre());
            System.out.println("Admitidos:");
            for (Postulante p : c.getAdmitidos()) {
                System.out.printf("- %s, Cedula: %s, Puntaje: %.2f\n", p.getNombre(), p.getCedula(), p.getPuntajeTotal());
            }
            System.out.println("Total admitidos: " + c.getAdmitidos().size());
            System.out.println("---------------");
        }

        System.out.println("\n=== Estadísticas finales ===");
        System.out.println("Carreras con menos del 50% de cupos ocupados:");
        for (Carrera c : estadistica.getCarrerasBajoCupo()) {
            System.out.println("- " + c.getNombre());
        }

        System.out.println("\nCarreras que rechazaron postulantes por falta de cupos:");
        for (Carrera c : estadistica.getCarrerasRechazadas()) {
            System.out.println("- " + c.getNombre());
        }
    }

    public static String generarCedula() {
        Random random = new Random();
        StringBuilder cedula = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            cedula.append(random.nextInt(10));
        }
        return cedula.toString();
    }

    public static ArrayList<Carrera> inicializarCarreras() {
        ArrayList<Carrera> carreras = new ArrayList<>();
        carreras.add(new Carrera("Administracion de empresas", 60, 50, "Examen Admision"));
        carreras.add(new Carrera("Agropecuaria", 60, 50, "Examen Admision"));
        carreras.add(new Carrera("Alimentos", 60, 50, "Examen Admision"));
        carreras.add(new Carrera("Artes Escenicas", 60, 50, "Examen Admision"));
        carreras.add(new Carrera("Artes Visuales", 60, 50, "Examen Admision"));
        carreras.add(new Carrera("Biologia", 50, 50, "Examen Admision"));
        carreras.add(new Carrera("Contabilidad y Auditoria", 66, 50, "Examen Admision"));
        carreras.add(new Carrera("Derecho", 67, 50, "Examen Admision"));
        carreras.add(new Carrera("Economia", 60, 50, "Examen Admision"));
        carreras.add(new Carrera("Finanzas", 50, 50, "Examen Admision"));
        carreras.add(new Carrera("Geologia", 55, 50, "Examen Admision"));
        carreras.add(new Carrera("Ingenieria Ambiental", 50, 50, "Examen Admision"));
        carreras.add(new Carrera("Pedagogia de los idiomas Nacionales y Extranjeros", 55, 50, "Examen Admision"));
        carreras.add(new Carrera("Psicopedagogia", 50, 50, "Examen Admision"));
        carreras.add(new Carrera("Telecomunicaciones", 55, 50, "Examen Admision"));
        carreras.add(new Carrera("Arquitectura", 50, 0, "Examen Admision"));
        carreras.add(new Carrera("Bioquimica y Farmacia", 55, 0, "Examen Admision"));
        carreras.add(new Carrera("Computacion", 50, 0, "Examen Admision"));
        carreras.add(new Carrera("Enfermeria", 55, 0, "Examen Admision"));
        carreras.add(new Carrera("Fisioterapia", 50, 0, "Examen Admision"));
        carreras.add(new Carrera("Gastronomia", 55, 0, "Examen Admision"));
        carreras.add(new Carrera("Ingenieria Civil", 50, 0, "Examen Admision"));
        carreras.add(new Carrera("Ingenieria Industrial", 55, 0, "Examen Admision"));
        carreras.add(new Carrera("Ingenieria Quimica", 50, 0, "Examen Admision"));
        carreras.add(new Carrera("Medicina", 55, 0, "Examen Admision"));
        carreras.add(new Carrera("Nutricion y Dietética", 50, 0, "Examen Admision"));
        carreras.add(new Carrera("Psicologia", 55, 0, "Examen Admision"));
        carreras.add(new Carrera("Psicologia Clinica", 50, 50, "Examen Admision"));
        return carreras;
    }

    public static void agregarPostulantesSimulados(List<Postulante> lista, List<Carrera> carreras) {
        lista.add(crearPostulante("Jose Gualan", "Administracion de empresas", 80, "Abanderado", carreras));
        lista.add(crearPostulante("Juan Flores", "Agropecuaria", 70, null, carreras));
        lista.add(crearPostulante("Luis Jumbo", "Computacion", 95, "Bachillerato Afin", carreras));
        lista.add(crearPostulante("Diego Guaman", "Derecho", 70, "Capacidad especial", carreras));
        lista.add(crearPostulante("Jesus Rivas", "Psicologia", 65, null, carreras));
    }

    public static Postulante crearPostulante(String nombre, String carreraNombre, double puntaje, String merito, List<Carrera> carreras) {
        Carrera carrera = buscarCarreraPorNombre(carreraNombre, carreras);
        Postulante p = new Postulante(nombre, generarCedula(), carrera);
        p.setPuntajeExamen(puntaje);
        p.setTipoMerito(merito);
        return p;
    }

    public static Carrera buscarCarreraPorNombre(String nombre, List<Carrera> carreras) {
        for (Carrera c : carreras) {
            if (c.getNombre().equalsIgnoreCase(nombre)) {
                return c;
            }
        }
        return null;
        }
}
