package br.com.rws.handler;

import java.io.IOException;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import br.com.rws.DAO.TripRepository;
import br.com.rws.models.HandlerRequest;
import br.com.rws.models.HandlerResponse;
import br.com.rws.models.Trip;

public class CreateTripRecord implements RequestHandler<HandlerRequest, HandlerResponse>{
	
	private final TripRepository repository = new TripRepository();
	
	@Override
	public HandlerResponse handleRequest(HandlerRequest request, Context context) {
		context.getLogger().log("Creating a new trip record for the city ");
		System.out.println("fakfdsafgaldflajdfljasndfljansd");
		Trip trip = null;
		try {
			System.out.println(request.getBody());
			trip = new ObjectMapper().readValue(request.getBody(), Trip.class);
		} catch (IOException e) {
			return HandlerResponse.builder().setStatusCode(400).setRawBody("There is a error in your Trip! >>" + e.getMessage()).build();
		}
		try {
		context.getLogger().log("Creating a new trip record for the city " + trip.getCity());
		final Trip tripRecorded = repository.save(trip);
		return HandlerResponse.builder().setStatusCode(201).setObjectBody(tripRecorded).build();
		} catch (Exception e) {
			return HandlerResponse.builder().setStatusCode(400).setRawBody("There is a error in persistence >>" + e.getMessage()).build();
		}
	}

}