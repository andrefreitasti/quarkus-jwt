package com.fsfx.quarkusjwt.rest;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.agroal.api.AgroalDataSource;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.fsfx.quarkusjwt.model.Message;

/**
 *
 * @author fsfx
 */
@Path("/resource")
public class ResourceREST {

	@Inject
	@Named("teste")
	AgroalDataSource dataSource;

	@GET
	@Path("/teste")
	@Produces(MediaType.APPLICATION_JSON)
	public String hello() throws SQLException {
		StringBuilder sb = new StringBuilder();
		try (Connection connection = dataSource.getConnection();
			 PreparedStatement ps =
					 connection.prepareStatement(" SELECT * FROM teste");
			 ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				sb.append(rs.getString("field")).append("\n");
			}
		}
		return sb.toString();
	}

	@RolesAllowed("USER")
	@GET @Path("/user") @Produces(MediaType.APPLICATION_JSON)
	public Response user() {
		return Response.ok(new Message("Content for user")).build();
	}
	
	@RolesAllowed("ADMIN")
	@GET @Path("/admin") @Produces(MediaType.APPLICATION_JSON)
	public Response admin() {
		return Response.ok(new Message("Content for admin")).build();
	}
	
	@RolesAllowed({"USER", "ADMIN"})
	@GET @Path("/user-or-admin") @Produces(MediaType.APPLICATION_JSON)
	public Response userOrAdmin() {
		return Response.ok(new Message("Content for user or admin")).build();
	}
}
