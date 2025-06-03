package com.corewave.resources;

import com.corewave.Utils.ResponseUtil;
import com.corewave.models.Event;
import com.corewave.repositories.EventRepo;
import com.corewave.services.EventService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.SQLException;

@Path("/events")
public class EventResource {
    private final EventRepo REPO = new EventRepo();
    private final String DEFAULT_PAGE_SIZE = "15";

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEvents(
            @QueryParam("page") @DefaultValue("1")
            int page,

            @QueryParam("size") @DefaultValue(DEFAULT_PAGE_SIZE)
            int pageSize
    ) {
        page = page <= 0 ? 1 : page;

        try {
            var eventsList = REPO.list();

            return ResponseUtil.createPaginatedResponse(page, pageSize, eventsList);
        } catch (SQLException e) {
            return ResponseUtil.createExceptionResponse(Response.Status.INTERNAL_SERVER_ERROR,
                    ResponseUtil.Texts.SERVER_ERROR_GET.toString());
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addEvent(Event event) {
        if (!EventService.checkEventToCreate(event)) {
            return ResponseUtil.createExceptionResponse(Response.Status.BAD_REQUEST,
                    ResponseUtil.Texts.CREATE_MISSING_FIELDS.toString());
        }

        try {
            REPO.add(event);
        } catch (SQLException e) {
            return ResponseUtil.createExceptionResponse(Response.Status.INTERNAL_SERVER_ERROR,
                    ResponseUtil.Texts.SERVER_ERROR_ADD.toString());
        }

        return Response.noContent().build();

    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(
            @PathParam("id")
            int id
    ) {
        try {
            var eventOptional = REPO.getById(id);

            if (eventOptional.isEmpty()) {
                return ResponseUtil.createExceptionResponse(Response.Status.NOT_FOUND,
                        ResponseUtil.Texts.ID_NOT_FOUND.toString());
            }

            var event = eventOptional.get();

            return Response
                    .ok(event)
                    .build();
        } catch (SQLException e) {
            return ResponseUtil.createExceptionResponse(Response.Status.INTERNAL_SERVER_ERROR,
                    ResponseUtil.Texts.SERVER_ERROR_GET.toString());
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateEventById(
            @PathParam("id")
            int id,

            Event nEvent) {

        try {
            if (REPO.getById(id).isEmpty()) {
                return ResponseUtil.createExceptionResponse(Response.Status.NOT_FOUND,
                        ResponseUtil.Texts.ID_NOT_FOUND.toString());
            } else if (!EventService.checkEventToUpdate(nEvent)) {
                return ResponseUtil.createExceptionResponse(Response.Status.BAD_REQUEST,
                        ResponseUtil.Texts.UPDATE_MISSING_FIELDS.toString());
            }

            REPO.updateById(id, nEvent);
            return getById(id);
        } catch (SQLException e) {
            return ResponseUtil.createExceptionResponse(Response.Status.INTERNAL_SERVER_ERROR,
                    ResponseUtil.Texts.SERVER_ERROR_GET.toString());
        } catch (Exception e) {
            return ResponseUtil.createExceptionResponse(Response.Status.INTERNAL_SERVER_ERROR,
                    ResponseUtil.Texts.SERVER_ERROR_UPDATE.toString());
        }
    }

    @DELETE
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteEventById(
            @PathParam("id")
            int id
    ) {

        try {
            var eventOptional = REPO.getById(id);

            if (eventOptional.isEmpty()) {
                return ResponseUtil.createExceptionResponse(Response.Status.NOT_FOUND,
                        ResponseUtil.Texts.ID_NOT_FOUND.toString());
            }

            REPO.deleteById(id);
        } catch (SQLException e) {
            return ResponseUtil.createExceptionResponse(Response.Status.INTERNAL_SERVER_ERROR,
                    ResponseUtil.Texts.SERVER_ERROR_GET.toString());
        } catch (Exception e) {
            return ResponseUtil.createExceptionResponse(Response.Status.INTERNAL_SERVER_ERROR,
                    ResponseUtil.Texts.SERVER_ERROR_DELETE.toString());
        }

        return Response.noContent().build();
    }

}
