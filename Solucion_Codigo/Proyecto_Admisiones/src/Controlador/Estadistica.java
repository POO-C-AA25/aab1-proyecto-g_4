
package Controlador;
import modelado.Carrera;
import java.util.List;
import java.util.ArrayList;
public class Estadistica {
    private List<Carrera> carrerasBajoCupo;
    private List<Carrera> carrerasRechazadas;

    public Estadistica(List<<any>> carrerasBajoCupo, List<Carrera> carrerasRechazadas) {
        this.carrerasBajoCupo = carrerasBajoCupo;
        this.carrerasRechazadas = carrerasRechazadas;
    }
    
}
