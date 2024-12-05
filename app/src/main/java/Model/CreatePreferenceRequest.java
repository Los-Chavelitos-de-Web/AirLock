package Model;

import java.util.List;
import Model.ProdCart;

public class CreatePreferenceRequest {
    private List<ProdCart> relojes;
    private String correo;

    // Constructor
    public CreatePreferenceRequest(List<ProdCart> relojes, String correo) {
        this.relojes = relojes;
        this.correo = correo;
    }

    // Getters and setters
    public List<ProdCart> getRelojes() {
        return relojes;
    }

    public void setRelojes(List<ProdCart> relojes) {
        this.relojes = relojes;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
