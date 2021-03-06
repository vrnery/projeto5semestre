/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.witc.bean;

import br.com.witc.excessao.DadosUsuarioInvalidoException;
import br.com.witc.excessao.LinkRecuperacaoInvalidoException;
import br.com.witc.excessao.TipoPerfilException;
import br.com.witc.excessao.TipoTextoException;
import br.com.witc.excessao.UsuarioInvalidoException;
import br.com.witc.modelo.ControladorCadastro;
import br.com.witc.modelo.TipoPerfil;
import br.com.witc.modelo.TipoTexto;
import br.com.witc.modelo.Usuario;
import br.com.witc.persistencia.TipoPerfilDAO;
import br.com.witc.persistencia.TipoTextoDAO;
import java.awt.image.BufferedImage;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.el.ELContext;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.mail.EmailException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import org.primefaces.model.CroppedImage;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.DefaultStreamedContent;
import java.io.*;
import java.util.Base64;
import static javax.faces.context.FacesContext.getCurrentInstance;
import javax.imageio.ImageIO;
import org.apache.commons.io.IOUtils;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author marcelo.lima
 */
@ManagedBean
@SessionScoped
public class CadastrarBean {

    private final ControladorCadastro controlador;
    private Usuario usuario;
    private String emailVerificado;
    private String emailRecuperacaoSenha;
    private String senhaRedefinicao;
    private String hashRedefinicao;
    private String diaNascimento;
    private String mesNascimento;
    private String anoNascimento;
    private List<Usuario> amigos;
    private List<Usuario> sugestao;
    private List<Usuario> solicitacao;
    private List<Usuario> usuarios;
    public List<TipoTexto> tiposTextoUsuario;
    public List<TipoTexto> selectedTiposTextoUsuario;
    private String convidarEmail;
    private StreamedContent imagemEnviada = new DefaultStreamedContent();
    private String imagemTemporaria;
    private CroppedImage croppedImage;
    private boolean exibeBotao = true;
    private UploadedFile file;
    private TipoPerfil tipoPerfil;
    private TipoPerfilDAO tipoPerfildao;
    public TipoTexto tipoTexto;
    public TipoTextoDAO tipoTextoDAO;    

    private static final String CAMINHO_FOTO_DEFAULT = "/resources/imagens/semFoto.png";
    
    public CadastrarBean() {
        this.controlador = new ControladorCadastro();
        this.usuario = new Usuario();
        this.tipoPerfil = new TipoPerfil();
        this.tipoPerfildao = new TipoPerfilDAO();
        this.tipoTexto = new TipoTexto();
        this.tipoTextoDAO = new TipoTextoDAO();
    }
    
    /**
     * @return the usuario
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the emailVerificado
     */
    public String getEmailVerificado() {
        return emailVerificado;
    }

    /**
     * @param emailVerificado the emailVerificado to set
     */
    public void setEmailVerificado(String emailVerificado) {
        this.emailVerificado = emailVerificado;
    }

    /**
     * @return the emailRecuperacaoSenha
     */
    public String getEmailRecuperacaoSenha() {
        return emailRecuperacaoSenha;
    }

    /**
     * @param emailRecuperacaoSenha the emailRecuperacaoSenha to set
     */
    public void setEmailRecuperacaoSenha(String emailRecuperacaoSenha) {
        this.emailRecuperacaoSenha = emailRecuperacaoSenha;
    }

    /**
     * @return the senhaRedefinicao
     */
    public String getSenhaRedefinicao() {
        return senhaRedefinicao;
    }

    /**
     * @param senhaRedefinicao the senhaRedefinicao to set
     */
    public void setSenhaRedefinicao(String senhaRedefinicao) {
        this.senhaRedefinicao = senhaRedefinicao;
    }

    /**
     * @return the hashRedefinicao
     */
    public String getHashRedefinicao() {
        return hashRedefinicao;
    }

    /**
     * @param hashRedefinicao the hashRedefinicao to set
     */
    public void setHashRedefinicao(String hashRedefinicao) {
        this.hashRedefinicao = hashRedefinicao;
    }

    /**
     * @return the diaNascimento
     */
    public String getDiaNascimento() {
        return diaNascimento;
    }

    /**
     * @param diaNascimento the diaNascimento to set
     */
    public void setDiaNascimento(String diaNascimento) {
        this.diaNascimento = diaNascimento;
    }

    /**
     * @return the mesNascimento
     */
    public String getMesNascimento() {
        return mesNascimento;
    }

    /**
     * @param mesNascimento the mesNascimento to set
     */
    public void setMesNascimento(String mesNascimento) {
        this.mesNascimento = mesNascimento;
    }

    /**
     * @return the anoNascimento
     */
    public String getAnoNascimento() {
        return anoNascimento;
    }

    /**
     * @param anoNascimento the anoNascimento to set
     */
    public void setAnoNascimento(String anoNascimento) {
        this.anoNascimento = anoNascimento;
    }
    
    /**
     * @return 
     */
    public StreamedContent getImagemEnviada() {
        return imagemEnviada;
    }
    /**
     * @param imagemEnviada 
     */
    public void setImagemEnviada(StreamedContent imagemEnviada) {
        this.imagemEnviada = imagemEnviada;
    }
    /**
     * @return 
     */
    public String getImagemTemporaria() {
        return imagemTemporaria;
    }
    /**
     * @param imagemTemporaria 
     */
    public void setImagemTemporaria(String imagemTemporaria) {
        this.imagemTemporaria = imagemTemporaria;
    }
    /**
     * @return 
     */
    public CroppedImage getCroppedImage() {
        return croppedImage;
    }
    /**
     * @param croppedImage 
     */
    public void setCroppedImage(CroppedImage croppedImage) {
        this.croppedImage = croppedImage;
    }
    /**
     * @return 
     */
    public boolean isExibeBotao() {
        return exibeBotao;
    }
    /**
     * @param exibeBotao 
     */
    public void setExibeBotao(boolean exibeBotao) {
        this.exibeBotao = exibeBotao;
    }
    
    public UploadedFile getFile() {
        return this.file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }
    
    public StreamedContent getFoto() {                 
        if (this.usuario.getFoto() == null) {
            return carregarFotoDefault();
        }        
        InputStream is = new ByteArrayInputStream(this.usuario.getFoto());               
        StreamedContent image = new DefaultStreamedContent(is);        
        return image;
    }
    
    public TipoPerfil getTipoPerfil() {
        return tipoPerfil;
    }
    
    public void setTipoPerfil(TipoPerfil tipoPerfil) {
        this.tipoPerfil = tipoPerfil;
    }

    public TipoPerfilDAO getTipoPerfildao() {
        return tipoPerfildao;
    }

    public void setTipoPerfildao(TipoPerfilDAO tipoPerfildao) {
        this.tipoPerfildao = tipoPerfildao;
    }
    
    /**
     * @return the tipoTexto
     */
    public TipoTexto getTipoTexto() {
        return tipoTexto;
    }

    /**
     * @param tipoTexto the tipoTexto to set
     */
    public void setTipoTexto(TipoTexto tipoTexto) {
        this.tipoTexto = tipoTexto;
    }

    /**
     * @return the tipoTextoDAO
     */
    public TipoTextoDAO getTipoTextoDAO() {
        return tipoTextoDAO;
    }

    /**
     * @param tipoTextoDAO the tipoTextoDAO to set
     */
    public void setTipoTextoDAO(TipoTextoDAO tipoTextoDAO) {
        this.tipoTextoDAO = tipoTextoDAO;
    }
           
    /**
     * Busca e atualiza a lista de amigos
     *
     * @return Lista de amigos
     */
    public List<Usuario> getAmigos() {
        this.amigos = this.controlador.listarAmigos();
        return this.amigos;
    }

    /**
     * Verifica se o usuario possui amigos
     *
     * @return Se tem amigos
     */
    public boolean isTemAmigos() {
        return !(this.amigos == null || this.amigos.isEmpty());
    }

    /**
     * Busca e atualiza a lista de sugestão
     *
     * @return Lista de sugestão
     */
    public List<Usuario> getSugestao() {
        this.sugestao = this.controlador.listarSugestao();
        return this.sugestao;
    }

    /**
     * Verifica se o sistema possui sugestão de amigos
     *
     * @return Se tem sugestão de amigos
     */
    public boolean isTemSugestao() {
        return !(this.sugestao == null || this.sugestao.isEmpty());
    }

    /**
     * Busca e atualiza a lista de solicitação
     *
     * @return Lista de convites recebidos
     */
    public List<Usuario> getSolicitacao() {
        this.solicitacao = this.controlador.listarSolicitacao();
        return solicitacao;
    }

    /**
     * Metodo para verificar se existe solicitação
     *
     * @return Se tem solicitação
     */
    public boolean isTemSolicitacao() {
        return !(this.solicitacao == null || this.solicitacao.isEmpty());
    }

    /**
     * Busca a lista de usuarios do sistema
     *
     * @return Lista de usuarios do sistema
     */
    public List<Usuario> getUsuarios() {
        this.usuarios = this.controlador.listarUsuarios();
        return usuarios;
    }

    /**
     * @return Email do convidado para ser amigo
     */
    public String getConvidarEmail() {
        return convidarEmail;
    }

    /**
     * @param convidarEmail Email de quem vai ser solicitado como amigo
     */
    public void setConvidarEmail(String convidarEmail) {
        this.convidarEmail = convidarEmail;
    }
    
    public StreamedContent getFotos(Usuario user) {
        try {
            if (user.getFoto() == null) {
                return carregarFotoDefault();
            }
            InputStream is = new ByteArrayInputStream(user.getFoto());
            StreamedContent image = new DefaultStreamedContent(is);
            return image;
        } catch(NumberFormatException ex) {
            return carregarFotoDefault();
        }
    }
    
    public StreamedContent getFotoAmigo() {
        int idfoto = 0;
        try {
            idfoto = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("userfoto"));
        } catch (NumberFormatException ex) {
            return carregarFotoDefault();
        }
        Usuario usu = new Usuario();
        for (Usuario us : this.amigos) {
            if (us.getId() == idfoto) {
                usu = us;
                break;
            }
        }
        return this.getFotos(usu);
    }
    
    public StreamedContent getFotoSugestao() {
        int idfoto = 0;
        try {
            idfoto = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("userfoto"));
        } catch (NumberFormatException ex) {
            return carregarFotoDefault();
        }
        Usuario usu = new Usuario();
        for (Usuario us : this.sugestao) {
            if (us.getId() == idfoto) {
                usu = us;
                break;
            }
        }
        return this.getFotos(usu);
    }
    
    public StreamedContent getFotoSolicitacao() {
        int idfoto = 0;
        try {
            idfoto = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("userfoto"));
        } catch (NumberFormatException ex) {
            return carregarFotoDefault();
        }
        Usuario usu = new Usuario();
        for (Usuario us : this.solicitacao) {
            if (us.getId() == idfoto) {
                usu = us;
                break;
            }
        }
        return this.getFotos(usu);
    }
    
    public StreamedContent getFotoConvidar() {
        int idfoto = 0;
        try {
            idfoto = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("userfoto"));
        } catch (NumberFormatException ex) {
            return carregarFotoDefault();
        }
        Usuario usu = new Usuario();
        for (Usuario us : this.usuarios) {
            if (us.getId() == idfoto) {
                usu = us;
                break;
            }
        }
        return this.getFotos(usu);
    }

    /**
     * @return the anoAtual
     */
    public String getAnoAtual() {
        Calendar now = Calendar.getInstance();
        return String.valueOf(now.get(Calendar.YEAR));
    }

    /**
     * @return the anoInicial
     */
    public String getAnoInicial() {
        int anoAtual = Integer.parseInt(this.getAnoAtual());
        return String.valueOf(anoAtual - 80);

    }

    /**
     * Seta o usuario deste bean com o usuario logado no sistema
     */
    public void setUsuarioLogado() {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        AutenticarBean autenticarBean = (AutenticarBean) FacesContext.getCurrentInstance().getApplication()
                .getELResolver().getValue(elContext, null, "autenticarBean");

        this.usuario = autenticarBean.usuarioLogado();
    }

    public void setDataNascimento() throws ParseException {
        SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
        String data = getDiaNascimento() + "/" + getMesNascimento() + "/" + getAnoNascimento();
        Calendar c = Calendar.getInstance();
        c.setTime(formatoData.parse(data));

        this.usuario.setDataAniversario(c);

    }

    /**
     * Seta a data de nascimento nas variaveis locais
     */
    public void preencherDataNasc() {
        try {
            Calendar c; 
            c = this.usuario.getDataAniversario();             
            this.diaNascimento = Integer.toString(c.get(Calendar.DAY_OF_MONTH));
            this.mesNascimento = Integer.toString(c.get(Calendar.MONTH)+1); 
            this.anoNascimento = Integer.toString(c.get(Calendar.YEAR));
            /*this.diaNascimento = Integer.toString(this.usuario.getDataAniversario().getTime().getDate());
            this.mesNascimento = Integer.toString(this.usuario.getDataAniversario().getTime().getMonth());
            this.anoNascimento = Integer.toString(this.usuario.getDataAniversario().getTime().getYear());*/
        } catch (Exception e) {
            enviarMensagem(javax.faces.application.FacesMessage.SEVERITY_ERROR, e.getMessage());
        }
    }

    /**
     * Retorna a data de nascimento
     */
    public void retornarDataNasc() {
        // Passei aqui e quiz atualizar o status
        this.atualizarStatusUsuario(1);
        this.preencherDataNasc();
    }

    /**
     * Cadastra um usuario no sistema
     *
     * @return Uma string contendo a próxima página a ser enviada para o usuário
     */
    public String cadastrarUsuario() {
        // Setar a data de nascimento no usuario
        try {
            if (!this.usuario.getEmail().equals(this.emailVerificado)) {
                throw new DadosUsuarioInvalidoException("Os emails informados não coincidem!");
            }

            setDataNascimento();
            this.usuario.setStatus("Pensando");
            this.usuario.setAtivo(true);
            this.controlador.cadastrarUsuario(this.usuario);
            setDataNascimento();                  
            this.controlador.criarPerfilPadrao(this.usuario);

            ELContext elContext = FacesContext.getCurrentInstance().getELContext();
            AutenticarBean autenticarBean = (AutenticarBean) FacesContext.getCurrentInstance().getApplication()
                    .getELResolver().getValue(elContext, null, "autenticarBean");
            autenticarBean.setUsuario(this.usuario);
            // Verifica se o novo usuario ja recebeu alguma solicitação de amizade
            this.controlador.verificarConvite(this.usuario.getEmail());
            return "timeline";
        } catch (ParseException ex) {
            enviarMensagem(javax.faces.application.FacesMessage.SEVERITY_ERROR, "Data de Nascimento inválida.");
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            enviarMensagem(javax.faces.application.FacesMessage.SEVERITY_ERROR, "Problemas na geração do hash da senha!");
        } catch (DadosUsuarioInvalidoException ex) {
            enviarMensagem(javax.faces.application.FacesMessage.SEVERITY_ERROR, ex.getMessage());
        } catch (UsuarioInvalidoException ex) {
            // Apaga os dados do formulario
            this.usuario = new Usuario();
            this.diaNascimento = null;
            this.mesNascimento = null;
            this.anoNascimento = null;
            this.emailVerificado = null;
            enviarMensagem(javax.faces.application.FacesMessage.SEVERITY_ERROR, ex.getMessage());
        }
        return null;
    }
    
    /**
     * Altera o usuário não utlizado o método de cadastro para 
     * não prejudicar a validação do email 
     * @return 
     */
    public String alterarUsuario() {
        InputStream inputStream = null;
        byte[] imgBytes = null;
        // Setar a data de nascimento no usuario
        try {            
            if ((this.file != null) && (!this.file.getFileName().isEmpty()))  {
                inputStream = this.file.getInputstream();                
                imgBytes = IOUtils.toByteArray(inputStream);
                this.usuario.setFoto(imgBytes);
            }                        
            setDataNascimento();
            this.controlador.alterarUsuario(this.usuario);
            ELContext elContext = FacesContext.getCurrentInstance().getELContext();
            AutenticarBean autenticarBean = (AutenticarBean) FacesContext.getCurrentInstance().getApplication()
                    .getELResolver().getValue(elContext, null, "autenticarBean");
            autenticarBean.setUsuario(this.usuario);
            // Verifica se o novo usuario ja recebeu alguma solicitação de amizade
            this.controlador.verificarConvite(this.usuario.getEmail());            
            return "timeline";
        } catch (ParseException ex) {
            enviarMensagem(javax.faces.application.FacesMessage.SEVERITY_ERROR, "Data de Nascimento inválida.");
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            enviarMensagem(javax.faces.application.FacesMessage.SEVERITY_ERROR, "Problemas na geração do hash da senha!");
        } catch (DadosUsuarioInvalidoException ex) {
            enviarMensagem(javax.faces.application.FacesMessage.SEVERITY_ERROR, ex.getMessage());
        } catch (UsuarioInvalidoException ex) {
            // Apaga os dados do formulario
            this.usuario = new Usuario();
            this.diaNascimento = null;
            this.mesNascimento = null;
            this.anoNascimento = null;
            this.emailVerificado = null;
            enviarMensagem(javax.faces.application.FacesMessage.SEVERITY_ERROR, ex.getMessage());
        } catch (IOException ex) {
            enviarMensagem(FacesMessage.SEVERITY_ERROR, "Problemas ao carregar a foto!");
        }
        return null;
    }
    public String excluirUsuario(){                
        try {
            this.usuario.setStatus("");
            this.usuario.setAtivo(false);
            this.controlador.excluirUsuario(this.usuario); 
            removerTodasAmizades(this.usuario.getId());
            excluirTodosTipoTextoUsuario(this.usuario.getId());
        } catch (DadosUsuarioInvalidoException | NoSuchAlgorithmException | UnsupportedEncodingException | UsuarioInvalidoException ex) {
            enviarMensagem(javax.faces.application.FacesMessage.SEVERITY_ERROR, ex.getMessage());
        }
        this.usuario = new Usuario();
        this.diaNascimento = null;
        this.mesNascimento = null;
        this.anoNascimento = null;
        this.emailVerificado = null;  
        getCurrentInstance().getExternalContext().invalidateSession();
        return "index.xhtml?faces-redirect=true";   
    }
    /**
     * Envia o link de redefinição de senha para o usuário
     *
     * @return A próxima página a ser visualizada pelo usuário
     */
    public String recuperarConta() {
        try {
            // Capitura a url do sistema
            String path = ((HttpServletRequest) FacesContext.getCurrentInstance()
                    .getExternalContext().getRequest()).getRequestURL().toString();
            // Altera a variavel para excluir o restante da url
            path = path.replaceFirst("/faces(.*)", "");
            this.controlador.recuperarConta(this.emailRecuperacaoSenha, path);
            enviarMensagem(javax.faces.application.FacesMessage.SEVERITY_INFO, "Um email com instruções para redefinir sua senha foi enviado.");
            return "resultadoOper";
        } catch (DadosUsuarioInvalidoException e) {
            enviarMensagem(javax.faces.application.FacesMessage.SEVERITY_ERROR, e.getMessage());
        } catch (EmailException e) {
            enviarMensagem(javax.faces.application.FacesMessage.SEVERITY_ERROR, "Não foi possível enviar o email para redefinição de senha!");
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            enviarMensagem(javax.faces.application.FacesMessage.SEVERITY_ERROR, "Problemas na geração do hash para redefinição de senha!");
        }
        return null;
    }

    /**
     * Redefine a senha do usuário
     *
     * @return A próxima página a ser visualizada pelo usuário
     */
    public String redefinirSenha() {
        if (!this.usuario.getSenha().equals(this.senhaRedefinicao)) {
            enviarMensagem(javax.faces.application.FacesMessage.SEVERITY_ERROR, "As senhas informadas não coicidem!");
            return null;
        }

        if (this.hashRedefinicao.length() != 64) {
            enviarMensagem(javax.faces.application.FacesMessage.SEVERITY_ERROR, "Link de redefinição inválido!");
            return null;
        }

        try {
            this.controlador.redefinirSenha(this.emailRecuperacaoSenha, this.hashRedefinicao, this.senhaRedefinicao);
            enviarMensagem(javax.faces.application.FacesMessage.SEVERITY_INFO, "Senha alterada com sucesso");
            return "index.xhtml";
        } catch (DadosUsuarioInvalidoException ex) {
            enviarMensagem(javax.faces.application.FacesMessage.SEVERITY_ERROR, ex.getMessage());
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            enviarMensagem(javax.faces.application.FacesMessage.SEVERITY_ERROR, "Problemas na geração do hash para redefinição de senha!");
        } catch (LinkRecuperacaoInvalidoException ex) {
            enviarMensagem(javax.faces.application.FacesMessage.SEVERITY_ERROR, ex.getMessage());
        }

        return null;
    }

    /**
     * Prepara as variaveis para a view de amigos
     *
     * @return Pagina de manutenção de amigos
     */
    public String listarAmigos() {
        this.controlador.usuarioLogado(this.usuario);
        this.usuarios = this.listarUsuarios();
        return "listarAmigos";
    }

    /**
     * Metodo para carregar os usuarios do sistema
     *
     * @return Lista de usuarios do sistema
     */
    public List<Usuario> listarUsuarios() {
        return this.controlador.listarUsuarios();
    }

    /**
     * Metodo utilizado no autocomplete para completar automaticamente o email
     *
     * @param email Digitado no autocomplete
     * @return Lista de usuarios com o possivel email
     */
    public List<Usuario> completeEmail(String email) {
        List<Usuario> filteredUsuario = new ArrayList<>();
        for (Usuario pesquisando : this.listarUsuarios()) {
            // Verificar se o email digitado é parecido com os cadastrados no sistema
            if (pesquisando.getEmail().toLowerCase().startsWith(email.toLowerCase())) {
                // Adiciona os email se forem parecidos
                filteredUsuario.add(pesquisando);
            }
        }
        // Verifica se possui usuario adicionado para retorno
        if (filteredUsuario.size() == 0 || filteredUsuario.isEmpty()) {
            // Cria um novo usuario vazio e seta o email com o digitado pelo usuario
            Usuario filuser = new Usuario();
            filuser.setEmail(email);
            filteredUsuario.add(filuser);
        }
        return filteredUsuario;
    }

    /**
     * Metodo para solicitar a amizade para outro usuario
     *
     * @param idSugestao Identificação do usuario que deseja-se tornar amigo
     */
    public void solicitarAmizade(int idSugestao) {
        // Status do usuario
        this.atualizarStatusUsuario(1);
        this.controlador.solicitarAmizade(idSugestao);
    }

    /**
     * Metodo para confirmar a amizade de dois usuarios
     *
     * @param idAceitar Identificador do solicitante da amizade
     */
    public void aceitarAmizade(int idAceitar) {
        // Status do usuario
        this.atualizarStatusUsuario(1);
        this.controlador.aceitarAmizade(idAceitar);
    }

    /**
     * Metodo para negar a amizade do usuario
     *
     * @param idAmizade Identificador do solicitante da amizade
     */
    public void removerAmizade(int idAmizade) {
        // Status do usuario
        this.atualizarStatusUsuario(1);
        this.controlador.removerAmizade(idAmizade);
    }
    
    /**
     * Exclui todas as amizades do usuário que está apagando a conta
     *
     * @param idUsuario id do usuario que esta apagando a conta
     */
    public void removerTodasAmizades(int idUsuario) {
        this.controlador.removerTodasAmizades(idUsuario);
    }

    /**
     * Metodo para capiturar o email e a url para enviar o convite
     */
    public void enviarConvite() {
        // Status do usuario
        this.atualizarStatusUsuario(1);
        try {
            // Capitura o email para convidar
            String email = this.getConvidarEmail();
            // Limpa o campo para a tela
            this.convidarEmail = null;
            // Capitura a url do sistema
            String path = ((HttpServletRequest) FacesContext.getCurrentInstance()
                    .getExternalContext().getRequest()).getRequestURL().toString();
            // Altera a variavel para excluir o restante da url
            path = path.replaceFirst("/faces(.*)", "");
            // Envia o email digitado e a URL para o metodo que envia o email
            this.controlador.enviarConvite(email, path);
            enviarMensagem(FacesMessage.SEVERITY_ERROR, "Email enviado com sucesso para "
                    + email + "!");
        } catch (EmailException e) {
            enviarMensagem(FacesMessage.SEVERITY_ERROR, "Erro ao enviar o convite, tente novamente mais tarde!");
        }
    }

    public String editarLivro() {
        return "editarLivro";
    }
    
    /**
     * Converte uma imagem para apresentar em um componente p:graphicImage     
     * @return Um objeto StreamedContent
     */
    public StreamedContent converterFoto(String pathFile) {        
        // Cria um objeto File com a foto do cliente        
        File imgFile = new File(pathFile);
        
        // Converte o arquivo em um array de bytes
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] fotoCliente = null;
        try {            
            BufferedImage imagem = ImageIO.read(imgFile);
            ImageIO.write(imagem, "PNG", bos);
            bos.flush();  
            fotoCliente = bos.toByteArray();                
        } catch (IOException e) {            
        }        
        
        try {
            return new DefaultStreamedContent(new ByteArrayInputStream(fotoCliente));
        } catch(NullPointerException e) {
            // Nao foi possivel localizar nenhuma foto ...
            return new DefaultStreamedContent();
        }        
    }        
    
    /**
     * Converte uma imagem para apresentar em um componente p:graphicImage     
     * @return Um objeto StreamedContent
     */
    public StreamedContent carregarFotoDefault() {        
        File imgFile = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath(CAMINHO_FOTO_DEFAULT));            
        
        // Converte o arquivo em um array de bytes
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] fotoCliente = null;
        try {            
            BufferedImage imagem = ImageIO.read(imgFile);
            ImageIO.write(imagem, "PNG", bos);
            bos.flush();  
            fotoCliente = bos.toByteArray();                
        } catch (IOException e) {            
        }        
        
        try {
            return new DefaultStreamedContent(new ByteArrayInputStream(fotoCliente));
        } catch(NullPointerException e) {
            // Nao foi possivel localizar nenhuma foto ...
            return new DefaultStreamedContent();
        }        
    }    
    
    /**
     * Cadastra um novo perfil
     * @return 
     */
    public String cadastrarTipoPerfil(){   
        try {
            this.controlador.cadastrarTipoPerfil(tipoPerfil);
            return "tiposPerfis"; 
        } catch (TipoPerfilException ex) {
            enviarMensagem(FacesMessage.SEVERITY_ERROR,"Não foi possível cadastrar este perfil - "+ex.getMessage());
        }
        return "timeline"; 
    }
    
    /**
     * Chamada para a tela de criação de um novo perfil 
     * @return 
     */
    public String novoPerfil(){
        this.tipoPerfil = new TipoPerfil();
        return "novoTipoPerfil"; 
    }
    
    /**
     * Retorna uma lista de perfis
     * @return 
     */
    public List<TipoPerfil> listarTipoPerfil(){   
        return this.controlador.listarTipoPerfil();
    }
    
    /**
     * Retorna a tela de edição do perfil selecionado na lista
     * @param id
     * @return 
     */
    public String editarPerfil(int id){      
        this.tipoPerfil = this.tipoPerfildao.carregarTipoPerfil(id);
        return "novoTipoPerfil";
    }
    /**
     * Cadastra um novo perfil
     * @return 
     */
    public String cadastrarTipoTexto(){   
        try {
            this.controlador.cadastrarTipoTexto(tipoTexto);
            return "tiposTexto"; 
        } catch (TipoTextoException ex) {
            enviarMensagem(FacesMessage.SEVERITY_ERROR,"Não foi possível cadastrar este tipo de texto. - "+ex.getMessage());
        }
        return "timeline"; 
    }
    
    /**
     * Chamada para a tela de criação de um novo perfil 
     * @return 
     */
    public String novoTipoTexto(){
        this.tipoTexto = new TipoTexto();
        return "novoTipoTexto"; 
    }
    /**
     * Retorna uma lista de perfis
     * @return 
     * @throws br.com.witc.excessao.TipoTextoException 
     */
    public List<TipoTexto> listarTipoTexto() throws TipoTextoException{        
        return this.controlador.listarTipoTexto();
    }
    
    /**
     * Retorna a tela de edição do perfil selecionado na lista
     * @param id
     * @return 
     */
    public String editarTipoTexto(int id){ 
        tipoTexto = this.controlador.carregarTipoTexto(id);
        return "novoTipoTexto";
    }
    
    /**
     * Método para salvar o tipo de texto ao usuário para identificar com quais
     * tipos de texto ele se identifica
     */
    public String salvarTipoTextoUsuario(){
        this.controlador.salvarTipoTextoUsuario(this.selectedTiposTextoUsuario, this.usuario.getId());
        return "timeline"; 
    }
    
    /**
     * Método para excluir um tipo de texto em que o usuário nao se 
     * identifica mais
     * @param idTipoTexto
     * @return 
     */
    public String excluirTipoTextoUsuario(int idTipoTexto){
        this.controlador.excluirTipoTextoUsuario(this.usuario.getId(), idTipoTexto);
        return "index.xhtml?faces-redirect=true";
    }
    
    /**
     * Método para excluir todos os tipo de texto com ligação ao usuário que está 
     * apagando a conta 
     * @param idUsuario
     */
    public void excluirTodosTipoTextoUsuario(int idUsuario){
        this.controlador.excluirTodosTipoTextoUsuario(idUsuario);        
    }
    
    /**
     * Envia à viewer uma mensagem com o status da operação
     *
     * @param sev A severidade da mensagem
     * @param msg A mensagem a ser apresentada
     */
    private void enviarMensagem(FacesMessage.Severity sev, String msg) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(sev, msg, ""));
    } 

    /**
     * @return the tiposTextoUsuario
     */
    public List<TipoTexto> getTiposTextoUsuario() {
        return tiposTextoUsuario;
    }

    /**
     * @param tiposTextoUsuario the tiposTextoUsuario to set
     */
    public void setTiposTextoUsuario(List<TipoTexto> tiposTextoUsuario) {
        this.tiposTextoUsuario = tiposTextoUsuario;
    }

    /**
     * @return the selectedTiposTextoUsuario
     */
    public List<TipoTexto> getSelectedTiposTextoUsuario() {
        return selectedTiposTextoUsuario;
    }

    /**
     * @param selectedTiposTextoUsuario the selectedTiposTextoUsuario to set
     */
    public void setSelectedTiposTextoUsuario(List<TipoTexto> selectedTiposTextoUsuario) {
        this.selectedTiposTextoUsuario = selectedTiposTextoUsuario;
    }
    
    public void atualizarStatusUsuario(int status) {
        //Atualizar Status do Usuario
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        AutenticarBean autenticarBean = (AutenticarBean) FacesContext.getCurrentInstance().getApplication()
                .getELResolver().getValue(elContext, null, "autenticarBean");
        autenticarBean.atualizarStatusUsuario(status);
    }
}
