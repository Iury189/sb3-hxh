package com.springboot3.sb3hxh.Service;

import com.springboot3.sb3hxh.DAO.*;
import com.springboot3.sb3hxh.Entity.*;
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
    public List<RecompensadoEntity> index() {
        TypedQuery<RecompensadoEntity> query = entityManager.createQuery("SELECT re FROM RecompensadoEntity re " +
                        "INNER JOIN FETCH re.hunter_id h " +
                        "INNER JOIN FETCH re.recompensa_id r " +
                        "WHERE re.deleted_at IS NULL " +
                        "ORDER BY re.id ASC", RecompensadoEntity.class);
        return query.getResultList();
    }

    @Override
    public List<RecompensadoEntity> indexPagination(int page, int size) {
        TypedQuery<RecompensadoEntity> query = entityManager.createQuery("SELECT re FROM RecompensadoEntity re " +
                "INNER JOIN FETCH re.hunter_id h " +
                "INNER JOIN FETCH re.recompensa_id r " +
                "WHERE re.deleted_at IS NULL " +
                "ORDER BY re.id ASC", RecompensadoEntity.class);
        query.setFirstResult(page * size);
        query.setMaxResults(size);
        return query.getResultList();
    }

    @Override
    public List<RecompensadoEntity> searchRecompensado(String search, int page, int size) {
        TypedQuery<RecompensadoEntity> query = entityManager.createQuery("SELECT re FROM RecompensadoEntity re " +
                "INNER JOIN FETCH re.hunter_id h " +
                "INNER JOIN FETCH re.recompensa_id r " +
                "WHERE re.deleted_at IS NULL " +
                "AND (LOWER(h.nome_hunter) LIKE LOWER(:search) OR LOWER(r.descricao_recompensa) LIKE LOWER(:search)) " +
                "ORDER BY re.id ASC ", RecompensadoEntity.class);
        query.setParameter("search", "%" + search + "%");
        query.setFirstResult(page * size);
        query.setMaxResults(size);
        return query.getResultList();
    }

    @Override
    @Transactional
    public RecompensadoEntity create(RecompensadoEntity theRecompensadoEntity) {
        entityManager.persist(theRecompensadoEntity);
        return theRecompensadoEntity;
    }

    @Override
    public RecompensadoEntity read(int id) {
        return entityManager.find(RecompensadoEntity.class, id);
    }

    @Override
    @Transactional
    public RecompensadoEntity update(RecompensadoEntity theRecompensadoEntity) {
        RecompensadoEntity recompensadoEntity = entityManager.merge(theRecompensadoEntity);
        return recompensadoEntity;
    }

    @Override
    @Transactional
    public void trash(int id) {
        RecompensadoEntity recompensadoEntity = entityManager.find(RecompensadoEntity.class, id);
        if (recompensadoEntity != null) {
            recompensadoEntity.setDeletedAt(LocalDateTime.now());
            entityManager.merge(recompensadoEntity);
        }
    }

    @Override
    public List<RecompensadoEntity> indexTrash(int page, int size) {
        TypedQuery<RecompensadoEntity> query = entityManager.createQuery("SELECT re FROM RecompensadoEntity re " +
                "INNER JOIN FETCH re.hunter_id h " +
                "INNER JOIN FETCH re.recompensa_id r " +
                "WHERE re.deleted_at IS NOT NULL " +
                "ORDER BY re.id ASC", RecompensadoEntity.class);
        query.setFirstResult(page * size);
        query.setMaxResults(size);
        return query.getResultList();
    }

    @Override
    public List<RecompensadoEntity> searchRecompensadoTrash(String search, int page, int size) {
        TypedQuery<RecompensadoEntity> query = entityManager.createQuery("SELECT re FROM RecompensadoEntity re " +
                "INNER JOIN FETCH re.hunter_id h " +
                "INNER JOIN FETCH re.recompensa_id r " +
                "WHERE re.deleted_at IS NOT NULL " +
                "AND (LOWER(h.nome_hunter) LIKE LOWER(:search) OR LOWER(r.descricao_recompensa) LIKE LOWER(:search)) " +
                "ORDER BY re.id ASC ", RecompensadoEntity.class);
        query.setParameter("search", "%" + search + "%");
        query.setFirstResult(page * size);
        query.setMaxResults(size);
        return query.getResultList();
    }

    @Override
    @Transactional
    public RecompensadoEntity restore(int id) {
        RecompensadoEntity recompensadoEntity = entityManager.find(RecompensadoEntity.class, id);
        if (recompensadoEntity != null) {
            recompensadoEntity.setDeletedAt(null);
            entityManager.persist(recompensadoEntity);
        } else {
            throw new IllegalArgumentException("Registro não encontrado com o ID fornecido: " + id);
        }
        return recompensadoEntity;
    }

    @Override
    @Transactional
    public void delete(int id) {
        RecompensadoEntity recompensadoEntity = entityManager.find(RecompensadoEntity.class,id);
        entityManager.remove(recompensadoEntity);
    }

    @Override
    public int totalRecompensados() {
        TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT(re) FROM RecompensadoEntity re", Long.class);
        return query.getSingleResult().intValue();
    }

    @Override
    public int totalRecompensadosBySearch(String search) {
        TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT(re) FROM RecompensadoEntity re " +
                "INNER JOIN re.hunter_id h " +
                "INNER JOIN re.recompensa_id r " +
                "WHERE re.deleted_at IS NULL " +
                "AND (LOWER(h.nome_hunter) LIKE LOWER(:search) OR LOWER(r.descricao_recompensa) LIKE LOWER(:search))", Long.class);
        query.setParameter("search", "%" + search + "%");
        return query.getSingleResult().intValue();
    }

    @Override
    public int totalRecompensadosTrashBySearch(String search) {
        TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT(re) FROM RecompensadoEntity re " +
                "INNER JOIN re.hunter_id h " +
                "INNER JOIN re.recompensa_id r " +
                "WHERE re.deleted_at IS NOT NULL " +
                "AND (LOWER(h.nome_hunter) LIKE LOWER(:search) OR LOWER(r.descricao_recompensa) LIKE LOWER(:search))", Long.class);
        query.setParameter("search", "%" + search + "%");
        return query.getSingleResult().intValue();
    }

}