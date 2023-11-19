package model.entities;
import java.util.Date;

public class Estoque_Produto {
    private int estId;
    private String proNome;
    private float proPreco;
    private String proCategoria;
    private int forId;
    private String forRazaoSocial;
    private int estQuantidade;
    private String estLocal;
    private Date estDataEntrada;
    private Date estDataValidade;
    private Date origemDataEntrada;
    private Date origemDataValidade;
    private int proId;

    public Estoque_Produto() {
    }

    public Estoque_Produto(int estId, String proNome, float proPreco, String proCategoria, int forId, String forRazaoSocial, int estQuantidade, String estLocal, Date estDataEntrada, Date estDataValidade, Date origemDataEntrada, Date origemDataValidade, int proId) {
        this.estId = estId;
        this.proNome = proNome;
        this.proPreco = proPreco;
        this.proCategoria = proCategoria;
        this.forId = forId;
        this.forRazaoSocial = forRazaoSocial;
        this.estQuantidade = estQuantidade;
        this.estLocal = estLocal;
        this.estDataEntrada = estDataEntrada;
        this.estDataValidade = estDataValidade;
        this.origemDataEntrada = origemDataEntrada;
        this.origemDataValidade = origemDataValidade;
        this.proId = proId;
    }

    public int getEstId() {
        return estId;
    }

    public void setEstId(int estId) {
        this.estId = estId;
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

    public String getForRazaoSocial() {
        return forRazaoSocial;
    }

    public void setForRazaoSocial(String forRazaoSocial) {
        this.forRazaoSocial = forRazaoSocial;
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

    public Date getOrigemDataEntrada() {
        return origemDataEntrada;
    }

    public void setOrigemDataEntrada(Date origemDataEntrada) {
        this.origemDataEntrada = origemDataEntrada;
    }

    public Date getOrigemDataValidade() {
        return origemDataValidade;
    }

    public void setOrigemDataValidade(Date origemDataValidade) {
        this.origemDataValidade = origemDataValidade;
    }

    public int getProId() {
        return proId;
    }

    public void setProId(int proId) {
        this.proId = proId;
    }

    @Override
    public String toString() {
        return "Estoque_Produto [estId=" + estId + ", proNome=" + proNome + ", proPreco=" + proPreco + ", proCategoria="
                + proCategoria + ", forId=" + forId + ", forRazaoSocial=" + forRazaoSocial + ", estQuantidade="
                + estQuantidade + ", estLocal=" + estLocal + ", estDataEntrada=" + estDataEntrada + ", estDataValidade="
                + estDataValidade + ", origemDataEntrada=" + origemDataEntrada + ", origemDataValidade="
                + origemDataValidade + ", proId=" + proId + "]";
    }
    
    
    
    
}
