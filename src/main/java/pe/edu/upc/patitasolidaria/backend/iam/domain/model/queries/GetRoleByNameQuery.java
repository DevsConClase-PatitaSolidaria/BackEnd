package pe.edu.upc.patitasolidaria.backend.iam.domain.model.queries;

import pe.edu.upc.patitasolidaria.backend.iam.domain.model.valueobjects.Roles;

public record GetRoleByNameQuery(Roles name) {
}
