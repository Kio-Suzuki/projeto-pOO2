package model.entities;

import java.util.Date;

public class Auditoria {
    private int audId;
    private int funId;
    private int proId;
    private Date datahora;
    
    public Auditoria() {
    }
    
    public Auditoria(int audId, int funId, int proId, Date datahora) {
        this.audId = audId;
        this.funId = funId;
        this.proId = proId;
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

    public Date getDatahora() {
        return datahora;
    }

    public void setDatahora(Date datahora) {
        this.datahora = datahora;
    }

    @Override
    public String toString() {
        return "Auditoria [audId=" + audId + ", funId=" + funId + ", proId=" + proId + ", datahora=" + datahora + "]";
    }

    
}
