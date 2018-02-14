import com.openmind.app.Course
import com.openmind.app.CourseRenderer

import static ratpack.groovy.Groovy.ratpack

ratpack {
    bindings {
        // Add to registry, so Ratpack can use it to render a Course object.
        bind CourseRenderer
    }

    handlers {
        all {
            final Course course = new Course(name: 'Ratpack Framework', teacher: 'Marcelo Martins', maxOccupation: 450)

            next(single(course))
        }
        get('course') { Course course ->
            render(course)
        }
    }
}
