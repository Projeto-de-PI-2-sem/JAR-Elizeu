package org.notelog.model;

public class Empresa {
    private String id;
    private String nome;
    private String cnpj;
    private String email;
    private String webHookUrl;
    private String oAuthToken;
    private String slackChannel;

    public Empresa(String id, String nome, String cnpj, String email, String webHookUrl, String oAuthToken, String slackChannel) {
        this.id = id;
        this.nome = nome;
        this.cnpj = cnpj;
        this.email = email;
        this.webHookUrl = webHookUrl;
        this.oAuthToken = oAuthToken;
        this.slackChannel = slackChannel;
    }

    public Empresa() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebHookUrl() {
        return webHookUrl;
    }

    public void setWebHookUrl(String webHookUrl) {
        this.webHookUrl = webHookUrl;
    }

    public String getoAuthToken() {
        return oAuthToken;
    }

    public void setoAuthToken(String oAuthToken) {
        this.oAuthToken = oAuthToken;
    }

    public String getSlackChannel() {
        return slackChannel;
    }

    public void setSlackChannel(String slackChannel) {
        this.slackChannel = slackChannel;
    }

    @Override
    public String toString() {
        return "Empresa{" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                ", cnpj='" + cnpj + '\'' +
                ", email='" + email + '\'' +
                ", webHookUrl='" + webHookUrl + '\'' +
                ", oAuthToken='" + oAuthToken + '\'' +
                ", slackChannel='" + slackChannel + '\'' +
                '}';
    }
}