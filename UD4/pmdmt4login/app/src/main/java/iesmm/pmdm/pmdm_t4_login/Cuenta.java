package iesmm.pmdm.pmdm_t4_login;

public class Cuenta {

    String usuario;
    String contraseña;
    String telefono;

    public Cuenta(String usuario, String contraseña, String telefono) {
        this.usuario = usuario;
        this.contraseña = contraseña;
        this.telefono = telefono;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
