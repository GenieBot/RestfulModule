package io.sponges.bot.modules.rest;

import io.sponges.bot.api.module.Module;
import io.sponges.bot.modules.rest.route.ClientsRoute;
import io.sponges.bot.modules.rest.route.IndexRoute;
import spark.Service;

import java.lang.reflect.InvocationTargetException;

class Routes {

    private final Module module;
    private final Service service;

    Routes(Module module) {
        this.module = module;
        this.service = Service.ignite();
        this.service.port(4568);
        this.service.exception(Exception.class, (exception, request, response) -> {
            exception.printStackTrace();
        });
        Route.setModule(module);
        register(
                new IndexRoute(), new ClientsRoute()
        );
        this.service.redirect.get("/api", RestModule.API_ROOT);
        this.service.redirect.get(RestModule.API_ROOT, RestModule.API_ROOT + "/");
    }

    private void register(Route... routes) {
        for (Route route : routes) {
            Class<? extends Service> clazz = service.getClass();
            String methodName = route.getMethod().name().toLowerCase();
            try {
                clazz.getMethod(methodName, String.class, spark.Route.class).invoke(service,
                        RestModule.API_ROOT + route.getRoute(), (spark.Route) route::internalExecute);
            } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

}
