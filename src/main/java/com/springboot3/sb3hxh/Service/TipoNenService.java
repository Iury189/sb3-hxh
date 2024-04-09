package com.springboot3.sb3hxh.Service;

import com.springboot3.sb3hxh.DAO.*;
import com.springboot3.sb3hxh.Entity.*;
import jakarta.persistence.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class TipoNenService implements TipoNenDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public TipoNenService(EntityManager theEntityManager) {
        this.entityManager = theEntityManager;
    }

    @Override
    public List<TipoNenEntity> index() {
        TypedQuery<TipoNenEntity> query = entityManager.createQuery("SELECT tn FROM TipoNenEntity tn WHERE tn.deleted_at IS NULL ORDER BY tn.id ASC", TipoNenEntity.class);
        return query.getResultList();
    }

    public TipoNenEntity read(int id) {
        return entityManager.find(TipoNenEntity.class, id);
    }

    @Override
    public boolean existsId(String id) {
        try {
            int idAsInt = Integer.parseInt(id);
            TipoNenEntity tipoNenEntity = entityManager.find(TipoNenEntity.class, idAsInt);
            return tipoNenEntity != null;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}