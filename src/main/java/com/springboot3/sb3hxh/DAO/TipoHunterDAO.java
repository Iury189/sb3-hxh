package com.springboot3.sb3hxh.DAO;

import com.springboot3.sb3hxh.Entity.TipoHunterEntity;

import java.util.*;

public interface TipoHunterDAO {

    List<TipoHunterEntity> index();
    TipoHunterEntity read(int id);
    boolean existsId(String id);

}