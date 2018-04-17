package br.usp.poli.utils;

import static br.usp.poli.enums.Acao.DELETED;
import static br.usp.poli.enums.Acao.INSERTED;
import static br.usp.poli.enums.Acao.UPDATED;
import static javax.transaction.Transactional.TxType.MANDATORY;

import javax.persistence.EntityManager;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.transaction.Transactional;

import br.usp.poli.entity.Historico;
import br.usp.poli.enums.Acao;

public class HistoricoEntityListener {

    @PrePersist
    public <T> void prePersist(Audit entidade) {
        perform(entidade,INSERTED);
    }

    @PreUpdate
    public <T> void preUpdate(Audit entidade) {
        perform(entidade, UPDATED);
    }

    @PreRemove
    public <T> void preRemove(Audit entidade) {
        perform(entidade, DELETED);
    }

    @Transactional(MANDATORY)
    private void perform(Audit entidade, Acao action) {
        EntityManager entityManager = BeanUtil.getBean(EntityManager.class);
        entityManager.persist(new Historico(entidade, action, entidade.getTela()));
    }

}