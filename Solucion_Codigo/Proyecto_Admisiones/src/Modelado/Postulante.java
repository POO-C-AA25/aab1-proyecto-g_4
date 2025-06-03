package Modelado;
public class Postulante {
    public String nombre;
    public String cedula;
    public Carrera carreraDeseada;
    private double puntajeExamen;
    private int puntajeMerito;
    private String tipoMerito;
    public Postulante(String nombre, String cedula, Carrera carreraDeseada){
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

    public String getCedula() {
        return cedula;
    }

    public String getNombre() {
        return nombre;
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

    public String getTipoMerito() {
        return tipoMerito;
    }
    
    public double calcularMeritos(){
        if (this.tipoMerito != null){
            if (this.tipoMerito.equalsIgnoreCase("Abanderado")){
                this.puntajeMerito += 5;
            } else if (this.tipoMerito.equalsIgnoreCase("Bachillerato Afin")){
                this.puntajeMerito += 2;
            } else if (this.tipoMerito.equalsIgnoreCase("Capacidad especial")){
                this.puntajeMerito += 3;
            }
        }
        return this.puntajeMerito;
    }

    public double calcularPuntajeTotal(){
        return this.puntajeExamen + calcularMeritos();
    }

    public boolean requiereNivelacion(){
        return carreraDeseada.getFormaAdmision().equalsIgnoreCase("Diagnostico") &&
               puntajeExamen > carreraDeseada.getPuntajeNivelacion();
    }
}