package Controlador;

import Modelado.Carrera;
import java.util.List;

public class Estadistica {

    private List<Carrera> carrerasBajoCupo;
    private List<Carrera> carrerasRechazadas;

    public Estadistica(List<Carrera> carrerasBajoCupo, List<Carrera> carrerasRechazadas) {
        this.carrerasBajoCupo = carrerasBajoCupo;
        this.carrerasRechazadas = carrerasRechazadas;
    }

    public List<Carrera> getCarrerasBajoCupo() {
        return this.carrerasBajoCupo;
    }

    public List<Carrera> getCarrerasRechazadas() {
        return this.carrerasRechazadas;
    }

    @Override
    public String toString() {
        return String.format("Carreras con bajo cupo: %d\nCarreras rechazadas: %d\n",
                this.carrerasBajoCupo.size(), this.carrerasRechazadas.size());
    }

}
