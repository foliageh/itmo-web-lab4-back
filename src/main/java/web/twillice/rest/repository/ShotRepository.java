package web.twillice.rest.repository;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import web.twillice.rest.model.Shot;
import web.twillice.rest.model.User;

import java.util.List;

@Stateless
public class ShotRepository {
    @PersistenceContext
    private EntityManager db;

    public Shot save(Shot shot) {
        db.persist(shot);
        return shot;
    }

    public List<Shot> findByUserId(Long userId) {
        return db.createQuery("from Shot s where s.user = :user", Shot.class).setParameter("user", userId).getResultList();
    }

    public List<Shot> findByUser(User user) {
        return db.createQuery("from Shot s where s.user = :user", Shot.class).setParameter("user", user).getResultList();
    }

    public List<Shot> findAll() {
        return db.createQuery("from Shot", Shot.class).getResultList();
    }
}
