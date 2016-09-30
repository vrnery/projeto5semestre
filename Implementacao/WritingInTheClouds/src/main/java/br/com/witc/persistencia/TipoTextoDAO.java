/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.witc.persistencia;

import br.com.witc.modelo.TipoTexto;
import static br.com.witc.persistencia.HibernateUtil.getSessionFactory;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

/**
 *
 * @author root
 */
public class TipoTextoDAO {
    Session sessao;
    
    public TipoTextoDAO() {
        this.sessao = getSessionFactory().getCurrentSession();
    }
    
    /**
     * Retorna um objeto tipo texto com os dados daquele objeto do banco de dados
     * @param id
     * @return 
     */
    public TipoTexto carregarTipoTexto(int id){
       return  (TipoTexto) sessao.load(TipoTexto.class,id);
    }
    
    /**
     * 
     * @param aThis 
     */
    public void salvarTipoTexto(TipoTexto aThis) {       
        try{
            sessao.saveOrUpdate(aThis);
        }catch(ConstraintViolationException e){
             sessao.clear();
        }    
    }
    
    /**
     * Retorna uma lista de todos os campos da tabela tipo perfil
     * @return 
     */
    public List<TipoTexto> listarTiposTexto() {  
        List<TipoTexto> lista;
        lista = (List<TipoTexto>) sessao.createQuery("from TipoTexto").list();        
        return lista;
    }
}
