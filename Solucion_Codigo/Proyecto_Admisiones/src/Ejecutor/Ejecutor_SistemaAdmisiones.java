package Ejecutor;
import Modelado.Postulante;
import Modelado.Carrera;
import Controlador.Estadistica;
import java.util.ArrayList;
import java.util.Random;
import java.util.List;
import java.util.Formatter;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
public class Ejecutor_SistemaAdmisiones {

    static final LocalDate fechaIncio = LocalDate.of(2025, 04, 18);
    static final LocalDate fechaFin = LocalDate.of(2025, 05, 05);
    static final DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    static List<String> postulantesFueraDeFecha = new ArrayList<>();

    public static void main(String[] args) {
        ArrayList<Carrera> carrerasDisponibles = carreras();
        ArrayList<Postulante> postulantes = new ArrayList<>();
        cargarPostulantes(postulantes, carrerasDisponibles);

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
        try {
            Formatter salida = new Formatter(new File("Estadisticas.txt"));
            salida.format("=== Resultados de admisión por carrera ===\n");
            for (Carrera c : carrerasDisponibles) {
                salida.format("Carrera: %s\n", c.getNombre());
                salida.format("Admitidos:\n");
                for (Postulante p : c.getAdmitidos()) {
                    salida.format("- %s, Cédula: %s, Puntaje: %.2f\n", p.getNombre(), p.getCedula(), p.getPuntajeTotal());
                }
                salida.format("Total Admitidos: %d\n", c.getAdmitidos().size());
                salida.format("---------------\n");
            }

            salida.format("\n=== Estadísticas finales ===\n");
            salida.format("Carreras con menos del 50%% de cupos ocupados:\n");
            for (Carrera c : estadistica.getCarrerasBajoCupo()) {
                salida.format("- %s\n", c.getNombre());
            }

            salida.format("\nCarreras que rechazaron postulantes por falta de cupos:\n");
            for (Carrera c : estadistica.getCarrerasRechazadas()) {
                salida.format("- %s\n", c.getNombre());
            }
            salida.format("\n=== Postulantes rechazados por inscripción fuera de fecha ===\n");
            if (postulantesFueraDeFecha.isEmpty()) {
                salida.format("Ninguno\n");
            } else {
                for (String info : postulantesFueraDeFecha) {
                    salida.format("- %s\n", info);
                }
            }
            salida.close();
            System.out.println("Resultados guardados en Estadisticas.txt");

        } catch (FileNotFoundException e) {
            System.out.println("Error al crear archivo: " + e.getMessage());
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

    public static void cargarPostulantes(List<Postulante> postulantes, List<Carrera> carrerasDisponibles) {
        try {
            Scanner leer = new Scanner(new File("Postulantes.txt"));
            while (leer.hasNextLine()) {
                String linea = leer.nextLine();
                String datos[] = linea.split(";");
                if (datos.length >= 4) {
                    String nombre = datos[0];
                    String nombreCarrera = datos[1];
                    double puntaje = Double.parseDouble(datos[2]);
                    String tipoMerito = datos[3];
                    String fechaTexto = datos[4];
                    LocalDate fechaInscripcion = LocalDate.parse(fechaTexto, formatoFecha);
                    if (fechaInscripcion.isBefore(fechaIncio) || fechaInscripcion.isAfter(fechaFin)) {
                        postulantesFueraDeFecha.add(nombre + " (Carrera: " + nombreCarrera + ", Fecha: " + fechaTexto + ")");
                        continue;
                    }
                    Postulante nuevo = crearPostulante(nombre, nombreCarrera, puntaje, tipoMerito, carrerasDisponibles);;
                    if (nuevo != null) {
                        postulantes.add(nuevo);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public static ArrayList<Carrera> carreras() {
        ArrayList<Carrera> carreras = new ArrayList<>();
        try {
            Scanner leer = new Scanner(new File("Carreras.txt"));
            while (leer.hasNextLine()) {
                String linea = leer.nextLine();
                String datos[] = linea.split(";");
                if (datos.length >= 4) {
                    String nombre = datos[0];
                    int cupoTotal = Integer.parseInt(datos[1]);
                    int cupoDisponible = Integer.parseInt(datos[2]);
                    String tipoAdmision = datos[3];
                    carreras.add(new Carrera(nombre, cupoTotal, cupoDisponible, tipoAdmision));
                }
            }
            leer.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return carreras;
    }
}
