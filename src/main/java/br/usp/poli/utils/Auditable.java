package br.usp.poli.utils;

import static javax.persistence.TemporalType.TIMESTAMP;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable {
	
	@CreatedBy
    protected String usuario;

    @CreatedDate
    @Temporal(TIMESTAMP)
    @Column(name="data_criacao",updatable=false)
    protected Date dataCriacao;

    @LastModifiedBy
    @Column(name="ultimo_usuario")
    protected String ultimoUsuario;

    @LastModifiedDate
    @Temporal(TIMESTAMP)
    @Column(name="data_alteracao")
    protected Date dataAlteracao;


}
