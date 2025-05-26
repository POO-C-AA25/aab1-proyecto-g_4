
package Modelado;
public class Postulante {
    private String nombre;
    private long cedula;
    private Carrera carreraDeseada;
    private double puntajeExamen;
    private boolean nesecitaNivelacion;
    private int puntajeMerito;
    private String tipoMerito;
    public Postulante(String nombre, long cedula, Carrera carreraDeseada){
        this.nombre = nombre;
        this.cedula = cedula;
        this.carreraDeseada = carreraDeseada;
    }

    public void setPuntajeExamen(double puntajeExamen) {
        this.puntajeExamen = puntajeExamen;
    }

    public void setTipoMerito(String tipoMerito) {
        this.tipoMerito = tipoMerito;
    }

    public long getCedula() {
        return cedula;
    }

    public Carrera getCarreraDeseada() {
        return carreraDeseada;
    }

    public double getPuntajeExamen() {
        return puntajeExamen;
    }
    public double getPuntajeTotal(){
        return calcularPuntajeTotal();
    }
    public double calcularMeritos(){
        if (this.tipoMerito == null){
            if (this.tipoMerito.equalsIgnoreCase("Abanderado")){
                this.puntajeMerito += 5;
            }else if (this.tipoMerito.equalsIgnoreCase("Bachillerato Afin")){
                this.puntajeMerito += 2;
            }else if (this.tipoMerito.equalsIgnoreCase("Capacidad especial")){
                this.puntajeMerito += 3;
            }
        }
        return this.puntajeMerito;
    }
    public double calcularPuntajeTotal(){
        return this.puntajeExamen + calcularMeritos();
    }
    public boolean requiereNivelacion(){
        return carreraDeseada.getFormaAdmision().equalsIgnoreCase("Diagnostico") && puntajeExamen > carreraDeseada.getPuntajeNivelacion();
    }
}
