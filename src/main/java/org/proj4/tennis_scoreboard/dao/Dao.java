package org.proj4.tennis_scoreboard.dao;

import java.util.List;

public interface Dao <T> {

    T save(T t);

    List<T> saveAll(List<T> items);

}
