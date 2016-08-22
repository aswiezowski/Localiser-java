package pl.swiezowski.adam.localiser.rest;

import java.util.Optional;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import pl.swiezowski.adam.localiser.dto.ResponseDTO;
import pl.swiezowski.adam.localiser.dto.ResponseDTOImpl;
import pl.swiezowski.adam.localiser.entities.Localisation;
import pl.swiezowski.adam.localiser.hibernate.LocationDAO;
 
@Path("/localisation")
public class RestServlet {
 
	LocationDAO locationDAO;
	
	public RestServlet(){
		locationDAO = new LocationDAO();
	}
	
	@GET
	@Path("/{param}")
	public Response getMsg(@PathParam("param") String msg) {
 
		String output = "Jersey say : " + msg;
 
		return Response.status(200).entity(output).build();
 
	}
	
	@PUT
	@Consumes("application/json")
	@Produces("application/json")
	public Response addLocalisation(Localisation localisation) {
		ResponseDTO response = locationDAO.saveLocalisation(localisation);
		
 
		return Response.status(200).entity(response).build();
 
	}
	
	@GET
	@Path("/{code}")
	@Produces("application/json")
	public Response getLocalisation(@PathParam("code") String code) {
		Optional<Localisation> localisation = locationDAO.getLocalisation(code);
		if(localisation.isPresent()){
			return Response.status(200).entity(localisation.get()).build();
		}
		
		return Response.status(400).entity(prepareErrorDTO()).build();
	}
	
	private ResponseDTO prepareErrorDTO(){
		ResponseDTO response = new ResponseDTOImpl();
		response.setStatus("No localisation found!");
		return response;
	}
 
}
