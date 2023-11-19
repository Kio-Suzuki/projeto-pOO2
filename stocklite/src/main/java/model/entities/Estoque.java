package model.entities;
import java.util.Date;

public class Estoque {
    private int estId;
    private int estQuantidade;
    private String estLocal;
    private Date estDataEntrada;
    private Date estDataValidade;
    private int proId;
    
    public Estoque() {
    }

    public Estoque(int estId, int estQuantidade, String estLocal, Date estDataEntrada, Date estDataValidade,
            int proId) {
        this.estId = estId;
        this.estQuantidade = estQuantidade;
        this.estLocal = estLocal;
        this.estDataEntrada = estDataEntrada;
        this.estDataValidade = estDataValidade;
        this.proId = proId;
    }

    public int getEstId() {
        return estId;
    }

    public void setEstId(int estId) {
        this.estId = estId;
    }

    public int getEstQuantidade() {
        return estQuantidade;
    }

    public void setEstQuantidade(int estQuantidade) {
        this.estQuantidade = estQuantidade;
    }

    public String getEstLocal() {
        return estLocal;
    }

    public void setEstLocal(String estLocal) {
        this.estLocal = estLocal;
    }

    public Date getEstDataEntrada() {
        return estDataEntrada;
    }

    public void setEstDataEntrada(Date estDataEntrada) {
        this.estDataEntrada = estDataEntrada;
    }

    public Date getEstDataValidade() {
        return estDataValidade;
    }

    public void setEstDataValidade(Date estDataValidade) {
        this.estDataValidade = estDataValidade;
    }

    public int getProId() {
        return proId;
    }

    public void setProId(int proId) {
        this.proId = proId;
    }

    @Override
    public String toString() {
        return "Estoque [estId=" + estId + ", estQuantidade=" + estQuantidade + ", estLocal=" + estLocal
                + ", estDataEntrada=" + estDataEntrada + ", estDataValidade=" + estDataValidade + ", proId=" + proId
                + "]";
    } 

    
}
