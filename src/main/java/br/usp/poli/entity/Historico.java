package br.usp.poli.entity;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.TemporalType.TIMESTAMP;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import br.usp.poli.enums.Acao;
import br.usp.poli.enums.TelaCadastro;
import br.usp.poli.utils.Audit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Juan Leiro
 */

//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@EqualsAndHashCode(callSuper=false)
//@Builder
//@Entity
//@EntityListeners(AuditingEntityListener.class)
public class Historico {
    
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq_id_historico")
	@SequenceGenerator(name="seq_id_historico", sequenceName="seq_id_historico",allocationSize=1)
    @Column(name="id_historico")
    private Integer id;
    
	//TODO: usuario
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "usuario_id")
//    private Usuario usuario;

    @Lob
    @Column(name="conteudo")
    private String conteudo;

    @CreatedBy
    @Column(name="nome_usuario")
    private String nomeUsuario;

    @CreatedDate
    @Temporal(TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd  hh:mm")
    @Column(name="data_alteracao")
    private Date dataAlteracao;
    
    @Enumerated(STRING)
    @Column(name="tela")
    private TelaCadastro tela;

    @Enumerated(STRING)
    @Column(name="acao")
    private Acao acao;
    
    public <T> Historico(Audit target, Acao acao, TelaCadastro tela) {
    	//this.usuario = (Usuario) target;
        this.conteudo = target.toString();
        this.tela = tela;
        this.acao = acao;
    }

}