package model.entities;

public class Fornecedora {
    private int forId;
    private String forCnpj;
    private String forRazaoSocial;
    private String forEmail;
    private String forTelefone;
    private String forLogradouro;
    private int forNumero;
    private String forCep;
    private String forCidade;
    private String forEstado;
    public int getForId() {
        return forId;
    }
    public void setForId(int forId) {
        this.forId = forId;
    }
    public String getForCnpj() {
        return forCnpj;
    }
    public void setForCnpj(String forCnpj) {
        this.forCnpj = forCnpj;
    }
    public String getForRazaoSocial() {
        return forRazaoSocial;
    }
    public void setForRazaoSocial(String forRazaoSocial) {
        this.forRazaoSocial = forRazaoSocial;
    }
    public String getForEmail() {
        return forEmail;
    }
    public void setForEmail(String forEmail) {
        this.forEmail = forEmail;
    }
    public String getForTelefone() {
        return forTelefone;
    }
    public void setForTelefone(String forTelefone) {
        this.forTelefone = forTelefone;
    }
    public String getForLogradouro() {
        return forLogradouro;
    }
    public void setForLogradouro(String forLogradouro) {
        this.forLogradouro = forLogradouro;
    }
    public int getForNumero() {
        return forNumero;
    }
    public void setForNumero(int forNumero) {
        this.forNumero = forNumero;
    }
    public String getForCep() {
        return forCep;
    }
    public void setForCep(String forCep) {
        this.forCep = forCep;
    }
    public String getForCidade() {
        return forCidade;
    }
    public void setForCidade(String forCidade) {
        this.forCidade = forCidade;
    }
    public String getForEstado() {
        return forEstado;
    }
    public void setForEstado(String forEstado) {
        this.forEstado = forEstado;
    }
    public Fornecedora(int forId, String forCnpj, String forRazaoSocial, String forEmail, String forTelefone,
            String forLogradouro, int forNumero, String forCep, String forCidade, String forEstado) {
        this.forId = forId;
        this.forCnpj = forCnpj;
        this.forRazaoSocial = forRazaoSocial;
        this.forEmail = forEmail;
        this.forTelefone = forTelefone;
        this.forLogradouro = forLogradouro;
        this.forNumero = forNumero;
        this.forCep = forCep;
        this.forCidade = forCidade;
        this.forEstado = forEstado;
    }
    public Fornecedora() {
    }
    @Override
    public String toString() {
        return "Fornecedora [forId=" + forId + ", forCnpj=" + forCnpj + ", forRazaoSocial=" + forRazaoSocial
                + ", forEmail=" + forEmail + ", forTelefone=" + forTelefone + ", forLogradouro=" + forLogradouro
                + ", forNumero=" + forNumero + ", forCep=" + forCep + ", forCidade=" + forCidade + ", forEstado="
                + forEstado + "]";
    }

    public void setVisible(boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    
}
