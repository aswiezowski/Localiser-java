package pl.swiezowski.adam.localiser.rest;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import pl.swiezowski.adam.localiser.dto.CreateResponseDTO;
import pl.swiezowski.adam.localiser.dto.CreateResponseDTOImpl;
import pl.swiezowski.adam.localiser.dto.ResponseDTO;
import pl.swiezowski.adam.localiser.dto.ResponseDTOImpl;
import pl.swiezowski.adam.localiser.entities.Localisation;
import pl.swiezowski.adam.localiser.hibernate.LocalisationDAO;
import pl.swiezowski.adam.localiser.logic.CodeGenerator;

@Path("/localisations")
@Consumes("application/json")
@Produces("application/json")
public class LocalisationResource {

	LocalisationDAO locationDAO;
	CodeGenerator linkGenerator;

	public LocalisationResource() {
		locationDAO = new LocalisationDAO();
		linkGenerator = new CodeGenerator();
	}

	@POST
	public Response save(Localisation localisation) {
		localisation.setCode(linkGenerator.generateLink());
		if (!localisation.isValid()) {
			return Response.status(Response.Status.BAD_REQUEST.getStatusCode())
					.entity(new ResponseDTOImpl("Localisation is invalid!")).build();
		}
		locationDAO.save(localisation);
		CreateResponseDTO response = new CreateResponseDTOImpl();
		response.setStatus("Save successful");
		response.setCode(localisation.getCode());
		return Response.status(Response.Status.OK.getStatusCode()).entity(response).build();
	}

	@GET
	@Path("/{code}")
	public Response get(@PathParam("code") String code) {
		Optional<Localisation> localisation = locationDAO.get(code);
		if (localisation.isPresent()) {
			return Response.status(Response.Status.OK.getStatusCode()).entity(localisation.get()).build();
		}
		return Response.status(Response.Status.BAD_REQUEST.getStatusCode()).entity(prepareErrorDTO()).build();
	}

	private ResponseDTO prepareErrorDTO() {
		ResponseDTO response = new ResponseDTOImpl();
		response.setStatus("No localisation found!");
		return response;
	}

	@PUT
	@Path("/{code}")
	public Response update(@PathParam("code") String code, Localisation localisation) {
		locationDAO.update(code, localisation);
		return Response.status(Response.Status.OK.getStatusCode()).entity(new ResponseDTOImpl("Update successful"))
				.build();
	}

	@DELETE
	@Path("/{code}")
	public Response remove(@PathParam("code") String code) {
		locationDAO.remove(code);
		return Response.status(Response.Status.OK.getStatusCode()).entity(new ResponseDTOImpl("Remove successful"))
				.build();
	}

	@POST
	@Path("/shortest-route")
	public List<String> findShortestroute(Collection<String> codes) {
		return null;
	}

}
