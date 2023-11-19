    package model.entities;

    public class Transportadora {
        private int traId;
        private String traCnpj;
        private String traRazaoSocial;
        private String traEmail;
        private String traTelefone;
        private String traLogradouro;
        private int traNumero;
        private int traCep;
        private String traCidade;
        private String traEstado;
        public Transportadora() {
        }
        public Transportadora(int traId, String traCnpj, String traRazaoSocial, String traEmail, String traTelefone,
                String traLogradouro, int traNumero, int traCep, String traCidade, String traEstado) {
            this.traId = traId;
            this.traCnpj = traCnpj;
            this.traRazaoSocial = traRazaoSocial;
            this.traEmail = traEmail;
            this.traTelefone = traTelefone;
            this.traLogradouro = traLogradouro;
            this.traNumero = traNumero;
            this.traCep = traCep;
            this.traCidade = traCidade;
            this.traEstado = traEstado;
        }
        public int getTraId() {
            return traId;
        }
        public void setTraId(int traId) {
            this.traId = traId;
        }
        public String getTraCnpj() {
            return traCnpj;
        }
        public void setTraCnpj(String traCnpj) {
            this.traCnpj = traCnpj;
        }
        public String getTraRazaoSocial() {
            return traRazaoSocial;
        }
        public void setTraRazaoSocial(String traRazaoSocial) {
            this.traRazaoSocial = traRazaoSocial;
        }
        public String getTraEmail() {
            return traEmail;
        }
        public void setTraEmail(String traEmail) {
            this.traEmail = traEmail;
        }
        public String getTraTelefone() {
            return traTelefone;
        }
        public void setTraTelefone(String traTelefone) {
            this.traTelefone = traTelefone;
        }
        public String getTraLogradouro() {
            return traLogradouro;
        }
        public void setTraLogradouro(String traLogradouro) {
            this.traLogradouro = traLogradouro;
        }
        public int getTraNumero() {
            return traNumero;
        }
        public void setTraNumero(int traNumero) {
            this.traNumero = traNumero;
        }
        public int getTraCep() {
            return traCep;
        }
        public void setTraCep(int traCep) {
            this.traCep = traCep;
        }
        public String getTraCidade() {
            return traCidade;
        }
        public void setTraCidade(String traCidade) {
            this.traCidade = traCidade;
        }
        public String getTraEstado() {
            return traEstado;
        }
        public void setTraEstado(String traEstado) {
            this.traEstado = traEstado;
        }
        @Override
        public String toString() {
            return "Transportadora [traId=" + traId + ", traCnpj=" + traCnpj + ", traRazaoSocial=" + traRazaoSocial
                    + ", traEmail=" + traEmail + ", traTelefone=" + traTelefone + ", traLogradouro=" + traLogradouro
                    + ", traNumero=" + traNumero + ", traCep=" + traCep + ", traCidade=" + traCidade + ", traEstado="
                    + traEstado + "]";
        }

        
    }
