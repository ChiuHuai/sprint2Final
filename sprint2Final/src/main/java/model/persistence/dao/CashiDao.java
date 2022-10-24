package model.persistence.dao;

import model.Cashi;
import model.CashiId;
import model.persistence.CrudOperations;

import java.util.List;
import java.util.function.Predicate;

;

public interface CashiDao extends CrudOperations<Cashi, CashiId> {

    List<Cashi> findAllBy(Predicate<Cashi> predicate);
}
