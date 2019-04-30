package top.yzlin.homework.database;

import org.hibernate.query.Query;

public interface OperateQuery<T> {
    void apply(Query<T> query);
}
