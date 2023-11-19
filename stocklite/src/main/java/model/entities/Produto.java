package model.entities;

public class Produto {
    private int proId;
    private String proNome;
    private float proPreco;
    private String proCategoria;
    private int forId;
    
    public int getProId() {
        return proId;
    }
    public void setProId(int proId) {
        this.proId = proId;
    }
    public String getProNome() {
        return proNome;
    }
    public void setProNome(String proNome) {
        this.proNome = proNome;
    }
    public float getProPreco() {
        return proPreco;
    }
    public void setProPreco(float proPreco) {
        this.proPreco = proPreco;
    }
    public String getProCategoria() {
        return proCategoria;
    }
    public void setProCategoria(String proCategoria) {
        this.proCategoria = proCategoria;
    }
    public int getForId() {
        return forId;
    }
    public void setForId(int forId) {
        this.forId = forId;
    }
    public Produto() {
    }
    public Produto(int proId, String proNome, float proPreco, String proCategoria, int forId) {
        this.proId = proId;
        this.proNome = proNome;
        this.proPreco = proPreco;
        this.proCategoria = proCategoria;
        this.forId = forId;
    }
    @Override
    public String toString() {
        return "Produto [proId=" + proId + ", proNome=" + proNome + ", proPreco=" + proPreco + ", proCategoria="
                + proCategoria + ", forId=" + forId + "]";
    } 

    
}
