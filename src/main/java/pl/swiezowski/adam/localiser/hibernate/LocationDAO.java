package pl.swiezowski.adam.localiser.hibernate;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.Transaction;

import pl.swiezowski.adam.localiser.entities.Location;
import pl.swiezowski.adam.localiser.entities.Location_;

public class LocationDAO {

	public boolean save(Location localisation) {
		Session session = openSession();
		Transaction transaction = session.beginTransaction();
		session.save(localisation);
		transaction.commit();
		closeSession(session);

		return true;
	}

	public Optional<Location> get(String code) {
		Session session = openSession();
		Transaction transaction = session.beginTransaction();
		Optional<Location> localisation = getLocalisationInSession(code, session);
		transaction.commit();
		closeSession(session);
		return localisation;
	}

	public List<Location> getAll(Collection<String> code) {
		Session session = openSession();
		Transaction transaction = session.beginTransaction();
		List<Location> localisation = getLocalisationInSession(code, session);
		transaction.commit();
		closeSession(session);
		return localisation;
	}

	private Optional<Location> getLocalisationInSession(String codes, Session session) {
		return getLocalisationInSession(Collections.singleton(codes), session).stream().findFirst();
	}

	private List<Location> getLocalisationInSession(Collection<String> code, Session session) {
		CriteriaBuilder builder = session.getCriteriaBuilder();

		CriteriaQuery<Location> criteria = builder.createQuery(Location.class);
		Root<Location> root = criteria.from(Location.class);
		criteria.select(root);
		criteria.where(root.get(Location_.code).in(code));
		return session.createQuery(criteria).getResultList();
	}

	private void closeSession(Session session) {
		session.close();
	}

	private Session openSession() {
		return HibernateUtil.getSessionFactory().openSession();
	}

	public void update(String code, Location localisation) {
		Session session = openSession();
		Transaction transaction = session.beginTransaction();
		Location oldLocalisation = getLocalisationInSession(code, session)
				.orElseThrow(() -> new IllegalStateException("No location with code: " + code));
		oldLocalisation.setLatitude(localisation.getLatitude());
		oldLocalisation.setLongitude(localisation.getLongitude());
		oldLocalisation.setDescription(localisation.getDescription());
		session.update(oldLocalisation);
		transaction.commit();
		session.close();
	}

	public void remove(String code) {
		Session session = openSession();
		Transaction transaction = session.beginTransaction();
		Location oldLocalisation = getLocalisationInSession(code, session)
				.orElseThrow(() -> new IllegalStateException("No location with code: " + code));
		session.delete(oldLocalisation);
		transaction.commit();

		session.close();
	}
}
