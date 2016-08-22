package pl.swiezowski.adam.localiser.hibernate;

import org.hibernate.Session;
import org.hibernate.Transaction;

import pl.swiezowski.adam.localiser.entities.Localisation;
import pl.swiezowski.adam.localiser.dto.ResponseDTO;
import pl.swiezowski.adam.localiser.dto.ResponseDTOImpl;

public class LocationDAO {
	
	private static final String SUCCSESSFUL_STATUS = "Save successful";
	
	public ResponseDTO saveLocalisation(Localisation localisation){
		Session session = openSession();
		Transaction transaction = session.beginTransaction();
		session.save(localisation);
		transaction.commit();
		closeSession(session);
		
		ResponseDTO response = new ResponseDTOImpl();
		response.setId(localisation.getId());
		response.setStatus(SUCCSESSFUL_STATUS);
		return response;
	}
	
	private void closeSession(Session session){
		session.close();
	}
	
	private Session openSession(){
		return HibernateUtil.getSessionFactory().openSession();
	}
}
