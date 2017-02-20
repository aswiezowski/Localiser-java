package pl.swiezowski.adam.localiser.hibernate;

import java.util.Optional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import pl.swiezowski.adam.localiser.entities.Localisation;

public class LocalisationDAO {

	public boolean save(Localisation localisation) {
		Session session = openSession();
		Transaction transaction = session.beginTransaction();
		session.save(localisation);
		transaction.commit();
		closeSession(session);
		
		return true;
	}

	public Optional<Localisation> get(String code) {
		Session session = openSession();
		Transaction transaction = session.beginTransaction();
		Optional<Localisation> localisation = getFromSession(code, session);
		transaction.commit();
		closeSession(session);
		return localisation;
	}

	private Optional<Localisation> getFromSession(String code, Session session) {
		Criteria criteria = session.createCriteria(Localisation.class);
		criteria.add(Restrictions.eq("code", code));
		if(criteria.list().size()<1){
			return Optional.empty();
		}
		return Optional.of((Localisation)criteria.list().iterator().next());
	}

	private void closeSession(Session session) {
		session.close();
	}

	private Session openSession() {
		return HibernateUtil.getSessionFactory().openSession();
	}

	public void update(String code, Localisation localisation) {
		Session session = openSession();
		Transaction transaction = session.beginTransaction();
		Localisation oldLocalisation = getFromSession(code, session).orElseThrow(() -> new IllegalStateException("No localisation with code: "+code));
		oldLocalisation.setLatitude(localisation.getLatitude());
		oldLocalisation.setLongitude(localisation.getLongitude());
		session.update(oldLocalisation);
		transaction.commit();
		session.close();
	}

	public void remove(String code) {
		Session session = openSession();
		Transaction transaction = session.beginTransaction();
		Localisation oldLocalisation = getFromSession(code, session).orElseThrow(() -> new IllegalStateException("No localisation with code: "+code));
		session.delete(oldLocalisation);
		transaction.commit();
		
		session.close();
	}
}
