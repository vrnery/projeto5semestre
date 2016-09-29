/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.witc.modelo;

import br.com.witc.persistencia.ConvidadoPerfilDAO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

/**
 *
 * @author 10070124
 */
@Entity
public class ConvidadoPerfil implements Serializable {
    @Id
    @OneToOne
    @JoinColumn(name="id")
    private Perfil idPerfil;
    @OneToOne
    @JoinColumn(name="id")
    private Perfil idPerfilConvidado;
    @OneToOne
    @JoinColumn(name="id")
    private Livro idLivro;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Calendar dataSolicitacao;

    public Perfil getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(Perfil idPerfil) {
        this.idPerfil = idPerfil;
    }

    public Perfil getIdPerfilConvidado() {
        return idPerfilConvidado;
    }

    public void setIdPerfilConvidado(Perfil idPerfilConvidado) {
        this.idPerfilConvidado = idPerfilConvidado;
    }

    public Livro getIdLivro() {
        return idLivro;
    }

    public void setIdLivro(Livro idLivro) {
        this.idLivro = idLivro;
    }

    public Calendar getDataSolicitacao() {
        return dataSolicitacao;
    }

    public void setDataSolicitacao(Calendar dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }

    public ConvidadoPerfil() {
    }

    public ConvidadoPerfil(Calendar dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }
    
    public void salvar(ConvidadoPerfil salva) {
        ConvidadoPerfilDAO dao = new ConvidadoPerfilDAO();
        dao.salvar(salva);
    }
    
    public List<ConvidadoPerfil> carregar(Perfil idPerfilConvidado) {
        List<ConvidadoPerfil> list = new ArrayList<ConvidadoPerfil>();
        ConvidadoPerfilDAO dao = new ConvidadoPerfilDAO();
        list = dao.carregar(idPerfilConvidado);
        return list;
    }
    
    public void remover() {
        ConvidadoPerfilDAO dao = new ConvidadoPerfilDAO();
        dao.remover(this);
    }

    public List<ConvidadoPerfil> listarSolicitacao() {
        ConvidadoPerfilDAO dao = new ConvidadoPerfilDAO();
        return dao.carregar(this.idPerfilConvidado);
    }

    public void aceitarEdicao() {
        HistoricoLivros historico = new HistoricoLivros();
        historico.setLivro(this.idLivro);
        historico.setPerfil(this.idPerfilConvidado);
        TipoStatus status = new TipoStatus();
        status.carregarTipoStatus(1);
        historico.setStatus(status);
        historico.setDataInicio(Calendar.getInstance());
        historico.salvar();
        this.negarEdicao();
    }

    public void negarEdicao() {
        ConvidadoPerfilDAO dao = new ConvidadoPerfilDAO();
        dao.remover(this);
    }
}
