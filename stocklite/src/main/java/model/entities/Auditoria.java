package model.entities;

import java.util.Date;

public class Auditoria {
    private int audId;
    private int funId;
    private int proId;
    private int acao;
    private int quantidade;
    private Date datahora;

    public Auditoria() {
    }

    public Auditoria(int audId, int funId, int proId, int acao, int quantidade, Date datahora) {
        this.audId = audId;
        this.funId = funId;
        this.proId = proId;
        this.acao = acao;
        this.quantidade = quantidade;
        this.datahora = datahora;
    }

    public int getAudId() {
        return audId;
    }

    public void setAudId(int audId) {
        this.audId = audId;
    }

    public int getFunId() {
        return funId;
    }

    public void setFunId(int funId) {
        this.funId = funId;
    }

    public int getProId() {
        return proId;
    }

    public void setProId(int proId) {
        this.proId = proId;
    }

    public int getAcao() {
        return acao;
    }

    public void setAcao(int acao) {
        this.acao = acao;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Date getDatahora() {
        return datahora;
    }

    public void setDatahora(Date datahora) {
        this.datahora = datahora;
    }

    @Override
    public String toString() {
        return "Auditoria [audId=" + audId + ", funId=" + funId + ", proId=" + proId + ", acao=" + acao
                + ", quantidade=" + quantidade + ", datahora=" + datahora + "]";
    }
    
    
    
    
}
