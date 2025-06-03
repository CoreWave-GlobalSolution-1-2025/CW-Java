package com.corewave.resources;

import com.corewave.dtos.PageDto;
import com.corewave.dtos.UserDto;
import com.corewave.models.User;
import com.corewave.repositories.UserRepo;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

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

        var usersList = REPO.list().stream()
                .map(u -> new UserDto(
                        u.getId(),
                        u.getName(),
                        u.isDeleted(),
                        u.getEmail())
                ).toList();

        var start = (page - 1) * pageSize;
        var end = Math.min(usersList.size(), (start + pageSize) - 1);

        var usersListPaginated = usersList.subList(start, end);

        return Response.ok(
                new PageDto<>(
                        page,
                        pageSize,
                        usersList.size(),
                        usersListPaginated
                )
        ).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addUser(User user) {
        return Response.status(Response.Status.NOT_IMPLEMENTED).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(User user) {
        return Response.status(Response.Status.NOT_IMPLEMENTED).build();
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUser(User user) {
        return Response.status(Response.Status.NOT_IMPLEMENTED).build();
    }

}
