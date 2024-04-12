package com.springboot3.sb3hxh.Service;

import com.springboot3.sb3hxh.DAO.*;
import com.springboot3.sb3hxh.Entity.*;
import jakarta.persistence.*;
import org.springframework.stereotype.*;
import jakarta.transaction.*;

import java.time.*;
import java.util.*;

@Service
public class RecompensaService implements RecompensaDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public RecompensaService(EntityManager theEntityManager) {
        this.entityManager = theEntityManager;
    }

    @Override
    public List<RecompensaEntity> index() {
        TypedQuery<RecompensaEntity> query = entityManager.createQuery("SELECT r FROM RecompensaEntity r WHERE r.deleted_at IS NULL ORDER BY r.id ASC", RecompensaEntity.class);
        return query.getResultList();
    }

    @Override
    public List<RecompensaEntity> indexPagination(int page, int size) {
        TypedQuery<RecompensaEntity> query = entityManager.createQuery("SELECT r FROM RecompensaEntity r WHERE r.deleted_at IS NULL ORDER BY r.id ASC", RecompensaEntity.class);
        query.setFirstResult(page * size);
        query.setMaxResults(size);
        return query.getResultList();
    }

    public List<RecompensaEntity> searchRecompensa(String search, int page, int size) {
        TypedQuery<RecompensaEntity> query = entityManager.createQuery("SELECT r FROM RecompensaEntity r " +
                "WHERE r.deleted_at IS NULL " +
                "AND LOWER(r.descricao_recompensa) LIKE LOWER(:search) " +
                "ORDER BY r.id ASC", RecompensaEntity.class);
        query.setParameter("search", "%" + search + "%");
        query.setFirstResult(page * size);
        query.setMaxResults(size);
        return query.getResultList();
    }

    @Override
    @Transactional
    public RecompensaEntity create(RecompensaEntity theRecompensaEntity) {
        entityManager.persist(theRecompensaEntity);
        return theRecompensaEntity;
    }

    @Override
    public RecompensaEntity read(int id) {
        return entityManager.find(RecompensaEntity.class, id);
    }

    @Override
    @Transactional
    public RecompensaEntity update(RecompensaEntity theRecompensaEntity) {
        RecompensaEntity recompensaEntity = entityManager.merge(theRecompensaEntity);
        return recompensaEntity;
    }

    @Override
    @Transactional
    public void trash(int id) {
        RecompensaEntity recompensaEntity = entityManager.find(RecompensaEntity.class, id);
        if (recompensaEntity != null) {
            recompensaEntity.setDeletedAt(LocalDateTime.now());
            entityManager.merge(recompensaEntity);
        }
    }

    @Override
    public List<RecompensaEntity> indexTrash(int page, int size) {
        TypedQuery<RecompensaEntity> query = entityManager.createQuery("SELECT r FROM RecompensaEntity r WHERE r.deleted_at IS NOT NULL ORDER BY r.id ASC", RecompensaEntity.class);
        query.setFirstResult(page * size);
        query.setMaxResults(size);
        return query.getResultList();
    }

    public List<RecompensaEntity> searchRecompensaTrash(String search, int page, int size) {
        TypedQuery<RecompensaEntity> query = entityManager.createQuery("SELECT r FROM RecompensaEntity r " +
                "WHERE r.deleted_at IS NOT NULL " +
                "AND LOWER(r.descricao_recompensa) LIKE LOWER(:search) " +
                "ORDER BY r.id ASC", RecompensaEntity.class);
        query.setParameter("search", "%" + search + "%");
        query.setFirstResult(page * size);
        query.setMaxResults(size);
        return query.getResultList();
    }

    @Override
    @Transactional
    public RecompensaEntity restore(int id) {
        RecompensaEntity recompensaEntity = entityManager.find(RecompensaEntity.class, id);
        if (recompensaEntity != null) {
            recompensaEntity.setDeletedAt(null);
            entityManager.persist(recompensaEntity);
        } else {
            throw new IllegalArgumentException("Registro não encontrado com o ID fornecido: " + id);
        }
        return recompensaEntity;
    }

    @Override
    @Transactional
    public void delete(int id) {
        RecompensaEntity recompensaEntity = entityManager.find(RecompensaEntity.class,id);
        entityManager.remove(recompensaEntity);
    }

    @Override
    public int totalRecompensas() {
        TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT(h) FROM RecompensaEntity h", Long.class);
        return query.getSingleResult().intValue();
    }

    @Override
    public int totalRecompensasBySearch(String search) {
        TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT(r) FROM RecompensaEntity r WHERE r.deleted_at IS NULL AND LOWER(r.descricao_recompensa) LIKE LOWER(:search)", Long.class);
        query.setParameter("search", "%" + search + "%");
        return query.getSingleResult().intValue();
    }

    @Override
    public int totalRecompensasTrashBySearch(String search) {
        TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT(r) FROM RecompensaEntity r WHERE r.deleted_at IS NOT NULL AND LOWER(r.descricao_recompensa) LIKE LOWER(:search)", Long.class);
        query.setParameter("search", "%" + search + "%");
        return query.getSingleResult().intValue();
    }

    @Override
    public boolean existsId(String id) {
        try {
            int idAsInt = Integer.parseInt(id);
            RecompensaEntity recompensaEntity = entityManager.find(RecompensaEntity.class, idAsInt);
            return recompensaEntity != null;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}