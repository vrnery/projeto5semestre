/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.witc.modelo;

import br.com.witc.excessao.LoginInvalidoException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 *
 * @author marcelo.lima
 */
public class ControladorAutenticacao {
    private Usuario usuario;
    private Perfil perfil;
    private Usuario amigoUsuario;
    
    public ControladorAutenticacao() {
        this.usuario = new Usuario();
        this.amigoUsuario = new Usuario();
    }        

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    /**
     * @return O usuário logado no sistema
     */
    public Usuario getUsuario() {
        return usuario;
    }

    
    /**     
     * @return O nome do usuário logado no sistema
     */
    public String getNomeCompletoUsuario() {
        // Verificar se é para retornar o nome do usuario ou do amigo carregado
        if (this.amigoUsuario.getId() != 0) {
            return this.amigoUsuario.getNome() + " " + this.amigoUsuario.getSobrenome();
        }
        return this.usuario.getNome() + " " + this.usuario.getSobrenome();
    }
    
    /**     
     * @return A quantidade de amigos do usuário logado no sistema
     */
    public int getNumeroAmigosUsuarioLogado() {
        List<Usuario> lstAmigos;
        // Verificar se é para contar os amigos do usuario ou do amigo carregado
        if (this.amigoUsuario.getId() != 0) {
            lstAmigos = this.amigoUsuario.listarAmigos();
        } else {
            lstAmigos = this.usuario.listarAmigos();
        }
        if (lstAmigos != null) {
            return lstAmigos.size();
        }
        return 0;
    }        
    
    /**
     * @return 
     */
    public String getTipoPerfil() {
        return perfil.getTipoPerfil().getTipoPerfil();
    }
    
    /**
     * @param perfil 
     */
    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }
    
    /**
     * Autentica um usuário no sistema
     * @param email O email do usuário
     * @param senha A senha do usuário     
     * @throws br.com.witc.excessao.LoginInvalidoException  Se o login for invalido            
     * @throws java.security.NoSuchAlgorithmException Caso o algorítimo SHA-256 não seja localizado          
     * @throws java.io.UnsupportedEncodingException Caso haja erro de codificação          
     */
    public void efetuarLogin(String email, String senha) 
            throws LoginInvalidoException, NoSuchAlgorithmException, UnsupportedEncodingException {                
        this.setUsuario(Usuario.efetuarLogin(email, senha));
    }
    public void retornarPerfilUsuarioLogado(){
       this.setPerfil(Perfil.retornarPerfilUsuarioLogado(this.getUsuario()));
    }

    /**
     * Preenche o status do usuario verificando se é usuario ou amigo
     * @return O status do usuario que tiver salvo no banco
     */
    public String getStatusUsuario() {
        if (this.amigoUsuario.getId() != 0) {
            return this.amigoUsuario.getStatus();
        }
        return this.usuario.getStatus();
    }
    
    /**
     * Carrega a variavel com o codigo do amigo passado
     * @param id Codigo do amigo
     */
    public void setAmigoUsuario(int id) {
        Usuario carrega = new Usuario();
        this.amigoUsuario = carrega.carregarAmigo(id);
    }
    
    /**
     * Zera a variavel amigoUsuario
     */
    public void setAmigoUsuario() {
        this.amigoUsuario = new Usuario();
    }

    /**
     * @return O usuario amigo
     */
    public Usuario getAmigoUsuario() {
        return amigoUsuario;
    }
    
    /**
     * Acessa o modelo para atualizar o status do usuario
     * @param status Codigo do status (Enum do banco)
     */
    public void atualizarStatusUsuario(int status) {
        this.usuario.atualizarStatus(status);
    }
}
