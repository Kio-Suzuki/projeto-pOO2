package model.entities;

public class Funcionario {
    private int funId;
    private String funNome;
    private String funSobrenome;
    private String funCpf;
    private String funTelefone;
    private String funDepartamento;
    private float funSalario;
    public Funcionario() {
    }
    public Funcionario(int funId, String funNome, String funSobrenome, String funCpf, String funTelefone,
            String funDepartamento, float funSalario) {
        this.funId = funId;
        this.funNome = funNome;
        this.funSobrenome = funSobrenome;
        this.funCpf = funCpf;
        this.funTelefone = funTelefone;
        this.funDepartamento = funDepartamento;
        this.funSalario = funSalario;
    }
    public int getFunId() {
        return funId;
    }
    public void setFunId(int funId) {
        this.funId = funId;
    }
    public String getFunNome() {
        return funNome;
    }
    public void setFunNome(String funNome) {
        this.funNome = funNome;
    }
    public String getFunSobrenome() {
        return funSobrenome;
    }
    public void setFunSobrenome(String funSobrenome) {
        this.funSobrenome = funSobrenome;
    }
    public String getFunCpf() {
        return funCpf;
    }
    public void setFunCpf(String funCpf) {
        this.funCpf = funCpf;
    }
    public String getFunTelefone() {
        return funTelefone;
    }
    public void setFunTelefone(String funTelefone) {
        this.funTelefone = funTelefone;
    }
    public String getFunDepartamento() {
        return funDepartamento;
    }
    public void setFunDepartamento(String funDepartamento) {
        this.funDepartamento = funDepartamento;
    }
    public float getFunSalario() {
        return funSalario;
    }
    public void setFunSalario(float funSalario) {
        this.funSalario = funSalario;
    }
    @Override
    public String toString() {
        return "Funcionario [funId=" + funId + ", funNome=" + funNome + ", funSobrenome=" + funSobrenome + ", funCpf="
                + funCpf + ", funTelefone=" + funTelefone + ", funDepartamento=" + funDepartamento + ", funSalario="
                + funSalario + "]";
    }

    
}
