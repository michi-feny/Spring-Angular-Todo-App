package ibee.webapp.todo_app.controller.support.hateoas.LinkConverter;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import ibee.webapp.todo_app.controller.support.Link;

public final class LinkUtils {

    private LinkUtils() {}

    public static Link createCustomLink(Class<?> controllerClass, String rel, String pathId, String method) {
        org.springframework.hateoas.Link springLink = linkTo(controllerClass)
                .slash(pathId)
                .withRel(rel);

        return Link.builder()
                .rel(springLink.getRel().value())
                .href(springLink.getHref())
                .method(method)
                .build();
    }
}
