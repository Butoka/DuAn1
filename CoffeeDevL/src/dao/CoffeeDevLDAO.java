
package dao;

import java.util.List;

public abstract class CoffeeDevLDAO<Entity, Key> {
     abstract public void insert(Entity entity);

    abstract public void update(Entity entity);

    abstract public void delete(Key id);

    abstract public Entity selectById(Key id);

    abstract public List<Entity> selectAll();

    abstract public List<Entity> selectBySql(String sqlString, Object... args);
}
