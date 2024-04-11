package looca;

public class DTO {

    private String id_registro;

    public String getId_registro() {
        return id_registro;
    }

    public void setId_registro(String id_registro) {
        this.id_registro = id_registro;
    }

    @Override
    public String toString() {
        return id_registro;
    }
}
