package com.springboot3.sb3hxh.Service;

import com.springboot3.sb3hxh.DAO.*;
import com.springboot3.sb3hxh.Model.*;
import jakarta.persistence.*;
import jakarta.transaction.*;
import org.springframework.stereotype.*;

import java.time.*;
import java.util.*;

@Service
public class RecompensadoService implements RecompensadoDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public RecompensadoService(EntityManager theEntityManager) {
        this.entityManager = theEntityManager;
    }

    @Override
    public List<RecompensadoModel> index() {
        TypedQuery<RecompensadoModel> query = entityManager.createQuery("SELECT re FROM RecompensadoModel re " +
                        "INNER JOIN FETCH re.hunter_id h " +
                        "INNER JOIN FETCH re.recompensa_id r " +
                        "WHERE re.deleted_at IS NULL " +
                        "ORDER BY re.id ASC", RecompensadoModel.class);
        return query.getResultList();
    }

    @Override
    public List<RecompensadoModel> indexPagination(int page, int size) {
        TypedQuery<RecompensadoModel> query = entityManager.createQuery("SELECT re FROM RecompensadoModel re " +
                "INNER JOIN FETCH re.hunter_id h " +
                "INNER JOIN FETCH re.recompensa_id r " +
                "WHERE re.deleted_at IS NULL " +
                "ORDER BY re.id ASC", RecompensadoModel.class);
        query.setFirstResult(page * size);
        query.setMaxResults(size);
        return query.getResultList();
    }

    @Override
    public List<RecompensadoModel> searchRecompensado(String search, int page, int size) {
        TypedQuery<RecompensadoModel> query = entityManager.createQuery("SELECT re FROM RecompensadoModel re " +
                "INNER JOIN FETCH re.hunter_id h " +
                "INNER JOIN FETCH re.recompensa_id r " +
                "WHERE re.deleted_at IS NULL " +
                "AND (LOWER(h.nome_hunter) LIKE LOWER(:search) OR LOWER(r.descricao_recompensa) LIKE LOWER(:search)) " +
                "ORDER BY re.id ASC ", RecompensadoModel.class);
        query.setParameter("search", "%" + search + "%");
        query.setFirstResult(page * size);
        query.setMaxResults(size);
        return query.getResultList();
    }

    @Override
    @Transactional
    public RecompensadoModel create(RecompensadoModel theRecompensadoModel) {
        entityManager.persist(theRecompensadoModel);
        return theRecompensadoModel;
    }

    @Override
    public RecompensadoModel read(int id) {
        return entityManager.find(RecompensadoModel.class, id);
    }

    @Override
    @Transactional
    public RecompensadoModel update(RecompensadoModel theRecompensadoModel) {
        RecompensadoModel recompensadoModel = entityManager.merge(theRecompensadoModel);
        return recompensadoModel;
    }

    @Override
    @Transactional
    public void trash(int id) {
        RecompensadoModel recompensadoModel = entityManager.find(RecompensadoModel.class, id);
        if (recompensadoModel != null) {
            recompensadoModel.setDeleted_at(LocalDateTime.now());
            entityManager.merge(recompensadoModel);
        }
    }

    @Override
    public List<RecompensadoModel> indexTrash(int page, int size) {
        TypedQuery<RecompensadoModel> query = entityManager.createQuery("SELECT re FROM RecompensadoModel re " +
                "INNER JOIN FETCH re.hunter_id h " +
                "INNER JOIN FETCH re.recompensa_id r " +
                "WHERE re.deleted_at IS NOT NULL " +
                "ORDER BY re.id ASC", RecompensadoModel.class);
        query.setFirstResult(page * size);
        query.setMaxResults(size);
        return query.getResultList();
    }

    @Override
    public List<RecompensadoModel> searchRecompensadoTrash(String search, int page, int size) {
        TypedQuery<RecompensadoModel> query = entityManager.createQuery("SELECT re FROM RecompensadoModel re " +
                "INNER JOIN FETCH re.hunter_id h " +
                "INNER JOIN FETCH re.recompensa_id r " +
                "WHERE re.deleted_at IS NOT NULL " +
                "AND (LOWER(h.nome_hunter) LIKE LOWER(:search) OR LOWER(r.descricao_recompensa) LIKE LOWER(:search)) " +
                "ORDER BY re.id ASC ", RecompensadoModel.class);
        query.setParameter("search", "%" + search + "%");
        query.setFirstResult(page * size);
        query.setMaxResults(size);
        return query.getResultList();
    }

    @Override
    @Transactional
    public RecompensadoModel restore(int id) {
        RecompensadoModel recompensadoModel = entityManager.find(RecompensadoModel.class, id);
        if (recompensadoModel != null) {
            recompensadoModel.setDeleted_at(null);
            entityManager.persist(recompensadoModel);
        } else {
            throw new IllegalArgumentException("Registro não encontrado com o ID fornecido: " + id);
        }
        return recompensadoModel;
    }

    @Override
    @Transactional
    public void delete(int id) {
        RecompensadoModel recompensadoModel = entityManager.find(RecompensadoModel.class,id);
        entityManager.remove(recompensadoModel);
    }

    @Override
    public int totalRecompensados() {
        TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT(h) FROM RecompensadoModel h", Long.class);
        return query.getSingleResult().intValue();
    }

}