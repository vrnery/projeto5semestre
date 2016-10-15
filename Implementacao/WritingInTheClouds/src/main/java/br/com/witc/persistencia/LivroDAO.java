/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.witc.persistencia;

import br.com.witc.excessao.BibliotecaVirtualVaziaException;
import br.com.witc.excessao.LivroException;
import br.com.witc.modelo.Livro;
import br.com.witc.modelo.Perfil;
import br.com.witc.modelo.TipoTexto;
import static br.com.witc.persistencia.HibernateUtil.getSessionFactory;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;

/**
 *
 * @author root
 */
public class LivroDAO {
    Session sessao;

    /**
     *
     */
    public LivroDAO() {
        sessao = getSessionFactory().getCurrentSession();
    }
    
    /**
     *
     * @param livro
     */
    public void salvarLivro(Livro livro){
        
        try {
           sessao.saveOrUpdate(livro);
           
            } catch (ConstraintViolationException e) {
          
                sessao.clear();
             
            } 
        }
    /**
     * 
     * @param perfil
     * @return 
     
    public List<Livro> listarLivrosPerfil(Perfil perfil){
        String consulta ="select livro.* from livro inner join historicolivros on livro.id=historicolivros.idLivro inner join perfil on perfil.id = historicolivros.idPerfil  where idPerfil=:id";
        List<Livro> lista ;
        lista= sessao.createSQLQuery(consulta).addEntity("livro",Livro.class).setInteger("id",perfil.getId()).list();
        return lista;
    }*/

    /**
     * Carrega um livro 
     * @param idLivro O id do livro a ser carregado
     * @return Livro Um objeto livro
     * @throws br.com.witc.excessao.LivroException Caso o livro não seja encontrado
     */
    public Livro carregarLivro(int idLivro) throws LivroException {        
        Livro tmpLivro = (Livro) sessao.load(Livro.class, idLivro);
        if (tmpLivro == null) {
            throw new LivroException("Nenhum livro encontrado com id " + idLivro);
        }
        return tmpLivro;
    }
    
    /**
     * Lista os livros publicados na Biblioteca Virtual
     * @param tp O Tipo Texto para pesquisa
     * @return Uma lista de livros publicados na Biblioteca Virtual
     * @throws br.com.witc.excessao.BibliotecaVirtualVaziaException Caso não haja livros publicados na Biblioteca
     */
    public List<Livro> listarLivrosPorTipoTexto(TipoTexto tp) throws BibliotecaVirtualVaziaException {
        List<Livro> tmpLstLivro = sessao.createCriteria(Livro.class)
                .add(Restrictions.like("disponivelBiblioteca", true))
                .add(Restrictions.eq("tipoTexto", tp))
                .addOrder(Order.asc("tipoTexto"))
                .list();
        
        if (tmpLstLivro.isEmpty()) {
            throw new BibliotecaVirtualVaziaException("Nenhum livro foi publicado até o momento.");
        }
        return tmpLstLivro;
    }
    
    /**
     * Lista os livros publicados na Biblioteca Virtual     
     * @param tp O Tipo Texto para pesquisa
     * @param campoPesquisa O campo a ser pesquisado
     * @param valorPesquisa O valor a ser pesquisado
     * @return Uma lista de livros publicados na Biblioteca Virtual
     * @throws br.com.witc.excessao.BibliotecaVirtualVaziaException Caso não haja livros publicados na Biblioteca
     */    
    public List<Livro> listarLivrosPublicados(TipoTexto tp, String campoPesquisa, String valorPesquisa) 
            throws BibliotecaVirtualVaziaException {        
        String sql = "FROM Livro AS l ";
        switch (campoPesquisa) {
            case "autor":
                sql += "INNER JOIN l.tipoTexto AS tp "
                     + "INNER JOIN l.historicoLivros AS hl "
                     + "INNER JOIN hl.perfil AS p "
                     + "INNER JOIN p.usuario AS u "
                     + "WHERE tp.tipoTexto = '" + tp.getTipoTexto() + "' AND u.nome LIKE ";
                break;
            case "classificacao":
                sql += "INNER JOIN l.tipoTexto tp "
                     + "WHERE tp.tipoTexto = '" + tp.getTipoTexto() + "' AND l.classificacao LIKE ";
                break;
            case "tipoTexto":
                sql += "INNER JOIN l.tipoTexto tp  "
                     + "WHERE tp.tipoTexto LIKE ";
                break;
            case "titulo":
                sql += "INNER JOIN l.tipoTexto tp  "
                     + "WHERE tp.tipoTexto = '" + tp.getTipoTexto() + "' AND l.titulo LIKE ";
        }
        sql += "'%" + valorPesquisa + "%' ORDER BY l.tipoTexto";
                        
        List<Livro> tmpLstLivro = new ArrayList();
        List<Object[]> lstResult = sessao.createQuery(sql).list();
        for (Object[] o : lstResult) {            
            for (Object o1 : o) {
                if (o1 instanceof Livro) {
                    tmpLstLivro.add((Livro) o1);
                    break;
                }
            }
        }
                
        if (tmpLstLivro.isEmpty()) {
            throw new BibliotecaVirtualVaziaException("Não foi possível localizar livros com os critérios informados.");
        }
        return tmpLstLivro;
    }    
    
    /**
     * Verifica se o livro está disponível para edição do usuário logado
     * @param idLivro O id do Livro
     * @param idPerfil O id do perfil do usuário logado
     * @return True, caso o livro esteja disponível para edição e false, caso contrário
     */
    public boolean estaDisponivelEdicaoUsuario(int idLivro, int idPerfil) {
        Livro livro = (Livro) sessao.createQuery("FROM Livro WHERE id=:idLivro")
                .setString("idLivro", String.valueOf(idLivro))                
                .uniqueResult();
        
        sessao.refresh(livro);
        return (livro.getBookLock() == idPerfil) || (livro.getBookLock() == 0);
    }
    
    /**     
     * @param idPerfil O id do perfil do usuário
     * @return Uma lista de livros publicados pelo usuário
     */
    public List<Livro> listarLivrosPublicadosPerfil(int idPerfil) {
        return (List<Livro>) sessao.createQuery("FROM Livro AS l "
                + "INNER JOIN l.historicoLivros AS hl "
                + "WHERE h1.perfil=:idPerfil AND l.disponivelBiblioteca = true")                 
                .setString("idPerfil", String.valueOf(idPerfil))
                .list();
    }
    
    public Livro carregarHistoricoConvite(int livro) {
        return (Livro) sessao.createQuery("FROM Livro WHERE id=:liv")
                .setParameter("liv", livro)
                .uniqueResult();
    }
}
