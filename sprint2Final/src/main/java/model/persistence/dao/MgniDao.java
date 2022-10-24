package model.persistence.dao;

import model.Mgni;
import model.persistence.CrudOperations;

import java.util.List;
import java.util.function.Predicate;

public interface MgniDao extends CrudOperations<Mgni,String>{
    List<Mgni> findAllBy(Predicate<Mgni> predicate);

}
