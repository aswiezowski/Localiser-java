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

import pl.swiezowski.adam.localiser.entities.Localisation;
import pl.swiezowski.adam.localiser.entities.Localisation_;

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
		Optional<Localisation> localisation = getLocalisationInSession(code, session);
		transaction.commit();
		closeSession(session);
		return localisation;
	}

	public List<Localisation> getAll(Collection<String> code) {
		Session session = openSession();
		Transaction transaction = session.beginTransaction();
		List<Localisation> localisation = getLocalisationInSession(code, session);
		transaction.commit();
		closeSession(session);
		return localisation;
	}

	private Optional<Localisation> getLocalisationInSession(String codes, Session session) {
		return getLocalisationInSession(Collections.singleton(codes), session).stream().findFirst();
	}

	private List<Localisation> getLocalisationInSession(Collection<String> code, Session session) {
		CriteriaBuilder builder = session.getCriteriaBuilder();

		CriteriaQuery<Localisation> criteria = builder.createQuery(Localisation.class);
		Root<Localisation> root = criteria.from(Localisation.class);
		criteria.select(root);
		criteria.where(root.get(Localisation_.code).in(code));
		return session.createQuery(criteria).getResultList();
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
		Localisation oldLocalisation = getLocalisationInSession(code, session)
				.orElseThrow(() -> new IllegalStateException("No localisation with code: " + code));
		oldLocalisation.setLatitude(localisation.getLatitude());
		oldLocalisation.setLongitude(localisation.getLongitude());
		session.update(oldLocalisation);
		transaction.commit();
		session.close();
	}

	public void remove(String code) {
		Session session = openSession();
		Transaction transaction = session.beginTransaction();
		Localisation oldLocalisation = getLocalisationInSession(code, session)
				.orElseThrow(() -> new IllegalStateException("No localisation with code: " + code));
		session.delete(oldLocalisation);
		transaction.commit();

		session.close();
	}
}
