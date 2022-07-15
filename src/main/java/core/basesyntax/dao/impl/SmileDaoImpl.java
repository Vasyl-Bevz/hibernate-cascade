package core.basesyntax.dao.impl;

import core.basesyntax.dao.SmileDao;
import core.basesyntax.model.Smile;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class SmileDaoImpl extends AbstractDao implements SmileDao {
    public SmileDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Smile create(Smile entity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.save(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Cant create smile: " + entity);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entity;
    }

    @Override
    public Smile get(Long id) {
        Smile smile = null;
        try (Session session = factory.openSession();) {
            smile = session.get(Smile.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Cant get smile by id: " + id);
        }
        return smile;
    }

    @Override
    public List<Smile> getAll() {
        List<Smile> smiles = new ArrayList<>();
        try (Session session = factory.openSession();) {
            smiles = session.createQuery("FROM Smile").getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Cant get all smiles");
        }
        return smiles;
    }
}
