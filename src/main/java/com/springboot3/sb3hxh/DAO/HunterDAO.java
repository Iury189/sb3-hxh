package com.springboot3.sb3hxh.DAO;

import com.springboot3.sb3hxh.Entity.HunterEntity;

import java.util.*;

public interface HunterDAO {

    List<HunterEntity> index();
    List<HunterEntity> indexPagination(int page, int size);
    HunterEntity create(HunterEntity theHunterEntity);
    HunterEntity read(int id);
    HunterEntity update(HunterEntity theHunterEntity);
    void trash(int id);
    List<HunterEntity> indexTrash(int page, int size);
    HunterEntity restore(int id);
    void delete(int id);
    List<HunterEntity> searchHunter(String search, int page, int size);
    List<HunterEntity> searchHunterTrash(String search, int page, int size);
    int totalHunters();
    boolean existsId(String id);

}