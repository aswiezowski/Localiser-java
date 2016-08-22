package pl.swiezowski.adam.localiser.hibernate;

import java.util.Optional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import pl.swiezowski.adam.localiser.dto.CreateResponseDTO;
import pl.swiezowski.adam.localiser.dto.CreateResponseDTOImpl;
import pl.swiezowski.adam.localiser.entities.Localisation;
import pl.swiezowski.adam.localiser.logic.LocalisationPreparator;

public class LocationDAO {
	
	private static final String SUCCSESSFUL_STATUS = "Save successful";
	
	public CreateResponseDTO saveLocalisation(Localisation localisation){
		Localisation preparedLocalisation = LocalisationPreparator.prepareLocation(localisation);
		Session session = openSession();
		Transaction transaction = session.beginTransaction();
		session.save(preparedLocalisation);
		transaction.commit();
		closeSession(session);
		
		CreateResponseDTO response = new CreateResponseDTOImpl();
		response.setStatus(SUCCSESSFUL_STATUS);
		response.setCode(preparedLocalisation.getCode());
		return response;
	}
	
	public Optional<Localisation> getLocalisation(String code){
		Session session = openSession();
		Transaction transaction = session.beginTransaction();
		Criteria criteria = session.createCriteria(Localisation.class);
		criteria.add(Restrictions.eq("code", code));
		@SuppressWarnings("unchecked")
		Optional<Localisation> localisation = criteria.list().stream().findFirst().map(Localisation.class::cast);
		transaction.commit();
		closeSession(session);

		return localisation;
	}
	
	private void closeSession(Session session){
		session.close();
	}
	
	private Session openSession(){
		return HibernateUtil.getSessionFactory().openSession();
	}
}
