package com.springboot3.sb3hxh.DAO;

import com.springboot3.sb3hxh.Entity.RecompensaEntity;

import java.util.*;

public interface RecompensaDAO {

    List<RecompensaEntity> index();
    List<RecompensaEntity> indexPagination(int page, int size);
    RecompensaEntity create(RecompensaEntity theRecompensaEntity);
    RecompensaEntity read(int id);
    RecompensaEntity update(RecompensaEntity theRecompensaEntity);
    void trash(int id);
    List<RecompensaEntity> indexTrash(int page, int size);
    RecompensaEntity restore(int id);
    void delete(int id);
    List<RecompensaEntity> searchRecompensa(String search, int page, int size);
    List<RecompensaEntity> searchRecompensaTrash(String search, int page, int size);
    int totalRecompensas();
    boolean existsId(String id);

}