package com.corewave.resources;

import com.corewave.Utils.ResponseUtil;
import com.corewave.dtos.UserDto;
import com.corewave.models.User;
import com.corewave.repositories.UserRepo;
import com.corewave.services.UserService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.SQLException;
import java.util.stream.Collectors;

@Path("/users")
public class UserResource {
    private final UserRepo REPO = new UserRepo();
    private final String DEFAULT_PAGE_SIZE = "15";

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers(
            @QueryParam("page") @DefaultValue("1")
            int page,

            @QueryParam("size") @DefaultValue(DEFAULT_PAGE_SIZE)
            int pageSize
    ) {
        page = page <= 0 ? 1 : page;

        try {
            var usersList = REPO.list().stream()
                    .map(u -> new UserDto(
                            u.getId(),
                            u.getName(),
                            u.isDeleted(),
                            u.getEmail())
                    ).toList();

            return ResponseUtil.createPaginatedResponse(page, pageSize, usersList);

        } catch (SQLException e) {
            return ResponseUtil.createExceptionResponse(Response.Status.INTERNAL_SERVER_ERROR,
                    ResponseUtil.Texts.SERVER_ERROR_GET.toString());
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response addUser(User user) {
        if (!UserService.checkUserToCreate(user)) {
            return ResponseUtil.createExceptionResponse(Response.Status.BAD_REQUEST,
                    ResponseUtil.Texts.CREATE_MISSING_FIELDS.toString());
        }

        try {

            REPO.add(user);

        } catch (SQLException e) {
            return ResponseUtil.createExceptionResponse(Response.Status.INTERNAL_SERVER_ERROR,
                    ResponseUtil.Texts.SERVER_ERROR_ADD.toString());
        }

        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(
            @PathParam("id")
            int id
    ) {
        try {

            var userOptional = REPO.getById(id);

            if (userOptional.isEmpty()) {
                return ResponseUtil.createExceptionResponse(Response.Status.NOT_FOUND,
                        ResponseUtil.Texts.NOT_FOUND.toString());
            }

            var user = userOptional.get();

            return Response
                    .ok(user)
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

            User nUser) {

        try {

            if (REPO.getById(id).isEmpty()) {
                return ResponseUtil.createExceptionResponse(Response.Status.NOT_FOUND,
                        ResponseUtil.Texts.NOT_FOUND.toString());

            } else if (!UserService.checkUSerToUpdate(nUser)) {
                return ResponseUtil.createExceptionResponse(Response.Status.BAD_REQUEST,
                        ResponseUtil.Texts.UPDATE_MISSING_FIELDS.toString());
            }

            REPO.updateById(id, nUser);
            return getById(id);

        } catch (SQLException e) {
            return ResponseUtil.createExceptionResponse(Response.Status.INTERNAL_SERVER_ERROR,
                    ResponseUtil.Texts.SERVER_ERROR_GET.toString());
        } catch (NotFoundException e) {
            return ResponseUtil.createExceptionResponse(Response.Status.NOT_FOUND,
                    ResponseUtil.Texts.NOT_FOUND.toString());
        } catch (RuntimeException e) {
            return ResponseUtil.createExceptionResponse(Response.Status.INTERNAL_SERVER_ERROR,
                    ResponseUtil.Texts.SERVER_ERROR_UPDATE.toString());
        }
    }

    @DELETE
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteById(
            @PathParam("id")
            int id
    ) {

        try {
            var userOptional = REPO.getById(id);

            if (userOptional.isEmpty()) {
                return ResponseUtil.createExceptionResponse(Response.Status.NOT_FOUND,
                        ResponseUtil.Texts.NOT_FOUND.toString());
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

    @GET
    @Path("/validate-user")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response validateUser(User user) {

        try {

            var usersMap = REPO.list().stream()
                    .collect(Collectors.toMap(User::getEmail, User::getPassword));

            var savedPassword = usersMap.get(user.getEmail());

            if (savedPassword == null) {

                return ResponseUtil.createExceptionResponse(Response.Status.NOT_FOUND,
                        ResponseUtil.Texts.USER_NOT_FOUND.toString());
            } else {
                var validatedUser = savedPassword.equals(user.getPassword());
                if (validatedUser) {
                    return Response.noContent().build();
                } else {
                    return ResponseUtil.createExceptionResponse(Response.Status.UNAUTHORIZED,
                            ResponseUtil.Texts.INVALID_PASSWORD.toString());
                }

            }
        } catch (SQLException e) {
            return ResponseUtil.createExceptionResponse(Response.Status.INTERNAL_SERVER_ERROR,
                    ResponseUtil.Texts.SERVER_ERROR_GET.toString());
        }

    }

}
