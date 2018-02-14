package com.openmind.app

import ratpack.handling.Context
import ratpack.render.RendererSupport

import static ratpack.jackson.Jackson.json

class CourseRenderer extends RendererSupport<Course> {

    @Override
    void render(Context ctx, Course course) throws Exception {
        // Get request parameter fields with a comma separated list
        // of field names to include in the output.
        final String paramFields = ctx.request.queryParams.get('fields')

        if (paramFields) {
            // Transform comma separated property names to a Set.
            final Set<String> coursePropertyNames = paramFields.tokenize(',').toSet()

            // Create Map with only Course properties that need to be included.
            final Map partialCourse = filterProperties(course, coursePropertyNames)

            // Render Map.
            ctx.render(json(partialCourse))
        } else {
            // No fields request parameter so we can return the original Course object.
            ctx.render(json(course))
        }
    }

    /**
     * Find all properties in the object that are in the collection
     * of property names.
     *
     * @param object Object with properties to filter
     * @param propertyNames Names of properties to find
     * @return Map with properties
     */
    private Map filterProperties(final Object object, final Set<String> propertyNames) {
        object.properties.findAll { prop ->
            prop.key in propertyNames
        }
    }
}
