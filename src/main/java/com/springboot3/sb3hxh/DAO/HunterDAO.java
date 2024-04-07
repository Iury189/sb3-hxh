package com.springboot3.sb3hxh.DAO;

import com.springboot3.sb3hxh.Model.HunterModel;

import java.util.*;

public interface HunterDAO {

    List<HunterModel> index();
    List<HunterModel> indexPagination(int page, int size);
    HunterModel create(HunterModel theHunterModel);
    HunterModel read(int id);
    HunterModel update(HunterModel theHunterModel);
    void trash(int id);
    List<HunterModel> indexTrash(int page, int size);
    HunterModel restore(int id);
    void delete(int id);
    List<HunterModel> searchHunter(String search, int page, int size);
    List<HunterModel> searchHunterTrash(String search, int page, int size);
    int totalHunters();

}