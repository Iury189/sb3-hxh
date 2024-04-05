package com.springboot3.sb3hxh.Service;

import com.springboot3.sb3hxh.DAO.*;
import com.springboot3.sb3hxh.Model.*;
import jakarta.persistence.*;
import jakarta.transaction.*;
import org.springframework.stereotype.*;

import java.time.*;
import java.util.*;

@Service
public class HunterService implements HunterDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public HunterService(EntityManager theEntityManager) {
        this.entityManager = theEntityManager;
    }

    @Override
    public List<HunterModel> index() {
        TypedQuery<HunterModel> query = entityManager.createQuery("SELECT h FROM HunterModel h " +
                "INNER JOIN FETCH h.tipo_hunter_id th " +
                "INNER JOIN FETCH h.tipo_nen_id tn " +
                "INNER JOIN FETCH h.tipo_sanguineo_id ts " +
                "WHERE h.deleted_at IS NULL " +
                "ORDER BY h.id ASC", HunterModel.class);
        return query.getResultList();
    }

    @Override
    public List<HunterModel> searchHunter(String search) {
        TypedQuery<HunterModel> query = entityManager.createQuery("SELECT h FROM HunterModel h " +
                "INNER JOIN FETCH h.tipo_hunter_id th " +
                "INNER JOIN FETCH h.tipo_nen_id tn " +
                "INNER JOIN FETCH h.tipo_sanguineo_id ts " +
                "WHERE h.deleted_at IS NULL " +
                "AND LOWER(h.nome_hunter) LIKE LOWER(:search) " +
                "ORDER BY h.id ASC", HunterModel.class);
        query.setParameter("search", "%" + search + "%");
        return query.getResultList();
    }

    @Override
    @Transactional
    public HunterModel create(HunterModel theHunterModel) {
        entityManager.persist(theHunterModel);
        return theHunterModel;
    }

    @Override
    public HunterModel read(int id) {
        return entityManager.find(HunterModel.class, id);
    }

    @Override
    @Transactional
    public HunterModel update(HunterModel theHunterModel) {
        HunterModel hunterModel = entityManager.merge(theHunterModel);
        return hunterModel;
    }

    @Override
    @Transactional
    public void trash(int id) {
        HunterModel hunterModel = entityManager.find(HunterModel.class, id);
        if (hunterModel != null) {
            hunterModel.setDeleted_at(LocalDateTime.now());
            entityManager.merge(hunterModel);
        }
    }

    @Override
    public List<HunterModel> indexTrash() {
        TypedQuery<HunterModel> query = entityManager.createQuery("SELECT h FROM HunterModel h " +
                "INNER JOIN FETCH h.tipo_hunter_id th " +
                "INNER JOIN FETCH h.tipo_nen_id tn " +
                "INNER JOIN FETCH h.tipo_sanguineo_id ts " +
                "WHERE h.deleted_at IS NOT NULL " +
                "ORDER BY h.id ASC", HunterModel.class);
        return query.getResultList();
    }

    @Override
    public List<HunterModel> searchHunterTrash(String search) {
        TypedQuery<HunterModel> query = entityManager.createQuery("SELECT h FROM HunterModel h " +
                "INNER JOIN FETCH h.tipo_hunter_id th " +
                "INNER JOIN FETCH h.tipo_nen_id tn " +
                "INNER JOIN FETCH h.tipo_sanguineo_id ts " +
                "WHERE h.deleted_at IS NOT NULL " +
                "AND LOWER(h.nome_hunter) LIKE LOWER(:search) " +
                "ORDER BY h.id ASC", HunterModel.class);
        query.setParameter("search", "%" + search + "%");
        return query.getResultList();
    }

    @Override
    @Transactional
    public HunterModel restore(int id) {
        HunterModel hunterModel = entityManager.find(HunterModel.class, id);
        if (hunterModel != null) {
            hunterModel.setDeleted_at(null);
            entityManager.persist(hunterModel);
        } else {
            throw new IllegalArgumentException("Registro não encontrado com o ID fornecido: " + id);
        }
        return hunterModel;
    }

    @Override
    @Transactional
    public void delete(int id) {
        HunterModel hunterModel = entityManager.find(HunterModel.class,id);
        entityManager.remove(hunterModel);
    }

}