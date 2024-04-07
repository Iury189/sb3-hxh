package com.springboot3.sb3hxh.DAO;

import com.springboot3.sb3hxh.Model.RecompensaModel;

import java.util.*;

public interface RecompensaDAO {

    List<RecompensaModel> index();
    List<RecompensaModel> indexPagination(int page, int size);
    RecompensaModel create(RecompensaModel theRecompensaModel);
    RecompensaModel read(int id);
    RecompensaModel update(RecompensaModel theRecompensaModel);
    void trash(int id);
    List<RecompensaModel> indexTrash(int page, int size);
    RecompensaModel restore(int id);
    void delete(int id);
    List<RecompensaModel> searchRecompensa(String search, int page, int size);
    List<RecompensaModel> searchRecompensaTrash(String search, int page, int size);
    int totalRecompensas();

}