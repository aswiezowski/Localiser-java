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
import pl.swiezowski.adam.localiser.dto.ResponseDTO;
import pl.swiezowski.adam.localiser.dto.RouteDTO;
import pl.swiezowski.adam.localiser.entities.Location;
import pl.swiezowski.adam.localiser.hibernate.LocationDAO;
import pl.swiezowski.adam.localiser.logic.CodeGenerator;
import pl.swiezowski.adam.localiser.logic.DistanceCalculator;
import pl.swiezowski.adam.localiser.logic.TravellingSalesmanSolver;

@Path("/locations")
@Consumes("application/json;charset=utf-8")
@Produces("application/json;charset=utf-8")
public class LocationResource {

	private DistanceCalculator distanceCalculator = new DistanceCalculator();
	private LocationDAO locationDAO = new LocationDAO();
	private CodeGenerator linkGenerator = new CodeGenerator();

	@POST
	public Response save(Location localisation) {
		localisation.setCode(linkGenerator.generateCode());
		if (!localisation.isValid()) {
			return Response.status(Response.Status.BAD_REQUEST.getStatusCode())
					.entity(new ResponseDTO("Localisation is invalid!")).build();
		}
		locationDAO.save(localisation);
		return Response.status(Response.Status.OK.getStatusCode())
				.entity(CreateResponseDTO.builder()
						.code(localisation.getCode())
						.status("Save successful")
						.build())
				.build();
	}

	@GET
	@Path("/{code}")
	public Response get(@PathParam("code") String code) {
		Optional<Location> localisation = locationDAO.get(code);
		if (localisation.isPresent()) {
			return Response.status(Response.Status.OK.getStatusCode()).entity(localisation.get()).build();
		}
		return Response.status(Response.Status.BAD_REQUEST.getStatusCode())
				.entity(new ResponseDTO("No localisation found!")).build();
	}

	@PUT
	@Path("/{code}")
	public Response update(@PathParam("code") String code, Location localisation) {
		locationDAO.update(code, localisation);
		return Response.status(Response.Status.OK.getStatusCode()).entity(new ResponseDTO("Update successful"))
				.build();
	}

	@DELETE
	@Path("/{code}")
	public Response remove(@PathParam("code") String code) {
		locationDAO.remove(code);
		return Response.status(Response.Status.OK.getStatusCode()).entity(new ResponseDTO("Remove successful"))
				.build();
	}

	@POST
	@Path("/shortest-route")
	public Response findShortestRoute(Collection<String> codes) {
		List<Location> localisations = locationDAO.getAll(codes);
		TravellingSalesmanSolver solver = new TravellingSalesmanSolver();
		List<Location> path = solver.findShortestPath(localisations.get(0), localisations);
		RouteDTO routeDTO = RouteDTO.builder().locations(path).distance(distanceCalculator.getDistance(path)).build();
		return Response.status(Response.Status.OK.getStatusCode()).entity(routeDTO)
				.build();
	}

}
