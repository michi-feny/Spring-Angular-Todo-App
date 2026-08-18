package ibee.webapp.todo_app.controller.baseController.hateosCrud.oldApproach;

import java.util.List;

import ibee.webapp.todo_app.controller.support.Link;

public class HateoasLinkBuilder {

    public static Link createLink(String rel, String href, String method) {
        return Link.builder().rel(rel).href(href).method(method).build();
    }

    public static List<Link> getDefaultCrudLinks(String basePath, Long id) {
        return List.of(
            createLink("self", basePath + "/" + id, "GET"),
            createLink("update", basePath + "/" + id, "PUT"),
            createLink("delete", basePath + "/" + id, "DELETE"),
            createLink("list", basePath, "GET"),
            createLink("create", basePath, "POST")
        );
    }

    public static List<Link> getCollectionLinks(String basePath) {
        return List.of(
            createLink("self", basePath, "GET"),
            createLink("create", basePath, "POST")
        );
    }
}
