package com.springboot3.sb3hxh.DAO;

import com.springboot3.sb3hxh.Entity.RecompensadoEntity;

import java.util.*;

public interface RecompensadoDAO {

    List<RecompensadoEntity> index();
    List<RecompensadoEntity> indexPagination(int page, int size);
    RecompensadoEntity create(RecompensadoEntity theRecompensadoEntity);
    RecompensadoEntity read(int id);
    RecompensadoEntity update(RecompensadoEntity theRecompensadoEntity);
    void trash(int id);
    List<RecompensadoEntity> indexTrash(int page, int size);
    RecompensadoEntity restore(int id);
    void delete(int id);
    List<RecompensadoEntity> searchRecompensado(String search, int page, int size);
    List<RecompensadoEntity> searchRecompensadoTrash(String search, int page, int size);
    int totalRecompensados();
    int totalRecompensadosBySearch(String search);
    int totalRecompensadosTrashBySearch(String search);

}